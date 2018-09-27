package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.database.AtmDatabase;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.BtsInfoData;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTObjectUtil;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class BTSFragment extends MainFragment {
    private LinearLayout mContainerView;
    private Button btnSubmit;
    private AppCompatEditText etNofCab;
    private AppCompatEditText etNoofDCDB, etNofKroneBox, etNoofRack;
    private Spinner btstyelSpinner, etbtsOperator, etMicrowave, etOperatorType;
    private String btsOperator, NoofDCDB, NofKroneBox, NoofRack, Microwave, NofCab, btsty, stretOperatorType;
    private String strUserId, strSiteId;
    private SharedPreferences userPref;
    private int SNo = 1;

    @Override
    protected int fragmentLayout() {
        return R.layout.bts_fragment;
    }

    @Override
    protected void loadView() {
        btstyelSpinner = this.findViewById(R.id.btstye);
        etbtsOperator = this.findViewById(R.id.etbtsOperator);
        etNoofDCDB = this.findViewById(R.id.etNoofDCDB);
        etNofKroneBox = this.findViewById(R.id.etNofKroneBox);
        etNofCab = this.findViewById(R.id.etNofCab);
        etNoofRack = this.findViewById(R.id.etNoofRack);
        etMicrowave = this.findViewById(R.id.etMicrowave);
        btnSubmit = findViewById(R.id.btnSubmit);
        etOperatorType = findViewById(R.id.etOperatorType);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {
        etNofCab.setEnabled(false);
    }


    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
    }

    @Override
    protected void dataToView() {
        getUserPref();
    }


    @Override
    public void onClick(View view) {
        if (isValidate()) {
            ASTUIUtil.showToast("Your BTS  Data save Successfully");
            AtmDatabase atmDatabase = new AtmDatabase(getContext());
            BtsInfoData btsInfoData = new BtsInfoData();
            btsInfoData.setsNo(SNo);
            btsInfoData.setType(btsty);
            btsInfoData.setName(btsOperator);
            btsInfoData.setCabinetQty(NofCab);
            btsInfoData.setNoofDCDBBox(NoofDCDB);
            btsInfoData.setNoofKroneBox(NofKroneBox);
            btsInfoData.setNoofTransmissionRack(NoofRack);
            btsInfoData.setMicrowave(Microwave);
            btsInfoData.setOperatorType(stretOperatorType);
            atmDatabase.upsertBTSInfo(btsInfoData);
            showAddMoreItemDialog();

            //     saveBasicDataonServer();

        }


    }

    public boolean isValidate() {
        // btsOperator = getTextFromView(this.etbtsOperator);
        btsOperator = etbtsOperator.getSelectedItem().toString();
        NofCab = getTextFromView(this.etNofCab);
        NoofDCDB = getTextFromView(this.etNoofDCDB);
        NofKroneBox = getTextFromView(this.etNofKroneBox);
        NoofRack = getTextFromView(this.etNoofRack);
        btsty = btstyelSpinner.getSelectedItem().toString();
        Microwave = etMicrowave.getSelectedItem().toString();
        stretOperatorType = etOperatorType.getSelectedItem().toString();

        if (ASTObjectUtil.isEmptyStr(btsOperator)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter BTS Operator");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(NofCab)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Number Of Cabinets");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(NoofDCDB)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Number Of Dcdb Box");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(NofKroneBox)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Number Of Krone Box");
            return false;
        } else if (ASTObjectUtil.isEmptyStr(NoofRack)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter  Number Of Transmission Rack");
            return false;
        }


        return true;
    }


    public void showAddMoreItemDialog() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        android.support.v7.app.AlertDialog dialog = builder.create();
        //dialog.getWindow().getAttributes().windowAnimations = R.style.alertAnimation;
        dialog.setMessage("Do you want do add more BTS Operator Details");
        dialog.setTitle("Add BTS Item");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Add More BTS Item", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SNo = SNo + 1;
                clearFiledData();
            }
        });
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reloadBackScreen();
            }
        });
        dialog.show();
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#000000"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#000000"));
    }

    public void clearFiledData() {
        etbtsOperator.setSelection(0);
        etNofCab.setText("");
        etNoofDCDB.setText("");
        etNofKroneBox.setText("");
        etNoofRack.setText("");
        etMicrowave.setSelection(0);
        etNofCab.setText("");
        btstyelSpinner.setSelection(0);
    }

    @Override
    public boolean onBackPressed() {
        return isGoBack();
    }

    boolean isgoBack;

    private boolean isGoBack() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you Sure you want go to Back Screen");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                reloadBackScreen();
                isgoBack = true;
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                isgoBack = false;
                dialog.dismiss();

            }
        });
        alertDialog.show();
        return isgoBack;
    }


}
