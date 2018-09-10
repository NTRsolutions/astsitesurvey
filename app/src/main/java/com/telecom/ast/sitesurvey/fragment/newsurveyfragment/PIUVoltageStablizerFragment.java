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
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
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
import com.telecom.ast.sitesurvey.utils.ASTObjectUtil;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.ASTUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
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

public class PIUVoltageStablizerFragment extends MainFragment {
    private static ImageView frontimg, openImg, sNoPlateImg;
    private static File frontimgFile, openImgFile, sNoPlateImgFile;
    private static boolean isImage1, isImage2;
    private AppCompatEditText etDescription, etetNofLcu;
    private AppCompatAutoCompleteTextView etCapacity, etMake, etModel, etSerialNum;
    private SharedPreferences pref;
    private String strUserId, strSiteId, CurtomerSite_Id;
    private String strMakeId = "0", NofLcu = "0";
    private String[] arrMake;
    private String[] arrModel;
    private String[] arrCapacity;
    private AtmDatabase atmDatabase;
    private String make = "", model = "", capacity = "", serialNumber = "", yearOfManufacturing = "", description = "", currentDateTime = "";
    private LinearLayout descriptionLayout;
    private Spinner itemConditionSpinner;


    private Spinner itemStatusSpineer;
    private String itemCondition = "";

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
    private SharedPreferences smpsShrepreforrpiu;
    private Spinner nameofNameofEquipment, workingStatus, SiteAutomationStatus;
    private String strnameofNameofEquipment, strworkingStatus, stretnotworkingText, strSiteAutomationStatus;
    private LinearLayout noofLcuLauout, notworkinsItemLayout;
    private AutoCompleteTextView etnotworkingText;
    private TextInputLayout etnotworlyTextInput;
    private boolean isFaulty;
    private CardView image1ImageCardview, image12ImageCardview, image3ImageCardview;
    private TextView frontPhotolabl;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_piuvoltage_stablizer;
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
        etetNofLcu = findViewById(R.id.etNofLcu);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
        btnSubmit = findViewById(R.id.btnSubmit);
        nameofNameofEquipment = findViewById(R.id.nameofNameofEquipment);
        noofLcuLauout = findViewById(R.id.noofLcuLauout);
        workingStatus = findViewById(R.id.workingStatus);
        notworkinsItemLayout = findViewById(R.id.notworkinsItemLayout);
        etnotworkingText = findViewById(R.id.etnotworlyText);
        SiteAutomationStatus = findViewById(R.id.SiteAutomationStatus);
        etnotworlyTextInput = findViewById(R.id.etnotworlyTextInput);
        image1ImageCardview = findViewById(R.id.image1ImageCardview);
        image12ImageCardview = findViewById(R.id.image2ImageCardview);
        image3ImageCardview = findViewById(R.id.image3ImageCardview);
        frontPhotolabl = findViewById(R.id.frontPhotolabl);
    }

    @Override
    protected void setClickListeners() {
        frontimg.setOnClickListener(this);
        openImg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        dateLayout.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        notworkinsItemLayout.setOnClickListener(this);
        etnotworlyTextInput.setOnClickListener(this);
        etnotworkingText.setOnClickListener(this);
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

        final String nameofNameofEquipmentSpineer_array[] = {"IPMS", "PIU", "AMF"};
        ArrayAdapter<String> nameofNameofEquipmentdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, nameofNameofEquipmentSpineer_array);
        nameofNameofEquipment.setAdapter(nameofNameofEquipmentdapter);


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
        atmDatabase = new AtmDatabase(getContext());
        setSpinnerValue();
        getUserPref();
        atmDatabase = new AtmDatabase(getContext());
        equipList = atmDatabase.getEquipmentData(strnameofNameofEquipment);
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", strnameofNameofEquipment);
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
        itemConditionSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                descriptionLayout.setVisibility(selectedItem.equalsIgnoreCase("Fully Fault") ? View.VISIBLE : View.GONE);

                isFaulty = ASTObjectUtil.isEmptyStr(description) &&
                        itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")
                        || itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Not Ok");
                image12ImageCardview.setVisibility(isFaulty ? View.INVISIBLE : View.VISIBLE);
                image3ImageCardview.setVisibility(isFaulty ? View.GONE : View.VISIBLE);
                frontPhotolabl.setText(isFaulty ? "Faulty Photo" : "Front Photo");
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        workingStatus.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Not Working")) {
                    notworkinsItemLayout.setVisibility(View.VISIBLE);

                } else {
                    notworkinsItemLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.notworkinsItemLayout || view.getId() == R.id.etnotworlyTextInput || view.getId() == R.id.etnotworlyText) {
            showSelectFaultyItemDialog();
        } else if (view.getId() == R.id.dateLayout) {
            setDateofSiteonAir();
        } else if (view.getId() == R.id.image1) {
            isImage1 = true;
            isImage2 = false;
            String imageName = CurtomerSite_Id + "_" + strnameofNameofEquipment + "_" + EquipmentSno + "_Front.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.image2) {
            isImage1 = false;
            isImage2 = true;
            String imageName = CurtomerSite_Id + "_" + strnameofNameofEquipment + "_" + EquipmentSno + "_Open.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.image3) {
            isImage1 = false;
            isImage2 = false;
            String imageName = CurtomerSite_Id + "_" + strnameofNameofEquipment + "_" + EquipmentSno + "_SerialNoPlate.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValiDate()) {
                saveBasicDataonServer();
            }
        }
    }


    public boolean isValiDate() {
        itemstatus = itemStatusSpineer.getSelectedItem().toString();
        strnameofNameofEquipment = nameofNameofEquipment.getSelectedItem().toString();
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
            make = etMake.getText().toString();
            model = etModel.getText().toString();
            capacity = etCapacity.getText().toString();
            serialNumber = etSerialNum.getText().toString();
            yearOfManufacturing = etYear.getText().toString();
            description = etDescription.getText().toString();
            currentDateTime = String.valueOf(System.currentTimeMillis());
            itemCondition = itemConditionSpinner.getSelectedItem().toString();
            NofLcu = etetNofLcu.getText().toString();
            strworkingStatus = workingStatus.getSelectedItem().toString();
            stretnotworkingText = etnotworkingText.getText().toString();
            strSiteAutomationStatus = SiteAutomationStatus.getSelectedItem().toString();
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
            } else if (isEmptyStr(NofLcu)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Number Of Lcu");
                return false;
            } else if (frontimgFile == null || !frontimgFile.exists()) {
                if (isFaulty) {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select  Faulty Photo");
                } else {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Front Photo");
                }
                return false;
            } else if (!isFaulty) {
                if (openImgFile == null || !openImgFile.exists()) {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Open Photo");
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
                EquipmentDataa.put("Equipment", strnameofNameofEquipment);
                EquipmentDataa.put("MakeID", strMakeId);
                EquipmentDataa.put("Make", make);
                EquipmentDataa.put("Capacity", capacity);
                EquipmentDataa.put("SerialNo", serialNumber);
                EquipmentDataa.put("MfgDate", datemilisec);
                EquipmentDataa.put("ItemCondition", itemCondition);
                EquipmentDataa.put("PIULCUQt", NofLcu);
                EquipmentDataa.put("PIULCUCapacity", "0");
                EquipmentDataa.put("PP_WorkingStatus", strworkingStatus);
                EquipmentDataa.put("PP_WorkingStatus_Detail", stretnotworkingText);
                EquipmentDataa.put("PP_AutomationStatus", strSiteAutomationStatus);
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
                            ASTUIUtil.showToast("Your" + strnameofNameofEquipment + "Data save Successfully");
                            if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
                                showAddMoreItemDialog();
                            } else {
                                reloadBackScreen();
                            }
                            smpsShrepreforrpiu = getContext().getSharedPreferences("PiuenablePref", MODE_PRIVATE);
                            SharedPreferences.Editor editor = smpsShrepreforrpiu.edit();
                            editor.clear().commit();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Your" + strnameofNameofEquipment + " Data has not been updated!");
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
        dialog.setMessage("Do you want do add more PIU Item Details");
        dialog.setTitle("PIU Alert");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
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
        etetNofLcu.setText("");
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
            String imageName = CurtomerSite_Id + "_" + strnameofNameofEquipment + "_" + EquipmentSno + "_Front.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, frontimg);
            }
        } else if (isImage2) {
            String imageName = CurtomerSite_Id + "_" + strnameofNameofEquipment + "_" + EquipmentSno + "_Open.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, openImg);
            }
        } else {
            String imageName = CurtomerSite_Id + "_" + strnameofNameofEquipment + "_" + EquipmentSno + "_SerialNoPlate.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, sNoPlateImg);
            }
        }
    }


    //compres image
    private void compresImage(final File file, final String fileName, final ImageView imageView) {
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
                Bitmap bitmap = FilePickerHelper.compressImage(file.getAbsolutePath(), ot, 800.0f, 800.0f);
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
                } else if (isImage2) {
                    openImgFile = imgFile;
                } else {
                    sNoPlateImgFile = imgFile;
                }
                imageView.setImageURI(uri);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        }.execute();

    }

    protected CharSequence[] faulityItem = {"Contractar", "LCU 1", "LCU 2", "MCB", "Controler Card", "Cantroler Display", "Intrernal Wiring"};
    protected ArrayList<CharSequence> selectedfaulityItem = new ArrayList<CharSequence>();

    protected void showSelectFaultyItemDialog() {
        boolean[] checkedItems = new boolean[faulityItem.length];
        int count = faulityItem.length;
        for (int i = 0; i < count; i++)
            checkedItems[i] = selectedfaulityItem.contains(faulityItem[i]);
        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked)
                    selectedfaulityItem.add(faulityItem[which]);
                else
                    selectedfaulityItem.remove(faulityItem[which]);
                onChangeSelectedItem();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Faulty Items");
        builder.setMultiChoiceItems(faulityItem, checkedItems, coloursDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    protected void onChangeSelectedItem() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence colour : selectedfaulityItem)
            stringBuilder.append(colour + ",");
        etnotworkingText.setText(stringBuilder.toString());

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
