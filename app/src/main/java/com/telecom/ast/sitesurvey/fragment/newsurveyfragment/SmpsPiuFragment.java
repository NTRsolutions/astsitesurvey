package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.adapter.SMPSPIUPagerAdapter;
import com.telecom.ast.sitesurvey.component.SwitchViewPager;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;

import java.util.ArrayList;

public class SmpsPiuFragment extends MainFragment {
    SwitchViewPager mPager;

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_add_home;
    }

    @Override
    protected void loadView() {
        mPager = this.findViewById(R.id.viewPager_itemList);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        SMPSPIUPagerAdapter mAdapter = new SMPSPIUPagerAdapter(getActivity().getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(2);
    }

    //for geting next previous click action
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("ViewPageChange")) {
                boolean DoneFlag = intent.getBooleanExtra("DoneFlag", false);
                boolean NextPreviousFlag = intent.getBooleanExtra("NextPreviousFlag", false);
                if (NextPreviousFlag) {
                    int currentPage = mPager.getCurrentItem();
                    mPager.setCurrentItem(currentPage + 1, true);
                } else if (DoneFlag) {
                    ASTUIUtil.showToast("Done All Page");
                    getHostActivity().redirectToHomeMenu();
                } else {
                    int currentPagepre = mPager.getCurrentItem();
                    if (currentPagepre > 0) {
                        mPager.setCurrentItem(currentPagepre - 1, true);
                    }
                }

            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(receiver, new IntentFilter("ViewPageChange"));
    }

    @Override
    public void onDestroy() {
        if (receiver != null) {
            getContext().unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }


    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FNReqResCode.ATTACHMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FNFilePicker.EXTRA_SELECTED_MEDIA);

            int currentPagepre = mPager.getCurrentItem();
            if (currentPagepre == 0)
                SmpsFragment.getResult(files);
            else if (currentPagepre == 1) {
                PIUVoltageStablizerFragment.getResult(files);

            }


        }
    }

}
