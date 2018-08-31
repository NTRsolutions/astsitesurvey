package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
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
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class MiscElectricalEquiFragment extends MainFragment {

    private Spinner spinnerElectrical, spinnerAviationLamp, LightningSpinner, StabiliserTypeSpinner;
    private TextInputEditText etEarthingvalue;
    private Button btnSubmit;
    private String strEarthingvalue, Earthingvalue, strspinnerElectrical, strspinnerAviationLamp, strLightningSpinner, strStabiliserTypeSpinner, ServoStabiliser;
    private String strUserId, strSiteId;
    private SharedPreferences userPref;
    private LinearLayout ElectricaltypeLayout;
    private AutoCompleteTextView spinnerElectricaltype;
    private String strspinnerElectricalfaultytype, strstraviationLampworkingstatus, strLightningArresterstatus;
    private Spinner etServoStabiliserSpinner, StabiliserConditopnSpinner, aviationLampworkingstatus, LightningArresterstatus,
            numberofPhaseSpinner;
    private TextInputEditText etServoStabilisermake, etServoStabilisercapacity;
    private LinearLayout ServoStabiliserCondiLayout, LampstatusLayout, LightningArresterstatusLayout,
            ServoStabilisercapacityLayout, ServoStabiliserMakeLayout;
    private String strStabiliserConditopnSpinner, stretServoStabilisermake, stretServoStabilisercapacity, strnumberofPhaseSpinner;
    TextInputLayout spinnerElectricaltypeLayuot;

    @Override
    protected int fragmentLayout() {
        return R.layout.miscelectrequipment_fragment;
    }

    @Override
    protected void loadView() {
        spinnerElectrical = this.findViewById(R.id.spinnerElectrical);
        spinnerAviationLamp = this.findViewById(R.id.spinnerAviationLamp);
        LightningSpinner = this.findViewById(R.id.LightningSpinner);
        etEarthingvalue = this.findViewById(R.id.etEarthingvalue);
        btnSubmit = this.findViewById(R.id.btnSubmit);
        etServoStabiliserSpinner = this.findViewById(R.id.etServoStabiliserSpinner);
        StabiliserTypeSpinner = this.findViewById(R.id.StabiliserTypeSpinner);
        ElectricaltypeLayout = this.findViewById(R.id.ElectricaltypeLayout);
        spinnerElectricaltype = this.findViewById(R.id.spinnerElectricaltype);
        StabiliserConditopnSpinner = this.findViewById(R.id.StabiliserConditopnSpinner);
        ServoStabiliserCondiLayout = this.findViewById(R.id.ServoStabiliserCondiLayout);
        aviationLampworkingstatus = this.findViewById(R.id.aviationLampworkingstatus);
        LampstatusLayout = this.findViewById(R.id.LampstatusLayout);

        LightningArresterstatus = this.findViewById(R.id.LightningArresterstatus);
        LightningArresterstatusLayout = this.findViewById(R.id.LightningArresterstatusLayout);

        etServoStabilisermake = this.findViewById(R.id.etServoStabilisermake);
        etServoStabilisercapacity = this.findViewById(R.id.etServoStabilisercapacity);
        ServoStabilisercapacityLayout = this.findViewById(R.id.ServoStabilisercapacityLayout);
        ServoStabiliserMakeLayout = this.findViewById(R.id.ServoStabiliserMakeLayout);
        numberofPhaseSpinner = this.findViewById(R.id.numberofPhaseSpinner);
        spinnerElectricaltypeLayuot = this.findViewById(R.id.spinnerElectricaltypeLayuot);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
        this.spinnerElectricaltype.setOnClickListener(this);
        spinnerElectricaltype.setOnClickListener(this);
        spinnerElectricaltypeLayuot.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        setSpinnerValue();
        getUserPref();
        spinnerElectrical.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Not Working")) {
                    ElectricaltypeLayout.setVisibility(View.VISIBLE);

                } else {
                    ElectricaltypeLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etServoStabiliserSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Available")) {
                    ServoStabiliserCondiLayout.setVisibility(View.VISIBLE);
                    ServoStabilisercapacityLayout.setVisibility(View.VISIBLE);
                    ServoStabiliserMakeLayout.setVisibility(View.VISIBLE);

                } else {
                    ServoStabiliserCondiLayout.setVisibility(View.GONE);
                    ServoStabilisercapacityLayout.setVisibility(View.GONE);
                    ServoStabiliserMakeLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

      /*  spinnerElectricaltype.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                showSelectFaultyItemDialog();
            }
        });
*/

        spinnerAviationLamp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Available")) {
                    LampstatusLayout.setVisibility(View.VISIBLE);
                } else {
                    LampstatusLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        LightningSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Available")) {
                    LightningArresterstatusLayout.setVisibility(View.VISIBLE);
                } else {
                    LightningArresterstatusLayout.setVisibility(View.GONE);
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
        final String etfiredetectSpineer_array[] = {"Working", "Not Working"};
        ArrayAdapter<String> etfiredetect = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineer_array);
        spinnerElectrical.setAdapter(etfiredetect);
        if (!isEmptyStr(strEarthingvalue)) {
            for (int i = 0; i < etfiredetectSpineer_array.length; i++) {
                if (strEarthingvalue.equalsIgnoreCase(etfiredetectSpineer_array[i])) {
                    spinnerElectrical.setSelection(i);
                } else {
                    spinnerElectrical.setSelection(0);
                }
            }
        }

        final String etextinguiserArray[] = {"Available", "Not Available"};
        ArrayAdapter<String> etextinguiser = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etextinguiserArray);
        spinnerAviationLamp.setAdapter(etextinguiser);
        if (!isEmptyStr(strspinnerElectrical)) {
            for (int i = 0; i < etextinguiserArray.length; i++) {
                if (strspinnerElectrical.equalsIgnoreCase(etextinguiserArray[i])) {
                    spinnerAviationLamp.setSelection(i);
                } else {
                    spinnerAviationLamp.setSelection(0);
                }
            }
        }


        final String LightningArray[] = {"Available", "Not Available"};
        ArrayAdapter<String> Lightning = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, LightningArray);
        LightningSpinner.setAdapter(Lightning);
        if (!isEmptyStr(strspinnerAviationLamp)) {
            for (int i = 0; i < LightningArray.length; i++) {
                if (strspinnerAviationLamp.equalsIgnoreCase(LightningArray[i])) {
                    LightningSpinner.setSelection(i);
                } else {
                    LightningSpinner.setSelection(0);
                }
            }
        }


        final String TubeLightSpinnerArray[] = {"Dry", "Oil"};
        ArrayAdapter<String> TubeLightSpinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, TubeLightSpinnerArray);
        StabiliserTypeSpinner.setAdapter(TubeLightSpinnerAdapter);
        if (!isEmptyStr(strStabiliserTypeSpinner)) {
            for (int i = 0; i < TubeLightSpinnerArray.length; i++) {
                if (strStabiliserTypeSpinner.equalsIgnoreCase(TubeLightSpinnerArray[i])) {
                    StabiliserTypeSpinner.setSelection(i);
                } else {
                    StabiliserTypeSpinner.setSelection(0);
                }
            }
        }


        final String etServoStabiliserSpinnerArray[] = {"Available", "Not Available"};
        ArrayAdapter<String> etServoStabiliserSpinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etServoStabiliserSpinnerArray);
        etServoStabiliserSpinner.setAdapter(etServoStabiliserSpinnerArrayAdapter);


        final String eServoStabilisercondiArray[] = {"Working", "Faulty"};
        ArrayAdapter<String> eServoStabilisercondiArrayadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, eServoStabilisercondiArray);
        StabiliserConditopnSpinner.setAdapter(eServoStabilisercondiArrayadapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.spinnerElectricaltype || view.getId() == R.id.spinnerElectricaltypeLayuot) {
            showSelectFaultyItemDialog();
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();
            }

        }
    }

    // ----validation -----
    private boolean isValidate() {
        strEarthingvalue = getTextFromView(this.etEarthingvalue);
        strspinnerElectrical = spinnerElectrical.getSelectedItem().toString();
        strspinnerElectricalfaultytype = spinnerElectricaltype.getText().toString();
        strspinnerAviationLamp = spinnerAviationLamp.getSelectedItem().toString();
        strLightningSpinner = LightningSpinner.getSelectedItem().toString();
        strStabiliserTypeSpinner = StabiliserTypeSpinner.getSelectedItem().toString();
        ServoStabiliser = etServoStabiliserSpinner.getSelectedItem().toString();
        strStabiliserConditopnSpinner = StabiliserConditopnSpinner.getSelectedItem().toString();
        strstraviationLampworkingstatus = aviationLampworkingstatus.getSelectedItem().toString();
        strLightningArresterstatus = LightningArresterstatus.getSelectedItem().toString();

        stretServoStabilisermake = getTextFromView(this.etServoStabilisermake);
        stretServoStabilisercapacity = getTextFromView(this.etServoStabilisercapacity);
        strnumberofPhaseSpinner = numberofPhaseSpinner.getSelectedItem().toString();


        if (isEmptyStr(strEarthingvalue)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Earthing-Resistance Value");
            return false;
        } else if (isEmptyStr(strspinnerElectrical)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Item Condition");
            return false;
        } else if (isEmptyStr(strspinnerAviationLamp)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Lamp Item Condition");
            return false;
        } else if (isEmptyStr(strLightningSpinner)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Ligiting Item Condition");
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
                jsonObject.put("Activity", "MiscEEqp");
                JSONObject MiscEEqpData = new JSONObject();
                MiscEEqpData.put("ShelterLightandWiring", strspinnerElectrical);
                MiscEEqpData.put("ShelterLightandWiring_Detail", strspinnerElectricalfaultytype);
                if (strspinnerAviationLamp.equalsIgnoreCase("Available")) {
                    MiscEEqpData.put("AviationLamp", strstraviationLampworkingstatus);
                } else {
                    MiscEEqpData.put("AviationLamp", strspinnerAviationLamp);
                }
                MiscEEqpData.put("LightningArrester", strLightningSpinner);
                MiscEEqpData.put("ShelterLightandWiring_Detail", strLightningArresterstatus);
                MiscEEqpData.put("EarthingResistanceValue", strEarthingvalue);
                MiscEEqpData.put("ServoStabiliser", ServoStabiliser);
                MiscEEqpData.put("ServoStabilisercondition", strStabiliserConditopnSpinner);
                MiscEEqpData.put("ServoStabiliserMake", stretServoStabilisermake);
                MiscEEqpData.put("ServoStabiliserCapacity", stretServoStabilisercapacity);
                MiscEEqpData.put("ServoStabiliserType", strStabiliserTypeSpinner);
                MiscEEqpData.put("ServoStabiliserPhase", strnumberofPhaseSpinner);
                jsonObject.put("MiscEEqpData", MiscEEqpData);
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
                            ASTUIUtil.showToast("Your Misc Electrical Eqi  Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Your Misc Electrical Eqi  Data has not been updated!");
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

    protected CharSequence[] faulityItem = {"Tube lights", "2Power socket", "Shelter outside light", "Other"};
    protected ArrayList<CharSequence> selectedfaulityItem = new ArrayList<CharSequence>();

    protected void showSelectFaultyItemDialog() {
        boolean[] checkedItems = new boolean[faulityItem.length];
        int count = faulityItem.length;
        for (int i = 0; i < count; i++)
            checkedItems[i] = selectedfaulityItem.contains(faulityItem[i]);
        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked)
                    selectedfaulityItem.add(faulityItem[which]);
                else
                    selectedfaulityItem.remove(faulityItem[which]);
                onChangeSelectedItem();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Faulty Items");
        builder.setMultiChoiceItems(faulityItem, checkedItems, coloursDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    protected void onChangeSelectedItem() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence colour : selectedfaulityItem)
            stringBuilder.append(colour + ",");
        spinnerElectricaltype.setText(stringBuilder.toString());

    }

}
