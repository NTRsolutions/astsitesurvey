package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class TowerFragment extends MainFragment {

    static ImageView overviewImg, northmg, eastImg, southImg, westImg;
    static boolean isImage1, isImage2, isImage3, isImage4, isImage5;
    Button btnSubmit;
    LinearLayout descriptionLayout;
    Spinner itemConditionSpinner, typeTowerSpinner;
    String strUserId, strSavedDateTime, strSiteId;
    SharedPreferences pref;
    EditText etHeight, etDate, etDescription;
    String toerTypestr, typeheightstr, datesiteStr, itemConditionstr, descriptionstr;
    String type, height, date, itemcondion, descreption;
    static String overviewImgstr, northmgStr, eastImgStr, southImgStr, westImgStr;

    @Override
    protected int fragmentLayout() {
        return R.layout.tower_fragment;
    }

    @Override
    protected void loadView() {
        overviewImg = findViewById(R.id.image1);
        northmg = findViewById(R.id.image2);
        eastImg = findViewById(R.id.image3);
        southImg = findViewById(R.id.image4);
        westImg = findViewById(R.id.image5);
        btnSubmit = findViewById(R.id.btnSubmit);
        itemConditionSpinner = findViewById(R.id.itemConditionSpinner);
        typeTowerSpinner = findViewById(R.id.typeTowerSpinner);
        etHeight = findViewById(R.id.etHeight);
        etDate = findViewById(R.id.etDate);
        etDescription = findViewById(R.id.etDescription);
        descriptionLayout = findViewById(R.id.descriptionLayout);

    }

    @Override
    protected void setClickListeners() {
        overviewImg.setOnClickListener(this);
        northmg.setOnClickListener(this);
        eastImg.setOnClickListener(this);
        southImg.setOnClickListener(this);
        westImg.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }


    public void setSpinnerValue() {
        final String typeTowerSpinner_array[] = {"RTT", "RTP", "Ground 3 leg", "Ground 4 leg",};
        ArrayAdapter<String> itemCondi = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, typeTowerSpinner_array);
        typeTowerSpinner.setAdapter(itemCondi);

        final String itemCondition_array[] = {"Ok", "Missing", "Poor Condition"};
        ArrayAdapter<String> towerType = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, itemCondition_array);
        itemConditionSpinner.setAdapter(towerType);


    }

    @Override
    protected void dataToView() {
        setSpinnerValue();
        getSharedPrefData();
        if (!toerTypestr.equals("") || !typeheightstr.equals("") || !datesiteStr.equals("") || !itemConditionstr.equals("")
                || !descriptionstr.equals("")) {
            etHeight.setText(typeheightstr);
            etDate.setText(datesiteStr);
            etDescription.setText(descriptionstr);
        }
        if (!overviewImgstr.equals("") || !northmgStr.equals("") || !eastImgStr.equals("") || !westImgStr.equals("") || !southImgStr.equals("")) {
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(overviewImgstr)).placeholder(R.drawable.noimage).into(overviewImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(northmgStr)).placeholder(R.drawable.noimage).into(northmg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(eastImgStr)).placeholder(R.drawable.noimage).into(eastImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(southImgStr)).placeholder(R.drawable.noimage).into(southImg);
            Picasso.with(ApplicationHelper.application().getContext()).load(new File(westImgStr)).placeholder(R.drawable.noimage).into(westImg);
        }
        itemConditionSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                descriptionLayout.setVisibility(selectedItem.equalsIgnoreCase("Poor Condition") ? View.VISIBLE : View.GONE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getSharedPrefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = pref.getString("USER_ID", "");
        toerTypestr = pref.getString("Tower_Type", "");
        typeheightstr = pref.getString("Tower_Height", "");
        datesiteStr = pref.getString("Tower_Date_SITE", "");
        itemConditionstr = pref.getString("Tower_item_Condi", "");
        descriptionstr = pref.getString("Description", "");
        overviewImgstr = pref.getString("Tower_Photo1", "");
        northmgStr = pref.getString("Tower_Photo2", "");
        eastImgStr = pref.getString("Tower_Photo3", "");
        southImgStr = pref.getString("Tower_Photo4", "");
        westImgStr = pref.getString("Tower_Photo5", "");
        strSiteId = pref.getString("SiteId", "");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.image1) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = true;
            isImage2 = false;
            isImage3 = false;
            isImage4 = false;
            isImage5 = false;
        } else if (view.getId() == R.id.image2) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = true;
            isImage3 = false;
            isImage4 = false;
            isImage5 = false;
        } else if (view.getId() == R.id.image3) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = false;
            isImage3 = true;
            isImage4 = false;
            isImage5 = false;
        } else if (view.getId() == R.id.image4) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = false;
            isImage3 = false;
            isImage4 = true;
            isImage5 = false;
        } else if (view.getId() == R.id.image5) {
            ASTUIUtil.startImagePicker(getHostActivity());
            isImage1 = false;
            isImage2 = false;
            isImage3 = false;
            isImage4 = false;
            isImage5 = true;
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("USER_ID", strUserId);
                editor.putString("Tower_Type", type);
                editor.putString("Tower_Height", height);
                editor.putString("Tower_Date_SITE", date);
                editor.putString("Tower_item_Condi", itemcondion);
                editor.putString("Tower_Description", descreption);
                editor.putString("Tower_Photo1", overviewImgstr);
                editor.putString("Tower_Photo2", northmgStr);
                editor.putString("Tower_Photo3", eastImgStr);
                editor.putString("Tower_Photo4", southImgStr);
                editor.putString("Tower_Photo5", westImgStr);
                editor.commit();
            }
        }

    }

    public boolean isValidate() {
        type = typeTowerSpinner.getSelectedItem().toString();
        itemcondion = itemConditionSpinner.getSelectedItem().toString();
        height = etHeight.getText().toString();
        date = etDate.getText().toString();
        descreption = etDescription.getText().toString();
        if (isEmptyStr(type)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Tower Type");
            return false;
        } else if (isEmptyStr(height)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Height");
            return false;
        } else if (isEmptyStr(date)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Site Date");
            return false;
        } else if (isEmptyStr(itemcondion)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Tower Condition");
            return false;
        } else if (isEmptyStr(descreption)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Description");
            return false;
        } else if (isEmptyStr(overviewImgstr)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Overview Photo");
            return false;
        } else if (isEmptyStr(eastImgStr)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select East Photo");
            return false;

        } else if (isEmptyStr(northmgStr)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select North Photo");
            return false;
        } else if (isEmptyStr(southImgStr)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select South Photo");
            return false;
        } else if (isEmptyStr(westImgStr)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select West Photo");
            return false;
        }
        return true;
    }

    public static void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (FNObjectUtil.isNonEmptyStr(deviceFile.getCompressFilePath())) {
                File compressPath = new File(deviceFile.getCompressFilePath());
                if (compressPath.exists()) {
                    if (isImage1) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(overviewImg);
                        overviewImgstr = deviceFile.getFilePath().toString();
                    } else if (isImage2) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(northmg);
                        northmgStr = deviceFile.getFilePath().toString();
                    } else if (isImage3) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(eastImg);
                        eastImgStr = deviceFile.getFilePath().toString();
                    } else if (isImage4) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(southImg);
                        southImgStr = deviceFile.getFilePath().toString();
                    } else {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(westImg);
                        westImgStr = deviceFile.getFilePath().toString();
                    }
                    //compressPath.delete();
                }
            } else if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (isImage1) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(overviewImg);
                    overviewImgstr = deviceFile.getFilePath().toString();
                } else if (isImage2) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(northmg);
                    northmgStr = deviceFile.getFilePath().toString();
                } else if (isImage3) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(eastImg);
                    eastImgStr = deviceFile.getFilePath().toString();
                } else if (isImage4) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(southImg);
                    southImgStr = deviceFile.getFilePath().toString();
                } else {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(westImg);
                    westImgStr = deviceFile.getFilePath().toString();
                }
                if (deviceFile.isfromCamera() || deviceFile.isCropped()) {
                    // deviceFile.getFilePath().delete();
                }
            }
        }
    }

    public static void getResult(ArrayList<MediaFile> files) {
        getPickedFiles(files);
    }

    /**
     * THIS USE an ActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FNReqResCode.ATTACHMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FNFilePicker.EXTRA_SELECTED_MEDIA);
            getResult(files);

        }
    }
}
