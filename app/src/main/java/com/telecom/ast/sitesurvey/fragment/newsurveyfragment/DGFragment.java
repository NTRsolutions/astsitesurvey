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
import android.support.v7.app.AlertDialog;
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
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipDescriptionDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.utils.ASTObjectUtil;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.ASTUtil;
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

public class DGFragment extends MainFragment {
    private static ImageView frontImg, openImg, sNoPlateImg;
    private static boolean isImage1, isImage2;
    private AppCompatEditText etDescription;
    private AutoCompleteTextView etModel, etMake, etCapacity, etSerialNum;
    private SharedPreferences pref;
    private String strSavedDateTime, strUserId, strSiteId, strDescriptionId, CurtomerSite_Id;
    private String strMakeId, strModelId;
    private String[] arrMake;
    private String[] arrModel;
    private String[] arrCapacity;
    private AtmDatabase atmDatabase;
    private ArrayList<EquipMakeDataModel> equipMakeList;
    private ArrayList<EquipMakeDataModel> equipList;
    private ArrayList<EquipCapacityDataModel> equipCapacityList;
    private ArrayList<EquipMakeDataModel> arrEquipData;
    private ArrayList<EquipCapacityDataModel> equipCapacityDataList;
    private ArrayList<EquipDescriptionDataModel> equipDescriptionDataList;
    private String make = "", model = "", capacity = "", serialNumber = "", yearOfManufacturing = "0", description = "", currentDateTime, itemCondition;
    private Button btnSubmit;
    private LinearLayout descriptionLayout;
    private Spinner itemConditionSpinner;
    private Spinner itemStatusSpineer;

    private String mCBStatus = "", dbAlternatermake = "", eSN = "0",
            dBCapacity = "", dgContacter = "", backCompressor = "", AutomationCondition = "", PowerPanelMake = "", PowerPanelCapacity = "",
            DGType, AlternaterSno = "",
            AlternterCapacity = "", DGBatteryStatus = "", DGBatteryMake = "", Conditionofwiring = "", DGearthing = "0", ConditionCANOPY = "",
            DGRunHourMer = "0", DGlowLUBEWire = "", DGFuelTank = "", CableGrouting = "", DGFoundation = "", DGCoolingtype = "",
            Dgintelpipe = "", Dgoutelpipe = "",
            DGExhaustcondi = "", DGEmergencyStopSwitch = "", RentalDGChangeOver = "", DGPollutionCertificate = "",
            straMFPanelSpinner = "", strnoofDGCylinderSpinner = "";
    private String stralternaterPhaseSpinner = "", stretDGBatterySrNo = "", strdGExhaustSmokecolour = "";
    private Spinner mCBStatusSpinner,
            dgContacterSpinner, backCompressorSpinner;

    private AppCompatEditText etPowerPanelMake, etPowerPanelCapacity, etAlternaterSno,
            etAlternterCapacity, etDGBatteryMake,
            etDGRunHourMeter, etDGFuelTank,

    etdgAlternatermake, eSNSpinner, etdgbCapacity, etDGBatterySrNo;

    private TextView etYear, dateIcon;
    private LinearLayout dateLayout;
    private long datemilisec;
    private static File frontimgFile, openImgFile, sNoPlateImgFile;
    private Typeface materialdesignicons_font;
    private SharedPreferences userPref;
    private SharedPreferences noofPhaseprf;
    private String strEqupId;
    private String capcityId = "0";
    private String itemstatus;
    private Spinner aMFPanelSpinner, automationConditionSpiiner, noofDGCylinderSpinner, alternaterPhaseSpinner,
            etDGBatteryStatus, etConditionofwiring, etDGearthing, etConditionCANOPY, eTDGlowLUBEWire, etCableGroutingspinner,
            etDGFoundation, etDGCoolingtype, etDgintelpipe, etDgouttelpipe, etDGExhaustcondi, etDGEmergencyStopSwitch, etRentalDGChangeOver,
            etDGPollutionCertificate, dGExhaustSmokecolour;
    private boolean isFaulty;
    private CardView image1ImageCardview, image12ImageCardview, image3ImageCardview;
    private TextView frontPhotolabl;


    @Override
    protected int fragmentLayout() {
        return R.layout.activity_dg;
    }

