package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
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
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;
import com.telecom.ast.sitesurvey.utils.FontManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner, typeTowerSpinner,
            laEarthingStatusSpinner, towerFoundationSpinner, towerTighteningSpinner;
    String strUserId, strSiteId, CurtomerSite_Id, strSavedDateTime, strworkingCondi, strnoMicrowaveAntenna, strnoGSMAntenna, strmissingMem, strEarthingofTower,
            strlaEarthingStatusSpinner, strtowerFoundationSpinner, strtowerTighteningSpinner;
    SharedPreferences towerSharedPrefpref, userPref;
    AppCompatEditText etHeight, etDescription,
            etworkingCondi, etnoMicrowaveAntenna, etnoGSMAntenna, etmissingMem, etEarthingofTower;
    String toerTypestr, typeheightstr, datesiteStr, itemConditionstr, descriptionstr;
    String type, height, date, itemcondion, descreption,
            workingCondi, noMicrowaveAntenna, noGSMAntenna, missingMem, EarthingofTower,
            laEarthingStatus, towerFoundation, towerTightening;
    TextView etYear, dateIcon;
    LinearLayout dateLayout;
    long datemilisec;
    private File overviewImgFile, northmgFile, eastImgFile, southImgFile, westImgFile;
    Typeface materialdesignicons_font;

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
        etmissingMem = findViewById(R.id.etmissingMem);
        etEarthingofTower = findViewById(R.id.etEarthingofTower);
        laEarthingStatusSpinner = findViewById(R.id.laEarthingStatusSpinner);
        towerFoundationSpinner = findViewById(R.id.towerFoundationSpinner);
        towerTighteningSpinner = findViewById(R.id.towerTighteningSpinner);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
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
        final String typeTowerSpinner_array[] = {"RTT", "RTP", "Ground 3 leg", "Ground 4 leg",};
        ArrayAdapter<String> itemCondi = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, typeTowerSpinner_array);
        typeTowerSpinner.setAdapter(itemCondi);

        final String itemCondition_array[] = {"Ok", "Missing", "Poor Condition"};
        ArrayAdapter<String> towerType = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(towerType);


        final String laEarthingStatus_array[] = {"Available", "Not Available", "Connected", "Not Connected"};
        ArrayAdapter<String> laEarthingStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, laEarthingStatus_array);
        laEarthingStatusSpinner.setAdapter(laEarthingStatus);


        final String towerFoundation_array[] = {"Available", "Not Available", "Covered", "Grouted"};
        ArrayAdapter<String> towerFoundation = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, towerFoundation_array);
        towerFoundationSpinner.setAdapter(towerFoundation);


        final String towerTightening_array[] = {"Done", "Not Done"};
        ArrayAdapter<String> towerTightening = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, towerTightening_array);
        towerTighteningSpinner.setAdapter(towerTightening);


    }

    @Override
    protected void dataToView() {
        setSpinnerValue();
        getSharedPrefData();
        getUserPref();
        if (!isEmptyStr(toerTypestr) || !isEmptyStr(typeheightstr) || !isEmptyStr(datesiteStr) || !isEmptyStr(itemConditionstr)
                || !isEmptyStr(descriptionstr)
                || !isEmptyStr(strworkingCondi)
                || !isEmptyStr(strnoMicrowaveAntenna)
                || !isEmptyStr(strnoGSMAntenna)
                || !isEmptyStr(strmissingMem)
                || !isEmptyStr(strEarthingofTower)) {
            etHeight.setText(typeheightstr);
            etYear.setText(datesiteStr);
            etDescription.setText(descriptionstr);
            etworkingCondi.setText(strworkingCondi);
            etnoMicrowaveAntenna.setText(strnoMicrowaveAntenna);
            etnoGSMAntenna.setText(strnoGSMAntenna);
            etmissingMem.setText(strmissingMem);
            etEarthingofTower.setText(strEarthingofTower);

        }
       /* if (!isEmptyStr(overviewImgstr) || !isEmptyStr(northmgStr) || !isEmptyStr(eastImgStr) || !isEmptyStr(westImgStr) || !isEmptyStr(southImgStr)) {
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(overviewImgstr)).placeholder(R.drawable.noimage).into(overviewImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(northmgStr)).placeholder(R.drawable.noimage).into(northmg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(eastImgStr)).placeholder(R.drawable.noimage).into(eastImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(southImgStr)).placeholder(R.drawable.noimage).into(southImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(westImgStr)).placeholder(R.drawable.noimage).into(westImg);
        }*/
        itemConditionSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                descriptionLayout.setVisibility(selectedItem.equalsIgnoreCase("Poor Condition") ? View.VISIBLE : View.GONE);
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

    public void getSharedPrefData() {
      /*  towerSharedPrefpref = getContext().getSharedPreferences("TowerSharedPref", MODE_PRIVATE);
        toerTypestr = towerSharedPrefpref.getString("Tower_Type", "");
        typeheightstr = towerSharedPrefpref.getString("Tower_Height", "");
        datesiteStr = towerSharedPrefpref.getString("Tower_Date_SITE", "");
        itemConditionstr = towerSharedPrefpref.getString("Tower_item_Condi", "");
        descriptionstr = towerSharedPrefpref.getString("Description", "");
        overviewImgstr = towerSharedPrefpref.getString("Tower_Photo1", "");
        northmgStr = towerSharedPrefpref.getString("Tower_Photo2", "");
        eastImgStr = towerSharedPrefpref.getString("Tower_Photo3", "");
        southImgStr = towerSharedPrefpref.getString("Tower_Photo4", "");
        westImgStr = towerSharedPrefpref.getString("Tower_Photo5", "");
        strSiteId = towerSharedPrefpref.getString("SiteId", "");
        laEarthingStatus = towerSharedPrefpref.getString("Tower_laEarthingStatusSpinner", "");
        towerFoundation = towerSharedPrefpref.getString("Tower_towerFoundationSpinner", "");
        towerFoundation = towerSharedPrefpref.getString("Tower_towerTighteningSpinner", "");
        strworkingCondi = towerSharedPrefpref.getString("Tower_workingCondi", "");
        strnoMicrowaveAntenna = towerSharedPrefpref.getString("Tower_noMicrowaveAntenna", "");
        strnoGSMAntenna = towerSharedPrefpref.getString("Tower_noGSMAntenna", "");
        strmissingMem = towerSharedPrefpref.getString("Tower_missingMem", "");
        strEarthingofTower = towerSharedPrefpref.getString("Tower_EarthingofTower", "");*/
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dateLayout) {
            setDateofSiteonAir();
        } else if (view.getId() == R.id.image1) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = true;
            isImage2 = false;
            isImage3 = false;
            isImage4 = false;
            isImage5 = false;
        } else if (view.getId() == R.id.image2) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = true;
            isImage3 = false;
            isImage4 = false;
            isImage5 = false;
        } else if (view.getId() == R.id.image3) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = false;
            isImage3 = true;
            isImage4 = false;
            isImage5 = false;
        } else if (view.getId() == R.id.image4) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = false;
            isImage3 = false;
            isImage4 = true;
            isImage5 = false;
        } else if (view.getId() == R.id.image5) {
            ASTUIUtil.startImagePicker(getHostActivity());
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
        strlaEarthingStatusSpinner = laEarthingStatusSpinner.getSelectedItem().toString();
        strtowerFoundationSpinner = towerFoundationSpinner.getSelectedItem().toString();
        strtowerTighteningSpinner = towerTighteningSpinner.getSelectedItem().toString();
        height = etHeight.getText().toString();
        date = etYear.getText().toString();
        descreption = etDescription.getText().toString();
        workingCondi = etworkingCondi.getText().toString();
        noMicrowaveAntenna = etnoMicrowaveAntenna.getText().toString();
        noGSMAntenna = etnoGSMAntenna.getText().toString();
        missingMem = etmissingMem.getText().toString();
        EarthingofTower = etEarthingofTower.getText().toString();

        if (isEmptyStr(type)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Tower Type");
            return false;
        } else if (isEmptyStr(height)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Height");
            return false;
        } else if (isEmptyStr(date)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Site Date");
            return false;
        } else if (isEmptyStr(itemcondion)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Tower Condition");
            return false;
        } else if (isEmptyStr(descreption) && itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
            return false;
        }/* else if (isEmptyStr(descreption)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
            return false;
        }*/ else if (overviewImgFile == null || !overviewImgFile.exists()) {
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


    public void saveBasicDataintSharedPref() {
        /*SharedPreferences.Editor editor = towerSharedPrefpref.edit();
        editor.putString("Tower_Type", type);
        editor.putString("Tower_Height", height);
        editor.putString("Tower_Date_SITE", date);
        editor.putString("Tower_item_Condi", itemcondion);
        editor.putString("Tower_Description", descreption);
        editor.putString("Tower_Photo1", overviewImgstr);
        editor.putString("Tower_Photo2", northmgStr);
        editor.putString("Tower_Photo3", eastImgStr);
        editor.putString("Tower_Photo4", southImgStr);
        editor.putString("Tower_Photo5", westImgStr);
        editor.putString("Tower_laEarthingStatusSpinner", strlaEarthingStatusSpinner);
        editor.putString("Tower_towerFoundationSpinner", strtowerFoundationSpinner);
        editor.putString("Tower_towerTighteningSpinner", strtowerTighteningSpinner);
        editor.putString("Tower_workingCondi", workingCondi);
        editor.putString("Tower_noMicrowaveAntenna", noMicrowaveAntenna);
        editor.putString("Tower_noGSMAntenna", noGSMAntenna);
        editor.putString("Tower_missingMem", missingMem);
        editor.putString("Tower_EarthingofTower", EarthingofTower);
        editor.commit();*/
    }

    public void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            // if (FNObjectUtil.isNonEmptyStr(deviceFile.getCompressFilePath())) {
             /* //  File compressPath = new File(deviceFile.getCompressFilePath());
                if (compressPath.exists()) {
                    if (isImage1) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(overviewImg);
                        overviewImgstr = deviceFile.getFilePath().toString();
                    } else if (isImage2) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(northmg);
                        northmgStr = deviceFile.getFilePath().toString();
                    } else if (isImage3) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(eastImg);
                        eastImgStr = deviceFile.getFilePath().toString();
                    } else if (isImage4) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(southImg);
                        southImgStr = deviceFile.getFilePath().toString();
                    } else {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(westImg);
                        westImgStr = deviceFile.getFilePath().toString();
                    }
                    //compressPath.delete();
                }
            } else*/

            if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1) {
                    String imageName = CurtomerSite_Id + "_Tower_1_TowerOverview.jpg";
                    overviewImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(overviewImgFile).into(overviewImg);
                    //overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    String imageName = CurtomerSite_Id + "_Tower_1_TowerNorthPhase.jpg";
                    northmgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(northmgFile).into(northmg);
                } else if (isImage3) {
                    String imageName = CurtomerSite_Id + "_Tower_1_TowerEastPhase.jpg";
                    eastImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(eastImgFile).into(eastImg);
                } else if (isImage4) {
                    String imageName = CurtomerSite_Id + "_Tower_1_TowerSouthPhase.jpg";
                    southImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(southImgFile).into(southImg);
                } else {
                    String imageName = CurtomerSite_Id + "_Tower_1_TowerWestPhase.jpg";
                    westImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(westImgFile).into(westImg);
                }
            }
            //  }
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

    @Override
    public boolean onBackPressed() {
        saveBasicDataintSharedPref();
        return super.onBackPressed();
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
                TowerData.put("NoofMicrowaveAntenna", noMicrowaveAntenna);
                TowerData.put("NoofGSMAntenna", noGSMAntenna);
                TowerData.put("TowerFoundationVolt", towerFoundation);
                TowerData.put("TowerTightening", towerTightening);
                TowerData.put("EarthingofeachTowerPole", EarthingofTower);
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
                            saveBasicDataintSharedPref();
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
}