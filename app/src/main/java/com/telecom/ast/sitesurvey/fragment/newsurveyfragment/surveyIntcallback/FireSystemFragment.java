package com.telecom.ast.sitesurvey.fragment.newsurveyfragment.surveyIntcallback;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class FireSystemFragment extends MainFragment {

    Spinner etfiredetectSpineer, etextinguiserSpineer;
    Spinner itemStatusSpineer;
    SharedPreferences pref;

    String strfiredetectSpineer, strextinguiserSpineer, stritemStatusSpineer,
            firedetectSpineer, extinguiserSpineer, itemStatus;

    @Override
    protected int fragmentLayout() {
        return R.layout.firesystem_fragment;
    }

    @Override
    protected void loadView() {
        etfiredetectSpineer = this.findViewById(R.id.etfiredetectSpineer);
        etextinguiserSpineer = this.findViewById(R.id.etextinguiserSpineer);
        itemStatusSpineer = findViewById(R.id.itemStatusSpineer);
    }

    @Override
    protected void setClickListeners() {

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
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

    }


    public void setSpinnerValue() {
        final String etfiredetectSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> etfiredetect = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineer_array);
        etfiredetectSpineer.setAdapter(etfiredetect);
        final String etextinguiserArray[] = {"Available", "Not Available"};
        ArrayAdapter<String> etextinguiser = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etextinguiserArray);
        etextinguiserSpineer.setAdapter(etextinguiser);

        final String itemStatusSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> itemStatus = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemStatusSpineer_array);
        itemStatusSpineer.setAdapter(itemStatus);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("FIRE_strfiredetectSpineer", firedetectSpineer);
                editor.putString("FIRE_strextinguiserSpineer", firedetectSpineer);
                editor.putString("FIRE_stritemStatusSpineer", itemStatus);
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