    @Override
    protected void loadView() {
        frontImg = findViewById(R.id.image1);
        openImg = findViewById(R.id.image2);
        sNoPlateImg = findViewById(R.id.image3);
        etMake = findViewById(R.id.etMake);
        etCapacity = findViewById(R.id.etCapacity);
        etModel = findViewById(R.id.etModel);
        etSerialNum = findViewById(R.id.etSerialNum);
        etYear = findViewById(R.id.etYear);
        etDescription = findViewById(R.id.etDescription);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        btnSubmit = findViewById(R.id.btnSubmit);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        mCBStatusSpinner = findViewById(R.id.mCBStatusSpinner);
        etdgAlternatermake = findViewById(R.id.etdgAlternatermake);
        eSNSpinner = findViewById(R.id.eSNSpinner);
        etDGBatteryMake = findViewById(R.id.etDGBatteryMake);
        etdgbCapacity = findViewById(R.id.etdgbCapacity);
        etDGBatterySrNo = findViewById(R.id.etDGBatterySrNo);
        dgContacterSpinner = findViewById(R.id.dgContacterSpinner);
        backCompressorSpinner = findViewById(R.id.backCompressorSpinner);
        etPowerPanelMake = findViewById(R.id.etPowerPanelMake);
        etPowerPanelCapacity = findViewById(R.id.etPowerPanelCapacity);
        etAlternaterSno = findViewById(R.id.etAlternaterSno);
        etAlternterCapacity = findViewById(R.id.etAlternterCapacity);
        etConditionofwiring = findViewById(R.id.etConditionofwiring);
        etDGearthing = findViewById(R.id.etDGearthing);
        etConditionCANOPY = findViewById(R.id.etConditionCANOPY);
        etDGRunHourMeter = findViewById(R.id.etDGRunHourMeter);
        eTDGlowLUBEWire = findViewById(R.id.eTDGlowLUBEWire);
        etDGFuelTank = findViewById(R.id.etDGFuelTank);
        etCableGroutingspinner = findViewById(R.id.etCableGroutingspinner);
        etDGFoundation = findViewById(R.id.etDGFoundation);
        etDGCoolingtype = findViewById(R.id.etDGCoolingtype);
        etDgintelpipe = findViewById(R.id.etDgintelpipe);
        etDgouttelpipe = findViewById(R.id.etDgouttelpipe);
        etDGExhaustcondi = findViewById(R.id.etDGExhaustcondi);
        etDGEmergencyStopSwitch = findViewById(R.id.etDGEmergencyStopSwitch);
        etRentalDGChangeOver = findViewById(R.id.etRentalDGChangeOver);
        etDGPollutionCertificate = findViewById(R.id.etDGPollutionCertificate);
        dateIcon = findViewById(R.id.dateIcon);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
        dateLayout = findViewById(R.id.dateLayout);
        aMFPanelSpinner = findViewById(R.id.aMFPanelSpinner);
        automationConditionSpiiner = findViewById(R.id.automationConditionSpiiner);
        noofDGCylinderSpinner = findViewById(R.id.noofDGCylinderSpinner);
        alternaterPhaseSpinner = findViewById(R.id.alternaterPhaseSpinner);
        etDGBatteryStatus = findViewById(R.id.etDGBatteryStatus);
        dGExhaustSmokecolour = findViewById(R.id.dGExhaustSmokecolour);
        image1ImageCardview = findViewById(R.id.image1ImageCardview);
        image12ImageCardview = findViewById(R.id.image2ImageCardview);
        image3ImageCardview = findViewById(R.id.image3ImageCardview);
        frontPhotolabl = findViewById(R.id.frontPhotolabl);
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

    public void setSpinnerValue() {
        final String itemCondition_array[] = {"Ok", "Not Ok", "Fully Fault"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(homeadapter);

        final String itemStatusSpineer_array[] = {"DG", "Non DG"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);


        final String mCBStatusSpinner_array[] = {"Ok", "Not Ok"};
        ArrayAdapter<String> mCBStatusSpinnerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, mCBStatusSpinner_array);
        mCBStatusSpinner.setAdapter(mCBStatusSpinnerada);


        final String alternaterPhaseSpinner_array[] = {"1 Phase", "3 Phase"};
        ArrayAdapter<String> alternaterPhaseSpinner_arrayda = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, alternaterPhaseSpinner_array);
        alternaterPhaseSpinner.setAdapter(alternaterPhaseSpinner_arrayda);


        final String etConditionofwiring_array[] = {"Ok", "Not ok"};
        ArrayAdapter<String> etConditionofwiringad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etConditionofwiring_array);
        etConditionofwiring.setAdapter(etConditionofwiringad);

