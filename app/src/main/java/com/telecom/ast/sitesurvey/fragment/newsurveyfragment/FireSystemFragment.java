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
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class FireSystemFragment extends MainFragment {

    Spinner etfiredetectSpineer, etextinguiserSpineer;
    Spinner  etstatusSpinner;
    AutoCompleteTextView etMake, etCapacity;
    String strfiredetectSpineer, status, strextinguiserSpineer, stritemStatusSpineer, firedetectSpineer, extinguiserSpineer, itemStatus, make, capacity;
    LinearLayout fillEmptyLayout;
    Button btnSubmit;
    String strUserId, strSiteId;
    SharedPreferences FireSystemSharedPref, userPref;

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
                jsonObject.put("Activity", "Fire");
                JSONObject FireSysData = new JSONObject();
                FireSysData.put("FireDetectionSystem", firedetectSpineer);
                FireSysData.put("Fireextinguiser", extinguiserSpineer);
                FireSysData.put("FireextinguiserMake", make);
                FireSysData.put("FireextinguiserCapacity", capacity);
                FireSysData.put("Fireextinguiserfilledstatus", status);
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
}
