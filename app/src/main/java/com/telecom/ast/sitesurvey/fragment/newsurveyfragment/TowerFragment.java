package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTObjectUtil;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.ASTUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;
import com.telecom.ast.sitesurvey.utils.FilePickerHelper;
import com.telecom.ast.sitesurvey.utils.FontManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class TowerFragment extends MainFragment {

    private ImageView overviewImg, northmg, eastImg, southImg, westImg;
    private boolean isImage1, isImage2, isImage3, isImage4, isImage5;
    private Button btnSubmit;
    private LinearLayout descriptionLayout;
    private Spinner itemConditionSpinner, typeTowerSpinner,
            laEarthingStatusSpinner, towerTighteningSpinner;
    private String strUserId, strSiteId, CurtomerSite_Id,
            strtowerTighteningSpinner;
    private SharedPreferences userPref;
    private AppCompatEditText etHeight, etDescription,
            etnoMicrowaveAntenna, etnoGSMAntenna, TowerPolenoteConnectedDesc, etTowerAngleMissingSize;
    private String type, height, date, itemcondion, descreption,
            workingCondi, noMicrowaveAntenna, noGSMAntenna, EarthingofTower, EarthingofTowerstatus, stretAnyTowerAngleMissing,
            laEarthingStatus;
    private TextView etYear, dateIcon;
    private LinearLayout dateLayout;
    private long datemilisec;
    private File overviewImgFile, northmgFile, eastImgFile, southImgFile, westImgFile;
    private Typeface materialdesignicons_font;
    private LinearLayout laEarthingConnetLauout, PolenoteConnectedLayout, miisingangelSizeLayout, etBoltMissingLayout;
    private Spinner etworkingCondi, laEarthingConnetSpinner, etEarthingofTower, etAnyTowerAngleMissing, etTowerFoundationBolt;
    private String strlaEarthingConnetSpinner, stretTowerAngleMissingSize, stretTowerFoundationBolt, stretFoundationBoltmissingquantity;

    private AppCompatEditText etFoundationBoltmissingquantity;

    @Override
    protected int fragmentLayout() {
        return R.layout.tower_fragment;
    }

    @Override
    protected void loadView() {
        overviewImg = findViewById(R.id.image1);
        northmg = findViewById(R.id.image2);
        eastImg = findViewById(R.id.image3);
        southImg = findViewById(R.id.image4);
        westImg = findViewById(R.id.image5);
        btnSubmit = findViewById(R.id.btnSubmit);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        typeTowerSpinner = findViewById(R.id.typeTowerSpinner);
        etHeight = findViewById(R.id.etHeight);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        etworkingCondi = findViewById(R.id.etworkingCondi);
        etnoMicrowaveAntenna = findViewById(R.id.etnoMicrowaveAntenna);
        etnoGSMAntenna = findViewById(R.id.etnoGSMAntenna);
        etEarthingofTower = findViewById(R.id.etEarthingofTower);
        laEarthingStatusSpinner = findViewById(R.id.laEarthingStatusSpinner);
        towerTighteningSpinner = findViewById(R.id.towerTighteningSpinner);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
        laEarthingConnetSpinner = findViewById(R.id.laEarthingConnetSpinner);
        laEarthingConnetLauout = findViewById(R.id.laEarthingConnetLauout);
        PolenoteConnectedLayout = findViewById(R.id.PolenoteConnectedLayout);
        TowerPolenoteConnectedDesc = findViewById(R.id.TowerPolenoteConnectedDesc);
        etAnyTowerAngleMissing = findViewById(R.id.etAnyTowerAngleMissing);
        etTowerAngleMissingSize = findViewById(R.id.etTowerAngleMissingSize);
        miisingangelSizeLayout = findViewById(R.id.miisingangelSizeLayout);

        etTowerFoundationBolt = findViewById(R.id.etTowerFoundationBolt);
        etFoundationBoltmissingquantity = findViewById(R.id.etFoundationBoltmissingquantity);
        etBoltMissingLayout = findViewById(R.id.etBoltMissingLayout);

    }

    @Override
    protected void setClickListeners() {
        overviewImg.setOnClickListener(this);
        northmg.setOnClickListener(this);
        eastImg.setOnClickListener(this);
        southImg.setOnClickListener(this);
        westImg.setOnClickListener(this);
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
    }


    public void setSpinnerValue() {
        final String typeTowerSpinner_array[] = {"RTT", "RTP", "Ground 3 leg", "Ground 4 leg",};
        ArrayAdapter<String> itemCondi = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, typeTowerSpinner_array);
        typeTowerSpinner.setAdapter(itemCondi);

        final String itemCondition_array[] = {"Ok", "Missing", "Poor Condition"};
        ArrayAdapter<String> towerType = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(towerType);


        final String laEarthingStatus_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> laEarthingStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, laEarthingStatus_array);
        laEarthingStatusSpinner.setAdapter(laEarthingStatus);


        final String towerTightening_array[] = {"Done", "Not Done"};
        ArrayAdapter<String> towerTightening = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, towerTightening_array);
        towerTighteningSpinner.setAdapter(towerTightening);


        final String etworkingCondi_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> etworkingCondiada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etworkingCondi_array);
        etworkingCondi.setAdapter(etworkingCondiada);


        final String laEarthingConnetSpinner_array[] = {"Connected", "Not Connected"};
        ArrayAdapter<String> laEarthingConnetSpinnerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, laEarthingConnetSpinner_array);
        laEarthingConnetSpinner.setAdapter(laEarthingConnetSpinnerada);


        final String etEarthingofTower_array[] = {"Done", "Not Done"};
        ArrayAdapter<String> etEarthingofTowerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etEarthingofTower_array);
        etEarthingofTower.setAdapter(etEarthingofTowerada);

        final String etAnyTowerAngleMissing_array[] = {"Yes", "No"};
        ArrayAdapter<String> etAnyTowerAngleMissingTowerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etAnyTowerAngleMissing_array);
        etAnyTowerAngleMissing.setAdapter(etAnyTowerAngleMissingTowerada);

        final String etTowerFoundationBolt_array[] = {"Covered", "Grouted", "Missing"};
        ArrayAdapter<String> etTowerFoundationBoltada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etTowerFoundationBolt_array);
        etTowerFoundationBolt.setAdapter(etTowerFoundationBoltada);


    }

    @Override
    protected void dataToView() {
        setSpinnerValue();
        getUserPref();
        itemConditionSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                descriptionLayout.setVisibility(selectedItem.equalsIgnoreCase("Poor Condition") ? View.VISIBLE : View.GONE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        laEarthingStatusSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                laEarthingConnetLauout.setVisibility(selectedItem.equalsIgnoreCase("Available") ? View.VISIBLE : View.GONE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etEarthingofTower.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                PolenoteConnectedLayout.setVisibility(selectedItem.equalsIgnoreCase("Not Done") ? View.VISIBLE : View.GONE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        etAnyTowerAngleMissing.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                miisingangelSizeLayout.setVisibility(selectedItem.equalsIgnoreCase("Yes") ? View.VISIBLE : View.GONE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        etTowerFoundationBolt.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                etBoltMissingLayout.setVisibility(selectedItem.equalsIgnoreCase("Missing") ? View.VISIBLE : View.GONE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
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
    public void onClick(View view) {
        if (view.getId() == R.id.dateLayout) {
            setDateofSiteonAir();
        } else if (view.getId() == R.id.image1) {
            String imageName = CurtomerSite_Id + "_Tower_1_TowerOverview.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = true;
            isImage2 = false;
            isImage3 = false;
            isImage4 = false;
            isImage5 = false;
        } else if (view.getId() == R.id.image2) {
            String imageName = CurtomerSite_Id + "_Tower_1_TowerNorthPhase.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = false;
            isImage2 = true;
            isImage3 = false;
            isImage4 = false;
            isImage5 = false;
        } else if (view.getId() == R.id.image3) {
            String imageName = CurtomerSite_Id + "_Tower_1_TowerEastPhase.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = false;
            isImage2 = false;
            isImage3 = true;
            isImage4 = false;
            isImage5 = false;
        } else if (view.getId() == R.id.image4) {
            String imageName = CurtomerSite_Id + "_Tower_1_TowerSouthPhase.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = false;
            isImage2 = false;
            isImage3 = false;
            isImage4 = true;
            isImage5 = false;
        } else if (view.getId() == R.id.image5) {
            String imageName = CurtomerSite_Id + "_Tower_1_TowerWestPhase.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = false;
            isImage2 = false;
            isImage3 = false;
            isImage4 = false;
            isImage5 = true;
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();
            }
        }

    }


    public boolean isValidate() {
        type = typeTowerSpinner.getSelectedItem().toString();
        itemcondion = itemConditionSpinner.getSelectedItem().toString();
        laEarthingStatus = laEarthingStatusSpinner.getSelectedItem().toString();
        strlaEarthingConnetSpinner = laEarthingConnetSpinner.getSelectedItem().toString();
        strtowerTighteningSpinner = towerTighteningSpinner.getSelectedItem().toString();
        height = etHeight.getText().toString();
        date = etYear.getText().toString();
        descreption = etDescription.getText().toString();
        workingCondi = etworkingCondi.getSelectedItem().toString();
        noMicrowaveAntenna = etnoMicrowaveAntenna.getText().toString();
        noGSMAntenna = etnoGSMAntenna.getText().toString();
        EarthingofTower = etEarthingofTower.getSelectedItem().toString();
        EarthingofTowerstatus = TowerPolenoteConnectedDesc.getText().toString();
        stretAnyTowerAngleMissing = etAnyTowerAngleMissing.getSelectedItem().toString();
        stretTowerAngleMissingSize = etTowerAngleMissingSize.getText().toString();
        stretTowerFoundationBolt = etTowerFoundationBolt.getSelectedItem().toString();
        stretFoundationBoltmissingquantity = etFoundationBoltmissingquantity.getText().toString();


        if (ASTObjectUtil.isEmptyStr(type)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Tower Type");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(height)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Height");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(date)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Site Date");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(itemcondion)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Tower Condition");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(descreption) && itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(noMicrowaveAntenna)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Number Of Microwave Antenna ");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(noGSMAntenna)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Number Of Gsm Antenna ");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(stretAnyTowerAngleMissing)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Any Tower Angle Missing ");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(stretTowerFoundationBolt)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Tower Foundation Bolt");
            return false;
        } else if (overviewImgFile == null || !overviewImgFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Overview Photo");
            return false;
        } else if (eastImgFile == null || !eastImgFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select East Photo");
            return false;
        } else if (northmgFile == null || !northmgFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select North Photo");
            return false;
        } else if (southImgFile == null || !southImgFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select South Photo");
            return false;
        } else if (westImgFile == null || !westImgFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select West Photo");
            return false;
        }


        return true;
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
                jsonObject.put("Activity", "Tower");
                JSONObject TowerData = new JSONObject();
                TowerData.put("Type", type);
                TowerData.put("Height", height);
                TowerData.put("SiteOnAirDate", datemilisec);
                TowerData.put("Condition", itemcondion);
                TowerData.put("AVWorkingCondition", workingCondi);
                TowerData.put("LAEarthingStatus", laEarthingStatus);
                TowerData.put("LAEarthingStatusConnected", strlaEarthingConnetSpinner);
                TowerData.put("NoofMicrowaveAntenna", noMicrowaveAntenna);
                TowerData.put("NoofGSMAntenna", noGSMAntenna);
                TowerData.put("TowerFoundationBolt", stretTowerFoundationBolt);
                TowerData.put("TowerFoundationBolt_Missing", stretFoundationBoltmissingquantity);
                TowerData.put("TowerTightening", strtowerTighteningSpinner);
                TowerData.put("EarthingofeachTowerPole", EarthingofTower);
                TowerData.put("EarthingofeachTowerPole_Detail", EarthingofTowerstatus);
                TowerData.put("TowerAngleMissing", stretAnyTowerAngleMissing);
                TowerData.put("TowerAngleMissing_Size", stretTowerAngleMissingSize);


                jsonObject.put("TowerData", TowerData);

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
                            ASTUIUtil.showToast("Your Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("BasiC Data Information has not been updated!");
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
        if (overviewImgFile.exists()) {
            multipartBody.addFormDataPart(overviewImgFile.getName(), overviewImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, overviewImgFile));
        }
        if (northmgFile.exists()) {
            multipartBody.addFormDataPart(northmgFile.getName(), northmgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, northmgFile));
        }
        if (eastImgFile.exists()) {
            multipartBody.addFormDataPart(eastImgFile.getName(), eastImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, eastImgFile));
        }
        if (southImgFile.exists()) {
            multipartBody.addFormDataPart(southImgFile.getName(), southImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, southImgFile));
        }
        if (westImgFile.exists()) {
            multipartBody.addFormDataPart(westImgFile.getName(), westImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, westImgFile));
        }
        return multipartBody;
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
            String imageName = CurtomerSite_Id + "_Tower_1_TowerOverview.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, overviewImg);
            }
        } else if (isImage2) {
            String imageName = CurtomerSite_Id + "_Tower_1_TowerNorthPhase.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, northmg);
            }
        } else if (isImage3) {
            String imageName = CurtomerSite_Id + "_Tower_1_TowerEastPhase.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, eastImg);
            }
        } else if (isImage4) {
            String imageName = CurtomerSite_Id + "_Tower_1_TowerSouthPhase.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, southImg);
            }
        } else {
            String imageName = CurtomerSite_Id + "_Tower_1_TowerWestPhase.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, westImg);
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
                    overviewImgFile = imgFile;
                } else if (isImage2) {
                    northmgFile = imgFile;
                } else if (isImage3) {
                    eastImgFile = imgFile;
                } else if (isImage4) {
                    southImgFile = imgFile;
                } else {
                    westImgFile = imgFile;
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