        final String dgContacterSpinnerarray[] = {"CANOPY door lock", "door pannel", "ok", "not ok"};
        ArrayAdapter<String> dgContacterSpinnerad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, dgContacterSpinnerarray);
        dgContacterSpinner.setAdapter(dgContacterSpinnerad);


        final String backCompressorSpinner_array[] = {"TOH", "MOH"};
        ArrayAdapter<String> backCompressorSpinnerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, backCompressorSpinner_array);
        backCompressorSpinner.setAdapter(backCompressorSpinnerada);


        final String baMFPanelSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> aMFPanelSpinnerada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, baMFPanelSpinner_array);
        aMFPanelSpinner.setAdapter(aMFPanelSpinnerada);


        final String automationConditionSpinner_array[] = {"OK", "Not OK"};
        ArrayAdapter<String> automationConditionSpinner_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, automationConditionSpinner_array);
        automationConditionSpiiner.setAdapter(automationConditionSpinner_adapter);

        final String noofDGCylinderSpinner_array[] = {"2", "3", "4"};
        ArrayAdapter<String> noofDGCylinderSpinner_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, noofDGCylinderSpinner_array);
        noofDGCylinderSpinner.setAdapter(noofDGCylinderSpinner_adapter);

        final String etDGBatteryStatus_array[] = {"working", "not working"};
        ArrayAdapter<String> etDGBatteryStatus_array_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etDGBatteryStatus_array);
        etDGBatteryStatus.setAdapter(etDGBatteryStatus_array_adapter);

        final String etDGearthing_array[] = {"Body earth", "Neutral Earth"};
        ArrayAdapter<String> etDGearthing_array_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etDGearthing_array);
        etDGearthing.setAdapter(etDGearthing_array_adapter);

        final String etConditionCANOPY_array[] = {"OK", "Not OK"};
        ArrayAdapter<String> etConditionCANOPY_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etConditionCANOPY_array);
        etConditionCANOPY.setAdapter(etConditionCANOPY_adapter);


        final String eTDGlowLUBEWire_array[] = {"Working", "Faulty"};
        ArrayAdapter<String> eTDGlowLUBEWire_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, eTDGlowLUBEWire_array);
        eTDGlowLUBEWire.setAdapter(eTDGlowLUBEWire_adapter);


        final String etCableGroutingspinner_array[] = {"Yes", "No"};
        ArrayAdapter<String> etCableGroutingspinner_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etCableGroutingspinner_array);
        etCableGroutingspinner.setAdapter(etCableGroutingspinner_adapter);


        final String etDGFoundation_array[] = {"Ok", "Not Ok"};
        ArrayAdapter<String> etDGFoundation_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etDGFoundation_array);
        etDGFoundation.setAdapter(etDGFoundation_adapter);


        final String etDGCoolingtype_array[] = {"Air", "Water"};
        ArrayAdapter<String> etDGCoolingtypen_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etDGCoolingtype_array);
        etDGCoolingtype.setAdapter(etDGCoolingtypen_adapter);


        final String etDgpipe_array[] = {"Ok", "Not ok"};
        ArrayAdapter<String> etDgpipe_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etDgpipe_array);
        etDgintelpipe.setAdapter(etDgpipe_adapter);

        final String etDgouttelpipearray[] = {"Ok", "Not ok"};
        ArrayAdapter<String> etDgouttelpipeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etDgouttelpipearray);
        etDgouttelpipe.setAdapter(etDgouttelpipeadapter);

        final String etDGExhaustcondi_array[] = {"Ok", "Not ok"};
        ArrayAdapter<String> etDGExhaustcondi_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etDGExhaustcondi_array);
        etDGExhaustcondi.setAdapter(etDGExhaustcondi_adapter);

        final String dGExhaustSmokecolour_array[] = {"Blue", "Black", "Grey", "White"};
        ArrayAdapter<String> dGExhaustSmokecolour_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, dGExhaustSmokecolour_array);
        dGExhaustSmokecolour.setAdapter(dGExhaustSmokecolour_adapter);


        final String etDGEmergencyStopSwitch_array[] = {"Ok", "Not Ok"};
        ArrayAdapter<String> etDGEmergencyStopSwitch_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etDGEmergencyStopSwitch_array);
        etDGEmergencyStopSwitch.setAdapter(etDGEmergencyStopSwitch_adapter);


        final String etRentalDGChangeOver_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> etRentalDGChangeOver_adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etRentalDGChangeOver_array);
        etRentalDGChangeOver.setAdapter(etRentalDGChangeOver_adapter);


        final String etDGPollutionCertificate_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> etDGPollutionCertificateadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etDGPollutionCertificate_array);
        etDGPollutionCertificate.setAdapter(etDGPollutionCertificateadapter);


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
        getUserPref();
        atmDatabase = new AtmDatabase(getContext());
        equipList = atmDatabase.getEquipmentData("DG");
        equipMakeList = atmDatabase.getEquipmentMakeData("Desc", "DG");
        arrMake = new String[equipMakeList.size()];
        for (int i = 0; i < equipMakeList.size(); i++) {
            arrMake[i] = equipMakeList.get(i).getName();
        }
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
        ASTUIUtil commonFunctions = new ASTUIUtil();
        final String currentDate = commonFunctions.getFormattedDate("dd/MM/yyyy", System.currentTimeMillis());

        itemConditionSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                descriptionLayout.setVisibility(selectedItem.equalsIgnoreCase("Fully Fault") ? View.VISIBLE : View.GONE);

                isFaulty = ASTObjectUtil.isEmptyStr(description) &&
                        itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")
                        || itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Not Ok");
                image12ImageCardview.setVisibility(isFaulty ? View.INVISIBLE : View.VISIBLE);
                image3ImageCardview.setVisibility(isFaulty ? View.GONE : View.VISIBLE);
                frontPhotolabl.setText(isFaulty ? "Faulty Photo" : "  Photo With Equipment Specification(DG run hr meter reading image");

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        itemStatusSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                descriptionLayout.setVisibility(selectedItem.equalsIgnoreCase("") ? View.VISIBLE : View.GONE);
                if (selectedItem.equalsIgnoreCase("Non DG")) {
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
                    mCBStatusSpinner.setEnabled(false);
                    etdgAlternatermake.setEnabled(false);
                    eSNSpinner.setEnabled(false);
                    etdgbCapacity.setEnabled(false);
                    dgContacterSpinner.setEnabled(false);
                    backCompressorSpinner.setEnabled(false);
                    automationConditionSpiiner.setEnabled(false);
                    etPowerPanelMake.setEnabled(false);
                    etPowerPanelCapacity.setEnabled(false);
                    etAlternaterSno.setEnabled(false);
                    etAlternterCapacity.setEnabled(false);
                    etDGBatteryStatus.setEnabled(false);
                    etDGBatteryMake.setEnabled(false);
                    etConditionofwiring.setEnabled(false);
                    etDGearthing.setEnabled(false);
                    etConditionCANOPY.setEnabled(false);
                    etDGRunHourMeter.setEnabled(false);
                    eTDGlowLUBEWire.setEnabled(false);
                    etDGFuelTank.setEnabled(false);
                    etCableGroutingspinner.setEnabled(false);
                    etDGFoundation.setEnabled(false);
                    etDGCoolingtype.setEnabled(false);
                    etDgintelpipe.setEnabled(false);
                    alternaterPhaseSpinner.setEnabled(false);
                    etDgouttelpipe.setEnabled(false);
                    etDGExhaustcondi.setEnabled(false);
                    etDGEmergencyStopSwitch.setEnabled(false);
                    etRentalDGChangeOver.setEnabled(false);
                    etDGPollutionCertificate.setEnabled(false);
                    etDGBatterySrNo.setEnabled(false);
                    dGExhaustSmokecolour.setEnabled(false);
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
                    mCBStatusSpinner.setEnabled(true);
                    etdgAlternatermake.setEnabled(true);
                    eSNSpinner.setEnabled(true);
                    etdgbCapacity.setEnabled(true);
                    dgContacterSpinner.setEnabled(true);
                    backCompressorSpinner.setEnabled(true);
                    automationConditionSpiiner.setEnabled(true);
                    etPowerPanelMake.setEnabled(true);
                    etPowerPanelCapacity.setEnabled(true);
                    etAlternaterSno.setEnabled(true);
                    etAlternterCapacity.setEnabled(true);
                    etDGBatteryStatus.setEnabled(true);
                    alternaterPhaseSpinner.setEnabled(true);
                    etDGBatteryMake.setEnabled(true);
                    etConditionofwiring.setEnabled(true);
                    etDGearthing.setEnabled(true);
                    etConditionCANOPY.setEnabled(true);
                    etDGRunHourMeter.setEnabled(true);
                    eTDGlowLUBEWire.setEnabled(true);
                    etDGFuelTank.setEnabled(true);
                    etCableGroutingspinner.setEnabled(true);
                    etDGFoundation.setEnabled(true);
                    etDGCoolingtype.setEnabled(true);
                    etDgintelpipe.setEnabled(true);
                    etDgouttelpipe.setEnabled(true);
                    etDGExhaustcondi.setEnabled(true);
                    etDGEmergencyStopSwitch.setEnabled(true);
                    etRentalDGChangeOver.setEnabled(true);
                    etDGPollutionCertificate.setEnabled(true);
                    etDGBatterySrNo.setEnabled(true);
                    dGExhaustSmokecolour.setEnabled(true);
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
        }
        if (view.getId() == R.id.image1) {
            isImage1 = true;
            isImage2 = false;
            String imageName = CurtomerSite_Id + "_DG_1_Front.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.image2) {
            isImage1 = false;
            isImage2 = true;
            String imageName = CurtomerSite_Id + "_DG_1_Open.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.image3) {

            isImage1 = false;
            isImage2 = false;
            String imageName = CurtomerSite_Id + "_DG_1_SerialNoPlate.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();
            }
        }
    }


    public boolean isValidate() {
        itemstatus = itemStatusSpineer.getSelectedItem().toString();
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("DG")) {
            make = getTextFromView(this.etMake);
            model = getTextFromView(this.etModel);
            capacity = getTextFromView(this.etCapacity);
            serialNumber = getTextFromView(this.etSerialNum);
            yearOfManufacturing = getTextFromView(this.etYear);
            description = getTextFromView(this.etDescription);
            currentDateTime = String.valueOf(System.currentTimeMillis());
            mCBStatus = mCBStatusSpinner.getSelectedItem().toString();
            dbAlternatermake = getTextFromView(this.etdgAlternatermake);
            eSN = getTextFromView(this.eSNSpinner);
            stralternaterPhaseSpinner = alternaterPhaseSpinner.getSelectedItem().toString();
            dBCapacity = getTextFromView(this.etdgbCapacity);
            stretDGBatterySrNo = getTextFromView(this.etDGBatterySrNo);
            dgContacter = dgContacterSpinner.getSelectedItem().toString();
            backCompressor = backCompressorSpinner.getSelectedItem().toString();
            AutomationCondition = automationConditionSpiiner.getSelectedItem().toString();
            PowerPanelMake = getTextFromView(this.etPowerPanelMake);
            PowerPanelCapacity = getTextFromView(this.etPowerPanelCapacity);
            AlternaterSno = getTextFromView(this.etAlternaterSno);
            AlternterCapacity = getTextFromView(this.etAlternterCapacity);
            DGBatteryStatus = etDGBatteryStatus.getSelectedItem().toString();
            DGBatteryMake = getTextFromView(this.etDGBatteryMake);
            Conditionofwiring = etConditionofwiring.getSelectedItem().toString();
            DGearthing = etDGearthing.getSelectedItem().toString();
            ConditionCANOPY = etConditionCANOPY.getSelectedItem().toString();
            DGlowLUBEWire = eTDGlowLUBEWire.getSelectedItem().toString();
            DGFuelTank = getTextFromView(this.etDGFuelTank);
            CableGrouting = etCableGroutingspinner.getSelectedItem().toString();
            DGFoundation = etDGFoundation.getSelectedItem().toString();
            DGCoolingtype = etDGCoolingtype.getSelectedItem().toString();
            Dgintelpipe = etDgintelpipe.getSelectedItem().toString();
            Dgoutelpipe = etDgouttelpipe.getSelectedItem().toString();
            DGExhaustcondi = etDGExhaustcondi.getSelectedItem().toString();
            DGEmergencyStopSwitch = etDGEmergencyStopSwitch.getSelectedItem().toString();
            RentalDGChangeOver = etRentalDGChangeOver.getSelectedItem().toString();
            DGPollutionCertificate = etDGPollutionCertificate.getSelectedItem().toString();
            straMFPanelSpinner = aMFPanelSpinner.getSelectedItem().toString();
            strnoofDGCylinderSpinner = noofDGCylinderSpinner.getSelectedItem().toString();
            itemCondition = itemConditionSpinner.getSelectedItem().toString();
            strdGExhaustSmokecolour = dGExhaustSmokecolour.getSelectedItem().toString();
            DGRunHourMer = getTextFromView(this.etDGRunHourMeter);
            if (DGRunHourMer.equals("")) {
                DGRunHourMer = "0";
            }

            if (ASTObjectUtil.isEmptyStr(make)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Make");
                return false;
            } else if (isEmptyStr(model)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Model");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(serialNumber)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Serial Number");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(capacity)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(yearOfManufacturing)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Manufacturing Date");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(description) && itemConditionSpinner.getSelectedItem().toString().equalsIgnoreCase("Fully Fault")) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
                return false;
            } else if (ASTObjectUtil.isEmptyStr(straMFPanelSpinner)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Amf Panel");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(PowerPanelMake)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Amf Panel Make");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(PowerPanelCapacity)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Amf Panel Capacity");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(AutomationCondition)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Automation Working Condition");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(mCBStatus)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select  Mcb Status");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(strnoofDGCylinderSpinner)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  No.Of Dg  Cylinder");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(dbAlternatermake)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Alternater Make");
                return false;


            } else if (ASTObjectUtil.isEmptyStr(AlternaterSno)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Alternater Sno");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(AlternterCapacity)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Alternter Capacity");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(stralternaterPhaseSpinner)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Alternater Phase");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(eSN)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Engine Sr. No.");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGBatteryStatus)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Dg Battery Status");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGBatteryMake)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Dg Battery Make");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(stretDGBatterySrNo)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Dg Battery Sr.No.");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(dBCapacity)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Dg Battery Capacity");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGRunHourMer)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Dg Run Hour Meter Reading");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(dgContacter)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Dg Contacter");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(Conditionofwiring)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Condition Of Wiring");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGearthing)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Dg Earthing");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(ConditionCANOPY)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Condition Of Canopy");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGlowLUBEWire)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Low Lube  Oil Pressure");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(backCompressor)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Back Compressor");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGFuelTank)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Dg Fuel Tank Capacity");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(CableGrouting)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter CableGrouting");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGFoundation)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Dg Foundation");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGCoolingtype)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter   Dg Cooling type");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(Dgintelpipe)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Dg Inlet Fuel Pipe");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(Dgoutelpipe)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Dg Outlet Fuel Pipe");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGExhaustcondi)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Dg Exhaust Pipe Condition");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(strdGExhaustSmokecolour)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Dg Exhaust Smoke Colour");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGEmergencyStopSwitch)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Dg Emergency Stop Switch");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(RentalDGChangeOver)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Rental DG ChangeOver Box");
                return false;

            } else if (ASTObjectUtil.isEmptyStr(DGPollutionCertificate)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Dg Pollution Certificate");
                return false;

            } else if (frontimgFile == null || !frontimgFile.exists()) {
                if (isFaulty) {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select DG Faulty Photo");
                } else {
                    ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Photo With Equipment Specification(DG run hr meter reading image)");
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
            // JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject = makeJsonData();
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("JsonData", jsonObject.toString());
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelper fileUploaderHelper = new FileUploaderHelper(getContext(), payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        if (data.getStatus() == 1) {
                            ASTUIUtil.showToast("Site Equipment DG Details updated Successfully");
                            noofPhaseprf = getContext().getSharedPreferences("noofPhaseprf", MODE_PRIVATE);
                            SharedPreferences.Editor editornoofPhaseprf = noofPhaseprf.edit();
                            editornoofPhaseprf.putString("noofPhase", stralternaterPhaseSpinner);
                            editornoofPhaseprf.commit();

                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Your Site Equipment DG Details not updated !");
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

    private JSONObject makeJsonData() {
        JSONObject jsonObject = new JSONObject();
        getMakeAndEqupmentId();
        try {
            jsonObject.put("Site_ID", strSiteId);
            jsonObject.put("User_ID", strUserId);
            jsonObject.put("Activity", "Equipment");
            JSONObject EquipmentData = new JSONObject();
            EquipmentData.put("EquipmentStatus", itemstatus);
            EquipmentData.put("EquipmentSno", "1");
            EquipmentData.put("EquipmentID", strEqupId);
            EquipmentData.put("CapacityID", capcityId);
            EquipmentData.put("MakeID", strMakeId);
            EquipmentData.put("Make", make);
            EquipmentData.put("Equipment", "DG");
            EquipmentData.put("Capacity", capacity);
            EquipmentData.put("SerialNo", serialNumber);
            EquipmentData.put("MfgDate", datemilisec);
            //  EquipmentData.put("DG_PowerDistPanelStatus", pDistribution);
            EquipmentData.put("DG_Type", DGType);
            EquipmentData.put("DG_AMFPanelMake", PowerPanelMake);
            EquipmentData.put("DG_AMFPanelCapacity", PowerPanelCapacity);
            EquipmentData.put("DG_AutomationWorkingCondition", AutomationCondition);
            EquipmentData.put("DG_MCBStatus", mCBStatus);
            EquipmentData.put("DG_AlternaterMake", dbAlternatermake);
            EquipmentData.put("DG_AlternaterSno", AlternaterSno);
            EquipmentData.put("DG_AlternaterCapacity", AlternterCapacity);
            EquipmentData.put("DG_BatteryStatus", DGBatteryStatus);
            EquipmentData.put("DG_BatteryMake", DGBatteryMake);
            EquipmentData.put("DG_BatterySerialNo", stretDGBatterySrNo);
            EquipmentData.put("DG_BatteryCapacity", dBCapacity);
            EquipmentData.put("DG_Contacter", dgContacter);
            EquipmentData.put("DG_WiringCondition", Conditionofwiring);
            EquipmentData.put("DG_Earthing", DGearthing);
            EquipmentData.put("DG_CANOPYCondition", ConditionCANOPY);
            EquipmentData.put("DG_RunHourMeter", DGRunHourMer);
            EquipmentData.put("DG_LLOP", DGlowLUBEWire);
            EquipmentData.put("DG_BackCompressor", backCompressor);
            EquipmentData.put("DG_FuelTankCapacity", DGFuelTank);
            EquipmentData.put("DG_CableGrouting", CableGrouting);
            EquipmentData.put("DG_FoundationStatus", DGFoundation);
            EquipmentData.put("DG_CoolingType", DGCoolingtype);
            EquipmentData.put("DG_InletFuelPipe", Dgintelpipe);
            EquipmentData.put("DG_OutletFuelPipe", Dgoutelpipe);
            EquipmentData.put("DG_ExhaustPipeCondition", DGExhaustcondi);
            EquipmentData.put("DG_EmergencyStopSwitch", DGEmergencyStopSwitch);
            EquipmentData.put("DG_Rental", "");
            EquipmentData.put("DG_ChangeOverBox", RentalDGChangeOver);
            EquipmentData.put("DG_PollutionCertificate", DGPollutionCertificate);
            EquipmentData.put("DG_EngineSNo", eSN);
            EquipmentData.put("ItemCondition", itemCondition);
            EquipmentData.put("DG_AMFPanelStatus", straMFPanelSpinner);
            EquipmentData.put("DG_Cylinder", strnoofDGCylinderSpinner);
            EquipmentData.put("DG_AlternaterPhase", stralternaterPhaseSpinner);
            EquipmentData.put("DG_ExhaustSmokeColour", strdGExhaustSmokecolour);
            JSONArray EquipmentDataa = new JSONArray();
            EquipmentDataa.put(EquipmentData);
            jsonObject.put("EquipmentData", EquipmentDataa);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;

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
            String imageName = CurtomerSite_Id + "_DG_1_Front.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, frontImg);
            }
        } else if (isImage2) {
            String imageName = CurtomerSite_Id + "_DG_1_Open.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, openImg);
            }
        } else {
            String imageName = CurtomerSite_Id + "_DG_1_SerialNoPlate.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, sNoPlateImg);
            }
        }
    }

    //compres image
    private void compresImage(final File file, final String fileName,
                              final ImageView imageView) {
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