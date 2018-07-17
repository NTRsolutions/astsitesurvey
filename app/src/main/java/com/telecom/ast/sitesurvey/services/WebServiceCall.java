package com.telecom.ast.sitesurvey.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.telecom.ast.sitesurvey.activity.MainActivity;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.model.CircleMasterDataModel;
import com.telecom.ast.sitesurvey.model.DistrictMasterDataModel;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipDescriptionDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.model.EquipTypeDataModel;
import com.telecom.ast.sitesurvey.model.SSAmasterDataModel;
import com.telecom.ast.sitesurvey.model.SiteMasterDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AST on 28-02-2017.
 */

public class WebServiceCall {

    ProgressDialog progressbar;
    Context globalContext;

    //Shared Prefrences

    SharedPreferences pref;
    String userId, userName, empName;

    //-----------------

    AtmDatabase atmDatabase;


    public void login(String serviceURL, Context context) {
        // TODO Auto-generated method stub
        progressbar = ProgressDialog.show(context, "", "Please wait..", true);
        globalContext = context;

        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqSuccessListener(),
                reqErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                /*params.put("uid", "3494");
                params.put("ctid", "0");
                params.put("lat", "25.23");
                params.put("lon", "25.23");
                params.put("mr", "1");*/

                return params;
            }

