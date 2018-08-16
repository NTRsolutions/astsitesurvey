package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipDescriptionDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;
import com.telecom.ast.sitesurvey.utils.FontManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class DGFragment extends MainFragment {
    static ImageView frontImg, openImg, sNoPlateImg;
    static boolean isImage1, isImage2;
    AppCompatEditText etDescription;
    AutoCompleteTextView etModel, etMake, etCapacity, etSerialNum;
    SharedPreferences pref;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription;
    String strSavedDateTime, strUserId, strSiteId, strDescriptionId, CurtomerSite_Id;
    String strMakeId, strModelId;
    String[] arrMake;
    String[] arrModel;
    String[] arrCapacity;
    AtmDatabase atmDatabase;
    ArrayList<EquipMakeDataModel> equipMakeList;
    ArrayList<EquipMakeDataModel> equipList;
    ArrayList<EquipCapacityDataModel> equipCapacityList;
    ArrayList<EquipMakeDataModel> arrEquipData;
    ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime, itemCondition;
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner;
    Spinner itemStatusSpineer;

    String pDistribution, mCBStatus, dbAlternatermake, eSN,
            dBCapacity, dgContacter, backCompressor, AutomationCondition, PowerPanelMake, PowerPanelCapacity, DGType, AlternaterSno,
            AlternterCapacity, DGBatteryStatus, DGBatteryMake, Conditionofwiring, DGearthing, ConditionCANOPY,
            DGRunHourMer, DGlowLUBEWire, DGFuelTank, CableGrouting, DGFoundation, DGCoolingtype, Dgpipe,
            DGExhaustcondi, DGEmergencyStopSwitch, RentalDGChangeOver, DGBatterysn, DGPollutionCertificate;

    Spinner pDistributionSpinner, mCBStatusSpinner, dbAlternatermakeSpinner, eSNSpinner,
            dBCapacitySpinner, dgContacterSpinner, backCompressorSpinner;

    AppCompatEditText etAutomationCondition, etPowerPanelMake, etPowerPanelCapacity, etDGType, etAlternaterSno,
            etAlternterCapacity, etDGBatteryStatus, etDGBatteryMake, etConditionofwiring, etDGearthing, etConditionCANOPY,
            etDGRunHourMeter, eTDGlowLUBEWire, etDGFuelTank, etCableGrouting, etDGFoundation, etDGCoolingtype, etDgpipe,
            etDGExhaustcondi, etDGEmergencyStopSwitch, etRentalDGChangeOver, etDGBatterysn, etDGPollutionCertificate;

    TextView etYear, dateIcon;
    LinearLayout dateLayout;
    long datemilisec;
    static File frontimgFile, openImgFile, sNoPlateImgFile;
    Typeface materialdesignicons_font;
    SharedPreferences mpptSharedPrefpref, userPref;

    String strEqupId;
    private String capcityId = "0";
    private String itemstatus;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_dg;
    }

    @Override
    protected void loadView() {
        frontImg = findViewById(R.id.image1);
        openImg = findViewById(R.id.image2);
        sNoPlateImg = findViewById(R.id.image3);
        etMake = findViewById(R.id.etMake);
        etCapacity = findViewById(R.id.etCapacity);
        etModel = findViewById(R.id.etModel);
        etSerialNum = findViewById(R.id.etSerialNum);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        btnSubmit = findViewById(R.id.btnSubmit);

        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        pDistributionSpinner = findViewById(R.id.pDistributionSpinner);
        mCBStatusSpinner = findViewById(R.id.mCBStatusSpinner);
        dbAlternatermakeSpinner = findViewById(R.id.dbAlternatermakeSpinner);
        eSNSpinner = findViewById(R.id.eSNSpinner);
        dBCapacitySpinner = findViewById(R.id.dBCapacitySpinner);
        dgContacterSpinner = findViewById(R.id.dgContacterSpinner);
        backCompressorSpinner = findViewById(R.id.backCompressorSpinner);
        etAutomationCondition = findViewById(R.id.etAutomationCondition);
        etPowerPanelMake = findViewById(R.id.etPowerPanelMake);
        etPowerPanelCapacity = findViewById(R.id.etPowerPanelCapacity);
        etDGType = findViewById(R.id.etDGType);
        etAlternaterSno = findViewById(R.id.etAlternaterSno);
        etAlternterCapacity = findViewById(R.id.etAlternterCapacity);
        etDGBatteryStatus = findViewById(R.id.etDGBatteryStatus);
        etDGBatteryMake = findViewById(R.id.etDGBatteryMake);
        etConditionofwiring = findViewById(R.id.etConditionofwiring);
        etDGearthing = findViewById(R.id.etDGearthing);
        etConditionCANOPY = findViewById(R.id.etConditionCANOPY);
        etDGRunHourMeter = findViewById(R.id.etDGRunHourMeter);
        eTDGlowLUBEWire = findViewById(R.id.eTDGlowLUBEWire);
        etDGFuelTank = findViewById(R.id.etDGFuelTank);
        etCableGrouting = findViewById(R.id.etCableGrouting);
        etDGFoundation = findViewById(R.id.etDGFoundation);
        etDGCoolingtype = findViewById(R.id.etDGCoolingtype);
        etDgpipe = findViewById(R.id.etDgpipe);
        etDGExhaustcondi = findViewById(R.id.etDGExhaustcondi);
        etDGEmergencyStopSwitch = findViewById(R.id.etDGEmergencyStopSwitch);
        etRentalDGChangeOver = findViewById(R.id.etRentalDGChangeOver);
        etDGBatterysn = findViewById(R.id.etDGBatterysn);
        etDGPollutionCertificate = findViewById(R.id.etDGPollutionCertificate);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
    }

    @Override
    protected void setClickListeners() {
        openImg.setOnClickListener(this);
        frontImg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        dateLayout.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    public void setSpinnerValue() {
        final String itemCondition_array[] = {"Ok", "Not Ok", "Fully Fault"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(homeadapter);

        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);


        final String pDistributionSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> pDistributionSpinnerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, pDistributionSpinner_array);
        pDistributionSpinner.setAdapter(pDistributionSpinnerada);


        final String mCBStatusSpinner_array[] = {"Single Phase", "3 Phase"};
        ArrayAdapter<String> mCBStatusSpinnerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, mCBStatusSpinner_array);
        mCBStatusSpinner.setAdapter(mCBStatusSpinnerada);


        final String dbAlternatermakeSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> dbAlternatermakeSpinnerad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, dbAlternatermakeSpinner_array);
        dbAlternatermakeSpinner.setAdapter(dbAlternatermakeSpinnerad);


        final String eSNSpinner_array[] = {"Working", "Not working"};
        ArrayAdapter<String> eSNSpinnerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, eSNSpinner_array);
        eSNSpinner.setAdapter(eSNSpinnerada);


        final String dBCapacitySpinnerSpineer_array[] = {"CANOPY door lock", "door pannel", "ok", "not ok"};
        ArrayAdapter<String> dBCapacitySpinnerad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, dBCapacitySpinnerSpineer_array);
        dBCapacitySpinner.setAdapter(dBCapacitySpinnerad);

        final String dgContacterSpinnerarray[] = {"DG Body earth ", "Neutral Earth"};
        ArrayAdapter<String> dgContacterSpinnerad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, dgContacterSpinnerarray);
        dgContacterSpinner.setAdapter(dgContacterSpinnerad);


        final String backCompressorSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> backCompressorSpinnerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, backCompressorSpinner_array);
        backCompressorSpinner.setAdapter(backCompressorSpinnerada);


    }

    /**
     * Shared Prefrences
     */
    public void getSharedPrefData() {
       /* pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("DG_Make", "");
        strModel = pref.getString("DG_Model", "");
        strCapacity = pref.getString("DG_Capacity", "");
        strMakeId = pref.getString("DG_MakeId", "");
        strModelId = pref.getString("DG_ModelId", "");
        strDescriptionId = pref.getString("DG_DescriptionId", "");
        strSerialNum = pref.getString("DG_SerialNum", "");
        strYearOfManufacturing = pref.getString("DG_YearOfManufacturing", "");
        strDescription = pref.getString("DG_Description", "");
        frontphoto = pref.getString("DG_Photo1", "");
        openPhoto = pref.getString("DG_Photo2", "");
        sNoPlatephoto = pref.getString("DG_Photo3", "");
        strSavedDateTime = pref.getString("DG_SavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");

        pDistribution = pref.getString("DG_pDistribution", "");
        mCBStatus = pref.getString("DG_mCBStatus", "");
        dbAlternatermake = pref.getString("DG_dbAlternatermake", "");
        eSN = pref.getString("DG_eSN", "");
        dBCapacity = pref.getString("DG_dBCapacity", "");
        dgContacter = pref.getString("DG_dgContacter", "");
        backCompressor = pref.getString("DG_backCompressor", "");
        AutomationCondition = pref.getString("DG_AutomationCondition", "");
        PowerPanelMake = pref.getString("DG_PowerPanelMake", "");
        PowerPanelCapacity = pref.getString("DG_PowerPanelCapacity", "");
        DGType = pref.getString("DG_DGType", "");
        AlternaterSno = pref.getString("DG_AlternaterSno", "");
        AlternterCapacity = pref.getString("DG_AlternterCapacity", "");
        DGBatteryStatus = pref.getString("DG_DGBatteryStatus", "");
        DGBatteryMake = pref.getString("DG_DGBatteryMake", "");
        Conditionofwiring = pref.getString("DG_Conditionofwiring", "");
        DGearthing = pref.getString("DG_DGearthing", "");
        ConditionCANOPY = pref.getString("DG_ConditionCANOPY", "");
        DGRunHourMer = pref.getString("DG_DGRunHourMer", "");
        DGlowLUBEWire = pref.getString("DG_DGlowLUBEWire", "");
        DGFuelTank = pref.getString("DG_DGFuelTank", "");
        CableGrouting = pref.getString("DG_CableGrouting", "");
        DGFoundation = pref.getString("DG_DGFoundation", "");
        DGCoolingtype = pref.getString("DG_DGCoolingtype", "");
        Dgpipe = pref.getString("DG_Dgpipe", "");
        DGExhaustcondi = pref.getString("DG_DGExhaustcondi", "");
        DGEmergencyStopSwitch = pref.getString("DG_DGEmergencyStopSwitch", "");
        RentalDGChangeOver = pref.getString("DG_RentalDGChangeOver", "");
        DGBatterysn = pref.getString("DG_DGBatterysn", "");
        DGPollutionCertificate = pref.getString("DG_DGPollutionCertificate", "");
*/
    }

    public void setDateofSiteonAir() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                etYear.setText(sdf.format(myCalendar.getTime()));
                datemilisec = myCalendar.getTimeInMillis();
            }
        };
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    @Override
    protected void dataToView() {
        getUserPref();
        atmDatabase = new AtmDatabase(getContext());
        equipList = atmDatabase.getEquipmentData("DG");
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "DG");
        arrMake = new String[equipMakeList.size()];
        for (int i = 0; i < equipMakeList.size(); i++) {
            arrMake[i] = equipMakeList.get(i).getName();
        }
        getSharedPrefData();
        setSpinnerValue();
        ArrayAdapter<String> adapterMakeName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrMake);

        etMake.setAdapter(adapterMakeName);
        etMake.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String strMake = etMake.getText().toString();

                if (!strMake.equals("") && strMake.length() > 1) {
                    equipCapacityList = atmDatabase.getEquipmentCapacityData("DESC", strMake);
                    if (equipCapacityList.size() > 0) {
                        arrCapacity = new String[equipCapacityList.size()];
                        for (int i = 0; i < equipCapacityList.size(); i++) {
                            arrCapacity[i] = equipCapacityList.get(i).getName();
                        }
                        ArrayAdapter<String> adapterCapacityName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrCapacity);
                        etCapacity.setAdapter(adapterCapacityName);
                    }
                }
            }
        });
        ASTUIUtil commonFunctions = new ASTUIUtil();
        final String currentDate = commonFunctions.getFormattedDate("dd/MM/yyyy", System.currentTimeMillis());
        if (!isEmptyStr(strMake) || !isEmptyStr(strModel) || !isEmptyStr(strCapacity)
                || !isEmptyStr(strSerialNum)
                || !isEmptyStr(strYearOfManufacturing) || !isEmptyStr(strDescription)) {
        /*    etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            pDistribution = pDistributionSpinner.getSelectedItem().toString();
            mCBStatus = mCBStatusSpinner.getSelectedItem().toString();
            dbAlternatermake = dbAlternatermakeSpinner.getSelectedItem().toString();
            eSN = eSNSpinner.getSelectedItem().toString();
            dBCapacity = dBCapacitySpinner.getSelectedItem().toString();
            dgContacter = dgContacterSpinner.getSelectedItem().toString();
            backCompressor = backCompressorSpinner.getSelectedItem().toString();
            this.etAutomationCondition.setText(AutomationCondition);
            this.etPowerPanelMake.setText(PowerPanelMake);
            this.etPowerPanelCapacity.setText(PowerPanelCapacity);
            this.etDGType.setText(DGType);
            this.etAlternaterSno.setText(AlternaterSno);
            this.etAlternterCapacity.setText(AlternterCapacity);
            this.etDGBatteryStatus.setText(DGBatteryStatus);
            this.etDGBatteryMake.setText(DGBatteryMake);
            this.etConditionofwiring.setText(Conditionofwiring);
            this.etDGearthing.setText(DGearthing);
            this.etConditionCANOPY.setText(ConditionCANOPY);
            this.etDGRunHourMeter.setText(DGRunHourMer);
            this.eTDGlowLUBEWire.setText(DGlowLUBEWire);
            this.etDGFuelTank.setText(DGFuelTank);
            this.etCableGrouting.setText(CableGrouting);
            this.etDGFoundation.setText(DGFoundation);
            this.etDGCoolingtype.setText(DGCoolingtype);
            this.etDgpipe.setText(Dgpipe);
            this.etDGExhaustcondi.setText(DGExhaustcondi);
            this.etDGEmergencyStopSwitch.setText(DGEmergencyStopSwitch);
            this.etRentalDGChangeOver.setText(RentalDGChangeOver);
            this.etDGBatterysn.setText(DGBatterysn);
            etDGPollutionCertificate.setText(DGPollutionCertificate);
            itemCondition = itemConditionSpinner.getSelectedItem().toString();
            arrEquipData = atmDatabase.getEquipmentMakeData("DESC", "DG");
            equipCapacityDataList = atmDatabase.getEquipmentCapacityData("DESC", strMake);
            equipDescriptionDataList = atmDatabase.getEquipmentDescriptionData("DESC", strModel);*/
          /*  if (!frontphoto.equals("") || !openPhoto.equals("") || !sNoPlatephoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(frontphoto)).placeholder(R.drawable.noimage).into(frontImg);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(openPhoto)).placeholder(R.drawable.noimage).into(openImg);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(sNoPlatephoto)).placeholder(R.drawable.noimage).into(sNoPlateImg);
            }*/
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
                descriptionLayout.setVisibility(selectedItem.equalsIgnoreCase("") ? View.VISIBLE : View.GONE);
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

                    itemStatusSpineer.setEnabled(false);
                    pDistributionSpinner.setEnabled(false);
                    mCBStatusSpinner.setEnabled(false);
                    dbAlternatermakeSpinner.setEnabled(false);
                    eSNSpinner.setEnabled(false);
                    dBCapacitySpinner.setEnabled(false);
                    dgContacterSpinner.setEnabled(false);
                    backCompressorSpinner.setEnabled(false);
                    etAutomationCondition.setEnabled(false);
                    etPowerPanelMake.setEnabled(false);
                    etPowerPanelCapacity.setEnabled(false);
                    etDGType.setEnabled(false);
                    etAlternaterSno.setEnabled(false);
                    etAlternterCapacity.setEnabled(false);
                    etDGBatteryStatus.setEnabled(false);
                    etDGBatteryMake.setEnabled(false);
                    etConditionofwiring.setEnabled(false);
                    etDGearthing.setEnabled(false);
                    etConditionCANOPY.setEnabled(false);
                    etDGRunHourMeter.setEnabled(false);
                    eTDGlowLUBEWire.setEnabled(false);
                    etDGFuelTank.setEnabled(false);
                    etCableGrouting.setEnabled(false);
                    etDGFoundation.setEnabled(false);
                    etDGCoolingtype.setEnabled(false);
                    etDgpipe.setEnabled(false);
                    etDGExhaustcondi.setEnabled(false);
                    etDGEmergencyStopSwitch.setEnabled(false);
                    etRentalDGChangeOver.setEnabled(false);
                    etDGBatterysn.setEnabled(false);
                    etDGPollutionCertificate.setEnabled(false);
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
                    itemStatusSpineer.setEnabled(true);
                    pDistributionSpinner.setEnabled(true);
                    mCBStatusSpinner.setEnabled(true);
                    dbAlternatermakeSpinner.setEnabled(true);
                    eSNSpinner.setEnabled(true);
                    dBCapacitySpinner.setEnabled(true);
                    dgContacterSpinner.setEnabled(true);
                    backCompressorSpinner.setEnabled(true);
                    etAutomationCondition.setEnabled(true);
                    etPowerPanelMake.setEnabled(true);
                    etPowerPanelCapacity.setEnabled(true);
                    etDGType.setEnabled(true);
                    etAlternaterSno.setEnabled(true);
                    etAlternterCapacity.setEnabled(true);
                    etDGBatteryStatus.setEnabled(true);
                    etDGBatteryMake.setEnabled(true);
                    etConditionofwiring.setEnabled(true);
                    etDGearthing.setEnabled(true);
                    etConditionCANOPY.setEnabled(true);
                    etDGRunHourMeter.setEnabled(true);
                    eTDGlowLUBEWire.setEnabled(true);
                    etDGFuelTank.setEnabled(true);
                    etCableGrouting.setEnabled(true);
                    etDGFoundation.setEnabled(true);
                    etDGCoolingtype.setEnabled(true);
                    etDgpipe.setEnabled(true);
                    etDGExhaustcondi.setEnabled(true);
                    etDGEmergencyStopSwitch.setEnabled(true);
                    etRentalDGChangeOver.setEnabled(true);
                    etDGBatterysn.setEnabled(true);
                    etDGPollutionCertificate.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dateLayout) {
            setDateofSiteonAir();
        }
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
              /*  SharedPreferences.Editor editor = pref.edit();
                editor.putString("DG_UserId", strUserId);
                editor.putString("DG_Make", make);
                editor.putString("DG_Model", model);
                editor.putString("DG_Capacity", capacity);
                editor.putString("DescriptionId", strDescriptionId);
                editor.putString("MakeId", strMakeId);
                editor.putString("ModelId", strModelId);
                editor.putString("DG_SerialNum", serialNumber);
                editor.putString("DG_YearOfManufacturing", yearOfManufacturing);
                editor.putString("DG_Description", description);
                editor.putString("DG_Photo1", frontphoto);
                editor.putString("DG_Photo2", openPhoto);
                editor.putString("DG_Photo3", sNoPlatephoto);
                editor.putString("DG_SavedDateTime", currentDateTime);

                editor.putString("DG_pDistribution", pDistribution);
                editor.putString("DG_mCBStatus", mCBStatus);
                editor.putString("DG_dbAlternatermake", dbAlternatermake);
                editor.putString("DG_eSN", eSN);
                editor.putString("DG_dBCapacity", dBCapacity);
                editor.putString("DG_dgContacter", dgContacter);
                editor.putString("DG_backCompressor", backCompressor);
                editor.putString("DG_AutomationCondition", AutomationCondition);
                editor.putString("DG_PowerPanelMake", PowerPanelMake);
                editor.putString("DG_PowerPanelCapacity", PowerPanelCapacity);
                editor.putString("DG_DGType", DGType);
                editor.putString("DG_AlternaterSno", AlternaterSno);
                editor.putString("DG_AlternterCapacity", AlternterCapacity);
                editor.putString("DG_DGBatteryStatus", DGBatteryStatus);
                editor.putString("DG_DGBatteryMake", DGBatteryMake);
                editor.putString("DG_Conditionofwiring", Conditionofwiring);
                editor.putString("DG_DGearthing", DGearthing);
                editor.putString("DG_ConditionCANOPY", ConditionCANOPY);
                editor.putString("DG_DGRunHourMer", DGRunHourMer);
                editor.putString("DG_DGlowLUBEWire", DGlowLUBEWire);
                editor.putString("DG_DGFuelTank", DGFuelTank);
                editor.putString("DG_CableGrouting", CableGrouting);
                editor.putString("DG_DGFoundation", DGFoundation);
                editor.putString("DG_DGCoolingtype", DGCoolingtype);
                editor.putString("DG_Dgpipe", Dgpipe);
                editor.putString("DG_DGExhaustcondi", DGExhaustcondi);
                editor.putString("DG_DGEmergencyStopSwitch", DGEmergencyStopSwitch);
                editor.putString("DG_RentalDGChangeOver", RentalDGChangeOver);
                editor.putString("DG_DGBatterysn", DGBatterysn);
                editor.putString("DG_DGPollutionCertificate", DGPollutionCertificate);
                editor.commit();*/
                saveBasicDataonServer();
            }
        }
    }


    public boolean isValidate() {
        make = getTextFromView(this.etMake);
        model = getTextFromView(this.etModel);
        capacity = getTextFromView(this.etCapacity);
        serialNumber = getTextFromView(this.etSerialNum);
        yearOfManufacturing = getTextFromView(this.etYear);
        description = getTextFromView(this.etDescription);
        currentDateTime = String.valueOf(System.currentTimeMillis());
        pDistribution = pDistributionSpinner.getSelectedItem().toString();
        mCBStatus = mCBStatusSpinner.getSelectedItem().toString();
        dbAlternatermake = dbAlternatermakeSpinner.getSelectedItem().toString();
        eSN = eSNSpinner.getSelectedItem().toString();
        dBCapacity = dBCapacitySpinner.getSelectedItem().toString();
        dgContacter = dgContacterSpinner.getSelectedItem().toString();
        backCompressor = backCompressorSpinner.getSelectedItem().toString();
        AutomationCondition = getTextFromView(this.etAutomationCondition);
        PowerPanelMake = getTextFromView(this.etPowerPanelMake);
        PowerPanelCapacity = getTextFromView(this.etPowerPanelCapacity);
        DGType = getTextFromView(this.etDGType);
        AlternaterSno = getTextFromView(this.etAlternaterSno);
        AlternterCapacity = getTextFromView(this.etAlternterCapacity);
        DGBatteryStatus = getTextFromView(this.etDGBatteryStatus);
        DGBatteryMake = getTextFromView(this.etDGBatteryMake);
        Conditionofwiring = getTextFromView(this.etConditionofwiring);
        DGearthing = getTextFromView(this.etDGearthing);
        ConditionCANOPY = getTextFromView(this.etConditionCANOPY);
        DGRunHourMer = getTextFromView(this.etDGRunHourMeter);
        DGlowLUBEWire = getTextFromView(this.eTDGlowLUBEWire);
        DGFuelTank = getTextFromView(this.etDGFuelTank);
        CableGrouting = getTextFromView(this.etCableGrouting);
        DGFoundation = getTextFromView(this.etDGFoundation);
        DGCoolingtype = getTextFromView(this.etDGCoolingtype);
        Dgpipe = getTextFromView(this.etDgpipe);
        DGExhaustcondi = getTextFromView(this.etDGExhaustcondi);
        DGEmergencyStopSwitch = getTextFromView(this.etDGEmergencyStopSwitch);
        RentalDGChangeOver = getTextFromView(this.etRentalDGChangeOver);
        DGBatterysn = getTextFromView(this.etDGBatterysn);
        DGPollutionCertificate = getTextFromView(this.etDGPollutionCertificate);
        itemstatus = itemStatusSpineer.getSelectedItem().toString();

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
            } else if (isEmptyStr(description) && itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
                return false;
            } else if (frontimgFile == null || !frontimgFile.exists()) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Front Photo");
                return false;
            } else if (openImgFile == null || !openImgFile.exists()) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Open Photo");
                return false;
            } else if (sNoPlateImgFile == null || !sNoPlateImgFile.exists()) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Sr no Plate Photo");
                return false;
            }
        } else {
            ASTUIUtil.showToast("Item Not Available");
        }

        return true;
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

    public void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1) {
                    String imageName = CurtomerSite_Id + "_DG_1_Front.jpg";
                    frontimgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(frontimgFile).into(frontImg);
                    //overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    String imageName = CurtomerSite_Id + "_DG_1_Open.jpg";
                    openImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(openImgFile).into(openImg);
                } else {
                    String imageName = CurtomerSite_Id + "_DG_1_SerialNoPlate.jpg";
                    sNoPlateImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(sNoPlateImgFile).into(sNoPlateImg);
                }
            }
            //  }
        }
    }


    public void getResult(ArrayList<MediaFile> files) {
        getPickedFiles(files);
    }

    public void saveBasicDataonServer() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar progressBar = new ASTProgressBar(getContext());
            progressBar.show();
            String serviceURL = Constant.BASE_URL + Constant.SurveyDataSave;
            // JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject = makeJsonData();
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("JsonData", jsonObject.toString());
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelper fileUploaderHelper = new FileUploaderHelper(getContext(), payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        if (data.getStatus() == 1) {
                            ASTUIUtil.showToast("Your MPPT Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Your MPPT Data has not been updated!");
                    }
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                }
            };
            fileUploaderHelper.execute();
        } else {
            ASTUIUtil.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }

    }

    private JSONObject makeJsonData() {
        JSONObject jsonObject = new JSONObject();
        getMakeAndEqupmentId();
        try {
            jsonObject.put("Site_ID", strSiteId);
            jsonObject.put("User_ID", strUserId);
            jsonObject.put("Activity", "Equipment");
            JSONObject EquipmentData = new JSONObject();
            EquipmentData.put("EquipmentStatus", itemstatus);
            EquipmentData.put("EquipmentSno", "1");
            EquipmentData.put("EquipmentID", strEqupId);
            EquipmentData.put("Capacity_ID", capcityId);
            EquipmentData.put("MakeID", strMakeId);
            EquipmentData.put("Make", make);
            EquipmentData.put("Equipment", "DG");
            EquipmentData.put("Capacity", capacity);
            EquipmentData.put("SerialNo", serialNumber);
            EquipmentData.put("MfgDate", datemilisec);
            EquipmentData.put("DG_PowerDistPanelStatus", pDistribution);
            EquipmentData.put("DG_Type", DGType);
            EquipmentData.put("DG_PowerDistPanelMake", PowerPanelMake);
            EquipmentData.put("DG_PowerDistPanelCapacity", PowerPanelCapacity);
            EquipmentData.put("DG_AutomationWorkingCondition", AutomationCondition);
            EquipmentData.put("DG_MCBStatus", mCBStatus);
            EquipmentData.put("DG_AlternaterMake", dbAlternatermake);
            EquipmentData.put("DG_AlternaterSno", AlternaterSno);
            EquipmentData.put("DG_AlternaterCapacity", AlternterCapacity);
            EquipmentData.put("DG_BatteryStatus", DGBatteryStatus);
            EquipmentData.put("DG_BatteryMake", DGBatteryMake);
            EquipmentData.put("DG_BatterySerialNo", DGBatterysn);
            EquipmentData.put("DG_BatteryCapacity", dBCapacity);
            EquipmentData.put("DG_Contacter", dgContacter);
            EquipmentData.put("DG_WiringCondition", Conditionofwiring);
            EquipmentData.put("DG_Earthing", DGearthing);
            EquipmentData.put("DG_CANOPYCondition", ConditionCANOPY);
            EquipmentData.put("DG_RunHourMeter", DGRunHourMer);
            EquipmentData.put("DG_LowLUBEWire", DGlowLUBEWire);
            EquipmentData.put("DG_BackCompressor", backCompressor);
            EquipmentData.put("DG_FuelTankStatus", DGFuelTank);
            EquipmentData.put("DG_CableGrouting", CableGrouting);
            EquipmentData.put("DG_FoundationStatus", DGFoundation);
            EquipmentData.put("DG_CoolingType", DGCoolingtype);
            EquipmentData.put("DG_Pipe", Dgpipe);
            EquipmentData.put("DG_ExhaustPipeCondition", DGExhaustcondi);
            EquipmentData.put("DG_EmergencyStopSwitch", DGEmergencyStopSwitch);
            EquipmentData.put("DG_Rental", "");
            EquipmentData.put("DG_ChangeOverBox", RentalDGChangeOver);
            EquipmentData.put("DG_PollutionCertificate", DGPollutionCertificate);
            EquipmentData.put("DG_ESNo", eSN);
            EquipmentData.put("ItemCondition", itemCondition);
            JSONArray EquipmentDataa = new JSONArray();
            EquipmentDataa.put(EquipmentData);
            jsonObject.put("EquipmentData", EquipmentDataa);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (frontimgFile.exists()) {
            multipartBody.addFormDataPart(frontimgFile.getName(), frontimgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, frontimgFile));
        }
        if (openImgFile.exists()) {
            multipartBody.addFormDataPart(openImgFile.getName(), openImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, openImgFile));
        }
        if (sNoPlateImgFile.exists()) {
            multipartBody.addFormDataPart(sNoPlateImgFile.getName(), sNoPlateImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sNoPlateImgFile));
        }

        return multipartBody;
    }


    //get make and equpment id from  list
    private void getMakeAndEqupmentId() {
        for (EquipMakeDataModel dataModel : equipMakeList) {
            if (dataModel.getName().equals(make)) {
                strMakeId = dataModel.getId();
            }
        }
        //get equpment id from equpiment list
        for (EquipMakeDataModel dataModel : equipList) {
            strEqupId = dataModel.getId();
        }
//get Capcity id
        if (equipCapacityList.size() > 0) {
            for (int i = 0; i < equipCapacityList.size(); i++) {
                if (capacity.equals(equipCapacityList.get(i).getName())) {
                    capcityId = equipCapacityList.get(i).getId();
                }
            }
        }
    }
}
