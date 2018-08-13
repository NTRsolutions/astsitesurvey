package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
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

public class MpptFragment extends MainFragment {
    static ImageView frontImg, openImg, sNoPlateImg;
    static boolean isImage1, isImage2;
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner;
    String strUserId, strSavedDateTime, strSiteId, CurtomerSite_Id;
    String make, model, capacity, serialNumber, description, currentDateTime, yearOfManufacturing;
    AutoCompleteTextView etCapacity, etMake, etSerialNum, etModel;
    AppCompatEditText etDescription, etMPPTReading;
    String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription, MPPTReading, itemCondition;
    String strMakeId = "0", strModelId, strDescriptionId;
    Spinner itemStatusSpineer;
    TextView etYear, dateIcon;
    LinearLayout dateLayout;
    long datemilisec;
    static File frontimgFile, openImgFile, sNoPlateImgFile;
    Typeface materialdesignicons_font;
    SharedPreferences mpptSharedPrefpref, userPref;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_mppt;
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
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        etMPPTReading = findViewById(R.id.etMPPTReading);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
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

    public void getSharedPrefData() {
/*        mpptSharedPrefpref = getContext().getSharedPreferences("mpptSharedPref", MODE_PRIVATE);
        strUserId = mpptSharedPrefpref.getString("USER_ID", "");
        strMake = mpptSharedPrefpref.getString("MPPT_Make", "");
        strModel = mpptSharedPrefpref.getString("MPPT_Model", "");
        strCapacity = mpptSharedPrefpref.getString("MPPT_Capacity", "");
        strMakeId = mpptSharedPrefpref.getString("MPPT_MakeId", "");
        strModelId = mpptSharedPrefpref.getString("MPPT_ModelId", "");
        strDescriptionId = mpptSharedPrefpref.getString("MPPT_DescriptionId", "");
        strSerialNum = mpptSharedPrefpref.getString("MPPT_SerialNum", "");
        strYearOfManufacturing = mpptSharedPrefpref.getString("MPPT_YearOfManufacturing", "");
        strDescription = mpptSharedPrefpref.getString("MPPT_Description", "");
        frontphoto = mpptSharedPrefpref.getString("MPPT_Photo1", "");
        openPhoto = mpptSharedPrefpref.getString("MPPT_Photo2", "");
        sNoPlatephoto = mpptSharedPrefpref.getString("MPPT_Photo3", "");
        strSavedDateTime = mpptSharedPrefpref.getString("MPPT_EbMeterSavedDateTime", "");
        strSiteId = mpptSharedPrefpref.getString("SiteId", "");
        MPPTReading = mpptSharedPrefpref.getString("MPPTReading", "");*/

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
        getSharedPrefData();
        setSpinnerValue();

        if (!isEmptyStr(strMake) || !isEmptyStr(strModel) || !isEmptyStr(strCapacity)
                || !isEmptyStr(strSerialNum)
                || !isEmptyStr(strYearOfManufacturing) || !isEmptyStr(strDescription) ||
                !isEmptyStr(MPPTReading)) {
            etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            etMPPTReading.setText(MPPTReading);
        }
/*        if (!frontphoto.equals("") || !openPhoto.equals("") || !sNoPlatephoto.equals("")) {
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(frontphoto)).placeholder(R.drawable.noimage).into(frontImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(openPhoto)).placeholder(R.drawable.noimage).into(openImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(sNoPlatephoto)).placeholder(R.drawable.noimage).into(sNoPlateImg);
        }*/
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
                    etMPPTReading.setEnabled(false);
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
                    etMPPTReading.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
               /* SharedPreferences.Editor editor = pref.edit();
                editor.putString("MPPT_UserId", strUserId);
                editor.putString("MPPT_Make", make);
                editor.putString("MPPT_Model", model);
                editor.putString("MPPT_Capacity", capacity);
                editor.putString("MPPT_DescriptionId", strDescriptionId);
                editor.putString("MPPT_MakeId", strMakeId);
                editor.putString("MPPT_ModelId", strModelId);
                editor.putString("MPPT_SerialNum", serialNumber);
                editor.putString("MPPT_YearOfManufacturing", yearOfManufacturing);
                editor.putString("MPPT_Description", description);
                editor.putString("MPPT_Photo1", frontphoto);
                editor.putString("MPPT_Photo2", openPhoto);
                editor.putString("MPPT_Photo3", sNoPlatephoto);
                editor.putString("MPPT_EbMeterSavedDateTime", currentDateTime);
                editor.putString("MPPTReading", MPPTReading);
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
        MPPTReading = etMPPTReading.getText().toString();
        description = etDescription.getText().toString();
        currentDateTime = String.valueOf(System.currentTimeMillis());
        currentDateTime = String.valueOf(System.currentTimeMillis());
        itemCondition = itemConditionSpinner.getSelectedItem().toString();
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
            } else if (isEmptyStr(description)) {
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
            }
        } else {
            ASTUIUtil.showToast("Item Not Available");
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
                    String imageName = CurtomerSite_Id + "_MPPT_1_Front.png";
                    frontimgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(frontimgFile).into(frontImg);
                    //overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    String imageName = CurtomerSite_Id + "_MPPT_1_Open.png";
                    openImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(openImgFile).into(openImg);
                } else {
                    String imageName = CurtomerSite_Id + "_MPPT_1_SerialNoPlate.png";
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
                JSONObject EquipmentData = new JSONObject();
                EquipmentData.put("EquipmentSno", "0");
                EquipmentData.put("EquipmentID", "0");
                EquipmentData.put("Equipment", "MPPT");
                EquipmentData.put("MakeID", strMakeId);
                EquipmentData.put("Capacity_ID", "0");
                EquipmentData.put("Capacity", capacity);
                EquipmentData.put("SerialNo", serialNumber);
                EquipmentData.put("MfgDate", datemilisec);
                EquipmentData.put("MPPT_Reading", MPPTReading);
                EquipmentData.put("ItemCondition", itemCondition);
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
                            ASTUIUtil.showToast("Your MPPT Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Your MPPT Data has not been updated!");
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
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
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
}
