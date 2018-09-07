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
    private AppCompatEditText etDGCurrentRphase, etDGCurrentBphase, etDGCurrentYphase,
            etDGVoltageYphase, etDGVoltageBphase, etDGVoltageRphase, etDGFrequencyRphase, etDGFrequencyYphase, etDGFrequencyBphase,
            etBatChangeCurrent, etBatteryVoltage;


    private String strUserId, strSavedDateTime, strSiteId, CurtomerSite_Id;
    private Button btnSubmit;
    private SharedPreferences userPref;
    private SharedPreferences noofPhaseprf;
    private String batteryChargeCurrent, batteryVoltage, dgrCurrent="0", dgyCurrent="0", dgbCurrent="0",
            stretDGVoltageYphase="0", stretDGVoltageBphase="0", stretDGVoltageRphase="0",
            stretDGFrequencyRphase="0", stretDGFrequencyYphase="0", stretDGFrequencyBphase="0";
    String noofPhase;

    private LinearLayout etDGCurrentRphaseLayout, etDGCurrentBphaseLayout, etDGCurrentYphaseLayout,
            etDGVoltageRphaseLayout, etDGVoltageYphaseLayout, etDGVoltageBphaseLayout,
            etDGFrequencyRphaseLayout, etDGFrequencyYphaseLayout, etDGFrequencyBphaseLayout;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_site_on_dg_set;
    }

    @Override
    protected void loadView() {

        btnSubmit = findViewById(R.id.btnSubmit);
        etDGCurrentRphase = findViewById(R.id.etDGCurrentRphase);
        etBatChangeCurrent = findViewById(R.id.etBatChangeCurrent);
        etBatteryVoltage = findViewById(R.id.etBatteryVoltage);

        etDGCurrentRphaseLayout = findViewById(R.id.etDGCurrentRphaseLayout);
        etDGCurrentBphase = findViewById(R.id.etDGCurrentBphase);
        etDGCurrentYphase = findViewById(R.id.etDGCurrentYphase);
        etDGCurrentBphaseLayout = findViewById(R.id.etDGCurrentBphaseLayout);
        etDGCurrentYphaseLayout = findViewById(R.id.etDGCurrentYphaseLayout);
        etDGVoltageYphase = findViewById(R.id.etDGVoltageYphase);
        etDGVoltageBphase = findViewById(R.id.etDGVoltageBphase);
        etDGVoltageRphase = findViewById(R.id.etDGVoltageRphase);
        etDGVoltageRphaseLayout = findViewById(R.id.etDGVoltageRphaseLayout);
        etDGVoltageYphaseLayout = findViewById(R.id.etDGVoltageYphaseLayout);
        etDGVoltageBphaseLayout = findViewById(R.id.etDGVoltageBphaseLayout);
        etDGFrequencyRphaseLayout = findViewById(R.id.etDGFrequencyRphaseLayout);
        etDGFrequencyYphaseLayout = findViewById(R.id.etDGFrequencyYphaseLayout);
        etDGFrequencyBphaseLayout = findViewById(R.id.etDGFrequencyBphaseLayout);
        etDGFrequencyRphase = findViewById(R.id.etDGFrequencyRphase);
        etDGFrequencyYphase = findViewById(R.id.etDGFrequencyYphase);
        etDGFrequencyBphase = findViewById(R.id.etDGFrequencyBphase);
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


    @Override
    protected void dataToView() {
        getUserPref();
        noofPhaseprf = getContext().getSharedPreferences("noofPhaseprf", MODE_PRIVATE);
        noofPhase = noofPhaseprf.getString("noofPhase", "");


        if (noofPhase.equalsIgnoreCase("1 Phase")) {
            etDGCurrentRphaseLayout.setVisibility(View.VISIBLE);
            etDGCurrentBphaseLayout.setVisibility(View.GONE);
            etDGCurrentYphaseLayout.setVisibility(View.GONE);
            etDGVoltageRphaseLayout.setVisibility(View.VISIBLE);
            etDGVoltageYphaseLayout.setVisibility(View.GONE);
            etDGVoltageBphaseLayout.setVisibility(View.GONE);
            etDGFrequencyRphaseLayout.setVisibility(View.VISIBLE);
            etDGFrequencyYphaseLayout.setVisibility(View.GONE);
            etDGFrequencyBphaseLayout.setVisibility(View.GONE);
        } else {
            etDGCurrentRphaseLayout.setVisibility(View.VISIBLE);
            etDGCurrentBphaseLayout.setVisibility(View.VISIBLE);
            etDGCurrentYphaseLayout.setVisibility(View.VISIBLE);
            etDGVoltageRphaseLayout.setVisibility(View.VISIBLE);
            etDGVoltageYphaseLayout.setVisibility(View.VISIBLE);
            etDGVoltageBphaseLayout.setVisibility(View.VISIBLE);
            etDGFrequencyRphaseLayout.setVisibility(View.VISIBLE);
            etDGFrequencyYphaseLayout.setVisibility(View.VISIBLE);
            etDGFrequencyBphaseLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            dgrCurrent = etDGCurrentRphase.getText().toString().trim();
            dgyCurrent = etDGCurrentYphase.getText().toString().trim();
            dgbCurrent = etDGCurrentBphase.getText().toString().trim();
            stretDGVoltageRphase = etDGVoltageRphase.getText().toString().trim();
            stretDGVoltageYphase = etDGVoltageYphase.getText().toString().trim();
            stretDGVoltageBphase = etDGVoltageBphase.getText().toString().trim();
            stretDGFrequencyRphase = etDGFrequencyRphase.getText().toString().trim();
            stretDGFrequencyYphase = etDGFrequencyYphase.getText().toString().trim();
            stretDGFrequencyBphase = etDGFrequencyBphase.getText().toString().trim();

            batteryChargeCurrent = etBatChangeCurrent.getText().toString().trim();
            batteryVoltage = etBatteryVoltage.getText().toString().trim();
            if (isEmptyStr(dgrCurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Current");
            } else if (isEmptyStr(stretDGFrequencyRphase)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Frequency");
            } else if (isEmptyStr(stretDGVoltageRphase)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Voltage");
            } else if (isEmptyStr(batteryChargeCurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Battery Charge Current");
            } else if (isEmptyStr(batteryVoltage)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Battery Voltage");
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
                jsonObject.put("Activity", "DG");
                JSONObject DGData = new JSONObject();
                DGData.put("DG_CurrentR", dgrCurrent);
                DGData.put("DG_CurrentY", dgyCurrent);
                DGData.put("DG_CurrentB", dgbCurrent);
                DGData.put("DG_VoltageR", stretDGVoltageRphase);
                DGData.put("DG_VoltageY", stretDGVoltageYphase);
                DGData.put("DG_VoltageB", stretDGVoltageBphase);
                DGData.put("DG_FrequencyR", stretDGFrequencyRphase);
                DGData.put("DG_FrequencyY", stretDGFrequencyYphase);
                DGData.put("DG_FrequencyB", stretDGFrequencyBphase);
                DGData.put("DG_BattVoltage", batteryVoltage);
                DGData.put("DG_BattChargeCurrent", batteryChargeCurrent);
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
