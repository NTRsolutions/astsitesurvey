package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class SiteOnBatteryBankFragment extends MainFragment {
    FNEditText etCurrent, etVoltage;
    TextView imgPreious, imgNext;
    SharedPreferences pref;
    String strVoltage, strCurrent, strSavedDateTime, strUserId;
    LinearLayout perviousLayout, nextLayout;

    @Override
    protected int fragmentLayout() {
        return R.layout.activity_site_on_battery_bank;
    }

    @Override
    protected void loadView() {
        etCurrent = findViewById(R.id.etCurrent);
        etVoltage = findViewById(R.id.etVoltage);
        imgPreious = findViewById(R.id.imgPrevious);
        imgNext = findViewById(R.id.imgNext);
        this.nextLayout = findViewById(R.id.nextLayout);
        this.perviousLayout = findViewById(R.id.nextLayout);
    }

    @Override
    protected void setClickListeners() {
        imgPreious.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        nextLayout.setOnClickListener(this);
        perviousLayout.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        strVoltage = pref.getString("Voltage", "");
        strCurrent = pref.getString("Current", "");
        strSavedDateTime = pref.getString("SiteOnBBSavedDateTime", "");
        strUserId = pref.getString("USER_ID", "");
        if (!(isEmptyStr(strVoltage)) || !(isEmptyStr(strCurrent))) {
            etCurrent.setText(strCurrent);
            etVoltage.setText(strVoltage);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgNext || view.getId() == R.id.nextLayout) {
            String current = etCurrent.getText().toString().trim();
            String voltage = etVoltage.getText().toString().trim();
            if (isEmptyStr(current)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Current");
            } else if (isEmptyStr(voltage)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Provide Voltage");
            } else {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("UserId", strUserId);
                editor.putString("Voltage", voltage);
                editor.putString("Current", current);
                editor.putString("SiteOnBBSavedDateTime", strSavedDateTime);
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
