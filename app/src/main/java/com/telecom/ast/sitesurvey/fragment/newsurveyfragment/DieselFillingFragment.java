package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class DieselFillingFragment extends MainFragment {

    AppCompatEditText etLPD, etQualityDiesel, etFillingother, etDieselfuillDone;
    String LPD, QualityDiesel, Fillingother, DieselfuillDone;
    String strLPD, strQualityDiesel, strFillingother, strDieselfuillDone;
    Button btnSubmit;
    SharedPreferences pref;

    @Override
    protected int fragmentLayout() {
        return R.layout.dieselfilling_fragment;
    }

    @Override
    protected void loadView() {
        etLPD = this.findViewById(R.id.etNoEarthPits);
        etQualityDiesel = this.findViewById(R.id.etEarthpitsconnected);
        etFillingother = this.findViewById(R.id.etinterConEarthPits);
        etDieselfuillDone = this.findViewById(R.id.etVoltageEarth);
        btnSubmit = this.findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getSharedPrefData();
        if (!strLPD.equals("") || !strQualityDiesel.equals("") || !strFillingother.equals("")
                || !strDieselfuillDone.equals("")
                ) {
            etLPD.setText(strLPD);
            etQualityDiesel.setText(strQualityDiesel);
            etFillingother.setText(strFillingother);
            etDieselfuillDone.setText(strDieselfuillDone);
        }
    }

    /*
     *
     * Shared Prefrences---------------------------------------
     */
    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strLPD = pref.getString("strLPD", "");
        strQualityDiesel = pref.getString("strQualityDiesel", "");
        strFillingother = pref.getString("strFillingother", "");
        strDieselfuillDone = pref.getString("strDieselfuillDone", "");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("strLPD", LPD);
                editor.putString("strQualityDiesel", QualityDiesel);
                editor.putString("strFillingother", Fillingother);
                editor.putString("strDieselfuillDone", DieselfuillDone);
                editor.commit();
            }

        }
    }

    public boolean isValidate() {
        LPD = etLPD.getText().toString();
        QualityDiesel = etQualityDiesel.getText().toString();
        Fillingother = etFillingother.getText().toString();
        DieselfuillDone = etDieselfuillDone.getText().toString();
        if (isEmptyStr(LPD)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter LPD");
            return false;
        } else if (isEmptyStr(QualityDiesel)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Quality of Diesel");
            return false;
        } else if (isEmptyStr(Fillingother)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Filling In DG Tank or Any Other ");
            return false;
        } else if (isEmptyStr(DieselfuillDone)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Diesel Filling Done by CareTaker/Technician/Filler");
            return false;
        }

        return true;
    }

}