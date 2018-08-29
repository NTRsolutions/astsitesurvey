package com.telecom.ast.sitesurvey.fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.adapter.HomeFeGridAdapter;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.constants.Constant;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Narayan .
 */
public class HomeFragment extends MainFragment {
    HomeFeGridAdapter homeFeGridAdapter;
    GridView homeScreenGrid;
    ASTUIUtil commonFunctions;
    AtmDatabase atmDatabase;
    ImageView imgRefresh;
    ASTProgressBar Circle_progrssBar, Site_progrssBar, equipmentList_progrssBar;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void loadView() {
        this.homeScreenGrid = findViewById(R.id.homeScreenGrid);
        this.imgRefresh = findViewById(R.id.imgRefresh);
    }

    @Override
    protected void setClickListeners() {
        this.imgRefresh.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {
        this.imgRefresh.setVisibility(View.VISIBLE);
    }

    @Override
    protected void dataToView() {
        this.commonFunctions = new ASTUIUtil();
        this.atmDatabase = new AtmDatabase(getContext());
        this.homeFeGridAdapter = new HomeFeGridAdapter(getContext());
        getAllData();
        setAdaptor();

    }


    private void setAdaptor() {
        this.homeScreenGrid.setAdapter(new HomeFeGridAdapter(getContext()));
    }

    public void getAllData() {
        if (commonFunctions.checkNetwork(getContext())) {
            String serviceURLCircle = Constant.BASE_URL + Constant.CIRCLE_MASTER_URL;
            String serviceURLSite = Constant.BASE_URL + Constant.SITE_MASTER_URL;
            String serviceURLEquipment = Constant.BASE_URL + Constant.EQUIPMENT_MASTER_URL;
            /*ArrayList<CircleMasterDataModel> arrCircleData = atmDatabase.getAllCircleData("Desc");
            ArrayList<SiteMasterDataModel> arrSiteData = atmDatabase.getAllSiteData("Desc");
            ArrayList<EquipMakeDataModel> arrEquipData = at mDatabase.getEquipmentMakeData("Desc", "BB");
            if (arrCircleData != null && arrCircleData.size() <= 0)
                getCircleMaster(serviceURLCircle);
            if (arrSiteData != null && arrSiteData.size() <= 0)
                getSiteMaster(serviceURLSite);
            if (arrEquipData != null && arrEquipData.size() <= 0)
                getEquipmentListMaster(serviceURLEquipment);*/

            getSiteMaster(serviceURLSite);
            getCircleMaster(serviceURLCircle);
            getEquipmentListMaster(serviceURLEquipment);
        }
    }

    public void refreshAllData() {
        String serviceURLCircle = Constant.BASE_URL + Constant.CIRCLE_MASTER_URL;
        String serviceURLSite = Constant.BASE_URL + Constant.SITE_MASTER_URL;
        String serviceURLEquipment = Constant.BASE_URL + Constant.EQUIPMENT_MASTER_URL;
        getCircleMaster(serviceURLCircle);
        getSiteMaster(serviceURLSite);
        getEquipmentListMaster(serviceURLEquipment);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgRefresh) {
            refreshAllData();
        }
    }


    public void getCircleMaster(String serviceURL) {
        Circle_progrssBar = new ASTProgressBar(getContext());
        Circle_progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        serviceCaller.CallCommanServiceMethod(serviceURL, "getClusterData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveCircleMasterData(result);
                } else {
                    ASTUIUtil.showToast("Data Not Avilable");
                }
                Circle_progrssBar.dismiss();
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
                        atmDatabase = new AtmDatabase(getContext());
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
                        }

                        flag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return flag;
                }

                @Override
                protected void onPostExecute(Boolean flag) {
                    super.onPostExecute(flag);
                    if (flag) {
                    }
                }
            }.execute();
        }
    }

    /*
     *
     * get SiteMaster
     */

    public void getSiteMaster(String serviceURL) {
        Site_progrssBar = new ASTProgressBar(getContext());
        Site_progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        serviceCaller.CallCommanServiceMethod(serviceURL, "getClusterData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveSiteMasterData(result);
                } else {
                    ASTUIUtil.showToast("Data Not Avilable");
                }
                Site_progrssBar.dismiss();
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
                        atmDatabase = new AtmDatabase(getContext());
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
                            String test = "";
                        }
                        flag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return flag;
                }

                @Override
                protected void onPostExecute(Boolean flag) {
                    super.onPostExecute(flag);
                    if (flag) {
                    }
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
        equipmentList_progrssBar = new ASTProgressBar(getContext());
        equipmentList_progrssBar.show();
        ServiceCaller serviceCaller = new ServiceCaller(getContext());
        serviceCaller.CallCommanServiceMethod(serviceURL, "getClusterData", new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    parseandsaveEquipmentListMaster(result);
                } else {
                    ASTUIUtil.showToast("EquipmentListMaster Data Not Avilable");
                }
                equipmentList_progrssBar.dismiss();
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
                        atmDatabase = new AtmDatabase(getContext());
                        //atmDatabase.deleteAllRows("equipment");
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

                                //arrEquipData.add(equipMasterDataModel);
                            }

                            atmDatabase.addNewEquipData(arrEquipTypeData, arrEquipMakeData, arrEquipCapacityData, arrEquipDescriptionData);
                        }
                        flag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return flag;
                }

                @Override
                protected void onPostExecute(Boolean flag) {
                    super.onPostExecute(flag);
                    if (flag) {
                    }
                }
            }.execute();

        }

    }
}