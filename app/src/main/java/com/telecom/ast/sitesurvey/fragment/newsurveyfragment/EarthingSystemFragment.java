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

public class EarthingSystemFragment extends MainFragment {
    AppCompatEditText etNoEarthPits, etEarthpitsconnected, etinterConEarthPits, etVoltageEarth, etwireconnected;
    String strNoEarthPits, strEarthpitsconnected, strinterConEarthPits, strVoltageEarth, strwireconnected;
    String NoEarthPits, Earthpitsconnected, interConEarthPits, VoltageEarth, wireconnected;
    Button btnSubmit;
    SharedPreferences pref;

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
    }


    /*
     *
     * Shared Prefrences---------------------------------------
     */
    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strNoEarthPits = pref.getString("strNoEarthPits", "");
        strEarthpitsconnected = pref.getString("strEarthpitsconnected", "");
        strinterConEarthPits = pref.getString("strinterConEarthPits", "");
        strVoltageEarth = pref.getString("strVoltageEarth", "");
        strwireconnected = pref.getString("strwireconnected", "");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("strNoEarthPits", NoEarthPits);
                editor.putString("strEarthpitsconnected", Earthpitsconnected);
                editor.putString("strinterConEarthPits", interConEarthPits);
                editor.putString("strVoltageEarth", VoltageEarth);
                editor.putString("strwireconnected", wireconnected);
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
        return true;
    }
}
