package com.telecom.ast.sitesurvey.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNTileView;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.AirConditionerFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.BTSFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.BasicDataFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.BatteryFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.DGFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.EBMeterFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.MiscellaneousFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.MpptFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SiteOnBBFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SmpsMainFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SolarPanelsFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.TowerFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by AST on 23-01-2017.
 */

public class SiteOnBBGridAdapter extends BaseAdapter {
    private Context context;
    // Populate a List from Array elements
    final List<String> gridviewItem;

    public SiteOnBBGridAdapter(Context context, List<String> gridviewItem) {
        this.context = context;
        this.gridviewItem = gridviewItem;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        gridView = new View(context);
        gridView = inflater.inflate(R.layout.fe_home_grid_item, null);
        FNTileView llGridItem = gridView.findViewById(R.id.customerTile);
        llGridItem.setTitle(gridviewItem.get(position));

        return gridView;
    }

    @Override
    public int getCount() {
        return gridviewItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    //open BasicDataFragment
    private void openBasicDataFragment(MainFragment fragment, String headertext) {
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", headertext);
        bundle.putBoolean("showMenuButton", false);
        ApplicationHelper.application().getActivity().updateFragment(fragment, bundle);

    }
}