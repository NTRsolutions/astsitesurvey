package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.adapter.PowerPanelGridAdapter;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PowerPlantFragment extends MainFragment {
    PowerPanelGridAdapter powerPanelGridAdapter;
    GridView homeScreenGrid;
    List<String> gridviewItemList;
    SharedPreferences piuenable;
    int count = 1;
    // Initializing a new String Array
    static final String[] gridviewItem = new String[]{
            "SMPS", "IPMS/PIU/AMF Panel"};


    @Override
    protected int fragmentLayout() {
        return R.layout.sitebbgrid_home;
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
        gridviewItemList = new ArrayList<String>(Arrays.asList(gridviewItem));
        this.powerPanelGridAdapter = new PowerPanelGridAdapter(getContext(), gridviewItemList);
        setAdaptor();

    }


    private void setAdaptor() {
        this.homeScreenGrid.setAdapter(powerPanelGridAdapter);
        homeScreenGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0) {
                    SmpsFragment smpsFragment = new SmpsFragment();
                    openBasicDataFragment(smpsFragment, "SMPS");
              /*  } else if (position == 1) {
                    IpmsFragment ipmsFragment = new IpmsFragment();
                    openBasicDataFragment(ipmsFragment, "");
                */
                } else if (position == 1) {
                    piuenable = getContext().getSharedPreferences("PiuenablePref", MODE_PRIVATE);
                    boolean isenablePiu = piuenable.getBoolean("PIU_ENABLE", false);
                    if (isenablePiu) {
                        PIUVoltageStablizerFragment piuVoltageStablizerFragment = new PIUVoltageStablizerFragment();
                        openBasicDataFragment(piuVoltageStablizerFragment, "IPMS/PIU/AMF Panel");
                    } else {
                        ASTUIUtil.showToast("Please Fill and Submitted  SMPS data information First");
                    }
                }
            }
        });


    }


    //open BasicDataFragment
    private void openBasicDataFragment(MainFragment fragment, String headertext) {
        Bundle bundle = new Bundle();
        bundle.putString("headerTxt", headertext);
        bundle.putBoolean("showMenuButton", false);
        ApplicationHelper.application().getActivity().updateFragment(fragment, bundle);

    }

    /*@Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            onCaptureImageResult();
        }
    }*/

   /* @Override
    protected int fragmentLayout() {
        return R.layout.sitesurvey_tav_fragment;
    }

    @Override
    protected void getArgs() {
    }

    @Override
    protected void loadView() {
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabs);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new SmpsPiuFragment(), "SMPS");
        adapter.addFragment(new IpmsFragment(), "IPMS");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    *//**
     * Adapter for the viewpager using FragmentPagerAdapter
     *//*
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<MainFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public MainFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(MainFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    *//**
     * THIS USE an ActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     *//*
    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FNReqResCode.ATTACHMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FNFilePicker.EXTRA_SELECTED_MEDIA);
         //  SmpsFragment.getResult(files);

           MainFragment fnFragment = null;
            if (getParentFragment()  instanceof SmpsFragment) {
                SmpsFragment.getResult(files);
            } else if (getParentFragment() instanceof PIUVoltageStablizerFragment) {
                PIUVoltageStablizerFragment.getPickedFiles(files);

            } else if (getParentFragment()  instanceof IpmsFragment) {
                IpmsFragment.getPickedFiles(files);

            }

        }
    }*/
}