package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class DieselFillingFragment extends MainFragment {

    AppCompatEditText etLPD, etQualityDiesel, etFillingother, etDieselfuillDone;
    String LPD, QualityDiesel, Fillingother, DieselfuillDone;
    String strLPD, strQualityDiesel, strFillingother, strDieselfuillDone;
    Button btnSubmit;
    SharedPreferences dieselSharedPref, userPref;
    String strUserId, strSiteId;

    @Override
    protected int fragmentLayout() {
        return R.layout.dieselfilling_fragment;
    }

    @Override
    protected void loadView() {
        etLPD = this.findViewById(R.id.etLPD);
        etQualityDiesel = this.findViewById(R.id.etQualityDiesel);
        etFillingother = this.findViewById(R.id.etFillingother);
        etDieselfuillDone = this.findViewById(R.id.etDieselfuillDone);
        btnSubmit = this.findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getSharedPrefData();
        getUserPref();
        if (!isEmptyStr(strLPD) || !isEmptyStr(strQualityDiesel) || !isEmptyStr(strFillingother)
                || isEmptyStr(strDieselfuillDone)
                ) {
            etLPD.setText(strLPD);
            etQualityDiesel.setText(strQualityDiesel);
            etFillingother.setText(strFillingother);
            etDieselfuillDone.setText(strDieselfuillDone);
        }
    }

    /*
     *
     * Shared Prefrences---------------------------------------
     */
    public void getSharedPrefData() {
 /*       dieselSharedPref = getContext().getSharedPreferences("DieselSharedPref", MODE_PRIVATE);
        strLPD = dieselSharedPref.getString("DIESEL_strLPD", "");
        strQualityDiesel = dieselSharedPref.getString("DIESEL_strQualityDiesel", "");
        strFillingother = dieselSharedPref.getString("DIESEL_strFillingother", "");
        strDieselfuillDone = dieselSharedPref.getString("DIESEL_strDieselfuillDone", "");*/
    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {

                saveBasicDataonServer();
           /*     SharedPreferences.Editor editor = dieselSharedPref.edit();
                editor.putString("DIESEL_strLPD", LPD);
                editor.putString("DIESEL_strQualityDiesel", QualityDiesel);
                editor.putString("DIESEL_strFillingother", Fillingother);
                editor.putString("DIESEL_strDieselfuillDone", DieselfuillDone);
                editor.commit();*/
            }

        }
    }

    public boolean isValidate() {
        LPD = etLPD.getText().toString();
        QualityDiesel = etQualityDiesel.getText().toString();
        Fillingother = etFillingother.getText().toString();
        DieselfuillDone = etDieselfuillDone.getText().toString();
            if (isEmptyStr(LPD)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter LPD");
                return false;
            } else if (isEmptyStr(QualityDiesel)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Quality of Diesel");
                return false;
            } else if (isEmptyStr(Fillingother)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Filling In DG Tank or Any Other ");
                return false;
            } else if (isEmptyStr(DieselfuillDone)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Diesel Filling Done by CareTaker/Technician/Filler");
                return false;
            }

        return true;
    }


    public void saveBasicDataonServer() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar progressBar = new ASTProgressBar(getContext());
            progressBar.show();
            String serviceURL = Constant.BASE_URL + Constant.SurveyDataSave;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Site_ID", strSiteId);
                jsonObject.put("User_ID", strUserId);
                jsonObject.put("Activity", "DieselFilling");
                JSONObject DieselFillingData = new JSONObject();
                DieselFillingData.put("LPD", LPD);
                DieselFillingData.put("QualityofDiesel", QualityDiesel);
                DieselFillingData.put("FillingInDGTank", Fillingother);
                DieselFillingData.put("DieselFillingDoneby", DieselfuillDone);
                jsonObject.put("DieselFillingData",DieselFillingData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("JsonData", jsonObject.toString());
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelper fileUploaderHelper = new FileUploaderHelper(getContext(), payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        if (data.getStatus() == 1) {
                            ASTUIUtil.showToast("Your Diesel Filling Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Diesel Filling Data  has not been updated!");
                    }
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                }
            };
            fileUploaderHelper.execute();
        } else {
            ASTUIUtil.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }

    }

    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
      /*  if (equpImagList != null && equpImagList.size() > 0) {
            for (SaveOffLineData data : equpImagList) {
                if (data != null) {
                    if (data.getImagePath() != null) {
                        File inputFile = new File(data.getImagePath());
                        if (inputFile.exists()) {
                            multipartBody.addFormDataPart("PMInstalEqupImages", data.getImageName(), RequestBody.create(MEDIA_TYPE_PNG, inputFile));
                        }
                    }
                }
            }
        }
*/
        return multipartBody;
    }
}
