package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
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

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class BTSFragment extends MainFragment {
    private LinearLayout mContainerView;
    private Button btnSubmit;
    AppCompatEditText etNofCab;
    AppCompatEditText etbtsOperator, etNoofDCDB, etNofKroneBox, etNoofRack, etMicrowave;
    Spinner btstyelSpinner;
    String btsOperator, NoofDCDB, NofKroneBox, NoofRack, Microwave, NofCab, btsty;
    String strUserId, strSiteId;
    SharedPreferences userPref;

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
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }


    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
    }

    @Override
    protected void dataToView() {
        getUserPref();
        getSharedPrefSaveData();
        if (!isEmptyStr(btsOperator) || !isEmptyStr(NoofDCDB) || !isEmptyStr(NofKroneBox) || !isEmptyStr(NoofRack) ||
                !isEmptyStr(Microwave) ||
                !isEmptyStr(NofCab)) {
            etbtsOperator.setText(btsOperator);
            etNofCab.setText(NofCab);
            etbtsOperator.setText(btsOperator);
            etNoofDCDB.setText(NoofDCDB);
            etNofKroneBox.setText(NofKroneBox);
            etNoofRack.setText(NoofRack);
            etMicrowave.setText(Microwave);
            etNofCab.setText(NofCab);
        }
    }

    /*
     *
     * get Data in Shared Pref.
     */
    public void getSharedPrefSaveData() {
    /*    pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strbtsOperator = pref.getString("strbtsOperator", "");
        strNofCab = pref.getString("strNofCab", "");*/

    }


    @Override
    public void onClick(View view) {
        if (isValidate()) {
            saveBasicDataonServer();

        }


    }

    public boolean isValidate() {
        btsOperator = getTextFromView(this.etbtsOperator);
        NofCab = getTextFromView(this.etNofCab);
        NoofDCDB = getTextFromView(this.etNoofDCDB);
        NofKroneBox = getTextFromView(this.etNofKroneBox);
        NoofRack = getTextFromView(this.etNoofRack);
        Microwave = getTextFromView(this.etMicrowave);
        btsty = btstyelSpinner.getSelectedItem().toString();


        if (isEmptyStr(btsOperator)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter BTS Operator");
            return false;
        } else if (isEmptyStr(NofCab)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of Cab.");
            return false;
        }
        return true;
    }

    ArrayList<Object> operatorArray;

    public void saveBasicDataonServer() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar progressBar = new ASTProgressBar(getContext());
            progressBar.show();
            String serviceURL = Constant.BASE_URL + Constant.SurveyDataSave;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Site_ID", strSiteId);
                jsonObject.put("User_ID", strUserId);
                jsonObject.put("Activity", "BTS");
                JSONObject BTSData = new JSONObject();


                BTSData.put("Type", btsty);
                BTSData.put("Name", btsOperator);
                BTSData.put("CabinetQty", NofCab);
                BTSData.put("SMPSVoltage", "0");
                BTSData.put("SMPSCurrent", "0");
                BTSData.put("BTSVoltage", "0");
                BTSData.put("BTSCurrent", "0");
                BTSData.put("NoofDCDBBox", NoofDCDB);
                BTSData.put("NoofKroneBox", NofKroneBox);
                BTSData.put("NoofTransmissionRack", Microwave);
                BTSData.put("NoofTransmissionRack", NoofRack);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(BTSData);
                jsonObject.put("BTSData", jsonArray);
                operatorArray = new ArrayList<Object>();

                operatorArray.add(BTSData);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("JsonData", jsonObject.toString());
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelper fileUploaderHelper = new FileUploaderHelper(getContext(), payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        if (data.getStatus() == 1) {
                            ASTUIUtil.showToast("Your BTS  Data save Successfully");
                            showAddMoreItemDialog();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Your BTS  Data has not been updated!");
                    }
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                }
            };
            fileUploaderHelper.execute();
        } else {
            ASTUIUtil.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getContext());//off line msg....
        }

    }

    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
      /*  if (equpImagList != null && equpImagList.size() > 0) {
            for (SaveOffLineData data : equpImagList) {
                if (data != null) {
                    if (data.getImagePath() != null) {
                        File inputFile = new File(data.getImagePath());
                        if (inputFile.exists()) {
                            multipartBody.addFormDataPart("PMInstalEqupImages", data.getImageName(), RequestBody.create(MEDIA_TYPE_PNG, inputFile));
                        }
                    }
                }
            }
        }
*/
        return multipartBody;
    }


    public void showAddMoreItemDialog() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        android.support.v7.app.AlertDialog dialog = builder.create();
        //dialog.getWindow().getAttributes().windowAnimations = R.style.alertAnimation;
        dialog.setMessage("Do you want do add more BTS Operator Details");
        dialog.setTitle("Add BTS Item");
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Add More BTS Item", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
        etbtsOperator.setText("");
        etNofCab.setText("");
        etbtsOperator.setText("");
        etNoofDCDB.setText("");
        etNofKroneBox.setText("");
        etNoofRack.setText("");
        etMicrowave.setText("");
        etNofCab.setText("");
        btstyelSpinner.setSelection(0);
    }

}
