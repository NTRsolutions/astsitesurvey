package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.adapter.SiteOnBBGridAdapter;
import com.telecom.ast.sitesurvey.fragment.MainFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SiteOnBBFragment extends MainFragment {
    SiteOnBBGridAdapter siteOnBBGridAdapter;
    GridView homeScreenGrid;
    Button button;
    List<String> gridviewItemList;
    int count = 1;
    // Initializing a new String Array
    static final String[] gridviewItem = new String[]{
            "SMPS", "Clamp Meter", "Operator 1 Name"};

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
        gridviewItemList = new ArrayList<String>(Arrays.asList(gridviewItem));
        this.siteOnBBGridAdapter = new SiteOnBBGridAdapter(getContext(), gridviewItemList);
        setAdaptor();

    }


    private void setAdaptor() {
        this.homeScreenGrid.setAdapter(siteOnBBGridAdapter);
        homeScreenGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0) {
                    ReadingSmpsFragment readingSmpsFragment = new ReadingSmpsFragment();
                    openBasicDataFragment(readingSmpsFragment, "Reading on SMPS Screen");
                } else if (position == 1) {
                    ClampMeterFragment clampMeterFragment = new ClampMeterFragment();
                    openBasicDataFragment(clampMeterFragment, "Clamp Meter");
                } else {
                    OperatorNameFragment operatorNameFragment = new OperatorNameFragment();
                    openBasicDataFragment(operatorNameFragment, "Operator Details");
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addMoreItem) {
            count++;
            gridviewItemList.add(gridviewItemList.size(), "Operator" + " " + count + " " + "Name");
            siteOnBBGridAdapter.notifyDataSetChanged();
            //     String addedItemText = gridviewItemList.get(gridviewItemList.size() - 1);
        }
    }

    //open BasicDataFragment
    private void openBasicDataFragment(MainFragment fragment, String headertext) {
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", headertext);
        bundle.putBoolean("showMenuButton", false);
        ApplicationHelper.application().getActivity().updateFragment(fragment, bundle);

    }
}
