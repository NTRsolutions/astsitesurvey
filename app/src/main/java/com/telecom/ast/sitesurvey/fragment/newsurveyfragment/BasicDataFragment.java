package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.model.CircleMasterDataModel;
import com.telecom.ast.sitesurvey.model.DistrictMasterDataModel;
import com.telecom.ast.sitesurvey.model.SSAmasterDataModel;
import com.telecom.ast.sitesurvey.model.SiteMasterDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import java.util.ArrayList;

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
    SharedPreferences pref;
    String userId, strDate, strTime, strSurveyyorName, strSiteId, strAddress, strMilli;
    String strCircleId, strSSAId, strDistrictId, strCity, strPincode, strSiteCustomerId, strSiteName;
    String strCirclePosition, strSSAPosition, strDistrictPosition,
            strOwner, strOwnerContact, strCaretaker, strCaretakercontact, strownerAddress;
    long currentMilli;
    String dateTime, finalDate, finalTime, finalSurveyorName, finalSiteId, finalSiteName, finalAddress,
            finalCity, finalPincode, Owner, OwnerContact, Caretaker, Caretakercontact, ownerAddress;
    ;

    int finalCircle, finalSSA, finalDistrict;

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
        getSharedPrefSaveData();
        currentMilli = System.currentTimeMillis();
        String currentDate = commonFunctions.getFormattedDate("dd/MM/yyyy", System.currentTimeMillis());
        etDate.setText(currentDate);
        etDate.setEnabled(false);
        final String currentTime = commonFunctions.getFormattedDate("hh:mm:ss", System.currentTimeMillis());
        etTime.setText(currentTime);
        etTime.setEnabled(false);
        atmDatabase = new AtmDatabase(getContext());
        arrSiteData = atmDatabase.getAllSiteData("Desc");
        String[] arrSiteName = new String[arrSiteData.size()];
        String[] arrSiteId = new String[arrSiteData.size()];
        for (int i = 0; i < arrSiteData.size(); i++) {
            arrSiteId[i] = arrSiteData.get(i).getCustomerSiteId();
            arrSiteName[i] = arrSiteData.get(i).getSiteName();
        }
        setSiteNameAdapter(arrSiteId, arrSiteName);
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


        if (!strCircleId.equals("")) {
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
        //-----------------Populating Pre Filled Fields---------------------
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

    /*
     *
     * get Data in Shared Pref.
     */

    public void getSharedPrefSaveData() {
        commonFunctions = new ASTUIUtil();
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        userId = pref.getString("USER_ID", "");
        strMilli = pref.getString("MilliSeconds", "");
        strDate = pref.getString("Date", "");
        strTime = pref.getString("Time", "");
        strSurveyyorName = pref.getString("SurveyorName", "");
        strSiteId = pref.getString("SiteId", "");
        strSiteCustomerId = pref.getString("SiteCustomerId", "");
        strSiteName = pref.getString("SiteName", "");
        strAddress = pref.getString("Address", "");
        strCircleId = pref.getString("CircleId", "");
        strSSAId = pref.getString("SSAId", "");
        strDistrictId = pref.getString("DistrictId", "");
        strCirclePosition = pref.getString("CirclePosition", "");
        strSSAPosition = pref.getString("SSAPosition", "");
        strDistrictPosition = pref.getString("DistrictPosition", "");
        strCity = pref.getString("City", "");
        strPincode = pref.getString("Pincode", "");
        strOwner = pref.getString("strOwner", "");
        strOwnerContact = pref.getString("strOwnerContact", "");
        strCaretaker = pref.getString("strCaretaker", "");
        strCaretakercontact = pref.getString("strCaretakercontact", "");
        strownerAddress = pref.getString("strownerAddress", "");

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


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("USER_ID", userId);
                editor.putString("Date", finalDate);
                editor.putString("Time", finalTime);
                editor.putString("SurveyorName", finalSurveyorName);
                editor.putString("SiteId", finalSiteId);
                editor.putString("SiteCustomerId", etSiteId.getText().toString());
                editor.putString("SiteName", etSiteName.getText().toString());
                editor.putString("Address", finalAddress);
                editor.putString("CircleId", arrCircleData.get(spCircle.getSelectedItemPosition() - 1).getCircleId());
                editor.putString("SSAId", arrSSAData.get(spSSA.getSelectedItemPosition() - 1).getSSAid());
                editor.putString("DistrictId", arrDistrictData.get(spDistrict.getSelectedItemPosition() - 1).getDistrictId());
                editor.putString("CirclePosition", String.valueOf(spCircle.getSelectedItemPosition()));
                editor.putString("SSAPosition", String.valueOf(spSSA.getSelectedItemPosition()));
                editor.putString("DistrictPosition", String.valueOf(spDistrict.getSelectedItemPosition()));
                editor.putString("City", finalCity);
                editor.putString("Pincode", finalPincode);
                editor.putString("strOwner", Owner);
                editor.putString("strOwnerContact", OwnerContact);
                editor.putString("strCaretaker", Caretaker);
                editor.putString("strCaretakercontact", Caretakercontact);
                editor.putString("strownerAddress", ownerAddress);


                editor.putString("MilliSeconds", String.valueOf(currentMilli));
                editor.commit();
                strCirclePosition = pref.getString("CirclePosition", "");
                strSSAPosition = pref.getString("SSAPosition", "");
                strDistrictPosition = pref.getString("DistrictPosition", "");
                reloadBackScreen();

            }
        }
    }


    public boolean isValidate() {
        dateTime = String.valueOf(currentMilli);
        finalDate = getTextFromView(this.etDate);
        finalTime = getTextFromView(this.etTime);
        finalSurveyorName = getTextFromView(this.etSurveyorName);
        finalSiteId = getTextFromView(this.etSiteId);
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


        if (isEmptyStr(finalSurveyorName)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter SurveyorName");
            return false;
        } else if (isEmptyStr(finalSiteId)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Site");
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
}
