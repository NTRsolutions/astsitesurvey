package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.telecom.ast.sitesurvey.model.CircleMasterDataModel;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.model.DistrictMasterDataModel;
import com.telecom.ast.sitesurvey.model.SSAmasterDataModel;
import com.telecom.ast.sitesurvey.model.SiteMasterDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class SolarPanelsFragment extends MainFragment {
    AppCompatEditText etNoofPanel, etMake, etcapacitypanel, etNoofAGb;
    Button btnSubmit;
    String NoofPanel, Make, capacitypanel, noOfAgb;
    String strNoofPanel, serMake, strcapacitypanel, strAgb;
    String strUserId, strSiteId;
    SharedPreferences solarPannelSharedPref, userPref;
    @Override
    protected int fragmentLayout() {
        return R.layout.solarpanel_fragment;
    }

    @Override
    protected void loadView() {
        this.etNoofPanel = findViewById(R.id.etNoofPanel);
        this.etMake = findViewById(R.id.etMake);
        this.etcapacitypanel = findViewById(R.id.etcapacitypanel);
        this.etNoofAGb = findViewById(R.id.etNoofAGb);
        this.btnSubmit = findViewById(R.id.btnSubmit);
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
        getSharedPrefSaveData();
        getUserPref();
        if (!isEmptyStr(strNoofPanel) || !isEmptyStr(serMake) ||
                !isEmptyStr(strcapacitypanel) || !isEmptyStr(strAgb)) {
            etNoofPanel.setText(strNoofPanel);
            etMake.setText(serMake);
            etcapacitypanel.setText(strcapacitypanel);
            etNoofAGb.setText(strAgb);
        }

    }
    /*
     *
     * get Data in Shared Pref.
     */

    public void getSharedPrefSaveData() {
    /*    solarPannelSharedPref = getContext().getSharedPreferences("SolarPannelSharedPref", MODE_PRIVATE);
        strNoofPanel = solarPannelSharedPref.getString("SOLARPAN_NoofPanel", "");
        serMake = solarPannelSharedPref.getString("SOLARPAN_Make", "");
        strcapacitypanel = solarPannelSharedPref.getString("SOLARPAN_capacitypanel", "");
        strAgb = solarPannelSharedPref.getString("SOLARPAN_noOfAgb", "");*/
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
              /*  SharedPreferences.Editor editor = solarPannelSharedPref.edit();
                editor.putString("SOLARPAN_NoofPanel", NoofPanel);
                editor.putString("SOLARPAN_Make", Make);
                editor.putString("SOLARPAN_capacitypanel", capacitypanel);
                editor.putString("SOLARPAN_noOfAgb", noOfAgb);
                editor.commit();*/

            }
        }
    }


    public boolean isValidate() {
        NoofPanel = getTextFromView(this.etNoofPanel);
        Make = getTextFromView(this.etMake);
        capacitypanel = getTextFromView(this.etcapacitypanel);
        noOfAgb = getTextFromView(this.etNoofAGb);
            if (isEmptyStr(NoofPanel)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of Panel");
                return false;
            } else if (isEmptyStr(Make)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Make");
                return false;
            } else if (isEmptyStr(capacitypanel)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity panel");
                return false;
            } else if (isEmptyStr(noOfAgb)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of AGB");
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
                jsonObject.put("Activity", "SolarPanel");
                JSONObject SolarPanelData = new JSONObject();
                SolarPanelData.put("Qty", NoofPanel);
                SolarPanelData.put("Make", Make);
                SolarPanelData.put("Capacity", capacitypanel);
                SolarPanelData.put("AGBQty", noOfAgb);
                jsonObject.put("SolarPanelData", SolarPanelData);
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
                            ASTUIUtil.showToast("Your Sloar Panels Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Your Sloar Panels   Data   has not been updated!");
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
