package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTErrorIndicator;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipDescriptionDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class BatteryFragment extends MainFragment {
    TextView imgPrevious, imgNext;
    static ImageView batteryimg, cellImg;
    static boolean isImage1;
   static String bateryphoto, cellPhoto;
    FNEditText etSerialNum, etYear, etDescription;
    AutoCompleteTextView etMake, etModel, etCapacity;
    SharedPreferences pref;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription;
    String strSavedDateTime, strUserId, strSiteId, strDescriptionId;
    String strMakeId, strModelId;
    ArrayList<EquipMakeDataModel> equipMakeList;
    AtmDatabase atmDatabase;
    String[] arrMake;
    String[] arrModel;
    ArrayList<EquipCapacityDataModel> equipCapacityList;
    String[] arrCapacity;

    LinearLayout perviousLayout, nextLayout;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_battery;
    }

    @Override
    protected void loadView() {
        imgNext = findViewById(R.id.imgNext);
        imgPrevious = findViewById(R.id.imgPrevious);
        batteryimg = findViewById(R.id.image1);
        cellImg = findViewById(R.id.image2);
        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
        etCapacity = findViewById(R.id.etCapacity);
        etSerialNum = findViewById(R.id.etSerialNum);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        this.nextLayout = findViewById(R.id.nextLayout);
        this.perviousLayout = findViewById(R.id.nextLayout);
    }

    @Override
    protected void setClickListeners() {
        imgPrevious.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        batteryimg.setOnClickListener(this);
        cellImg.setOnClickListener(this);
        nextLayout.setOnClickListener(this);
        perviousLayout.setOnClickListener(this);

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void dataToView() {
        atmDatabase = new AtmDatabase(getContext());
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "BB");
        arrMake = new String[equipMakeList.size()];
        for (int i = 0; i < equipMakeList.size(); i++) {
            arrMake[i] = equipMakeList.get(i).getName();
        }
        getSharedprefData();
        equipCapacityList = new ArrayList<>();
        ArrayAdapter<String> adapterMakeName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrMake);
        etMake.setAdapter(adapterMakeName);
        etMake.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String strMake = etMake.getText().toString();
                if (!strMake.equals("") && strMake.length() > 1) {
                    ArrayList<EquipCapacityDataModel> equipCapacityDataList = atmDatabase.getEquipmentCapacityData("DESC", strMake);
                    if (equipCapacityDataList.size() > 0) {
                        for (int i = 0; i < equipCapacityDataList.size(); i++) {
                            arrModel[i] = equipCapacityDataList.get(i).getName();
                        }
                        ArrayAdapter<String> adapterModelName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrModel);
                        etModel.setAdapter(adapterModelName);
                    }
                }
            }
        });

        etModel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String strModel = etModel.getText().toString();
                if (!strModel.equals("") && strModel.length() > 1) {
                    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList = atmDatabase.getEquipmentDescriptionData("DESC", strModel);
                    arrCapacity = new String[equipDescriptionDataList.size()];
                    if (equipDescriptionDataList.size() > 0) {
                        for (int i = 0; i < equipDescriptionDataList.size(); i++) {
                            arrCapacity[i] = equipDescriptionDataList.get(i).getName();
                        }
                        ArrayAdapter<String> adapterCapacityName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrCapacity);
                        etCapacity.setAdapter(adapterCapacityName);
                    }
                }
            }
        });
        if (!strMake.equals("") || !strModel.equals("") || !strCapacity.equals("") || !strSerialNum.equals("")
                || !strYearOfManufacturing.equals("") || !strDescription.equals("")) {
            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            if (!bateryphoto.equals("") || !cellPhoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(bateryphoto)).placeholder(R.drawable.noimage).into(batteryimg);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(cellPhoto)).placeholder(R.drawable.noimage).into(cellImg);
            }
        }
        makeDir();
        ASTUIUtil commonFunctions = new ASTUIUtil();
        final String currentDate = commonFunctions.getFormattedDate("dd/MM/yyyy", System.currentTimeMillis());
    }

    /*
     *
     *     Shared Prefrences
     */
    public void getSharedprefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("Make", "");
        strModel = pref.getString("Model", "");
        strCapacity = pref.getString("Capacity", "");
        strMakeId = pref.getString("MakeId", "");
        strModelId = pref.getString("ModelId", "");
        strDescriptionId = pref.getString("DescriptionId", "");
        strSerialNum = pref.getString("SerialNum", "");
        strYearOfManufacturing = pref.getString("YearOfManufacturing", "");
        strDescription = pref.getString("Description", "");
        bateryphoto = pref.getString("Photo1", "");
        cellPhoto = pref.getString("Photo2", "");
        strSavedDateTime = pref.getString("BbActivitySavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");

    }


    public void makeDir() {
        File direct = new File(Environment.getExternalStorageDirectory() + "/" + strSiteId);
        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/" + strSiteId + "/");
            wallpaperDirectory.mkdirs();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgPrevious || view.getId() == R.id.perviousLayout) {
            saveScreenData(false, false);
        } else if (view.getId() == R.id.image1) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = true;
        } else if (view.getId() == R.id.image2) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
        } else if (view.getId() == R.id.imgNext || view.getId() == R.id.nextLayout) {
            if (isValidate()) {
                String newEquipment = "0";
                if (equipCapacityList != null && equipCapacityList.size() > 0) {
                    for (int i = 0; i < equipCapacityList.size(); i++) {
                        if (capacity.equals(equipCapacityList.get(i).getName())) {
                            strDescriptionId = equipCapacityList.get(i).getId();
                        }
                    }
                }
                if (strDescriptionId.equals("") || strDescriptionId.equals("0")) {
                    strDescriptionId = "0";
                }
                if (equipMakeList != null && equipMakeList.size() > 0) {
                    for (int i = 0; i < equipMakeList.size(); i++) {
                        if (make.equals(equipMakeList.get(i).getName())) {
                            strMakeId = equipMakeList.get(i).getId();
                        }
                    }
                }
                if (strMakeId.equals("") || strMakeId.equals("0")) {
                    strMakeId = "0";
                }
                if (equipCapacityList != null && equipCapacityList.size() > 0) {
                    for (int i = 0; i < equipCapacityList.size(); i++) {
                        if (make.equals(equipCapacityList.get(i).getName())) {
                            strModelId = equipCapacityList.get(i).getId();
                        }
                    }
                }
                if (strModelId.equals("") || strModelId.equals("0")) {
                    strModelId = "0";
                }
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("USER_ID", strUserId);
                editor.putString("Make", make);
                editor.putString("Model", model);
                editor.putString("Capacity", capacity);
                editor.putString("DescriptionId", strDescriptionId);
                editor.putString("MakeId", strMakeId);
                editor.putString("ModelId", strModelId);
                editor.putString("SerialNum", serialNumber);
                editor.putString("YearOfManufacturing", yearOfManufacturing);
                editor.putString("Description", description);
                editor.putString("Photo1", bateryphoto);
                editor.putString("Photo2", cellPhoto);
                editor.putString("BbActivitySavedDateTime", currentDateTime);
                strMakeId = pref.getString("", "");
                strModelId = pref.getString("", "");
                editor.commit();
                saveScreenData(true, false);

            }

        }
    }


    // ----validation -----
    private boolean isValidate() {
        make = getTextFromView(this.etMake);
        model = getTextFromView(this.etCapacity);
        capacity = getTextFromView(this.etCapacity);
        serialNumber = getTextFromView(this.etSerialNum);
        yearOfManufacturing = getTextFromView(this.etYear);
        description = getTextFromView(this.etDescription);
        currentDateTime = String.valueOf(System.currentTimeMillis());
        if (isEmptyStr(make)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Make");
            return false;
        } else if (isEmptyStr(model)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Model");
            return false;
        } else if (isEmptyStr(capacity)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity");
            return false;
        } else if (isEmptyStr(serialNumber)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Serial Number");
            return false;
        } else if (isEmptyStr(yearOfManufacturing)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Manufacturing Year");
            return false;
        } else if (isEmptyStr(description)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
            return false;
        } else if (isEmptyStr(bateryphoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Battery Bank Photo");
            return false;
        } else if (isEmptyStr(cellPhoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select One Cell Photo");
            return false;
        }
        return true;
    }

    private void saveScreenData(boolean NextPreviousFlag, boolean DoneFlag) {
        Intent intent = new Intent("ViewPageChange");
        intent.putExtra("NextPreviousFlag", NextPreviousFlag);
        intent.putExtra("DoneFlag", DoneFlag);
        getActivity().sendBroadcast(intent);
    }



    public static void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (FNObjectUtil.isNonEmptyStr(deviceFile.getCompressFilePath())) {
                File compressPath = new File(deviceFile.getCompressFilePath());
                if (compressPath.exists()) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(isImage1 ? batteryimg : cellImg);
                    if (isImage1) {
                        bateryphoto = deviceFile.getFilePath().toString();
                    } else {
                        cellPhoto = deviceFile.getFilePath().toString();
                    }
                    //compressPath.delete();
                }
            } else if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(isImage1 ? batteryimg : cellImg);
                if (isImage1) {
                    bateryphoto = deviceFile.getFilePath().toString();
                } else {
                    cellPhoto = deviceFile.getFilePath().toString();
                }
                if (deviceFile.isfromCamera() || deviceFile.isCropped()) {
                    // deviceFile.getFilePath().delete();
                }
            }
        }
    }

    public static void getResult(ArrayList<MediaFile> files) {
        getPickedFiles(files);
    }
}

