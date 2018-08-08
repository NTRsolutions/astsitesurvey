package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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

public class PIUVoltageStablizerFragment extends MainFragment {
    TextView imgPrevious, imgNext;
    LinearLayout perviousLayout, nextLayout;
    static ImageView frontImg, openImg, sNoPlateImg;
    static boolean isImage1, isImage2;
    static String frontphoto, openPhoto, sNoPlatephoto;
    AppCompatEditText etYear, etDescription, etetNofLcu;
    AppCompatAutoCompleteTextView etCapacity, etMake, etModel, etSerialNum;
    SharedPreferences pref;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription, stretNofLcu;
    String strSavedDateTime, strUserId, strSiteId;
    String strMakeId, strModelId, strDescriptionId, NofLcu;
    String[] arrMake;
    String[] arrModel;
    String[] arrCapacity;
    ArrayList<EquipMakeDataModel> arrEquipData;
    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    AtmDatabase atmDatabase;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner;
    AppCompatEditText etController, etConditionbackPlane, etBodyEarthing, etPositiveEarthing, etRatingofCable, etAlarmConnection,
            etNoofRMWorking, etNoofRMFaulty, etSpareFuseStatus;

    Spinner itemStatusSpineer;
    String Controller, ConditionbackPlane, BodyEarthing, PositiveEarthing, RatingofCable, AlarmConnection,
            NoofRMWorking, NoofRMFaulty, SpareFuseStatus, itemStatus;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_piuvoltage_stablizer;
    }

    @Override
    protected void loadView() {
        imgNext = findViewById(R.id.imgNext);
        imgPrevious = findViewById(R.id.imgPrevious);
        frontImg = findViewById(R.id.image1);
        openImg = findViewById(R.id.image2);
        sNoPlateImg = findViewById(R.id.image3);
        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
        etCapacity = findViewById(R.id.etCapacity);
        etSerialNum = findViewById(R.id.etSerialNum);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        this.nextLayout = findViewById(R.id.nextLayout);
        this.perviousLayout = findViewById(R.id.nextLayout);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        etetNofLcu = findViewById(R.id.etNofLcu);

        etController = findViewById(R.id.etController);
        etConditionbackPlane = findViewById(R.id.etConditionbackPlane);
        etBodyEarthing = findViewById(R.id.etBodyEarthing);
        etPositiveEarthing = findViewById(R.id.etPositiveEarthing);
        etRatingofCable = findViewById(R.id.etRatingofCable);
        etAlarmConnection = findViewById(R.id.etAlarmConnection);
        etNoofRMWorking = findViewById(R.id.etNoofRMWorking);
        etNoofRMFaulty = findViewById(R.id.etNoofRMFaulty);
        etSpareFuseStatus = findViewById(R.id.etSpareFuseStatus);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
    }

    @Override
    protected void setClickListeners() {
        openImg.setOnClickListener(this);
        frontImg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        imgNext.setOnClickListener((this));
        imgPrevious.setOnClickListener((this));
        nextLayout.setOnClickListener(this);
        perviousLayout.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("PIU_Make", "");
        strModel = pref.getString("PIU_Model", "");
        strCapacity = pref.getString("PIU_Capacity", "");
        strMakeId = pref.getString("_PIU_MakeId", "");
        strModelId = pref.getString("PIU_ModelId", "");
        strDescriptionId = pref.getString("PIU_DescriptionId", "");
        strSerialNum = pref.getString("PIU_SerialNum", "");
        strYearOfManufacturing = pref.getString("PIU_YearOfManufacturing", "");
        strDescription = pref.getString("PIU_Description", "");
        stretNofLcu = pref.getString("noofLcu", "");
        frontphoto = pref.getString("PIU_Photo1", "");
        openPhoto = pref.getString("PIU_Photo2", "");
        sNoPlatephoto = pref.getString("PIU_Photo3", "");

        Controller = pref.getString("PIU_Controller", "");
        ConditionbackPlane = pref.getString("PIU_ConditionbackPlane", "");
        BodyEarthing = pref.getString("PIU_BodyEarthing", "");
        PositiveEarthing = pref.getString("PIU_PositiveEarthing", "");
        RatingofCable = pref.getString("PIU_RatingofCable", "");
        AlarmConnection = pref.getString("PIU_AlarmConnection", "");
        NoofRMWorking = pref.getString("PIU_NoofRMWorking", "");
        NoofRMFaulty = pref.getString("PIU_NoofRMFaulty", "");
        SpareFuseStatus = pref.getString("PIU_SpareFuseStatus", "");
        itemStatus = pref.getString("PIU_itemStatus", "");


        strSavedDateTime = pref.getString("PIU_SavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");
    }

    public void setSpinnerValue() {
        final String itemCondition_array[] = {"Ok", "Not Ok", "Fully Fault"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(homeadapter);

        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatusSpineeradapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatusSpineeradapter);

    }

    @Override
    protected void dataToView() {
        atmDatabase = new AtmDatabase(getContext());
        getSharedPrefData();
        setSpinnerValue();
        arrEquipData = atmDatabase.getEquipmentMakeData("Desc", "PIU");
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
                || !strYearOfManufacturing.equals("")
                || !strDescription.equals("") || !Controller.equals("")
                || !ConditionbackPlane.equals("")
                || !BodyEarthing.equals("")
                || !PositiveEarthing.equals("")
                || !AlarmConnection.equals("")
                || !NoofRMWorking.equals("")
                || !NoofRMFaulty.equals("")
                || !SpareFuseStatus.equals("")) {


            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);

            etController.setText(Controller);
            etConditionbackPlane.setText(strDescription);
            etBodyEarthing.setText(BodyEarthing);
            etPositiveEarthing.setText(PositiveEarthing);
            etRatingofCable.setText(RatingofCable);
            etAlarmConnection.setText(AlarmConnection);
            etNoofRMWorking.setText(NoofRMWorking);
            etNoofRMFaulty.setText(NoofRMFaulty);
            etSpareFuseStatus.setText(SpareFuseStatus);
            itemStatus = itemStatusSpineer.getSelectedItem().toString();
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
            isImage1 = true;
            isImage2 = false;
            ASTUIUtil.startImagePicker(getHostActivity());
        } else if (view.getId() == R.id.image2) {
            isImage1 = false;
            isImage2 = true;
            ASTUIUtil.startImagePicker(getHostActivity());
        } else if (view.getId() == R.id.image3) {
            isImage1 = false;
            isImage2 = false;
            ASTUIUtil.startImagePicker(getHostActivity());
        } else if (view.getId() == R.id.imgNext || view.getId() == R.id.nextLayout) {
            if (isValiDate()) {
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
                editor.putString("PIU_UserId", strUserId);
                editor.putString("PIU_Make", make);
                editor.putString("PIU_Model", model);
                editor.putString("PIU_Capacity", capacity);
                editor.putString("PIU_DescriptionId", strDescriptionId);
                editor.putString("PIU_MakeId", strMakeId);
                editor.putString("PIU_ModelId", strModelId);
                editor.putString("PIU_SerialNum", serialNumber);
                editor.putString("PIU_YearOfManufacturing", yearOfManufacturing);
                editor.putString("PIU_Description", description);
                editor.putString("noofLcu", NofLcu);
                editor.putString("PIU_Photo1", frontphoto);
                editor.putString("PIU_Photo2", openPhoto);
                editor.putString("PIU_Photo3", sNoPlatephoto);
                editor.putString("PIU_SavedDateTime", currentDateTime);

                editor.putString("PIU__Controller", Controller);
                editor.putString("PIU_ConditionbackPlane", ConditionbackPlane);
                editor.putString("PIU_BodyEarthing", BodyEarthing);
                editor.putString("PIU_PositiveEarthing", PositiveEarthing);
                editor.putString("PIU_RatingofCable", RatingofCable);
                editor.putString("PIU_AlarmConnection", AlarmConnection);
                editor.putString("PIU_NoofRMWorking", NoofRMWorking);
                editor.putString("PIU_NoofRMFaulty", NoofRMFaulty);
                editor.putString("PIU_SpareFuseStatus", SpareFuseStatus);
                editor.putString("PIU_itemStatus", itemStatus);

                editor.commit();
                saveScreenData(false, true);
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


    public boolean isValiDate() {
        make = etMake.getText().toString();
        model = etCapacity.getText().toString();
        capacity = etCapacity.getText().toString();
        serialNumber = etSerialNum.getText().toString();
        yearOfManufacturing = etYear.getText().toString();
        description = etDescription.getText().toString();
        currentDateTime = String.valueOf(System.currentTimeMillis());

        Controller = etController.getText().toString();
        ConditionbackPlane = etConditionbackPlane.getText().toString();
        BodyEarthing = etBodyEarthing.getText().toString();
        PositiveEarthing = etPositiveEarthing.getText().toString();
        RatingofCable = etRatingofCable.getText().toString();
        AlarmConnection = etAlarmConnection.getText().toString();
        NoofRMWorking = etNoofRMWorking.getText().toString();
        NoofRMFaulty = etNoofRMFaulty.getText().toString();
        SpareFuseStatus = etSpareFuseStatus.getText().toString();
        itemStatus= itemStatusSpineer.getSelectedItem().toString();
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
        } else if (isEmptyStr(frontphoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Set Plate Photo");
            return false;
        } else if (isEmptyStr(openPhoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Outside Photo");
            return false;
        } else if (isEmptyStr(sNoPlatephoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Sr no Plate Photo");
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
