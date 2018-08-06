package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
    Spinner itemStatusSpineer;

    @Override
    protected int fragmentLayout() {
        return R.layout.dieselfilling_fragment;
    }

    @Override
    protected void loadView() {
        etLPD = this.findViewById(R.id.etLPD);
        etQualityDiesel = this.findViewById(R.id.etQualityDiesel);
        etFillingother = this.findViewById(R.id.etFillingother);
        etDieselfuillDone = this.findViewById(R.id.etDieselfuillDone);
        btnSubmit = this.findViewById(R.id.btnSubmit);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getSharedPrefData();
        setSpinnerValue();
        if (!strLPD.equals("") || !strQualityDiesel.equals("") || !strFillingother.equals("")
                || !strDieselfuillDone.equals("")
                ) {
            etLPD.setText(strLPD);
            etQualityDiesel.setText(strQualityDiesel);
            etFillingother.setText(strFillingother);
            etDieselfuillDone.setText(strDieselfuillDone);
        }
        itemStatusSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Not Available")) {
                    etLPD.setEnabled(false);
                    etQualityDiesel.setEnabled(false);
                    etFillingother.setEnabled(false);
                    etDieselfuillDone.setEnabled(false);
                } else {
                    etLPD.setEnabled(true);
                    etQualityDiesel.setEnabled(true);
                    etFillingother.setEnabled(true);
                    etDieselfuillDone.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /*
     *
     * Shared Prefrences---------------------------------------
     */
    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strLPD = pref.getString("DIESEL_strLPD", "");
        strQualityDiesel = pref.getString("DIESEL_strQualityDiesel", "");
        strFillingother = pref.getString("DIESEL_strFillingother", "");
        strDieselfuillDone = pref.getString("DIESEL_strDieselfuillDone", "");
    }


    public void setSpinnerValue() {
        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("DIESEL_strLPD", LPD);
                editor.putString("DIESEL_strQualityDiesel", QualityDiesel);
                editor.putString("DIESEL_strFillingother", Fillingother);
                editor.putString("DIESEL_strDieselfuillDone", DieselfuillDone);
                editor.commit();
            }

        }
    }

    public boolean isValidate() {
        LPD = etLPD.getText().toString();
        QualityDiesel = etQualityDiesel.getText().toString();
        Fillingother = etFillingother.getText().toString();
        DieselfuillDone = etDieselfuillDone.getText().toString();
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
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
        } else {
            ASTUIUtil.showToast("Item Not Available");
        }

        return true;
    }


}