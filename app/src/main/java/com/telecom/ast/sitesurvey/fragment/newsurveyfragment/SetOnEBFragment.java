package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.ProgressDialog;
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
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.BasicDataModel;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.model.EbMeterDataModel;
import com.telecom.ast.sitesurvey.model.SelectedEquipmentDataModel;
import com.telecom.ast.sitesurvey.model.SiteOnBatteryBankDataModel;
import com.telecom.ast.sitesurvey.model.SiteOnDG;
import com.telecom.ast.sitesurvey.model.SiteOnEbDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class SetOnEBFragment extends MainFragment {
    AppCompatEditText etebCurrent, etebVolatge, etEbFrequency, etbattcharging, etBattcurrent;
    String strCurrent, strVoltage, strFrequency, battcharging, Battcurrent;
    String strUserId, strSavedDateTime, strSiteId, CurtomerSite_Id;
    AtmDatabase atmDatabase;
    Button btnSubmit;
    SharedPreferences userPref;
    String ebCurrent,ebFrequency,ebVoltage,batrycharging,battcurrent;
    @Override
    protected int fragmentLayout() {
        return R.layout.activity_set_on_eb;
    }

    @Override
    protected void loadView() {
        etebCurrent = findViewById(R.id.etebCurrent);
        etebVolatge = findViewById(R.id.etebVolatge);
        etEbFrequency = findViewById(R.id.etEbFrequency);
        etbattcharging = findViewById(R.id.etbattcharging);
        etBattcurrent = findViewById(R.id.etBattcurrent);
        btnSubmit = findViewById(R.id.btnSubmit);

    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    public void getSharedPrefData() {
   /*     pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strCurrent = pref.getString("ebCurrent", "");
        strVoltage = pref.getString("ebVoltage", "");
        strFrequency = pref.getString("ebFrequency", "");
        battcharging = pref.getString("battcharging", "");
        Battcurrent = pref.getString("Battcurrent", "");
        strUserId = pref.getString("USER_ID", "");
        strSavedDateTime = pref.getString("SetOnEbSavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");*/
    }


    @Override
    protected void dataToView() {
        getUserPref();
        atmDatabase = new AtmDatabase(getContext());
        getSharedPrefData();
        if (!isEmptyStr(strCurrent) || !isEmptyStr(strVoltage)
                || !isEmptyStr(strFrequency) || !isEmptyStr(battcharging)
                ||!isEmptyStr(Battcurrent)) {
            etebCurrent.setText(strCurrent);
            etebVolatge.setText(strVoltage);
            etEbFrequency.setText(strFrequency);
            etbattcharging.setText(battcharging);
            etBattcurrent.setText(Battcurrent);

        }
    }


    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
             ebCurrent = etebCurrent.getText().toString().trim();
             ebFrequency = etebVolatge.getText().toString().trim();
             ebVoltage = etEbFrequency.getText().toString().trim();
             batrycharging = etbattcharging.getText().toString().trim();
             battcurrent = etBattcurrent.getText().toString().trim();
            if (isEmptyStr(ebCurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Current");
            } else if (isEmptyStr(ebFrequency)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Frequency");
            } else if (isEmptyStr(ebVoltage)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Voltage");
            } else if (isEmptyStr(batrycharging)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Batt Charging Voltage");
            } else if (isEmptyStr(battcurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Batt Charging Current");
            } else {
              /*  SharedPreferences.Editor editor = pref.edit();
                editor.putString("UserId", strUserId);
                editor.putString("ebCurrent", ebCurrent);
                editor.putString("ebFrequency", ebFrequency);
                editor.putString("ebVoltage", ebVoltage);
                editor.putString("battcharging", batrycharging);
                editor.putString("Battcurrent", battcurrent);
                editor.putString("SetOnEbSavedDateTime", strSavedDateTime);
                editor.commit();
                ASTProgressBar progressDialog = new ASTProgressBar(getContext());
                progressDialog.show();
                //  saveBasicDataDetails();
                //  saveEbMeterDataDetails();
                //   saveSiteOnDg();
                //   saveSiteOnEb();
                //  saveEquipmentData();
                progressDialog.dismiss();*/
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
                jsonObject.put("Activity", "EB");
                JSONObject EBData = new JSONObject();
                EBData.put("EB_Current", ebCurrent);
                EBData.put("EB_Frequency", ebFrequency);
                EBData.put("EB_Voltage", ebVoltage);
                EBData.put("Battery_Voltage", batrycharging);
                EBData.put("Battery_ChargeCurrent", battcurrent);
                jsonObject.put("EBData", EBData);
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
                            ASTUIUtil.showToast("Site EB Details Updated Successfully.");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Site EB Details not  updated!");
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
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
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


