package com.telecom.ast.sitesurvey.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.ArrayList;

public class SMPSPIUPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfPage = 2;

    public SMPSPIUPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
               // SmpsFragment tab1 = new SmpsFragment();
               // return tab1;
            case 1:
                //PIUVoltageStablizerFragment tab2 = new PIUVoltageStablizerFragment();
               // return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfPage;
    }



}

