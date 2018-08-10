package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.framework.IAsyncWorkCompletedCallback;
import com.telecom.ast.sitesurvey.framework.ServiceCaller;
import com.telecom.ast.sitesurvey.model.CircleMasterDataModel;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.model.DistrictMasterDataModel;
import com.telecom.ast.sitesurvey.model.SSAmasterDataModel;
import com.telecom.ast.sitesurvey.model.SiteMasterDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class BasicDataFragment extends MainFragment {
    AppCompatEditText etDate, etTime, etSurveyorName, etAddress, etPincode, etCity,
            etOwner, etOwnerContact, etCaretaker, etCaretakercontact, etownerAddress;
    Spinner spDistrict, spCircle, spSSA;
    AutoCompleteTextView etSiteId, etSiteName;

    Button btnSubmit;
    AtmDatabase atmDatabase;
    ASTUIUtil commonFunctions;
    ArrayList<SiteMasterDataModel> arrSiteData;
    ArrayList<CircleMasterDataModel> arrCircleData = new ArrayList<>();
    ArrayList<DistrictMasterDataModel> arrDistrictData = new ArrayList<>();
    ArrayList<SSAmasterDataModel> arrSSAData = new ArrayList<>();
    SharedPreferences basicSharedPref;
    SharedPreferences userPref;
    String userId, strDate, strTime, strSurveyyorName, strSiteId, strAddress, strMilli;
    String strCircleId, strSSAId, strDistrictId, strCity, strPincode, strSiteCustomerId, strSiteName;
    String strCirclePosition, strSSAPosition, strDistrictPosition,
            strOwner, strOwnerContact, strCaretaker, strCaretakercontact, strownerAddress;
    String SSAId, DistrictId, CirclePosition, SSAPosition, DistrictPosition;
    long currentMilli;
    String dateTime, finalDate, finalTime, finalSurveyorName, curtomerSiteIdStr, siteIdStr, finalSiteName, finalAddress,
            finalCity, finalPincode, Owner, OwnerContact, Caretaker, Caretakercontact, ownerAddress;

    int finalCircle, finalSSA, finalDistrict;
    private String[] arrCustomerSiteId;
    private long dateTimeMili;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_basic_data;
    }

    @Override
    protected void loadView() {
        this.etDate = findViewById(R.id.etDate);
        this.etTime = findViewById(R.id.etTime);
        this.etSurveyorName = findViewById(R.id.etSurveyorName);
        this.etAddress = findViewById(R.id.etAddress);
        this.etPincode = findViewById(R.id.etPincode);
        this.etSiteId = findViewById(R.id.etSiteId);
        this.etSiteName = findViewById(R.id.etSiteName);
        this.spCircle = findViewById(R.id.spCircle);
        this.spSSA = findViewById(R.id.spSSA);
        this.spDistrict = findViewById(R.id.spDistrict);
        this.etCity = findViewById(R.id.etCity);
        this.btnSubmit = findViewById(R.id.btnSubmit);
        etOwner = findViewById(R.id.etOwner);
        etOwnerContact = findViewById(R.id.etOwnerContact);
        etCaretaker = findViewById(R.id.etCaretaker);
        etCaretakercontact = findViewById(R.id.etCaretakercontact);
        etownerAddress = findViewById(R.id.etownerAddress);
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
        getUserPref();
        getSaveBasicDataSharedPref();
        getBasicData();
    }


    public void getBasicData() {
        setBasiMasterData();
        getCircleData();
        getPreFilledFields();
    }


    public void setBasiMasterData() {
        currentMilli = System.currentTimeMillis();
        String currentDate = commonFunctions.getFormattedDate("dd/MM/yyyy", System.currentTimeMillis());
        dateTimeMili = System.currentTimeMillis();
        etDate.setText(currentDate);
        etDate.setEnabled(false);
        final String currentTime = commonFunctions.getFormattedDate("hh:mm:ss", System.currentTimeMillis());
        etTime.setText(currentTime);
        etTime.setEnabled(false);
        atmDatabase = new AtmDatabase(getContext());
        arrSiteData = atmDatabase.getAllSiteData("Desc");
        String[] arrSiteName = new String[arrSiteData.size()];
        arrCustomerSiteId = new String[arrSiteData.size()];
        for (int i = 0; i < arrSiteData.size(); i++) {
            arrSiteName[i] = arrSiteData.get(i).getSiteName();
            arrCustomerSiteId[i] = arrSiteData.get(i).getCustomerSiteId();
        }
        setSiteNameAdapter(arrCustomerSiteId, arrSiteName);
        arrCircleData = atmDatabase.getAllCircleData("Desc");
        String[] arrCircle = new String[arrCircleData.size() + 1];
        arrCircle[0] = "--Select Circle--";
        for (int i = 1; i < arrCircleData.size() + 1; i++) {
            String currentCircleName = arrCircleData.get(i - 1).getCircleName().toString();
            if (currentCircleName != null || currentCircleName != "null") {
                arrCircle[i] = currentCircleName;
            } else {
                arrCircle[i] = "Not Available";
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrCircle);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCircle.setAdapter(dataAdapter);
        final String[] arrSSA = new String[arrSSAData.size() + 1];
        arrSSA[0] = "--Select SSA--";
        setSSAadapter(arrSSA, 0);
        spSSA.setEnabled(false);
        final String[] arrDistrict = new String[arrDistrictData.size() + 1];
        arrDistrict[0] = "---Select District-";
        ArrayAdapter<String> dataAdapterDistrict = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrSSA);
        dataAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistrict.setAdapter(dataAdapterDistrict);
        spDistrict.setEnabled(false);
        spCircle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spCircle.getSelectedItemPosition() != 0) {
                    String circleId = arrCircleData.get(spCircle.getSelectedItemPosition() - 1).getCircleId();
                    arrSSAData = atmDatabase.getAllSSAData("Desc", circleId);
                    String[] arrTempSSA = new String[arrSSAData.size() + 1];
                    arrTempSSA[0] = "---Select SSA-";
                    for (int i = 0; i < arrSSAData.size(); i++) {
                        arrTempSSA[i + 1] = arrSSAData.get(i).getSSAname();
                    }
                    setSSAadapter(arrTempSSA, 0);
                    spSSA.setEnabled(true);
                    if (!strSSAPosition.equals("")) {
                        spSSA.setSelection(Integer.parseInt(strSSAPosition));
                    }
                    arrDistrictData = atmDatabase.getAllDistrictData("Desc", circleId);
                    String[] arrTempDistrict = new String[arrDistrictData.size() + 1];
                    arrTempDistrict[0] = "--Select District--";
                    for (int i = 0; i < arrDistrictData.size(); i++) {
                        arrTempDistrict[i + 1] = arrDistrictData.get(i).getDistrictName();
                    }
                    setDistrictadapter(arrTempDistrict, 0);
                    spDistrict.setEnabled(true);
                    if (!strDistrictPosition.equals("")) {
                        spDistrict.setSelection(Integer.parseInt(strDistrictPosition));
                    }
                } else {
                    spSSA.setSelection(0);
                    spDistrict.setSelection(0);
                    spSSA.setEnabled(false);
                    spDistrict.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getCircleData() {
        if (!strCircleId.equals("")) {
            if (!strCirclePosition.equals("")) {
                String circleId = arrCircleData.get(Integer.parseInt(strCirclePosition) - 1).getCircleId();
                arrSSAData = atmDatabase.getAllSSAData("Desc", circleId);
                String[] arrTempSSA = new String[arrSSAData.size() + 1];
                arrTempSSA[0] = "---Select SSA-";

                for (int i = 0; i < arrSSAData.size(); i++) {
                    arrTempSSA[i + 1] = arrSSAData.get(i).getSSAname();
                }
                setSSAadapter(arrTempSSA, Integer.parseInt(strSSAPosition));
                spSSA.setEnabled(true);
                //spSSA.setSelection(Integer.parseInt(strSSAPosition));
                arrDistrictData = atmDatabase.getAllDistrictData("Desc", circleId);
                String[] arrTempDistrict = new String[arrDistrictData.size() + 1];
                arrTempDistrict[0] = "--Select District--";
                for (int i = 0; i < arrDistrictData.size(); i++) {
                    arrTempDistrict[i + 1] = arrDistrictData.get(i).getDistrictName();
                }
                setDistrictadapter(arrTempDistrict, Integer.parseInt(strDistrictPosition));
                spDistrict.setEnabled(true);
                //spDistrict.setSelection(Integer.parseInt(strDistrictPosition));
            }
        }
    }

    //-----------------Populating Pre Filled Fields---------------------
    public void getPreFilledFields() {
        if (!strDate.equals("")) {
            etDate.setText(strDate);
            etTime.setText(strTime);
            etSurveyorName.setText(strSurveyyorName);
            etSiteId.setText(strSiteCustomerId);
            etSiteName.setText(strSiteName);
            etAddress.setText(strAddress);
            spCircle.setSelection(Integer.parseInt(strCirclePosition));
            spSSA.setSelection(Integer.parseInt(strSSAPosition));
            spDistrict.setSelection(Integer.parseInt(strDistrictPosition));
            etCity.setText(strCity);
            etPincode.setText(strPincode);
            etOwner.setText(strOwner);
            etOwnerContact.setText(strOwnerContact);
            etCaretaker.setText(strCaretaker);
            etCaretakercontact.setText(strCaretakercontact);
            etownerAddress.setText(strownerAddress);

        }
    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        userId = userPref.getString("USER_ID", "");
      //  strSiteId = userPref.getString("strSiteId", "");
    }    /*
     *
     * get Data in Shared Pref.
     */


    public void getSaveBasicDataSharedPref() {
      /*  commonFunctions = new ASTUIUtil();
        basicSharedPref = getContext().getSharedPreferences("BasicSharedPref", MODE_PRIVATE);
        strMilli = basicSharedPref.getString("MilliSeconds", "");
        strDate = basicSharedPref.getString("Date", "");
        strTime = basicSharedPref.getString("Time", "");
        strSurveyyorName = basicSharedPref.getString("SurveyorName", "");
        strSiteId = basicSharedPref.getString("SiteId", "");
        strSiteCustomerId = basicSharedPref.getString("SiteCustomerId", "");
        strSiteName = basicSharedPref.getString("SiteName", "");
        strAddress = basicSharedPref.getString("Address", "");
        strCircleId = basicSharedPref.getString("CircleId", "");
        strSSAId = basicSharedPref.getString("SSAId", "");
        strDistrictId = basicSharedPref.getString("DistrictId", "");
        strCirclePosition = basicSharedPref.getString("CirclePosition", "");
        strSSAPosition = basicSharedPref.getString("SSAPosition", "");
        strDistrictPosition = basicSharedPref.getString("DistrictPosition", "");
        strCity = basicSharedPref.getString("City", "");
        strPincode = basicSharedPref.getString("Pincode", "");
        strOwner = basicSharedPref.getString("strOwner", "");
        strOwnerContact = basicSharedPref.getString("strOwnerContact", "");
        strCaretaker = basicSharedPref.getString("strCaretaker", "");
        strCaretakercontact = basicSharedPref.getString("strCaretakercontact", "");
        strownerAddress = basicSharedPref.getString("strownerAddress", "");*/

    }

    public void setSSAadapter(String[] arrSSA, int position) {
        final ArrayAdapter<String> dataAdapterSSA = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrSSA);
        dataAdapterSSA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSSA.setAdapter(dataAdapterSSA);
        spSSA.setSelection(position, true);
    }

    public void setDistrictadapter(String[] arrDistrict, int position) {
        final ArrayAdapter<String> dataAdapterDistrict = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrDistrict);
        dataAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDistrict.setAdapter(dataAdapterDistrict);
        spDistrict.setSelection(position, true);
    }

    private void setSiteNameAdapter(final String[] arrSiteId1, final String[] arrSiteName1) {
        ArrayAdapter<String> adapterSiteName = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrSiteName1);
        etSiteName.setAdapter(adapterSiteName);
        etSiteName.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String siteName = etSiteName.getText().toString();
                for (int i = 0; i <= arrSiteName1.length - 1; i++) {
                    if (siteName.equalsIgnoreCase(arrSiteName1[i])) {
                        etSiteId.setText(arrSiteId1[i]);
                    }
                }
            }
        });

        ArrayAdapter<String> adapterSiteId = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrSiteId1);
        etSiteId.setAdapter(adapterSiteId);
        etSiteId.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String siteId = etSiteId.getText().toString();
                for (int i = 0; i <= arrSiteId1.length - 1; i++) {
                    if (siteId.equalsIgnoreCase(arrSiteId1[i])) {
                        etSiteName.setText(arrSiteName1[i]);
                    }
                }
            }
        });

    }

    //get site id
    private void getSiteId() {
        for (int i = 0; i < arrCustomerSiteId.length; i++) {
            if (curtomerSiteIdStr.equalsIgnoreCase(arrCustomerSiteId[i])) {
                siteIdStr = arrSiteData.get(i).getSiteId();
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                getSiteId();
                saveBasicDataonServer();
            }


        }

    }

    @Override
    public boolean onBackPressed() {
        saveBasicDataintSharedPref();
        return super.onBackPressed();
    }

    private void saveBasicDataintSharedPref() {
       /* SharedPreferences.Editor editor = basicSharedPref.edit();
        editor.putString("Date", finalDate);
        editor.putString("Time", finalTime);
        editor.putString("SurveyorName", finalSurveyorName);
        editor.putString("SiteId", siteIdStr);
        editor.putString("SiteCustomerId", curtomerSiteIdStr);
        editor.putString("SiteName", finalSiteName);
        editor.putString("Address", finalAddress);
        editor.putString("CircleId", strCircleId);
        editor.putString("SSAId", SSAId);
        editor.putString("DistrictId", DistrictId);
        editor.putString("CirclePosition", CirclePosition);
        editor.putString("SSAPosition", SSAPosition);
        editor.putString("DistrictPosition", DistrictPosition);
        editor.putString("City", finalCity);
        editor.putString("Pincode", finalPincode);
        editor.putString("strOwner", Owner);
        editor.putString("strOwnerContact", OwnerContact);
        editor.putString("strCaretaker", Caretaker);
        editor.putString("strCaretakercontact", Caretakercontact);
        editor.putString("strownerAddress", ownerAddress);
        editor.putString("MilliSeconds", String.valueOf(currentMilli));
        editor.commit();*/
    }

    public boolean isValidate() {
        dateTime = String.valueOf(currentMilli);
        finalDate = getTextFromView(this.etDate);
        finalTime = getTextFromView(this.etTime);
        finalSurveyorName = getTextFromView(this.etSurveyorName);
        curtomerSiteIdStr = getTextFromView(this.etSiteId);
        finalSiteName = getTextFromView(this.etSiteName);
        finalAddress = getTextFromView(this.etAddress);
        finalCircle = spCircle.getSelectedItemPosition();
        finalSSA = spSSA.getSelectedItemPosition();
        finalDistrict = spDistrict.getSelectedItemPosition();
        finalCity = getTextFromView(this.etCity);
        finalPincode = getTextFromView(this.etPincode);
        Owner = getTextFromView(this.etOwner);
        OwnerContact = getTextFromView(this.etOwnerContact);
        Caretaker = getTextFromView(this.etCaretaker);
        Caretakercontact = getTextFromView(this.etCaretakercontact);
        ownerAddress = getTextFromView(this.etownerAddress);
        strCircleId = arrCircleData.get(spCircle.getSelectedItemPosition() - 1).getCircleId();
        SSAId = arrSSAData.get(spSSA.getSelectedItemPosition() - 1).getSSAid();
        DistrictId = arrDistrictData.get(spDistrict.getSelectedItemPosition() - 1).getDistrictId();
        CirclePosition = String.valueOf(spCircle.getSelectedItemPosition());
        SSAPosition = String.valueOf(spSSA.getSelectedItemPosition());
        DistrictPosition = String.valueOf(spDistrict.getSelectedItemPosition());


        if (isEmptyStr(finalSurveyorName)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter SurveyorName");
            return false;
        } else if (isEmptyStr(finalSiteName)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Site Name");
            return false;
        } else if (isEmptyStr(curtomerSiteIdStr)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Site ID");
            return false;
        } else if (isEmptyStr(finalAddress)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Address");
            return false;
        } else if (finalCircle <= 0) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Circle");
            return false;
        } else if (finalSSA <= 0) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select SSA");
            return false;
        } else if (finalDistrict <= 0) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select District");
            return false;
        } else if (isEmptyStr(finalCity)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter City");
            return false;
        } else if (isEmptyStr(finalPincode)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Pincode");
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
                jsonObject.put("Site_ID", siteIdStr);
                jsonObject.put("User_ID", userId);
                jsonObject.put("Activity", "Basic");
                JSONObject BasicData = new JSONObject();
                BasicData.put("SiteName", finalSiteName);
                BasicData.put("Address", finalAddress);
                BasicData.put("CircleID", strCircleId);
                BasicData.put("State", finalCircle);
                BasicData.put("SSAID", finalSSA);
                BasicData.put("DistrictID", finalDistrict);
                BasicData.put("City", finalCity);
                BasicData.put("Pincode", finalPincode);
                BasicData.put("DateTime", dateTimeMili);//finalDate
                BasicData.put("Surveyor", finalSurveyorName);
                BasicData.put("Owner", Owner);
                BasicData.put("OwnerContactNo", OwnerContact);
                BasicData.put("OwnerAddress", ownerAddress);
                BasicData.put("CareTaker", Caretaker);
                BasicData.put("CareTakerNo", Caretakercontact);
                BasicData.put("Latitude", "23.45678");
                BasicData.put("Longitude", "23.45678");

                jsonObject.put("BasicData", BasicData);

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
                            SharedPreferences.Editor editor = userPref.edit();
                            editor.putString("Site_ID", siteIdStr);
                            editor.commit();
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
