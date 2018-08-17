package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getSharedprefData();
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

                } else {
                    fillEmptyLayout.setVisibility(View.GONE);
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
/*        FireSystemSharedPref = getContext().getSharedPreferences("FireSystemSharedPref", MODE_PRIVATE);
        strfiredetectSpineer = FireSystemSharedPref.getString("Fire_strfiredetectSpineer", "");
        strextinguiserSpineer = FireSystemSharedPref.getString("Fire_strextinguiserSpineer", "");
        stritemStatusSpineer = FireSystemSharedPref.getString("Fire_stritemStatusSpineer", "");
        status = FireSystemSharedPref.getString("FIRE_status", "");
        make = FireSystemSharedPref.getString("FIRE_make", "");
        capacity = FireSystemSharedPref.getString("FIRE_capacity", "");*/

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
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();
              /*  SharedPreferences.Editor editor = FireSystemSharedPref.edit();
                editor.putString("FIRE_strfiredetectSpineer", firedetectSpineer);
                editor.putString("FIRE_strextinguiserSpineer", firedetectSpineer);
                editor.putString("FIRE_stritemStatusSpineer", itemStatus);
                editor.putString("FIRE_status", status);
                editor.putString("FIRE_make", make);
                editor.putString("FIRE_capacity", capacity);
                editor.commit();*/


            }

        }
    }


    // ----validation -----
    private boolean isValidate() {
        firedetectSpineer = etfiredetectSpineer.getSelectedItem().toString();
        extinguiserSpineer = etextinguiserSpineer.getSelectedItem().toString();
        status = etstatusSpinner.getSelectedItem().toString();
        make = getTextFromView(this.etMake);
        capacity = getTextFromView(this.etCapacity);
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
                FireSysData.put("Fireextinguiser", extinguiserSpineer);
                FireSysData.put("FireextinguiserMake", make);
                FireSysData.put("FireextinguiserCapacity", capacity);
                FireSysData.put("Fireextinguiserfilledstatus", status);
                FireSysData.put("Capacity_ID", capcityId);
                FireSysData.put("Equipment", "Fire");
                FireSysData.put("MakeID", strMakeId);


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
      /*  if (equpImagList != null && equpImagList.size() > 0) {
            for (SaveOffLineData data : equpImagList) {
                if (data != null) {
                    if (data.getImagePath() != null) {
                        File inputFile = new File(data.getImagePath());
                        if (inputFile.exists()) {
                            multipartBody.addFormDataPart("PMInstalEqupImages", data.getImageName(), RequestBody.create(MEDIA_TYPE_PNG, inputFile));
                        }
                    }
                }
            }
        }
*/
        return multipartBody;
    }

    //get make and equpment id from  list
    private void getMakeAndEqupmentId() {
        for (EquipMakeDataModel dataModel : equipMakeList) {
            if (dataModel.getName().equals(make)) {
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

}
