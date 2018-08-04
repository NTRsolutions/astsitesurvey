package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class BTSFragment extends MainFragment {
    private LinearLayout mContainerView;
    private Button mAddButton, btnSubmit;
    private View mExclusiveEmptyView;
    FNEditText etbtsOperator, etNofCab;
    Spinner btstyelSpinner;
    String btsOperator, NofCab;
    String strbtsOperator, strNofCab;
    SharedPreferences pref;

    @Override
    protected int fragmentLayout() {
        return R.layout.btsmain_fragment;
    }

    @Override
    protected void loadView() {
        mContainerView = findViewById(R.id.parentView);
        mAddButton = findViewById(R.id.addBts);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        mAddButton.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        inflateEditRow();
        getSharedPrefSaveData();
        if (!strbtsOperator.equals("") || !strNofCab.equals("")) {
            etbtsOperator.setText(strbtsOperator);
            etNofCab.setText(strNofCab);
        }
    }

    /*
     *
     * get Data in Shared Pref.
     */
    public void getSharedPrefSaveData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strbtsOperator = pref.getString("strbtsOperator", "");
        strNofCab = pref.getString("strNofCab", "");

    }


    // Helper for inflating a row
    private void inflateEditRow() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.bts_fragment, null);
        etbtsOperator = rowView.findViewById(R.id.etbtsOperator);
        etNofCab = rowView.findViewById(R.id.etNofCab);
       // etbtscurrent = rowView.findViewById(R.id.btscurrent);
    //    etbtscurrentbtSide = rowView.findViewById(R.id.btscurrentbtSide);
     //   etbtsvVoltagebtsside = rowView.findViewById(R.id.btsvVoltagebtsside);
      //  etbtsvollatagesmpsside = rowView.findViewById(R.id.btsvollatagesmpsside);
      btstyelSpinner = rowView.findViewById(R.id.btstye);
        mExclusiveEmptyView = rowView;
        mContainerView.addView(rowView, mContainerView.getChildCount() - 1);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addBts) {
            inflateEditRow();
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("strbtsOperator", btsOperator);
                editor.putString("strNofCab", NofCab);
                editor.commit();

            }
        }
    }

    public boolean isValidate() {
        btsOperator = getTextFromView(this.etbtsOperator);
        NofCab = getTextFromView(this.etNofCab);

        if (isEmptyStr(btsOperator)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter BTS Operator");
            return false;
        } else if (isEmptyStr(NofCab)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of Cab.");
            return false;
        }
        return true;
    }
}
