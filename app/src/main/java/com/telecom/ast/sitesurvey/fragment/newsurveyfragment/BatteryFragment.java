package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTErrorIndicator;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.component.FNEditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class BatteryFragment extends MainFragment {
    static ImageView batteryimg, cellImg, sNoPlateImg;
    private File batteryimgFile, cellImgFile, sNoPlateImgImgFile;
    static boolean isImage1, isImage2;
    AppCompatAutoCompleteTextView etMake;
    AppCompatEditText etDescription, etNoofItems, etNoofCell, etCellVoltage, etNoofWeakCells, etBackUpinHrs,
            etTightnessofBentCaps, etCellInterconnecting;
    AppCompatAutoCompleteTextView etModel, etCapacity, etSerialNum;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription;
    String strSavedDateTime, strUserId, strSiteId, strDescriptionId, itemCondition, CurtomerSite_Id;
    String NoofItems, NoofCell, CellVoltage, NoofWeakCells, BackUpinHrs,
            TightnessofBentCaps, CellInterconnecting;
    String strMakeId, strModelId;
    ArrayList<EquipMakeDataModel> equipMakeList;
    AtmDatabase atmDatabase;
    String[] arrMake;
    String[] arrModel;
    ArrayList<EquipCapacityDataModel> equipCapacityList;
    String[] arrCapacity;
    Spinner itemConditionSpinner;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime;
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemStatusSpineer;
    SharedPreferences batterySharedPref, userPref;
    TextView etYear, dateIcon;
    Typeface materialdesignicons_font;
    LinearLayout dateLayout;
    long datemilisec;
    String capcityId="0";
    @Override
    protected int fragmentLayout() {
        return R.layout.activity_battery;
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
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        etNoofItems = findViewById(R.id.etNoofItems);
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
    }


    @Override
    protected void setClickListeners() {
        batteryimg.setOnClickListener(this);
        cellImg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        dateLayout.setOnClickListener(this);
    }


    @Override
    protected void setAccessibility() {

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
  /*      final SimpleDateFormat sdfTime = new SimpleDateFormat("HH.mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                timeView.setText(sdfTime.format(myCalendar.getTime()));
            }
        };
        timeView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new TimePickerDialog(getContext(), time, myCalendar
                        .get(Calendar.HOUR_OF_DAY), myCalendar
                        .get(Calendar.MINUTE), true).show();
            }
        });*/
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
        atmDatabase = new AtmDatabase(getContext());
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "BB");
        arrMake = new String[equipMakeList.size()];
        for (int i = 0; i < equipMakeList.size(); i++) {
            arrMake[i] = equipMakeList.get(i).getName();
        }
        getSharedprefData();
        getUserPref();
        setSpinnerValue();
        equipCapacityList = new ArrayList<>();
        ArrayAdapter<String> adapterMakeName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrMake);
        etMake.setAdapter(adapterMakeName);
        etMake.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String strMake = etMake.getText().toString();
                if (!strMake.equals("") && strMake.length() > 1) {
                    ArrayList<EquipCapacityDataModel> equipCapacityDataList = atmDatabase.getEquipmentCapacityData("DESC", strMake);
                    if (equipCapacityDataList.size() > 0) {
                        for (int i = 0; i < equipCapacityDataList.size(); i++) {
                            arrModel[i] = equipCapacityDataList.get(i).getName();
                             capcityId=equipCapacityDataList.get(i).getId();
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
                    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList = atmDatabase.getEquipmentDescriptionData("DESC", strModel);
                    arrCapacity = new String[equipDescriptionDataList.size()];
                    if (equipDescriptionDataList.size() > 0) {
                        for (int i = 0; i < equipDescriptionDataList.size(); i++) {
                            arrCapacity[i] = equipDescriptionDataList.get(i).getName();
                        }
                        ArrayAdapter<String> adapterCapacityName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrCapacity);
                        etCapacity.setAdapter(adapterCapacityName);
                    }
                }
            }
        });
        if (
                !isEmptyStr(strMake) || !isEmptyStr(strModel) || !isEmptyStr(strCapacity) || !isEmptyStr(strSerialNum)
                        || !isEmptyStr(strYearOfManufacturing) || !isEmptyStr(strDescription)
                        || !isEmptyStr(NoofItems)
                        || !isEmptyStr(NoofCell)
                        || !isEmptyStr(CellVoltage)
                        || !isEmptyStr(NoofWeakCells)
                        || !isEmptyStr(BackUpinHrs)
                        || !isEmptyStr(TightnessofBentCaps)
                        || !isEmptyStr(CellInterconnecting)
                ) {
            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            etNoofItems.setText(NoofItems);
            etNoofCell.setText(NoofCell);
            etCellVoltage.setText(CellVoltage);
            etNoofWeakCells.setText(BackUpinHrs);
            etBackUpinHrs.setText(BackUpinHrs);
            etTightnessofBentCaps.setText(TightnessofBentCaps);
            etCellInterconnecting.setText(CellInterconnecting);


            /*if (!bateryphoto.equals("") || !cellPhoto.equals("") || !sNoPlatephoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(bateryphoto)).placeholder(R.drawable.noimage).into(batteryimg);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(cellPhoto)).placeholder(R.drawable.noimage).into(cellImg);
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


                    etNoofItems.setEnabled(false);
                    etNoofCell.setEnabled(false);
                    etCellVoltage.setEnabled(false);
                    etNoofWeakCells.setEnabled(false);
                    etBackUpinHrs.setEnabled(false);
                    etTightnessofBentCaps.setEnabled(false);
                    etCellInterconnecting.setEnabled(false);
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

                    etNoofItems.setEnabled(true);
                    etNoofCell.setEnabled(true);
                    etCellVoltage.setEnabled(true);
                    etNoofWeakCells.setEnabled(true);
                    etBackUpinHrs.setEnabled(true);
                    etTightnessofBentCaps.setEnabled(true);
                    etCellInterconnecting.setEnabled(true);
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
 /*       batterySharedPref = getContext().getSharedPreferences("BatterySharedPref", MODE_PRIVATE);
        strUserId = batterySharedPref.getString("USER_ID", "");
        strMake = batterySharedPref.getString("Make", "");
        strModel = batterySharedPref.getString("Model", "");
        strCapacity = batterySharedPref.getString("Capacity", "");
        strMakeId = batterySharedPref.getString("MakeId", "");
        strModelId = batterySharedPref.getString("ModelId", "");
        strDescriptionId = batterySharedPref.getString("DescriptionId", "");
        strSerialNum = batterySharedPref.getString("SerialNum", "");
        strYearOfManufacturing = batterySharedPref.getString("YearOfManufacturing", "");
        strDescription = batterySharedPref.getString("Description", "");
        bateryphoto = batterySharedPref.getString("batryPhoto1", "");
        cellPhoto = batterySharedPref.getString("batryPhoto2", "");
        sNoPlatephoto = batterySharedPref.getString("batryPhoto3", "");
        strSavedDateTime = batterySharedPref.getString("BbActivitySavedDateTime", "");
        strSiteId = batterySharedPref.getString("SiteId", "");
        itemCondition = batterySharedPref.getString("ItemCondition", "");
        NoofItems = batterySharedPref.getString("NoofItems", "");
        NoofCell = batterySharedPref.getString("NoofCell", "");
        CellVoltage = batterySharedPref.getString("CellVoltage", "");
        NoofWeakCells = batterySharedPref.getString("NoofWeakCells", "");
        BackUpinHrs = batterySharedPref.getString("BackUpinHrs", "");
        TightnessofBentCaps = batterySharedPref.getString("TightnessofBentCaps", "");
        CellInterconnecting = batterySharedPref.getString("CellInterconnecting", "");*/


    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dateLayout) {
            setDateofSiteonAir();
        } else if (view.getId() == R.id.image1) {
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
                if (equipCapacityList != null && equipCapacityList.size() > 0) {
                    for (int i = 0; i < equipCapacityList.size(); i++) {
                        if (capacity.equals(equipCapacityList.get(i).getName())) {
                            strDescriptionId = equipCapacityList.get(i).getId();
                        }
                    }
                }

                if (equipMakeList != null && equipMakeList.size() > 0) {
                    for (int i = 0; i < equipMakeList.size(); i++) {
                        if (make.equals(equipMakeList.get(i).getName())) {
                            strMakeId = equipMakeList.get(i).getId();
                        }
                    }
                }
                if ( isEmptyStr(strMakeId) || strMakeId.equals("0")) {
                    strMakeId = "0";
                }
                if (equipCapacityList != null && equipCapacityList.size() > 0) {
                    for (int i = 0; i < equipCapacityList.size(); i++) {
                        if (make.equals(equipCapacityList.get(i).getName())) {
                            strModelId = equipCapacityList.get(i).getId();
                        }
                    }
                }
                if (isEmptyStr(strModelId) || strModelId.equals("0")) ;
                {
                    strModelId = "0";
                }
                saveBasicDataonServer();


            }

        }
    }


    // ----validation -----
    private boolean isValidate() {
        make = getTextFromView(this.etMake);
        model = getTextFromView(this.etCapacity);
        capacity = getTextFromView(this.etCapacity);
        serialNumber = getTextFromView(this.etSerialNum);
        yearOfManufacturing = getTextFromView(this.etYear);
        itemCondition = itemConditionSpinner.getSelectedItem().toString();
        description = getTextFromView(this.etDescription);
        currentDateTime = String.valueOf(System.currentTimeMillis());
        NoofItems = getTextFromView(this.etNoofItems);
        NoofCell = getTextFromView(this.etNoofCell);
        CellVoltage = getTextFromView(this.etCellVoltage);
        NoofWeakCells = getTextFromView(this.etNoofWeakCells);
        BackUpinHrs = getTextFromView(this.etBackUpinHrs);
        TightnessofBentCaps = getTextFromView(this.etTightnessofBentCaps);
        CellInterconnecting = getTextFromView(this.etCellInterconnecting);

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
            } else if (batteryimgFile == null || !batteryimgFile.exists()) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Battery Bank Photo");
                return false;
            } else if (cellImgFile == null || !cellImgFile.exists()) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select One Cell Photo");
                return false;
            } else if (sNoPlateImgImgFile == null || !sNoPlateImgImgFile.exists()) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Sr no Plate Photo");
                return false;
            }
        } else {
            ASTUIUtil.showToast("Item Not Available");
        }
        return true;
    }

    public void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1) {
                    String imageName = CurtomerSite_Id + "_BATTERY_2_Front.jpg";

                    batteryimgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(batteryimgFile).into(batteryimg);
                    //overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    String imageName = CurtomerSite_Id + "_BATTERY_2_Open.jpg";
                    cellImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(cellImgFile).into(cellImg);
                } else {
                    String imageName = CurtomerSite_Id + "_BATTERY_2_SerialNoPlate.jpg";
                    sNoPlateImgImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(sNoPlateImgImgFile).into(sNoPlateImg);

                }
            }
        }
    }


    public void getResult(ArrayList<MediaFile> files) {
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

    public void saveBasicDataonServer() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar progressBar = new ASTProgressBar(getContext());
            progressBar.show();
            String serviceURL = Constant.BASE_URL + Constant.SurveyDataSave;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Site_ID", strSiteId);
                jsonObject.put("User_ID", strUserId);
                jsonObject.put("Activity", "Equipment");
                JSONObject EquipmentData = new JSONObject();
                EquipmentData.put("EquipmentSno", "");
                EquipmentData.put("EquipmentID", "");
                EquipmentData.put("Equipment", "Battery");
                EquipmentData.put("MakeID", strMakeId);
                EquipmentData.put("Capacity_ID", "");
                EquipmentData.put("Capacity", capacity);
                EquipmentData.put("SerialNo", serialNumber);
                EquipmentData.put("MfgDate", yearOfManufacturing);
                EquipmentData.put("ItemCondition", itemCondition);
                EquipmentData.put("BB_NoofBB", NoofItems);
                EquipmentData.put("BB_NoofCell", NoofCell);
                EquipmentData.put("BB_CellVoltage", CellVoltage);
                EquipmentData.put("BB_NoofWeakCells", NoofWeakCells);
                EquipmentData.put("BB_BackUp", BackUpinHrs);
                EquipmentData.put("BB_TightnessofBentCaps", TightnessofBentCaps);
                EquipmentData.put("BB_CellInterconnectingstriptightness", CellInterconnecting);




            } catch (JSONException e) {
                e.printStackTrace();
            }
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("JsonData", jsonObject.toString());
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelper fileUploaderHelper = new FileUploaderHelper(getContext(), payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        if (data.getStatus() == 1) {
                            ASTUIUtil.showToast("Your Battery Data save Successfully");
                            reloadBackScreen();
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

    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (batteryimgFile.exists()) {
            multipartBody.addFormDataPart(batteryimgFile.getName(), batteryimgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, batteryimgFile));
        }
        if (cellImgFile.exists()) {
            multipartBody.addFormDataPart(cellImgFile.getName(), cellImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, cellImgFile));
        }
        if (sNoPlateImgImgFile.exists()) {
            multipartBody.addFormDataPart(sNoPlateImgImgFile.getName(), sNoPlateImgImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sNoPlateImgImgFile));
        }

        return multipartBody;
    }
}