package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
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

public class InputAlarmPanelFragment extends MainFragment {

    private static ImageView frontimg, openImg, sNoPlateImg;
    private static File frontimgFile, openImgFile, sNoPlateImgFile;
    private static boolean isImage1, isImage2;
    private AppCompatEditText etDescription;
    private AppCompatEditText etAnchorOperator, etSharingOperator;
    private AppCompatAutoCompleteTextView etMake, etModel, etCapacity, etSerialNum;

    private String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription;
    private String strSavedDateTime, strUserId, strSiteId, strDescriptionId, itemCondition;
    private String strMakeId = "0", strModelId;
    private String CurtomerSite_Id;
    private AtmDatabase atmDatabase;
    private Spinner itemConditionSpinner;
    private String make = "", model = "", capacity = "", serialNumber = "", yearOfManufacturing = "", description = "", currentDateTime = "", AnchorOperator = "", SharingOperator = "";
    private Button btnSubmit;
    private LinearLayout descriptionLayout;
    private Spinner itemStatusSpineer;
    private SharedPreferences userPref;
    private TextView etYear, dateIcon;
    private Typeface materialdesignicons_font;
    private LinearLayout dateLayout;
    private long datemilisec;
    private String strEqupId;
    private String capcityId = "0";
    private String itemstatus;
    private ArrayList<EquipMakeDataModel> equipMakeList;
    private ArrayList<EquipMakeDataModel> equipList;
    private ArrayList<EquipCapacityDataModel> equipCapacityList;
    private String[] arrMake;
    private String[] arrCapacity;
    private boolean isFaulty;
    private CardView image1ImageCardview, image12ImageCardview, image3ImageCardview;
    private TextView frontPhotolabl;


    @Override
    protected int fragmentLayout() {
        return R.layout.inputalarmpannel_fragment;
    }


    @Override
    protected void loadView() {
        btnSubmit = findViewById(R.id.btnSubmit);
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
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        etAnchorOperator = findViewById(R.id.etAnchorOperator);
        etSharingOperator = findViewById(R.id.etSharingOperator);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
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
                DatePickerDialog dpDialog = new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpDialog.show();
            }
        });
    }

    @Override
    protected void setAccessibility() {

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

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }


    @Override
    protected void dataToView() {
        getUserPref();
        setSpinnerValue();
        atmDatabase = new AtmDatabase(getContext());
        equipList = atmDatabase.getEquipmentData("IAP");
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "IAP");
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
        if (!isEmptyStr(strMake) || !isEmptyStr(strModel) || !isEmptyStr(strCapacity) || !isEmptyStr(strSerialNum)
                || !isEmptyStr(strYearOfManufacturing) || !isEmptyStr(strDescription) || !isEmptyStr(AnchorOperator)
                || !isEmptyStr(SharingOperator)) {

        }
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
                    frontimg.setEnabled(false);
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
                    etAnchorOperator.setEnabled(false);
                    etSharingOperator.setEnabled(false);

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
                    itemConditionSpinner.setEnabled(true);
                    descriptionLayout.setEnabled(true);
                    etAnchorOperator.setEnabled(true);
                    etSharingOperator.setEnabled(true);
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
            String imageName = CurtomerSite_Id + "_IAP_1_EquipmentSepcificationPhoto.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = true;
            isImage2 = false;
        } else if (view.getId() == R.id.image2) {
            String imageName = CurtomerSite_Id + "_IAP_1_SystemOpenPhoto.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = false;
            isImage2 = true;
        } else if (view.getId() == R.id.image3) {
            String imageName = CurtomerSite_Id + "_IAP_1_SerialNoPlate.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = false;
            isImage2 = false;
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();
            }

        }
    }


    // ----validation -----
    private boolean isValidate() {
        make = getTextFromView(this.etMake);
        model = getTextFromView(this.etModel);
        capacity = getTextFromView(this.etCapacity);
        serialNumber = getTextFromView(this.etSerialNum);
        AnchorOperator = getTextFromView(this.etAnchorOperator);
        SharingOperator = getTextFromView(this.etSharingOperator);
        yearOfManufacturing = getTextFromView(this.etYear);
        itemCondition = itemConditionSpinner.getSelectedItem().toString();
        description = getTextFromView(this.etDescription);
        currentDateTime = String.valueOf(System.currentTimeMillis());
        itemstatus = itemStatusSpineer.getSelectedItem().toString();
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
            if (ASTObjectUtil.isEmptyStr(make)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Make");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(model)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Model");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(serialNumber)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Serial Number");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(capacity)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(itemCondition)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Item Condition");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(yearOfManufacturing)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Manufacturing Date");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(description) && itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(AnchorOperator)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Anchor Operator");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(SharingOperator)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Sharing Operator");
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
                EquipmentDataa.put("EquipmentSno", "1");
                EquipmentDataa.put("Equipment", "MPPT");
                EquipmentDataa.put("MakeID", strMakeId);
                EquipmentDataa.put("Make", make);
                EquipmentDataa.put("Capacity", capacity);
                EquipmentDataa.put("SerialNo", serialNumber);
                EquipmentDataa.put("MfgDate", datemilisec);
                EquipmentDataa.put("ItemCondition", itemCondition);
                EquipmentDataa.put("IAP_AnchorOperator", AnchorOperator);
                EquipmentDataa.put("IAP_SharingOperator", SharingOperator);
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
                            reloadBackScreen();
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
            String imageName;
            if (itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
                imageName = CurtomerSite_Id + "_IAP_1__FaultyPhoto.jpg";
            } else {
                imageName = CurtomerSite_Id + "_IAP_1_EquipmentSepcificationPhoto.jpg";
            }

            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, frontimg, imageName);
            }
        } else if (isImage2) {
            String imageName = CurtomerSite_Id + "_IAP_1_SystemOpenPhoto.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, openImg, imageName);
            }
        } else {
            String imageName = CurtomerSite_Id + "_IAP_1_SerialNoPlate.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, sNoPlateImg, imageName);
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
                Bitmap bitmap = FilePickerHelper.compressImage(file.getAbsolutePath(), ot, 800.0f, 800.0f, imageName);
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
