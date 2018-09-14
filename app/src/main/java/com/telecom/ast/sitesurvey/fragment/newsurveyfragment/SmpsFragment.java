package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.telecom.ast.sitesurvey.utils.ASTObjectUtil;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.ASTUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;
import com.telecom.ast.sitesurvey.utils.FilePickerHelper;
import com.telecom.ast.sitesurvey.utils.FontManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    private static ImageView frontimg, openImg, sNoPlateImg;
    private static File frontimgFile, openImgFile, sNoPlateImgFile;
    private static boolean isImage1, isImage2;
    private AppCompatEditText etDescription, etnoofModule, etModuleCapacity, etRatingofCable;
    private AppCompatAutoCompleteTextView etCapacity, etMake, etModel, etSerialNum;
    private SharedPreferences pref, smpsShrepreforrpiu;
    private String strSavedDateTime, strUserId, strSiteId, CurtomerSite_Id;
    private String strMakeId = "0", nofModule = "0", ModuleCapacity = "0";
    private String[] arrMake;
    private String[] arrModel;
    private String[] arrCapacity;
    private ArrayList<EquipMakeDataModel> arrEquipData;
    private ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    private ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    private AtmDatabase atmDatabase;
    private String make = "", model = "", capacity = "", serialNumber = "", yearOfManufacturing = "0", description = "", currentDateTime = "";
    private LinearLayout descriptionLayout;
    private Spinner itemConditionSpinner;

    private AppCompatEditText
            etNoofRMWorking, etNoofRMFaulty;

    private Spinner itemStatusSpineer, etController, etConditionbackPlane, etBodyEarthing, etPositiveEarthing, etAlarmConnection, etSpareFuseStatus;
    private String Controller = "", ConditionbackPlane = "", BodyEarthing = "0", PositiveEarthing = "0", RatingofCable = "0", AlarmConnection = "",
            NoofRMWorking = "0", NoofRMFaulty = "0", SpareFuseStatus = "", itemCondition = "";

    private SharedPreferences userPref;
    private TextView etYear, dateIcon;
    private Typeface materialdesignicons_font;
    private LinearLayout dateLayout;
    private long datemilisec;
    private int EquipmentSno = 1;
    private Button btnSubmit;
    private String strEqupId = "0";
    private String capcityId = "0";
    private String itemstatus;
    private ArrayList<EquipMakeDataModel> equipMakeList;
    private ArrayList<EquipMakeDataModel> equipList;
    private ArrayList<EquipCapacityDataModel> equipCapacityList;
    private boolean isFaulty;
    private CardView image1ImageCardview, image12ImageCardview, image3ImageCardview;
    private TextView frontPhotolabl;

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
        image1ImageCardview = findViewById(R.id.image1ImageCardview);
        image12ImageCardview = findViewById(R.id.image2ImageCardview);
        image3ImageCardview = findViewById(R.id.image3ImageCardview);
        frontPhotolabl = findViewById(R.id.frontPhotolabl);
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
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dateLayout) {
            setDateofSiteonAir();
        } else if (view.getId() == R.id.image1) {
            isImage1 = true;
            isImage2 = false;
            String imageName = CurtomerSite_Id + "_SMPS_" + EquipmentSno + "_Front.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);

        } else if (view.getId() == R.id.image2) {
            isImage1 = false;
            isImage2 = true;
            String imageName = CurtomerSite_Id + "_SMPS_" + EquipmentSno + "_Open.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.image3) {
            isImage1 = false;
            isImage2 = false;
            String imageName = CurtomerSite_Id + "_SMPS_" + EquipmentSno + "_SerialNoPlate.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);

        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();
            }


        }

    }


    public boolean isValidate() {
        itemstatus = itemStatusSpineer.getSelectedItem().toString();
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
            make = etMake.getText().toString();
            model = etModel.getText().toString();
            capacity = etCapacity.getText().toString();
            serialNumber = etSerialNum.getText().toString();
            yearOfManufacturing = etYear.getText().toString();
            description = etDescription.getText().toString();
            nofModule = etnoofModule.getText().toString();
            ModuleCapacity = etModuleCapacity.getText().toString();
            Controller = etController.getSelectedItem().toString();
            ConditionbackPlane = etConditionbackPlane.getSelectedItem().toString();
            AlarmConnection = etAlarmConnection.getSelectedItem().toString();
            NoofRMWorking = etNoofRMWorking.getText().toString();
            NoofRMFaulty = etNoofRMFaulty.getText().toString();
            SpareFuseStatus = etSpareFuseStatus.getSelectedItem().toString();
            itemCondition = itemConditionSpinner.getSelectedItem().toString();
            currentDateTime = String.valueOf(System.currentTimeMillis());

            RatingofCable = etRatingofCable.getText().toString();
            BodyEarthing = etBodyEarthing.getSelectedItem().toString();
            PositiveEarthing = etPositiveEarthing.getSelectedItem().toString();
            NoofRMWorking = etNoofRMWorking.getText().toString();
            NoofRMFaulty = etNoofRMFaulty.getText().toString();

           /* RatingofCable = etRatingofCable.getText().toString();
            if (RatingofCable.equals("")) {
                RatingofCable = "0";
            }

            BodyEarthing = etBodyEarthing.getSelectedItem().toString();
            if (BodyEarthing.equals("")) {
                BodyEarthing = "0";
            }
            PositiveEarthing = etPositiveEarthing.getSelectedItem().toString();
            if (PositiveEarthing.equals("")) {
                PositiveEarthing = "0";
            }
            NoofRMWorking = etNoofRMWorking.getText().toString();
            if (NoofRMWorking.equals("")) {
                NoofRMWorking = "0";
            }
            NoofRMFaulty = etNoofRMFaulty.getText().toString();
            if (NoofRMFaulty.equals("")) {
                NoofRMFaulty = "0";
            }*/
            if (isEmptyStr(make)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Make");
                return false;
            } else if (isEmptyStr(model)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Model");
                return false;
            } else if (isEmptyStr(serialNumber)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Serial Number");
                return false;
            } else if (isEmptyStr(capacity)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity");
                return false;
            } else if (isEmptyStr(yearOfManufacturing)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Manufacturing Date");
                return false;
            } else if (isEmptyStr(description) && itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
                return false;
            } else if (isEmptyStr(nofModule)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of Modules Available at Site");
                return false;
            } else if (isEmptyStr(ModuleCapacity)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Module Capacity");
                return false;
            } else if (isEmptyStr(RatingofCable)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter BB to SMPS Cable Rating");
                return false;
            } else if (isEmptyStr(NoofRMWorking)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of RM Working");
                return false;
            } else if (isEmptyStr(NoofRMFaulty)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Number Of Rm Faulty");
                return false;
            } else if (frontimgFile == null || !frontimgFile.exists()) {
                if (isFaulty) {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select  Faulty Photo");
                } else {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Photo With Equipment Specification");
                }
                return false;
            } else if (!isFaulty) {
                if (openImgFile == null || !openImgFile.exists()) {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select System Open Photo");
                    return false;
                }

            } else if (!isFaulty) {
                if (sNoPlateImgFile == null || !sNoPlateImgFile.exists()) {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Sr Number Plate Photo");
                    return false;
                }
            }
        } else {
            ASTUIUtil.showToast("Item Not Available");
        }
        return true;
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
                EquipmentDataa.put("PP_BattFuseStatus", SpareFuseStatus);
                EquipmentDataa.put("SMPSModulesQt", nofModule);
                EquipmentDataa.put("SMPSModuleCapacity", ModuleCapacity);
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
                            if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
                                showAddMoreItemDialog();
                            } else {
                                reloadBackScreen();
                            }
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
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
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
        etController.setSelection(0);
        etConditionbackPlane.setSelection(0);
        etBodyEarthing.setSelection(0);
        etPositiveEarthing.setSelection(0);
        etRatingofCable.setSelection(0);
        etAlarmConnection.setSelection(0);
        etNoofRMWorking.setText("");
        etNoofRMFaulty.setText("");
        etSpareFuseStatus.setSelection(0);
        itemStatusSpineer.setSelection(0);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(frontimg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(openImg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(sNoPlateImg);
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

    /**
     * THIS USE an ActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            onCaptureImageResult();
        }
    }


    //capture image compress
    private void onCaptureImageResult() {
        if (isImage1) {
            String imageName = CurtomerSite_Id + "_SMPS_" + EquipmentSno + "_Front.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, frontimg,imageName);
            }
        } else if (isImage2) {
            String imageName = CurtomerSite_Id + "_SMPS_" + EquipmentSno + "_Open.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, openImg,imageName);
            }
        } else {
            String imageName = CurtomerSite_Id + "_SMPS_" + EquipmentSno + "_SerialNoPlate.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, sNoPlateImg,imageName);
            }
        }
    }


    //compres image
    private void compresImage(final File file, final String fileName, final ImageView imageView, final String imageName) {
        new AsyncTask<Void, Void, Boolean>() {
            File imgFile;
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
                Bitmap bitmap = FilePickerHelper.compressImage(file.getAbsolutePath(), ot, 800.0f, 800.0f,imageName);
                if (bitmap != null) {
                    uri = FilePickerHelper.getImageUri(getContext(), bitmap);
//save compresed file into location
                    imgFile = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator, fileName);

                    try {
                        if (imgFile.exists()) {
                            imgFile.delete();
                        }
                        InputStream iStream = getContext().getContentResolver().openInputStream(uri);
                        byte[] inputData = FilePickerHelper.getBytes(iStream);

                        FileOutputStream fOut = new FileOutputStream(imgFile);
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
                    frontimgFile = imgFile;
                    // Picasso.with(ApplicationHelper.application().getContext()).load(frontimgFile).into(imageView);
                } else if (isImage2) {
                    openImgFile = imgFile;
                    // Picasso.with(ApplicationHelper.application().getContext()).load(openImgFile).into(imageView);
                } else {
                    sNoPlateImgFile = imgFile;
                    //Picasso.with(ApplicationHelper.application().getContext()).load(sNoPlateImgFile).into(imageView);
                }
                imageView.setImageURI(uri);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        }.execute();

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
}
