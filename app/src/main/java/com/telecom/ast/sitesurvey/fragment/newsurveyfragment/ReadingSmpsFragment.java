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
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class ReadingSmpsFragment extends MainFragment {

    static ImageView battVoltageImage, loadCurrentImage, batteryDisChaImage;
    static String battVoltagephoto, adCurrentPhoto, batteryDisChaphoto;
    String strBattVoltage, strLoadCurrent;
    String BattVoltage, LoadCurrent;
    AppCompatEditText etBattVoltage, etLoadCurrent;
    SharedPreferences pref;
    Button btnSubmit;
    static boolean isImage1, isImage2;
    SharedPreferences userPref;
    String strSavedDateTime, strUserId, strSiteId,CurtomerSite_Id;

    @Override
    protected int fragmentLayout() {
        return R.layout.readingsmps_fragment;
    }

    @Override
    protected void loadView() {
        battVoltageImage = findViewById(R.id.image1);
        loadCurrentImage = findViewById(R.id.image2);
        batteryDisChaImage = findViewById(R.id.image3);
        etBattVoltage = findViewById(R.id.etBattVoltage);
        etLoadCurrent = findViewById(R.id.etLoadCurrent);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        battVoltageImage.setOnClickListener(this);
        loadCurrentImage.setOnClickListener(this);
        batteryDisChaImage.setOnClickListener(this);
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
        if (!strBattVoltage.equals("") || !strLoadCurrent.equals("")) {
            etBattVoltage.setText(strBattVoltage);
            etLoadCurrent.setText(strLoadCurrent);
            if (!battVoltagephoto.equals("") || !adCurrentPhoto.equals("") || !batteryDisChaphoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(battVoltagephoto)).placeholder(R.drawable.noimage).into(battVoltageImage);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(adCurrentPhoto)).placeholder(R.drawable.noimage).into(loadCurrentImage);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(batteryDisChaphoto)).placeholder(R.drawable.noimage).into(batteryDisChaImage);
            }
        }
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
               /* SharedPreferences.Editor editor = pref.edit();
                editor.putString("READING_BattVoltage", BattVoltage);
                editor.putString("READING_LoadCurrent", LoadCurrent);
                editor.putString("BattVoltagephoto", battVoltagephoto);
                editor.putString("AdCurrentPhoto", adCurrentPhoto);
                editor.putString("BatteryDisChaphoto", batteryDisChaphoto);
                editor.commit();*/
            }

        }
    }


    public boolean isValidate() {
        BattVoltage = etBattVoltage.getText().toString();
        LoadCurrent = etLoadCurrent.getText().toString();
        if (isEmptyStr(BattVoltage)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Batt Voltage");
            return false;
        } else if (isEmptyStr(LoadCurrent)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Load Current");
            return false;
        } else if (isEmptyStr(battVoltagephoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Batt Voltage Photo");
            return false;
        } else if (isEmptyStr(adCurrentPhoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Load Current Photo");
            return false;
        } else if (isEmptyStr(batteryDisChaphoto)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please SelectBattery Discharge Current Photo");
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
                        battVoltagephoto = deviceFile.getFilePath().toString();
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(battVoltageImage);
                    } else if (isImage2) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(loadCurrentImage);
                        adCurrentPhoto = deviceFile.getFilePath().toString();

                    } else {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(batteryDisChaImage);
                        batteryDisChaphoto = deviceFile.getFilePath().toString();
                    }
                    //compressPath.delete();
                }
            } else if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1) {
                    battVoltagephoto = deviceFile.getFilePath().toString();
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(battVoltageImage);
                } else if (isImage2) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(loadCurrentImage);
                    adCurrentPhoto = deviceFile.getFilePath().toString();
                } else {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(batteryDisChaImage);
                    batteryDisChaphoto = deviceFile.getFilePath().toString();
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
