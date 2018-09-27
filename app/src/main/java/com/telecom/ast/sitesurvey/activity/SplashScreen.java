package com.telecom.ast.sitesurvey.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.AstAppUgradeDlgActivity;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.framework.IAsyncWorkCompletedCallback;
import com.telecom.ast.sitesurvey.framework.ServiceCaller;
import com.telecom.ast.sitesurvey.model.CircleMasterDataModel;
import com.telecom.ast.sitesurvey.model.DistrictMasterDataModel;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipDescriptionDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.model.EquipTypeDataModel;
import com.telecom.ast.sitesurvey.model.SSAmasterDataModel;
import com.telecom.ast.sitesurvey.model.SiteMasterDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.GCM_Registration;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;


/**
 * Created by Narayan Semwal on 12-09-2017.
 */

public class SplashScreen extends AppCompatActivity {
    private String gcmRegId;
    private SharedPreferences pref;
    private GCM_Registration gcmClass;
    private AtmDatabase atmDatabase;
    private ASTProgressBar progrssBar;
    private boolean CircleMasterFlage = false;
    private boolean EquipmentFlage = false;
    private boolean SiteMasterFlage = false;
    private String currentVersion, latestVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getCurrentVersion();
        ApplicationHelper.application().initIcons();
        this.atmDatabase = new AtmDatabase(SplashScreen.this);
        pref = getApplicationContext().getSharedPreferences("SharedPref", MODE_PRIVATE);

