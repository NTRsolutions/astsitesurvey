package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.telecom.ast.sitesurvey.utils.FNReqResCode;
import com.telecom.ast.sitesurvey.utils.FontManager;

import org.json.JSONArray;
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

public class IpmsFragment extends MainFragment {


    static ImageView frontimg, openImg, sNoPlateImg;
    static File frontimgFile, openImgFile, sNoPlateImgFile;
    static boolean isImage1, isImage2;
    AppCompatEditText etDescription;
    AppCompatAutoCompleteTextView etSerialNum, etCapacity, etMake, etnoofModule, etLcuCapacity, etModel, etModuleCapacity;
    SharedPreferences pref;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription, strnoofModule, strModuleCapacity, strlcucapacity;
    String strSavedDateTime, strUserId, strSiteId;
    String strMakeId, strModelId, strDescriptionId, nofModule, ModuleCapacity;
    String[] arrMake;
    String[] arrModel;
    String[] arrCapacity;
    ArrayList<EquipMakeDataModel> arrEquipData;
    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    AtmDatabase atmDatabase;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime, lcucapacity;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner;
    Button btnSubmit;
    AppCompatEditText etController, etConditionbackPlane, etBodyEarthing, etPositiveEarthing, etRatingofCable, etAlarmConnection,
            etNoofRMWorking, etNoofRMFaulty, etSpareFuseStatus;

    Spinner itemStatusSpineer;
    String Controller, ConditionbackPlane, BodyEarthing, PositiveEarthing, RatingofCable, AlarmConnection,
            NoofRMWorking, NoofRMFaulty, SpareFuseStatus, itemStatus, CurtomerSite_Id, itemCondition;


