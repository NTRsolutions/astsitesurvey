package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
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
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class EBMeterFragment extends MainFragment {
    static ImageView frontImg, openImg, sNoPlateImg;
    static boolean isImage1, isImage2;
    static String frontphoto, openPhoto, sNoPlatephoto;
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner, meeterTypeSpinner, powerTypeSpinner, transformerTypeSpinner, MeterstatusSpinner;
    String strUserId, strSavedDateTime, meterreading, strSiteId;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, type, currentDateTime;

    SharedPreferences pref;
    AppCompatEditText etCapacity, etMake, etModel, etSerialNum,
            etConnectionNo, etCableRating, etALTHTConnection, etTransformerEarthing, etmccbStatus,etkitkatChangeover, etTheftfromSite;
    AppCompatEditText etYear, etDescription, ebMeterreading;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription;
    String strMakeId, strModelId, strDescriptionId;
    Spinner itemStatusSpineer;
    String ConnectionNo, CableRating, ALTHTConnection, TransformerEarthing, mccbStatus, kitkatChangeover,TheftfromSite,
            strmeeterTypeSpinner, strpowerTypeSpinner, strtransformerTypeSpinner, strMeterstatusSpinner;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_eb_meter;
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
        ebMeterreading = findViewById(R.id.ebMeterreading);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);

        etConnectionNo = findViewById(R.id.etConnectionNo);
        etCableRating = findViewById(R.id.etCableRating);
        etALTHTConnection = findViewById(R.id.etALTHTConnection);
        etTransformerEarthing = findViewById(R.id.etTransformerEarthing);
        etmccbStatus = findViewById(R.id.etmccbStatus);
        etkitkatChangeover = findViewById(R.id.etmcckitkatChangeover);

        etTheftfromSite = findViewById(R.id.etTheftfromSite);
        meeterTypeSpinner = findViewById(R.id.meeterTypeSpinner);
        powerTypeSpinner = findViewById(R.id.powerTypeSpinner);
        transformerTypeSpinner = findViewById(R.id.transformerTypeSpinner);
        MeterstatusSpinner = findViewById(R.id.MeterstatusSpinner);
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
        strMake = pref.getString("EBM_Make", "");
        strModel = pref.getString("EBM_Model", "");
        strCapacity = pref.getString("EBM_Capacity", "");
        strMakeId = pref.getString("EBM_MakeId", "");
        strModelId = pref.getString("EBM_ModelId", "");
        strDescriptionId = pref.getString("EBM_DescriptionId", "");
        strSerialNum = pref.getString("EBM_SerialNum", "");
        strYearOfManufacturing = pref.getString("EBM_YearOfManufacturing", "");
        strDescription = pref.getString("EBM_Description", "");
        frontphoto = pref.getString("EBM_Photo1", "");
        openPhoto = pref.getString("EBM_Photo2", "");
        sNoPlatephoto = pref.getString("EBM_Photo3", "");
        strSavedDateTime = pref.getString("EbMeterSavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");
        meterreading = pref.getString("Meterreading", "");
        ConnectionNo = pref.getString("EbMeterConnectionNo", "");
        CableRating = pref.getString("EbMeterCableRating", "");
        ALTHTConnection = pref.getString("EbMeterALTHTConnection", "");
        TransformerEarthing = pref.getString("EbMeterTransformerEarthing", "");
        mccbStatus = pref.getString("EbMetermccbStatus", "");

        kitkatChangeover= pref.getString("EbMeterkitkatChangeover", "");
        TheftfromSite = pref.getString("EbMeterTheftfromSite", "");
        strmeeterTypeSpinner = pref.getString("EbMeterstrmeeterTypeSpinner", "");
        strpowerTypeSpinner = pref.getString("EbMeterstrpowerTypeSpinner", "");
        strtransformerTypeSpinner = pref.getString("EbMeterstrtransformerTypeSpinner", "");
        strMeterstatusSpinner = pref.getString("EbMeterstrMeterstatusSpinner", "");


    }

    public void setSpinnerValue() {
        final String itemCondition_array[] = {"Ok", "Not Ok", "Fully Fault"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(homeadapter);

        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);


        final String meeterTypeSpinnerArray[] = {"Single Phase", "3 Phase"};
        ArrayAdapter<String> meeterTypeSpinnerArrayad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, meeterTypeSpinnerArray);
        meeterTypeSpinner.setAdapter(meeterTypeSpinnerArrayad);


        final String powerTypeSpinnerarray[] = {"Temporary", "3 Phase"};
        ArrayAdapter<String> powerTypeSpinnerarrayad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, powerTypeSpinnerarray);
        powerTypeSpinner.setAdapter(powerTypeSpinnerarrayad);


        final String transformerTypeSpinnerArray[] = {"Public", "Site"};
        ArrayAdapter<String> transformerTypeSpinnerArrayad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, transformerTypeSpinnerArray);
        transformerTypeSpinner.setAdapter(transformerTypeSpinnerArrayad);


        final String MeterstatusSpinnerArraay[] = {"Covereds", "Open"};
        ArrayAdapter<String> MeterstatusSpinnerAd = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, MeterstatusSpinnerArraay);
        MeterstatusSpinner.setAdapter(MeterstatusSpinnerAd);

    }

    @Override
    protected void dataToView() {
        getSharedPrefData();
        setSpinnerValue();

        if (!strMake.equals("") || !strModel.equals("") || !strCapacity.equals("") || !strSerialNum.equals("")
                || !strYearOfManufacturing.equals("") || !strDescription.equals("") || !meterreading.equals("")

                || !ConnectionNo.equals("")
                || !CableRating.equals("")
                || !ALTHTConnection.equals("")
                || !TransformerEarthing.equals("")
                || !mccbStatus.equals("")
                || !kitkatChangeover.equals("")

                || !TheftfromSite.equals("")


                ) {
            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            ebMeterreading.setText(meterreading);

            etConnectionNo.setText(ConnectionNo);
            etCableRating.setText(CableRating);
            etALTHTConnection.setText(ALTHTConnection);
            etTransformerEarthing.setText(TransformerEarthing);
            etmccbStatus.setText(mccbStatus);
            etkitkatChangeover.setText(kitkatChangeover);
            etTheftfromSite.setText(TheftfromSite);
            strmeeterTypeSpinner = meeterTypeSpinner.getSelectedItem().toString();
            strpowerTypeSpinner = powerTypeSpinner.getSelectedItem().toString();
            strtransformerTypeSpinner = transformerTypeSpinner.getSelectedItem().toString();
            strMeterstatusSpinner = MeterstatusSpinner.getSelectedItem().toString();

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
                    itemConditionSpinner.setEnabled(false);
                    descriptionLayout.setEnabled(false);
                    ebMeterreading.setEnabled(false);
                    etConnectionNo.setEnabled(false);
                    etCableRating.setEnabled(false);
                    etALTHTConnection.setEnabled(false);
                    etTransformerEarthing.setEnabled(false);
                    etmccbStatus.setEnabled(false);
                    etkitkatChangeover.setEnabled(false);
                    etTheftfromSite.setEnabled(false);
                    meeterTypeSpinner.setEnabled(false);
                    powerTypeSpinner.setEnabled(false);
                    transformerTypeSpinner.setEnabled(false);
                    MeterstatusSpinner.setEnabled(false);
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
                    ebMeterreading.setEnabled(true);
                    etConnectionNo.setEnabled(true);
                    etCableRating.setEnabled(true);
                    etALTHTConnection.setEnabled(true);
                    etTransformerEarthing.setEnabled(true);
                    etmccbStatus.setEnabled(true);
                    etkitkatChangeover.setEnabled(true);
                    etTheftfromSite.setEnabled(true);
                    meeterTypeSpinner.setEnabled(true);
                    powerTypeSpinner.setEnabled(true);
                    transformerTypeSpinner.setEnabled(true);
                    MeterstatusSpinner.setEnabled(true);
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
                editor.putString("EBM_UserId", strUserId);
                editor.putString("EBM_Make", make);
                editor.putString("EBM_Model", model);
                editor.putString("EBM_Capacity", capacity);
                editor.putString("EBM_DescriptionId", strDescriptionId);
                editor.putString("EBM_MakeId", strMakeId);
                editor.putString("EBM_ModelId", strModelId);
                editor.putString("EBM_SerialNum", serialNumber);
                editor.putString("EBM_YearOfManufacturing", yearOfManufacturing);
                editor.putString("EBM_Description", description);
                editor.putString("Meterreading", meterreading);
                editor.putString("EBM_Photo1", frontphoto);
                editor.putString("EBM_Photo2", openPhoto);
                editor.putString("EBM_Photo3", sNoPlatephoto);
                editor.putString("EbMeterSavedDateTime", currentDateTime);


                editor.putString("EbMeterConnectionNo", ConnectionNo);
                editor.putString("EbMeterCableRating", CableRating);
                editor.putString("EbMeterALTHTConnection", ALTHTConnection);
                editor.putString("EbMeterTransformerEarthing", TransformerEarthing);
                editor.putString("EbMetermccbStatus", mccbStatus);
                editor.putString("EbMetermccbStatus", kitkatChangeover);
                editor.putString("EbMeterTheftfromSite", TheftfromSite);
                editor.putString("EbMeterstrmeeterTypeSpinner", strmeeterTypeSpinner);
                editor.putString("EbMeterstrpowerTypeSpinner", strpowerTypeSpinner);
                editor.putString("EbMeterstrtransformerTypeSpinner", strtransformerTypeSpinner);
                editor.putString("EbMeterstrMeterstatusSpinner", strMeterstatusSpinner);


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
        meterreading = ebMeterreading.getText().toString();

        ConnectionNo = etConnectionNo.getText().toString();
        CableRating = etCableRating.getText().toString();
        ALTHTConnection = etALTHTConnection.getText().toString();
        TransformerEarthing = etTransformerEarthing.getText().toString();
        mccbStatus = etmccbStatus.getText().toString();
        kitkatChangeover=etkitkatChangeover.getText().toString();
        TheftfromSite = etTheftfromSite.getText().toString();
        strmeeterTypeSpinner = meeterTypeSpinner.getSelectedItem().toString();
        strpowerTypeSpinner = powerTypeSpinner.getSelectedItem().toString();
        strtransformerTypeSpinner = transformerTypeSpinner.getSelectedItem().toString();
        strMeterstatusSpinner = MeterstatusSpinner.getSelectedItem().toString();


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
            } else if (isEmptyStr(meterreading)) {
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
