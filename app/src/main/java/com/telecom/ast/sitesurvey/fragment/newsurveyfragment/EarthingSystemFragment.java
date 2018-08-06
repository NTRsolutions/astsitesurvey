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

public class EarthingSystemFragment extends MainFragment {
    AppCompatEditText etNoEarthPits, etEarthpitsconnected, etinterConEarthPits, etVoltageEarth, etwireconnected;
    String strNoEarthPits, strEarthpitsconnected, strinterConEarthPits, strVoltageEarth, strwireconnected;
    String NoEarthPits, Earthpitsconnected, interConEarthPits, VoltageEarth, wireconnected;
    Button btnSubmit;
    SharedPreferences pref;
   Spinner itemStatusSpineer;

    @Override
    protected int fragmentLayout() {
        return R.layout.earthingsystem_fragment;
    }

    @Override
    protected void loadView() {
        etNoEarthPits = this.findViewById(R.id.etNoEarthPits);
        etEarthpitsconnected = this.findViewById(R.id.etEarthpitsconnected);
        etinterConEarthPits = this.findViewById(R.id.etinterConEarthPits);
        etVoltageEarth = this.findViewById(R.id.etVoltageEarth);
        etwireconnected = this.findViewById(R.id.etwireconnected);
        btnSubmit = this.findViewById(R.id.btnSubmit);
        itemStatusSpineer = this.findViewById(R.id.itemStatusSpineer);
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
        if (!strNoEarthPits.equals("") || !strEarthpitsconnected.equals("") || !strinterConEarthPits.equals("")
                || !strwireconnected.equals("")
                || !strVoltageEarth.equals("")
                ) {
            etNoEarthPits.setText(strNoEarthPits);
            etEarthpitsconnected.setText(strEarthpitsconnected);
            etinterConEarthPits.setText(strinterConEarthPits);
            etVoltageEarth.setText(strVoltageEarth);
            etwireconnected.setText(strwireconnected);
        }
        itemStatusSpineer.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Not Available")) {
                    etNoEarthPits.setEnabled(false);
                    etEarthpitsconnected.setEnabled(false);
                    etinterConEarthPits.setEnabled(false);
                    etVoltageEarth.setEnabled(false);
                    etwireconnected.setEnabled(false);
                } else {
                    etNoEarthPits.setEnabled(true);
                    etEarthpitsconnected.setEnabled(true);
                    etinterConEarthPits.setEnabled(true);
                    etVoltageEarth.setEnabled(true);
                    etwireconnected.setEnabled(true);
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
     * Shared Prefrences---------------------------------------
     */
    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strNoEarthPits = pref.getString("EARTH_strNoEarthPits", "");
        strEarthpitsconnected = pref.getString("EARTH_strEarthpitsconnected", "");
        strinterConEarthPits = pref.getString("EARTH_strinterConEarthPits", "");
        strVoltageEarth = pref.getString("EARTH_strVoltageEarth", "");
        strwireconnected = pref.getString("EARTH_strwireconnected", "");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("EARTH_strNoEarthPits", NoEarthPits);
                editor.putString("EARTH_strEarthpitsconnected", Earthpitsconnected);
                editor.putString("EARTH_strinterConEarthPits", interConEarthPits);
                editor.putString("EARTH_strVoltageEarth", VoltageEarth);
                editor.putString("EARTH_strwireconnected", wireconnected);
                editor.commit();
            }

        }
    }

    public boolean isValidate() {
        NoEarthPits = etNoEarthPits.getText().toString();
        Earthpitsconnected = etEarthpitsconnected.getText().toString();
        interConEarthPits = etinterConEarthPits.getText().toString();
        VoltageEarth = etVoltageEarth.getText().toString();
        wireconnected = etwireconnected.getText().toString();
        if (itemStatusSpineer.getSelectedItem().toString().equalsIgnoreCase("Available")) {
            if (isEmptyStr(NoEarthPits)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of EarthPits");
                return false;
            } else if (isEmptyStr(Earthpitsconnected)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of Earthpits connected  EGB/IGB");
                return false;
            } else if (isEmptyStr(interConEarthPits)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Inter Connectivity of EarthPits");
                return false;
            } else if (isEmptyStr(VoltageEarth)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Voltage between Earth and Neutral");
                return false;
            } else if (isEmptyStr(wireconnected)) {
                ASTUIUtil.shownewErrorIndicator(getContext(), "DG/EB neutral wire connected with earthing");
                return false;
            }
        } else {
            ASTUIUtil.showToast("Item Not Available");
        }
        return true;
    }
}
