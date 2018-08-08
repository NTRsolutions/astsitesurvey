package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    TextInputEditText etshater2size, etoutDoorsize, etGateSize, etPlot, etApproachRoad, etboundryheight;
    TextInputEditText etshater1size, etLayoutDiagram, etVisitRegister, etsiteHygiene;
    ImageView image1, image2, image3, image4, image5, image6;
    static boolean ismage1, ismage2, ismage3, ismage4, ismage5, ismage6;
    String shaterimage1, shaterimage2, outDoorimage3, GateSizeimage4, Plotimage5, Approachimage6;
    String shater1size, shater2size, outDoorsize, GateSize, Plot, ApproachRoad, boundryheight, Boundarywall,
            strUserId,
            signagestatus, cablelebelling, etOtherIssues,
            fSRCopy, SiteProblem, ShelterCovered, SheltLeakage,
            AnyOtherItem, dFCNOPY, bBCabinet, mainDoor, shelter, police,
            ambulane, Technician, LayoutDiagram, VisitRegister, siteHygiene, powerPlant, fire;

    Spinner typeBoundarywall, signagestatusSpinner, cablelebellingSpinner, etOtherIssuesSpinner,
            fSRCopyspinner, SiteProblemspinner, ShelterCoveredSpinner, SheltLeakageSpinner,
            etAnyOtherItemSpinner, dFCNOPYSpinner, bBCabinetSpinner, mainDoorSpineer, shelterSpinner, policeSpinner,
            ambulaneSpinner, TechnicianSpinner, powerPlantSpinner, fireSpinner;

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
        etLayoutDiagram = findViewById(R.id.etLayoutDiagram);
        etVisitRegister = findViewById(R.id.etVisitRegister);
        etsiteHygiene = findViewById(R.id.etsiteHygiene);
        signagestatusSpinner = findViewById(R.id.signagestatusSpinner);
        cablelebellingSpinner = findViewById(R.id.cablelebellingSpinner);
        etOtherIssuesSpinner = findViewById(R.id.etOtherIssuesSpinner);
        fSRCopyspinner = findViewById(R.id.fSRCopyspinner);
        SiteProblemspinner = findViewById(R.id.SiteProblemspinner);
        ShelterCoveredSpinner = findViewById(R.id.ShelterCoveredSpinner);
        SheltLeakageSpinner = findViewById(R.id.SheltLeakageSpinner);
        etAnyOtherItemSpinner = findViewById(R.id.etAnyOtherItemSpinner);
        dFCNOPYSpinner = findViewById(R.id.dFCNOPYSpinner);
        bBCabinetSpinner = findViewById(R.id.bBCabinetSpinner);
        mainDoorSpineer = findViewById(R.id.mainDoorSpineer);
        shelterSpinner = findViewById(R.id.shelterSpinner);
        policeSpinner = findViewById(R.id.policeSpinner);
        ambulaneSpinner = findViewById(R.id.ambulaneSpinner);
        TechnicianSpinner = findViewById(R.id.TechnicianSpinner);
        powerPlantSpinner = findViewById(R.id.powerPlantSpinner);
        fireSpinner = findViewById(R.id.fireSpinner);
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
        getSharedprefData();
        setSpinnerValue();
        if (!shater1size.equals("") || !shater2size.equals("") || !outDoorsize.equals("") || !GateSize.equals("")
                || !Plot.equals("") || !ApproachRoad.equals("") || !boundryheight.equals("") || !Boundarywall.equals("")) {
            etshater1size.setText(shater1size);
            etshater2size.setText(shater2size);
            etoutDoorsize.setText(outDoorsize);
            etGateSize.setText(GateSize);
            etPlot.setText(Plot);
            etApproachRoad.setText(ApproachRoad);
            etboundryheight.setText(boundryheight);
            etLayoutDiagram.setText(LayoutDiagram);
            etVisitRegister.setText(VisitRegister);
            etsiteHygiene.setText(siteHygiene);

            Boundarywall = typeBoundarywall.getSelectedItem().toString();
            signagestatus = signagestatusSpinner.getSelectedItem().toString();
            cablelebelling = cablelebellingSpinner.getSelectedItem().toString();
            etOtherIssues = etOtherIssuesSpinner.getSelectedItem().toString();
            fSRCopy = fSRCopyspinner.getSelectedItem().toString();
            SiteProblem = SiteProblemspinner.getSelectedItem().toString();
            ShelterCovered = ShelterCoveredSpinner.getSelectedItem().toString();
            SheltLeakage = SheltLeakageSpinner.getSelectedItem().toString();
            AnyOtherItem = etAnyOtherItemSpinner.getSelectedItem().toString();
            dFCNOPY = dFCNOPYSpinner.getSelectedItem().toString();
            bBCabinet = bBCabinetSpinner.getSelectedItem().toString();
            mainDoor = mainDoorSpineer.getSelectedItem().toString();
            shelter = shelterSpinner.getSelectedItem().toString();
            police = policeSpinner.getSelectedItem().toString();
            Technician = TechnicianSpinner.getSelectedItem().toString();
            ambulane = ambulaneSpinner.getSelectedItem().toString();
            powerPlant = powerPlantSpinner.getSelectedItem().toString();
            fire = fireSpinner.getSelectedItem().toString();

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


        LayoutDiagram = pref.getString("LayoutDiagram", "");
        VisitRegister = pref.getString("VisitRegister", "");
        siteHygiene = pref.getString("siteHygiene", "");

        signagestatus = pref.getString("signagestatus", "");
        cablelebelling = pref.getString("cablelebelling", "");
        etOtherIssues = pref.getString("etOtherIssues", "");
        fSRCopy = pref.getString("fSRCopy", "");
        SiteProblem = pref.getString("SiteProblem", "");
        ShelterCovered = pref.getString("ShelterCovered", "");
        SheltLeakage = pref.getString("SheltLeakage", "");
        AnyOtherItem = pref.getString("AnyOtherItem", "");
        dFCNOPY = pref.getString("dFCNOPY", "");
        bBCabinet = pref.getString("bBCabinet", "");
        mainDoor = pref.getString("mainDoor", "");
        shelter = pref.getString("shelter", "");
        police = pref.getString("police", "");
        Technician = pref.getString("Technician", "");
        ambulane = pref.getString("ambulane", "");
        powerPlant = pref.getString("powerPlant", "");
        fire = pref.getString("fire", "");




    }


    public void setSpinnerValue() {
        final String typeboundrywall_array[] = {"Barb wire", "wall", "mixed", "None"};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, typeboundrywall_array);
        typeBoundarywall.setAdapter(homeadapter);
        if (Boundarywall != null && !Boundarywall.equals("")) {
            for (int i = 0; i < typeboundrywall_array.length; i++) {
                if (Boundarywall.equalsIgnoreCase(typeboundrywall_array[i])) {
                    typeBoundarywall.setSelection(i);
                } else {
                    typeBoundarywall.setSelection(0);
                }
            }
        }

        final String signagestatusSpinner_array[] = {"Danger", "Caution", "mixed", "Warning"};
        ArrayAdapter<String> signagestatusadapater = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, signagestatusSpinner_array);
        signagestatusSpinner.setAdapter(signagestatusadapater);
        if (signagestatus != null && !signagestatus.equals("")) {
            for (int i = 0; i < signagestatusSpinner_array.length; i++) {
                if (signagestatus.equalsIgnoreCase(signagestatusSpinner_array[i])) {
                    signagestatusSpinner.setSelection(i);
                } else {
                    signagestatusSpinner.setSelection(0);
                }
            }
        }

        final String cablelebellingSpinner_array[] = {"Done ", "Not Done",};
        ArrayAdapter<String> cablelebellingSpinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, cablelebellingSpinner_array);
        cablelebellingSpinner.setAdapter(cablelebellingSpinnerAdapter);
        if (cablelebelling != null && !cablelebelling.equals("")) {
            for (int i = 0; i < cablelebellingSpinner_array.length; i++) {
                if (cablelebelling.equalsIgnoreCase(cablelebellingSpinner_array[i])) {
                    cablelebellingSpinner.setSelection(i);
                } else {
                    cablelebellingSpinner.setSelection(0);
                }
            }
        }

        final String etOtherIssues_array[] = {" History of Site Theft", "Pending Payments to technician,Caretaker,owner", "diesel filler", "Municipality tax due etc ", "Owner aggreement copy available or not with due date", "Owner Change"};
        ArrayAdapter<String> etOtherIssuesadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etOtherIssues_array);
        etOtherIssuesSpinner.setAdapter(etOtherIssuesadapter);
        if (etOtherIssues != null && !etOtherIssues.equals("")) {
            for (int i = 0; i < etOtherIssues_array.length; i++) {
                if (etOtherIssues.equalsIgnoreCase(etOtherIssues_array[i])) {
                    etOtherIssuesSpinner.setSelection(i);
                } else {
                    etOtherIssuesSpinner.setSelection(0);
                }
            }
        }

        final String fSRCopyspinner_array[] = {"DG ", "AC"};
        ArrayAdapter<String> fSRCopyspinner_arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, fSRCopyspinner_array);
        fSRCopyspinner.setAdapter(fSRCopyspinner_arrayAdapter);
        if (fSRCopy != null && !fSRCopy.equals("")) {
            for (int i = 0; i < fSRCopyspinner_array.length; i++) {
                if (fSRCopy.equalsIgnoreCase(fSRCopyspinner_array[i])) {
                    fSRCopyspinner.setSelection(i);
                } else {
                    fSRCopyspinner.setSelection(0);
                }
            }
        }

        final String SiteProblemspAdapter_array[] = {"Night Access problem", "Water Logging"};
        ArrayAdapter<String> SiteProblemspAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, SiteProblemspAdapter_array);
        SiteProblemspinner.setAdapter(SiteProblemspAdapter);
        if (SiteProblem != null && !SiteProblem.equals("")) {
            for (int i = 0; i < SiteProblemspAdapter_array.length; i++) {
                if (SiteProblem.equalsIgnoreCase(SiteProblemspAdapter_array[i])) {
                    SiteProblemspinner.setSelection(i);
                } else {
                    SiteProblemspinner.setSelection(0);
                }
            }
        }

        final String ShelterCoveredSpinner_array[] = {"Natural Cool", "Turbo Cool", "AirCon Cool"};
        ArrayAdapter<String> ShelterCoveredSpinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, ShelterCoveredSpinner_array);
        ShelterCoveredSpinner.setAdapter(ShelterCoveredSpinnerAdapter);
        if (ShelterCovered != null && !ShelterCovered.equals("")) {
            for (int i = 0; i < ShelterCoveredSpinner_array.length; i++) {
                if (ShelterCovered.equalsIgnoreCase(ShelterCoveredSpinner_array[i])) {
                    ShelterCoveredSpinner.setSelection(i);
                } else {
                    ShelterCoveredSpinner.setSelection(0);
                }
            }
        }

        final String SheltLeakageSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> SheltLeakageadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, SheltLeakageSpinner_array);
        SheltLeakageSpinner.setAdapter(SheltLeakageadapter);
        if (SheltLeakage != null && !SheltLeakage.equals("")) {
            for (int i = 0; i < SheltLeakageSpinner_array.length; i++) {
                if (SheltLeakage.equalsIgnoreCase(SheltLeakageSpinner_array[i])) {
                    SheltLeakageSpinner.setSelection(i);
                } else {
                    SheltLeakageSpinner.setSelection(0);
                }
            }
        }
        final String etAnyOtherItemSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> etAnyOtherItemSpinnerapapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etAnyOtherItemSpinner_array);
        etAnyOtherItemSpinner.setAdapter(etAnyOtherItemSpinnerapapter);
        if (AnyOtherItem == null && !AnyOtherItem.equals("")) {
            for (int i = 0; i < etAnyOtherItemSpinner_array.length; i++) {
                if (AnyOtherItem.equalsIgnoreCase(etAnyOtherItemSpinner_array[i])) {
                    etAnyOtherItemSpinner.setSelection(i);
                } else {
                    etAnyOtherItemSpinner.setSelection(0);
                }
            }
        }

        final String dFCNOPYSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> dFCNOPYSpinneradapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, dFCNOPYSpinner_array);
        dFCNOPYSpinner.setAdapter(dFCNOPYSpinneradapter);
        if (dFCNOPY != null && !dFCNOPY.equals("")) {
            for (int i = 0; i < dFCNOPYSpinner_array.length; i++) {
                if (dFCNOPY.equalsIgnoreCase(dFCNOPYSpinner_array[i])) {
                    dFCNOPYSpinner.setSelection(i);
                } else {
                    dFCNOPYSpinner.setSelection(0);
                }
            }
        }


        final String powerPlantSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> powerPlantSpinner_arrayadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, powerPlantSpinner_array);
        powerPlantSpinner.setAdapter(powerPlantSpinner_arrayadapter);
        if (powerPlant != null && !powerPlant.equals("")) {
            for (int i = 0; i < powerPlantSpinner_array.length; i++) {
                if (powerPlant.equalsIgnoreCase(powerPlantSpinner_array[i])) {
                    powerPlantSpinner.setSelection(i);
                } else {
                    powerPlantSpinner.setSelection(0);
                }
            }
        }

        final String fireSpinne_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> fireSpinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, fireSpinne_array);
        fireSpinner.setAdapter(fireSpinnerAdapter);
        if (fire != null && !fire.equals("")) {
            for (int i = 0; i < fireSpinne_array.length; i++) {
                if (fire.equalsIgnoreCase(fireSpinne_array[i])) {
                    fireSpinner.setSelection(i);
                } else {
                    fireSpinner.setSelection(0);
                }
            }
        }


        final String bBCabinetSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> bBCabinetAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, bBCabinetSpinner_array);
        bBCabinetSpinner.setAdapter(bBCabinetAdapter);
        if (bBCabinet != null && !bBCabinet.equals("")) {
            for (int i = 0; i < bBCabinetSpinner_array.length; i++) {
                if (bBCabinet.equalsIgnoreCase(bBCabinetSpinner_array[i])) {
                    bBCabinetSpinner.setSelection(i);
                } else {
                    bBCabinetSpinner.setSelection(0);
                }
            }
        }

        final String mainDoorSpineer_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> mainDoorSpineeradapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, mainDoorSpineer_array);
        mainDoorSpineer.setAdapter(mainDoorSpineeradapter);
        if (mainDoor != null && !mainDoor.equals("")) {
            for (int i = 0; i < mainDoorSpineer_array.length; i++) {
                if (mainDoor.equalsIgnoreCase(mainDoorSpineer_array[i])) {
                    mainDoorSpineer.setSelection(i);
                } else {
                    mainDoorSpineer.setSelection(0);
                }
            }
        }


        final String shelterSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> shelterSpinnerApater = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, shelterSpinner_array);
        shelterSpinner.setAdapter(shelterSpinnerApater);
        if (shelter != null && !shelter.equals("")) {
            for (int i = 0; i < shelterSpinner_array.length; i++) {
                if (shelter.equalsIgnoreCase(shelterSpinner_array[i])) {
                    shelterSpinner.setSelection(i);
                } else {
                    shelterSpinner.setSelection(0);
                }
            }
        }


        final String policeSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> policeSpinneradapater = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, policeSpinner_array);
        policeSpinner.setAdapter(policeSpinneradapater);
        if (police == null && !police.equals("")) {
            for (int i = 0; i < policeSpinner_array.length; i++) {
                if (police.equalsIgnoreCase(policeSpinner_array[i])) {
                    policeSpinner.setSelection(i);
                } else {
                    policeSpinner.setSelection(0);
                }
            }
        }
        final String ambulaneSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> ambulaneSpinneradapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, ambulaneSpinner_array);
        ambulaneSpinner.setAdapter(ambulaneSpinneradapter);
        if (ambulane == null && !ambulane.equals("")) {
            for (int i = 0; i < ambulaneSpinner_array.length; i++) {
                if (ambulane.equalsIgnoreCase(ambulaneSpinner_array[i])) {
                    ambulaneSpinner.setSelection(i);
                } else {
                    ambulaneSpinner.setSelection(0);
                }
            }
        }

        final String TechnicianSpinnerarray[] = {"Available", "Not Available"};
        ArrayAdapter<String> TechnicianSpinneadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, TechnicianSpinnerarray);
        TechnicianSpinner.setAdapter(TechnicianSpinneadapter);
        if (Technician == null && !Technician.equals("")) {
            for (int i = 0; i < TechnicianSpinnerarray.length; i++) {
                if (Technician.equalsIgnoreCase(TechnicianSpinnerarray[i])) {
                    TechnicianSpinner.setSelection(i);
                } else {
                    TechnicianSpinner.setSelection(0);
                }
            }
        }


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
           /*     shater1size = getTextFromView(this.etshater1size);
                shater2size = getTextFromView(this.etshater2size);
                outDoorsize = getTextFromView(this.etoutDoorsize);
                GateSize = getTextFromView(this.etGateSize);
                Plot = getTextFromView(this.etPlot);
                ApproachRoad = getTextFromView(this.etApproachRoad);
                boundryheight = getTextFromView(this.etboundryheight);
                Boundarywall = typeBoundarywall.getSelectedItem().toString();*/
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

                editor.putString("LayoutDiagram", LayoutDiagram);
                editor.putString("VisitRegister", VisitRegister);
                editor.putString("siteHygiene", siteHygiene);
                editor.putString("signagestatus", signagestatus);
                editor.putString("cablelebelling", cablelebelling);
                editor.putString("etOtherIssues", etOtherIssues);
                editor.putString("fSRCopy", fSRCopy);
                editor.putString("SiteProblem", SiteProblem);
                editor.putString("ShelterCovered", ShelterCovered);
                editor.putString("SheltLeakage", SheltLeakage);
                editor.putString("AnyOtherItem", AnyOtherItem);
                editor.putString("dFCNOPY", dFCNOPY);
                editor.putString("bBCabinet", bBCabinet);
                editor.putString("mainDoor", mainDoor);
                editor.putString("shelter", shelter);
                editor.putString("police", police);
                editor.putString("Technician", Technician);
                editor.putString("ambulane", ambulane);
                editor.putString("powerPlant", powerPlant);
                editor.putString("fire", fire);
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

        LayoutDiagram = getTextFromView(this.etLayoutDiagram);
        VisitRegister = getTextFromView(this.etVisitRegister);
        siteHygiene = getTextFromView(this.etsiteHygiene);
        signagestatus = signagestatusSpinner.getSelectedItem().toString();
        cablelebelling = cablelebellingSpinner.getSelectedItem().toString();
        etOtherIssues = etOtherIssuesSpinner.getSelectedItem().toString();
        fSRCopy = fSRCopyspinner.getSelectedItem().toString();
        SiteProblem = SiteProblemspinner.getSelectedItem().toString();
        ShelterCovered = ShelterCoveredSpinner.getSelectedItem().toString();
        SheltLeakage = SheltLeakageSpinner.getSelectedItem().toString();
        AnyOtherItem = etAnyOtherItemSpinner.getSelectedItem().toString();
        dFCNOPY = dFCNOPYSpinner.getSelectedItem().toString();
        bBCabinet = bBCabinetSpinner.getSelectedItem().toString();
        mainDoor = mainDoorSpineer.getSelectedItem().toString();
        shelter = shelterSpinner.getSelectedItem().toString();
        police = policeSpinner.getSelectedItem().toString();
        Technician = TechnicianSpinner.getSelectedItem().toString();
        ambulane = ambulaneSpinner.getSelectedItem().toString();
        powerPlant = powerPlantSpinner.getSelectedItem().toString();
        fire = fireSpinner.getSelectedItem().toString();

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
