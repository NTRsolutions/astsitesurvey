package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.FNEditText;
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

public class MiscellaneousFragment extends MainFragment {
    FNEditText etshater1size, etshater2size, etoutDoorsize, etGateSize, etPlot, etApproachRoad, etboundryheight;
    ImageView image1, image2, image3, image4, image5, image6;
    static boolean ismage1, ismage2, ismage3, ismage4, ismage5, ismage6;
    String shaterimage1, shaterimage2, outDoorimage3, GateSizeimage4, Plotimage5, Approachimage6;
    String shater1size, shater2size, outDoorsize, GateSize, Plot, ApproachRoad, boundryheight, Boundarywall, strUserId;
    Spinner typeBoundarywall;
    Button btnSubmit;
    SharedPreferences pref;

    @Override
    protected int fragmentLayout() {
        return R.layout.miscellaneous_fragment;
    }

    @Override
    protected void loadView() {
        etshater1size = findViewById(R.id.etshater1size);
        etshater2size = findViewById(R.id.etshater2size);
        etoutDoorsize = findViewById(R.id.etoutDoorsize);
        etGateSize = findViewById(R.id.etGateSize);
        etPlot = findViewById(R.id.etPlot);
        etApproachRoad = findViewById(R.id.etApproachRoad);
        etboundryheight = findViewById(R.id.etboundryheight);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        typeBoundarywall = findViewById(R.id.typeBoundarywall);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    @Override
    protected void setClickListeners() {
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        image6.setOnClickListener(this);
//        typeBoundarywall.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        setSpinnerValue();
        getSharedprefData();
        if (!shater1size.equals("") || !shater2size.equals("") || !outDoorsize.equals("") || !GateSize.equals("")
                || !Plot.equals("") || !ApproachRoad.equals("") || !boundryheight.equals("") || !Boundarywall.equals("")) {
            etshater1size.setText(shater1size);
            etshater2size.setText(shater2size);
            etoutDoorsize.setText(outDoorsize);
            etGateSize.setText(GateSize);
            etPlot.setText(Plot);
            etApproachRoad.setText(ApproachRoad);
            etboundryheight.setText(boundryheight);
            Boundarywall = typeBoundarywall.getSelectedItem().toString();
            //     typeBoundarywall.setText(Boundarywall);
            if (!shaterimage1.equals("") || !shaterimage2.equals("") || !outDoorimage3.equals("") || !GateSizeimage4.equals("") || !Plotimage5.equals("") || !Approachimage6.equals("")) {
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(shaterimage1)).placeholder(R.drawable.noimage).into(image1);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(shaterimage2)).placeholder(R.drawable.noimage).into(image2);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(outDoorimage3)).placeholder(R.drawable.noimage).into(image3);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(GateSizeimage4)).placeholder(R.drawable.noimage).into(image4);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(Plotimage5)).placeholder(R.drawable.noimage).into(image5);
                Picasso.with(ApplicationHelper.application().getContext()).load(new File(Approachimage6)).placeholder(R.drawable.noimage).into(image6);

            }

        }
    }

    /*
     *
     *     Shared Prefrences
     */
    public void getSharedprefData() {
        pref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        shater1size = pref.getString("shater1size", "");
        shater2size = pref.getString("shater2size", "");
        outDoorsize = pref.getString("outDoorsize", "");
        GateSize = pref.getString("GateSize", "");
        Plot = pref.getString("Plot", "");
        ApproachRoad = pref.getString("ApproachRoad", "");
        boundryheight = pref.getString("boundryheight", "");
        Boundarywall = pref.getString("Boundarywall", "");
        shaterimage1 = pref.getString("shaterimage1", "");
        shaterimage2 = pref.getString("shaterimage2", "");
        outDoorimage3 = pref.getString("outDoorimage3", "");
        GateSizeimage4 = pref.getString("GateSizeimage4", "");
        Plotimage5 = pref.getString("Plotimage5", "");
        Approachimage6 = pref.getString("Approachimage6", "");
        strUserId = pref.getString("USER_ID", "");

    }


    public void setSpinnerValue() {
        final String typeboundrywall_array[] = {"Barb wire", "wall", "mixed", "None"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, typeboundrywall_array);
        typeBoundarywall.setAdapter(homeadapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.image1) {
            ismage1 = true;
            ismage2 = false;
            ismage3 = false;
            ismage4 = false;
            ismage5 = false;
            ismage6 = false;
            ASTUIUtil.startImagePicker(getHostActivity());
        } else if (view.getId() == R.id.image2) {
            ismage1 = false;
            ismage2 = true;
            ismage3 = false;
            ismage4 = false;
            ismage5 = false;
            ismage6 = false;
            ASTUIUtil.startImagePicker(getHostActivity());
        } else if (view.getId() == R.id.image3) {
            ismage1 = false;
            ismage2 = false;
            ismage3 = true;
            ismage4 = false;
            ismage5 = false;
            ismage6 = false;
            ASTUIUtil.startImagePicker(getHostActivity());
        } else if (view.getId() == R.id.image4) {
            ASTUIUtil.startImagePicker(getHostActivity());
            ismage1 = false;
            ismage2 = false;
            ismage3 = false;
            ismage4 = true;
            ismage5 = false;
            ismage6 = false;
        } else if (view.getId() == R.id.image5) {
            ismage1 = false;
            ismage2 = false;
            ismage3 = false;
            ismage4 = false;
            ismage5 = true;
            ismage6 = false;
            ASTUIUtil.startImagePicker(getHostActivity());
        } else if (view.getId() == R.id.image6) {
            ismage1 = false;
            ismage2 = false;
            ismage3 = false;
            ismage4 = false;
            ismage5 = false;
            ismage6 = true;
            ASTUIUtil.startImagePicker(getHostActivity());
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                shater1size = getTextFromView(this.etshater1size);
                shater2size = getTextFromView(this.etshater2size);
                outDoorsize = getTextFromView(this.etoutDoorsize);
                GateSize = getTextFromView(this.etGateSize);
                Plot = getTextFromView(this.etPlot);
                ApproachRoad = getTextFromView(this.etApproachRoad);
                boundryheight = getTextFromView(this.etboundryheight);
                Boundarywall = typeBoundarywall.getSelectedItem().toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("USER_ID", strUserId);
                editor.putString("shater1size", shater1size);
                editor.putString("shater2size", shater2size);
                editor.putString("outDoorsize", outDoorsize);
                editor.putString("GateSize", GateSize);
                editor.putString("Plot", Plot);
                editor.putString("ApproachRoad", ApproachRoad);
                editor.putString("boundryheight", boundryheight);
                editor.putString("Boundarywall", Boundarywall);
                editor.putString("shaterimage1", shaterimage1);
                editor.putString("shaterimage2", shaterimage2);
                editor.putString("outDoorimage3", outDoorimage3);
                editor.putString("outDoorimage3", outDoorimage3);
                editor.putString("GateSizeimage4", GateSizeimage4);
                editor.putString("Plotimage5", Plotimage5);
                editor.putString("Approachimage6", Approachimage6);
                editor.commit();
            }
        }

    }


    // ----validation -----
    private boolean isValidate() {
        shater1size = getTextFromView(this.etshater1size);
        shater2size = getTextFromView(this.etshater2size);
        outDoorsize = getTextFromView(this.etoutDoorsize);
        GateSize = getTextFromView(this.etGateSize);
        Plot = getTextFromView(this.etPlot);
        ApproachRoad = getTextFromView(this.etApproachRoad);
        boundryheight = getTextFromView(this.etboundryheight);
        Boundarywall = typeBoundarywall.getSelectedItem().toString();
        if (isEmptyStr(shater1size)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Shelter 1 Size");
            return false;
        } else if (isEmptyStr(shater2size)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Shelter 2 Size");
            return false;
        } else if (isEmptyStr(outDoorsize)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Outdoor Size");
            return false;
        } else if (isEmptyStr(GateSize)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter GateSize");
            return false;

        } else if (isEmptyStr(Plot)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Plot");
            return false;
        } else if (isEmptyStr(ApproachRoad)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter ApproachRoad");
            return false;
        } else if (isEmptyStr(boundryheight)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter boundryheight");
            return false;
        } else if (isEmptyStr(Boundarywall)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter strBoundarywall");
            return false;
        } else if (isEmptyStr(shaterimage1)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select shater  1 Photo");
            return false;
        } else if (isEmptyStr(shaterimage2)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select shater 2  Photo");
            return false;
        } else if (isEmptyStr(outDoorimage3)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select outDoor   Photo");
            return false;
        } else if (isEmptyStr(GateSizeimage4)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select GateSize   Photo");
            return false;
        } else if (isEmptyStr(Plotimage5)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Plot  Photo");
            return false;
        } else if (isEmptyStr(Approachimage6)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Approach  Photo");
            return false;
        }
        return true;
    }


    public void getPickedFiles(ArrayList<MediaFile> files) {
        for (MediaFile deviceFile : files) {
            if (FNObjectUtil.isNonEmptyStr(deviceFile.getCompressFilePath())) {
                File compressPath = new File(deviceFile.getCompressFilePath());
                if (compressPath.exists()) {
                    if (ismage1) {
                        shaterimage1 = deviceFile.getFilePath().toString();
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(image1);
                    } else if (ismage2) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(image2);
                        shaterimage2 = deviceFile.getFilePath().toString();
                    } else if (ismage3) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(image3);
                        outDoorimage3 = deviceFile.getFilePath().toString();
                    } else if (ismage4) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(image4);
                        GateSizeimage4 = deviceFile.getFilePath().toString();
                    } else if (ismage5) {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(image5);
                        Plotimage5 = deviceFile.getFilePath().toString();
                    } else {
                        Picasso.with(ApplicationHelper.application().getContext()).load(compressPath).into(image6);
                        Approachimage6 = deviceFile.getFilePath().toString();
                    }
                    //compressPath.delete();
                }
            } else if (deviceFile.getFilePath() != null && deviceFile.getFilePath().exists()) {
                if (ismage1) {
                    shaterimage1 = deviceFile.getFilePath().toString();
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(image1);
                } else if (ismage2) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(image2);
                    shaterimage2 = deviceFile.getFilePath().toString();

                } else if (ismage3) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(image3);
                    outDoorimage3 = deviceFile.getFilePath().toString();

                } else if (ismage4) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(image4);
                    GateSizeimage4 = deviceFile.getFilePath().toString();

                } else if (ismage5) {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(image5);
                    Plotimage5 = deviceFile.getFilePath().toString();

                } else {
                    Picasso.with(ApplicationHelper.application().getContext()).load(deviceFile.getFilePath()).into(image6);
                    Approachimage6 = deviceFile.getFilePath().toString();
                }
                if (deviceFile.isfromCamera() || deviceFile.isCropped()) {
                    // deviceFile.getFilePath().delete();
                }
            }
        }
    }

    public void getResult(ArrayList<MediaFile> files) {
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
