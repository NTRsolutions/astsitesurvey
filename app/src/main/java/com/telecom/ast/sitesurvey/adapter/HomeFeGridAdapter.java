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
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.NewSurveyFragment;

/**
 * Created by AST on 23-01-2017.
 */

public class HomeFeGridAdapter extends BaseAdapter {
    private Context context;

    public HomeFeGridAdapter(Context context) {
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        gridView = new View(context);
        gridView = inflater.inflate(R.layout.fe_home_grid_item, null);
        FNTileView llGridItem = gridView.findViewById(R.id.customerTile);
        if (position == 0) {
            llGridItem.setTitle("New Survey");
            llGridItem.setImageResource(R.drawable.ic_profiles);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#078f4b"));
            llGridItem.setTitleColor(R.color.black_med_color);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openBasicDataFragment();

                }
            });
        }
        if (position == 1) {
            llGridItem.setTitle("Sync Surveys");
            llGridItem.setImageResource(R.drawable.ic_sync);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#2e4fc7"));
            llGridItem.setTitleColor(R.color.black_med_color);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openBasicDataFragment();

                }
            });
        } else if (position == 2) {
            llGridItem.setTitle("View Unsyced Surveys");
            llGridItem.setImageResource(R.drawable.new_survey);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#eb7f1a"));
            llGridItem.setTitleColor(R.color.black_med_color);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openBasicDataFragment();
                }
            });
        } else if (position == 3) {
            llGridItem.setTitle("View Synced Surveys");
            llGridItem.setImageResource(R.drawable.new_survey);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#a6241b"));
            llGridItem.setTitleColor(R.color.black_med_color);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openBasicDataFragment();

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
    private void openBasicDataFragment() {
        // BasicDataFragment basicDataFragment = new BasicDataFragment();
        NewSurveyFragment basicDataFragment = new NewSurveyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", "New Survey");
        bundle.putBoolean("showMenuButton", false);
        ApplicationHelper.application().getActivity().updateFragment(basicDataFragment, bundle);

    }
}