package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipDescriptionDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class AirConditionerFragment extends MainFragment {
    static ImageView frontImg, openImg, sNoPlateImg;
    static boolean isImage1, isImage2;
    static String frontphoto, openPhoto, sNoPlatephoto;
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner;
    AppCompatEditText etYear, etDescription, etNumberOfAC, etnoAc,
            etaCType, etacACWorkingCondition, etCompresserStatus, etACAlarms, etSocketandPlug;
    AutoCompleteTextView etCapacity, etMake, etModel, etSerialNum;
    SharedPreferences pref;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription, strType, strNumberOfAC;
    String strSavedDateTime, strUserId, strSiteId, sNoAC;
    String strMakeId, strModelId, strDescriptionId;
    String[] arrMake;
    String[] arrModel;
    String[] arrCapacity;
    ArrayList<EquipMakeDataModel> arrEquipData;
    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    AtmDatabase atmDatabase;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime, numOfACs, sNAC;
    Spinner itemStatusSpineer;

    String aCType, acACWorkingCondition, CompresserStatus, ACAlarms, SocketandPlug;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_air_conditioner;
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
        etNumberOfAC = findViewById(R.id.etNumOfAC);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        btnSubmit = findViewById(R.id.btnSubmit);
        etnoAc = findViewById(R.id.noAc);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        etaCType = findViewById(R.id.etaCType);
        etacACWorkingCondition = findViewById(R.id.etacACWorkingCondition);
        etCompresserStatus = findViewById(R.id.etCompresserStatus);
        etACAlarms = findViewById(R.id.etACAlarms);
        etSocketandPlug = findViewById(R.id.etSocketandPlug);
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

    /**
     * Shared Prefrences
     */

    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
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
        frontphoto = pref.getString("AC_Photo1", "");
        openPhoto = pref.getString("AC_Photo2", "");
        sNoPlatephoto = pref.getString("AC_Photo3", "");
        strSavedDateTime = pref.getString("AC_SavedDateTime", "");
        strType = pref.getString("AC_Type", "");
        strNumberOfAC = pref.getString("AC_Number", "");
        strSiteId = pref.getString("SiteId", "");
        aCType = pref.getString("AC_aCType", "");
        acACWorkingCondition = pref.getString("AC_acACWorkingCondition", "");
        CompresserStatus = pref.getString("AC_CompresserStatus", "");
        ACAlarms = pref.getString("AC_ACAlarms", "");
        SocketandPlug = pref.getString("AC_SocketandPlug", "");
        sNoAC = pref.getString("sNoAC", "");
    }

    public void setSpinnerValue() {
        final String itemCondition_array[] = {"Ok", "Not Ok", "Fully Fault"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(homeadapter);

        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);

    }

    @Override
    protected void dataToView() {
        atmDatabase = new AtmDatabase(getContext());
        getSharedPrefData();
        setSpinnerValue();
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
                || !strYearOfManufacturing.equals("") || !strDescription.equals("") || !sNoAC.equals("")
                || !sNoAC.equals("")
                || !acACWorkingCondition.equals("")
                || !CompresserStatus.equals("")
                || !ACAlarms.equals("")
                || !SocketandPlug.equals("")
                ) {

            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            etNumberOfAC.setText(strNumberOfAC);
            etnoAc.setText(sNoAC);

            etaCType.setText(aCType);
            etacACWorkingCondition.setText(acACWorkingCondition);
            etCompresserStatus.setText(CompresserStatus);
            etACAlarms.setText(ACAlarms);
            etSocketandPlug.setText(SocketandPlug);


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

        itemStatusSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Not Available")) {
                    frontImg.setEnabled(false);
                    openImg.setEnabled(false);
                    sNoPlateImg.setEnabled(false);
                    etMake.setEnabled(false);
                    etModel.setEnabled(false);
                    etCapacity.setEnabled(false);
                    etSerialNum.setEnabled(false);
                    etYear.setEnabled(false);
                    etDescription.setEnabled(false);
                    etNumberOfAC.setEnabled(false);
                    itemConditionSpinner.setEnabled(false);
                    descriptionLayout.setEnabled(false);
                    etnoAc.setEnabled(false);
                    etaCType.setEnabled(false);
                    etacACWorkingCondition.setEnabled(false);
                    etCompresserStatus.setEnabled(false);
                    etACAlarms.setEnabled(false);
                    etSocketandPlug.setEnabled(false);

                } else {
                    frontImg.setEnabled(true);
                    openImg.setEnabled(true);
                    sNoPlateImg.setEnabled(true);
                    etMake.setEnabled(true);
                    etModel.setEnabled(true);
                    etCapacity.setEnabled(true);
                    etSerialNum.setEnabled(true);
                    etYear.setEnabled(true);
                    etDescription.setEnabled(true);
                    etNumberOfAC.setEnabled(true);
                    itemConditionSpinner.setEnabled(true);
                    descriptionLayout.setEnabled(true);
                    etnoAc.setEnabled(true);
                    etaCType.setEnabled(true);
                    etacACWorkingCondition.setEnabled(true);
                    etCompresserStatus.setEnabled(true);
                    etACAlarms.setEnabled(true);
                    etSocketandPlug.setEnabled(true);
                }
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
            isImage2 = true;
            isImage1 = false;
        } else if (view.getId() == R.id.image3) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage2 = false;
            isImage1 = false;
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
                editor.putString("AC_Photo1", frontphoto);
                editor.putString("AC_Photo2", openPhoto);
                editor.putString("AC_Photo3", sNoPlatephoto);
                editor.putString("AC_SavedDateTime", currentDateTime);
                editor.putString("AC_Number", numOfACs);
                editor.putString("sNoAC", sNAC);

                editor.putString("AC_aCType", aCType);
                editor.putString("AC_acACWorkingCondition", acACWorkingCondition);
                editor.putString("AC_CompresserStatus", CompresserStatus);
                editor.putString("AC_ACAlarms", ACAlarms);
                editor.putString("AC_SocketandPlug", SocketandPlug);

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
        currentDateTime = String.valueOf(System.currentTimeMillis());
        numOfACs = etNumberOfAC.getText().toString();
        aCType = etaCType.getText().toString();
        acACWorkingCondition = etacACWorkingCondition.getText().toString();
        CompresserStatus = etCompresserStatus.getText().toString();
        ACAlarms = etACAlarms.getText().toString();
        SocketandPlug = etSocketandPlug.getText().toString();


        sNAC = etnoAc.getText().toString();
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
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
            } else if (isEmptyStr(numOfACs)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter AC Quantity");
                return false;
            } else if (isEmptyStr(frontphoto)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Front Photo");
                return false;
            } else if (isEmptyStr(openPhoto)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Open Photo");
                return false;

            } else if (isEmptyStr(sNoPlatephoto)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Sr no Plate Photo");
                return false;
            } else if (isEmptyStr(sNAC)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No Ac");
                return false;
            }
        } else {
            ASTUIUtil.showToast("Item Not Available");
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
}
