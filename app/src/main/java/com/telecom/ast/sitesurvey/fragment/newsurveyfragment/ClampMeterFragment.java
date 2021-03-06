package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class ClampMeterFragment extends MainFragment {

    static ImageView clampimage1, clampimage2;
    File clampbattVoltageFile, clampadCurrentFile;
    String strclampmeBattVoltage, strclampLoadCurrent;
    String BattVoltageclamp, LoadCurrentclamp;
    AppCompatEditText etclampBattVoltage, etclampLoadCurrent;
    Button btnSubmit;
    static boolean isImage1clmp, isImage2clmp;
    SharedPreferences userPref;
    String strSavedDateTime, strUserId, strSiteId, CurtomerSite_Id;

    @Override
    protected int fragmentLayout() {
        return R.layout.clampmeter_fragment;
    }


    @Override
    protected void loadView() {
        clampimage1 = findViewById(R.id.clampimage1);
        clampimage2 = findViewById(R.id.clampimage2);
        etclampBattVoltage = findViewById(R.id.etBattVoltage);
        etclampLoadCurrent = findViewById(R.id.etLoadCurrent);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        clampimage1.setOnClickListener(this);
        clampimage2.setOnClickListener(this);
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

    /*
     *
     * Shared Prefrences---------------------------------------
     */

    public void getSharedPrefData() {
  /*      pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strBattVoltage = pref.getString("CLAMP_BattVoltage", "");
        strLoadCurrent = pref.getString("CLAMP_LoadCurrent", "");
        battVoltagephoto = pref.getString("CLAMP_BattVoltagephoto", "");
        adCurrentPhoto = pref.getString("CLAMP_AdCurrentPhoto", "");
        batteryDisChaphoto = pref.getString("CLAMP_BatteryDisChaphoto", "");*/
    }

    @Override
    protected void dataToView() {
        getUserPref();
        getSharedPrefData();
        if (!isEmptyStr(strclampmeBattVoltage) || !isEmptyStr(strclampLoadCurrent)) {
            etclampBattVoltage.setText(strclampmeBattVoltage);
            etclampLoadCurrent.setText(strclampLoadCurrent);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.image1) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1clmp = true;
            isImage2clmp = false;
        } else if (view.getId() == R.id.image2) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1clmp = false;
            isImage2clmp = true;
        } else {
            if (isValidate()) {
           /*     SharedPreferences.Editor editor = pref.edit();
                editor.putString("CLAMP_BattVoltage", BattVoltage);
                editor.putString("CLAMP_LoadCurrent", LoadCurrent);
                editor.putString("CLAMP_BattVoltagephoto", battVoltagephoto);
                editor.putString("CLAMP_AdCurrentPhoto", adCurrentPhoto);
                editor.putString("CLAMP_BatteryDisChaphoto", batteryDisChaphoto);
                editor.commit();*/
                saveBasicDataonServer();
            }

        }
    }


    public boolean isValidate() {
        BattVoltageclamp = etclampBattVoltage.getText().toString();
        LoadCurrentclamp = etclampLoadCurrent.getText().toString();
        if (isEmptyStr(BattVoltageclamp)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Batt Voltage");
            return false;
        } else if (isEmptyStr(LoadCurrentclamp)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Load Current");
            return false;
        } else if (clampbattVoltageFile == null || !clampbattVoltageFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Batt Voltage Photo");
            return false;
        } else if (clampadCurrentFile == null || !clampadCurrentFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Load Current Photo");
            return false;
        }
        return true;
    }


    /**
     * THIS USE an ActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FNReqResCode.ATTACHMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FNFilePicker.EXTRA_SELECTED_MEDIA);
            getResult(files);

        }
    }

    public void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1clmp) {
                    String imageName = CurtomerSite_Id + "_Clamp_1_BattVoltImg.jpg";
                    clampbattVoltageFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(clampbattVoltageFile).into(clampimage1);
                    //overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2clmp) {
                    String imageName = CurtomerSite_Id + "_Clamp_1_LoadCurrentImg.jpg";
                    clampadCurrentFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(clampadCurrentFile).into(clampimage2);
                }
            }
            //  }
        }
    }


    public void getResult(ArrayList<MediaFile> files) {
        getPickedFiles(files);
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
                jsonObject.put("Activity", "BB");
                JSONObject BBData = new JSONObject();
                BBData.put("BBEquipment_ID", "Clamp");
                BBData.put("BattVoltage", BattVoltageclamp);
                BBData.put("LoadCurrent", LoadCurrentclamp);
                JSONArray jsonObjectarray = new JSONArray();
                jsonObjectarray.put(BBData);
                jsonObject.put("BBData", jsonObjectarray);


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
                            ASTUIUtil.showToast("Your Clamp Meter Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Your Clamp Meter Data  has not been updated!");
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
        if (clampbattVoltageFile.exists()) {
            multipartBody.addFormDataPart(clampbattVoltageFile.getName(), clampbattVoltageFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, clampbattVoltageFile));
        }
        if (clampadCurrentFile.exists()) {
            multipartBody.addFormDataPart(clampadCurrentFile.getName(), clampadCurrentFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, clampadCurrentFile));
        }

        return multipartBody;
    }


}