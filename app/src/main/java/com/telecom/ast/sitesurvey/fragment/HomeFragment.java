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
    }

    @Override
    protected void dataToView() {
        this.commonFunctions = new ASTUIUtil();
        this.atmDatabase = new AtmDatabase(getContext());
        this.homeFeGridAdapter = new HomeFeGridAdapter(getContext());

        setAdaptor();

    }


    private void setAdaptor() {
        this.homeScreenGrid.setAdapter(new HomeFeGridAdapter(getContext()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgRefresh) {

        }
    }


}