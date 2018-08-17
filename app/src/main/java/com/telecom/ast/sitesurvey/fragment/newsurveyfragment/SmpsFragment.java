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

public class SmpsFragment extends MainFragment {
    static ImageView frontimg, openImg, sNoPlateImg;
    static File frontimgFile, openImgFile, sNoPlateImgFile;
    static boolean isImage1, isImage2;
    AppCompatEditText etDescription, etnoofModule, etModuleCapacity;
    AppCompatAutoCompleteTextView etCapacity, etMake, etModel, etSerialNum;
    SharedPreferences pref, smpsShrepreforrpiu;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription, strnoofModule, strModuleCapacity;
    String strSavedDateTime, strUserId, strSiteId, CurtomerSite_Id;
    String strMakeId, strModelId, strDescriptionId, nofModule, ModuleCapacity;
    String[] arrMake;
    String[] arrModel;
    String[] arrCapacity;
    ArrayList<EquipMakeDataModel> arrEquipData;
    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    AtmDatabase atmDatabase;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner;

    AppCompatEditText etController, etConditionbackPlane, etBodyEarthing, etPositiveEarthing, etRatingofCable, etAlarmConnection,
            etNoofRMWorking, etNoofRMFaulty, etSpareFuseStatus;

    Spinner itemStatusSpineer;
    String Controller, ConditionbackPlane, BodyEarthing, PositiveEarthing, RatingofCable, AlarmConnection,
            NoofRMWorking, NoofRMFaulty, SpareFuseStatus, itemStatus, itemCondition;

