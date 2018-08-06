package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class MiscElectricalEquiFragment extends MainFragment {

    Spinner spinnerElectrical, spinnerAviationLamp, LightningSpinner;
    AppCompatEditText etEarthingvalue;
    Button btnSubmit;
    String strEarthingvalue, Earthingvalue, strspinnerElectrical, strspinnerAviationLamp, strLightningSpinner;
    SharedPreferences pref;

    @Override
    protected int fragmentLayout() {
        return R.layout.miscelectrequipment_fragment;
    }

    @Override
    protected void loadView() {
        spinnerElectrical = this.findViewById(R.id.spinnerElectrical);
        spinnerAviationLamp = this.findViewById(R.id.spinnerAviationLamp);
        LightningSpinner = this.findViewById(R.id.LightningSpinner);
        etEarthingvalue = this.findViewById(R.id.etEarthingvalue);
        btnSubmit = this.findViewById(R.id.btnSubmit);
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
        setSpinnerValue();
        getSharedprefData();
        if (!Earthingvalue.equals("")) {
            etEarthingvalue.setText(Earthingvalue);
        }
    }


    /*
     *
     *     Shared Prefrences
     */
    public void getSharedprefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        Earthingvalue = pref.getString("MISC_strbtsOperator", "");
        strspinnerElectrical = pref.getString("MISC_strspinnerElectrical", "");
        strspinnerAviationLamp = pref.getString("MISC_strspinnerAviationLamp", "");
        strLightningSpinner = pref.getString("MISC_strLightningSpinner", "");

    }

    public void setSpinnerValue() {
        final String etfiredetectSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> etfiredetect = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineer_array);
        spinnerElectrical.setAdapter(etfiredetect);

        final String etextinguiserArray[] = {"Available", "Not Available"};
        ArrayAdapter<String> etextinguiser = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etextinguiserArray);
        spinnerAviationLamp.setAdapter(etextinguiser);


        final String LightningArray[] = {"Available", "Not Available"};
        ArrayAdapter<String> Lightning = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, LightningArray);
        LightningSpinner.setAdapter(Lightning);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("MISC_strbtsOperator", strEarthingvalue);
                editor.putString("MISC_strspinnerElectrical", strspinnerElectrical);
                editor.putString("MISC_strspinnerAviationLamp", strspinnerAviationLamp);
                editor.putString("MISC_strLightningSpinner", strLightningSpinner);
                editor.commit();
            }

        }
    }

    // ----validation -----
    private boolean isValidate() {
        strEarthingvalue = getTextFromView(this.etEarthingvalue);
        strspinnerElectrical = spinnerElectrical.getSelectedItem().toString();
        strspinnerAviationLamp = spinnerAviationLamp.getSelectedItem().toString();
        strLightningSpinner = LightningSpinner.getSelectedItem().toString();
        if (isEmptyStr(strEarthingvalue)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Earthing-Resistance Value");
            return false;
        } else if (isEmptyStr(strspinnerElectrical)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Item Condition");
            return false;
        } else if (isEmptyStr(strspinnerAviationLamp)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Lamp Item Condition");
            return false;
        } else if (isEmptyStr(strLightningSpinner)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Ligiting Item Condition");
            return false;
        }
        return true;
    }
}
