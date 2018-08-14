package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.component.FNEditText;
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

public class SiteOnDgSetFragment extends MainFragment {
    AppCompatEditText etDGCurrent, etDGFrequency, etDGVoltage, etBatChangeCurrent, etBatteryVoltage;
    SharedPreferences SITEDGpref;
    String strDgCurrent, strDgFrequency, strDgVoltage, strBatteryChargeCurrent, strBatteryVoltage;
    String strUserId, strSavedDateTime, strSiteId, CurtomerSite_Id;
    Button btnSubmit;
    SharedPreferences userPref;
    String dgCurrent, dgFrequency, dgVoltage, batteryChargeCurrent, batteryVoltage;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_site_on_dg_set;
    }

    @Override
    protected void loadView() {
        etDGCurrent = findViewById(R.id.etDGCurrent);
        etDGFrequency = findViewById(R.id.etDGFrequency);
        etDGVoltage = findViewById(R.id.etDGVoltage);
        etBatChangeCurrent = findViewById(R.id.etBatChangeCurrent);
        etBatteryVoltage = findViewById(R.id.etBatteryVoltage);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    public void getSharedPrefData() {
       /* SITEDGpref = getContext().getSharedPreferences("SITEDGpref", MODE_PRIVATE);
        strDgCurrent = SITEDGpref.getString("DgCurrent", "");
        strDgFrequency = SITEDGpref.getString("DgFrequency", "");
        strDgVoltage = SITEDGpref.getString("DgVoltage", "");
        strBatteryChargeCurrent = SITEDGpref.getString("BatteryChargeCurrent", "");
        strBatteryVoltage = SITEDGpref.getString("BatteryVoltage", "");
        strUserId = SITEDGpref.getString("USER_ID", "");
        strSavedDateTime = SITEDGpref.getString("SetOnDGSavedDateTime", "");*/
    }

    @Override
    protected void dataToView() {
        getUserPref();
        getSharedPrefData();
        if (!isEmptyStr(strDgCurrent) || !isEmptyStr(strDgFrequency)
                || !isEmptyStr(strDgVoltage)
                || !isEmptyStr(strBatteryChargeCurrent) || !isEmptyStr(strBatteryVoltage)) {
            etDGCurrent.setText(strDgCurrent);
            etDGFrequency.setText(strDgFrequency);
            etDGVoltage.setText(strDgVoltage);
            etBatChangeCurrent.setText(strBatteryChargeCurrent);
            etBatteryVoltage.setText(strBatteryVoltage);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            dgCurrent = etDGCurrent.getText().toString().trim();
            dgFrequency = etDGFrequency.getText().toString().trim();
            dgVoltage = etDGVoltage.getText().toString().trim();
            batteryChargeCurrent = etBatChangeCurrent.getText().toString().trim();
            batteryVoltage = etBatteryVoltage.getText().toString().trim();
            if (isEmptyStr(dgCurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Current");
            } else if (isEmptyStr(dgFrequency)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Frequency");
            } else if (isEmptyStr(dgVoltage)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Voltage");
            } else if (isEmptyStr(batteryChargeCurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Battery Charge Current");
            } else if (isEmptyStr(batteryVoltage)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Battery Voltage");
            } else {
                /*SharedPreferences.Editor editor = SITEDGpref.edit();
                editor.putString("UserId", strUserId);
                editor.putString("DgCurrent", dgCurrent);
                editor.putString("DgFrequency", dgFrequency);
                editor.putString("DgVoltage", dgVoltage);
                editor.putString("BatteryChargeCurrent", batteryChargeCurrent);
                editor.putString("BatteryVoltage", batteryVoltage);
                editor.putString("SetOnDGSavedDateTime", strSavedDateTime);
                editor.commit();*/
                saveBasicDataonServer();
            }
        }
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
                jsonObject.put("Activity", "DG");
                JSONObject DGData = new JSONObject();
                DGData.put("DG_Current", dgCurrent);
                DGData.put("DG_Frequency", dgFrequency);
                DGData.put("DG_Voltage", dgVoltage);
                DGData.put("Battery_Voltage", batteryVoltage);
                DGData.put("Battery_ChargeCurrent", batteryChargeCurrent);
                jsonObject.put("DGData", DGData);
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
                            ASTUIUtil.showToast("Site DG Details Updated Successfully.");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Site DG Details not  updated!");
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
