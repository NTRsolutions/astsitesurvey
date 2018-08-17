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

public class AirConditionerFragment extends MainFragment {
    static ImageView frontimg, openImg, sNoPlateImg;
    static File frontimgFile, openImgFile, sNoPlateImgFile;
    static boolean isImage1, isImage2;
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner;
    AppCompatEditText etDescription, etNumberOfAC,
            etaCType, etacACWorkingCondition, etCompresserStatus, etACAlarms, etSocketandPlug;
    AutoCompleteTextView etCapacity, etMake, etModel, etSerialNum;
    SharedPreferences pref;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription, strType, strNumberOfAC;
    String strSavedDateTime, strUserId, strSiteId, sNoAC, CurtomerSite_Id;
    String strMakeId, strModelId, strDescriptionId;
    String[] arrMake;
    String[] arrModel;
    String[] arrCapacity;
    ArrayList<EquipMakeDataModel> arrEquipData;
    ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    AtmDatabase atmDatabase;
    String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime, numOfACs;
    Spinner itemStatusSpineer;
    String aCType, acACWorkingCondition, CompresserStatus, ACAlarms, SocketandPlug, itemCondition;
    SharedPreferences userPref;
    TextView etYear, dateIcon;
    Typeface materialdesignicons_font;
    LinearLayout dateLayout;
    long datemilisec;
    int EquipmentSno = 1;

    String strEqupId;
    private String capcityId = "0";
    private String itemstatus;
    ArrayList<EquipMakeDataModel> equipMakeList;
    ArrayList<EquipMakeDataModel> equipList;
    ArrayList<EquipCapacityDataModel> equipCapacityList;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_air_conditioner;
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
        etNumberOfAC = findViewById(R.id.etNumOfAC);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        btnSubmit = findViewById(R.id.btnSubmit);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        etaCType = findViewById(R.id.etaCType);
        etacACWorkingCondition = findViewById(R.id.etacACWorkingCondition);
        etCompresserStatus = findViewById(R.id.etCompresserStatus);
        etACAlarms = findViewById(R.id.etACAlarms);
        etSocketandPlug = findViewById(R.id.etSocketandPlug);
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

    @Override
    protected void setAccessibility() {

    }

    /**
     * Shared Prefrences
     */

