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
import com.telecom.ast.sitesurvey.model.BtsInfoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AST on 23-01-2017.
 */

public class SiteOnBBGridAdapter extends BaseAdapter {
    private Context context;
    // Populate a List from Array elements
    ArrayList<BtsInfoData> btsInfoDataList;

    public SiteOnBBGridAdapter(Context context, ArrayList<BtsInfoData> btsInfoDataList) {
        this.context = context;
        this.btsInfoDataList = btsInfoDataList;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        gridView = new View(context);
        gridView = inflater.inflate(R.layout.fe_home_grid_item, null);
        FNTileView llGridItem = gridView.findViewById(R.id.customerTile);
        llGridItem.setTitle(btsInfoDataList.get(position).getName());
        if (position == 0) {
            llGridItem.setImageCircleColor(false);
            llGridItem.setImageResource(R.drawable.ic_light);
        } else  {
            llGridItem.setImageCircleColor(false);
            llGridItem.setImageResource(R.drawable.ic_support);
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return btsInfoDataList.size();
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