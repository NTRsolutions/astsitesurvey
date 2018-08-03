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

import java.util.List;

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
        if (position == 0) {
            llGridItem.setImageCircleColor(false);
            llGridItem.setImageResource(R.drawable.ic_gauge);
        } else if (position == 1) {
            llGridItem.setImageResource(R.drawable.ic_gauge);
            llGridItem.setImageCircleColor(false);


        } else if (position == 2) {
            llGridItem.setImageCircleColor(false);
            llGridItem.setImageResource(R.drawable.ic_gauge);
        } else {
            llGridItem.setImageCircleColor(false);
            llGridItem.setImageResource(R.drawable.ic_gauge);
        }

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