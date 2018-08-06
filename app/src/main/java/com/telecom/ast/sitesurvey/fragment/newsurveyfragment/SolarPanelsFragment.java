package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.model.CircleMasterDataModel;
import com.telecom.ast.sitesurvey.model.DistrictMasterDataModel;
import com.telecom.ast.sitesurvey.model.SSAmasterDataModel;
import com.telecom.ast.sitesurvey.model.SiteMasterDataModel;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class SolarPanelsFragment extends MainFragment {
    AppCompatEditText etNoofPanel, etMake, etcapacitypanel, etNoofAGb;
    Button btnSubmit;
    ASTUIUtil commonFunctions;
    SharedPreferences pref;
    String NoofPanel, Make, capacitypanel, noOfAgb;
    String strNoofPanel, serMake, strcapacitypanel, strAgb;
    Spinner itemStatusSpineer;

    @Override
    protected int fragmentLayout() {
        return R.layout.solarpanel_fragment;
    }

    @Override
    protected void loadView() {
        this.etNoofPanel = findViewById(R.id.etNoofPanel);
        this.etMake = findViewById(R.id.etMake);
        this.etcapacitypanel = findViewById(R.id.etcapacitypanel);
        this.etNoofAGb = findViewById(R.id.etNoofAGb);
        this.btnSubmit = findViewById(R.id.btnSubmit);
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
        getSharedPrefSaveData();
        setSpinnerValue();
        if (!strNoofPanel.equals("") || !serMake.equals("") || !strcapacitypanel.equals("") || !strAgb.equals("")) {
            etNoofPanel.setText(strNoofPanel);
            etMake.setText(serMake);
            etcapacitypanel.setText(strcapacitypanel);
            etNoofAGb.setText(strAgb);
        }

        itemStatusSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Not Available")) {
                    etNoofPanel.setEnabled(false);
                    etMake.setEnabled(false);
                    etcapacitypanel.setEnabled(false);
                    etNoofAGb.setEnabled(false);
                } else {
                    etNoofPanel.setEnabled(true);
                    etMake.setEnabled(true);
                    etcapacitypanel.setEnabled(true);
                    etNoofAGb.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setSpinnerValue() {
        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);
    }
    /*
     *
     * get Data in Shared Pref.
     */

    public void getSharedPrefSaveData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strNoofPanel = pref.getString("SOLARPAN_NoofPanel", "");
        serMake = pref.getString("SOLARPAN_Make", "");
        strcapacitypanel = pref.getString("SOLARPAN_capacitypanel", "");
        strAgb = pref.getString("SOLARPAN_noOfAgb", "");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("SOLARPAN_NoofPanel", NoofPanel);
                editor.putString("SOLARPAN_Make", Make);
                editor.putString("SOLARPAN_capacitypanel", capacitypanel);
                editor.putString("SOLARPAN_noOfAgb", noOfAgb);
                editor.commit();

            }
        }
    }


    public boolean isValidate() {
        NoofPanel = getTextFromView(this.etNoofPanel);
        Make = getTextFromView(this.etMake);
        capacitypanel = getTextFromView(this.etcapacitypanel);
        noOfAgb = getTextFromView(this.etNoofAGb);
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
            if (isEmptyStr(NoofPanel)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of Panel");
                return false;
            } else if (isEmptyStr(Make)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Make");
                return false;
            } else if (isEmptyStr(capacitypanel)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Capacity panel");
                return false;
            } else if (isEmptyStr(noOfAgb)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of AGB");
                return false;
            }
        } else {
            ASTUIUtil.showToast("Item Not Available");
        }
        return true;
    }
}
