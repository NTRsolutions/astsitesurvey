package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class FireSystemFragment extends MainFragment {

    Spinner etfiredetectSpineer, etextinguiserSpineer;
    Spinner itemStatusSpineer, etstatusSpinner;
    SharedPreferences pref;
    AutoCompleteTextView etMake, etCapacity;
    String strfiredetectSpineer, status, strextinguiserSpineer, stritemStatusSpineer, firedetectSpineer, extinguiserSpineer, itemStatus, make, capacity;
    LinearLayout fillEmptyLayout;
    Button btnSubmit;

    @Override
    protected int fragmentLayout() {
        return R.layout.firesystem_fragment;
    }

    @Override
    protected void loadView() {
        etfiredetectSpineer = this.findViewById(R.id.etfiredetectSpineer);
        etextinguiserSpineer = this.findViewById(R.id.etextinguiserSpineer);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
        etstatusSpinner = findViewById(R.id.etstatusSpinner);
        etMake = findViewById(R.id.etMake);
        etCapacity = findViewById(R.id.etCapacity);
        fillEmptyLayout = findViewById(R.id.fillEmptyLayout);
        btnSubmit = findViewById(R.id.btnSubmit);
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
        getSharedprefData();
        setSpinnerValue();


        itemStatusSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Not Available")) {
                    etfiredetectSpineer.setEnabled(false);
                    etextinguiserSpineer.setEnabled(false);
                } else {
                    etfiredetectSpineer.setEnabled(true);
                    etextinguiserSpineer.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etextinguiserSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Available")) {
                    fillEmptyLayout.setVisibility(View.VISIBLE);

                } else {
                    fillEmptyLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /*
     *
     *     Shared Prefrences
     */
    public void getSharedprefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strfiredetectSpineer = pref.getString("Fire_strfiredetectSpineer", "");
        strextinguiserSpineer = pref.getString("Fire_strextinguiserSpineer", "");
        stritemStatusSpineer = pref.getString("Fire_stritemStatusSpineer", "");
        status = pref.getString("FIRE_status", "");
        make = pref.getString("FIRE_make", "");
        capacity = pref.getString("FIRE_capacity", "");

    }


    public void setSpinnerValue() {
        final String etfiredetectSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> etfiredetect = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineer_array);
        etfiredetectSpineer.setAdapter(etfiredetect);
        if (strfiredetectSpineer != null && !strfiredetectSpineer.equals("")) {
            for (int i = 0; i < etfiredetectSpineer_array.length; i++) {
                if (strfiredetectSpineer.equalsIgnoreCase(etfiredetectSpineer_array[i])) {
                    etfiredetectSpineer.setSelection(i);
                } else {
                    etfiredetectSpineer.setSelection(0);
                }
            }
        }

        final String etextinguiserArray[] = {"Available", "Not Available"};
        ArrayAdapter<String> etextinguiser = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etextinguiserArray);
        etextinguiserSpineer.setAdapter(etextinguiser);
        if (strextinguiserSpineer != null && !strextinguiserSpineer.equals("")) {
            for (int i = 0; i < etextinguiserArray.length; i++) {
                if (strextinguiserSpineer.equalsIgnoreCase(etextinguiserArray[i])) {
                    etextinguiserSpineer.setSelection(i);
                } else {
                    etextinguiserSpineer.setSelection(0);
                }
            }
        }
        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);
        if (stritemStatusSpineer != null && !stritemStatusSpineer.equals("")) {
            for (int i = 0; i < itemStatusSpineer_array.length; i++) {
                if (stritemStatusSpineer.equalsIgnoreCase(itemStatusSpineer_array[i])) {
                    itemStatusSpineer.setSelection(i);
                } else {
                    itemStatusSpineer.setSelection(0);
                }
            }
        }
        final String etstatus_array[] = {"Filled ", "Empty"};
        ArrayAdapter<String> etstatusada = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etstatus_array);
        etstatusSpinner.setAdapter(etstatusada);
        if (status != null && !status.equals("")) {
            for (int i = 0; i < etstatus_array.length; i++) {
                if (status.equalsIgnoreCase(etstatus_array[i])) {
                    etstatusSpinner.setSelection(i);
                } else {
                    etstatusSpinner.setSelection(0);
                }
            }


        }

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("FIRE_strfiredetectSpineer", firedetectSpineer);
                editor.putString("FIRE_strextinguiserSpineer", firedetectSpineer);
                editor.putString("FIRE_stritemStatusSpineer", itemStatus);
                editor.putString("FIRE_status", status);
                editor.putString("FIRE_make", make);
                editor.putString("FIRE_capacity", capacity);
                editor.commit();
                reloadBackScreen();

            }

        }
    }


    // ----validation -----
    private boolean isValidate() {
        firedetectSpineer = etfiredetectSpineer.getSelectedItem().toString();
        extinguiserSpineer = etfiredetectSpineer.getSelectedItem().toString();
        itemStatus = itemStatusSpineer.getSelectedItem().toString();
        status = etstatusSpinner.getSelectedItem().toString();
        make = getTextFromView(this.etMake);
        capacity = getTextFromView(this.etCapacity);
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
            if (isEmptyStr(firedetectSpineer)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Fire Detect System");
                return false;
            } else if (isEmptyStr(extinguiserSpineer)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Fire Extinguiser ");
                return false;
            }
        } else {
            ASTUIUtil.showToast("Item Not Available");
        }
        return true;
    }
}
