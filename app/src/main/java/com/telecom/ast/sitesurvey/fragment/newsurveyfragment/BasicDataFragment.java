package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class BasicDataFragment extends MainFragment {
    private AppCompatEditText etDate, etTime, etSurveyorName, etAddress, etPincode, etCity,
            etOwner, etOwnerContact, etCaretaker, etCaretakercontact, etownerAddress, etNearestPoliceAddress, etSPOfficeAddress;
    private Spinner spDistrict, spCircle, spSSA;
    private AutoCompleteTextView etSiteId, etSiteName;

    private Button btnSubmit;
    private AtmDatabase atmDatabase;
    private ASTUIUtil commonFunctions;
    private ArrayList<SiteMasterDataModel> arrSiteData;
    private ArrayList<CircleMasterDataModel> arrCircleData = new ArrayList<>();
    private ArrayList<DistrictMasterDataModel> arrDistrictData = new ArrayList<>();
    private ArrayList<SSAmasterDataModel> arrSSAData = new ArrayList<>();
    private SharedPreferences basicSharedPref;
    private SharedPreferences userPref;

    private String userId;
    private String strCircleId;
    private String strCirclePosition, strSSAPosition, strDistrictPosition;
    private String SSAId, DistrictId, CirclePosition, SSAPosition, DistrictPosition;
    private long currentMilli;
    private String dateTime, finalDate, finalTime, finalSurveyorName, curtomerSiteIdStr, siteIdStr, finalSiteName, finalAddress,
            finalCity, finalPincode, Owner, OwnerContact, Caretaker, Caretakercontact, ownerAddress, stretNearestPoliceAddress, stretSPOfficeAddress;

    private int finalCircle, finalSSA, finalDistrict;
    private String[] arrCustomerSiteId;
    private long dateTimeMili;

    private String currentLongitude = "";
    private String currentLatitude = "";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

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
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        //startLocationUpdates();
        etNearestPoliceAddress = findViewById(R.id.etNearestPoliceAddress);
        etSPOfficeAddress = findViewById(R.id.etSPOfficeAddress);
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
    }


    public void setBasiMasterData() {
        commonFunctions = new ASTUIUtil();
        currentMilli = System.currentTimeMillis();
        String currentDate = commonFunctions.getFormattedDate("dd/MM/yyyy", System.currentTimeMillis());
        dateTimeMili = System.currentTimeMillis();
        if (dateTimeMili != 0) {
            etDate.setText(currentDate);
        }
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
                    if (!isEmptyStr(strSSAPosition)) {
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
                    if (!isEmptyStr(strDistrictPosition)) {
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
        if (!isEmptyStr(strCircleId)) {
            if (!isEmptyStr(strCirclePosition)) {
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


    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        userId = userPref.getString("USER_ID", "");
        //  strSiteId = userPref.getString("strSiteId", "");
    }    /*
     *
     * get Data in Shared Pref.
     */


    public void getSaveBasicDataSharedPref() {
        basicSharedPref = getContext().getSharedPreferences("BasicSharedPref", MODE_PRIVATE);
        strCirclePosition = basicSharedPref.getString("CirclePosition", "");
        strSSAPosition = basicSharedPref.getString("SSAPosition", "");
        strDistrictPosition = basicSharedPref.getString("DistrictPosition", "");
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
                if (OwnerContact.length() > 0) {
                    if (OwnerContact.length() != 10) {
                        ASTUIUtil.shownewErrorIndicator(getContext(), "Please enter valid Owner phone no!");
                    } else {
                        saveBasicDataonServer();
                    }
                } else {
                    saveBasicDataonServer();
                }
            }


        }

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
        stretNearestPoliceAddress = getTextFromView(this.etNearestPoliceAddress);
        stretSPOfficeAddress = getTextFromView(this.etSPOfficeAddress);
        if (spCircle.getSelectedItemPosition() > 0) {
            strCircleId = arrCircleData.get(spCircle.getSelectedItemPosition() - 1).getCircleId();
            CirclePosition = String.valueOf(spCircle.getSelectedItemPosition());
        }
        if (spSSA.getSelectedItemPosition() > 0) {
            SSAId = arrSSAData.get(spSSA.getSelectedItemPosition() - 1).getSSAid();
            SSAPosition = String.valueOf(spSSA.getSelectedItemPosition());
        }
        if (spDistrict.getSelectedItemPosition() > 0) {
            DistrictId = arrDistrictData.get(spDistrict.getSelectedItemPosition() - 1).getDistrictId();
            DistrictPosition = String.valueOf(spDistrict.getSelectedItemPosition());
        }
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
        } else if (isEmptyStr(stretNearestPoliceAddress)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Nearest Police Station Address");
            return false;
        } else if (isEmptyStr(stretSPOfficeAddress)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter SP Office Address");
            return false;
        }

        return true;
    }


    public void saveBasicDataonServer() {
        boolean phhoneval = true;
        if (Caretakercontact.length() > 0) {
            if (Caretakercontact.length() != 10) {
                phhoneval = false;
            } else {
                phhoneval = true;
            }
        } else {
            phhoneval = true;
        }
        if (phhoneval) {
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
                    BasicData.put("Latitude", currentLatitude);
                    BasicData.put("Longitude", currentLongitude);
                    BasicData.put("PoliceStationAddress", stretNearestPoliceAddress);
                    BasicData.put("SPOfficeAddress", stretSPOfficeAddress);
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
                                editor.putString("CurtomerSite_Id", curtomerSiteIdStr);
                                editor.commit();


                                reloadBackScreen();
                            } else {
                                ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
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
        } else {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please enter valid Caretaker phone no!");
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

    // Trigger new location updates at interval
    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(getContext());
        settingsClient.checkLocationSettings(locationSettingsRequest);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
               /* for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                }*/
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
       /* mFusedLocationClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());*/
    }

    //get location change
    public void onLocationChanged(Location location) {
        // New location has now been determined
        currentLongitude = Double.toString(location.getLongitude());
        currentLatitude = Double.toString(location.getLatitude());

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Log.d(Contants.LOG_TAG, "Current Location*******" + msg);

      /*  Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
         LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());*/
    }

    //get last location
    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // GPS location can be null if GPS is switched off
                if (location != null) {
                    onLocationChanged(location);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Contants.LOG_TAG, "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFusedLocationClient != null) {
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    //stop location update
    private void stopLocationUpdates() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }


}
