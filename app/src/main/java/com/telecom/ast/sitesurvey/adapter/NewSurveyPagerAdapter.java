package com.telecom.ast.sitesurvey.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.AirConditionerFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.BasicDataFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.BatteryFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.DGFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.EBMeterFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.PIUVoltageStablizerFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SetOnEBFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SiteOnBatteryBankFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SiteOnDgSetFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SmpsFragment;

public class NewSurveyPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfPage = 11;

    public NewSurveyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BasicDataFragment tab1 = new BasicDataFragment();
                return tab1;
            case 1:
                BatteryFragment tab2 = new BatteryFragment();
                return tab2;
            case 2:
                DGFragment tab3 = new DGFragment();
                return tab3;
            case 3:
                AirConditionerFragment tab4 = new AirConditionerFragment();
                return tab4;
            case 4:
                SmpsFragment tab5 = new SmpsFragment();
                return tab5;
            case 5:
                PIUVoltageStablizerFragment tab6 = new PIUVoltageStablizerFragment();
                return tab6;
            case 6:
                EBMeterFragment tab7 = new EBMeterFragment();
                return tab7;
            case 7:
                SiteOnBatteryBankFragment tab8 = new SiteOnBatteryBankFragment();
                return tab8;
            case 8:
                SiteOnDgSetFragment tab9 = new SiteOnDgSetFragment();
                return tab9;
            case 9:
                SetOnEBFragment tab10 = new SetOnEBFragment();
                return tab10;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfPage;
    }
}

