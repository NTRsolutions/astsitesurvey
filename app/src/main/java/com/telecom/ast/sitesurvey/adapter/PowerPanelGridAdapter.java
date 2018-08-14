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

public class PowerPanelGridAdapter extends BaseAdapter {
    private Context context;
    final List<String> gridviewItem;

    public PowerPanelGridAdapter(Context context, List<String> gridviewItem) {
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


}