            ;
        };
        int socketTimeout = 30000;//30 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
        queue.add(myReq);
    }

    private Response.Listener<String> reqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressbar.dismiss();
                if (response != null) {
                    try {
                        pref = globalContext.getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

                        JSONObject jsonRootObject = new JSONObject(response);
                        String jsonStatus = jsonRootObject.optString("Status").toString();
                        if (jsonStatus.equals("1")) {
                            JSONArray jsonArray = jsonRootObject.optJSONArray("Data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String userName = jsonObject.optString("UserName").toString();
                                String userId = jsonObject.optString("UserId").toString();
                                String Name = jsonObject.optString("Name").toString();

                                SharedPreferences.Editor editor = pref.edit();

                                //------------------ Storing data as KEY/VALUE pair ------------

                                editor.putString("USER_NAME", userName);
                                editor.putString("USER_ID", userId);
                                editor.putString("EMP_NAME", Name);

                                editor.commit();

                                Intent intentHomeScreen = new Intent(globalContext, MainActivity.class);
                                globalContext.startActivity(intentHomeScreen);
                            }
                        } else {
                            Toast.makeText(globalContext, "Please Provide Correct Credentials",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private Response.ErrorListener reqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.dismiss();
            }
        };
    }

    public void getCircleMaster(String serviceURL, Context context) {
        // TODO Auto-generated method stub
        //progressbar = ProgressDialog.show(context, "", "Please wait..", true);
        globalContext = context;

        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqCircleSuccessListener(),
                reqCircleErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                /*params.put("uid", "3494");
                params.put("ctid", "0");
                params.put("lat", "25.23");
                params.put("lon", "25.23");
                params.put("mr", "1");*/

                return params;
            }

            ;
        };
        int socketTimeout = 30000;//30 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
        queue.add(myReq);
    }

    private Response.Listener<String> reqCircleSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressbar.dismiss();
                if (response != null) {

                    try {
                        atmDatabase = new AtmDatabase(globalContext);
                        atmDatabase.deleteAllRows("circle");
                        atmDatabase.deleteAllRows("district");
                        atmDatabase.deleteAllRows("SSA");

                        JSONObject jsonRootObject = new JSONObject(response);
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

                            /*arrCircleData = atmDatabase.getAllCircleData("Desc");
                            arrDistrictData = atmDatabase.getAllDistrictData("Desc");
                            arrSSAData = atmDatabase.getAllSSAData("Desc");
                            String test = "";*/
                        } else {
                            Toast.makeText(globalContext, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(globalContext, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    private Response.ErrorListener reqCircleErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressbar.dismiss();
            }
        };
    }

    public void getSiteMaster(String serviceURL, Context context) {
        // TODO Auto-generated method stub
        progressbar = ProgressDialog.show(context, "", "Please wait..", true);
        globalContext = context;

        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqSiteSuccessListener(),
                reqSiteErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                /*params.put("uid", "3494");
                params.put("ctid", "0");
                params.put("lat", "25.23");
                params.put("lon", "25.23");
                params.put("mr", "1");*/

                return params;
            }

            ;
        };
        int socketTimeout = 30000;//30 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
        queue.add(myReq);
    }

    private Response.Listener<String> reqSiteSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        atmDatabase = new AtmDatabase(globalContext);
                        atmDatabase.deleteAllRows("site");

                        JSONObject jsonRootObject = new JSONObject(response);
                        String jsonStatus = jsonRootObject.optString("Status").toString();
                        if (jsonStatus.equals("1")) {
                            JSONArray jsonArray = jsonRootObject.optJSONArray("Data");

                            ArrayList<SiteMasterDataModel> arrSiteData = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                SiteMasterDataModel siteMasterDataModel = new SiteMasterDataModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
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

                            /*arrCircleData = atmDatabase.getAllCircleData("Desc");
                            arrDistrictData = atmDatabase.getAllDistrictData("Desc");
                            arrSSAData = atmDatabase.getAllSSAData("Desc");
                            String test = "";*/
                        } else {
                            Toast.makeText(globalContext, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(globalContext, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
                progressbar.dismiss();
            }
        };
    }

    private Response.ErrorListener reqSiteErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressbar.dismiss();
            }
        };
    }

    /*public void getEquipmentListMaster(String serviceURL, Context context ) {
        // TODO Auto-generated method stub
        //progressbar = ProgressDialog.show(context, "", "Please wait..", true);
        globalContext = context;

        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqEquipmentListSuccessListener(),
                reqEquipmentListErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                *//*params.put("uid", "3494");
                params.put("ctid", "0");
                params.put("lat", "25.23");
                params.put("lon", "25.23");
                params.put("mr", "1");*//*

                return params;
            }

            ;
        };
        int socketTimeout = 30000;//30 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
        queue.add(myReq);
    }

    private Response.Listener<String> reqEquipmentListSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        atmDatabase = new AtmDatabase(globalContext);
                        atmDatabase.deleteAllRows("equipment");

                        JSONObject jsonRootObject = new JSONObject(response);
                        String jsonStatus = jsonRootObject.optString("Status").toString();
                        if (jsonStatus.equals("1")) {
                            JSONArray jsonArray = jsonRootObject.optJSONArray("Data");

                            ArrayList<EquipmentMasterDataModel> arrEquipData = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                EquipmentMasterDataModel equipMasterDataModel = new EquipmentMasterDataModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String equipId = jsonObject.optString("ID").toString();
                                String equipType = jsonObject.optString("EqpType").toString();
                                String equipCode = jsonObject.optString("EqpCode").toString();
                                String equipDes = jsonObject.optString("Description").toString();
                                String equipMake = jsonObject.optString("Make").toString();
                                String equipModel = jsonObject.optString("Model").toString();
                                String equipRating = jsonObject.optString("Rating").toString();

                                equipMasterDataModel.setEquipId(equipId);
                                equipMasterDataModel.setEquipType(equipType);
                                equipMasterDataModel.setEquipCode(equipCode);
                                equipMasterDataModel.setEquipDes(equipDes);
                                equipMasterDataModel.setEquipMake(equipMake);
                                equipMasterDataModel.setEquipModel(equipModel);
                                equipMasterDataModel.setEquipRating(equipRating);

                                arrEquipData.add(equipMasterDataModel);
                            }

                            atmDatabase.addEquipData(arrEquipData);
                            //arrEquipData = atmDatabase.getAllEquipmentData("Desc");

                            String test = "";

                            *//*arrCircleData = atmDatabase.getAllCircleData("Desc");
                            arrDistrictData = atmDatabase.getAllDistrictData("Desc");
                            arrSSAData = atmDatabase.getAllSSAData("Desc");
                            String test = "";*//*
                        } else {
                            Toast.makeText(globalContext, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(globalContext, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
                //progressbar.dismiss();
            }
        };
    }
    private Response.ErrorListener reqEquipmentListErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressbar.dismiss();
            }
        };
    }*/

    public void getEquipmentListMaster(String serviceURL, Context context) {
        // TODO Auto-generated method stub
        //progressbar = ProgressDialog.show(context, "", "Please wait..", true);
        globalContext = context;

        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST,
                serviceURL, reqEquipmentListSuccessListener(),
                reqEquipmentListErrorListener()) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("key", dishId);
                return headers;
            }

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                /*params.put("uid", "3494");
                params.put("ctid", "0");
                params.put("lat", "25.23");
                params.put("lon", "25.23");
                params.put("mr", "1");*/

                return params;
            }

            ;
        };
        int socketTimeout = 300000;//3 minutes
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        myReq.setRetryPolicy(policy);
        queue.add(myReq);
    }

    private Response.Listener<String> reqEquipmentListSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        atmDatabase = new AtmDatabase(globalContext);
                        //atmDatabase.deleteAllRows("equipment");

                        JSONObject jsonRootObject = new JSONObject(response);
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

                        } else {
                            Toast.makeText(globalContext, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(globalContext, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
                //progressbar.dismiss();
            }
        };
    }

    private Response.ErrorListener reqEquipmentListErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String test = "";
                //progressbar.dismiss();
            }
        };
    }
}
