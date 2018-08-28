package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
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
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
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
import java.io.IOException;
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
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class EBMeterFragment extends MainFragment {
    private static ImageView frontImg, openImg, sNoPlateImg;
    private static boolean isImage1, isImage2;
    private Button btnSubmit;
    private LinearLayout descriptionLayout;
    private Spinner itemConditionSpinner, meeterTypeSpinner, powerTypeSpinner, transformerTypeSpinner, waterseedpinner;
    private String strUserId = "0", strSavedDateTime, meterreading, strSiteId, CurtomerSite_Id;
    private String make="", model="", capacity="", serialNumber="", yearOfManufacturing="0", description="", type, currentDateTime;
    private SharedPreferences pref;
    private AppCompatAutoCompleteTextView etCapacity, etMake, etModel, etSerialNum;
    private AppCompatEditText etConnectionNo, etCableRating, etALTHTConnection, etTransformerEarthing, etmccbStatus, etTheftfromSite;
    private AppCompatEditText etDescription, ebMeterreading;
    private String strMakeId = "0", strModelId, strDescriptionId;
    private Spinner itemStatusSpineer;
    private String ConnectionNo, CableRating, ALTHTConnection, TransformerEarthing, mccbStatus, kitkatChangeover, TheftfromSite,
            strmeeterTypeSpinner, strpowerTypeSpinner, strtransformerTypeSpinner, strwaterseedpinner, itemCondition, streBbillSpinner;
    private TextView etYear, dateIcon;
    private LinearLayout dateLayout;
    private long datemilisec;
    private static File frontimgFile, openImgFile, sNoPlateImgFile;
    private Typeface materialdesignicons_font;
    private SharedPreferences userPref;

    private String strEqupId;
    private String capcityId = "0";
    private String itemstatus;
    private ArrayList<EquipMakeDataModel> equipMakeList;
    private ArrayList<EquipMakeDataModel> equipList;
    private ArrayList<EquipCapacityDataModel> equipCapacityList;
    private AtmDatabase atmDatabase;
    private String[] arrMake;
    private String[] arrCapacity;
    private Spinner circuitBreakersSpinner, eBbillSpinner;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_eb_meter;
    }

    @Override
    protected void loadView() {
        frontImg = findViewById(R.id.image1);
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
        btnSubmit = findViewById(R.id.btnSubmit);
        ebMeterreading = findViewById(R.id.ebMeterreading);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);

        etConnectionNo = findViewById(R.id.etConnectionNo);
        etCableRating = findViewById(R.id.etCableRating);
        etALTHTConnection = findViewById(R.id.etALTHTConnection);
        etTransformerEarthing = findViewById(R.id.etTransformerEarthing);
        etmccbStatus = findViewById(R.id.etmccbStatus);
        etTheftfromSite = findViewById(R.id.etTheftfromSite);
        meeterTypeSpinner = findViewById(R.id.meeterTypeSpinner);
        powerTypeSpinner = findViewById(R.id.powerTypeSpinner);
        transformerTypeSpinner = findViewById(R.id.transformerTypeSpinner);
        waterseedpinner = findViewById(R.id.waterseedpinner);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
        circuitBreakersSpinner = findViewById(R.id.circuitBreakersSpinner);
        eBbillSpinner = findViewById(R.id.eBbillSpinner);
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

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    public void getSharedPrefData() {

    }

    public void setSpinnerValue() {
        final String itemCondition_array[] = {"Ok", "Not Ok", "Fully Fault"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(homeadapter);

        final String itemStatusSpineer_array[] = {"EB", "Non EB"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);


        final String meeterTypeSpinnerArray[] = {"Single Phase", "3 Phase"};
        ArrayAdapter<String> meeterTypeSpinnerArrayad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, meeterTypeSpinnerArray);
        meeterTypeSpinner.setAdapter(meeterTypeSpinnerArrayad);


        final String powerTypeSpinnerarray[] = {"Temporary", "3 Phase"};
        ArrayAdapter<String> powerTypeSpinnerarrayad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, powerTypeSpinnerarray);
        powerTypeSpinner.setAdapter(powerTypeSpinnerarrayad);


        final String transformerTypeSpinnerArray[] = {"Public", "Site"};
        ArrayAdapter<String> transformerTypeSpinnerArrayad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, transformerTypeSpinnerArray);
        transformerTypeSpinner.setAdapter(transformerTypeSpinnerArrayad);


        final String MeterstatusSpinnerArraay[] = {"Covereds", "Open"};
        ArrayAdapter<String> MeterstatusSpinnerAd = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, MeterstatusSpinnerArraay);
        waterseedpinner.setAdapter(MeterstatusSpinnerAd);


        final String circuitBreakersSpinnerArraay[] = {"Kit Kat fuse", "MCCB Status", "MCB"};
        ArrayAdapter<String> circuitBreakersSpinnerAd = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, circuitBreakersSpinnerArraay);
        circuitBreakersSpinner.setAdapter(circuitBreakersSpinnerAd);


        final String eBbillSpinnerSpinnerArraay[] = {"Monthly", "2 months", "Current"};
        ArrayAdapter<String> eBbillSpinnerSpinnerAd = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, eBbillSpinnerSpinnerArraay);
        eBbillSpinner.setAdapter(eBbillSpinnerSpinnerAd);


    }

    @Override
    protected void dataToView() {
        getSharedPrefData();
        getUserPref();
        setSpinnerValue();
        atmDatabase = new AtmDatabase(getContext());
        equipList = atmDatabase.getEquipmentData("EB");
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "EB");
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
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        itemStatusSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Non EB")) {
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
                    ebMeterreading.setEnabled(false);
                    etConnectionNo.setEnabled(false);
                    etCableRating.setEnabled(false);
                    etALTHTConnection.setEnabled(false);
                    etTransformerEarthing.setEnabled(false);
                    etmccbStatus.setEnabled(false);
                    circuitBreakersSpinner.setEnabled(false);
                    etTheftfromSite.setEnabled(false);
                    meeterTypeSpinner.setEnabled(false);
                    powerTypeSpinner.setEnabled(false);
                    transformerTypeSpinner.setEnabled(false);
                    waterseedpinner.setEnabled(false);
                    eBbillSpinner.setEnabled(false);

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
                    ebMeterreading.setEnabled(true);
                    etConnectionNo.setEnabled(true);
                    etCableRating.setEnabled(true);
                    etALTHTConnection.setEnabled(true);
                    etTransformerEarthing.setEnabled(true);
                    etmccbStatus.setEnabled(true);
                    circuitBreakersSpinner.setEnabled(true);
                    etTheftfromSite.setEnabled(true);
                    meeterTypeSpinner.setEnabled(true);
                    powerTypeSpinner.setEnabled(true);
                    transformerTypeSpinner.setEnabled(true);
                    waterseedpinner.setEnabled(true);
                    eBbillSpinner.setEnabled(true);
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
            isImage1 = true;
            isImage2 = false;
            String imageName = CurtomerSite_Id + "_EB_1_Front.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.image2) {
            isImage1 = false;
            isImage2 = true;
            String imageName = CurtomerSite_Id + "_EB_1_Open.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.image3) {
            isImage1 = false;
            isImage2 = false;
            String imageName = CurtomerSite_Id + "_EB_1_SerialNoPlate.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();
            }
        }


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

    public boolean isValidate() {
        itemstatus = itemStatusSpineer.getSelectedItem().toString();
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("EB")) {
            make = etMake.getText().toString();
            model = etCapacity.getText().toString();
            capacity = etCapacity.getText().toString();
            serialNumber = etSerialNum.getText().toString();
            yearOfManufacturing = etYear.getText().toString();
            description = etDescription.getText().toString();
            currentDateTime = String.valueOf(System.currentTimeMillis());
            meterreading = ebMeterreading.getText().toString();
            ConnectionNo = etConnectionNo.getText().toString();
            CableRating = etCableRating.getText().toString();
            ALTHTConnection = etALTHTConnection.getText().toString();
            TransformerEarthing = etTransformerEarthing.getText().toString();
            mccbStatus = etmccbStatus.getText().toString();
            kitkatChangeover = circuitBreakersSpinner.getSelectedItem().toString();
            TheftfromSite = etTheftfromSite.getText().toString();
            strmeeterTypeSpinner = meeterTypeSpinner.getSelectedItem().toString();
            strpowerTypeSpinner = powerTypeSpinner.getSelectedItem().toString();
            strtransformerTypeSpinner = transformerTypeSpinner.getSelectedItem().toString();
            strwaterseedpinner = waterseedpinner.getSelectedItem().toString();
            itemCondition = itemConditionSpinner.getSelectedItem().toString();
            currentDateTime = String.valueOf(System.currentTimeMillis());
            streBbillSpinner = eBbillSpinner.getSelectedItem().toString();
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
            } else if (isEmptyStr(meterreading)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No Ac");
                return false;
            }
        } else {
            ASTUIUtil.showToast("Item Not Available");
            itemCondition = "";
            strmeeterTypeSpinner = "";
            strpowerTypeSpinner = "";
            strtransformerTypeSpinner = "";
            kitkatChangeover = "";
            streBbillSpinner = "";
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
                JSONObject EquipmentData = new JSONObject();
                EquipmentData.put("EquipmentStatus", itemstatus);
                EquipmentData.put("EquipmentID", strEqupId);
                EquipmentData.put("CapacityID", capcityId);
                EquipmentData.put("EquipmentSno", "1");
                EquipmentData.put("Equipment", "EB");
                EquipmentData.put("MakeID", strMakeId);
                EquipmentData.put("Make", make);
                EquipmentData.put("Capacity", capacity);
                EquipmentData.put("SerialNo", serialNumber);
                EquipmentData.put("MfgDate", datemilisec);
                EquipmentData.put("ItemCondition", itemCondition);
                EquipmentData.put("EB_Type", strmeeterTypeSpinner);
                EquipmentData.put("EB_ConnectionNo", ConnectionNo);
                EquipmentData.put("EB_PowerType", strpowerTypeSpinner);
                EquipmentData.put("EB_CableRating", CableRating);
                EquipmentData.put("EB_ConnectionType", "");
                EquipmentData.put("EB_TransformerType", strtransformerTypeSpinner);
                EquipmentData.put("EB_TransformerNeutralEarthing", TransformerEarthing);
                EquipmentData.put("EB_KitKatChangeOver", kitkatChangeover);
                EquipmentData.put("EB_MCCBStatus", mccbStatus);
                EquipmentData.put("EB_WaterShedMeterstatus", strwaterseedpinner);
                EquipmentData.put("EB_TheftfromSite", TheftfromSite);
                EquipmentData.put("EB_Bill", streBbillSpinner);

                JSONArray EquipmentDataa = new JSONArray();
                EquipmentDataa.put(EquipmentData);
                jsonObject.put("EquipmentData", EquipmentDataa);
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
                            ASTUIUtil.showToast("Your EB Meter Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Your EB Meter  Data has not been updated!");
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
            String imageName = CurtomerSite_Id + "_EB_1_Front.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, frontImg);
            }
        } else if (isImage2) {
            String imageName = CurtomerSite_Id + "_EB_1_Open.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, openImg);
            }
        } else {
            String imageName = CurtomerSite_Id + "_EB_1_SerialNoPlate.jpg";
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
                    Uri uri = FilePickerHelper.getImageUri(getContext(), bitmap);
//save compresed file into location
                    imgFile = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator, fileName);
                    try {
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
                    imageView.setImageURI(FilePickerHelper.isFIleConvert(frontimgFile));
                    //  Picasso.with(ApplicationHelper.application().getContext()).load(frontimgFile).into(imageView);
                } else if (isImage2) {
                    openImgFile = imgFile;
                    imageView.setImageURI(FilePickerHelper.isFIleConvert(openImgFile));
                    // Picasso.with(ApplicationHelper.application().getContext()).load(openImgFile).into(imageView);
                } else {
                    sNoPlateImgFile = imgFile;
                    imageView.setImageURI(FilePickerHelper.isFIleConvert(sNoPlateImgFile));
                    //  Picasso.with(ApplicationHelper.application().getContext()).load(sNoPlateImgFile).into(imageView);
                }
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
