package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class IpmsFragment extends MainFragment {

    static ImageView frontImg, openImg, sNoPlateImg;
    static boolean isImage1, isImage2;
    static String frontphoto, openPhoto, sNoPlatephoto;
    AppCompatEditText etYear, etDescription ;
    AppCompatAutoCompleteTextView etSerialNum, etCapacity, etMake,etnoofModule, etLcuCapacity,etModel,etModuleCapacity;
    SharedPreferences pref;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription, strnoofModule, strModuleCapacity, strlcucapacity;
    String strSavedDateTime, strUserId, strSiteId;
    String strMakeId, strModelId, strDescriptionId, nofModule, ModuleCapacity;
    String[] arrMake;
    String[] arrModel;
    String[] arrCapacity;
    ArrayList<EquipMakeDataModel> arrEquipData;
    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    AtmDatabase atmDatabase;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime, lcucapacity;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner;
    Button btnSubmit;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_ipms;
    }

    @Override
    protected void loadView() {
        frontImg = findViewById(R.id.image1);
        openImg = findViewById(R.id.image2);
        sNoPlateImg = findViewById(R.id.image3);
        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
        etCapacity = findViewById(R.id.etCapacity);
        etSerialNum = findViewById(R.id.etSerialNum);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        etnoofModule = findViewById(R.id.etnoofModule);
        etModuleCapacity = findViewById(R.id.etModuleCapacity);
        etLcuCapacity = findViewById(R.id.etLcuCapacity);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        openImg.setOnClickListener(this);
        frontImg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    protected void setAccessibility() {

    }

    /*
     *
     * Shared Prefrences---------------------------------------
     */

    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("IPMS_Make", "");
        strModel = pref.getString("SMP_Model", "");
        strCapacity = pref.getString("IPMS_Capacity", "");
        strMakeId = pref.getString("IPMS_MakeId", "");
        strModelId = pref.getString("IPMS_ModelId", "");
        strDescriptionId = pref.getString("IPMS_DescriptionId", "");
        strSerialNum = pref.getString("IPMS_SerialNum", "");
        strYearOfManufacturing = pref.getString("IPMS_YearOfManufacturing", "");
        strDescription = pref.getString("IPMS_Description", "");
        strModuleCapacity = pref.getString("IPMS_ModuleCapacity", "");
        strlcucapacity = pref.getString("IPMS_Llcucapacity", "");
        strnoofModule = pref.getString("IPMS_noofModule", "");
        frontphoto = pref.getString("IPMS_Photo1", "");
        openPhoto = pref.getString("IPMS__Photo2", "");
        sNoPlatephoto = pref.getString("IPMS_Photo3", "");
        strSavedDateTime = pref.getString("IPMS_SavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");
    }

    public void setSpinnerValue() {
        final String itemCondition_array[] = {"Ok", "Not Ok", "Fully Fault"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(homeadapter);

    }

    @Override
    protected void dataToView() {
        atmDatabase = new AtmDatabase(getContext());
        getSharedPrefData();
        setSpinnerValue();
        arrEquipData = atmDatabase.getEquipmentMakeData("Desc", "SMPS");
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

                    if (equipCapacityDataList != null && equipCapacityDataList.size() > 0) {
                        for (int i = 0; i < equipCapacityDataList.size(); i++) {
                            arrModel[i] = equipCapacityDataList.get(i).getName();
                        }

                        ArrayAdapter<String> adapterModelName = new ArrayAdapter<String>(
                                getContext(), android.R.layout.select_dialog_item, arrModel);
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

                    if (equipDescriptionDataList != null && equipDescriptionDataList.size() > 0) {
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
                || !strYearOfManufacturing.equals("") || !strDescription.equals("") || !strlcucapacity.equals("")) {
            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            etLcuCapacity.setText(strlcucapacity);
            arrEquipData = atmDatabase.getEquipmentMakeData("DESC", "DG");
            equipCapacityDataList = atmDatabase.getEquipmentCapacityData("DESC", strMake);
            equipDescriptionDataList = atmDatabase.getEquipmentDescriptionData("DESC", strModel);
            if (!frontphoto.equals("") || !openPhoto.equals("") || !sNoPlatephoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(frontphoto)).placeholder(R.drawable.noimage).into(frontImg);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(openPhoto)).placeholder(R.drawable.noimage).into(openImg);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(sNoPlatephoto)).placeholder(R.drawable.noimage).into(sNoPlateImg);
            }
        }
        itemConditionSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                descriptionLayout.setVisibility(selectedItem.equalsIgnoreCase("Fully Fault") ? View.VISIBLE : View.GONE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.image1) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = true;
            isImage2 = false;
        } else if (view.getId() == R.id.image2) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = true;
        } else if (view.getId() == R.id.image3) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = false;
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                String newEquipment = "0";
                if (equipCapacityDataList != null && equipCapacityDataList.size() > 0) {
                    for (int i = 0; i < equipCapacityDataList.size(); i++) {
                        if (capacity.equals(equipCapacityDataList.get(i).getName())) {
                            strDescriptionId = equipCapacityDataList.get(i).getId();
                        }
                    }
                }
                if (strDescriptionId.equals("") || strDescriptionId.equals("0")) {
                    strDescriptionId = "0";
                }
                if (arrEquipData != null && arrEquipData.size() > 0) {
                    for (int i = 0; i < arrEquipData.size(); i++) {
                        if (make.equals(arrEquipData.get(i).getName())) {
                            strMakeId = arrEquipData.get(i).getId();
                        }
                    }
                }
                if (strMakeId.equals("") || strMakeId.equals("0")) {
                    strMakeId = "0";
                }
                if (equipDescriptionDataList != null && equipDescriptionDataList.size() > 0) {
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
                editor.putString("IPMS_UserId", strUserId);
                editor.putString("IPMS_Make", make);
                editor.putString("IPMS_Model", model);
                editor.putString("IPMS_Capacity", capacity);
                editor.putString("IPMS_DescriptionId", strDescriptionId);
                editor.putString("IPMS_MakeId", strMakeId);
                editor.putString("IPMS_ModelId", strModelId);
                editor.putString("IPMS_SerialNum", serialNumber);
                editor.putString("IPMS_YearOfManufacturing", yearOfManufacturing);
                editor.putString("IPMS_Description", description);
                editor.putString("IPMS_ModuleCapacity", ModuleCapacity);
                editor.putString("IPMS_Llcucapacity", lcucapacity);
                editor.putString("IPMS_noofModule", nofModule);
                editor.putString("IPMS_Photo1", frontphoto);
                editor.putString("IPMS_Photo2", openPhoto);
                editor.putString("IPMS_Photo3", sNoPlatephoto);
                editor.putString("IPMS_SavedDateTime", currentDateTime);
                editor.commit();
            }


        }
    }


    public boolean isValidate() {
        make = etMake.getText().toString();
        model = etCapacity.getText().toString();
        capacity = etCapacity.getText().toString();
        serialNumber = etSerialNum.getText().toString();
        yearOfManufacturing = etYear.getText().toString();
        description = etDescription.getText().toString();
        nofModule = etnoofModule.getText().toString();
        ModuleCapacity = etModuleCapacity.getText().toString();
        lcucapacity = etLcuCapacity.getText().toString();
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
        } else if (isEmptyStr(lcucapacity)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter LCU Capacity");
            return false;
        } else if (isEmptyStr(frontphoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Set Plate Photo");
            return false;
        } else if (isEmptyStr(openPhoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Outside Photo");
            return false;
        } else if (isEmptyStr(sNoPlatephoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Sr no Plate Photo");
            return false;
        } else if (isEmptyStr(nofModule)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Nof Of Modules");
            return false;
        } else if (isEmptyStr(ModuleCapacity)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Module Capacity");
            return false;
        }
        return true;
    }

    public static void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (FNObjectUtil.isNonEmptyStr(deviceFile.getCompressFilePath())) {
                File compressPath = new File(deviceFile.getCompressFilePath());
                if (compressPath.exists()) {

                    if (isImage1) {
                        frontphoto = deviceFile.getFilePath().toString();
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(frontImg);
                    } else if (isImage2) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(openImg);
                        openPhoto = deviceFile.getFilePath().toString();

                    } else {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(sNoPlateImg);
                        sNoPlatephoto = deviceFile.getFilePath().toString();
                    }
                    //compressPath.delete();
                }
            } else if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1) {
                    frontphoto = deviceFile.getFilePath().toString();
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(frontImg);
                } else if (isImage2) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(openImg);
                    openPhoto = deviceFile.getFilePath().toString();
                } else {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(sNoPlateImg);
                    sNoPlatephoto = deviceFile.getFilePath().toString();
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
