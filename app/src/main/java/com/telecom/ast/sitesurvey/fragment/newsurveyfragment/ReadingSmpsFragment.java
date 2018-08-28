package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipDescriptionDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.ASTUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;
import com.telecom.ast.sitesurvey.utils.FilePickerHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class ReadingSmpsFragment extends MainFragment {

    ImageView battVoltageImage, loadCurrentImage;
    File battVoltageFile, adCurrentFile;
    String strBattVoltage, strLoadCurrent;
    String BattVoltage, LoadCurrent;
    AppCompatEditText etBattVoltage, etLoadCurrent;
    SharedPreferences pref;
    Button btnSubmit;
    static boolean isImage1 = false, isImage2 = false;
    SharedPreferences userPref;
    String strSavedDateTime, strUserId, strSiteId, CurtomerSite_Id;
    static ImageView clampimage1, clampimage2;
    File clampbattVoltageFile, clampadCurrentFile;
    String strclampmeBattVoltage, strclampLoadCurrent;
    String BattVoltageclamp, LoadCurrentclamp;
    AppCompatEditText etclampBattVoltage, etclampLoadCurrent;
    static boolean isImage1clmp, isImage2clmp;

    @Override
    protected int fragmentLayout() {
        return R.layout.readingsmps_fragment;
    }

    @Override
    protected void loadView() {
        battVoltageImage = findViewById(R.id.image1);
        loadCurrentImage = findViewById(R.id.image2);
        etBattVoltage = findViewById(R.id.etBattVoltage);
        etLoadCurrent = findViewById(R.id.etLoadCurrent);
        btnSubmit = findViewById(R.id.btnSubmit);
        clampimage1 = findViewById(R.id.clampimage1);
        clampimage2 = findViewById(R.id.clampimage2);
        etclampBattVoltage = findViewById(R.id.etBattVoltage);
        etclampLoadCurrent = findViewById(R.id.etLoadCurrent);
    }

    @Override
    protected void setClickListeners() {
        battVoltageImage.setOnClickListener(this);
        loadCurrentImage.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        clampimage1.setOnClickListener(this);
        clampimage2.setOnClickListener(this);

    }

    @Override
    protected void setAccessibility() {

    }

    /*
     *
     * Shared Prefrences---------------------------------------
     */

    public void getSharedPrefData() {
   /*     pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strBattVoltage = pref.getString("READING_BattVoltage", "");
        strLoadCurrent = pref.getString("READING_LoadCurrent", "");
        battVoltagephoto = pref.getString("BattVoltagephoto", "");
        adCurrentPhoto = pref.getString("AdCurrentPhoto", "");
        batteryDisChaphoto = pref.getString("BatteryDisChaphoto", "");*/
    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    @Override
    protected void dataToView() {
        getSharedPrefData();
        getUserPref();
        if (!isEmptyStr(strBattVoltage) || !isEmptyStr(strLoadCurrent)) {
            etBattVoltage.setText(strBattVoltage);
            etLoadCurrent.setText(strLoadCurrent);
          /*  if (!battVoltagephoto.equals("") || !adCurrentPhoto.equals("") || !batteryDisChaphoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(battVoltagephoto)).placeholder(R.drawable.noimage).into(battVoltageImage);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(adCurrentPhoto)).placeholder(R.drawable.noimage).into(loadCurrentImage);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(batteryDisChaphoto)).placeholder(R.drawable.noimage).into(batteryDisChaImage);
            }*/
        }
        if (!isEmptyStr(strclampmeBattVoltage) || !isEmptyStr(strclampLoadCurrent)) {
            etclampBattVoltage.setText(strclampmeBattVoltage);
            etclampLoadCurrent.setText(strclampLoadCurrent);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.clampimage1) {
            String imageName = CurtomerSite_Id + "_SMPS_1_BattVoltImg.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1clmp = true;
            isImage2clmp = false;
            isImage1 = false;
            isImage2 = false;
        } else if (view.getId() == R.id.clampimage2) {
            String imageName = CurtomerSite_Id + "_SMPS_1_LoadCurrentImg.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1clmp = false;
            isImage2clmp = true;
            isImage1 = false;
            isImage2 = false;
        } else if (view.getId() == R.id.image1) {
            String imageName = CurtomerSite_Id + "_Clamp_1_BattVoltImg.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1clmp = false;
            isImage2clmp = false;
            isImage1 = true;
            isImage2 = false;
        } else if (view.getId() == R.id.image2) {
            String imageName = CurtomerSite_Id + "_Clamp_1_LoadCurrentImg.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1clmp = false;
            isImage2clmp = false;
            isImage1 = false;
            isImage2 = true;
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
               /* SharedPreferences.Editor editor = pref.edit();
                editor.putString("READING_BattVoltage", BattVoltage);
                editor.putString("READING_LoadCurrent", LoadCurrent);
                editor.putString("BattVoltagephoto", battVoltagephoto);
                editor.putString("AdCurrentPhoto", adCurrentPhoto);
                editor.putString("BatteryDisChaphoto", batteryDisChaphoto);
                editor.commit();*/
                saveBasicDataonServer();
            }

        }
    }


    public boolean isValidate() {
        BattVoltage = etBattVoltage.getText().toString();
        LoadCurrent = etLoadCurrent.getText().toString();
        BattVoltageclamp = etclampBattVoltage.getText().toString();
        LoadCurrentclamp = etclampLoadCurrent.getText().toString();
        if (isEmptyStr(BattVoltage)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Batt Voltage");
            return false;
        } else if (isEmptyStr(LoadCurrent)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Load Current");
            return false;
        } else if (battVoltageFile == null || !battVoltageFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Batt Voltage Photo");
            return false;
        } else if (adCurrentFile == null || !adCurrentFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Load Current Photo");
            return false;
        } else if (isEmptyStr(BattVoltageclamp)) {
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
                BBData.put("BBEquipment_ID", "SMPS");
                BBData.put("BattVoltage", BattVoltage);
                BBData.put("LoadCurrent", LoadCurrent);

                JSONObject BBclampMeterData = new JSONObject();
                BBclampMeterData.put("BBEquipment_ID", "Clamp");
                BBclampMeterData.put("BattVoltage", BattVoltageclamp);
                BBclampMeterData.put("LoadCurrent", LoadCurrentclamp);

                JSONArray jsonObjectarray = new JSONArray();
                jsonObjectarray.put(BBData);
                jsonObjectarray.put(BBclampMeterData);

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
                            ASTUIUtil.showToast("Your Reading SMPS Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Your IYour Reading SMPS  Data has not been updated!");
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
        if (battVoltageFile.exists()) {
            multipartBody.addFormDataPart(battVoltageFile.getName(), battVoltageFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, battVoltageFile));
        }
        if (adCurrentFile.exists()) {
            multipartBody.addFormDataPart(adCurrentFile.getName(), adCurrentFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, adCurrentFile));
        }
        if (clampbattVoltageFile.exists()) {
            multipartBody.addFormDataPart(clampbattVoltageFile.getName(), clampbattVoltageFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, clampbattVoltageFile));
        }
        if (clampadCurrentFile.exists()) {
            multipartBody.addFormDataPart(clampadCurrentFile.getName(), clampadCurrentFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, clampadCurrentFile));
        }

        return multipartBody;
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
        if (resultCode == Activity.RESULT_OK) {
            onCaptureImageResult();
        }
    }



    //capture image compress
    private void onCaptureImageResult() {
        if (isImage1) {
            String imageName = CurtomerSite_Id + "_SMPS_1_BattVoltImg.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, battVoltageImage);
            }
        } else if (isImage2) {
            String imageName = CurtomerSite_Id + "_SMPS_1_LoadCurrentImg.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, loadCurrentImage);
            }
        } else if (isImage1clmp) {
            String imageName = CurtomerSite_Id + "_Clamp_1_BattVoltImg.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, clampimage1);
            }
        } else if (isImage2clmp) {
            String imageName = CurtomerSite_Id + "_Clamp_1_LoadCurrentImg.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, clampimage2);
            }
        }
    }


    //compres image
    private void compresImage(final File file, final String fileName, final ImageView imageView) {
        new AsyncTask<Void, Void, Boolean>() {
            File imgFile;
            ASTProgressBar progressBar;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = new ASTProgressBar(getContext());
                progressBar.show();
            }
            @Override
            protected Boolean doInBackground(Void... voids) {
//compress file
                Boolean flag = false;
                int ot = FilePickerHelper.getExifRotation(file);
                Bitmap bitmap = FilePickerHelper.compressImage(file.getAbsolutePath(), ot, 800.0f, 800.0f);
                if (bitmap != null) {
                    Uri uri = FilePickerHelper.getImageUri(getContext(), bitmap);
//save compresed file into location
                    imgFile = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator, fileName);
                    try {
                        InputStream iStream = getContext().getContentResolver().openInputStream(uri);
                        byte[] inputData = FilePickerHelper.getBytes(iStream);

                        FileOutputStream fOut = new FileOutputStream(imgFile);
                        fOut.write(inputData);
                        //   bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        iStream.close();
                        flag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                // imageView.setImageBitmap(bitmap);
                if (isImage1) {
                    battVoltageFile = imgFile;
                    Picasso.with(ApplicationHelper.application().getContext()).load(battVoltageFile).into(imageView);
                } else if (isImage2) {
                    adCurrentFile = imgFile;
                    Picasso.with(ApplicationHelper.application().getContext()).load(adCurrentFile).into(imageView);
                } else if (isImage1clmp) {
                    clampbattVoltageFile = imgFile;
                    Picasso.with(ApplicationHelper.application().getContext()).load(clampbattVoltageFile).into(imageView);
                }else if (isImage2clmp) {
                    clampadCurrentFile = imgFile;
                    Picasso.with(ApplicationHelper.application().getContext()).load(clampadCurrentFile).into(imageView);
                }
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        }.execute();

    }


}
