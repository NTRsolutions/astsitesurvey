package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.BtsInfoData;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class OperatorNameFragment extends MainFragment {
    private LinearLayout mContainerView;
    private Button btnSubmit;
    private View mExclusiveEmptyView;
    AppCompatEditText etbtsOperator, etNofCab, etbtscurrent, etbtscurrentbtSide, etbtsvVoltagebtsside, etbtsvollatagesmpsside, btstyelSpinner;
    //Spinner btstyelSpinner ;
    String btsOperator, NofCab, btscurrentbtSide, btscurrent, btsvVoltagebtsside, btsvollatagesmpsside;
    String strbtsOperator, strNofCab, strbtscurrentbtSide, strbtscurrent, strbtsvVoltagebtsside, strbtsvollatagesmpsside, btstype;
    SharedPreferences pref;
    String strUserId, strSiteId;
    SharedPreferences userPref;
    int sno;
    AtmDatabase atmDatabase;

    @Override
    protected void getArgs() {
        sno = this.getArgsInt("sno");
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.operator_main;
    }

    @Override
    protected void loadView() {
        etbtsOperator = this.findViewById(R.id.etbtsOperator);
        etNofCab = this.findViewById(R.id.etNofCab);
        etbtscurrent = this.findViewById(R.id.btscurrent);
        etbtscurrentbtSide = this.findViewById(R.id.btscurrentbtSide);
        etbtsvVoltagebtsside = this.findViewById(R.id.btsvVoltagebtsside);
        etbtsvollatagesmpsside = this.findViewById(R.id.btsvollatagesmpsside);
        btstyelSpinner=this.findViewById(R.id.btstype);
        btnSubmit = this.findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);

    }

    @Override
    protected void setAccessibility() {
        etbtsOperator.setEnabled(false);
        etNofCab.setEnabled(false);
        btstyelSpinner.setEnabled(false);

    }


    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
    }

    @Override
    protected void dataToView() {
        getSharedPrefSaveData();
        getUserPref();

        atmDatabase = new AtmDatabase(getContext());
        BtsInfoData btsInfoData = atmDatabase.getBTSInfoById(sno);
        if (btsInfoData != null) {
            btstype = btsInfoData.getType();
            strbtsOperator = btsInfoData.getName();
            strNofCab = btsInfoData.getCabinetQty();
        }


        if (!isEmptyStr(strbtsOperator) || !isEmptyStr(strNofCab) || !isEmptyStr(strbtscurrentbtSide)) {
            etbtsOperator.setText(strbtsOperator);
            etNofCab.setText(strNofCab);
            etbtscurrent.setText(strbtscurrentbtSide);
            etbtscurrentbtSide.setText(strbtscurrent);
            etbtsvVoltagebtsside.setText(strbtsvVoltagebtsside);
            etbtsvollatagesmpsside.setText(strbtsvollatagesmpsside);
            btstyelSpinner.setText(btstype);
           /* if (btstyelSpinner.getSelectedItem().toString().equalsIgnoreCase(btstype)) {

            }*/
        }
    }

    /*
     *
     * get Data in Shared Pref.
     */

    public void getSharedPrefSaveData() {
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addBts) {
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();

             /*   SharedPreferences.Editor editor = pref.edit();
                editor.putString("strbtsOperator", strbtsOperator);
                editor.putString("strNofCab", strNofCab);
                editor.putString("strbtscurrentbtSide", strbtscurrentbtSide);
                editor.putString("strbtscurrent", strbtscurrent);
                editor.putString("strbtsvVoltagebtsside", strbtsvVoltagebtsside);
                editor.putString("strbtsvollatagesmpsside", strbtsvollatagesmpsside);
                editor.commit();
*/
            }
        }
    }

    public boolean isValidate() {
        strbtsOperator = getTextFromView(this.etbtsOperator);
        strNofCab = getTextFromView(this.etNofCab);
        strbtscurrentbtSide = getTextFromView(this.etbtscurrent);
        strbtscurrent = getTextFromView(this.etbtscurrentbtSide);
        strbtsvVoltagebtsside = getTextFromView(this.etbtsvVoltagebtsside);
        strbtsvollatagesmpsside = getTextFromView(this.etbtsvollatagesmpsside);
        btstype = getTextFromView(this.btstyelSpinner);
        // btstype = btstyelSpinner.getSelectedItem().toString();

        if (isEmptyStr(strbtsOperator)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Operator name ");
            return false;
        } else if (isEmptyStr(strNofCab)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No Of Caninets ");
            return false;
        } else if (isEmptyStr(strbtscurrentbtSide)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Current SMPS Side");
            return false;
        } else if (isEmptyStr(strbtscurrent)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Voltage SMPS Side");
            return false;
        } else if (isEmptyStr(strbtsvVoltagebtsside)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Filled");
            return false;
        } else if (isEmptyStr(strbtsvollatagesmpsside)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No Voltage BTS Side");
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
                jsonObject.put("Activity", "BTS");
                JSONObject BTSData = new JSONObject();
                BTSData.put("Type", btstype);
                BTSData.put("Name", btsOperator);
                BTSData.put("CabinetQty", NofCab);
                BTSData.put("SMPSVoltage", strbtsvVoltagebtsside);
                BTSData.put("SMPSCurrent", strbtsvollatagesmpsside);
                BTSData.put("BTSVoltage", strbtscurrent);
                BTSData.put("BTSCurrent", strbtscurrentbtSide);
                BTSData.put("NoofDCDBBox", "0");
                BTSData.put("NoofKroneBox", "0");
                BTSData.put("NoofTransmissionRack", "0");
                BTSData.put("NoofTransmissionRack", "0");
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(BTSData);
                jsonObject.put("BTSData", jsonArray);

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
                            ASTUIUtil.showToast("Your BTS  Data save Successfully");
                            atmDatabase.deleteBTCRows(sno);
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Your BTS  Data has not been updated!");
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
