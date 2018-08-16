package com.telecom.ast.sitesurvey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.telecom.ast.sitesurvey.model.BasicDataModel;
import com.telecom.ast.sitesurvey.model.BtsInfoData;
import com.telecom.ast.sitesurvey.model.CircleMasterDataModel;
import com.telecom.ast.sitesurvey.model.DistrictMasterDataModel;
import com.telecom.ast.sitesurvey.model.EbMeterDataModel;
import com.telecom.ast.sitesurvey.model.EquipCapacityDataModel;
import com.telecom.ast.sitesurvey.model.EquipDescriptionDataModel;
import com.telecom.ast.sitesurvey.model.EquipMakeDataModel;
import com.telecom.ast.sitesurvey.model.EquipTypeDataModel;
import com.telecom.ast.sitesurvey.model.EquipmentMasterDataModel;
import com.telecom.ast.sitesurvey.model.EqupmientData;
import com.telecom.ast.sitesurvey.model.SSAmasterDataModel;
import com.telecom.ast.sitesurvey.model.SelectedEquipmentDataModel;
import com.telecom.ast.sitesurvey.model.SiteMasterDataModel;
import com.telecom.ast.sitesurvey.model.SiteOnBatteryBankDataModel;
import com.telecom.ast.sitesurvey.model.SiteOnDG;
import com.telecom.ast.sitesurvey.model.SiteOnEbDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohit on 10/14/2015.
 */
public class AtmDatabase extends SQLiteOpenHelper {
    //http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 81;

    // Database Name
    private static final String DATABASE_NAME = "AST_ATM";

    // table name
    private static final String TABLE_CIRCLE = "circle";
    private static final String TABLE_DISTRICT = "district";
    private static final String TABLE_SSA = "SSA";
    private static final String TABLE_SITE = "site";
    private static final String TABLE_EQUIPMENT = "equipment";
    private static final String TABLE_BASIC_DATA = "basic_data";
    private static final String TABLE_EQUIPMENT_SELECTED = "eq_battery";
    private static final String TABLE_EB_METER = "eb_meter";
    private static final String TABLE_SITE_ON_BB = "site_on_bb";
    private static final String TABLE_SITE_ON_DG = "site_on_dg";
    private static final String TABLE_SITE_ON_EB = "site_on_eb";

    private static final String TABLE_EQUIPMENT_TYPE = "equipment_type";
    private static final String TABLE_EQUIPMENT_MAKE = "equipment_make";
    private static final String TABLE_EQUIPMENT_CAPACITY = "equipment_capacity";
    private static final String TABLE_EQUIPMENT_DESCRIPTION = "equipment_description";

    private static final String KEY_ID = "id";
    private static final String USERID = "user_id";

    // Equipment Type Table Columns
    //private static final String KEY_ID = "id";
    private static final String EQUIPMENT_TYPE_NAME = "type_name";
    private static final String EQUIPMENT_TYPE_ID = "type_id";
    private static final String EQUIPMENT_TYPE_LAST_UPDATED = "last_updated";

    // Equipment Make Table Columns
    //private static final String KEY_ID = "id";
    private static final String EQUIPMENT_MAKE_NAME = "make_name";
    private static final String EQUIPMENT_MAKE_ID = "make_id";
    private static final String EQUIPMENT_MAKE_TYPE_ID = "make_type_id";
    private static final String EQUIPMENT_MAKE_LAST_UPDATED = "last_updated";

    // Equipment Capacity Table Columns
    //private static final String KEY_ID = "id";
    private static final String EQUIPMENT_CAPACITY_NAME = "capacity_name";
    private static final String EQUIPMENT_CAPACITY_ID = "capacity_id";
    private static final String EQUIPMENT_CAPACITY_MAKE_ID = "capacity_make_id";
    private static final String EQUIPMENT_CAPACITY_LAST_UPDATED = "last_updated";

    // Equipment Description Table Columns
    //private static final String KEY_ID = "id";
    private static final String EQUIPMENT_DESCRIPTION_NAME = "description_name";
    private static final String EQUIPMENT_DESCRIPTION_ID = "description_id";
    private static final String EQUIPMENT_DESCRIPTION_CAPACITY_ID = "description_capacity_id";
    private static final String EQUIPMENT_DESCRIPTION_LAST_UPDATED = "last_updated";
    // Circle Table Columns
    //private static final String KEY_ID = "id";
    private static final String CIRCLE_NAME = "circle_name";
    private static final String CIRCLE_ID = "circle_id";
    private static final String CIRCLE_LAST_UPDATED = "last_updated";

    // District Table Columns
    //private static final String KEY_ID = "id";
    private static final String DISTRICT_NAME = "district_name";
    private static final String DISTRICT_ID = "district_id";
    private static final String DISTRICT_CIRCLE_ID = "circle_id";
    private static final String DISTRICT_LAST_UPDATED = "last_updated";

    // SSA Table Columns
    //private static final String KEY_ID = "id";
    private static final String SSA_NAME = "SSA_name";
    private static final String SSA_ID = "SSA_id";
    private static final String SSA_CIRCLE_ID = "SSA_circle_id";
    private static final String SSA_LAST_UPDATED = "last_updated";

    // Site Table Columns
    //private static final String KEY_ID = "id";
    private static final String SITE_ID = "site_id";
    private static final String SITE_NAME = "site_name";
    private static final String SITE_CUSTOMER_SITE_ID = "customer_site_id";
    private static final String SITE_CIRCLE_ID = "circle_id";
    private static final String SITE_CIRCLE_NAME = "circle_name";
    private static final String SITE_SSA_ID = "SSA_id";
    private static final String SITE_SSA_NAME = "SSA_name";
    private static final String SITE_LAST_UPDATED = "last_updated";

