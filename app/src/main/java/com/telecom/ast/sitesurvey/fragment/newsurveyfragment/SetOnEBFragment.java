package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.model.BasicDataModel;
import com.telecom.ast.sitesurvey.model.EbMeterDataModel;
import com.telecom.ast.sitesurvey.model.SelectedEquipmentDataModel;
import com.telecom.ast.sitesurvey.model.SiteOnBatteryBankDataModel;
import com.telecom.ast.sitesurvey.model.SiteOnDG;
import com.telecom.ast.sitesurvey.model.SiteOnEbDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class SetOnEBFragment extends MainFragment {
    AppCompatEditText etebCurrent, etebVolatge, etEbFrequency, etbattcharging, etBattcurrent;
    SharedPreferences pref;
    String strCurrent, strVoltage, strFrequency, battcharging, Battcurrent;
    String strUserId, strSavedDateTime, strSiteId;
    AtmDatabase atmDatabase;
    Button btnSubmit;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_set_on_eb;
    }

    @Override
    protected void loadView() {
        etebCurrent = findViewById(R.id.etebCurrent);
        etebVolatge = findViewById(R.id.etebVolatge);
        etEbFrequency = findViewById(R.id.etEbFrequency);
        etbattcharging = findViewById(R.id.etbattcharging);
        etBattcurrent = findViewById(R.id.etBattcurrent);
        btnSubmit = findViewById(R.id.btnSubmit);

    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strCurrent = pref.getString("ebCurrent", "");
        strVoltage = pref.getString("ebVoltage", "");
        strFrequency = pref.getString("ebFrequency", "");
        battcharging = pref.getString("battcharging", "");
        Battcurrent = pref.getString("Battcurrent", "");
        strUserId = pref.getString("USER_ID", "");
        strSavedDateTime = pref.getString("SetOnEbSavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");
    }


    @Override
    protected void dataToView() {
        atmDatabase = new AtmDatabase(getContext());
        getSharedPrefData();
        if (!strCurrent.equals("") || !strVoltage.equals("") || !strFrequency.equals("") || !battcharging.equals("") || !Battcurrent.equals("")) {
            etebCurrent.setText(strCurrent);
            etebVolatge.setText(strVoltage);
            etEbFrequency.setText(strFrequency);
            etbattcharging.setText(battcharging);
            etBattcurrent.setText(Battcurrent);

        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            String ebCurrent = etebCurrent.getText().toString().trim();
            String ebFrequency = etebVolatge.getText().toString().trim();
            String ebVoltage = etEbFrequency.getText().toString().trim();
            String batrycharging = etbattcharging.getText().toString().trim();
            String battcurrent = etBattcurrent.getText().toString().trim();
            if (isEmptyStr(ebCurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Current");
            } else if (isEmptyStr(ebFrequency)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Frequency");
            } else if (isEmptyStr(ebVoltage)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Voltage");
            } else if (isEmptyStr(batrycharging)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Batt Charging Voltage");
            } else if (isEmptyStr(battcurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Batt Charging Current");
            } else {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("UserId", strUserId);
                editor.putString("ebCurrent", ebCurrent);
                editor.putString("ebFrequency", ebFrequency);
                editor.putString("ebVoltage", ebVoltage);
                editor.putString("battcharging", batrycharging);
                editor.putString("Battcurrent", battcurrent);
                editor.putString("SetOnEbSavedDateTime", strSavedDateTime);
                editor.commit();
                ASTProgressBar progressDialog = new ASTProgressBar(getContext());
                progressDialog.show();
                //  saveBasicDataDetails();
                //  saveEbMeterDataDetails();
                //   saveSiteOnDg();
                //   saveSiteOnEb();
                //  saveEquipmentData();
                progressDialog.dismiss();
            }
        }
    }
/*   public void saveBasicDataDetails() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        String userId = pref.getString("USER_ID", "");
        String strMilli = pref.getString("MilliSeconds", "");
        String strDate = pref.getString("Date", "");
        String strTime = pref.getString("Time", "");
        String strSurveyyorName = pref.getString("SurveyorName", "");
        String strSiteId = pref.getString("SiteId", "");
        String strSiteCustomerId = pref.getString("SiteCustomerId", "");
        String strSiteName = pref.getString("SiteName", "");
        String strAddress = pref.getString("Address", "");
        String strCircleId = pref.getString("CircleId", "");
        String strSSAId = pref.getString("SSAId", "");
        String strDistrictId = pref.getString("DistrictId", "");
        String strCirclePosition = pref.getString("CirclePosition", "");
        String strSSAPosition = pref.getString("SSAPosition", "");
        String strDistrictPosition = pref.getString("DistrictPosition", "");
        String strCity = pref.getString("City", "");
        String strPincode = pref.getString("Pincode", "");
        BasicDataModel basicDataModel = new BasicDataModel();
        basicDataModel.setUserId(userId);
        basicDataModel.setDateTime(strMilli);
        basicDataModel.setSurveyorName(strSurveyyorName);
        basicDataModel.setSiteId(strSiteId);
        basicDataModel.setAddress(strAddress);
        basicDataModel.setCircle(strCircleId);
        basicDataModel.setSsa(strCircleId);
        basicDataModel.setSsa(strSSAId);
        basicDataModel.setDistrict(strDistrictId);
        basicDataModel.setCity(strCity);
        basicDataModel.setPincode(strPincode);
        List<BasicDataModel> basicDataList = new ArrayList<>();
        basicDataList.add(basicDataModel);
        atmDatabase.addBasicFormData(basicDataList);
    }*/

 /*   public void saveEbMeterDataDetails() {
        EbMeterDataModel ebMeterDataModel = new EbMeterDataModel();
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        String strEbMeterUserId = pref.getString("USER_ID", "");
        String strEbMeterReading = pref.getString("MeterReading", "");
        String strEbMeterNumber = pref.getString("MeterNumber", "");
        String strEbMeterAvailableHours = pref.getString("AvailableHours", "");
        String strEbMeterSupplySinglePhase = pref.getString("SupplySinglePhase", "");
        String strEbMeterSupplyThreePhase = pref.getString("SupplyThreePhase", "");
        String strEbMeterPhoto1 = pref.getString("Photo1", "");
        String strEbMeterPhoto2 = pref.getString("Photo2", "");
        String strEbMeterSiteId = pref.getString("SiteId", "");
        String strEbMeterPhoto1Name = strEbMeterSiteId + "-EbMeter-SinglePhase";
        String strEbMeterPhoto2Name = strEbMeterSiteId + "-EbMeter-ThreePhase";
        String strEbMeterSavedDateTime = pref.getString("EbMeterSavedDateTime", "");
        ebMeterDataModel.setUserId(strEbMeterUserId);
        ebMeterDataModel.setMeterReading(strEbMeterReading);
        ebMeterDataModel.setMeterNumber(strEbMeterNumber);
        ebMeterDataModel.setAvailableHours(strEbMeterAvailableHours);
        ebMeterDataModel.setSupplySinglePhase(strEbMeterSupplySinglePhase);
        ebMeterDataModel.setSupplyThreePhase(strEbMeterSupplyThreePhase);
        ebMeterDataModel.setPhoto1(strEbMeterPhoto1);
        ebMeterDataModel.setPhoto1Name(strEbMeterPhoto1Name);
        ebMeterDataModel.setPhoto2(strEbMeterPhoto2);
        ebMeterDataModel.setPhoto2Name(strEbMeterPhoto2Name);
        List<EbMeterDataModel> ebMeterDataList = new ArrayList<>();
        ebMeterDataList.add(ebMeterDataModel);

        atmDatabase.addEbMeterFormData(ebMeterDataList);
    }*/

/*    public void saveSiteOnBb() {
        SiteOnBatteryBankDataModel siteOnBatteryBankDataModel = new SiteOnBatteryBankDataModel();
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        String strSiteOnBbVoltage = pref.getString("Voltage", "");
        String strSiteOnBbCurrent = pref.getString("Current", "");
        String strSiteOnBbSavedDateTime = pref.getString("SiteOnBBSavedDateTime", "");
        String strSiteOnBbUserId = pref.getString("USER_ID", "");
        String strSiteOnBbSiteId = pref.getString("SiteId", "");
        siteOnBatteryBankDataModel.setUserId(strSiteOnBbUserId);
        siteOnBatteryBankDataModel.setSiteId(strSiteOnBbSiteId);
        siteOnBatteryBankDataModel.setDischargeVoltage(strSiteOnBbVoltage);
        siteOnBatteryBankDataModel.setDischargeCurrent(strSiteOnBbCurrent);
        List<SiteOnBatteryBankDataModel> siteOnBatteryBankDataList = new ArrayList<>();
        siteOnBatteryBankDataList.add(siteOnBatteryBankDataModel);
        atmDatabase.addSiteOnBbFormData(siteOnBatteryBankDataList);
    }*/

 /*   public void saveSiteOnDg() {
        SiteOnDG siteOnDGDataModel = new SiteOnDG();
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        String strDgCurrent = pref.getString("DgCurrent", "");
        String strDgFrequency = pref.getString("DgFrequency", "");
        String strDgVoltage = pref.getString("DgVoltage", "");
        String strBatteryChargeCurrent = pref.getString("BatteryChargeCurrent", "");
        String strBatteryVoltage = pref.getString("BatteryVoltage", "");
        String strDgUserId = pref.getString("USER_ID", "");
        String strSiteOnEbSiteId = pref.getString("SiteId", "");
        siteOnDGDataModel.setUserId(strDgUserId);
        siteOnDGDataModel.setSiteId(strSiteOnEbSiteId);
        siteOnDGDataModel.setDgCurrent(strDgCurrent);
        siteOnDGDataModel.setDgFrequency(strDgFrequency);
        siteOnDGDataModel.setDgVoltage(strDgVoltage);
        siteOnDGDataModel.setBatteryChargeCurrent(strBatteryChargeCurrent);
        siteOnDGDataModel.setBatteryVoltage(strBatteryVoltage);
        List<SiteOnDG> siteOnBatteryBankDataList = new ArrayList<>();
        siteOnBatteryBankDataList.add(siteOnDGDataModel);

        atmDatabase.addSiteOnDgFormData(siteOnBatteryBankDataList);
    }*/

  /*  public void saveSiteOnEb() {
        SiteOnEbDataModel siteOnEbDataModel = new SiteOnEbDataModel();
        siteOnEbDataModel.setGridCurrent(strCurrent);
        siteOnEbDataModel.setGridFrequency(strVoltage);
        siteOnEbDataModel.setGridVoltage(strFrequency);
        //  siteOnEbDataModel.setGridVoltage(strFrequency);
        // siteOnEbDataModel.setGridVoltage(strFrequency);
        siteOnEbDataModel.setSiteId(strSiteId);
        siteOnEbDataModel.setUserId(strUserId);
        List<SiteOnEbDataModel> siteOnEbDataModelList = new ArrayList<>();
        siteOnEbDataModelList.add(siteOnEbDataModel);
        atmDatabase.addSiteOnEbFormData(siteOnEbDataModelList);
    }*/

/*    public void saveEquipmentData() {
        SelectedEquipmentDataModel selectedEquipmentDataModel = new SelectedEquipmentDataModel();
        List<SelectedEquipmentDataModel> selectedEquipmentDataList = new ArrayList<>();
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        //----------------------------Adding Battery Data-----------------------------------------
        String strBbMake = pref.getString("Make", "");
        String strBbModel = pref.getString("Model", "");
        String strBbCapacity = pref.getString("Capacity", "");
        //String strBbEquipId = ;
        String strMakeId = pref.getString("MakeId", "");
        String strModelId = pref.getString("ModelId", "");
        String strDescriptionId = pref.getString("DescriptionId", "");
        String strBbSerialNum = pref.getString("SerialNum", "");
        String strBbYearOfManufacturing = pref.getString("YearOfManufacturing", "");
        String strBbDescription = pref.getString("Description", "");
        String strBbPhoto1 = pref.getString("Photo1", "");
        String strBbPhoto2 = pref.getString("Photo2", "");
        String strBbSavedDateTime = pref.getString("BbActivitySavedDateTime", "");
        String strBbSiteId = pref.getString("SiteId", "");
        String strPhoto1Name = strBbSiteId + "-BatteryBank-FullBatteryBank";
        String strPhoto2Name = strBbSiteId + "-BatteryBank-OneCell";
        selectedEquipmentDataModel.setEquipmentType("BB");
        selectedEquipmentDataModel.setSerialNumber(strBbSerialNum);
        selectedEquipmentDataModel.setDateOfManufacturing(strBbYearOfManufacturing);
        selectedEquipmentDataModel.setDescription(strBbDescription);
        selectedEquipmentDataModel.setPhoto1(strBbPhoto1);
        selectedEquipmentDataModel.setPhoto2(strBbPhoto2);
        selectedEquipmentDataModel.setPhoto1Name(strPhoto1Name);
        selectedEquipmentDataModel.setPhoto2Name(strPhoto2Name);
        selectedEquipmentDataModel.setType("NA");
        selectedEquipmentDataModel.setNumberOfAC("NA");
        selectedEquipmentDataModel.setEquipmentMake(strBbMake);
        selectedEquipmentDataModel.setEquipmentModel(strBbModel);
        selectedEquipmentDataModel.setEquipmentCapacity(strBbCapacity);
        selectedEquipmentDataModel.setUserId(strUserId);
        selectedEquipmentDataModel.setMakeId(strMakeId);
        selectedEquipmentDataModel.setModelId(strModelId);
        selectedEquipmentDataModel.setDescription(strDescriptionId);
        selectedEquipmentDataList.add(selectedEquipmentDataModel);
        //----------------------------Adding DG Data-----------------------------------------
        selectedEquipmentDataModel = new SelectedEquipmentDataModel();
        selectedEquipmentDataModel.setEquipmentType("DG");
        String strDGUserId = pref.getString("USER_ID", "");
        String strDGMake = pref.getString("DG_Make", "");
        String strDGModel = pref.getString("DG_Model", "");
        String strDGCapacity = pref.getString("DG_Capacity", "");
        String strDGMakeId = pref.getString("DG_MakeId", "");
        String strDGModelId = pref.getString("DG_ModelId", "");
        String strDGDescriptionId = pref.getString("DG_DescriptionId", "");
        String strDGSerialNum = pref.getString("DG_SerialNum", "");
        String strDGYearOfManufacturing = pref.getString("DG_YearOfManufacturing", "");
        String strDGDescription = pref.getString("DG_Description", "");
        String strDGPhoto1 = pref.getString("DG_Photo1", "");
        String strDGPhoto2 = pref.getString("DG_Photo2", "");
        String strDGSavedDateTime = pref.getString("DG_SavedDateTime", "");
        selectedEquipmentDataModel.setSerialNumber(strDGSerialNum);
        selectedEquipmentDataModel.setDateOfManufacturing(strDGYearOfManufacturing);
        selectedEquipmentDataModel.setDescription(strDGDescription);
        selectedEquipmentDataModel.setPhoto1(strDGPhoto1);
        selectedEquipmentDataModel.setPhoto2(strDGPhoto2);
        selectedEquipmentDataModel.setPhoto1Name(strSiteId + "-DG-SetPlate");
        selectedEquipmentDataModel.setPhoto2Name(strSiteId + "-DG-Outside");
        selectedEquipmentDataModel.setType("NA");
        selectedEquipmentDataModel.setNumberOfAC("NA");
        selectedEquipmentDataModel.setEquipmentMake(strDGMake);
        selectedEquipmentDataModel.setEquipmentModel(strDGModel);
        selectedEquipmentDataModel.setEquipmentCapacity(strDGCapacity);
        selectedEquipmentDataModel.setUserId(strDGUserId);
        selectedEquipmentDataModel.setMakeId(strDGMakeId);
        selectedEquipmentDataModel.setModelId(strDGModelId);
        selectedEquipmentDataModel.setDescription(strDGDescriptionId);
        selectedEquipmentDataList.add(selectedEquipmentDataModel);
        //----------------------------Adding Air Conditioner Data-----------------------------------------
        selectedEquipmentDataModel = new SelectedEquipmentDataModel();
        String strACUserId = pref.getString("USER_ID", "");
        String strACMake = pref.getString("AC_Make", "");
        String strACModel = pref.getString("AC_Model", "");
        String strACCapacity = pref.getString("AC_Capacity", "");
        String strACMakeId = pref.getString("AC_MakeId", "");
        String strACModelId = pref.getString("AC_ModelId", "");
        String strACDescriptionId = pref.getString("AC_DescriptionId", "");
        String strACSerialNum = pref.getString("AC_SerialNum", "");
        String strACYearOfManufacturing = pref.getString("AC_YearOfManufacturing", "");
        String strACDescription = pref.getString("AC_Description", "");
        String strACPhoto1 = pref.getString("AC_Photo1", "");
        String strACPhoto2 = pref.getString("AC_Photo2", "");
        String strACSavedDateTime = pref.getString("AC_SavedDateTime", "");
        String strACType = pref.getString("AC_Type", "");
        String strACNumberOfAC = pref.getString("AC_Number", "");
        selectedEquipmentDataModel.setEquipmentType("AC");
        selectedEquipmentDataModel.setSerialNumber(strACSerialNum);
        selectedEquipmentDataModel.setDateOfManufacturing(strACYearOfManufacturing);
        selectedEquipmentDataModel.setDescription(strACDescription);
        selectedEquipmentDataModel.setPhoto1(strACPhoto1);
        selectedEquipmentDataModel.setPhoto2(strACPhoto2);
        selectedEquipmentDataModel.setPhoto1Name(strSiteId + "-AC-Sticker");
        selectedEquipmentDataModel.setPhoto2Name(strSiteId + "-AC-Air-Con");
        selectedEquipmentDataModel.setType(strACType);
        selectedEquipmentDataModel.setNumberOfAC(strACNumberOfAC);
        selectedEquipmentDataModel.setEquipmentMake(strACMake);
        selectedEquipmentDataModel.setEquipmentModel(strACModel);
        selectedEquipmentDataModel.setEquipmentCapacity(strACCapacity);
        selectedEquipmentDataModel.setUserId(strACUserId);
        selectedEquipmentDataModel.setMakeId(strACMakeId);
        selectedEquipmentDataModel.setModelId(strACModelId);
        selectedEquipmentDataModel.setDescription(strACDescriptionId);
        selectedEquipmentDataList.add(selectedEquipmentDataModel);
//----------------------------Adding SMPS Data-----------------------------------------
        selectedEquipmentDataModel = new SelectedEquipmentDataModel();
        selectedEquipmentDataModel.setEquipmentType("SMPS");
        String strSmpsUserId = pref.getString("USER_ID", "");
        String strSmpsMake = pref.getString("SMPS_Make", "");
        String strSmpsModel = pref.getString("SMPS_Model", "");
        String strSmpsCapacity = pref.getString("SMPS_Capacity", "");
        String strSmpsMakeId = pref.getString("SMPS_MakeId", "");
        String strSmpsModelId = pref.getString("SMPS_ModelId", "");
        String strSmpsDescriptionId = pref.getString("SMPS_DescriptionId", "");
        String strSmpsSerialNum = pref.getString("SMPS_SerialNum", "");
        String strSmpsYearOfManufacturing = pref.getString("SMPS_YearOfManufacturing", "");
        String strSmpsDescription = pref.getString("SMPS_Description", "");
        String strSmpsPhoto1 = pref.getString("SMPS_Photo1", "");
        String strSmpsPhoto2 = pref.getString("SMPS_Photo2", "");
        String strSmpsSavedDateTime = pref.getString("SMPS_SavedDateTime", "");
        selectedEquipmentDataModel.setSerialNumber(strSmpsSerialNum);
        selectedEquipmentDataModel.setDateOfManufacturing(strSmpsYearOfManufacturing);
        selectedEquipmentDataModel.setDescription(strSmpsDescription);
        selectedEquipmentDataModel.setPhoto1(strSmpsPhoto1);
        selectedEquipmentDataModel.setPhoto2(strSmpsPhoto2);
        selectedEquipmentDataModel.setPhoto1Name(strSiteId + "-SMPS-SMPS_Shelf");
        selectedEquipmentDataModel.setPhoto2Name(strSiteId + "-SMPS-SMPS");
        selectedEquipmentDataModel.setType("NA");
        selectedEquipmentDataModel.setNumberOfAC("NA");
        selectedEquipmentDataModel.setEquipmentMake(strSmpsMake);
        selectedEquipmentDataModel.setEquipmentModel(strSmpsModel);
        selectedEquipmentDataModel.setEquipmentCapacity(strSmpsCapacity);
        selectedEquipmentDataModel.setUserId(strSmpsUserId);
        selectedEquipmentDataModel.setMakeId(strSmpsMakeId);
        selectedEquipmentDataModel.setModelId(strSmpsModelId);
        selectedEquipmentDataModel.setDescription(strSmpsDescriptionId);
        selectedEquipmentDataList.add(selectedEquipmentDataModel);
        //----------------------------Adding PIU/Voltage Stablizer Data-----------------------------------------
        selectedEquipmentDataModel = new SelectedEquipmentDataModel();
        String strPiuUserId = pref.getString("USER_ID", "");
        String strPiuMake = pref.getString("PIU_Make", "");
        String strPiuModel = pref.getString("PIU_Model", "");
        String strPiuCapacity = pref.getString("PIU_Capacity", "");
        String strPiuDGMakeId = pref.getString("PIU_MakeId", "");
        String strPiuModelId = pref.getString("PIU_ModelId", "");
        String strPiuDescriptionId = pref.getString("PIU_DescriptionId", "");
        String strPiuSerialNum = pref.getString("PIU_SerialNum", "");
        String strPiuYearOfManufacturing = pref.getString("PIU_YearOfManufacturing", "");
        String strPiuDescription = pref.getString("PIU_Description", "");
        String strPiuPhoto1 = pref.getString("PIU_Photo1", "");
        String strPiuPhoto2 = pref.getString("PIU_Photo2", "");
        String strPiuSavedDateTime = pref.getString("PIU_SavedDateTime", "");
        selectedEquipmentDataModel.setEquipmentType("PIU");
        selectedEquipmentDataModel.setSerialNumber(strPiuSerialNum);
        selectedEquipmentDataModel.setDateOfManufacturing(strPiuYearOfManufacturing);
        selectedEquipmentDataModel.setDescription(strPiuDescription);
        selectedEquipmentDataModel.setPhoto1(strPiuPhoto1);
        selectedEquipmentDataModel.setPhoto2(strPiuPhoto2);
        selectedEquipmentDataModel.setPhoto1Name(strSiteId + "-PIU/-SMPS_Shelf");
        selectedEquipmentDataModel.setPhoto2Name(strSiteId + "-SMPS-SMPS");
        selectedEquipmentDataModel.setType("NA");
        selectedEquipmentDataModel.setNumberOfAC("NA");
        selectedEquipmentDataModel.setEquipmentMake(strPiuMake);
        selectedEquipmentDataModel.setEquipmentModel(strPiuModel);
        selectedEquipmentDataModel.setEquipmentCapacity(strPiuCapacity);
        selectedEquipmentDataModel.setUserId(strPiuUserId);
        selectedEquipmentDataModel.setMakeId(strPiuDGMakeId);
        selectedEquipmentDataModel.setModelId(strPiuModelId);
        selectedEquipmentDataModel.setDescription(strPiuDescription);
        selectedEquipmentDataList.add(selectedEquipmentDataModel);
        atmDatabase.addEquipmentFormData(selectedEquipmentDataList);
        clearPrefrences();
    }*/

    /*public void clearPrefrences() {
        SharedPreferences.Editor editor = pref.edit();
        //-------------------Basic Data Activity----------------------------------
        editor.putString("Date", "");
        editor.putString("Time", "");
        editor.putString("SurveyorName", "");
        editor.putString("SiteId", "");
        editor.putString("SiteCustomerId", "");
        editor.putString("SiteName", "");
        editor.putString("Address", "");
        editor.putString("CircleId", "");
        editor.putString("SSAId", "");
        editor.putString("DistrictId", "");
        editor.putString("CirclePosition", "");
        editor.putString("SSAPosition", "");
        editor.putString("DistrictPosition", "");
        editor.putString("City", "");
        editor.putString("Pincode", "");
        editor.putString("MilliSeconds", String.valueOf(""));
        //-----------------Battery Activity------------------------------------
        editor.putString("Make", "");
        editor.putString("Model", "");
        editor.putString("Capacity", "");
        editor.putString("DescriptionId", "");
        editor.putString("MakeId", "");
        editor.putString("ModelId", "");
        editor.putString("SerialNum", "");
        editor.putString("YearOfManufacturing", "");
        editor.putString("Description", "");
        editor.putString("Photo1", "");
        editor.putString("Photo2", "");
        editor.putString("BbActivitySavedDateTime", "");
        //---------------DG Activity------------------------------------------
        editor.putString("DG_Make", "");
        editor.putString("DG_Model", "");
        editor.putString("DG_Capacity", "");
        editor.putString("DescriptionId", "");
        editor.putString("MakeId", "");
        editor.putString("ModelId", "");
        editor.putString("DG_SerialNum", "");
        editor.putString("DG_YearOfManufacturing", "");
        editor.putString("DG_Description", "");
        editor.putString("DG_Photo1", "");
        editor.putString("DG_Photo2", "");
        editor.putString("DG_SavedDateTime", "");
        //---------------AC Activity------------------------------------------
        editor.putString("AC_Make", "");
        editor.putString("AC_Model", "");
        editor.putString("AC_Capacity", "");
        editor.putString("AC_DescriptionId", "");
        editor.putString("AC_MakeId", "");
        editor.putString("AC_ModelId", "");
        editor.putString("AC_SerialNum", "");
        editor.putString("AC_YearOfManufacturing", "");
        editor.putString("AC_Description", "");
        editor.putString("AC_Photo1", "");
        editor.putString("AC_Photo2", "");
        editor.putString("AC_SavedDateTime", "");
        editor.putString("AC_Type", "");
        editor.putString("AC_Number", "");
        //---------------PIU Activity------------------------------------------
        editor.putString("PIU_Make", "");
        editor.putString("PIU_Model", "");
        editor.putString("PIU_Capacity", "");
        editor.putString("PIU_DescriptionId", "");
        editor.putString("PIU_MakeId", "");
        editor.putString("PIU_ModelId", "");
        editor.putString("PIU_SerialNum", "");
        editor.putString("PIU_YearOfManufacturing", "");
        editor.putString("PIU_Description", "");
        editor.putString("PIU_Photo1", "");
        editor.putString("PIU_Photo2", "");
        editor.putString("PIU_SavedDateTime", "");
        //---------------SMPS Activity------------------------------------------
        editor.putString("SMPS_Make", "");
        editor.putString("SMPS_Model", "");
        editor.putString("SMPS_Capacity", "");
        editor.putString("SMPS_DescriptionId", "");
        editor.putString("SMPS_MakeId", "");
        editor.putString("SMPS_ModelId", "");
        editor.putString("SMPS_SerialNum", "");
        editor.putString("SMPS_YearOfManufacturing", "");
        editor.putString("SMPS_Description", "");
        editor.putString("SMPS_Photo1", "");
        editor.putString("SMPS_Photo2", "");
        editor.putString("SMPS_SavedDateTime", "");
        //------------------EB Meter-------------------------------------------
        editor.putString("MeterReading", "");
        editor.putString("MeterNumber", "");
        editor.putString("AvailableHours", "");
        editor.putString("SupplySinglePhase", "");
        editor.putString("SupplyThreePhase", "");
        editor.putString("Photo1", "");
        editor.putString("Photo2", "");
        editor.putString("EbMeterSavedDateTime", "");
        //-----------------Site On Battery Bank--------------------------------
        editor.putString("Voltage", "");
        editor.putString("Current", "");
        editor.putString("SiteOnBBSavedDateTime", "");
        //------------------Site On DG Set------------------------------------
        editor.putString("DgCurrent", "");
        editor.putString("DgFrequency", "");
        editor.putString("DgVoltage", "");
        editor.putString("BatteryChargeCurrent", "");
        editor.putString("BatteryVoltage", "");
        editor.putString("SetOnDGSavedDateTime", "");
        //---------------------Site On EB --------------------------------------
        editor.commit();
    }*/

}
