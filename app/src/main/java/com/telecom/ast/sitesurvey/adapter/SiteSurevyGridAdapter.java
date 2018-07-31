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
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.AirConditionerFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.BTSFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.BasicDataFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.BatteryFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.DGFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.EBMeterFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.MiscellaneousFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.MpptFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.PIUVoltageStablizerFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SmpsFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SmpsMainFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SolarPanelsFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.TowerFragment;

import java.util.Random;

/**
 * Created by AST on 23-01-2017.
 */

public class SiteSurevyGridAdapter extends BaseAdapter {
    private Context context;

    public SiteSurevyGridAdapter(Context context) {
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        gridView = new View(context);
        gridView = inflater.inflate(R.layout.fe_home_grid_item, null);
        FNTileView llGridItem = gridView.findViewById(R.id.customerTile);
        int[] androidColors = ApplicationHelper.application().getResources().getIntArray(R.array.androidcolors);
        // int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        //      llGridItem.setCardViewBg(randomAndroidColor);
        if (position == 0) {
            llGridItem.setTitle(R.string.basidatatxt);
            llGridItem.setImageResource(R.drawable.new_survey);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#007FFF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BasicDataFragment basicDataFragment = new BasicDataFragment();
                    openBasicDataFragment(basicDataFragment, "BASIC DATA ");

                }
            });
        } else if (position == 1) {
            llGridItem.setTitle("BATTERY ");
            llGridItem.setImageResource(R.drawable.ic_battery_with_positive_and_negative_poles_symbols);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#FF00FF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BatteryFragment batteryFragment = new BatteryFragment();
                    openBasicDataFragment(batteryFragment, "BATTERY ");

                }
            });
        } else if (position == 2) {
            llGridItem.setTitle("DG");
            llGridItem.setImageResource(R.drawable.new_survey);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#FF007F"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DGFragment dgFragment = new DGFragment();
                    openBasicDataFragment(dgFragment, "DG");
                }
            });
        } else if (position == 3) {
            llGridItem.setTitle("AC");
            llGridItem.setImageResource(R.drawable.ic_air_conditioner);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#007FFF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AirConditionerFragment airConditionerFragment = new AirConditionerFragment();
                    openBasicDataFragment(airConditionerFragment, "AC");

                }
            });
        } else if (position == 4) {
            llGridItem.setTitle("SMPS");
            llGridItem.setImageResource(R.drawable.new_survey);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#7F00FF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SmpsMainFragment smpsFragment = new SmpsMainFragment();
                    openBasicDataFragment(smpsFragment, "SMPS");

                }
            });
        } else if (position == 5) {
            llGridItem.setTitle("EB METER");
            llGridItem.setImageResource(R.drawable.ic_electric_meter);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#FF00FF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EBMeterFragment ebMeterFragment = new EBMeterFragment();
                    openBasicDataFragment(ebMeterFragment, "EB METER");

                }
            });
        } else if (position == 6) {
            llGridItem.setTitle("MPPT");
            llGridItem.setImageResource(R.drawable.new_survey);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#198719"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MpptFragment mpptFragment = new MpptFragment();
                    openBasicDataFragment(mpptFragment, "MPPT");

                }
            });
        } else if (position == 7) {
            llGridItem.setTitle("Tower");
            llGridItem.setImageResource(R.drawable.ic_antenna);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#0867CB"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TowerFragment towerFragment = new TowerFragment();
                    openBasicDataFragment(towerFragment, "Tower");

                }
            });
        } else if (position == 8) {
            llGridItem.setTitle("Solar Panels");
            llGridItem.setImageResource(R.drawable.ic_solar_panel);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#007FFF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SolarPanelsFragment solarPanelsFragment = new SolarPanelsFragment();
                    openBasicDataFragment(solarPanelsFragment, "Solar Panels");

                }
            });
        } else if (position == 9) {
            llGridItem.setTitle("BTS");
            llGridItem.setImageResource(R.drawable.new_survey);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#7F00FF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BTSFragment btsFragment = new BTSFragment();
                    openBasicDataFragment(btsFragment, "BTS");

                }
            });
        } else if (position == 10) {
            llGridItem.setTitle("Miscellaneous Items");
            llGridItem.setImageResource(R.drawable.ic_briefcase);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#FF007F"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MiscellaneousFragment miscellaneousFragment = new MiscellaneousFragment();
                    openBasicDataFragment(miscellaneousFragment, "Miscellaneous Items");

                }
            });
        }
        return gridView;
    }

    @Override
    public int getCount() {
        return 11;
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