    public void getSharedPrefData() {
   /*     pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        strMake = pref.getString("AC_Make", "");
        strModel = pref.getString("AC_Model", "");
        strCapacity = pref.getString("AC_Capacity", "");
        strMakeId = pref.getString("AC_MakeId", "");
        strModelId = pref.getString("AC_ModelId", "");
        strDescriptionId = pref.getString("AC_DescriptionId", "");
        strSerialNum = pref.getString("AC_SerialNum", "");
        strYearOfManufacturing = pref.getString("AC_YearOfManufacturing", "");
        strDescription = pref.getString("AC_Description", "");
        frontphoto = pref.getString("AC_Photo1", "");
        openPhoto = pref.getString("AC_Photo2", "");
        sNoPlatephoto = pref.getString("AC_Photo3", "");
        strSavedDateTime = pref.getString("AC_SavedDateTime", "");
        strType = pref.getString("AC_Type", "");
        strNumberOfAC = pref.getString("AC_Number", "");
        strSiteId = pref.getString("SiteId", "");
        aCType = pref.getString("AC_aCType", "");
        acACWorkingCondition = pref.getString("AC_acACWorkingCondition", "");
        CompresserStatus = pref.getString("AC_CompresserStatus", "");
        ACAlarms = pref.getString("AC_ACAlarms", "");
        SocketandPlug = pref.getString("AC_SocketandPlug", "");
        sNoAC = pref.getString("sNoAC", "");*/
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
        getSharedPrefData();
        setSpinnerValue();
        getUserPref();
        atmDatabase = new AtmDatabase(getContext());
        equipList = atmDatabase.getEquipmentData("AC");
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "AC");
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
                || !isEmptyStr(strYearOfManufacturing)
                || !isEmptyStr(strDescription) || !isEmptyStr(sNoAC)
                || !isEmptyStr(sNoAC)
                || !isEmptyStr(acACWorkingCondition)
                || !isEmptyStr(CompresserStatus)
                || !isEmptyStr(ACAlarms)
                || !isEmptyStr(SocketandPlug)
                ) {

            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            etNumberOfAC.setText(strNumberOfAC);
            etaCType.setText(aCType);
            etacACWorkingCondition.setText(acACWorkingCondition);
            etCompresserStatus.setText(CompresserStatus);
            etACAlarms.setText(ACAlarms);
            etSocketandPlug.setText(SocketandPlug);


            arrEquipData = atmDatabase.getEquipmentMakeData("DESC", "DG");
            equipCapacityDataList = atmDatabase.getEquipmentCapacityData("DESC", strMake);
            equipDescriptionDataList = atmDatabase.getEquipmentDescriptionData("DESC", strModel);
           /* if (!frontphoto.equals("") || !openPhoto.equals("") || !sNoPlatephoto.equals("")) {
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

        itemStatusSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Not Available")) {
                    frontimg.setEnabled(false);
                    openImg.setEnabled(false);
                    sNoPlateImg.setEnabled(false);
                    etMake.setEnabled(false);
                    etModel.setEnabled(false);
                    etCapacity.setEnabled(false);
                    etSerialNum.setEnabled(false);
                    etYear.setEnabled(false);
                    etDescription.setEnabled(false);
                    etNumberOfAC.setEnabled(false);
                    itemConditionSpinner.setEnabled(false);
                    descriptionLayout.setEnabled(false);
                    etaCType.setEnabled(false);
                    etacACWorkingCondition.setEnabled(false);
                    etCompresserStatus.setEnabled(false);
                    etACAlarms.setEnabled(false);
                    etSocketandPlug.setEnabled(false);

                } else {
                    frontimg.setEnabled(true);
                    openImg.setEnabled(true);
                    sNoPlateImg.setEnabled(true);
                    etMake.setEnabled(true);
                    etModel.setEnabled(true);
                    etCapacity.setEnabled(true);
                    etSerialNum.setEnabled(true);
                    etYear.setEnabled(true);
                    etDescription.setEnabled(true);
                    etNumberOfAC.setEnabled(true);
                    itemConditionSpinner.setEnabled(true);
                    descriptionLayout.setEnabled(true);
                    etaCType.setEnabled(true);
                    etacACWorkingCondition.setEnabled(true);
                    etCompresserStatus.setEnabled(true);
                    etACAlarms.setEnabled(true);
                    etSocketandPlug.setEnabled(true);
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
        } else if (view.getId() == R.id.image1) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = true;
            isImage2 = false;
        } else if (view.getId() == R.id.image2) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage2 = true;
            isImage1 = false;
        } else if (view.getId() == R.id.image3) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage2 = false;
            isImage1 = false;
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                /*SharedPreferences.Editor editor = pref.edit();
                editor.putString("AC_UserId", strUserId);
                editor.putString("AC_Make", make);
                editor.putString("AC_Model", model);
                editor.putString("AC_Capacity", capacity);
                editor.putString("AC_DescriptionId", strDescriptionId);
                editor.putString("AC_MakeId", strMakeId);
                editor.putString("AC_ModelId", strModelId);
                editor.putString("AC_SerialNum", serialNumber);
                editor.putString("AC_YearOfManufacturing", yearOfManufacturing);
                editor.putString("AC_Description", description);
                editor.putString("AC_Photo1", frontphoto);
                editor.putString("AC_Photo2", openPhoto);
                editor.putString("AC_Photo3", sNoPlatephoto);
                editor.putString("AC_SavedDateTime", currentDateTime);
                editor.putString("AC_Number", numOfACs);
                editor.putString("sNoAC", sNAC);

                editor.putString("AC_aCType", aCType);
                editor.putString("AC_acACWorkingCondition", acACWorkingCondition);
                editor.putString("AC_CompresserStatus", CompresserStatus);
                editor.putString("AC_ACAlarms", ACAlarms);
                editor.putString("AC_SocketandPlug", SocketandPlug);

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
        currentDateTime = String.valueOf(System.currentTimeMillis());
        numOfACs = etNumberOfAC.getText().toString();
        aCType = etaCType.getText().toString();
        acACWorkingCondition = etacACWorkingCondition.getText().toString();
        CompresserStatus = etCompresserStatus.getText().toString();
        ACAlarms = etACAlarms.getText().toString();
        SocketandPlug = etSocketandPlug.getText().toString();
        itemCondition = itemConditionSpinner.getSelectedItem().toString();
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
            } else if (isEmptyStr(numOfACs)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter AC Quantity");
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

    public void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1) {
                    String imageName = CurtomerSite_Id + "_AC_" + EquipmentSno + "_Front.jpg";
                    frontimgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(frontimgFile).into(frontimg);
                    //overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    String imageName = CurtomerSite_Id + "_AC_" + EquipmentSno + "_Open.jpg";
                    openImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(openImgFile).into(openImg);
                } else {
                    String imageName = CurtomerSite_Id + "_AC_" + EquipmentSno + "_SerialNoPlate.jpg";
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
                EquipmentDataa.put("Capacity_ID", capcityId);
                EquipmentDataa.put("EquipmentSno", EquipmentSno);
                EquipmentDataa.put("Equipment", "AC");
                EquipmentDataa.put("MakeID", strMakeId);
                EquipmentDataa.put("Make", make);
                EquipmentDataa.put("Capacity", capacity);
                EquipmentDataa.put("SerialNo", serialNumber);
                EquipmentDataa.put("MfgDate", datemilisec);
                EquipmentDataa.put("ItemCondition", itemCondition);
                EquipmentDataa.put("AC_Type", aCType);
                EquipmentDataa.put("AIR_CON_Number", numOfACs);
                EquipmentDataa.put("AC_WorkingCondition", acACWorkingCondition);
                EquipmentDataa.put("AC_CompresserandBlowerWorking", CompresserStatus);
                EquipmentDataa.put("AC_Alarms", ACAlarms);
                EquipmentDataa.put("AC_SocketandPlug", SocketandPlug);

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
        dialog.setMessage("Do you want do add more AC Item Details");
        dialog.setTitle("Add Ac Item");
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Add More Item", new DialogInterface.OnClickListener() {
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
        etNumberOfAC.setText("");
        etaCType.setText("");
        etacACWorkingCondition.setText("");
        etCompresserStatus.setText("");
        etACAlarms.setText("");
        etSocketandPlug.setText("");
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
