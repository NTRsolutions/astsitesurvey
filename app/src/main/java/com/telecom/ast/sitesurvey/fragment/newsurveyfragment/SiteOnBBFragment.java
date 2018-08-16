package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.adapter.SiteOnBBGridAdapter;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.model.BtsInfoData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SiteOnBBFragment extends MainFragment {
    SiteOnBBGridAdapter siteOnBBGridAdapter;
    GridView homeScreenGrid;
    Button button;
    int count = 1;
    // Initializing a new String Array
    ArrayList<BtsInfoData> btsInfoDataList;


    @Override
    protected int fragmentLayout() {
        return R.layout.sitebbgrid_home;
    }

    @Override
    protected void loadView() {
        this.homeScreenGrid = findViewById(R.id.homeScreenGrid);
        button = findViewById(R.id.addMoreItem);
    }

    @Override
    protected void setClickListeners() {
        button.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {

        AtmDatabase atmDatabase = new AtmDatabase(getContext());
        btsInfoDataList = new ArrayList<BtsInfoData>();
        BtsInfoData btsInfoData = new BtsInfoData();
        btsInfoData.setName("SMPS & Clamp Meter");
        btsInfoDataList.add(btsInfoData);
        ArrayList<BtsInfoData> btsList = atmDatabase.getAllBTSInfoList();
        if (btsList != null && btsList.size() > 0) {
            btsInfoDataList.addAll(btsList);
        }
        setAdaptor();
    }


    private void setAdaptor() {
        this.siteOnBBGridAdapter = new SiteOnBBGridAdapter(getContext(), btsInfoDataList);
        this.homeScreenGrid.setAdapter(siteOnBBGridAdapter);
        homeScreenGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Bundle bundle = new Bundle();
                if (position == 0) {
                    ReadingSmpsFragment readingSmpsFragment = new ReadingSmpsFragment();
                    openBBDeatilFragment(readingSmpsFragment, "SMPS and Clamp Meter Screen", bundle);
                } /*else if (position == 1) {
                    ClampMeterFragment clampMeterFragment = new ClampMeterFragment();
                    openBasicDataFragment(clampMeterFragment, "Clamp Meter");
                }*/ else {
                    int sno = btsInfoDataList.get(position).getsNo();

                    bundle.putInt("sno", sno);
                    OperatorNameFragment operatorNameFragment = new OperatorNameFragment();
                    openBBDeatilFragment(operatorNameFragment, "Operator Details", bundle);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    //open BasicDataFragment
    private void openBBDeatilFragment(MainFragment fragment, String headertext, Bundle bundle) {
        bundle.putString("headerTxt", headertext);
        bundle.putBoolean("showMenuButton", false);
        ApplicationHelper.application().getActivity().updateFragment(fragment, bundle);

    }
}
