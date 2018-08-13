package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class MiscElectricalEquiFragment extends MainFragment {

    Spinner spinnerElectrical, spinnerAviationLamp, LightningSpinner, TubeLightSpinner;
    TextInputEditText etEarthingvalue, etServoStabiliser;
    Button btnSubmit;
    String strEarthingvalue, Earthingvalue, strspinnerElectrical, strspinnerAviationLamp, strLightningSpinner, TubeLight, ServoStabiliser;

    String strUserId, strSiteId;
    SharedPreferences MiscElect, userPref;

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
        etServoStabiliser = this.findViewById(R.id.etServoStabiliser);
        TubeLightSpinner = this.findViewById(R.id.TubeLightSpinner);
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
        setSpinnerValue();
        getUserPref();
        getSharedprefData();
        if (!isEmptyStr(Earthingvalue) || !isEmptyStr(ServoStabiliser)) {
            etEarthingvalue.setText(Earthingvalue);
            etServoStabiliser.setText(ServoStabiliser);
            strspinnerElectrical = spinnerElectrical.getSelectedItem().toString();
            strspinnerAviationLamp = spinnerAviationLamp.getSelectedItem().toString();
            strLightningSpinner = LightningSpinner.getSelectedItem().toString();
            TubeLight = TubeLightSpinner.getSelectedItem().toString();
            ServoStabiliser = getTextFromView(this.etServoStabiliser);
        }
    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
    }
    /*
     *
     *     Shared Prefrences
     */
    public void getSharedprefData() {
     /*   MiscElect = getContext().getSharedPreferences("MiscElect", MODE_PRIVATE);
        Earthingvalue = MiscElect.getString("MISC_strbtsOperator", "");
        strspinnerElectrical = MiscElect.getString("MISC_strspinnerElectrical", "");
        strspinnerAviationLamp = MiscElect.getString("MISC_strspinnerAviationLamp", "");
        strLightningSpinner = MiscElect.getString("MISC_strLightningSpinner", "");
        ServoStabiliser = MiscElect.getString("ServoStabiliser", "");
        TubeLight = MiscElect.getString("TubeLight", "");*/
    }

    public void setSpinnerValue() {
        final String etfiredetectSpineer_array[] = {"Available", "Not Available"};
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


        final String TubeLightSpinnerArray[] = {"Available", "Not Available"};
        ArrayAdapter<String> TubeLightSpinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, TubeLightSpinnerArray);
        TubeLightSpinner.setAdapter(TubeLightSpinnerAdapter);
        if (!isEmptyStr(TubeLight)) {
            for (int i = 0; i < TubeLightSpinnerArray.length; i++) {
                if (TubeLight.equalsIgnoreCase(TubeLightSpinnerArray[i])) {
                    TubeLightSpinner.setSelection(i);
                } else {
                    TubeLightSpinner.setSelection(0);
                }
            }
        }


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
            /*    SharedPreferences.Editor editor = MiscElect.edit();
                editor.putString("MISC_strbtsOperator", strEarthingvalue);
                editor.putString("MISC_strspinnerElectrical", strspinnerElectrical);
                editor.putString("MISC_strspinnerAviationLamp", strspinnerAviationLamp);
                editor.putString("MISC_strLightningSpinner", strLightningSpinner);

                editor.putString("ServoStabiliser", ServoStabiliser);
                editor.putString("TubeLight", TubeLight);
                editor.commit();*/

                saveBasicDataonServer();
            }

        }
    }

    // ----validation -----
    private boolean isValidate() {
        strEarthingvalue = getTextFromView(this.etEarthingvalue);
        strspinnerElectrical = spinnerElectrical.getSelectedItem().toString();
        strspinnerAviationLamp = spinnerAviationLamp.getSelectedItem().toString();
        strLightningSpinner = LightningSpinner.getSelectedItem().toString();
        TubeLight = TubeLightSpinner.getSelectedItem().toString();
        ServoStabiliser = getTextFromView(this.etServoStabiliser);
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
                MiscEEqpData.put("AviationLamp", strspinnerAviationLamp);
                MiscEEqpData.put("EarthingResistanceValue", strEarthingvalue);
                MiscEEqpData.put("ElectricalConnectionfittings", strspinnerElectrical);
                MiscEEqpData.put("LightningArrester", strLightningSpinner);
                MiscEEqpData.put("TubeLight", TubeLight);
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
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
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
