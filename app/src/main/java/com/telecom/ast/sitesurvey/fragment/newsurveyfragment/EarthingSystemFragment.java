package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class EarthingSystemFragment extends MainFragment {
    AppCompatEditText etNoEarthPits, etVoltageEarth, etwireconnected;
    String NoEarthPits, Earthpitsconnected, interConEarthPits, VoltageEarth, dgwireconnected, ebwireconnected;
    Button btnSubmit;
    String strUserId, strSiteId;
    SharedPreferences userPref;

    Spinner dgnaturelConnectSpinner, ebnaturelConnectSpinner, InterEarthPitsSpinner, etEarthpitsconnectedSpinner;

    @Override
    protected int fragmentLayout() {
        return R.layout.earthingsystem_fragment;
    }

    @Override
    protected void loadView() {
        etNoEarthPits = this.findViewById(R.id.etNoEarthPits);
        etEarthpitsconnectedSpinner = this.findViewById(R.id.etEarthpitsconnectedSpinner);
        etVoltageEarth = this.findViewById(R.id.etVoltageEarth);
        btnSubmit = this.findViewById(R.id.btnSubmit);
        dgnaturelConnectSpinner = this.findViewById(R.id.dgnaturelConnectSpinner);
        ebnaturelConnectSpinner = this.findViewById(R.id.ebnaturelConnectSpinner);
        InterEarthPitsSpinner = this.findViewById(R.id.InterEarthPitsSpinner);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
    }

    @Override
    protected void dataToView() {
        getUserPref();
        setSpinnerValue();
    }

    public void setSpinnerValue() {
        final String etfiredetectSpineer_array[] = {"Connected", "Not Connected"};
        ArrayAdapter<String> etfiredetect = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineer_array);
        dgnaturelConnectSpinner.setAdapter(etfiredetect);


        final String etfiredetectSpineerarray[] = {"Connected", "Not Connected"};
        ArrayAdapter<String> etty = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineerarray);
        ebnaturelConnectSpinner.setAdapter(etty);


        final String InterEarthPitsSpinneraray[] = {"Connected", "Not Connected"};
        ArrayAdapter<String> InterEartharrat = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, InterEarthPitsSpinneraray);
        InterEarthPitsSpinner.setAdapter(InterEartharrat);


        final String etEarthpitsconnectedSpinnerarray[] = {"Connected", "Not Connected", "EGB Missing", "IGFB Missing"};
        ArrayAdapter<String> etEarthpitsconnectedSpinnerad = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etEarthpitsconnectedSpinnerarray);
        etEarthpitsconnectedSpinner.setAdapter(etEarthpitsconnectedSpinnerad);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();
            }

        }
    }

    public boolean isValidate() {
        NoEarthPits = etNoEarthPits.getText().toString();
        Earthpitsconnected = etEarthpitsconnectedSpinner.getSelectedItem().toString();
        interConEarthPits = InterEarthPitsSpinner.getSelectedItem().toString();
        VoltageEarth = etVoltageEarth.getText().toString();
        dgwireconnected = dgnaturelConnectSpinner.getSelectedItem().toString();
        ebwireconnected = ebnaturelConnectSpinner.getSelectedItem().toString();
        if (isEmptyStr(NoEarthPits)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of EarthPits");
            return false;
        } else if (isEmptyStr(Earthpitsconnected)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of Earthpits connected  EGB/IGB");
            return false;
        } else if (isEmptyStr(interConEarthPits)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Inter Connectivity of EarthPits");
            return false;
        } else if (isEmptyStr(VoltageEarth)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Voltage between Earth and Neutral");
            return false;
        } else if (isEmptyStr(dgwireconnected)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "DG neutral wire connected with earthing");
            return false;
        } else if (isEmptyStr(ebwireconnected)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "EB neutral wire connected with earthing");
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
                jsonObject.put("Activity", "Earthing");
                JSONObject EarthingData = new JSONObject();
                EarthingData.put("NoofEarthPits", NoEarthPits);
                EarthingData.put("NoofEarthpitsconnectedEGBIGB", Earthpitsconnected);
                EarthingData.put("InterConnectivityofEarthPits", interConEarthPits);
                EarthingData.put("DGEBneutralwireconnectedwithearthing", dgwireconnected);
                EarthingData.put("DGEBneutralwireconnectedwithearthing", ebwireconnected);
                EarthingData.put("VoltagebetweenEarthandNeutral", VoltageEarth);
                jsonObject.put("EarthingData", EarthingData);
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
                            ASTUIUtil.showToast("Your Earthing System  Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Your Earthing System  Data   has not been updated!");
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
