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
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.InputAlarmPanelFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.MiscElectricalEquiFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.MiscellaneousFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.MpptFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.PowerPlantFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.SolarPanelsFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.TowerFragment;
import com.telecom.ast.sitesurvey.fragment.newsurveyfragment.surveyIntcallback.FireSystemFragment;

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
        if (position == 0) {
            llGridItem.setTitle(R.string.basidatatxt);
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_user);
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
            llGridItem.setTitle(R.string.batryinfo);
            llGridItem.setTitleBgColor(R.color.bg_color);
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
            llGridItem.setTitle(R.string.DG);
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_generator);
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
            llGridItem.setTitleBgColor(R.color.bg_color);
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
            llGridItem.setTitle("Power Plant");
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_power);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#7F00FF"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PowerPlantFragment smpsFragment = new PowerPlantFragment();
                    openBasicDataFragment(smpsFragment, "Power Plant");

                }
            });
        } else if (position == 5) {
            llGridItem.setTitle(R.string.EB_METER);
            llGridItem.setTitleBgColor(R.color.bg_color);
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
            llGridItem.setTitle(R.string.MPPT);
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_electricity);
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
            llGridItem.setTitle(R.string.Tower);
            llGridItem.setTitleBgColor(R.color.bg_color);
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
            llGridItem.setTitle(R.string.SolarPanels);
            llGridItem.setTitleBgColor(R.color.bg_color);
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
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_transmitter);
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
            llGridItem.setTitle("Fire System");
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_fire_extinguishe);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#CE2E22"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FireSystemFragment fireBaseFragment = new FireSystemFragment();
                    openBasicDataFragment(fireBaseFragment, "Fire System");

                }
            });
        } else if (position == 11) {
            llGridItem.setTitle("Input Alarm Panel");
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_alarm);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#0099CC"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputAlarmPanelFragment inputAlarmPanelFragment = new InputAlarmPanelFragment();
                    openBasicDataFragment(inputAlarmPanelFragment, "Input Alarm Panel");

                }
            });


        } else if (position == 12) {
            llGridItem.setTitle("Earthing System");
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_fire_extinguishe);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#CE2E22"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FireSystemFragment fireBaseFragment = new FireSystemFragment();
                    openBasicDataFragment(fireBaseFragment, "Earthing System");

                }
            });
        } else if (position == 13) {
            llGridItem.setTitle("Diesel Filling");
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_alarm);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#0099CC"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputAlarmPanelFragment inputAlarmPanelFragment = new InputAlarmPanelFragment();
                    openBasicDataFragment(inputAlarmPanelFragment, "Diesel Filling");

                }
            });
        } else if (position == 14) {
            llGridItem.setTitle("Misc Electrical Equipment");
            llGridItem.setTitleBgColor(R.color.bg_color);
            llGridItem.setImageResource(R.drawable.ic_settings);
            llGridItem.hideCountField();
            llGridItem.setCardViewBg(Color.parseColor("#fd5969"));
            llGridItem.setTitleColor(R.color.black);
            llGridItem.setImageCircleColor(false);
            llGridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MiscElectricalEquiFragment miscElectricalEquiFragment = new MiscElectricalEquiFragment();
                    openBasicDataFragment(miscElectricalEquiFragment, "Misc Electrical Equipment");

                }
            });
        } else if (position == 15) {
            llGridItem.setTitle("Miscellaneous Items");
            llGridItem.setTitleBgColor(R.color.bg_color);
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
        return 16;
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