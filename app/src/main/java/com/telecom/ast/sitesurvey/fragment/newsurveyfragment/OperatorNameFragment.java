package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.component.FNTextView;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class OperatorNameFragment extends MainFragment {
    private LinearLayout mContainerView;
    private Button mAddButton, btnSubmit;
    private View mExclusiveEmptyView;
    FNEditText etbtsOperator, etNofCab, etbtscurrent, etbtscurrentbtSide, etbtsvVoltagebtsside, etbtsvollatagesmpsside;
    Spinner btstyelSpinner;
    String btsOperator, NofCab, btscurrentbtSide, btscurrent, btsvVoltagebtsside, btsvollatagesmpsside;
    String strbtsOperator, strNofCab, strbtscurrentbtSide, strbtscurrent, strbtsvVoltagebtsside, strbtsvollatagesmpsside;
    SharedPreferences pref;


    @Override
    protected int fragmentLayout() {
        return R.layout.operator_main;
    }

    @Override
    protected void loadView() {
        etbtsOperator = this.findViewById(R.id.etbtsOperator);
        etNofCab = this.findViewById(R.id.etNofCab);
        etbtscurrent = this.findViewById(R.id.btscurrent);
        etbtscurrentbtSide = this.findViewById(R.id.btscurrentbtSide);
        etbtsvVoltagebtsside = this.findViewById(R.id.btsvVoltagebtsside);
        etbtsvollatagesmpsside = this.findViewById(R.id.btsvollatagesmpsside);
        btstyelSpinner = this.findViewById(R.id.btstye);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getSharedPrefSaveData();
        if (!isEmptyStr(strbtsOperator) ||!isEmptyStr(strNofCab) || !isEmptyStr(strbtscurrentbtSide)
                || !isEmptyStr(strbtscurrent)
                | !isEmptyStr(strbtsvVoltagebtsside)
                |!isEmptyStr(strbtsvollatagesmpsside)) {
            etbtsOperator.setText(strbtsOperator);
            etNofCab.setText(strNofCab);
            etbtscurrent.setText(strbtscurrentbtSide);
            etbtscurrentbtSide.setText(strbtscurrent);
            etbtsvVoltagebtsside.setText(strbtsvVoltagebtsside);
            etbtsvollatagesmpsside.setText(strbtsvollatagesmpsside);
        }
    }

    /*
     *
     * get Data in Shared Pref.
     */

    public void getSharedPrefSaveData() {
    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addBts) {
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("strbtsOperator", strbtsOperator);
                editor.putString("strNofCab", strNofCab);
                editor.putString("strbtscurrentbtSide", strbtscurrentbtSide);
                editor.putString("strbtscurrent", strbtscurrent);
                editor.putString("strbtsvVoltagebtsside", strbtsvVoltagebtsside);
                editor.putString("strbtsvollatagesmpsside", strbtsvollatagesmpsside);
                editor.commit();

            }
        }
    }

    public boolean isValidate() {
        strbtsOperator = getTextFromView(this.etbtsOperator);
        strNofCab = getTextFromView(this.etNofCab);
        strbtscurrentbtSide = getTextFromView(this.etbtscurrent);
        strbtscurrent = getTextFromView(this.etbtscurrentbtSide);
        strbtsvVoltagebtsside = getTextFromView(this.etbtsvVoltagebtsside);
        strbtsvollatagesmpsside = getTextFromView(this.etbtsvollatagesmpsside);

        if (isEmptyStr(strbtsOperator)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No ");
            return false;
        } else if (isEmptyStr(strNofCab)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter ");
            return false;
        } else if (isEmptyStr(strbtscurrentbtSide)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter ");
            return false;
        } else if (isEmptyStr(strbtscurrent)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter ");
            return false;
        } else if (isEmptyStr(strbtsvVoltagebtsside)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No ");
            return false;
        } else if (isEmptyStr(strbtsvollatagesmpsside)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No ");
            return false;
        }
        return true;
    }
}
