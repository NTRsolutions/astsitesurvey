package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class SiteOnSolarFragment extends MainFragment {
    FNEditText etagbpstring1, etCurrent1, etagbpstring2,
            etCurrent2, etCurrent3, etCurrent4, etagbpstring3, etagbpstring4, etagbpstring5,
            etCurrent6, etagbpstring6, etagbpstring7, etagpop1, etAGBop1, etMPPTuip1, etCurrent5, etCurrent7,
            etMPPTip1, etMPPT, etMPPTop, etBcharginfcurnt, etbatvoltage, etBcharginfvolatage,
            etbatcharging;


    /*  etCurrent5,etCurrent7*/
    String stragbpstring1, strCurrent1, stragbpstring2,
            strCurrent2, strCurrent4, stragbpstring3, stragbpstring4, strCurrent5, stragbpstring5,
            strCurrent6, stragbpstring6, stragbpstring7, stragpop1, strAGBop1, strCurrent7, strMPPTuip1,
            strMPPTip1, strMPPT, strMPPTop, strBcharginfcurnt, strbatvoltage, strBcharginfvolatage,
            strbatcharging, strCurrent3;
    String agbpstring1, Current1, agbpstring2,
            Current2, Current4, agbpstring3, agbpstring4, Current5, agbpstring5,
            Current6, agbpstring6, agbpstring7, agpop1, AGBop1, Current7, MPPTuip1,
            MPPTip1, MPPT, MPPTop, Bcharginfcurnt, batvoltage, Bcharginfvolatage, batcharging, Current3;
    SharedPreferences pref;
    Button btnSubmit;

    @Override
    protected int fragmentLayout() {
        return R.layout.siteonsolar_fragment;
    }

    @Override
    protected void loadView() {
        etagbpstring1 = findViewById(R.id.etagbpstring1);
        etCurrent1 = findViewById(R.id.etCurrent1);
        etagbpstring2 = findViewById(R.id.etagbpstring2);
        etCurrent2 = findViewById(R.id.etCurrent2);
        etCurrent4 = findViewById(R.id.etCurrent4);
        etCurrent3 = findViewById(R.id.etCurrent3);
        etagbpstring3 = findViewById(R.id.etagbpstring3);
        etagbpstring4 = findViewById(R.id.etagbpstring4);
        etCurrent5 = findViewById(R.id.etCurrent5);
        etagbpstring5 = findViewById(R.id.etagbpstring5);
        etCurrent6 = findViewById(R.id.etCurrent6);
        etagbpstring6 = findViewById(R.id.etagbpstring6);
        etagbpstring7 = findViewById(R.id.etagbpstring7);
        etCurrent7 = findViewById(R.id.etCurrent7);
        etagpop1 = findViewById(R.id.etagpop1);
        etAGBop1 = findViewById(R.id.etAGBop1);
        etMPPTuip1 = findViewById(R.id.etMPPTuip1);
        etMPPTip1 = findViewById(R.id.etMPPTip1);
        etMPPT = findViewById(R.id.etMPPT);
        etMPPTop = findViewById(R.id.etMPPTop);
        etBcharginfcurnt = findViewById(R.id.etBcharginfcurnt);
        etbatvoltage = findViewById(R.id.etbatvoltage);
        etBcharginfvolatage = findViewById(R.id.etBcharginfvolatage);
        etbatcharging = findViewById(R.id.etbatcharging);
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
        getSharedPrefSaveData();
        if (!stragbpstring1.equals("") || !strCurrent1.equals("") || !strCurrent3.equals("") || !stragbpstring2.equals("") || !strCurrent2.equals("") || !stragbpstring3.equals("") || !stragbpstring4.equals("") || !strCurrent5.equals("") || !stragbpstring5.equals("") || !strCurrent6.equals("") || !stragbpstring6.equals("") || !stragbpstring7.equals("") || !stragpop1.equals("") || !strAGBop1.equals("") || !strCurrent7.equals("") || !strMPPTuip1.equals("") || !strMPPTip1.equals("") || !strMPPT.equals("") || !strMPPTop.equals("") || !strBcharginfcurnt.equals("") || !strbatvoltage.equals("") || !strBcharginfvolatage.equals("") || !strbatcharging.equals("")) {
            etagbpstring1.setText(stragbpstring1);
            etCurrent1.setText(strCurrent1);
            etagbpstring2.setText(stragbpstring2);
            etCurrent2.setText(strCurrent2);
            etCurrent3.setText(strCurrent3);
            etCurrent4.setText(strCurrent4);
            etagbpstring3.setText(stragbpstring3);
            etagbpstring4.setText(stragbpstring4);
            etCurrent5.setText(strCurrent5);
            etagbpstring5.setText(stragbpstring5);
            etCurrent6.setText(strCurrent6);
            etagbpstring6.setText(stragbpstring6);
            etagbpstring7.setText(stragbpstring7);
            etagpop1.setText(stragpop1);
            etAGBop1.setText(strAGBop1);
            etCurrent7.setText(strCurrent7);
            etMPPTuip1.setText(strMPPTuip1);
            etMPPTip1.setText(strMPPTip1);
            etMPPT.setText(strMPPT);
            etMPPTop.setText(strMPPTop);
            etBcharginfcurnt.setText(strBcharginfcurnt);
            etbatvoltage.setText(strbatvoltage);
            etBcharginfvolatage.setText(strBcharginfvolatage);
            etbatcharging.setText(strbatcharging);

        }
    }
    /*
     *
     * get Data in Shared Pref.
     */

    public void getSharedPrefSaveData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        stragbpstring1 = pref.getString("Agbpstring1", "");
        strCurrent1 = pref.getString("Current1", "");
        stragbpstring2 = pref.getString("agbpstring2", "");
        strCurrent2 = pref.getString("Current2", "");
        strCurrent3 = pref.getString("Current3", "");
        strCurrent4 = pref.getString("Current4", "");
        stragbpstring3 = pref.getString("agbpstring3", "");
        stragbpstring4 = pref.getString("agbpstring4", "");
        strCurrent5 = pref.getString("Current5", "");
        stragbpstring5 = pref.getString("agbpstring5", "");
        strCurrent6 = pref.getString("Current6", "");
        stragbpstring6 = pref.getString("agbpstring6", "");
        stragbpstring7 = pref.getString("agbpstring7", "");
        stragpop1 = pref.getString("agpop1", "");
        strAGBop1 = pref.getString("AGBop1", "");
        strCurrent7 = pref.getString("Current7", "");
        strMPPTuip1 = pref.getString("MPPTuip1", "");
        strMPPTip1 = pref.getString("MPPTip1", "");
        strMPPT = pref.getString("MPPT", "");
        strMPPTop = pref.getString("MPPTop", "");
        strBcharginfcurnt = pref.getString("Bcharginfcurnt", "");
        strbatvoltage = pref.getString("batvoltage", "");
        strBcharginfvolatage = pref.getString("Bcharginfvolatage", "");
        strbatcharging = pref.getString("batcharging", "");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Agbpstring1", agbpstring1);
                editor.putString("Current1", Current1);
                editor.putString("agbpstring2", agbpstring2);
                editor.putString("Current2", Current2);
                editor.putString("Current3", Current3);
                editor.putString("Current4", Current4);
                editor.putString("Current5", Current5);
                editor.putString("agbpstring3", agbpstring3);
                editor.putString("agbpstring4", agbpstring4);
                editor.putString("agbpstring5", agbpstring5);
                editor.putString("Current6", Current6);
                editor.putString("Current7", Current7);
                editor.putString("agbpstring6", agbpstring6);
                editor.putString("agbpstring7", agbpstring7);
                editor.putString("agpop1", agpop1);
                editor.putString("AGBop1", AGBop1);
                editor.putString("MPPTuip1", MPPTuip1);
                editor.putString("MPPTip1", MPPTip1);
                editor.putString("MPPT", MPPT);
                editor.putString("MPPTop", MPPTop);
                editor.putString("Bcharginfcurnt", Bcharginfcurnt);
                editor.putString("batvoltage", batvoltage);
                editor.putString("Bcharginfvolatage", Bcharginfvolatage);
                editor.putString("batcharging", batcharging);
                editor.commit();
            }
        }
    }


    public boolean isValidate() {
        agbpstring1 = getTextFromView(this.etagbpstring1);
        Current1 = getTextFromView(this.etCurrent1);
        agbpstring2 = getTextFromView(this.etagbpstring2);
        Current2 = getTextFromView(this.etCurrent2);
        Current3 = getTextFromView(this.etCurrent3);
        Current4 = getTextFromView(this.etCurrent4);
        agbpstring3 = getTextFromView(this.etagbpstring3);
        agbpstring4 = getTextFromView(this.etagbpstring4);
        Current5 = getTextFromView(this.etCurrent5);
        agbpstring5 = getTextFromView(this.etagbpstring5);
        Current6 = getTextFromView(this.etCurrent6);
        agbpstring6 = getTextFromView(this.etagbpstring6);
        agbpstring7 = getTextFromView(this.etagbpstring7);
        agpop1 = getTextFromView(this.etagpop1);
        AGBop1 = getTextFromView(this.etAGBop1);
        Current7 = getTextFromView(this.etCurrent7);
        MPPTuip1 = getTextFromView(this.etMPPTuip1);
        MPPTip1 = getTextFromView(this.etMPPTip1);
        MPPT = getTextFromView(this.etMPPT);
        MPPTop = getTextFromView(this.etMPPTop);
        Bcharginfcurnt = getTextFromView(this.etBcharginfcurnt);
        batvoltage = getTextFromView(this.etbatvoltage);
        Bcharginfvolatage = getTextFromView(this.etBcharginfvolatage);
        batcharging = getTextFromView(this.etbatcharging);
        if (isEmptyStr(agbpstring1)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(Current1)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(agbpstring2)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(Current2)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(Current3)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(Current4)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(agbpstring3)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(agbpstring4)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(Current5)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(agbpstring5)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(Current6)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(agbpstring6)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(agbpstring7)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(agpop1)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(AGBop1)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(Current7)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(MPPTuip1)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(MPPTip1)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(MPPT)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(MPPTop)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(Bcharginfcurnt)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(batvoltage)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(Bcharginfvolatage)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        } else if (isEmptyStr(batcharging)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Fill All Field ");
            return false;
        }


        return true;
    }
}