    SharedPreferences userPref;
    TextView etYear, dateIcon;
    Typeface materialdesignicons_font;
    LinearLayout dateLayout;
    long datemilisec;
    int EquipmentSno = 1;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_ipms;
    }

    @Override
    protected void loadView() {
        frontimg = findViewById(R.id.image1);
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
        etnoofModule = findViewById(R.id.etnoofModule);
        etModuleCapacity = findViewById(R.id.etModuleCapacity);
        etLcuCapacity = findViewById(R.id.etLcuCapacity);
        btnSubmit = findViewById(R.id.btnSubmit);
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
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
    }

    @Override
    protected void setClickListeners() {
        openImg.setOnClickListener(this);
        frontimg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        dateLayout.setOnClickListener(this);

    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }


    @Override
    protected void setAccessibility() {

    }

    /*
     *
     * Shared Prefrences---------------------------------------
     */

    public void getSharedPrefData() {
       /* pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("IPMS_Make", "");
        strModel = pref.getString("SMP_Model", "");
        strCapacity = pref.getString("IPMS_Capacity", "");
        strMakeId = pref.getString("IPMS_MakeId", "");
        strModelId = pref.getString("IPMS_ModelId", "");
        strDescriptionId = pref.getString("IPMS_DescriptionId", "");
        strSerialNum = pref.getString("IPMS_SerialNum", "");
        strYearOfManufacturing = pref.getString("IPMS_YearOfManufacturing", "");
        strDescription = pref.getString("IPMS_Description", "");
        strModuleCapacity = pref.getString("IPMS_ModuleCapacity", "");
        strlcucapacity = pref.getString("IPMS_Llcucapacity", "");
        strnoofModule = pref.getString("IPMS_noofModule", "");
        frontphoto = pref.getString("IPMS_Photo1", "");
        openPhoto = pref.getString("IPMS__Photo2", "");
        sNoPlatephoto = pref.getString("IPMS_Photo3", "");
        strSavedDateTime = pref.getString("IPMS_SavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");
        Controller = pref.getString("IPMS_Controller", "");
        ConditionbackPlane = pref.getString("IPMS_ConditionbackPlane", "");
        BodyEarthing = pref.getString("IPMS_BodyEarthing", "");
        PositiveEarthing = pref.getString("IPMS_PositiveEarthing", "");
        RatingofCable = pref.getString("IPMS_RatingofCable", "");
        AlarmConnection = pref.getString("IPMS_AlarmConnection", "");
        NoofRMWorking = pref.getString("IPMS_NoofRMWorking", "");
        NoofRMFaulty = pref.getString("IPMS_NoofRMFaulty", "");
        SpareFuseStatus = pref.getString("IPMS_SpareFuseStatus", "");
        itemStatus = pref.getString("IPMS_itemStatus", "");*/
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
        getUserPref();
        setSpinnerValue();
        arrEquipData = atmDatabase.getEquipmentMakeData("Desc", "SMPS");
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

                        ArrayAdapter<String> adapterModelName = new ArrayAdapter<String>(
                                getContext(), android.R.layout.select_dialog_item, arrModel);
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
        if (!isEmptyStr(strMake) || !isEmptyStr(strModel) || !isEmptyStr(strCapacity)
                || !isEmptyStr(strSerialNum)
                || !isEmptyStr(strYearOfManufacturing) ||
                !isEmptyStr(strDescription)
                || !isEmptyStr(strlcucapacity)
                || !isEmptyStr(Controller)
                || !isEmptyStr(ConditionbackPlane)
                || !isEmptyStr(BodyEarthing)
                || !isEmptyStr(PositiveEarthing)
                || !isEmptyStr(AlarmConnection)
                || !isEmptyStr(NoofRMWorking)
                || !isEmptyStr(NoofRMFaulty)
                || !isEmptyStr(SpareFuseStatus)
                ) {
            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            etLcuCapacity.setText(strlcucapacity);
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
          /*  if (!frontphoto.equals("") || !openPhoto.equals("") || !sNoPlatephoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(frontphoto)).placeholder(R.drawable.noimage).into(frontimg);
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
                String newEquipment = "0";
                if (equipCapacityDataList != null && equipCapacityDataList.size() > 0) {
                    for (int i = 0; i < equipCapacityDataList.size(); i++) {
                        if (capacity.equals(equipCapacityDataList.get(i).getName())) {
                            strDescriptionId = equipCapacityDataList.get(i).getId();
                        }
                    }
                }
                if (isEmptyStr(strDescriptionId)) {
                    strDescriptionId = "0";
                }
                if (arrEquipData != null && arrEquipData.size() > 0) {
                    for (int i = 0; i < arrEquipData.size(); i++) {
                        if (make.equals(arrEquipData.get(i).getName())) {
                            strMakeId = arrEquipData.get(i).getId();
                        }
                    }
                }
                if (isEmptyStr(strMakeId)) {
                    strMakeId = "0";
                }
                if (equipDescriptionDataList != null && equipDescriptionDataList.size() > 0) {
                    for (int i = 0; i < equipDescriptionDataList.size(); i++) {
                        if (make.equals(equipDescriptionDataList.get(i).getName())) {
                            strModelId = equipDescriptionDataList.get(i).getId();
                        }
                    }
                }
                if (isEmptyStr(strModelId)) {
                    strModelId = "0";
                }
               /* SharedPreferences.Editor editor = pref.edit();
                editor.putString("IPMS_UserId", strUserId);
                editor.putString("IPMS_Make", make);
                editor.putString("IPMS_Model", model);
                editor.putString("IPMS_Capacity", capacity);
                editor.putString("IPMS_DescriptionId", strDescriptionId);
                editor.putString("IPMS_MakeId", strMakeId);
                editor.putString("IPMS_ModelId", strModelId);
                editor.putString("IPMS_SerialNum", serialNumber);
                editor.putString("IPMS_YearOfManufacturing", yearOfManufacturing);
                editor.putString("IPMS_Description", description);
                editor.putString("IPMS_ModuleCapacity", ModuleCapacity);
                editor.putString("IPMS_Llcucapacity", lcucapacity);
                editor.putString("IPMS_noofModule", nofModule);
                editor.putString("IPMS_Photo1", frontphoto);
                editor.putString("IPMS_Photo2", openPhoto);
                editor.putString("IPMS_Photo3", sNoPlatephoto);
                editor.putString("IPMS_SavedDateTime", currentDateTime);
                editor.putString("IPMS_Controller", Controller);
                editor.putString("IPMS_ConditionbackPlane", ConditionbackPlane);
                editor.putString("IPMS_BodyEarthing", BodyEarthing);
                editor.putString("IPMS_PositiveEarthing", PositiveEarthing);
                editor.putString("IPMS_RatingofCable", RatingofCable);
                editor.putString("IPMS_AlarmConnection", AlarmConnection);
                editor.putString("IPMS_NoofRMWorking", NoofRMWorking);
                editor.putString("IPMS_NoofRMFaulty", NoofRMFaulty);
                editor.putString("IPMS_SpareFuseStatus", SpareFuseStatus);
                editor.putString("IPMS_itemStatus", itemStatus);
                editor.commit();*/

                saveBasicDataonServer();
            }


        }
    }


    public boolean isValidate() {
        make = etMake.getText().toString();
        model = etCapacity.getText().toString();
        capacity = etCapacity.getText().toString();
        serialNumber = etSerialNum.getText().toString();
        yearOfManufacturing = etYear.getText().toString();
        description = etDescription.getText().toString();
        nofModule = etnoofModule.getText().toString();
        ModuleCapacity = etModuleCapacity.getText().toString();
        lcucapacity = etLcuCapacity.getText().toString();
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
        itemStatus = itemStatusSpineer.getSelectedItem().toString();
        itemCondition = itemConditionSpinner.getSelectedItem().toString();
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
        } else if (isEmptyStr(lcucapacity)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter LCU Capacity");
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
        } else if (isEmptyStr(nofModule)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Nof Of Modules");
            return false;
        } else if (isEmptyStr(ModuleCapacity)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Module Capacity");
            return false;
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
                    String imageName = CurtomerSite_Id + "_IPMS_" + EquipmentSno + "_Front.jpg";
                    frontimgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(frontimgFile).into(frontimg);
                    //overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    String imageName = CurtomerSite_Id + "_IPMS_" + EquipmentSno + "_Open.jpg";
                    openImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(openImgFile).into(openImg);
                } else {
                    String imageName = CurtomerSite_Id + "_IPMS_" + EquipmentSno + "_SerialNoPlate.jpg";
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
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Site_ID", strSiteId);
                jsonObject.put("User_ID", strUserId);
                jsonObject.put("Activity", "Equipment");

                JSONObject EquipmentDataa = new JSONObject();
                EquipmentDataa.put("EquipmentSno", EquipmentSno);
                EquipmentDataa.put("EquipmentID", "0");
                EquipmentDataa.put("Equipment", "IPMS");
                EquipmentDataa.put("MakeID", strMakeId);
                EquipmentDataa.put("Make", make);
                EquipmentDataa.put("Capacity_ID", "0");
                EquipmentDataa.put("Capacity", capacity);
                EquipmentDataa.put("SerialNo", serialNumber);
                EquipmentDataa.put("MfgDate", datemilisec);
                EquipmentDataa.put("ItemCondition", itemCondition);
                EquipmentDataa.put("PP_Controller", Controller);
                EquipmentDataa.put("PP_BackPlaneCondition", ConditionbackPlane);
                EquipmentDataa.put("PP_BodyEarthing", BodyEarthing);
                EquipmentDataa.put("PP_PositiveBusBarEarthing", PositiveEarthing);
                EquipmentDataa.put("PP_CableRating", RatingofCable);
                EquipmentDataa.put("PP_AlarmConnectionStatus", AlarmConnection);
                EquipmentDataa.put("PP_WorkingRM", NoofRMWorking);
                EquipmentDataa.put("PP_FaultyRM", NoofRMFaulty);
                EquipmentDataa.put("PP_SpareFuseStatusHRC", SpareFuseStatus);


                JSONArray EquipmentData = new JSONArray();
                EquipmentData.put(EquipmentDataa);
                jsonObject.put("EquipmentData", EquipmentData);


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
                            ASTUIUtil.showToast("Your Input Alarm Pannel Data save Successfully");
                            showAddMoreItemDialog();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Your Input Alarm Pannel Data has not been updated!");
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

    public void showAddMoreItemDialog() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        android.support.v7.app.AlertDialog dialog = builder.create();
        //dialog.getWindow().getAttributes().windowAnimations = R.style.alertAnimation;
        dialog.setMessage("Do you want do add more Battery Item Details");
        dialog.setTitle("Battery Alert");
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Add More", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearFiledData();
                EquipmentSno = EquipmentSno + 1;
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

    public void clearFiledData() {
        etMake.setText("");
        etModel.setText("");
        etCapacity.setText("");
        etSerialNum.setText("");
        etYear.setText("");
        etDescription.setText("");
        etLcuCapacity.setText("");
        etController.setText("");
        etConditionbackPlane.setText("");
        etBodyEarthing.setText("");
        etPositiveEarthing.setText("");
        etRatingofCable.setText("");
        etAlarmConnection.setText("");
        etNoofRMWorking.setText("");
        etNoofRMFaulty.setText("");
        etSpareFuseStatus.setText("");
        itemStatusSpineer.setSelection(0);
        itemConditionSpinner.setSelection(0);
        etMake.setFocusable(true);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(frontimg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(openImg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(sNoPlateImg);
    }
}
