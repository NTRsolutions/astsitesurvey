package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.widget.GridView;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.adapter.SiteSurevyGridAdapter;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

public class StaticSiteFragment extends MainFragment {
    GridView homeScreenGrid;
    ASTUIUtil commonFunctions;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void loadView() {
        this.homeScreenGrid = findViewById(R.id.homeScreenGrid);
    }

    @Override
    protected void setClickListeners() {
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        this.commonFunctions = new ASTUIUtil();
        setAdaptor();

    }


    private void setAdaptor() {
        this.homeScreenGrid.setAdapter(new SiteSurevyGridAdapter(getContext()));
    }
}