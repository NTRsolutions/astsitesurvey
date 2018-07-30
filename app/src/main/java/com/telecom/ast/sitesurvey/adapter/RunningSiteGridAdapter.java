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
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SiteOnBBFragment;

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
        int[] androidColors = ApplicationHelper.application().getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        llGridItem.setCardViewBg(randomAndroidColor);
        if (position == 0) {
            llGridItem.setTitle("SITE ON BB");
            llGridItem.setImageResource(R.drawable.new_survey);
            llGridItem.hideCountField();
            //llGridItem.setCardViewBg(randomAndroidColor);
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
            llGridItem.setImageResource(R.drawable.ic_battery_with_positive_and_negative_poles_symbols);
            llGridItem.hideCountField();
            //  llGridItem.setCardViewBg(randomAndroidColor);
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //BatteryFragment batteryFragment = new BatteryFragment();
                    // openBasicDataFragment(batteryFragment, "SITE ON DG");

                }
            });
        } else if (position == 2) {
            llGridItem.setTitle("SITE ON EB");
            llGridItem.setImageResource(R.drawable.new_survey);
            llGridItem.hideCountField();
            //   llGridItem.setCardViewBg(randomAndroidColor);
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // DGFragment dgFragment = new DGFragment();
                    //openBasicDataFragment(dgFragment, "SITE ON EB");
                }
            });
        } else if (position == 3) {
            llGridItem.setTitle("SITE ON SOLAR");
            llGridItem.setImageResource(R.drawable.ic_air_conditioner);
            llGridItem.hideCountField();
            // llGridItem.setCardViewBg(randomAndroidColor);
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  AirConditionerFragment airConditionerFragment = new AirConditionerFragment();
                    //   openBasicDataFragment(airConditionerFragment, "SITE ON SOLAR");

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