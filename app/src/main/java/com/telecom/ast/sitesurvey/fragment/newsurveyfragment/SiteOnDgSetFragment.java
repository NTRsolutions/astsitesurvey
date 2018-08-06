package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class SiteOnDgSetFragment extends MainFragment {
    AppCompatEditText etDGCurrent, etDGFrequency, etDGVoltage, etBatChangeCurrent, etBatteryVoltage;
    SharedPreferences pref;
    String strDgCurrent, strDgFrequency, strDgVoltage, strBatteryChargeCurrent, strBatteryVoltage;
    String strUserId, strSavedDateTime;
    Button btnSubmit;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_site_on_dg_set;
    }

    @Override
    protected void loadView() {
        etDGCurrent = findViewById(R.id.etDGCurrent);
        etDGFrequency = findViewById(R.id.etDGFrequency);
        etDGVoltage = findViewById(R.id.etDGVoltage);
        etBatChangeCurrent = findViewById(R.id.etBatChangeCurrent);
        etBatteryVoltage = findViewById(R.id.etBatteryVoltage);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }


    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strDgCurrent = pref.getString("DgCurrent", "");
        strDgFrequency = pref.getString("DgFrequency", "");
        strDgVoltage = pref.getString("DgVoltage", "");
        strBatteryChargeCurrent = pref.getString("BatteryChargeCurrent", "");
        strBatteryVoltage = pref.getString("BatteryVoltage", "");
        strUserId = pref.getString("USER_ID", "");
        strSavedDateTime = pref.getString("SetOnDGSavedDateTime", "");
    }

    @Override
    protected void dataToView() {
        getSharedPrefData();
        if (!strDgCurrent.equals("") || !strDgFrequency.equals("") || !strDgVoltage.equals("")
                || !strBatteryChargeCurrent.equals("") || !strBatteryVoltage.equals("")) {
            etDGCurrent.setText(strDgCurrent);
            etDGFrequency.setText(strDgFrequency);
            etDGVoltage.setText(strDgVoltage);
            etBatChangeCurrent.setText(strBatteryChargeCurrent);
            etBatteryVoltage.setText(strBatteryVoltage);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            String dgCurrent = etDGCurrent.getText().toString().trim();
            String dgFrequency = etDGFrequency.getText().toString().trim();
            String dgVoltage = etDGVoltage.getText().toString().trim();
            String batteryChargeCurrent = etBatChangeCurrent.getText().toString().trim();
            String batteryVoltage = etBatteryVoltage.getText().toString().trim();
            if (isEmptyStr(dgCurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Current");
            } else if (isEmptyStr(dgFrequency)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Frequency");
            } else if (isEmptyStr(dgVoltage)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Voltage");
            } else if (isEmptyStr(batteryChargeCurrent)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Battery Charge Current");
            } else if (isEmptyStr(batteryVoltage)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Battery Voltage");
            } else {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("UserId", strUserId);
                editor.putString("DgCurrent", dgCurrent);
                editor.putString("DgFrequency", dgFrequency);
                editor.putString("DgVoltage", dgVoltage);
                editor.putString("BatteryChargeCurrent", batteryChargeCurrent);
                editor.putString("BatteryVoltage", batteryVoltage);
                editor.putString("SetOnDGSavedDateTime", strSavedDateTime);
                editor.commit();
                saveScreenData(true, false);

            }
        } else if (view.getId() == R.id.imgPrevious || view.getId() == R.id.perviousLayout) {
            saveScreenData(false, false);
        }

    }

    private void saveScreenData(boolean NextPreviousFlag, boolean DoneFlag) {
        Intent intent = new Intent("ViewPageChange");
        intent.putExtra("NextPreviousFlag", NextPreviousFlag);
        intent.putExtra("DoneFlag", DoneFlag);
        getActivity().sendBroadcast(intent);
    }
}
