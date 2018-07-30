package com.telecom.ast.sitesurvey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.PIUVoltageStablizerFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SmpsFragment;

public class SMPSPIUPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfPage = 2;

    public SMPSPIUPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SmpsFragment tab1 = new SmpsFragment();
                return tab1;
            case 1:
                PIUVoltageStablizerFragment tab2 = new PIUVoltageStablizerFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfPage;
    }
}