    // Equipment Table Columns
    //private static final String KEY_ID = "id";
    private static final String EQUIP_ID = "equip_id";
    private static final String EQUIP_TYPE = "equip_type";
    private static final String EQUIP_CODE = "equip_code";
    private static final String EQUIP_DES = "equip_des";
    private static final String EQUIP_MAKE = "equip_make";
    private static final String EQUIP_MODEL = "equip_model";
    private static final String EQUIP_RATING = "equip_rating";
    private static final String EQUIP_LAST_UPDATED = "last_updated";

    // BASIC DATA FORM Table Columns
    //private static final String KEY_ID = "id";
    private static final String BASIC_DATE_TIME = "date_time";
    private static final String BASIC_SURVEYOR_NAME = "surveyor_name";
    private static final String BASIC_SITE_ID = "site_id";
    private static final String BASIC_SITE_NAME = "site_name";
    private static final String BASIC_ADDRESS = "address";
    private static final String BASIC_CIRCLE = "circle";
    private static final String BASIC_SSA = "ssa";
    private static final String BASIC_DISTRICT = "district";
    private static final String BASIC_CITY = "city";
    private static final String BASIC_PINCODE = "pincode";

    // EQUIPMENT FORM Table Columns
    //private static final String KEY_ID = "id";
    private static final String EQUIPMENT_FORM_TYPE = "equptype";
    private static final String EQUIPMENT_MAKE = "make";
    private static final String EQUIPMENT_MODEL = "model";
    private static final String EQUIPMENT_CAPACITY = "capacity";
    private static final String EQUIPMENT_FORM_MAKE_ID = "makeId";
    private static final String EQUIPMENT_FORM_MODEL_ID = "modelId";
    private static final String EQUIPMENT_FORM_CAPACITY_ID = "capacityId";
    private static final String EQUIPMENT_SERIAL_NUMBER = "serial_number";
    private static final String EQUIPMENT_DATE_OF_MANUFACTURING = "date_of_manufacturing";
    private static final String EQUIPMENT_DESCRIPTION = "description";
    private static final String EQUIPMENT_ID = "equipment_id";
    private static final String EQUIPMENT_PHOTO_1 = "photo1";
    private static final String EQUIPMENT_PHOTO_2 = "photo2";
    private static final String EQUIPMENT_PHOTO_1_NAME = "photo1_name";
    private static final String EQUIPMENT_PHOTO_2_NAME = "photo2_name";
    private static final String EQUIPMENT_TYPE = "type";
    private static final String EQUIPMENT_NUMBER_OF_AIR_CONDITIONER = "num_of_AC";
    private static final String EQUIPMENT_SITE_ID = "site_id";

    // EB Meter FORM Table Columns
    //private static final String KEY_ID = "id";
    private static final String METER_READING = "meter_reading";
    private static final String METER_NUMBER = "meter_number";
    private static final String AVAILABLE_HOURS = "available_hours";
    private static final String SUPPLY_SINGLE_PHASE = "supply_single_phase";
    private static final String SUPPLY_THREE_PHASE = "supply_three_phase";
    private static final String EB_METER_PHOTO_1 = "photo1";
    private static final String EB_METER_PHOTO_2 = "photo2";
    private static final String EB_METER_PHOTO_1_NAME = "photo1_name";
    private static final String EB_METER_PHOTO_2_NAME = "photo2_name";
    private static final String EB_METER_SITE_ID = "site_id";

    // Site On Battery Bank FORM Table Columns
    //private static final String KEY_ID = "id";
    private static final String BB_DISCHARGE_CURRENT = "discharge_current";
    private static final String BB_DISCHARGE_VOLTAGE = "discharge_voltage";
    private static final String BB_SITE_ID = "site_id";

    // SITE ON DG SET FORM Table Columns
    //private static final String KEY_ID = "id";
    private static final String DG_CURRENT = "dg_current";
    private static final String DG_FREQUENCY = "dg_frequency";
    private static final String DG_VOLTAGE = "dg_voltage";
    private static final String DG_BATTERY_CHARGE_CURRENT = "battery_charge_current";
    private static final String DG_BATTERY_VOLTAGE = "battery_voltage";
    private static final String DG_SITE_ID = "site_id";

    // SITE ON EB FORM Table Columns
    //private static final String KEY_ID = "id";
    private static final String GRID_CURRENT = "grid_current";
    private static final String GRID_VOLTAGE = "grid_voltage";
    private static final String GRID_FREQUENCY = "grid_frequency";
    private static final String GRID_SITE_ID = "grid_site_id";

