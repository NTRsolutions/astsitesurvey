package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class EBMeterFragment extends MainFragment {
    TextView imgPrevious, imgNext;
    static ImageView ebmeterImage, billImage;
    static boolean isImage1;
    static String ebmPhoto, billPhoto;
    FNEditText etMeterReading, etMeterNumber, etAvailableHours, etSinglePhase, et3PhaseMeter;
    String strUserId, strSavedDateTime;
    SharedPreferences pref;
    String strMeterReading, strMeterNumber, strAvailableHours, strSupplySinglePhase, strSupplyThreePhase, currentDateTime;
    String strSiteId;
    LinearLayout perviousLayout, nextLayout;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_eb_meter;
    }

    @Override
    protected void loadView() {
        imgNext = findViewById(R.id.imgNext);
        imgPrevious = findViewById(R.id.imgPrevious);
        ebmeterImage = findViewById(R.id.image1);
        billImage = findViewById(R.id.image2);
        etMeterReading = findViewById(R.id.etMeterReading);
        etMeterNumber = findViewById(R.id.etMeterNumber);
        etAvailableHours = findViewById(R.id.etAvailableHours);
        etSinglePhase = findViewById(R.id.et3PhaseMeter);
        et3PhaseMeter = findViewById(R.id.et3PhaseMeter);
        this.nextLayout = findViewById(R.id.nextLayout);
        this.perviousLayout = findViewById(R.id.nextLayout);
        nextLayout.setOnClickListener(this);
        perviousLayout.setOnClickListener(this);
    }

    @Override
    protected void setClickListeners() {
        this.ebmeterImage.setOnClickListener(this);
        this.billImage.setOnClickListener(this);
        this.imgNext.setOnClickListener(this);
        this.imgPrevious.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMeterReading = pref.getString("MeterReading", "");
        strMeterNumber = pref.getString("MeterNumber", "");
        strAvailableHours = pref.getString("AvailableHours", "");
        strSupplySinglePhase = pref.getString("SupplySinglePhase", "");
        strSupplyThreePhase = pref.getString("SupplyThreePhase", "");
        ebmPhoto = pref.getString("Photo1", "");
        billPhoto = pref.getString("Photo2", "");
        strSavedDateTime = pref.getString("EbMeterSavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");
    }

    @Override
    protected void dataToView() {
        getSharedPrefData();
        if (!strMeterReading.equals("") || !strMeterNumber.equals("") || !strAvailableHours.equals("")
                || !strSupplySinglePhase.equals("") || !strSupplyThreePhase.equals("")) {
            etMeterReading.setText(strMeterReading);
            etMeterNumber.setText(strMeterNumber);
            etAvailableHours.setText(strAvailableHours);
            etSinglePhase.setText(strSupplySinglePhase);
            et3PhaseMeter.setText(strSupplyThreePhase);
            if (!ebmPhoto.equals("") || !billPhoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(ebmPhoto)).placeholder(R.drawable.noimage).into(ebmeterImage);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(billPhoto)).placeholder(R.drawable.noimage).into(billImage);
            }
        }

        ASTUIUtil commonFunctions = new ASTUIUtil();
        final String currentDate = commonFunctions.getFormattedDate("dd/MM/yyyy", System.currentTimeMillis());
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
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("UserId", strUserId);
                editor.putString("MeterReading", strMeterReading);
                editor.putString("MeterNumber", strMeterNumber);
                editor.putString("AvailableHours", strAvailableHours);
                editor.putString("SupplySinglePhase", strSupplySinglePhase);
                editor.putString("SupplyThreePhase", strSupplyThreePhase);
                editor.putString("Photo1", ebmPhoto);
                editor.putString("Photo2", billPhoto);
                editor.putString("EbMeterSavedDateTime", currentDateTime);
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
        strMeterReading = etMeterReading.getText().toString();
        strMeterNumber = etMeterNumber.getText().toString();
        strAvailableHours = etAvailableHours.getText().toString();
        strSupplySinglePhase = etSinglePhase.getText().toString();
        strSupplyThreePhase = et3PhaseMeter.getText().toString();
        currentDateTime = String.valueOf(System.currentTimeMillis());
        if (isEmptyStr(strMeterReading)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Meter Reading");
            return false;
        } else if (isEmptyStr(strMeterNumber)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Meter Number");
            return false;
        } else if (isEmptyStr(strAvailableHours)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Available Hours");
            return false;
        } else if (isEmptyStr(strSupplySinglePhase)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Single Phase");
            return false;
        } else if (isEmptyStr(strSupplyThreePhase)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Three Phase");
            return false;
        } else if (isEmptyStr(ebmPhoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select EB Meter Photo");
            return false;
        } else if (isEmptyStr(billPhoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Bill Amount for One Month Photo");
            return false;
        }
        return true;
    }

    public static void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (FNObjectUtil.isNonEmptyStr(deviceFile.getCompressFilePath())) {
                File compressPath = new File(deviceFile.getCompressFilePath());
                if (compressPath.exists()) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(isImage1 ? ebmeterImage : billImage);
                    if (isImage1) {
                        ebmPhoto = deviceFile.getFilePath().toString();
                    } else {
                        billPhoto = deviceFile.getFilePath().toString();
                    }
                }
            } else if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                // deviceFile.setEncodedString(ASTUtil.encodeFileTobase64(deviceFile.getFilePath()));
                Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(isImage1 ? ebmeterImage : billImage);
                if (isImage1) {
                    ebmPhoto = deviceFile.getFilePath().toString();
                } else {
                    billPhoto = deviceFile.getFilePath().toString();
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
