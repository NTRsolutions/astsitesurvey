package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
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
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class AirConditionerFragment extends MainFragment {
    TextView imgPrevious, imgNext;
    static ImageView imageView1, imageView2;
    static boolean isImage1;
    FNEditText etSerialNum, etYear, etDescription, etType, etNumberOfAC;
    AutoCompleteTextView etCapacity, etMake, etModel;
    String image1 = "", image2 = "";
    Uri imageUri1, imageUri2;
    SharedPreferences pref;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription, strType, strNumberOfAC;
    String strPhoto1, strPhoto2, strSavedDateTime, strUserId, strSiteId;
    String strMakeId, strModelId, strDescriptionId;
    String[] arrMake;
    String[] arrModel;
    String[] arrCapacity;
    ArrayList<EquipMakeDataModel> arrEquipData;
    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    AtmDatabase atmDatabase;
    LinearLayout perviousLayout, nextLayout;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, type, currentDateTime, numOfACs;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_air_conditioner;
    }

    @Override
    protected void loadView() {
        imgNext = findViewById(R.id.imgNext);
        imgPrevious = findViewById(R.id.imgPrevious);
        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
        etCapacity = findViewById(R.id.etCapacity);
        etSerialNum = findViewById(R.id.etSerialNum);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        etType = findViewById(R.id.etType);
        etNumberOfAC = findViewById(R.id.etNumOfAC);
        this.nextLayout = findViewById(R.id.nextLayout);
        this.perviousLayout = findViewById(R.id.perviousLayout);
    }

    @Override
    protected void setClickListeners() {
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);
        nextLayout.setOnClickListener(this);
        perviousLayout.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    /**
     * Shared Prefrences
     */

    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("AC_Make", "");
        strModel = pref.getString("AC_Model", "");
        strCapacity = pref.getString("AC_Capacity", "");
        strMakeId = pref.getString("AC_MakeId", "");
        strModelId = pref.getString("AC_ModelId", "");
        strDescriptionId = pref.getString("AC_DescriptionId", "");
        strSerialNum = pref.getString("AC_SerialNum", "");
        strYearOfManufacturing = pref.getString("AC_YearOfManufacturing", "");
        strDescription = pref.getString("AC_Description", "");
        strPhoto1 = pref.getString("AC_Photo1", "");
        strPhoto2 = pref.getString("AC_Photo2", "");
        strSavedDateTime = pref.getString("AC_SavedDateTime", "");
        strType = pref.getString("AC_Type", "");
        strNumberOfAC = pref.getString("AC_Number", "");
        strSiteId = pref.getString("SiteId", "");
    }

    @Override
    protected void dataToView() {
        atmDatabase = new AtmDatabase(getContext());
        getSharedPrefData();
        arrEquipData = atmDatabase.getEquipmentMakeData("Desc", "AC");
        arrMake = new String[arrEquipData.size()];
        arrModel = new String[arrEquipData.size()];
        for (int i = 0; i < arrEquipData.size(); i++) {
            arrMake[i] = arrEquipData.get(i).getName();
        }

        ArrayAdapter<String> adapterMakeName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrMake);
        etMake.setAdapter(adapterMakeName);
        etMake.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String strMake = etMake.getText().toString();
                if (!strMake.equals("") && strMake.length() > 1) {
                    equipCapacityDataList = atmDatabase.getEquipmentCapacityData("DESC", strMake);
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
                    equipDescriptionDataList = atmDatabase.getEquipmentDescriptionData("DESC", strModel);
                    arrCapacity = new String[equipDescriptionDataList.size()];

                    if (equipDescriptionDataList.size() > 0) {
                        for (int i = 0; i < equipDescriptionDataList.size(); i++) {
                            arrCapacity[i] = equipDescriptionDataList.get(i).getName();
                        }

                        ArrayAdapter<String> adapterCapacityName = new ArrayAdapter<String>(
                                getContext(), android.R.layout.select_dialog_item, arrCapacity);
                        etCapacity.setAdapter(adapterCapacityName);
                    }
                }
            }
        });
        ASTUIUtil commonFunctions = new ASTUIUtil();
        final String currentDate = commonFunctions.getFormattedDate("dd/MM/yyyy", System.currentTimeMillis());
        if (!strMake.equals("") || !strModel.equals("") || !strCapacity.equals("") || !strSerialNum.equals("")
                || !strYearOfManufacturing.equals("") || !strDescription.equals("") || !strPhoto1.equals("")
                || !strPhoto2.equals("")) {
            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            etType.setText(strType);
            etNumberOfAC.setText(strNumberOfAC);

            if (!strPhoto1.equals("")) {
                //   image1.setText("Photo Selected");
                image1 = strPhoto1;
            }
            if (!strPhoto2.equals("")) {
                //   image2.setText("Photo Selected");
                image2 = strPhoto2;
            }
            arrEquipData = atmDatabase.getEquipmentMakeData("DESC", "DG");
            equipCapacityDataList = atmDatabase.getEquipmentCapacityData("DESC", strMake);
            equipDescriptionDataList = atmDatabase.getEquipmentDescriptionData("DESC", strModel);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.image1) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = true;
        } else if (view.getId() == R.id.image2) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
        } else if (view.getId() == R.id.imgNext || view.getId() == R.id.nextLayout) {
            if (isValidate()) {
                String newEquipment = "0";
                if (equipCapacityDataList.size() > 0) {
                    for (int i = 0; i < equipCapacityDataList.size(); i++) {
                        if (capacity.equals(equipCapacityDataList.get(i).getName())) {
                            strDescriptionId = equipCapacityDataList.get(i).getId();
                        }
                    }
                }

                if (strDescriptionId.equals("") || strDescriptionId.equals("0")) {
                    strDescriptionId = "0";
                }

                if (arrEquipData.size() > 0) {
                    for (int i = 0; i < arrEquipData.size(); i++) {
                        if (make.equals(arrEquipData.get(i).getName())) {
                            strMakeId = arrEquipData.get(i).getId();
                        }
                    }
                }
                if (strMakeId.equals("") || strMakeId.equals("0")) {
                    strMakeId = "0";
                }

                if (equipDescriptionDataList.size() > 0) {
                    for (int i = 0; i < equipDescriptionDataList.size(); i++) {
                        if (make.equals(equipDescriptionDataList.get(i).getName())) {
                            strModelId = equipDescriptionDataList.get(i).getId();
                        }
                    }
                }
                if (strModelId.equals("") || strModelId.equals("0")) {
                    strModelId = "0";
                }
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("AC_UserId", strUserId);
                editor.putString("AC_Make", make);
                editor.putString("AC_Model", model);
                editor.putString("AC_Capacity", capacity);
                editor.putString("AC_DescriptionId", strDescriptionId);
                editor.putString("AC_MakeId", strMakeId);
                editor.putString("AC_ModelId", strModelId);
                editor.putString("AC_SerialNum", serialNumber);
                editor.putString("AC_YearOfManufacturing", yearOfManufacturing);
                editor.putString("AC_Description", description);
                editor.putString("AC_Photo1", image1);
                editor.putString("AC_Photo2", image2);
                editor.putString("AC_SavedDateTime", currentDateTime);
                editor.putString("AC_Type", type);
                editor.putString("AC_Number", numOfACs);
                editor.commit();
                saveScreenData(true, false);
            }
        } else if (view.getId() == R.id.imgPrevious || view.getId() == R.id.perviousLayout) {
            saveScreenData(false, false);
        }

    }


    private void saveScreenData(boolean NextPreviousFlag, boolean DoneFlag) {
        Intent intent = new Intent("ViewPageChange");
        intent.putExtra("NextPreviousFlag", NextPreviousFlag);
        intent.putExtra("DoneFlag", DoneFlag);
        getActivity().sendBroadcast(intent);
    }


    public boolean isValidate() {
        make = etMake.getText().toString();
        model = etCapacity.getText().toString();
        capacity = etCapacity.getText().toString();
        serialNumber = etSerialNum.getText().toString();
        yearOfManufacturing = etYear.getText().toString();
        description = etDescription.getText().toString();
        currentDateTime = String.valueOf(System.currentTimeMillis());
        type = etType.getText().toString();
        numOfACs = etNumberOfAC.getText().toString();
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
        } else if (isEmptyStr(type)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Type");
            return false;
        } else if (isEmptyStr(numOfACs)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter AC Quantity");
            return false;
        } else if (isEmptyStr(image1)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Sticker Photo");
            return true;
        } else if (isEmptyStr(image2)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select AC Photo");
            return true;

        }
        return true;
    }

    public static void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (FNObjectUtil.isNonEmptyStr(deviceFile.getCompressFilePath())) {
                File compressPath = new File(deviceFile.getCompressFilePath());
                if (compressPath.exists()) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(isImage1 ? imageView1 : imageView2);
                }
            } else if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(isImage1 ? imageView1 : imageView2);
                if (deviceFile.isfromCamera() || deviceFile.isCropped()) {
                }
            }
        }

    }


    public static void getResult(ArrayList<MediaFile> files) {
        getPickedFiles(files);
    }

}