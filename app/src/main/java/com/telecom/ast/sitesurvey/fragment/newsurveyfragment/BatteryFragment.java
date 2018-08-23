package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.util.Log;
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
import com.telecom.ast.sitesurvey.utils.ASTUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;
import com.telecom.ast.sitesurvey.utils.FilePickerHelper;
import com.telecom.ast.sitesurvey.utils.FontManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class BatteryFragment extends MainFragment {

    private static ImageView batteryimg, cellImg, sNoPlateImg;
    private static File batteryimgFile, cellImgFile, sNoPlateImgImgFile;
    private static boolean isImage1, isImage2;
    private AppCompatAutoCompleteTextView etMake;
    private AppCompatEditText etDescription, etNoofItems, etNoofCell, etCellVoltage, etNoofWeakCells, etBackUpinHrs,
            etTightnessofBentCaps, etCellInterconnecting;
    private AppCompatAutoCompleteTextView etModel, etCapacity, etSerialNum;
    private String strMake, strModel, strCapacity, strSerialNum, strYearOfManufacturing, strDescription;
    private static String strSavedDateTime, strUserId, strSiteId, itemCondition, CurtomerSite_Id;
    private String NoofItems = "", NoofCell = "", CellVoltage = "", NoofWeakCells = "", BackUpinHrs = "",
            TightnessofBentCaps = "", CellInterconnecting = "";
    private String strMakeId = "", strEqupId = "";
    private ArrayList<EquipMakeDataModel> equipMakeList;
    private ArrayList<EquipMakeDataModel> equipList;
    private AtmDatabase atmDatabase;
    private String[] arrMake;
    private ArrayList<EquipCapacityDataModel> equipCapacityList;
    private String[] arrCapacity;
    private Spinner itemConditionSpinner;
    private String make, model, capacity, serialNumber, yearOfManufacturing, description, currentDateTime;
    private LinearLayout descriptionLayout;
    private Spinner itemStatusSpineer;
    private SharedPreferences batterySharedPref, userPref;
    private TextView etYear, dateIcon, next, done, previous;
    private Typeface materialdesignicons_font;
    private LinearLayout dateLayout;
    private LinearLayout nextLayout;
    private long datemilisec;
    private String capcityId = "0";
    private boolean isLast = false;
    private int screenPosition = 1;
    private Button btnSubmmit;
    private String itemstatus;


    @Override
    protected int fragmentLayout() {
        return R.layout.activity_battery;
    }

    @Override
    protected void loadView() {
        batteryimg = findViewById(R.id.image1);
        cellImg = findViewById(R.id.image2);
        sNoPlateImg = findViewById(R.id.image3);
        etMake = findViewById(R.id.etMake);
        etModel = findViewById(R.id.etModel);
        etCapacity = findViewById(R.id.etCapacity);
        etSerialNum = findViewById(R.id.etSerialNum);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        etNoofItems = findViewById(R.id.etNoofItems);
        etNoofCell = findViewById(R.id.etNoofCell);
        etCellVoltage = findViewById(R.id.etCellVoltage);
        etNoofWeakCells = findViewById(R.id.etNoofWeakCells);
        etBackUpinHrs = findViewById(R.id.etBackUpinHrs);
        etTightnessofBentCaps = findViewById(R.id.etTightnessofBentCaps);
        etCellInterconnecting = findViewById(R.id.etCellInterconnecting);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
        previous = findViewById(R.id.previous);
        previous.setTypeface(materialdesignicons_font);
        previous.setText(Html.fromHtml("&#xf141;"));
        nextLayout = findViewById(R.id.nextLayout);
        TextView nextIcon = findViewById(R.id.nextIcon);
        nextIcon.setTypeface(materialdesignicons_font);
        nextIcon.setText(Html.fromHtml("&#xf142;"));
        next = findViewById(R.id.next);
        done = findViewById(R.id.done);
        btnSubmmit = findViewById(R.id.btnSubmmit);

    }


    @Override
    protected void setClickListeners() {
        batteryimg.setOnClickListener(this);
        cellImg.setOnClickListener(this);
        sNoPlateImg.setOnClickListener(this);
        dateLayout.setOnClickListener(this);
        done.setOnClickListener(this);
        previous.setOnClickListener(this);
        nextLayout.setOnClickListener(this);
        btnSubmmit.setOnClickListener(this);
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

    @Override
    public void onResume() {
        super.onResume();
        // ASTUIUtil.showToast("Position"+screenPosition);
        Log.d(Contants.LOG_TAG, "onResume" + screenPosition);
    }

    public void setSpinnerValue() {
        final String itemCondition_array[] = {"Ok", "Not Ok", "Fully Fault"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(homeadapter);

        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);
    }

    private void setLastPageDoneButton() {
       /* if (isLast) {
            done.setVisibility(View.VISIBLE);
            nextLayout.setVisibility(View.GONE);
        } else {
            nextLayout.setVisibility(View.VISIBLE);
            done.setVisibility(View.GONE);
        }*/
        nextLayout.setVisibility(View.VISIBLE);
        done.setVisibility(View.GONE);
    }

    @Override
    protected void dataToView() {
        setLastPageDoneButton();
        atmDatabase = new AtmDatabase(getContext());
        equipList = atmDatabase.getEquipmentData("BB");
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "BB");
        arrMake = new String[equipMakeList.size()];
        for (int i = 0; i < equipMakeList.size(); i++) {
            arrMake[i] = equipMakeList.get(i).getName();
        }
        getSharedprefData();
        getUserPref();
        setSpinnerValue();
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
                || !isEmptyStr(strYearOfManufacturing) || !isEmptyStr(strDescription)
                || !isEmptyStr(NoofItems)
                || !isEmptyStr(NoofCell)
                || !isEmptyStr(CellVoltage)
                || !isEmptyStr(NoofWeakCells)
                || !isEmptyStr(BackUpinHrs)
                || !isEmptyStr(TightnessofBentCaps)
                || !isEmptyStr(CellInterconnecting)
                ) {
          /*  etMake.setText(strMake);
            etModel.setText(strModel);
            etCapacity.setText(strCapacity);
            etSerialNum.setText(strSerialNum);
            etYear.setText(strYearOfManufacturing);
            etDescription.setText(strDescription);
            etNoofItems.setText(NoofItems);
            etNoofCell.setText(NoofCell);
            etCellVoltage.setText(CellVoltage);
            etNoofWeakCells.setText(BackUpinHrs);
            etBackUpinHrs.setText(BackUpinHrs);
            etTightnessofBentCaps.setText(TightnessofBentCaps);
            etCellInterconnecting.setText(CellInterconnecting);*/


            /*if (!bateryphoto.equals("") || !cellPhoto.equals("") || !sNoPlatephoto.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(bateryphoto)).placeholder(R.drawable.noimage).into(batteryimg);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(cellPhoto)).placeholder(R.drawable.noimage).into(cellImg);
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
                    batteryimg.setEnabled(false);
                    cellImg.setEnabled(false);
                    sNoPlateImg.setEnabled(false);
                    etMake.setEnabled(false);
                    etModel.setEnabled(false);
                    etCapacity.setEnabled(false);
                    etSerialNum.setEnabled(false);
                    etYear.setEnabled(false);
                    etDescription.setEnabled(false);
                    itemConditionSpinner.setEnabled(false);
                    descriptionLayout.setEnabled(false);


                    etNoofItems.setEnabled(false);
                    etNoofCell.setEnabled(false);
                    etCellVoltage.setEnabled(false);
                    etNoofWeakCells.setEnabled(false);
                    etBackUpinHrs.setEnabled(false);
                    etTightnessofBentCaps.setEnabled(false);
                    etCellInterconnecting.setEnabled(false);
                } else {
                    batteryimg.setEnabled(true);
                    cellImg.setEnabled(true);
                    sNoPlateImg.setEnabled(true);
                    etMake.setEnabled(true);
                    etModel.setEnabled(true);
                    etCapacity.setEnabled(true);
                    etSerialNum.setEnabled(true);
                    etYear.setEnabled(true);
                    etDescription.setEnabled(true);
                    itemConditionSpinner.setEnabled(true);
                    descriptionLayout.setEnabled(true);

                    etNoofItems.setEnabled(true);
                    etNoofCell.setEnabled(true);
                    etCellVoltage.setEnabled(true);
                    etNoofWeakCells.setEnabled(true);
                    etBackUpinHrs.setEnabled(true);
                    etTightnessofBentCaps.setEnabled(true);
                    etCellInterconnecting.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /*
     *
     *     Shared Prefrences
     */
    public void getSharedprefData() {
 /*       batterySharedPref = getContext().getSharedPreferences("BatterySharedPref", MODE_PRIVATE);
        strUserId = batterySharedPref.getString("USER_ID", "");
        strMake = batterySharedPref.getString("Make", "");
        strModel = batterySharedPref.getString("Model", "");
        strCapacity = batterySharedPref.getString("Capacity", "");
        strMakeId = batterySharedPref.getString("MakeId", "");
        strModelId = batterySharedPref.getString("ModelId", "");
        strDescriptionId = batterySharedPref.getString("DescriptionId", "");
        strSerialNum = batterySharedPref.getString("SerialNum", "");
        strYearOfManufacturing = batterySharedPref.getString("YearOfManufacturing", "");
        strDescription = batterySharedPref.getString("Description", "");
        bateryphoto = batterySharedPref.getString("batryPhoto1", "");
        cellPhoto = batterySharedPref.getString("batryPhoto2", "");
        sNoPlatephoto = batterySharedPref.getString("batryPhoto3", "");
        strSavedDateTime = batterySharedPref.getString("BbActivitySavedDateTime", "");
        strSiteId = batterySharedPref.getString("SiteId", "");
        itemCondition = batterySharedPref.getString("ItemCondition", "");
        NoofItems = batterySharedPref.getString("NoofItems", "");
        NoofCell = batterySharedPref.getString("NoofCell", "");
        CellVoltage = batterySharedPref.getString("CellVoltage", "");
        NoofWeakCells = batterySharedPref.getString("NoofWeakCells", "");
        BackUpinHrs = batterySharedPref.getString("BackUpinHrs", "");
        TightnessofBentCaps = batterySharedPref.getString("TightnessofBentCaps", "");
        CellInterconnecting = batterySharedPref.getString("CellInterconnecting", "");*/


    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nextLayout) {
          /*  if (isValidate()) {
                saveScreenData(true, false);
            }*/
        } else if (view.getId() == R.id.previous) {
            //saveScreenData(false, false);
        } else if (view.getId() == R.id.done) {
            if (isValidate()) {
                // saveScreenData(true, true);
            }
        } else if (view.getId() == R.id.dateLayout) {
            setDateofSiteonAir();
        } else if (view.getId() == R.id.image1) {
            //ASTUIUtil.startImagePicker(getHostActivity());
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_Front.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            // ASTUtil.startFilePicker(getHostActivity(), 1, FNFilePicker.SIZE_LIMIT - attachmentSize());
            isImage1 = true;
            isImage2 = false;
        } else if (view.getId() == R.id.image2) {
            //ASTUIUtil.startImagePicker(getHostActivity());
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_Open.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            isImage1 = false;
            isImage2 = true;
        } else if (view.getId() == R.id.image3) {
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_SerialNoPlate.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            //ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = false;
        } else if (view.getId() == R.id.btnSubmmit) {
            if (isValidate()) {
                saveBatteryDataonServer();
            }
        }

    }


    // ----validation -----
    private boolean isValidate() {
        make = getTextFromView(this.etMake);
        model = getTextFromView(this.etCapacity);
        capacity = getTextFromView(this.etCapacity);
        serialNumber = getTextFromView(this.etSerialNum);
        yearOfManufacturing = getTextFromView(this.etYear);
        itemCondition = itemConditionSpinner.getSelectedItem().toString();
        description = getTextFromView(this.etDescription);
        currentDateTime = String.valueOf(System.currentTimeMillis());
        NoofItems = getTextFromView(this.etNoofItems);
        NoofCell = getTextFromView(this.etNoofCell);
        CellVoltage = getTextFromView(this.etCellVoltage);
        NoofWeakCells = getTextFromView(this.etNoofWeakCells);
        BackUpinHrs = getTextFromView(this.etBackUpinHrs);
        TightnessofBentCaps = getTextFromView(this.etTightnessofBentCaps);
        CellInterconnecting = getTextFromView(this.etCellInterconnecting);
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

            } else if (isEmptyStr(itemCondition)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Item Condition");
                return false;
            } else if (isEmptyStr(yearOfManufacturing)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Manufacturing Year");
                return false;
            } else if (isEmptyStr(description) && itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
                return false;
            } else if (batteryimgFile == null || !batteryimgFile.exists()) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Battery Bank Photo");
                return false;
            } else if (cellImgFile == null || !cellImgFile.exists()) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select One Cell Photo");
                return false;
            } else if (sNoPlateImgImgFile == null || !sNoPlateImgImgFile.exists()) {
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
                    String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_Front.jpg";
                    batteryimgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(batteryimgFile).into(batteryimg);
                    //overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_Open.jpg";
                    cellImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(cellImgFile).into(cellImg);
                } else {
                    String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_SerialNoPlate.jpg";
                    sNoPlateImgImgFile = ASTUIUtil.renameFile(deviceFile.getFileName(), imageName);
                    Picasso.with(ApplicationHelper.application().getContext()).load(sNoPlateImgImgFile).into(sNoPlateImg);
                }
            }
        }
    }


    public void getResult(ArrayList<MediaFile> files) {
        getPickedFiles(files);
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

    //save screen data into battery home screen through Broadcast
    private JSONObject makeJsonData() {
        JSONObject mainObj = new JSONObject();
        try {
            getMakeAndEqupmentId();
            mainObj.put("Site_ID", strSiteId);
            mainObj.put("User_ID", strUserId);
            mainObj.put("Activity", "Equipment");

            JSONObject EquipmentData = new JSONObject();
            EquipmentData.put("EquipmentStatus", itemstatus);
            EquipmentData.put("EquipmentSno", screenPosition);
            EquipmentData.put("EquipmentID", strEqupId);
            EquipmentData.put("CapacityID", capcityId);
            EquipmentData.put("MakeID", strMakeId);
            EquipmentData.put("Make", make);
            EquipmentData.put("Equipment", "BB");
            EquipmentData.put("Capacity", capacity);
            EquipmentData.put("SerialNo", serialNumber);
            EquipmentData.put("MfgDate", datemilisec);
            EquipmentData.put("ItemCondition", itemCondition);
            EquipmentData.put("BB_NoofBB", NoofItems);
            EquipmentData.put("BB_NoofCell", NoofCell);
            EquipmentData.put("BB_CellVoltage", CellVoltage);
            EquipmentData.put("BB_NoofWeakCells", NoofWeakCells);
            EquipmentData.put("BB_BackUp", BackUpinHrs);
            EquipmentData.put("BB_TightnessofBentCaps", TightnessofBentCaps);
            EquipmentData.put("BB_CellInterconnectingstriptightness", CellInterconnecting);

            JSONArray equipArray = new JSONArray();
            equipArray.put(EquipmentData);
            mainObj.put("EquipmentData", equipArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainObj;
    }

    //add images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (batteryimgFile != null && batteryimgFile.exists()) {
            multipartBody.addFormDataPart(batteryimgFile.getName(), batteryimgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, batteryimgFile));
        }
        if (cellImgFile != null && cellImgFile.exists()) {
            multipartBody.addFormDataPart(cellImgFile.getName(), cellImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, cellImgFile));
        }
        if (sNoPlateImgImgFile != null && sNoPlateImgImgFile.exists()) {
            multipartBody.addFormDataPart(sNoPlateImgImgFile.getName(), sNoPlateImgImgFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sNoPlateImgImgFile));
        }
        return multipartBody;
    }

    //save battery data into server
    public void saveBatteryDataonServer() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar progressBar = new ASTProgressBar(getContext());
            progressBar.show();
            String serviceURL = Constant.BASE_URL + Constant.SurveyDataSave;
            JSONObject mainObj = makeJsonData();
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("JsonData", mainObj.toString());
            FileUploaderHelper fileUploaderHelper = new FileUploaderHelper(getContext(), payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        if (data.getStatus() == 1) {
                            ASTUIUtil.showToast("Your Battery Data save Successfully");
                            showAddMoreItemDialog();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Battery Data  has not been updated!");
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

    public void showAddMoreItemDialog() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        android.support.v7.app.AlertDialog dialog = builder.create();
        //dialog.getWindow().getAttributes().windowAnimations = R.style.alertAnimation;
        dialog.setMessage("Do you want do add more Battery Item Details");
        dialog.setTitle("Battery Alert");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Add More", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearFiledData();
                screenPosition = screenPosition + 1;
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

    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
      /*  if (requestCode == FNReqResCode.ATTACHMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FNFilePicker.EXTRA_SELECTED_MEDIA);
            //getResult(files);
        }*/

        if (resultCode == Activity.RESULT_OK) {
            onCaptureImageResult();
        }
    }

    //capture image compress
    private void onCaptureImageResult() {
        if (isImage1) {
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_Front.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, batteryimg);
            }
        } else if (isImage2) {
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_Open.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, cellImg);
            }
        } else {
            String imageName = CurtomerSite_Id + "_BB_" + screenPosition + "_SerialNoPlate.jpg";
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
                    batteryimgFile = imgFile;
                    Picasso.with(ApplicationHelper.application().getContext()).load(batteryimgFile).into(imageView);
                } else if (isImage2) {
                    cellImgFile = imgFile;
                    Picasso.with(ApplicationHelper.application().getContext()).load(cellImgFile).into(imageView);
                } else {
                    sNoPlateImgImgFile = imgFile;
                    Picasso.with(ApplicationHelper.application().getContext()).load(sNoPlateImgImgFile).into(imageView);
                }
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        }.execute();

    }

    public void clearFiledData() {
        etMake.setText("");
        etModel.setText("");
        etCapacity.setText("");
        etSerialNum.setText("");
        etYear.setText("");
        etDescription.setText("");
        etNoofItems.setText("");
        etNoofCell.setText("");
        etCellVoltage.setText("");
        etNoofWeakCells.setText("");
        etBackUpinHrs.setText("");
        etTightnessofBentCaps.setText("");
        etCellInterconnecting.setText("");
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(batteryimg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(cellImg);
        Picasso.with(ApplicationHelper.application().getContext()).load(R.drawable.noimage).into(sNoPlateImg);
        itemConditionSpinner.setSelection(0);
    }

    private long attachmentSize() {
        long ttlSize = 5000;

        return ttlSize;
    }

    //convert uri into file
    private Boolean addUriAsFile(final Uri uri, final String fileName) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {

                Boolean flag = false;
                File imgFile = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator, fileName);
                try {
                    InputStream iStream = getContext().getContentResolver().openInputStream(uri);
                    byte[] inputData = FilePickerHelper.getBytes(iStream);

                    FileOutputStream fOut = new FileOutputStream(imgFile);
                    fOut.write(inputData);
                    //   bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    iStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);

            }
        }.execute();

        return true;
    }
}