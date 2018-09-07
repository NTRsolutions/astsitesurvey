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
    AppCompatEditText etEBCurrentRphase, etEBCurrentBphase, etEBCurrentYphase,
            etEBVoltageYphase, etEBVoltageBphase, etEBVoltageRphase, etEBFrequencyRphase, etEBFrequencyYphase, etEBFrequencyBphase,
            etbattcharging, etBattcurrent;
    String dgrCurrent="0", dgyCurrent="0", dgbCurrent="0",
            stretEBVoltageYphase="0", stretEBVoltageBphase="0", stretEBVoltageRphase="0",
            stretEBFrequencyRphase="0", stretEBFrequencyYphase="0", stretEBFrequencyBphase="0";
    String strUserId, strSavedDateTime, strSiteId, CurtomerSite_Id;
    AtmDatabase atmDatabase;
    Button btnSubmit;
    SharedPreferences userPref;
    String batrycharging, battcurrent;


    private LinearLayout etEBCurrentRphaseLayout, etEBCurrentBphaseLayout, etEBCurrentYphaseLayout,
            etEBVoltageRphaseLayout, etEBVoltageYphaseLayout, etEBVoltageBphaseLayout,
            etEBFrequencyRphaseLayout, etEBFrequencyYphaseLayout, etEBFrequencyBphaseLayout;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_set_on_eb;
    }

    @Override
    protected void loadView() {

        etbattcharging = findViewById(R.id.etbattcharging);
        etBattcurrent = findViewById(R.id.etBattcurrent);
        btnSubmit = findViewById(R.id.btnSubmit);
        etEBCurrentRphase = findViewById(R.id.etEBCurrentRphase);
        etEBCurrentBphase = findViewById(R.id.etEBCurrentBphase);
        etEBCurrentYphase = findViewById(R.id.etEBCurrentYphase);
        etEBCurrentRphaseLayout = findViewById(R.id.etEBCurrentRphaseLayout);
        etEBCurrentBphaseLayout = findViewById(R.id.etEBCurrentBphaseLayout);
        etEBCurrentYphaseLayout = findViewById(R.id.etEBCurrentYphaseLayout);
        etEBVoltageYphase = findViewById(R.id.etEBVoltageYphase);
        etEBVoltageBphase = findViewById(R.id.etEBVoltageBphase);
        etEBVoltageRphase = findViewById(R.id.etEBVoltageRphase);
        etEBVoltageRphaseLayout = findViewById(R.id.etEBVoltageRphaseLayout);
        etEBVoltageYphaseLayout = findViewById(R.id.etEBVoltageYphaseLayout);
        etEBVoltageBphaseLayout = findViewById(R.id.etEBVoltageBphaseLayout);
        etEBFrequencyRphaseLayout = findViewById(R.id.etEBFrequencyRphaseLayout);
        etEBFrequencyYphaseLayout = findViewById(R.id.etEBFrequencyYphaseLayout);
        etEBFrequencyBphaseLayout = findViewById(R.id.etEBFrequencyBphaseLayout);
        etEBFrequencyRphase = findViewById(R.id.etEBFrequencyRphase);
        etEBFrequencyYphase = findViewById(R.id.etEBFrequencyYphase);
        etEBFrequencyBphase = findViewById(R.id.etEBFrequencyBphase);

    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    private SharedPreferences noofebPhaseprf;
    String noofebPhase;

    @Override
    protected void dataToView() {
        getUserPref();
        atmDatabase = new AtmDatabase(getContext());
        noofebPhaseprf = getContext().getSharedPreferences("noofebPhaseprf", MODE_PRIVATE);
        noofebPhase = noofebPhaseprf.getString("noofebPhase", "");
        if (noofebPhase.equalsIgnoreCase("1 Phase")) {
            etEBCurrentRphaseLayout.setVisibility(View.VISIBLE);
            etEBCurrentBphaseLayout.setVisibility(View.GONE);
            etEBCurrentYphaseLayout.setVisibility(View.GONE);
            etEBVoltageRphaseLayout.setVisibility(View.VISIBLE);
            etEBVoltageYphaseLayout.setVisibility(View.GONE);
            etEBVoltageBphaseLayout.setVisibility(View.GONE);
            etEBFrequencyRphaseLayout.setVisibility(View.VISIBLE);
            etEBFrequencyYphaseLayout.setVisibility(View.GONE);
            etEBFrequencyBphaseLayout.setVisibility(View.GONE);
        } else {
            etEBCurrentRphaseLayout.setVisibility(View.VISIBLE);
            etEBCurrentBphaseLayout.setVisibility(View.VISIBLE);
            etEBCurrentYphaseLayout.setVisibility(View.VISIBLE);
            etEBVoltageRphaseLayout.setVisibility(View.VISIBLE);
            etEBVoltageYphaseLayout.setVisibility(View.VISIBLE);
            etEBVoltageBphaseLayout.setVisibility(View.VISIBLE);
            etEBFrequencyRphaseLayout.setVisibility(View.VISIBLE);
            etEBFrequencyYphaseLayout.setVisibility(View.VISIBLE);
            etEBFrequencyBphaseLayout.setVisibility(View.VISIBLE);
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
            dgrCurrent = etEBCurrentRphase.getText().toString().trim();
            dgyCurrent = etEBCurrentYphase.getText().toString().trim();
            dgbCurrent = etEBCurrentBphase.getText().toString().trim();
            stretEBVoltageRphase = etEBVoltageRphase.getText().toString().trim();
            stretEBVoltageYphase = etEBVoltageYphase.getText().toString().trim();
            stretEBVoltageBphase = etEBVoltageBphase.getText().toString().trim();
            stretEBFrequencyRphase = etEBFrequencyRphase.getText().toString().trim();
            stretEBFrequencyYphase = etEBFrequencyYphase.getText().toString().trim();
            stretEBFrequencyBphase = etEBFrequencyBphase.getText().toString().trim();
            batrycharging = etbattcharging.getText().toString().trim();
            battcurrent = etBattcurrent.getText().toString().trim();
            if (isEmptyStr(dgrCurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Current");
            } else if (isEmptyStr(stretEBFrequencyRphase)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Frequency");
            } else if (isEmptyStr(stretEBVoltageRphase)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Voltage");
            } else if (isEmptyStr(batrycharging)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Batt Charging Voltage");
            } else if (isEmptyStr(battcurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Batt Charging Current");
            } else {
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
                EBData.put("EB_BattVoltage", batrycharging);
                EBData.put("EB_BattChargeCurrent", battcurrent);
                EBData.put("EB_CurrentR", dgrCurrent);
                EBData.put("EB_CurrentY", dgyCurrent);
                EBData.put("EB_CurrentB", dgbCurrent);
                EBData.put("EB_VoltageR", stretEBVoltageRphase);
                EBData.put("EB_VoltageY", stretEBVoltageYphase);
                EBData.put("EB_VoltageB", stretEBVoltageBphase);
                EBData.put("EB_FrequencyR", stretEBFrequencyRphase);
                EBData.put("EB_FrequencyY", stretEBFrequencyYphase);
                EBData.put("EB_FrequencyB", stretEBFrequencyBphase);
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