    public AtmDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CIRCLE_TABLE = "CREATE TABLE " + TABLE_CIRCLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + CIRCLE_ID + " TEXT," + CIRCLE_NAME + " TEXT,"
                + CIRCLE_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_CIRCLE_TABLE);

        String CREATE_DISTRICT_TABLE = "CREATE TABLE " + TABLE_DISTRICT + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + DISTRICT_ID + " TEXT," + DISTRICT_NAME + " TEXT,"
                + DISTRICT_CIRCLE_ID + " TEXT," + DISTRICT_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_DISTRICT_TABLE);

        String CREATE_SSA_TABLE = "CREATE TABLE " + TABLE_SSA + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + SSA_ID + " TEXT," + SSA_NAME + " TEXT,"
                + SSA_CIRCLE_ID + " TEXT," + SSA_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_SSA_TABLE);

        String CREATE_SITE_TABLE = "CREATE TABLE " + TABLE_SITE + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + SITE_ID + " TEXT," + SITE_NAME + " TEXT,"
                + SITE_CUSTOMER_SITE_ID + " TEXT," + SITE_CIRCLE_ID + " TEXT,"
                + SITE_CIRCLE_NAME + " TEXT," + SITE_SSA_ID + " TEXT," + SITE_SSA_NAME + " TEXT,"
                + SITE_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_SITE_TABLE);

        String CREATE_EQUIPMENT_TABLE = "CREATE TABLE " + TABLE_EQUIPMENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + EQUIP_ID + " TEXT," + EQUIP_TYPE + " TEXT,"
                + EQUIP_CODE + " TEXT," + EQUIP_DES + " TEXT,"
                + EQUIP_MAKE + " TEXT," + EQUIP_MODEL + " TEXT," + EQUIP_RATING + " TEXT,"
                + EQUIP_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_EQUIPMENT_TABLE);

        String CREATE_BASIC_DATA_TABLE = "CREATE TABLE " + TABLE_BASIC_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + BASIC_DATE_TIME + " TEXT," + BASIC_SURVEYOR_NAME + " TEXT,"
                + BASIC_SITE_ID + " TEXT," + BASIC_SITE_NAME + " TEXT," + BASIC_ADDRESS + " TEXT," + BASIC_CIRCLE + " TEXT,"
                + BASIC_SSA + " TEXT," + BASIC_DISTRICT + " TEXT," + BASIC_CITY + " TEXT," + BASIC_PINCODE + " TEXT, " + USERID + " TEXT)";
        db.execSQL(CREATE_BASIC_DATA_TABLE);

        String CREATE_EQUIPMENT_SELECTED_TABLE = "CREATE TABLE " + TABLE_EQUIPMENT_SELECTED + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + EQUIPMENT_MAKE + " TEXT," + EQUIPMENT_MODEL + " TEXT,"
                + EQUIPMENT_CAPACITY + " TEXT," + EQUIPMENT_SERIAL_NUMBER + " TEXT," + EQUIPMENT_DATE_OF_MANUFACTURING + " TEXT,"
                + EQUIPMENT_DESCRIPTION + " TEXT," + EQUIPMENT_ID + " TEXT," + EQUIPMENT_PHOTO_1 + " TEXT,"
                + EQUIPMENT_PHOTO_2 + " TEXT," + EQUIPMENT_PHOTO_1_NAME + " TEXT," + EQUIPMENT_PHOTO_2_NAME + " TEXT,"
                + EQUIPMENT_TYPE + " TEXT," + EQUIPMENT_NUMBER_OF_AIR_CONDITIONER + " TEXT, " + EQUIPMENT_SITE_ID + " TEXT, "
                + EQUIPMENT_FORM_MAKE_ID + " TEXT," + EQUIPMENT_FORM_MODEL_ID + " TEXT, " + EQUIPMENT_FORM_CAPACITY_ID + " TEXT, "
                + EQUIPMENT_FORM_TYPE + " TEXT, " + USERID + " TEXT)";
        db.execSQL(CREATE_EQUIPMENT_SELECTED_TABLE);

        String CREATE_EB_METER_TABLE = "CREATE TABLE " + TABLE_EB_METER + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + METER_READING + " TEXT," + METER_NUMBER + " TEXT,"
                + AVAILABLE_HOURS + " TEXT," + SUPPLY_SINGLE_PHASE + " TEXT," + SUPPLY_THREE_PHASE + " TEXT,"
                + EB_METER_PHOTO_1 + " " + "TEXT,"
                + EB_METER_PHOTO_2 + " TEXT," + EB_METER_PHOTO_1_NAME + " TEXT,"
                + EB_METER_PHOTO_2_NAME + " TEXT," + EB_METER_SITE_ID + " TEXT," + USERID + " TEXT)";
        db.execSQL(CREATE_EB_METER_TABLE);

        String CREATE_SITE_ON_BB_TABLE = "CREATE TABLE " + TABLE_SITE_ON_BB + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + BB_DISCHARGE_CURRENT + " TEXT," + BB_DISCHARGE_VOLTAGE + " TEXT,"
                + BB_SITE_ID + " TEXT," + USERID + " TEXT)";
        db.execSQL(CREATE_SITE_ON_BB_TABLE);

        String CREATE_SITE_ON_DG_TABLE = "CREATE TABLE " + TABLE_SITE_ON_DG + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + DG_CURRENT + " TEXT," + DG_FREQUENCY + " TEXT,"
                + DG_VOLTAGE + " TEXT," + DG_BATTERY_CHARGE_CURRENT + " TEXT," + DG_BATTERY_VOLTAGE + " TEXT,"
                + DG_SITE_ID + " TEXT," + USERID + " TEXT)";
        db.execSQL(CREATE_SITE_ON_DG_TABLE);

        String CREATE_SITE_ON_EB_TABLE = "CREATE TABLE " + TABLE_SITE_ON_EB + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + GRID_CURRENT + " TEXT," + GRID_VOLTAGE + " TEXT,"
                + GRID_FREQUENCY + " TEXT," + GRID_SITE_ID + " TEXT," + USERID + " TEXT)";
        db.execSQL(CREATE_SITE_ON_EB_TABLE);

        String CREATE_TYPE_TABLE = "CREATE TABLE " + TABLE_EQUIPMENT_TYPE + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + EQUIPMENT_TYPE_NAME + " TEXT," + EQUIPMENT_TYPE_ID + " TEXT,"
                + EQUIPMENT_TYPE_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_TYPE_TABLE);

        String CREATE_MAKE_TABLE = "CREATE TABLE " + TABLE_EQUIPMENT_MAKE + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + EQUIPMENT_MAKE_NAME + " TEXT," + EQUIPMENT_MAKE_ID + " TEXT,"
                + EQUIPMENT_MAKE_TYPE_ID + " TEXT," + EQUIPMENT_MAKE_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_MAKE_TABLE);

        String CREATE_CAPACITY_TABLE = "CREATE TABLE " + TABLE_EQUIPMENT_CAPACITY + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + EQUIPMENT_CAPACITY_NAME + " TEXT," + EQUIPMENT_CAPACITY_ID + " TEXT,"
                + EQUIPMENT_CAPACITY_MAKE_ID + " TEXT," + EQUIPMENT_CAPACITY_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_CAPACITY_TABLE);

        String CREATE_DESCRIPTION_TABLE = "CREATE TABLE " + TABLE_EQUIPMENT_DESCRIPTION + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement," + EQUIPMENT_DESCRIPTION_ID + " TEXT," + EQUIPMENT_DESCRIPTION_NAME + " TEXT,"
                + EQUIPMENT_DESCRIPTION_CAPACITY_ID + " TEXT," + EQUIPMENT_DESCRIPTION_LAST_UPDATED + " TEXT)";
        db.execSQL(CREATE_DESCRIPTION_TABLE);


        String CREATE_BTSInfo_TABLE = "CREATE TABLE BTSInfo(sno INTEGR,type TEXT,btsName TEXT, CabinetQty TEXT,NoofDCDBBox TEXT,NoofKroneBox TEXT,NoofTransmissionRack TEXT)";
        db.execSQL(CREATE_BTSInfo_TABLE);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CIRCLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SSA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BASIC_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_SELECTED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EB_METER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE_ON_BB);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE_ON_DG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE_ON_EB);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_MAKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_CAPACITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_DESCRIPTION);

        db.execSQL("DROP TABLE IF EXISTS BTSInfo");

        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // -------------------------------Adding new Circle---------------------------------------
    public boolean addCircleData(List<CircleMasterDataModel> circleDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < circleDataList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(CIRCLE_NAME, circleDataList.get(i).getCircleName());
                values.put(CIRCLE_ID, circleDataList.get(i).getCircleId());
                values.put(CIRCLE_LAST_UPDATED, time);

                // Inserting Row
                db.insert(TABLE_CIRCLE, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    // ------------------------Getting All Circle Data-------------------------------------------
    public ArrayList<CircleMasterDataModel> getAllCircleData(String sortType) {
        ArrayList<CircleMasterDataModel> circleList = new ArrayList<CircleMasterDataModel>();
        // Select All Query
        String selectQuery = "";

        //selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " DESC";
        selectQuery = "SELECT  * FROM " + TABLE_CIRCLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CircleMasterDataModel circleModel = new CircleMasterDataModel();
                circleModel.setCircleName(cursor.getString(cursor.getColumnIndex(CIRCLE_NAME)));
                circleModel.setCircleId(cursor.getString(cursor.getColumnIndex(CIRCLE_ID)));
                circleModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(CIRCLE_LAST_UPDATED)));

                // Adding contact to list
                circleList.add(circleModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return circleList;
    }

    //---------------------------------------------------------------------------------------

    // -------------------------------Adding new SSA---------------------------------------
    public boolean addDistrictData(List<DistrictMasterDataModel> districtDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < districtDataList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(DISTRICT_ID, districtDataList.get(i).getDistrictId());
                values.put(DISTRICT_NAME, districtDataList.get(i).getDistrictName());
                values.put(DISTRICT_LAST_UPDATED, time);
                values.put(DISTRICT_CIRCLE_ID, districtDataList.get(i).getCircleId());

                // Inserting Row
                db.insert(TABLE_DISTRICT, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    // ------------------------Getting All District Data-------------------------------------------

    public ArrayList<DistrictMasterDataModel> getAllDistrictData(String sortType, String circleId) {
        ArrayList<DistrictMasterDataModel> districtList = new ArrayList<DistrictMasterDataModel>();
        // Select All Query
        String selectQuery = "";

        //selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " DESC";
        selectQuery = "SELECT  * FROM " + TABLE_DISTRICT + " WHERE " + DISTRICT_CIRCLE_ID + " = " + circleId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DistrictMasterDataModel districtMasterDataModel = new DistrictMasterDataModel();
                districtMasterDataModel.setCircleId(cursor.getString(cursor.getColumnIndex(DISTRICT_CIRCLE_ID)));
                districtMasterDataModel.setDistrictName(cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)));
                districtMasterDataModel.setDistrictId(cursor.getString(cursor.getColumnIndex(DISTRICT_ID)));
                districtMasterDataModel.setDistrictLastUpdated(cursor.getString(cursor.getColumnIndex(DISTRICT_LAST_UPDATED)));

                // Adding contact to list
                districtList.add(districtMasterDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return districtList;
    }

    //---------------------------------------------------------------------------------------
// -------------------------------Adding new SSA---------------------------------------
    public boolean addSSAData(List<SSAmasterDataModel> SSADataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < SSADataList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(SSA_ID, SSADataList.get(i).getSSAid());
                values.put(SSA_NAME, SSADataList.get(i).getSSAname());
                values.put(SSA_CIRCLE_ID, SSADataList.get(i).getCircleId());
                values.put(SSA_LAST_UPDATED, time);

                // Inserting Row
                db.insert(TABLE_SSA, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    // ------------------------Getting All SSA Data-------------------------------------------

    public ArrayList<SSAmasterDataModel> getAllSSAData(String sortType, String columnValue) {
        ArrayList<SSAmasterDataModel> SSAList = new ArrayList<SSAmasterDataModel>();
        // Select All Query
        String selectQuery = "";

        //selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " DESC";
        selectQuery = "SELECT  * FROM " + TABLE_SSA + " WHERE " + SSA_CIRCLE_ID + " = " + columnValue;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SSAmasterDataModel ssaDataModel = new SSAmasterDataModel();
                ssaDataModel.setCircleId(cursor.getString(cursor.getColumnIndex(SSA_CIRCLE_ID)));
                ssaDataModel.setSSAid(cursor.getString(cursor.getColumnIndex(SSA_ID)));
                ssaDataModel.setSSAname(cursor.getString(cursor.getColumnIndex(SSA_NAME)));
                ssaDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(SSA_LAST_UPDATED)));

                // Adding contact to list
                SSAList.add(ssaDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return SSAList;
    }

    //---------------------------------------------------------------------------------------
    // -------------------------------Adding new Site---------------------------------------
    public boolean addSiteData(List<SiteMasterDataModel> siteDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < siteDataList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(SITE_ID, siteDataList.get(i).getSiteId());
                values.put(SITE_NAME, siteDataList.get(i).getSiteName());
                values.put(SITE_CUSTOMER_SITE_ID, siteDataList.get(i).getCustomerSiteId());
                values.put(SITE_CIRCLE_ID, siteDataList.get(i).getCircleId());
                values.put(SITE_CIRCLE_NAME, siteDataList.get(i).getCircleName());
                values.put(SITE_SSA_ID, siteDataList.get(i).getSiteId());
                values.put(SITE_SSA_NAME, siteDataList.get(i).getSiteName());
                values.put(SITE_LAST_UPDATED, time);

                // Inserting Row
                db.insert(TABLE_SITE, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }


    // ------------------------Getting All Site Data-------------------------------------------

    public ArrayList<SiteMasterDataModel> getAllSiteData(String sortType) {
        ArrayList<SiteMasterDataModel> siteList = new ArrayList<SiteMasterDataModel>();
        // Select All Query
        String selectQuery = "";

        //selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " DESC";
        selectQuery = "SELECT  * FROM " + TABLE_SITE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SiteMasterDataModel siteDataModel = new SiteMasterDataModel();
                siteDataModel.setSiteId(cursor.getString(cursor.getColumnIndex(SITE_ID)));
                siteDataModel.setSiteName(cursor.getString(cursor.getColumnIndex(SITE_NAME)));
                siteDataModel.setCustomerSiteId(cursor.getString(cursor.getColumnIndex(SITE_CUSTOMER_SITE_ID)));
                siteDataModel.setCircleId(cursor.getString(cursor.getColumnIndex(SITE_CIRCLE_ID)));
                siteDataModel.setCircleName(cursor.getString(cursor.getColumnIndex(SITE_CIRCLE_NAME)));
                siteDataModel.setSsaName(cursor.getString(cursor.getColumnIndex(SITE_SSA_NAME)));
                siteDataModel.setSsaId(cursor.getString(cursor.getColumnIndex(SITE_SSA_ID)));
                siteDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(SITE_LAST_UPDATED)));

                // Adding contact to list
                siteList.add(siteDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return siteList;
    }

    //---------------------------------------------------------------------------------------

    // -------------------------------Adding new Equipment Data---------------------------------------
    public boolean addEquipData(List<EquipmentMasterDataModel> equipDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < equipDataList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(EQUIP_ID, equipDataList.get(i).getEquipId());
                values.put(EQUIP_TYPE, equipDataList.get(i).getEquipType());
                values.put(EQUIP_CODE, equipDataList.get(i).getEquipCode());
                values.put(EQUIP_DES, equipDataList.get(i).getEquipDes());
                values.put(EQUIP_MAKE, equipDataList.get(i).getEquipMake());
                values.put(EQUIP_MODEL, equipDataList.get(i).getEquipModel());
                values.put(EQUIP_RATING, equipDataList.get(i).getEquipRating());
                values.put(EQUIP_LAST_UPDATED, time);

                // Inserting Row
                db.insert(TABLE_EQUIPMENT, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }


    // ------------------------Getting All Equipment Data-------------------------------------------

    public ArrayList<EquipmentMasterDataModel> getAllEquipmentData(String sortType, String equipmentType) {
        ArrayList<EquipmentMasterDataModel> equipList = new ArrayList<EquipmentMasterDataModel>();
        // Select All Query
        String selectQuery = "";

        //selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " DESC";

        if (equipmentType.equals("NA")) {
            selectQuery = "SELECT  * FROM " + TABLE_EQUIPMENT;
        } else {
            selectQuery = "SELECT  * FROM " + TABLE_EQUIPMENT + " WHERE " + EQUIP_TYPE + " = '" + equipmentType + "'";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EquipmentMasterDataModel equipDataModel = new EquipmentMasterDataModel();
                equipDataModel.setEquipId(cursor.getString(cursor.getColumnIndex(EQUIP_ID)));
                equipDataModel.setEquipType(cursor.getString(cursor.getColumnIndex(EQUIP_TYPE)));
                equipDataModel.setEquipCode(cursor.getString(cursor.getColumnIndex(EQUIP_CODE)));
                equipDataModel.setEquipDes(cursor.getString(cursor.getColumnIndex(EQUIP_DES)));
                equipDataModel.setEquipMake(cursor.getString(cursor.getColumnIndex(EQUIP_MAKE)));
                equipDataModel.setEquipModel(cursor.getString(cursor.getColumnIndex(EQUIP_MODEL)));
                equipDataModel.setEquipRating(cursor.getString(cursor.getColumnIndex(EQUIP_RATING)));
                equipDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(EQUIP_LAST_UPDATED)));

                // Adding contact to list
                equipList.add(equipDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return equipList;
    }

    //---------------------------------------------------------------------------------------

    // -------------------------------Adding new Equipment Data---------------------------------------
    public boolean addNewEquipData(ArrayList<EquipTypeDataModel> equipTypeList, ArrayList<EquipMakeDataModel> equipMakeList,
                                   ArrayList<EquipCapacityDataModel> equipCapacityList, ArrayList<EquipDescriptionDataModel> equipDescriptionList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < equipTypeList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(EQUIPMENT_TYPE_ID, equipTypeList.get(i).getId());
                values.put(EQUIPMENT_TYPE_NAME, equipTypeList.get(i).getName());
                values.put(EQUIPMENT_TYPE_LAST_UPDATED, time);

                // Inserting Row
                db.insert(TABLE_EQUIPMENT_TYPE, null, values);
                //return  insert > 0;
            }

            for (int i = 0; i < equipMakeList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(EQUIPMENT_MAKE_ID, equipMakeList.get(i).getId());
                values.put(EQUIPMENT_MAKE_NAME, equipMakeList.get(i).getName());
                values.put(EQUIPMENT_MAKE_TYPE_ID, equipMakeList.get(i).getTypeId());
                values.put(EQUIPMENT_MAKE_LAST_UPDATED, time);

                // Inserting Row
                db.insert(TABLE_EQUIPMENT_MAKE, null, values);
            }

            for (int i = 0; i < equipCapacityList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(EQUIPMENT_CAPACITY_ID, equipCapacityList.get(i).getId());
                values.put(EQUIPMENT_CAPACITY_NAME, equipCapacityList.get(i).getName());
                values.put(EQUIPMENT_CAPACITY_MAKE_ID, equipCapacityList.get(i).getMakeId());
                values.put(EQUIPMENT_CAPACITY_LAST_UPDATED, time);

                // Inserting Row
                db.insert(TABLE_EQUIPMENT_CAPACITY, null, values);
            }

            for (int i = 0; i < equipDescriptionList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(EQUIPMENT_DESCRIPTION_ID, equipDescriptionList.get(i).getId());
                values.put(EQUIPMENT_DESCRIPTION_NAME, equipDescriptionList.get(i).getName());
                values.put(EQUIPMENT_DESCRIPTION_CAPACITY_ID, equipDescriptionList.get(i).getCapacityId());
                values.put(EQUIPMENT_DESCRIPTION_LAST_UPDATED, time);

                // Inserting Row
                db.insert(TABLE_EQUIPMENT_DESCRIPTION, null, values);
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    // ------------------------Getting Equipment Data-------------------------------------------
    public ArrayList<EquipMakeDataModel> getEquipmentData(String equipmentType) {
        ArrayList<EquipMakeDataModel> equipMakeList = new ArrayList<EquipMakeDataModel>();
        String selectQuery = "";
        selectQuery = "SELECT  * FROM " + TABLE_EQUIPMENT_TYPE + " WHERE " + EQUIPMENT_TYPE_NAME + " = '" + equipmentType + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EquipMakeDataModel equipMakeDataModel = new EquipMakeDataModel();
                equipMakeDataModel.setId(cursor.getString(cursor.getColumnIndex(EQUIPMENT_TYPE_ID)));
                equipMakeDataModel.setName(cursor.getString(cursor.getColumnIndex(EQUIPMENT_TYPE_NAME)));
                equipMakeDataModel.setLastUpdated(cursor.getString(cursor.getColumnIndex(EQUIPMENT_TYPE_LAST_UPDATED)));

                // Adding contact to list
                equipMakeList.add(equipMakeDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return equipMakeList;
    }

    public ArrayList<EquipMakeDataModel> getEquipmentMakeData(String sortType, String equipmentType) {
        ArrayList<EquipMakeDataModel> equipMakeList = new ArrayList<EquipMakeDataModel>();
        // Select All Query
        String selectQuery = "";

        //selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " DESC";

        selectQuery = "SELECT  * FROM " + TABLE_EQUIPMENT_MAKE + " WHERE " + EQUIPMENT_MAKE_TYPE_ID + " = '" + equipmentType + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EquipMakeDataModel equipMakeDataModel = new EquipMakeDataModel();
                equipMakeDataModel.setId(cursor.getString(cursor.getColumnIndex(EQUIPMENT_MAKE_ID)));
                equipMakeDataModel.setName(cursor.getString(cursor.getColumnIndex(EQUIPMENT_MAKE_NAME)));
                equipMakeDataModel.setLastUpdated(cursor.getString(cursor.getColumnIndex(EQUIPMENT_MAKE_LAST_UPDATED)));

                // Adding contact to list
                equipMakeList.add(equipMakeDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return equipMakeList;
    }

    public ArrayList<EquipCapacityDataModel> getEquipmentCapacityData(String sortType, String makeName) {
        ArrayList<EquipCapacityDataModel> equipCapacityList = new ArrayList<EquipCapacityDataModel>();
        // Select All Query
        String selectQuery = "";

        //selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " DESC";

        selectQuery = "SELECT  * FROM " + TABLE_EQUIPMENT_CAPACITY + " WHERE " + EQUIPMENT_CAPACITY_MAKE_ID + " = '" + makeName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EquipCapacityDataModel equipCapacityDataModel = new EquipCapacityDataModel();
                equipCapacityDataModel.setId(cursor.getString(cursor.getColumnIndex(EQUIPMENT_CAPACITY_ID)));
                equipCapacityDataModel.setName(cursor.getString(cursor.getColumnIndex(EQUIPMENT_CAPACITY_NAME)));
                equipCapacityDataModel.setLastUpdatedDate(cursor.getString(cursor.getColumnIndex(EQUIPMENT_CAPACITY_LAST_UPDATED)));

                // Adding contact to list
                equipCapacityList.add(equipCapacityDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return equipCapacityList;
    }

    public ArrayList<EquipDescriptionDataModel> getEquipmentDescriptionData(String sortType, String capacityName) {
        ArrayList<EquipDescriptionDataModel> equipDescriptionList = new ArrayList<EquipDescriptionDataModel>();
        // Select All Query
        String selectQuery = "";

        //selectQuery = "SELECT  * FROM " + TABLE_CIRCLE + " ORDER BY " + CIRCLE_TOTAL_ALARM_SITES + " DESC";

        selectQuery = "SELECT  * FROM " + TABLE_EQUIPMENT_DESCRIPTION + " WHERE " + EQUIPMENT_DESCRIPTION_CAPACITY_ID + " = '" + capacityName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EquipDescriptionDataModel equipDescriptionDataModel = new EquipDescriptionDataModel();
                equipDescriptionDataModel.setId(cursor.getString(cursor.getColumnIndex(EQUIPMENT_DESCRIPTION_ID)));
                equipDescriptionDataModel.setName(cursor.getString(cursor.getColumnIndex(EQUIPMENT_DESCRIPTION_NAME)));
                equipDescriptionDataModel.setLastUpdateDate(cursor.getString(cursor.getColumnIndex(EQUIPMENT_DESCRIPTION_LAST_UPDATED)));

                // Adding contact to list
                equipDescriptionList.add(equipDescriptionDataModel);
            } while (cursor.moveToNext());
        }

        db.close();

        // return contact list
        return equipDescriptionList;
    }

    //---------------------------------------------------------------------------------------


    // -------------------------------Adding new Basic Data---------------------------------------
    public boolean addBasicFormData(List<BasicDataModel> basicDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < basicDataList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(BASIC_DATE_TIME, basicDataList.get(i).getDateTime());
                values.put(BASIC_SURVEYOR_NAME, basicDataList.get(i).getSurveyorName());
                values.put(BASIC_SITE_ID, basicDataList.get(i).getSiteId());
                values.put(BASIC_ADDRESS, basicDataList.get(i).getAddress());
                values.put(BASIC_CIRCLE, basicDataList.get(i).getCircle());
                values.put(BASIC_SSA, basicDataList.get(i).getSsa());
                values.put(BASIC_DISTRICT, basicDataList.get(i).getDistrict());
                values.put(BASIC_CITY, basicDataList.get(i).getCity());
                values.put(BASIC_PINCODE, basicDataList.get(i).getPincode());
                values.put(USERID, basicDataList.get(i).getUserId());

                // Inserting Row
                db.insert(TABLE_BASIC_DATA, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    //---------------------------------------------------------------------------------------

    // -------------------------------Adding new Equipment Form Data---------------------------------------
    public boolean addEquipmentFormData(List<SelectedEquipmentDataModel> selectedEquipmentDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < selectedEquipmentDataList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(EQUIPMENT_FORM_TYPE, selectedEquipmentDataList.get(i).getEquipmentType());
                values.put(EQUIPMENT_MAKE, selectedEquipmentDataList.get(i).getEquipmentMake());
                values.put(EQUIPMENT_MODEL, selectedEquipmentDataList.get(i).getEquipmentModel());
                values.put(EQUIPMENT_CAPACITY, selectedEquipmentDataList.get(i).getEquipmentCapacity());
                values.put(EQUIPMENT_FORM_MAKE_ID, selectedEquipmentDataList.get(i).getMakeId());
                values.put(EQUIPMENT_FORM_MODEL_ID, selectedEquipmentDataList.get(i).getModelId());
                values.put(EQUIPMENT_FORM_CAPACITY_ID, selectedEquipmentDataList.get(i).getCapacityId());
                values.put(EQUIPMENT_SERIAL_NUMBER, selectedEquipmentDataList.get(i).getSerialNumber());
                values.put(EQUIPMENT_DATE_OF_MANUFACTURING, selectedEquipmentDataList.get(i).getDateOfManufacturing());
                values.put(EQUIPMENT_DESCRIPTION, selectedEquipmentDataList.get(i).getDescription());
                values.put(EQUIPMENT_ID, selectedEquipmentDataList.get(i).getEquipmentId());
                /*values.put(EQUIPMENT_PHOTO_1, selectedEquipmentDataList.get(i).getPhoto1());
                values.put(EQUIPMENT_PHOTO_2, selectedEquipmentDataList.get(i).getPhoto2());
                values.put(EQUIPMENT_PHOTO_1_NAME, selectedEquipmentDataList.get(i).getPhoto1Name());
                values.put(EQUIPMENT_PHOTO_2_NAME, selectedEquipmentDataList.get(i).getPhoto2Name());*/
                values.put(EQUIPMENT_TYPE, selectedEquipmentDataList.get(i).getType());
                values.put(EQUIPMENT_NUMBER_OF_AIR_CONDITIONER, selectedEquipmentDataList.get(i).getNumberOfAC());
                values.put(EQUIPMENT_SITE_ID, selectedEquipmentDataList.get(i).getSiteId());
                values.put(USERID, selectedEquipmentDataList.get(i).getUserId());

                // Inserting Row
                db.insert(TABLE_EQUIPMENT_SELECTED, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    //---------------------------------------------------------------------------------------

    // -------------------------------Adding new Equipment Form Data---------------------------------------
    public boolean addEbMeterFormData(List<EbMeterDataModel> ebMeterDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < ebMeterDataList.size(); i++) {
                ContentValues values = new ContentValues();
                /*values.put(EQUIPMENT_MAKE, selectedEquipmentDataList.get(i).get());
                values.put(EQUIPMENT_MODEL, selectedEquipmentDataList.get(i).getSurveyorName());
                values.put(EQUIPMENT_CAPACITY, selectedEquipmentDataList.get(i).getSiteId());*/
                values.put(METER_READING, ebMeterDataList.get(i).getMeterReading());
                values.put(METER_NUMBER, ebMeterDataList.get(i).getMeterNumber());
                values.put(AVAILABLE_HOURS, ebMeterDataList.get(i).getAvailableHours());
                values.put(SUPPLY_SINGLE_PHASE, ebMeterDataList.get(i).getSupplySinglePhase());
                values.put(SUPPLY_THREE_PHASE, ebMeterDataList.get(i).getSupplyThreePhase());
                /*values.put(EB_METER_PHOTO_1, ebMeterDataList.get(i).getPhoto1());
                values.put(EB_METER_PHOTO_2, ebMeterDataList.get(i).getPhoto2());
                values.put(EB_METER_PHOTO_1_NAME, ebMeterDataList.get(i).getPhoto1Name());
                values.put(EB_METER_PHOTO_2_NAME, ebMeterDataList.get(i).getPhoto2Name());*/
                values.put(EB_METER_SITE_ID, ebMeterDataList.get(i).getSiteId());
                values.put(USERID, ebMeterDataList.get(i).getUserId());

                // Inserting Row
                db.insert(TABLE_EB_METER, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    //---------------------------------------------------------------------------------------

    // -------------------------------Adding new Site On BB Form Data---------------------------------------
    public boolean addSiteOnBbFormData(List<SiteOnBatteryBankDataModel> siteOnBatteryBankDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < siteOnBatteryBankDataList.size(); i++) {
                ContentValues values = new ContentValues();
                /*values.put(EQUIPMENT_MAKE, selectedEquipmentDataList.get(i).get());
                values.put(EQUIPMENT_MODEL, selectedEquipmentDataList.get(i).getSurveyorName());
                values.put(EQUIPMENT_CAPACITY, selectedEquipmentDataList.get(i).getSiteId());*/
                values.put(BB_DISCHARGE_CURRENT, siteOnBatteryBankDataList.get(i).getDischargeCurrent());
                values.put(BB_DISCHARGE_VOLTAGE, siteOnBatteryBankDataList.get(i).getDischargeVoltage());
                values.put(BB_SITE_ID, siteOnBatteryBankDataList.get(i).getSiteId());
                values.put(USERID, siteOnBatteryBankDataList.get(i).getUserId());

                // Inserting Row
                db.insert(TABLE_SITE_ON_BB, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    //---------------------------------------------------------------------------------------

    // -------------------------------Adding new Site On DG Form Data---------------------------------------
    public boolean addSiteOnDgFormData(List<SiteOnDG> siteOnDgDataList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < siteOnDgDataList.size(); i++) {
                ContentValues values = new ContentValues();
                /*values.put(EQUIPMENT_MAKE, selectedEquipmentDataList.get(i).get());
                values.put(EQUIPMENT_MODEL, selectedEquipmentDataList.get(i).getSurveyorName());
                values.put(EQUIPMENT_CAPACITY, selectedEquipmentDataList.get(i).getSiteId());*/
                values.put(DG_CURRENT, siteOnDgDataList.get(i).getDgCurrent());
                values.put(DG_FREQUENCY, siteOnDgDataList.get(i).getDgFrequency());
                values.put(DG_VOLTAGE, siteOnDgDataList.get(i).getDgVoltage());
                values.put(DG_BATTERY_CHARGE_CURRENT, siteOnDgDataList.get(i).getBatteryChargeCurrent());
                values.put(DG_BATTERY_VOLTAGE, siteOnDgDataList.get(i).getBatteryVoltage());
                values.put(DG_SITE_ID, siteOnDgDataList.get(i).getSiteId());
                values.put(USERID, siteOnDgDataList.get(i).getUserId());

                // Inserting Row
                db.insert(TABLE_SITE_ON_DG, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    //---------------------------------------------------------------------------------------


    // -------------------------------Adding new Site On BB Form Data---------------------------------------
    public boolean addSiteOnEbFormData(List<SiteOnEbDataModel> siteOnEbDataModelList) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            long time = System.currentTimeMillis();
            for (int i = 0; i < siteOnEbDataModelList.size(); i++) {
                ContentValues values = new ContentValues();
                /*values.put(EQUIPMENT_MAKE, selectedEquipmentDataList.get(i).get());
                values.put(EQUIPMENT_MODEL, selectedEquipmentDataList.get(i).getSurveyorName());
                values.put(EQUIPMENT_CAPACITY, selectedEquipmentDataList.get(i).getSiteId());*/
                values.put(GRID_CURRENT, siteOnEbDataModelList.get(i).getGridCurrent());
                values.put(GRID_FREQUENCY, siteOnEbDataModelList.get(i).getGridFrequency());
                values.put(GRID_VOLTAGE, siteOnEbDataModelList.get(i).getGridVoltage());
                values.put(GRID_SITE_ID, siteOnEbDataModelList.get(i).getSiteId());
                values.put(USERID, siteOnEbDataModelList.get(i).getUserId());

                // Inserting Row
                db.insert(TABLE_SITE_ON_EB, null, values);
                //return  insert > 0;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close(); // Closing database connection
        }
        return false;
    }

    //---------------------------------------------------------------------------------------

    public void deleteAllRows(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    // upsert BTSInfo  for local use
    public boolean upsertBTSInfo(BtsInfoData ob) {
        boolean done = false;
        BtsInfoData data = null;
        if (ob.getsNo() != 0) {
            data = getBTSInfoById(ob.getsNo());
            if (data == null) {
                done = insertBTSInfoData(ob);
            } else {
                done = updateBTSInfoData(ob);
            }
        }
        return done;
    }

    public BtsInfoData getBTSInfoById(int id) {
        String query = "Select * FROM BTSInfo WHERE sno = '" + id + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BtsInfoData ob = new BtsInfoData();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            populateBTSInfoData(cursor, ob);
            cursor.close();
        } else {
            ob = null;
        }
        db.close();
        return ob;
    }


    //populate BTSInfo  list data
    private void populateBTSInfoData(Cursor cursor, BtsInfoData ob) {
        ob.setsNo(cursor.getInt(0));
        ob.setType(cursor.getString(1));
        ob.setName(cursor.getString(2));
        ob.setCabinetQty(cursor.getString(3));
        ob.setNoofDCDBBox(cursor.getString(4));
        ob.setNoofKroneBox(cursor.getString(5));
        ob.setNoofTransmissionRack(cursor.getString(6));
    }

    public boolean insertBTSInfoData(BtsInfoData ob) {
        ContentValues values = new ContentValues();
        populateBTSInfoValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("BTSInfo", null, values);
        db.close();
        return i > 0;
    }

    public boolean updateBTSInfoData(BtsInfoData ob) {
        ContentValues values = new ContentValues();
        populateBTSInfoValueData(values, ob);
        SQLiteDatabase db = this.getWritableDatabase();
        long i = 0;
        i = db.update("BTSInfo", values, " sno = '" + ob.getsNo() + "'", null);
        db.close();
        return i > 0;
    }


    public void populateBTSInfoValueData(ContentValues values, BtsInfoData ob) {
        values.put("sno", ob.getsNo());
        values.put("type", ob.getType());
        values.put("btsName", ob.getName());
        values.put("CabinetQty", ob.getCabinetQty());
        values.put("NoofDCDBBox", ob.getNoofDCDBBox());
        values.put("NoofKroneBox", ob.getNoofKroneBox());
        values.put("NoofTransmissionRack", ob.getNoofTransmissionRack());
    }

    public ArrayList<BtsInfoData> getAllBTSInfoList() {
        String query = "Select *  FROM BTSInfo ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<BtsInfoData> list = new ArrayList<BtsInfoData>();

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                BtsInfoData ob = new BtsInfoData();
                populateBTSInfoData(cursor, ob);
                list.add(ob);
                cursor.moveToNext();
            }
        }
        db.close();
        return list;
    }
    public void deleteBTCRows(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("BTSInfo", " sno = '" + id + "'", null);
        db.close();
    }

}