    SharedPreferences userPref;
    TextView etYear, dateIcon;
    Typeface materialdesignicons_font;
    LinearLayout dateLayout;
    long datemilisec;
    int EquipmentSno = 1;
    Button btnSubmit;
    String strEqupId;
    private String capcityId = "0";
    private String itemstatus;
    ArrayList<EquipMakeDataModel> equipMakeList;
    ArrayList<EquipMakeDataModel> equipList;
    ArrayList<EquipCapacityDataModel> equipCapacityList;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_smps;
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
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        openImg.setOnClickListener(this);
        frontimg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        dateLayout.setOnClickListener(this);
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
        /*pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("SMPS_Make", "");
        strModel = pref.getString("SMPS_Model", "");
        strCapacity = pref.getString("SMPS_Capacity", "");
        strMakeId = pref.getString("SMPS_MakeId", "");
        strModelId = pref.getString("SMPS_ModelId", "");
        strDescriptionId = pref.getString("SMPS_DescriptionId", "");
        strSerialNum = pref.getString("SMPS_SerialNum", "");
        strYearOfManufacturing = pref.getString("SMPS_YearOfManufacturing", "");
        strDescription = pref.getString("SMPS_Description", "");
        strModuleCapacity = pref.getString("ModuleCapacity", "");
        strnoofModule = pref.getString("noofModule", "");
        frontphoto = pref.getString("SMPSPhoto1", "");
        openPhoto = pref.getString("SMPSPhoto2", "");
        sNoPlatephoto = pref.getString("SMPSPhoto3", "");
        strSavedDateTime = pref.getString("SMPS_SavedDateTime", "");
        strSiteId = pref.getString("SiteId", "");
        Controller = pref.getString("SMPS_Controller", "");
        ConditionbackPlane = pref.getString("SMPS_ConditionbackPlane", "");
        BodyEarthing = pref.getString("SMPS_BodyEarthing", "");
        PositiveEarthing = pref.getString("SMPS_PositiveEarthing", "");
        RatingofCable = pref.getString("SMPS_RatingofCable", "");
        AlarmConnection = pref.getString("SMPS_AlarmConnection", "");
        NoofRMWorking = pref.getString("SMPS_NoofRMWorking", "");
        NoofRMFaulty = pref.getString("SMPS_NoofRMFaulty", "");
        SpareFuseStatus = pref.getString("SMPS_SpareFuseStatus", "");
        itemStatus = pref.getString("SMPS_itemStatus", "");*/
    }

    public void setSpinnerValue() {
        final String itemCondition_array[] = {"Ok", "Not Ok", "Fully Fault"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(homeadapter);

        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatusSpineeradapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatusSpineeradapter);

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

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    @Override
    protected void dataToView() {
        smpsShrepreforrpiu = getContext().getSharedPreferences("PiuenablePref", MODE_PRIVATE);
        atmDatabase = new AtmDatabase(getContext());
        getSharedPrefData();
        getUserPref();
        setSpinnerValue();
        atmDatabase = new AtmDatabase(getContext());
        equipList = atmDatabase.getEquipmentData("SMPS");
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "SMPS");
        arrMake = new String[equipMakeList.size()];
        for (int i = 0; i < equipMakeList.size(); i++) {
            arrMake[i] = equipMakeList.get(i).getName();
        }
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
                || !isEmptyStr(strYearOfManufacturing) || !isEmptyStr(strDescription)
                || !isEmptyStr(Controller)
                || !isEmptyStr(ConditionbackPlane)
                || !isEmptyStr(BodyEarthing)
                || !isEmptyStr(PositiveEarthing)
                || !isEmptyStr(AlarmConnection)
                || !isEmptyStr(NoofRMWorking)
                || !isEmptyStr(NoofRMFaulty)
                || !isEmptyStr(SpareFuseStatus)) {
            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
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
            isImage1 = true;
            isImage2 = false;
            ASTUIUtil.startImagePicker(getHostActivity());

        } else if (view.getId() == R.id.image2) {
            isImage1 = false;
            isImage2 = true;
            ASTUIUtil.startImagePicker(getHostActivity());
        } else if (view.getId() == R.id.image3) {
            isImage1 = false;
            isImage2 = false;
            ASTUIUtil.startImagePicker(getHostActivity());

        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {

               /* SharedPreferences.Editor editor = pref.edit();
                editor.putString("SMPS_UserId", strUserId);
                editor.putString("SMPS_Make", make);
                editor.putString("SMPS_Model", model);
                editor.putString("SMPS_Capacity", capacity);
                editor.putString("SMPS_DescriptionId", strDescriptionId);
                editor.putString("SMPS_MakeId", strMakeId);
                editor.putString("SMPS_ModelId", strModelId);
                editor.putString("SMPS_SerialNum", serialNumber);
                editor.putString("SMPS_YearOfManufacturing", yearOfManufacturing);
                editor.putString("SMPS_Description", description);
                editor.putString("ModuleCapacity", ModuleCapacity);
                editor.putString("noofModule", nofModule);
                editor.putString("SMPSPhoto1", frontphoto);
                editor.putString("SMPSPhoto2", openPhoto);
                editor.putString("SMPSPhoto3", sNoPlatephoto);
                editor.putString("SMPS_SavedDateTime", currentDateTime);

                editor.putString("SMPS_Controller", Controller);
                editor.putString("SMPS_ConditionbackPlane", ConditionbackPlane);
                editor.putString("SMPS_BodyEarthing", BodyEarthing);
                editor.putString("SMPS_PositiveEarthing", PositiveEarthing);
                editor.putString("SMPS_RatingofCable", RatingofCable);
                editor.putString("SMPS_AlarmConnection", AlarmConnection);
                editor.putString("SMPS_NoofRMWorking", NoofRMWorking);
                editor.putString("SMPS_NoofRMFaulty", NoofRMFaulty);
                editor.putString("SMPS_SpareFuseStatus", SpareFuseStatus);
                editor.putString("SMPS_itemStatus", itemStatus);

                editor.commit();
                saveScreenData(true, false);*/
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
            } else if (isEmptyStr(nofModule)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Nof Of Modules");
                return false;
            } else if (isEmptyStr(ModuleCapacity)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Module Capacity");
                return false;
            }
        }else{
            ASTUIUtil.showToast("Item Not Available");
        }
        return true;
    }


    public void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1) {
                    String imageName = CurtomerSite_Id + "_SMPS_" + EquipmentSno + "_Front.jpg";
                    frontimgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(frontimgFile).into(frontimg);
                    //overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    String imageName = CurtomerSite_Id + "_SMPS_" + EquipmentSno + "_Open.jpg";
                    openImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(openImgFile).into(openImg);
                } else {
                    String imageName = CurtomerSite_Id + "_SMPS_" + EquipmentSno + "_SerialNoPlate.jpg";
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
            getMakeAndEqupmentId();
            try {
                jsonObject.put("Site_ID", strSiteId);
                jsonObject.put("User_ID", strUserId);
                jsonObject.put("Activity", "Equipment");
                JSONObject EquipmentDataa = new JSONObject();
                EquipmentDataa.put("EquipmentStatus", itemstatus);
                EquipmentDataa.put("EquipmentID", strEqupId);
                EquipmentDataa.put("CapacityID", capcityId);
                EquipmentDataa.put("EquipmentSno", EquipmentSno);
                EquipmentDataa.put("Equipment", "SMPS");
                EquipmentDataa.put("MakeID", strMakeId);
                EquipmentDataa.put("Make", make);
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
                EquipmentDataa.put("SMPS_Modules_Qt", nofModule);
                EquipmentDataa.put("SMPS_Module_Capacity", ModuleCapacity);
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
                            ASTUIUtil.showToast("Your SMPS Data save Successfully");
                            showAddMoreItemDialog();
                            SharedPreferences.Editor editor = smpsShrepreforrpiu.edit();
                            editor.putBoolean("PIU_ENABLE", true);
                            editor.commit();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Your SMPS Data has not been updated!");
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
        if (frontimgFile != null && frontimgFile.exists()) {
            multipartBody.addFormDataPart(frontimgFile.getName(), frontimgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, frontimgFile));
        }
        if (openImgFile != null && openImgFile.exists()) {
            multipartBody.addFormDataPart(openImgFile.getName(), openImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, openImgFile));
        }
        if (sNoPlateImgFile != null && sNoPlateImgFile.exists()) {
            multipartBody.addFormDataPart(sNoPlateImgFile.getName(), sNoPlateImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sNoPlateImgFile));
        }

        return multipartBody;
    }

    public void showAddMoreItemDialog() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        android.support.v7.app.AlertDialog dialog = builder.create();
        //dialog.getWindow().getAttributes().windowAnimations = R.style.alertAnimation;
        dialog.setMessage("Do you want do add more SMPS Item Details");
        dialog.setTitle("SMPS Alert");
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Add More SMPS", new DialogInterface.OnClickListener() {
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
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(frontimg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(openImg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(sNoPlateImg);
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
}
