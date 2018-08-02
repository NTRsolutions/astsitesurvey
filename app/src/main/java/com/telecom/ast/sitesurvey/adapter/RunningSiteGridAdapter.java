package com.telecom.ast.sitesurvey.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNTileView;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SetOnEBFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SiteOnBBFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SiteOnDgSetFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SiteOnSolarFragment;

import java.util.Random;

/**
 * Created by AST on 23-01-2017.
 */

public class RunningSiteGridAdapter extends BaseAdapter {
    private Context context;

    public RunningSiteGridAdapter(Context context) {
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        gridView = new View(context);
        gridView = inflater.inflate(R.layout.fe_home_grid_item, null);
        FNTileView llGridItem = gridView.findViewById(R.id.customerTile);
        if (position == 0) {
            llGridItem.setTitle("SITE ON BB");
            llGridItem.setImageResource(R.drawable.ic_battery_with_positive_and_negative_poles_symbols);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#FF00FF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SiteOnBBFragment siteOnbbFragment = new SiteOnBBFragment();
                    openBasicDataFragment(siteOnbbFragment, "SITE ON BB");

                }
            });
        } else if (position == 1) {
            llGridItem.setTitle("SITE ON DG");
            llGridItem.setImageResource(R.drawable.ic_generator);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#7F00FF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SiteOnDgSetFragment runningSideDGFragment = new SiteOnDgSetFragment();
                    openBasicDataFragment(runningSideDGFragment, "SITE ON DG");

                }
            });
        } else if (position == 2) {
            llGridItem.setTitle("SITE ON EB");
            llGridItem.setImageResource(R.drawable.ic_electric_meter);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#007FFF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SetOnEBFragment setOnEBFragment = new SetOnEBFragment();
                    openBasicDataFragment(setOnEBFragment, "SITE ON EB");
                }
            });
        } else if (position == 3) {
            llGridItem.setTitle("SITE ON SOLAR");
            llGridItem.setImageResource(R.drawable.ic_battery_charging_with_solar_panel);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#FF007F"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SiteOnSolarFragment siteOnSolarFragment = new SiteOnSolarFragment();
                    openBasicDataFragment(siteOnSolarFragment, "SITE ON SOLAR");

                }
            });

        }
        return gridView;
    }

    @Override
    public int getCount() {
        return 4;
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