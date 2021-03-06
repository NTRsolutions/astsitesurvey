package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.utils.ASTObjectUtil;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.ASTUtil;
import com.telecom.ast.sitesurvey.utils.FilePickerHelper;
import com.telecom.ast.sitesurvey.utils.FontManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class BatteryFragment extends MainFragment {

    private ImageView batteryimg, cellImg, sNoPlateImg;
    private File batteryimgFile, cellImgFile, sNoPlateImgImgFile;
    private boolean isImage1, isImage2;
    private AppCompatAutoCompleteTextView etMake;
    private AppCompatEditText etDescription, etNoofCell, etCellVoltage, etNoofWeakCells, etBackUpinHrs,
            etCellInterconnecting;
    AppCompatEditText etTorqueTightnes;
    private AppCompatAutoCompleteTextView etModel, etCapacity, etSerialNum;
    private String strUserId, strSiteId, itemCondition, CurtomerSite_Id;
    private String NoofItems = "0", NoofCell = "0", CellVoltage = "0", NoofWeakCells = "0", BackUpinHrs = "0",
            TightnessofBentCaps = "0", CellInterconnecting = "0";
    private String strMakeId = "0", strEqupId = "0";
    private ArrayList<EquipMakeDataModel> equipMakeList;
    private ArrayList<EquipMakeDataModel> equipList;
    private AtmDatabase atmDatabase;
    private String[] arrMake;
    private ArrayList<EquipCapacityDataModel> equipCapacityList;
    private String[] arrCapacity;
    private Spinner itemConditionSpinner;
    private String make = "", model = "", capacity = "", serialNumber = "", yearOfManufacturing = "", description = "", currentDateTime = "";
    private LinearLayout descriptionLayout;
    private Spinner itemStatusSpineer, etTightnessofBentCaps;
    private SharedPreferences userPref;
    private TextView etYear, dateIcon;
    private Typeface materialdesignicons_font;
    private LinearLayout dateLayout;
    private long datemilisec;
    private String capcityId = "0";
    private boolean isLast = false;
    private int screenPosition = 1;
    private Button btnSubmmit;
    private String itemstatus;
    private boolean isFaulty;
    private CardView image1ImageCardview, image12ImageCardview, image3ImageCardview;
    private TextView frontPhotolabl;
    private LinearLayout lnrDynamicHolderBBCellsno;
    private String nofoBBCellSno, NoofWeakCellssNo;
    private List<EditText> allBBCELLSnoEditTextList;
    private List<EditText> allBBWeakCELLSnoEditTextList;
    private LinearLayout lnrDynamicHolderweKBBCellsno;
    private String strTorqueTightnes;
    private String SEPARATORComma = ",";

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_battery;
    }

    @Override
    protected void loadView() {
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
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        etNoofCell = findViewById(R.id.etNoofCell);
        etCellVoltage = findViewById(R.id.etCellVoltage);
        etNoofWeakCells = findViewById(R.id.etNoofWeakCells);
        etBackUpinHrs = findViewById(R.id.etBackUpinHrs);
        etTightnessofBentCaps = findViewById(R.id.etTightnessofBentCaps);
        etCellInterconnecting = findViewById(R.id.etCellInterconnecting);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
        btnSubmmit = findViewById(R.id.btnSubmmit);
        image1ImageCardview = findViewById(R.id.image1ImageCardview);
        image12ImageCardview = findViewById(R.id.image2ImageCardview);
        image3ImageCardview = findViewById(R.id.image3ImageCardview);
        frontPhotolabl = findViewById(R.id.frontPhotolabl);
        lnrDynamicHolderBBCellsno = findViewById(R.id.lnrDynamicHolderBBCellsno);
        lnrDynamicHolderweKBBCellsno = findViewById(R.id.lnrDynamicHolderweKBBCellsno);
        etTorqueTightnes = findViewById(R.id.etTorqueTightnes);

    }


    @Override
    protected void setClickListeners() {
        batteryimg.setOnClickListener(this);
        cellImg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        dateLayout.setOnClickListener(this);
        btnSubmmit.setOnClickListener(this);
    }


    @Override
    protected void setAccessibility() {

    }

    DatePickerDialog datePickerDialog;

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
                DatePickerDialog dpDialog = new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpDialog.show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Contants.LOG_TAG, "onResume" + screenPosition);
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
        equipList = atmDatabase.getEquipmentData("BB");
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "BB");
        arrMake = new String[equipMakeList.size()];
        for (int i = 0; i < equipMakeList.size(); i++) {
            arrMake[i] = equipMakeList.get(i).getName();
        }
        getUserPref();
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

        itemConditionSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                descriptionLayout.setVisibility(selectedItem.equalsIgnoreCase("Fully Fault") ? View.VISIBLE : View.GONE);


                isFaulty = ASTObjectUtil.isEmptyStr(description) &&
                        itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")
                        || itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Not Ok");
                image12ImageCardview.setVisibility(isFaulty ? View.INVISIBLE : View.VISIBLE);
                image3ImageCardview.setVisibility(isFaulty ? View.GONE : View.VISIBLE);
                frontPhotolabl.setText(isFaulty ? "Faulty Photo" : "Photo With Equipment Specification");

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
                    etNoofCell.setEnabled(false);
                    etCellVoltage.setEnabled(false);
                    etNoofWeakCells.setEnabled(false);
                    etBackUpinHrs.setEnabled(false);
                    etTightnessofBentCaps.setEnabled(false);
                    etCellInterconnecting.setEnabled(false);
                    etTorqueTightnes.setEnabled(false);
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
                    etNoofCell.setEnabled(true);
                    etCellVoltage.setEnabled(true);
                    etNoofWeakCells.setEnabled(true);
                    etBackUpinHrs.setEnabled(true);
                    etTightnessofBentCaps.setEnabled(true);
                    etCellInterconnecting.setEnabled(true);
                    etTorqueTightnes.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setnofBBCellSNo();
        setnofBBweakCellSNo();
    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", getContext().MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dateLayout) {
            setDateofSiteonAir();
        } else if (view.getId() == R.id.image1) {
            String imageName;
            if (itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
                imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_FaultyPhoto.jpg";
            } else {
                imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_EquipmentSepcificationPhoto.jpg";
            }
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = true;
            isImage2 = false;
        } else if (view.getId() == R.id.image2) {
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_CompleteBatteryBankPhoto.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = false;
            isImage2 = true;
        } else if (view.getId() == R.id.image3) {
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_SerialNoPlate.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = false;
            isImage2 = false;
        } else if (view.getId() == R.id.btnSubmmit) {
            if (isValidate()) {
                saveBatteryDataonServer();
            }
        }

    }


    // ----validation -----
    private boolean isValidate() {
        String twoDecimalRegExp = "^[0-9]{0,2}(\\.[0-9]{0,2})?$";
        itemstatus = itemStatusSpineer.getSelectedItem().toString();
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
            make = getTextFromView(this.etMake);
            model = getTextFromView(this.etModel);
            capacity = getTextFromView(this.etCapacity);
            serialNumber = getTextFromView(this.etSerialNum);
            yearOfManufacturing = getTextFromView(this.etYear);
            itemCondition = itemConditionSpinner.getSelectedItem().toString();
            description = getTextFromView(this.etDescription);
            currentDateTime = String.valueOf(System.currentTimeMillis());
            CellVoltage = getTextFromView(this.etCellVoltage);
            NoofCell = getTextFromView(this.etNoofCell);
            NoofWeakCells = getTextFromView(this.etNoofWeakCells);
            BackUpinHrs = getTextFromView(this.etBackUpinHrs);
            TightnessofBentCaps = etTightnessofBentCaps.getSelectedItem().toString();
            CellInterconnecting = getTextFromView(this.etCellInterconnecting);
            strTorqueTightnes = getTextFromView(this.etTorqueTightnes);



        /*    NoofCell = getTextFromView(this.etNoofCell);
            if (NoofCell.equals("")) {
                NoofCell = "0";
            }
            CellVoltage = getTextFromView(this.etCellVoltage);
            if (CellVoltage.equals("")) {
                CellVoltage = "0";
            }
            NoofWeakCells = getTextFromView(this.etNoofWeakCells);
            if (NoofWeakCells.equals("")) {
                NoofWeakCells = "0";
            }
            BackUpinHrs = getTextFromView(this.etBackUpinHrs);
            if (BackUpinHrs.equals("")) {
                BackUpinHrs = "0";
            }
            TightnessofBentCaps = getTextFromView(this.etTightnessofBentCaps);
            if (TightnessofBentCaps.equals("")) {
                TightnessofBentCaps = "0";
            }
            CellInterconnecting = getTextFromView(this.etCellInterconnecting);
            if (CellInterconnecting.equals("")) {
                CellInterconnecting = "0";
            }*/
            if (ASTObjectUtil.isEmptyStr(make)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Make");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(model)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Model");
                return false;
            } /*else if (ASTObjectUtil.isEmptyStr(serialNumber)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Serial Number");
                return false;
            }*/ else if (ASTObjectUtil.isEmptyStr(capacity)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(yearOfManufacturing)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Manufacturing Date");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(itemCondition)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Item Condition");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(description) && itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(CellVoltage)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter BB Voltage");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(NoofCell)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of Cell");
                return false;
            }/* else if (ASTObjectUtil.isEmptyStr(NoofWeakCells)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No Of Weak Cells");
                return false;
            }*/ else if (ASTObjectUtil.isNonEmptyStr(NoofWeakCells) && !isMatchWeakCellSno()) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Valid Weak Cell Sno");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(BackUpinHrs)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Back Up in Hrs");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(TightnessofBentCaps)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Tightness Of Bent Caps");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(CellInterconnecting)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Cell Interconnecting Strip Tightness");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(strTorqueTightnes)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter BB Bolts Torque Tightness (NM)");
                return false;
            } else if (!CellVoltage.matches(twoDecimalRegExp)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please enter valid Cell Voltage input like this xx.xx");
                return false;
            } else if (batteryimgFile == null || !batteryimgFile.exists()) {
                if (isFaulty) {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Battery Bank Faulty Photo");
                } else {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Battery Bank Photo");
                }

                return false;
            } else if (!isFaulty) {
                if (cellImgFile == null || !cellImgFile.exists()) {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select One Cell Photo");
                    return false;
                }

            } else if (!isFaulty) {
                if (sNoPlateImgImgFile == null || !sNoPlateImgImgFile.exists()) {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Sr no  PhoPlateto");
                    return false;
                }
            }

        } else {
            ASTUIUtil.showToast("Item Not Available");
        }
        return true;
    }

    //get make and equpment id from  list
    private void getMakeAndEqupmentId() {
        for (EquipMakeDataModel dataModel : equipMakeList) {
            if (make.equals(dataModel.getName().trim())) {
                strMakeId = dataModel.getId();
            }
        }
        //get equpment id from equpiment list
        for (EquipMakeDataModel dataModel : equipList) {
            strEqupId = dataModel.getId();
        }
//get Capcity id
        if (equipCapacityList != null && equipCapacityList.size() > 0) {
            for (int i = 0; i < equipCapacityList.size(); i++) {
                if (capacity.equals(equipCapacityList.get(i).getName())) {
                    capcityId = equipCapacityList.get(i).getId();
                }
            }
        }
    }

    //save screen data into battery home screen through Broadcast
    private JSONObject makeJsonData() {
        JSONObject mainObj = new JSONObject();
        try {
            getMakeAndEqupmentId();
            mainObj.put("Site_ID", strSiteId);
            mainObj.put("User_ID", strUserId);
            mainObj.put("Activity", "Equipment");
            JSONObject EquipmentData = new JSONObject();
            EquipmentData.put("EquipmentStatus", itemstatus);
            EquipmentData.put("EquipmentSno", screenPosition);
            EquipmentData.put("EquipmentID", strEqupId);
            EquipmentData.put("CapacityID", capcityId);
            EquipmentData.put("MakeID", strMakeId);
            EquipmentData.put("Make", make);
            EquipmentData.put("Equipment", "BB");
            EquipmentData.put("Capacity", capacity);
            EquipmentData.put("SerialNo", serialNumber);
            EquipmentData.put("MfgDate", datemilisec);
            EquipmentData.put("ItemCondition", itemCondition);
            EquipmentData.put("BB_NoofCell", NoofCell);
            EquipmentData.put("BB_CellSerialNo", nofoBBCellSno);
            EquipmentData.put("BB_BattVoltage", CellVoltage);
            EquipmentData.put("BB_CellVoltage", 0);
            EquipmentData.put("BB_NoofWeakCells", NoofWeakCells);
            EquipmentData.put("BB_WeakCellSerialNo", NoofWeakCellssNo);
            EquipmentData.put("BB_BackUp", BackUpinHrs);
            EquipmentData.put("BB_TightnessofBentCaps", TightnessofBentCaps);
            EquipmentData.put("BB_CellInterconnectingstriptightness", CellInterconnecting);
            EquipmentData.put("BB_BoltsTorqueTightness", strTorqueTightnes);

            JSONArray equipArray = new JSONArray();
            equipArray.put(EquipmentData);
            mainObj.put("EquipmentData", equipArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainObj;
    }

    //add images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (batteryimgFile != null && batteryimgFile.exists()) {
            multipartBody.addFormDataPart(batteryimgFile.getName(), batteryimgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, batteryimgFile));
        }
        if (cellImgFile != null && cellImgFile.exists()) {
            multipartBody.addFormDataPart(cellImgFile.getName(), cellImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, cellImgFile));
        }
        if (sNoPlateImgImgFile != null && sNoPlateImgImgFile.exists()) {
            multipartBody.addFormDataPart(sNoPlateImgImgFile.getName(), sNoPlateImgImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sNoPlateImgImgFile));
        }
        return multipartBody;
    }

    //save battery data into server
    public void saveBatteryDataonServer() {
        if (ASTUIUtil.isOnline(getContext())) {
            getBBCellSno();
            getBBweakCellSno();
            final ASTProgressBar progressBar = new ASTProgressBar(getContext());
            progressBar.show();
            String serviceURL = Constant.BASE_URL + Constant.SurveyDataSave;
            JSONObject mainObj = makeJsonData();
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("JsonData", mainObj.toString());
            FileUploaderHelper fileUploaderHelper = new FileUploaderHelper(getContext(), payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        if (data.getStatus() == 1) {
                            ASTUIUtil.showToast("Your Battery Data save Successfully");
                            if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
                                showAddMoreItemDialog();
                            } else {
                                reloadBackScreen();
                            }

                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Battery Data  has not been updated!");
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

    public void showAddMoreItemDialog() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        android.support.v7.app.AlertDialog dialog = builder.create();
        //dialog.getWindow().getAttributes().windowAnimations = R.style.alertAnimation;
        dialog.setMessage("Do you want do add more Battery Item Details");
        dialog.setTitle("Battery Alert");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Add More", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearFiledData();
                screenPosition = screenPosition + 1;
            }
        });
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reloadBackScreen();
            }
        });
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            onCaptureImageResult();
        }
    }

    //capture image compress
    private void onCaptureImageResult() {
        if (isImage1) {
            String imageName;
            if (itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
                imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_FaultyPhoto.jpg";
            } else {
                imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_EquipmentSepcificationPhoto.jpg";
            }

            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, batteryimg, imageName);
            }
        } else if (isImage2) {
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_CompleteBatteryBankPhoto.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, cellImg, imageName);
            }
        } else {
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_SerialNoPlate.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, sNoPlateImg, imageName);
            }
        }
    }

    //compres image
    private void compresImage(final File file, final String fileName, final ImageView imageView, final String imageName) {
        new AsyncTask<Void, Void, Boolean>() {
            File finalimgFile;
            Uri uri;
            ASTProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = new ASTProgressBar(getContext());
                progressBar.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
//compress file
                Boolean flag = false;
                int ot = FilePickerHelper.getExifRotation(file);
                Bitmap bitmap = FilePickerHelper.compressImage(file.getAbsolutePath(), ot, 800.0f, 800.0f, imageName);
                if (bitmap != null) {
                    uri = FilePickerHelper.getImageUri(getContext(), bitmap);
//save compresed file into location
                    finalimgFile = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator, fileName);
                    if (finalimgFile.exists()) {
                        finalimgFile.delete();
                    }
                    try {
                        InputStream iStream = getContext().getContentResolver().openInputStream(uri);
                        byte[] inputData = FilePickerHelper.getBytes(iStream);

                        FileOutputStream fOut = new FileOutputStream(finalimgFile);
                        fOut.write(inputData);
                        //   bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        iStream.close();
                        flag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                // imageView.setImageBitmap(bitmap);
                if (isImage1) {
                    batteryimgFile = finalimgFile;
                    imageView.setImageURI(uri);
                    //Picasso.with(ApplicationHelper.application().getContext()).load(batteryimgFile).into(imageView);

                } else if (isImage2) {
                    cellImgFile = finalimgFile;
                    imageView.setImageURI(uri);
                    // Picasso.with(ApplicationHelper.application().getContext()).load(cellImgFile).into(imageView);
                } else {
                    sNoPlateImgImgFile = finalimgFile;
                    imageView.setImageURI(uri);
                    //Picasso.with(ApplicationHelper.application().getContext()).load(sNoPlateImgImgFile).memoryPolicy(MemoryPolicy.NO_CACHE).into(imageView);
                }
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        }.execute();

    }

    public void clearFiledData() {
        etMake.setText("");
        etModel.setText("");
        etCapacity.setText("");
        etSerialNum.setText("");
        etYear.setText("");
        etDescription.setText("");
        etNoofCell.setText("");
        etCellVoltage.setText("");
        etNoofWeakCells.setText("");
        etBackUpinHrs.setText("");
        etTightnessofBentCaps.setSelection(0);
        etCellInterconnecting.setText("");
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(batteryimg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(cellImg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(sNoPlateImg);
        itemConditionSpinner.setSelection(0);
        etTorqueTightnes.setText("");
    }

    @Override
    public boolean onBackPressed() {
        return isGoBack();
    }

    boolean isgoBack;

    private boolean isGoBack() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you Sure you want go to Back Screen");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                reloadBackScreen();
                isgoBack = true;
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                isgoBack = false;
                dialog.dismiss();

            }
        });
        alertDialog.show();
        return isgoBack;
    }


    /**
     * make run time view for no of BB  Cell
     */
    public void setnofBBCellSNo() {
        etNoofCell.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (etNoofCell.getText().toString().length() > 0) {
                    allBBCELLSnoEditTextList = new ArrayList<EditText>();
                    try {
                        lnrDynamicHolderBBCellsno.removeAllViews();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    int length = Integer.parseInt(etNoofCell.getText().toString());

                    for (int i = 0; i < length; i++) {
                        AppCompatEditText editText = new AppCompatEditText(getContext());
                        allBBCELLSnoEditTextList.add(editText);
                        editText.setId(i + 1);
                        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        editText.setHint("BB cell  \t" + (i + 1) + "\t Serial No.");
                        lnrDynamicHolderBBCellsno.addView(editText);
                    }

                }
            }
        });
    }


    /**
     * make run time view for no of BB weak Cell
     */
    public void setnofBBweakCellSNo() {
        etNoofWeakCells.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (etNoofWeakCells.getText().toString().length() > 0) {
                    allBBWeakCELLSnoEditTextList = new ArrayList<EditText>();
                    try {
                        lnrDynamicHolderweKBBCellsno.removeAllViews();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    int length = Integer.parseInt(etNoofWeakCells.getText().toString());

                    for (int i = 0; i < length; i++) {
                        AppCompatEditText editText = new AppCompatEditText(getContext());
                        allBBWeakCELLSnoEditTextList.add(editText);
                        editText.setId(i + 1);
                        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        editText.setHint("BB Weak cell  \t" + (i + 1) + "\t Serial No.");
                        lnrDynamicHolderweKBBCellsno.addView(editText);
                    }

                }
            }
        });
    }



    public void getBBCellSno() {
        StringBuilder nofoBBCellSnoBuilder = new StringBuilder();
        for (int i = 0; i < allBBCELLSnoEditTextList.size(); i++) {
            nofoBBCellSnoBuilder.append(allBBCELLSnoEditTextList.get(i).getText().toString());
            nofoBBCellSnoBuilder.append(SEPARATORComma);
        }
        nofoBBCellSno = nofoBBCellSnoBuilder.toString();
        nofoBBCellSno = nofoBBCellSno.substring(0, nofoBBCellSno.length() - SEPARATORComma.length()); //Remove last comma

    }

    public void getBBweakCellSno() {
        StringBuilder nofoBBWeakCellSnoBuilder = new StringBuilder();
        for (int i = 0; i < allBBWeakCELLSnoEditTextList.size(); i++) {
            nofoBBWeakCellSnoBuilder.append(allBBWeakCELLSnoEditTextList.get(i).getText().toString());
            nofoBBWeakCellSnoBuilder.append(SEPARATORComma);
        }
        NoofWeakCellssNo = nofoBBWeakCellSnoBuilder.toString();
        NoofWeakCellssNo = NoofWeakCellssNo.substring(0, NoofWeakCellssNo.length() - SEPARATORComma.length()); //Remove last comma


    }


    public boolean isMatchWeakCellSno() {
        for (int i = 0; i < allBBWeakCELLSnoEditTextList.size(); i++) {
            for (int j = 0; j < allBBCELLSnoEditTextList.size(); j++) {
                return allBBWeakCELLSnoEditTextList.get(i).getText().toString().contains(allBBCELLSnoEditTextList.get(j).getText().toString());
            }

        }
        return false;
    }

}