        progrssBar = new ASTProgressBar(SplashScreen.this);
        getAllData();

    }

    //get all master data
    public void getAllData() {
        if (ASTUIUtil.isOnline(SplashScreen.this)) {
            progrssBar.show();
            String serviceURLSite = Constant.BASE_URL + Constant.SITE_MASTER_URL;
            String serviceURLEquipment = Constant.BASE_URL + Constant.EQUIPMENT_MASTER_URL;
            String serviceURLCircle = Constant.BASE_URL + Constant.CIRCLE_MASTER_URL;
            getSiteMaster(serviceURLSite);
            getCircleMaster(serviceURLCircle);
            getEquipmentListMaster(serviceURLEquipment);
        } else {
            ASTUIUtil.showToast(Contants.OFFLINE_MESSAGE);
        }
    }

    /*
     *
     * get SiteMaster
     */

    public void getSiteMaster(String serviceURL) {
        ServiceCaller serviceCaller = new ServiceCaller(SplashScreen.this);
        serviceCaller.CallCommanServiceMethod(serviceURL, "getSiteMaster", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSiteMasterData(result);
                } else {
                    ASTUIUtil.showToast("Site Master Data Not Avilable");
                    SiteMasterFlage = true;
                    checkAllcallDone();
                }
            }

        });
    }

    /**
     * Parse and Save  SiteMaster Data
     */

    public void parseandsaveSiteMasterData(final String result) {
        if (result != null) {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    Boolean flag = false;
                    try {
                        atmDatabase.deleteAllRows("site");
                        JSONObject jsonRootObject = new JSONObject(result);
                        String jsonStatus = jsonRootObject.optString("Status").toString();
                        if (jsonStatus.equals("1")) {
                            JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
                            ArrayList<SiteMasterDataModel> arrSiteData = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                SiteMasterDataModel siteMasterDataModel = new SiteMasterDataModel();
                                JSONObject jsonObject = null;

                                jsonObject = jsonArray.getJSONObject(i);

                                String siteId = jsonObject.optString("SiteId").toString();
                                String siteName = jsonObject.optString("SiteName").toString();
                                String customerSiteId = jsonObject.optString("CustomerSiteId").toString();
                                String circleId = jsonObject.optString("CircleId").toString();
                                String circleName = jsonObject.optString("CircleName").toString();
                                String SSAName = jsonObject.optString("SSAName").toString();
                                String SSAId = jsonObject.optString("SSAId").toString();
                                siteMasterDataModel.setSiteId(siteId);
                                siteMasterDataModel.setSiteName(siteName);
                                siteMasterDataModel.setCustomerSiteId(customerSiteId);
                                siteMasterDataModel.setCircleId(circleId);
                                siteMasterDataModel.setCircleName(circleName);
                                siteMasterDataModel.setSsaId(SSAId);
                                siteMasterDataModel.setSsaName(SSAName);
                                arrSiteData.add(siteMasterDataModel);
                            }
                            atmDatabase.addSiteData(arrSiteData);
                            //arrSiteData = atmDatabase.getAllSiteData("Desc");
                            flag = true;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return flag;
                }

                @Override
                protected void onPostExecute(Boolean flag) {
                    super.onPostExecute(flag);
                    SiteMasterFlage = true;
                    checkAllcallDone();
                }
            }.execute();
        }

    }

    /**
     * get  EquipmentListMaster
     *
     * @param serviceURL
     */
    public void getEquipmentListMaster(String serviceURL) {
        ServiceCaller serviceCaller = new ServiceCaller(SplashScreen.this);
        serviceCaller.CallCommanServiceMethod(serviceURL, "getEquipmentListMaster", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveEquipmentListMaster(result);
                } else {
                    EquipmentFlage = true;
                    ASTUIUtil.showToast("EquipmentListMaster Data Not Avilable");
                    checkAllcallDone();
                }
            }

        });
    }

    /**
     * Parse and Save  EquipmentListMaster Data
     */

    public void parseandsaveEquipmentListMaster(final String result) {
        if (result != null) {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    Boolean flag = false;
                    try {
                        JSONObject jsonRootObject = new JSONObject(result);
                        String jsonStatus = jsonRootObject.optString("Status").toString();
                        if (jsonStatus.equals("1")) {
                            atmDatabase.deleteAllRows("equipment_type");
                            atmDatabase.deleteAllRows("equipment_make");
                            atmDatabase.deleteAllRows("equipment_capacity");
                            atmDatabase.deleteAllRows("equipment_description");
                            JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
                            ArrayList<EquipTypeDataModel> arrEquipTypeData = new ArrayList<>();
                            ArrayList<EquipMakeDataModel> arrEquipMakeData = new ArrayList<>();
                            ArrayList<EquipCapacityDataModel> arrEquipCapacityData = new ArrayList<>();
                            ArrayList<EquipDescriptionDataModel> arrEquipDescriptionData = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                EquipTypeDataModel equipTypeDataModel = new EquipTypeDataModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String typeId = jsonObject.optString("ID").toString();
                                String typeName = jsonObject.optString("Item").toString();
                                equipTypeDataModel.setId(typeId);
                                equipTypeDataModel.setName(typeName);
                                arrEquipTypeData.add(equipTypeDataModel);
                                JSONArray makeJsonArray = jsonObject.optJSONArray("Make");
                                for (int j = 0; j < makeJsonArray.length(); j++) {
                                    EquipMakeDataModel equipMakeDataModel = new EquipMakeDataModel();
                                    JSONObject jsonMakeObject = makeJsonArray.getJSONObject(j);
                                    String makeId = jsonMakeObject.optString("ID").toString();
                                    String makeName = jsonMakeObject.optString("Make").toString();
                                    equipMakeDataModel.setName(makeName);
                                    equipMakeDataModel.setId(makeId);
                                    equipMakeDataModel.setTypeId(typeName);
                                    arrEquipMakeData.add(equipMakeDataModel);
                                    JSONArray capacityJsonArray = jsonMakeObject.optJSONArray("Capacity");
                                    for (int k = 0; k < capacityJsonArray.length(); k++) {
                                        EquipCapacityDataModel equipCapacityDataModel = new EquipCapacityDataModel();
                                        JSONObject jsonCapacityObject = capacityJsonArray.getJSONObject(k);
                                        String capacityId = jsonCapacityObject.optString("ID").toString();
                                        String capacityName = jsonCapacityObject.optString("Capacity").toString();
                                        equipCapacityDataModel.setId(capacityId);
                                        equipCapacityDataModel.setName(capacityName);
                                        equipCapacityDataModel.setMakeId(makeName);
                                        arrEquipCapacityData.add(equipCapacityDataModel);
                                        JSONArray descriptionJsonArray = jsonCapacityObject.optJSONArray("Description");
                                        for (int l = 0; l < descriptionJsonArray.length(); l++) {
                                            EquipDescriptionDataModel equipDescriptionDataModel = new EquipDescriptionDataModel();
                                            JSONObject jsonDescriptionObject = descriptionJsonArray.getJSONObject(l);
                                            String descriptionName = jsonDescriptionObject.optString("Description").toString();
                                            String descriptionId = jsonDescriptionObject.optString("ID").toString();
                                            equipDescriptionDataModel.setId(descriptionId);
                                            equipDescriptionDataModel.setName(descriptionName);
                                            equipDescriptionDataModel.setCapacityId(capacityName);
                                            arrEquipDescriptionData.add(equipDescriptionDataModel);
                                        }
                                    }
                                }
                            }
                            atmDatabase.addNewEquipData(arrEquipTypeData, arrEquipMakeData, arrEquipCapacityData, arrEquipDescriptionData);
                            flag = true;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return flag;
                }

                @Override
                protected void onPostExecute(Boolean flag) {
                    super.onPostExecute(flag);
                    EquipmentFlage = true;
                    checkAllcallDone();
                }
            }.execute();

        }

    }


    public void getCircleMaster(String serviceURL) {
        ServiceCaller serviceCaller = new ServiceCaller(SplashScreen.this);
        serviceCaller.CallCommanServiceMethod(serviceURL, "getCircleMaster", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveCircleMasterData(result);
                } else {
                    ASTUIUtil.showToast("Circle Master Data Not Avilable");
                    CircleMasterFlage = true;
                    checkAllcallDone();
                }
            }

        });
    }


    /**
     * Parse and Save  CircleMaster Data
     */

    public void parseandsaveCircleMasterData(final String result) {
        if (result != null) {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    Boolean flag = false;
                    try {
                        atmDatabase.deleteAllRows("circle");
                        atmDatabase.deleteAllRows("district");
                        atmDatabase.deleteAllRows("SSA");
                        JSONObject jsonRootObject = new JSONObject(result);
                        String jsonStatus = jsonRootObject.optString("Status").toString();
                        if (jsonStatus.equals("1")) {
                            JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
                            ArrayList<CircleMasterDataModel> arrCircleData = new ArrayList<>();
                            ArrayList<DistrictMasterDataModel> arrDistrictData = new ArrayList<>();
                            ArrayList<SSAmasterDataModel> arrSSAData = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                CircleMasterDataModel circleMasterDataModel = new CircleMasterDataModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String circleId = jsonObject.optString("CircleId").toString();
                                String circleName = jsonObject.optString("CircleName").toString();
                                circleMasterDataModel.setCircleName(circleName);
                                circleMasterDataModel.setCircleId(circleId);
                                JSONArray districtJsonArray = jsonObject.optJSONArray("DistrictList");
                                for (int j = 0; j < districtJsonArray.length(); j++) {
                                    DistrictMasterDataModel districtMasterDataModel = new DistrictMasterDataModel();
                                    JSONObject jsonDistrictObj = districtJsonArray.getJSONObject(j);
                                    String distictName = jsonDistrictObj.optString("DistrictName").toString();
                                    String distictId = jsonDistrictObj.optString("DistrictID").toString();
                                    districtMasterDataModel.setCircleId(circleId);
                                    districtMasterDataModel.setDistrictId(distictId);
                                    districtMasterDataModel.setDistrictName(distictName);
                                    arrDistrictData.add(districtMasterDataModel);
                                }
                                JSONArray SSAJsonArray = jsonObject.optJSONArray("SSAList");
                                for (int j = 0; j < SSAJsonArray.length(); j++) {
                                    SSAmasterDataModel SSAMasterDataModel = new SSAmasterDataModel();
                                    JSONObject jsonSSAObj = SSAJsonArray.getJSONObject(j);
                                    String SSAName = jsonSSAObj.optString("SSAName").toString();
                                    String SSAId = jsonSSAObj.optString("SSAId").toString();
                                    SSAMasterDataModel.setCircleId(circleId);
                                    SSAMasterDataModel.setSSAid(SSAId);
                                    SSAMasterDataModel.setSSAname(SSAName);
                                    arrSSAData.add(SSAMasterDataModel);
                                }
                                arrCircleData.add(circleMasterDataModel);
                            }

                            atmDatabase.addCircleData(arrCircleData);
                            atmDatabase.addDistrictData(arrDistrictData);
                            atmDatabase.addSSAData(arrSSAData);
                            flag = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return flag;
                }

                @Override
                protected void onPostExecute(Boolean flag) {
                    super.onPostExecute(flag);
                    CircleMasterFlage = true;
                    checkAllcallDone();
                }
            }.execute();
        }
    }

    private void checkAllcallDone() {
        if (SiteMasterFlage && EquipmentFlage && CircleMasterFlage) {
            String userId = pref.getString("userId", "");
            if (!userId.equals("")) {
                Intent intentLoggedIn = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intentLoggedIn);
            } else {
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
            if (progrssBar.isShowing()) {
                progrssBar.dismiss();
            }
        }
    }


    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;
        new GetLatestVersion().execute();
    }

    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=com.telecom.ast.sitesurvey";
                Document doc = Jsoup.connect(urlOfAppFromPlayStore).get();
                latestVersion = doc.getElementsByClass("htlgb").get(6).text();
            } catch (Exception e) {
                e.printStackTrace();

            }

            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    if (!isFinishing()) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
                        AstAppUgradeDlgActivity fnAppUgradeDlgActivity = new AstAppUgradeDlgActivity(SplashScreen.this) {
                            @Override
                            public void onSkip() {
                                Toast.makeText(SplashScreen.this, "Please Update your App", Toast.LENGTH_LONG).show();
                            }
                        };
                        fnAppUgradeDlgActivity.show();
                    }
                }
            } else
                super.onPostExecute(jsonObject);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progrssBar != null && progrssBar.isShowing()) {
            progrssBar.dismiss();
        }
    }

/*    @Override
    protected void onResume() {
        super.onResume();
        if (progrssBar != null && progrssBar.isShowing()) {
            progrssBar.dismiss();
        }
    }*/


    @Override
    public void onDetachedFromWindow() {
        if (progrssBar != null && progrssBar.isShowing())
            progrssBar.dismiss();
        super.onDetachedFromWindow();
    }
}
