package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
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
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class InputAlarmPanelFragment extends MainFragment {

    static ImageView batteryimg, cellImg, sNoPlateImg;
    static String bateryphoto, cellPhoto, sNoPlatephoto;
    static boolean isImage1, isImage2;
    AppCompatEditText etYear, etDescription;
    AppCompatEditText etAnchorOperator, etSharingOperator;
    AppCompatAutoCompleteTextView etMake, etModel, etCapacity, etSerialNum;
    SharedPreferences pref;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription;
    String strSavedDateTime, strUserId, strSiteId, strDescriptionId, itemCondition;
    String strMakeId, strModelId;
    AtmDatabase atmDatabase;
    Spinner itemConditionSpinner;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime, AnchorOperator, SharingOperator;
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemStatusSpineer;

    @Override
    protected int fragmentLayout() {
        return R.layout.inputalarmpannel_fragment;
    }

    @Override
    protected void loadView() {
        btnSubmit = findViewById(R.id.btnSubmit);
        batteryimg = findViewById(R.id.image1);
        cellImg = findViewById(R.id.image2);
        sNoPlateImg = findViewById(R.id.image3);
        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
        etCapacity = findViewById(R.id.etCapacity);
        etSerialNum = findViewById(R.id.etSerialNum);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        etAnchorOperator = findViewById(R.id.etAnchorOperator);
        etSharingOperator = findViewById(R.id.etSharingOperator);
    }

    @Override
    protected void setClickListeners() {
        batteryimg.setOnClickListener(this);
        cellImg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }


    @Override
    protected void setAccessibility() {

    }

    @Override
    public void onResume() {
        super.onResume();
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
        getSharedprefData();
        setSpinnerValue();
        if (!strMake.equals("") || !strModel.equals("") || !strCapacity.equals("") || !strSerialNum.equals("")
                || !strYearOfManufacturing.equals("") || !strDescription.equals("")|| !AnchorOperator.equals("")
                || !SharingOperator.equals("")) {

            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            etAnchorOperator.setText(AnchorOperator);
            etSharingOperator.setText(SharingOperator);



            if (!bateryphoto.equals("") || !cellPhoto.equals("") || !sNoPlatephoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(bateryphoto)).placeholder(R.drawable.noimage).into(batteryimg);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(cellPhoto)).placeholder(R.drawable.noimage).into(cellImg);
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
                    batteryimg.setEnabled(false);
                    cellImg.setEnabled(false);
                    sNoPlateImg.setEnabled(false);
                    etMake.setEnabled(false);
                    etModel.setEnabled(false);
                    etCapacity.setEnabled(false);
                    etSerialNum.setEnabled(false);
                    etYear.setEnabled(false);
                    etDescription.setEnabled(false);
                    itemConditionSpinner.setEnabled(false);
                    descriptionLayout.setEnabled(false);
                    etAnchorOperator.setEnabled(false);
                            etSharingOperator.setEnabled(false);

                } else {
                    batteryimg.setEnabled(true);
                    cellImg.setEnabled(true);
                    sNoPlateImg.setEnabled(true);
                    etMake.setEnabled(true);
                    etModel.setEnabled(true);
                    etCapacity.setEnabled(true);
                    etSerialNum.setEnabled(true);
                    etYear.setEnabled(true);
                    etDescription.setEnabled(true);
                    itemConditionSpinner.setEnabled(true);
                    descriptionLayout.setEnabled(true);
                    etAnchorOperator.setEnabled(true);
                    etSharingOperator.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /*
     *
     *     Shared Prefrences
     */
    public void getSharedprefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("ALARMPA_Make", "");
        strModel = pref.getString("ALARMPA_Model", "");
        strCapacity = pref.getString("ALARMPA_Capacity", "");
        strMakeId = pref.getString("ALARMPA_MakeId", "");
        strModelId = pref.getString("ALARMPA_ModelId", "");
        strDescriptionId = pref.getString("ALARMPA_DescriptionId", "");
        strSerialNum = pref.getString("ALARMPA_SerialNum", "");
        strYearOfManufacturing = pref.getString("ALARMPA_YearOfManufacturing", "");
        strDescription = pref.getString("ALARMPA_Description", "");
        bateryphoto = pref.getString("ALARMPA_batryPhoto1", "");
        cellPhoto = pref.getString("ALARMPA_batryPhoto2", "");
        sNoPlatephoto = pref.getString("ALARMPA_batryPhoto3", "");
        strSavedDateTime = pref.getString("ALARMPA_BbActivitySavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");
        itemCondition = pref.getString("ALARMPA_ItemCondition", "");

        AnchorOperator = pref.getString("ALARMPA_AnchorOperator", "");
        SharingOperator = pref.getString("ALARMPA_SharingOperator", "");


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
                if (strModelId.equals("") || strModelId.equals("0")) {
                    strModelId = "0";
                }
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("USER_ID", strUserId);
                editor.putString("ALARMPA_Make", make);
                editor.putString("ALARMPA_Model", model);
                editor.putString("ALARMPA_Capacity", capacity);
                editor.putString("ALARMPA_DescriptionId", strDescriptionId);
                editor.putString("ALARMPA_MakeId", strMakeId);
                editor.putString("ALARMPA_ModelId", strModelId);
                editor.putString("ALARMPA_SerialNum", serialNumber);
                editor.putString("ALARMPA_YearOfManufacturing", yearOfManufacturing);
                editor.putString("ALARMPA_Description", description);
                editor.putString("ALARMPA_batryPhoto1", bateryphoto);
                editor.putString("ALARMPA_batryPhoto2", cellPhoto);
                editor.putString("ALARMPA_batryPhoto3", sNoPlatephoto);
                editor.putString("BbActivitySavedDateTime", currentDateTime);
                editor.putString("ALARMPA_ItemCondition", itemCondition);
                editor.putString("ALARMPA_AnchorOperator", AnchorOperator);
                editor.putString("ALARMPA_SharingOperator", SharingOperator);

                strModelId = pref.getString("", "");
                editor.commit();
                reloadBackScreen();

            }

        }
    }


    // ----validation -----
    private boolean isValidate() {
        make = getTextFromView(this.etMake);
        model = getTextFromView(this.etCapacity);
        capacity = getTextFromView(this.etCapacity);
        serialNumber = getTextFromView(this.etSerialNum);

        AnchorOperator = getTextFromView(this.etAnchorOperator);
        SharingOperator = getTextFromView(this.etSharingOperator);

        yearOfManufacturing = getTextFromView(this.etYear);
        itemCondition = itemConditionSpinner.getSelectedItem().toString();
        description = getTextFromView(this.etDescription);
        currentDateTime = String.valueOf(System.currentTimeMillis());



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

            } else if (isEmptyStr(itemCondition)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Item Condition");
                return false;
            } else if (isEmptyStr(yearOfManufacturing)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Manufacturing Year");
                return false;
            } else if (isEmptyStr(description)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
                return false;
            } else if (isEmptyStr(bateryphoto)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Front Photo");
                return false;
            } else if (isEmptyStr(cellPhoto)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Open Photo");
                return false;
            } else if (isEmptyStr(sNoPlatephoto)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Sr no Plate Photo");
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
                        bateryphoto = deviceFile.getFilePath().toString();
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(batteryimg);
                    } else if (isImage2) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(cellImg);
                        cellPhoto = deviceFile.getFilePath().toString();

                    } else {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(sNoPlateImg);
                        sNoPlatephoto = deviceFile.getFilePath().toString();
                    }
                    //compressPath.delete();
                }
            } else if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(batteryimg);
                    bateryphoto = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(cellImg);
                    cellPhoto = deviceFile.getFilePath().toString();
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