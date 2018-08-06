package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MpptFragment extends MainFragment {
    static ImageView frontImg, openImg, sNoPlateImg;
    static boolean isImage1, isImage2;
    static String frontphoto, openPhoto, sNoPlatephoto;
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner;
    String strUserId, strSavedDateTime, strSiteId;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime;
    SharedPreferences pref;
    AutoCompleteTextView etCapacity, etMake, etSerialNum, etModel;
    AppCompatEditText etYear, etDescription;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription, strType;
    String strMakeId, strModelId, strDescriptionId;
    Spinner itemStatusSpineer;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_mppt;
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
        btnSubmit = findViewById(R.id.btnSubmit);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
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

    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("MPPT_Make", "");
        strModel = pref.getString("MPPT_Model", "");
        strCapacity = pref.getString("MPPT_Capacity", "");
        strMakeId = pref.getString("MPPT_MakeId", "");
        strModelId = pref.getString("MPPT_ModelId", "");
        strDescriptionId = pref.getString("MPPT_DescriptionId", "");
        strSerialNum = pref.getString("MPPT_SerialNum", "");
        strYearOfManufacturing = pref.getString("MPPT_YearOfManufacturing", "");
        strDescription = pref.getString("MPPT_Description", "");
        frontphoto = pref.getString("MPPT_Photo1", "");
        openPhoto = pref.getString("MPPT_Photo2", "");
        sNoPlatephoto = pref.getString("MPPT_Photo3", "");
        strSavedDateTime = pref.getString("MPPT_EbMeterSavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");
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
        getSharedPrefData();
        setSpinnerValue();

        if (!strMake.equals("") || !strModel.equals("") || !strCapacity.equals("") || !strSerialNum.equals("")
                || !strYearOfManufacturing.equals("") || !strDescription.equals("")) {
            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
        }
        if (!frontphoto.equals("") || !openPhoto.equals("") || !sNoPlatephoto.equals("")) {
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(frontphoto)).placeholder(R.drawable.noimage).into(frontImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(openPhoto)).placeholder(R.drawable.noimage).into(openImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(sNoPlatephoto)).placeholder(R.drawable.noimage).into(sNoPlateImg);
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
                    itemConditionSpinner.setEnabled(false);
                    descriptionLayout.setEnabled(false);
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
                    itemConditionSpinner.setEnabled(true);
                    descriptionLayout.setEnabled(true);
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
            isImage1 = false;
            isImage2 = true;
        } else if (view.getId() == R.id.image3) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = false;
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("MPPT_UserId", strUserId);
                editor.putString("MPPT_Make", make);
                editor.putString("MPPT_Model", model);
                editor.putString("MPPT_Capacity", capacity);
                editor.putString("MPPT_DescriptionId", strDescriptionId);
                editor.putString("MPPT_MakeId", strMakeId);
                editor.putString("MPPT_ModelId", strModelId);
                editor.putString("MPPT_SerialNum", serialNumber);
                editor.putString("MPPT_YearOfManufacturing", yearOfManufacturing);
                editor.putString("MPPT_Description", description);
                editor.putString("MPPT_Photo1", frontphoto);
                editor.putString("MPPT_Photo2", openPhoto);
                editor.putString("MPPT_Photo3", sNoPlatephoto);
                editor.putString("MPPT_EbMeterSavedDateTime", currentDateTime);
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
            } else if (isEmptyStr(yearOfManufacturing)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Manufacturing Year");
                return false;
            } else if (isEmptyStr(description)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
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
