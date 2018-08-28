package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FontManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class FireSystemFragment extends MainFragment {

    Spinner etfiredetectSpineer, etextinguiserSpineer;
    Spinner etstatusSpinner;
    AutoCompleteTextView etMake, etCapacity;
    String strfiredetectSpineer, status, strextinguiserSpineer, stritemStatusSpineer, firedetectSpineer, extinguiserSpineer, itemStatus, make, capacity;
    LinearLayout fillEmptyLayout;
    Button btnSubmit;
    String strUserId, strSiteId;
    SharedPreferences FireSystemSharedPref, userPref;
    String strEqupId, strMakeId;
    private String capcityId = "0";
    private String itemstatus;
    ArrayList<EquipMakeDataModel> equipMakeList;
    ArrayList<EquipMakeDataModel> equipList;
    ArrayList<EquipCapacityDataModel> equipCapacityList;

    String[] arrMake;
    AtmDatabase atmDatabase;
    String[] arrCapacity;
    Spinner firedetecttypeSpineer, extinguisertypeSpineer, etRefillingStatusSpinner;
    LinearLayout ExtinguiserLayout, FireDetectSystemLayout, FilledDateLayout;
    String strfiredetecttypeSpineer, strextinguisertypeSpineer, stretRefillingStatusSpinner, strFilledDate;
    TextView FilledDate, dateIcon;
    private long datemilisec;
    private Typeface materialdesignicons_font;

    @Override
    protected int fragmentLayout() {
        return R.layout.firesystem_fragment;
    }

    @Override
    protected void loadView() {
        etfiredetectSpineer = this.findViewById(R.id.etfiredetectSpineer);
        etextinguiserSpineer = this.findViewById(R.id.etextinguiserSpineer);
        etstatusSpinner = findViewById(R.id.etstatusSpinner);
        etMake = findViewById(R.id.etMake);
        etCapacity = findViewById(R.id.etCapacity);
        fillEmptyLayout = findViewById(R.id.fillEmptyLayout);
        btnSubmit = findViewById(R.id.btnSubmit);

        ExtinguiserLayout = findViewById(R.id.ExtinguiserLayout);
        FireDetectSystemLayout = findViewById(R.id.FireDetectSystemLayout);
        firedetecttypeSpineer = findViewById(R.id.firedetecttypeSpineer);
        extinguisertypeSpineer = findViewById(R.id.extinguisertypeSpineer);
        etRefillingStatusSpinner = findViewById(R.id.etRefillingStatusSpinner);
        FilledDateLayout = findViewById(R.id.FilledDateLayout);
        dateIcon = findViewById(R.id.dateIconfilled);
        materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(getContext(), "fonts/materialdesignicons-webfont.otf");
        dateIcon.setTypeface(materialdesignicons_font);
        dateIcon.setText(Html.fromHtml("&#xf0ed;"));
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
        FilledDateLayout.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getUserPref();
        setSpinnerValue();
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
        etextinguiserSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Available")) {
                    fillEmptyLayout.setVisibility(View.VISIBLE);
                    ExtinguiserLayout.setVisibility(View.VISIBLE);

                } else {
                    fillEmptyLayout.setVisibility(View.GONE);
                    ExtinguiserLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etfiredetectSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Available")) {
                    FireDetectSystemLayout.setVisibility(View.VISIBLE);

                } else {
                    FireDetectSystemLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etRefillingStatusSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Filled")) {
                    FilledDateLayout.setVisibility(View.VISIBLE);
                } else {
                    FilledDateLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }


    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
    }

    public void setSpinnerValue() {
        final String etfiredetectSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> etfiredetect = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineer_array);
        etfiredetectSpineer.setAdapter(etfiredetect);
        if (strfiredetectSpineer != null && !strfiredetectSpineer.equals("")) {
            for (int i = 0; i < etfiredetectSpineer_array.length; i++) {
                if (strfiredetectSpineer.equalsIgnoreCase(etfiredetectSpineer_array[i])) {
                    etfiredetectSpineer.setSelection(i);
                } else {
                    etfiredetectSpineer.setSelection(0);
                }
            }
        }


        final String etfiredetectSpineertype_array[] = {"Heat", "Smoke"};
        ArrayAdapter<String> etfiredetecttype = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineertype_array);
        firedetecttypeSpineer.setAdapter(etfiredetecttype);
    /*    if (firedetecttypeSpineer != null && !firedetecttypeSpineer.equals("")) {
            for (int i = 0; i < etfiredetectSpineertype_array.length; i++) {
                if (strfiredetecttypeSpineer.equalsIgnoreCase(etfiredetectSpineertype_array[i])) {
                    firedetecttypeSpineer.setSelection(i);
                } else {
                    firedetecttypeSpineer.setSelection(0);
                }
            }
        }
*/

        final String etextinguiserArray[] = {"Available", "Not Available"};
        ArrayAdapter<String> etextinguiser = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etextinguiserArray);
        etextinguiserSpineer.setAdapter(etextinguiser);
        if (strextinguiserSpineer != null && !strextinguiserSpineer.equals("")) {
            for (int i = 0; i < etextinguiserArray.length; i++) {
                if (strextinguiserSpineer.equalsIgnoreCase(etextinguiserArray[i])) {
                    etextinguiserSpineer.setSelection(i);
                } else {
                    etextinguiserSpineer.setSelection(0);
                }
            }
        }


        final String extinguisertypeSpineertype_array[] = {"Co2", "Foam"};
        ArrayAdapter<String> extinguisertypetype = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, extinguisertypeSpineertype_array);
        extinguisertypeSpineer.setAdapter(extinguisertypetype);
    /*    if (extinguisertypeSpineer != null && !extinguisertypeSpineer.equals("")) {
            for (int i = 0; i < extinguisertypeSpineertype_array.length; i++) {
                if (strextinguisertypeSpineer.equalsIgnoreCase(extinguisertypeSpineertype_array[i])) {
                    extinguisertypeSpineer.setSelection(i);
                } else {
                    extinguisertypeSpineer.setSelection(0);
                }
            }
        }*/

        final String etstatus_array[] = {"Filled ", "Empty"};
        ArrayAdapter<String> etstatusada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etstatus_array);
        etstatusSpinner.setAdapter(etstatusada);
        if (status != null && !status.equals("")) {
            for (int i = 0; i < etstatus_array.length; i++) {
                if (status.equalsIgnoreCase(etstatus_array[i])) {
                    etstatusSpinner.setSelection(i);
                } else {
                    etstatusSpinner.setSelection(0);
                }
            }


        }

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dateLayout) {
            setFilledDate();
        }
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();

            }

        }
    }


    // ----validation -----
    private boolean isValidate() {
        firedetectSpineer = etfiredetectSpineer.getSelectedItem().toString();
        extinguiserSpineer = etextinguiserSpineer.getSelectedItem().toString();

        strfiredetecttypeSpineer = firedetecttypeSpineer.getSelectedItem().toString();
        strextinguisertypeSpineer = extinguisertypeSpineer.getSelectedItem().toString();

        status = etstatusSpinner.getSelectedItem().toString();
        make = getTextFromView(this.etMake);
        capacity = getTextFromView(this.etCapacity);
        stretRefillingStatusSpinner = etRefillingStatusSpinner.getSelectedItem().toString();
        if (isEmptyStr(firedetectSpineer)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Fire Detect System");
            return false;
        } else if (isEmptyStr(extinguiserSpineer)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Fire Extinguiser ");
            return false;
        } else if (isEmptyStr(make)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Make ");
            return false;
        } else if (isEmptyStr(capacity)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter capacity ");
            return false;
        } else if (isEmptyStr(stretRefillingStatusSpinner)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Refilling Status ");
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
            getMakeAndEqupmentId();
            try {
                jsonObject.put("Site_ID", strSiteId);
                jsonObject.put("User_ID", strUserId);
                jsonObject.put("Activity", "Fire");
                JSONObject FireSysData = new JSONObject();
                FireSysData.put("FireDetectionSystem", firedetectSpineer);
                FireSysData.put("FireDetectionSystemtype", strfiredetecttypeSpineer);
                FireSysData.put("Fireextinguiser", extinguiserSpineer);
                FireSysData.put("Fireextinguisertype", strextinguisertypeSpineer);
                FireSysData.put("FireextinguiserMake", make);
                FireSysData.put("FireextinguiserCapacity", capacity);
                FireSysData.put("Fireextinguiserfilledstatus", status);
                FireSysData.put("CapacityID", capcityId);
                FireSysData.put("Equipment", "Fire");
                FireSysData.put("MakeID", strMakeId);
                FireSysData.put("stretRefillingStatusSpinner", stretRefillingStatusSpinner);
                FireSysData.put("RefillingFilledDate", datemilisec);

                jsonObject.put("FireSysData", FireSysData);
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
                            ASTUIUtil.showToast("Your Fire System  Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Your Fire System  Data has not been updated!");
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


    public void setFilledDate() {
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
                FilledDate.setText(sdf.format(myCalendar.getTime()));
                datemilisec = myCalendar.getTimeInMillis();
            }
        };
        FilledDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

}
