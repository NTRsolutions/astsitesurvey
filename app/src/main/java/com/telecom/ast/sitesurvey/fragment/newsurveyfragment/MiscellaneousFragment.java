package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;
import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.component.FNEditText;
import com.telecom.ast.sitesurvey.component.FNTextView;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.filepicker.FNFilePicker;
import com.telecom.ast.sitesurvey.filepicker.model.MediaFile;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.ASTUtil;
import com.telecom.ast.sitesurvey.utils.FNObjectUtil;
import com.telecom.ast.sitesurvey.utils.FNReqResCode;
import com.telecom.ast.sitesurvey.utils.FilePickerHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class MiscellaneousFragment extends MainFragment {
    private TextInputEditText etGateSize, etPlot, etApproachRoad, etboundryheight;
    private TextInputEditText etshater1size;
    private ImageView image1, image4, image5, image6,
            DiagramImage, SiteHygieneImage;
    private static boolean ismage1, ismage4, ismage5, ismage6, isDiagramImage;
    private File shaterimage1File, GateSizeimage4File, Plotimage5File,
            Approachimage6File, DiagramFile, SiteHygieneFile;

    private String shater1size, GateSize, Plot, ApproachRoad, boundryheight, Boundarywall;
    private String strUserId, strSiteId, CurtomerSite_Id,
            signagestatus, cablelebelling, stretOtherIssues,
            fSRCopy, ShelterCovered, SheltLeakage,
            AnyOtherItem, dFCNOPY, bBCabinet, mainDoor, shelter, shelterlock, police,
            ambulane, Technician, VisitRegister, powerPlant, fire, stretWaterLogging, stretOwnerIssues, stretCriticalOwnerissue;
    private FNTextView fSRCopyspinner;

    private Spinner typeBoundarywall, signagestatusSpinner, cablelebellingSpinner, etOtherIssuesSpinner,
            ShelterCoveredSpinner, SheltLeakageSpinner,
            etAnyOtherItemSpinner, dgCNOPYSpinner, bBCabinetSpinner, mainDoorSpineer, shelterSpinner, shelterlockSpinner, policeSpinner,
            ambulaneSpinner, TechnicianSpinner, powerPlantSpinner, fireSpinner, etWaterLogging, etVisitRegister, etOwnerIssues, etCriticalOwnerissue;

    private Button btnSubmit;
    private SharedPreferences userPref;
    private Spinner dangerSpinner, cautionSpinner, warningSpinner, ShelterFloorSpinner, ShelterRoofSpinner, ShelterFoundationSpinner,
            boundarywallStatusSpinner, levelRoadwrtSpinner;
    private String strdangerSpinner, strcautionSpinner, strwarningSpinner, strShelterFloorSpinner, strShelterRoofSpinner,
            strShelterFoundationSpinner,
            strboundarywallStatusSpinner, strlevelRoadwrtSpinner;
    private LinearLayout fSRCopyspinnerLayout;

    @Override
    protected int fragmentLayout() {
        return R.layout.miscellaneous_fragment;
    }

    @Override
    protected void loadView() {
        etshater1size = findViewById(R.id.etshater1size);
        etGateSize = findViewById(R.id.etGateSize);
        etPlot = findViewById(R.id.etPlot);
        etApproachRoad = findViewById(R.id.etApproachRoad);
        etboundryheight = findViewById(R.id.etboundryheight);
        image1 = findViewById(R.id.image1);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);
        DiagramImage = findViewById(R.id.DiagramImage);
        SiteHygieneImage = findViewById(R.id.SiteHygieneImage);
        typeBoundarywall = findViewById(R.id.typeBoundarywall);
        etVisitRegister = findViewById(R.id.etVisitRegister);
        signagestatusSpinner = findViewById(R.id.signagestatusSpinner);
        cablelebellingSpinner = findViewById(R.id.cablelebellingSpinner);
        etOtherIssuesSpinner = findViewById(R.id.etOtherIssuesSpinner);
        fSRCopyspinner = findViewById(R.id.fSRCopyspinner);
        ShelterCoveredSpinner = findViewById(R.id.ShelterCoveredSpinner);
        SheltLeakageSpinner = findViewById(R.id.SheltLeakageSpinner);
        etAnyOtherItemSpinner = findViewById(R.id.etAnyOtherItemSpinner);
        dgCNOPYSpinner = findViewById(R.id.dFCNOPYSpinner);
        bBCabinetSpinner = findViewById(R.id.bBCabinetSpinner);
        mainDoorSpineer = findViewById(R.id.mainDoorSpineer);
        shelterSpinner = findViewById(R.id.shelterSpinner);
        shelterlockSpinner = findViewById(R.id.shelterlockSpinner);
        policeSpinner = findViewById(R.id.policeSpinner);
        ambulaneSpinner = findViewById(R.id.ambulaneSpinner);
        TechnicianSpinner = findViewById(R.id.TechnicianSpinner);
        powerPlantSpinner = findViewById(R.id.powerPlantSpinner);
        fireSpinner = findViewById(R.id.fireSpinner);
        btnSubmit = findViewById(R.id.btnSubmit);
        etWaterLogging = findViewById(R.id.etWaterLogging);
        etOwnerIssues = findViewById(R.id.etOwnerIssues);
        etCriticalOwnerissue = findViewById(R.id.etCriticalOwnerissue);
        dangerSpinner = findViewById(R.id.dangerSpinner);
        cautionSpinner = findViewById(R.id.cautionSpinner);
        warningSpinner = findViewById(R.id.warningSpinner);
        fSRCopyspinnerLayout = findViewById(R.id.fSRCopyspinnerLayout);

        ShelterFloorSpinner = findViewById(R.id.ShelterFloorSpinner);
        ShelterRoofSpinner = findViewById(R.id.ShelterRoofSpinner);
        ShelterFoundationSpinner = findViewById(R.id.ShelterFoundationSpinner);
        boundarywallStatusSpinner = findViewById(R.id.boundarywallStatusSpinner);
        levelRoadwrtSpinner = findViewById(R.id.levelRoadwrtSpinner);
    }

    @Override
    protected void setClickListeners() {
        image1.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        image6.setOnClickListener(this);
        DiagramImage.setOnClickListener(this);
//        typeBoundarywall.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        SiteHygieneImage.setOnClickListener(this);
        fSRCopyspinner.setOnClickListener(this);
        fSRCopyspinnerLayout.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    @Override
    protected void dataToView() {
        getUserPref();
        setSpinnerValue();


    }


    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    public void setSpinnerValue() {
        final String typeboundrywall_array[] = {"RCC Wall", "Fencing"};
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

        final String etOtherIssues_array[] = {" Municipality tax due etc", "diesel filler", "Gram punchayat Tax ", "State devlopment authority tax", "No  dues certificate to be taken"};
        ArrayAdapter<String> etOtherIssuesadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etOtherIssues_array);
        etOtherIssuesSpinner.setAdapter(etOtherIssuesadapter);
        if (stretOtherIssues != null && !stretOtherIssues.equals("")) {
            for (int i = 0; i < etOtherIssues_array.length; i++) {
                if (stretOtherIssues.equalsIgnoreCase(etOtherIssues_array[i])) {
                    etOtherIssuesSpinner.setSelection(i);
                } else {
                    etOtherIssuesSpinner.setSelection(0);
                }
            }
        }

    /*    final String fSRCopyspinner_array[] = {"DG ", "AC", "PM Report"};
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
        }*/

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

        final String SheltLeakageSpinner_array[] = {"Yes", "No"};
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
        if (!isEmptyStr(AnyOtherItem)) {
            for (int i = 0; i < etAnyOtherItemSpinner_array.length; i++) {
                if (AnyOtherItem.equalsIgnoreCase(etAnyOtherItemSpinner_array[i])) {
                    etAnyOtherItemSpinner.setSelection(i);
                } else {
                    etAnyOtherItemSpinner.setSelection(0);
                }
            }
        }

        final String dFCNOPYSpinner_array[] = {"Available", "Damage/Leakage ", "Not Available"};
        ArrayAdapter<String> dFCNOPYSpinneradapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, dFCNOPYSpinner_array);
        dgCNOPYSpinner.setAdapter(dFCNOPYSpinneradapter);
        if (!isEmptyStr(dFCNOPY)) {
            for (int i = 0; i < dFCNOPYSpinner_array.length; i++) {
                if (dFCNOPY.equalsIgnoreCase(dFCNOPYSpinner_array[i])) {
                    dgCNOPYSpinner.setSelection(i);
                } else {
                    dgCNOPYSpinner.setSelection(0);
                }
            }
        }


        final String powerPlantSpinner_array[] = {"Available", "Not Available"};
        ArrayAdapter<String> powerPlantSpinner_arrayadapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, powerPlantSpinner_array);
        powerPlantSpinner.setAdapter(powerPlantSpinner_arrayadapter);
        if (!isEmptyStr(powerPlant)) {
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
        if (!isEmptyStr(fire)) {
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
        if (!isEmptyStr(bBCabinet)) {
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
        if (!isEmptyStr(mainDoor)) {
            for (int i = 0; i < mainDoorSpineer_array.length; i++) {
                if (mainDoor.equalsIgnoreCase(mainDoorSpineer_array[i])) {
                    mainDoorSpineer.setSelection(i);
                } else {
                    mainDoorSpineer.setSelection(0);
                }
            }
        }


        final String shelterSpinner_array[] = {"Door Working", "Not Working", "Broken"};
        ArrayAdapter<String> shelterSpinnerApater = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, shelterSpinner_array);
        shelterSpinner.setAdapter(shelterSpinnerApater);
        if (!isEmptyStr(shelter)) {
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
        if (!isEmptyStr(police)) {
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
        if (!isEmptyStr(ambulane)) {
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
        if (!isEmptyStr(Technician)) {
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
            ismage4 = false;
            ismage5 = false;
            ismage6 = false;
            String imageName = CurtomerSite_Id + "_MiscItem_1_Shelter.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.image4) {
            String imageName = CurtomerSite_Id + "_MiscItem_1_MainGateSize.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
            ismage1 = false;
            ismage4 = true;
            ismage5 = false;
            ismage6 = false;
        } else if (view.getId() == R.id.image5) {
            ismage1 = false;
            ismage4 = false;
            ismage5 = true;
            ismage6 = false;
            String imageName = CurtomerSite_Id + "_MiscItem_1_Plot.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.image6) {
            ismage1 = false;
            ismage4 = false;
            ismage5 = false;
            ismage6 = true;
            String imageName = CurtomerSite_Id + "_MiscItem_1_ApproachRoad.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.DiagramImage) {
            ismage1 = false;
            ismage4 = false;
            ismage5 = false;
            ismage6 = false;
            isDiagramImage = true;
            String imageName = CurtomerSite_Id + "_MiscItem_1_LayoutDiagram.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.SiteHygieneImage) {
            ismage1 = false;
            ismage4 = false;
            ismage5 = false;
            ismage6 = false;
            isDiagramImage = false;
            String imageName = CurtomerSite_Id + "_MiscItem_1_SiteHygiene.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();

            }
        } else if (view.getId() == R.id.fSRCopyspinnerLayout || view.getId() == R.id.fSRCopyspinner) {
            showSelectFaultyItemDialog();
        }

    }


    // ----validation -----
    private boolean isValidate() {
        shater1size = getTextFromView(this.etshater1size);
        GateSize = getTextFromView(this.etGateSize);
        Plot = getTextFromView(this.etPlot);
        ApproachRoad = getTextFromView(this.etApproachRoad);
        boundryheight = getTextFromView(this.etboundryheight);
        Boundarywall = typeBoundarywall.getSelectedItem().toString();
        VisitRegister = etVisitRegister.getSelectedItem().toString();
        signagestatus = signagestatusSpinner.getSelectedItem().toString();
        cablelebelling = cablelebellingSpinner.getSelectedItem().toString();
        stretOtherIssues = etOtherIssuesSpinner.getSelectedItem().toString();
        fSRCopy = getTextFromView(this.fSRCopyspinner);
        ShelterCovered = ShelterCoveredSpinner.getSelectedItem().toString();
        SheltLeakage = SheltLeakageSpinner.getSelectedItem().toString();
        AnyOtherItem = etAnyOtherItemSpinner.getSelectedItem().toString();
        dFCNOPY = dgCNOPYSpinner.getSelectedItem().toString();
        bBCabinet = bBCabinetSpinner.getSelectedItem().toString();
        mainDoor = mainDoorSpineer.getSelectedItem().toString();
        shelter = shelterSpinner.getSelectedItem().toString();
        shelterlock = shelterlockSpinner.getSelectedItem().toString();
        police = policeSpinner.getSelectedItem().toString();
        Technician = TechnicianSpinner.getSelectedItem().toString();
        ambulane = ambulaneSpinner.getSelectedItem().toString();
        powerPlant = powerPlantSpinner.getSelectedItem().toString();
        fire = fireSpinner.getSelectedItem().toString();
        stretWaterLogging = etWaterLogging.getSelectedItem().toString();
        stretOwnerIssues = etOwnerIssues.getSelectedItem().toString();
        stretCriticalOwnerissue = etCriticalOwnerissue.getSelectedItem().toString();
        strdangerSpinner = dangerSpinner.getSelectedItem().toString();
        strcautionSpinner = cautionSpinner.getSelectedItem().toString();
        strwarningSpinner = warningSpinner.getSelectedItem().toString();

        strShelterFloorSpinner = ShelterFloorSpinner.getSelectedItem().toString();
        strShelterRoofSpinner = ShelterRoofSpinner.getSelectedItem().toString();
        strShelterFoundationSpinner = ShelterFoundationSpinner.getSelectedItem().toString();
        strboundarywallStatusSpinner = boundarywallStatusSpinner.getSelectedItem().toString();
        strlevelRoadwrtSpinner = levelRoadwrtSpinner.getSelectedItem().toString();

        if (isEmptyStr(shater1size)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Shelter  Size");
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
        } else if (shaterimage1File == null || !shaterimage1File.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select shater  1 Photo");
            return false;
        } else if (GateSizeimage4File == null || !GateSizeimage4File.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select GateSize   Photo");
            return false;
        } else if (Plotimage5File == null || !Plotimage5File.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Plot  Photo");
            return false;
        } else if (Approachimage6File == null || !Approachimage6File.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Approach  Photo");
            return false;
        } else if (DiagramFile == null || !DiagramFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Layout Diagram Image");
            return false;
        } else if (SiteHygieneFile == null || !SiteHygieneFile.exists()) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select SiteHygiene Image");
            return false;
        } else if (isEmptyStr(stretWaterLogging)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Select Water Logging");
            return false;
        }


        return true;
    }


    public void saveBasicDataonServer() {
        if (ASTUIUtil.isOnline(getContext())) {
            final ASTProgressBar progressBar = new ASTProgressBar(getContext());
            progressBar.show();
            String serviceURL = Constant.BASE_URL + Constant.SurveyDataSave;
            JSONObject jsonObject = new JSONObject();
            jsonObject = getAlljsonObjectData();
            HashMap<String, String> payloadList = new HashMap<String, String>();
            payloadList.put("JsonData", jsonObject.toString());
            MultipartBody.Builder multipartBody = setMultipartBodyVaule();
            FileUploaderHelper fileUploaderHelper = new FileUploaderHelper(getContext(), payloadList, multipartBody, serviceURL) {
                @Override
                public void receiveData(String result) {
                    ContentData data = new Gson().fromJson(result, ContentData.class);
                    if (data != null) {
                        if (data.getStatus() == 1) {
                            ASTUIUtil.showToast("Site MiscItem Details Saved Successfully.");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast("Site MiscItem Details  has not been updated!");
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


    public JSONObject getAlljsonObjectData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Site_ID", strSiteId);
            jsonObject.put("User_ID", strUserId);
            jsonObject.put("Activity", "MiscItem");
            jsonObject.put("CablelebellingandTagging", cablelebelling);
            jsonObject.put("VisitRegister", VisitRegister);
            jsonObject.put("FSRCopy", fSRCopy);
            jsonObject.put("OtherIssues", stretOtherIssues);
            jsonObject.put("OwnerIssues", stretOwnerIssues);
            jsonObject.put("CriticalOwnerIssues", stretCriticalOwnerissue);
            jsonObject.put("ShelterCovered", ShelterCovered);
            jsonObject.put("ShelterLeakage", SheltLeakage);
            jsonObject.put("AnyOtherItem", AnyOtherItem);
            jsonObject.put("WaterLogging", stretWaterLogging);
            jsonObject.put("ShelterFloor", strShelterFloorSpinner);
            jsonObject.put("ShelterRoof", strShelterRoofSpinner);
            jsonObject.put("ShelterFoundation", strShelterFoundationSpinner);
            jsonObject.put("LevelRoadwrt", strlevelRoadwrtSpinner);
            // jsonObject.put("SiteHygiene", siteHygiene);

            JSONObject MiscItemData1 = new JSONObject();
            MiscItemData1.put("Code", "1");
            MiscItemData1.put("Item", "Shelter");
            MiscItemData1.put("Key", "");
            MiscItemData1.put("Status", "");
            MiscItemData1.put("Type", "");
            MiscItemData1.put("Size", shater1size);

     /*       JSONObject MiscItemData2 = new JSONObject();
            MiscItemData2.put("Code", "1");
            MiscItemData2.put("Item", "Shelter2");
            MiscItemData2.put("Key", "");
            MiscItemData2.put("Status", "");
            MiscItemData2.put("Type", "");
            MiscItemData2.put("Size", shater2size);*/

/*
            JSONObject MiscItemData3 = new JSONObject();
            MiscItemData3.put("Code", "1");
            MiscItemData3.put("Item", "OutdoorBTSArea");
            MiscItemData3.put("Key", "");
            MiscItemData3.put("Status", "");
            MiscItemData3.put("Type", "");
            MiscItemData3.put("Size", outDoorsize);*/


            JSONObject MiscItemData4 = new JSONObject();
            MiscItemData4.put("Code", "1");
            MiscItemData4.put("Item", "MainGateSize");
            MiscItemData4.put("Key", "");
            MiscItemData4.put("Status", "");
            MiscItemData4.put("Type", "");
            MiscItemData4.put("Size", GateSize);


            JSONObject MiscItemData5 = new JSONObject();
            MiscItemData5.put("Code", "1");
            MiscItemData5.put("Item", "Plot");
            MiscItemData5.put("Status", "");
            MiscItemData5.put("Type", "");
            MiscItemData5.put("Key", "");
            MiscItemData5.put("Size", Plot);

            JSONObject MiscItemData6 = new JSONObject();
            MiscItemData1.put("Code", "1");
            MiscItemData6.put("Item", "ApproachRoad");
            MiscItemData6.put("Key", "");
            MiscItemData6.put("Status", "");
            MiscItemData6.put("Type", "");
            MiscItemData6.put("Size", ApproachRoad);

            JSONObject MiscItemData7 = new JSONObject();
            MiscItemData7.put("Code", "1");
            MiscItemData7.put("Item", "Boundrywall");
            MiscItemData7.put("Key", "");
            MiscItemData7.put("Status", strboundarywallStatusSpinner);
            MiscItemData7.put("Type", Boundarywall);
            MiscItemData7.put("Size", boundryheight);


            JSONObject MiscItemData8 = new JSONObject();
            MiscItemData8.put("Code", "1");
            MiscItemData8.put("Item", "Site Keys");
            MiscItemData8.put("Key", "DG CNOPY");
            MiscItemData8.put("Status", dFCNOPY);
            MiscItemData8.put("Type", "");
            MiscItemData8.put("Size", "0");

            JSONObject MiscItemData9 = new JSONObject();
            MiscItemData9.put("Code", "1");
            MiscItemData9.put("Item", "Site Keys");
            MiscItemData9.put("Key", "BB Cabinet");
            MiscItemData9.put("Status", bBCabinet);
            MiscItemData9.put("Type", "");
            MiscItemData9.put("Size", "0");

            JSONObject MiscItemData10 = new JSONObject();
            MiscItemData10.put("Code", "1");
            MiscItemData10.put("Item", "Site Keys");
            MiscItemData10.put("Key", "Power Plant");
            MiscItemData10.put("Status", powerPlant);
            MiscItemData10.put("Type", "");
            MiscItemData10.put("Size", "0");


            JSONObject MiscItemData11 = new JSONObject();
            MiscItemData11.put("Code", "1");
            MiscItemData11.put("Item", "Site Keys");
            MiscItemData11.put("Key", "Shelter");
            MiscItemData11.put("Status", shelter);
            MiscItemData11.put("Type", "");
            MiscItemData11.put("Size", "0");

            JSONObject MiscItemData20 = new JSONObject();
            MiscItemData20.put("Code", "1");
            MiscItemData20.put("Item", "Site Keys");
            MiscItemData20.put("Key", "Shelter Lock");
            MiscItemData20.put("Status", shelterlock);
            MiscItemData20.put("Type", "");
            MiscItemData20.put("Size", "0");


            JSONObject MiscItemData12 = new JSONObject();
            MiscItemData12.put("Code", "1");
            MiscItemData12.put("Item", "Site Keys");
            MiscItemData12.put("Key", "Main Door");
            MiscItemData12.put("Status", mainDoor);
            MiscItemData12.put("Type", "");
            MiscItemData12.put("Size", "0");


            JSONObject MiscItemData13 = new JSONObject();
            MiscItemData13.put("Code", "1");
            MiscItemData13.put("Item", "Emergency Contact No Plate");
            MiscItemData13.put("Key", "Police");
            MiscItemData13.put("Status", police);
            MiscItemData13.put("Type", "");
            MiscItemData13.put("Size", "0");

            JSONObject MiscItemData14 = new JSONObject();
            MiscItemData14.put("Code", "1");
            MiscItemData14.put("Item", "Emergency Contact No Plate");
            MiscItemData14.put("Key", "Ambulane");
            MiscItemData14.put("Status", ambulane);
            MiscItemData14.put("Type", "");
            MiscItemData14.put("Size", "0");

            JSONObject MiscItemData15 = new JSONObject();
            MiscItemData15.put("Code", "1");
            MiscItemData15.put("Item", "Emergency Contact No Plate");
            MiscItemData15.put("Key", "Fire");
            MiscItemData15.put("Status", fire);
            MiscItemData15.put("Type", "");
            MiscItemData15.put("Size", "0");

            JSONObject MiscItemData16 = new JSONObject();
            MiscItemData16.put("Code", "1");
            MiscItemData16.put("Item", "Emergency Contact No Plate");
            MiscItemData16.put("Key", "Technician");
            MiscItemData16.put("Status", Technician);
            MiscItemData16.put("Type", "");
            MiscItemData16.put("Size", "0");


            JSONObject MiscItemData18 = new JSONObject();
            MiscItemData18.put("Code", "1");
            MiscItemData18.put("Item", "LayoutDiagram");
            MiscItemData18.put("Key", "");
            MiscItemData18.put("Status", "");
            MiscItemData18.put("Type", "");
            MiscItemData18.put("Size", "0");

            JSONObject MiscItemData19 = new JSONObject();
            MiscItemData19.put("Code", "1");
            MiscItemData19.put("Item", "SiteHygiene");
            MiscItemData19.put("Key", "");
            MiscItemData19.put("Status", "");
            MiscItemData19.put("Type", "");
            MiscItemData19.put("Size", "0");

            JSONObject MiscItemData17 = new JSONObject();
            MiscItemData17.put("Code", "1");
            MiscItemData17.put("Item", "Signage Board");
            MiscItemData17.put("Key", "Danger");
            MiscItemData17.put("Status", strdangerSpinner);
            MiscItemData17.put("Type", "");
            MiscItemData17.put("Size", "0");

            JSONObject MiscItemData120 = new JSONObject();
            MiscItemData120.put("Code", "1");
            MiscItemData120.put("Item", "Signage Board");
            MiscItemData120.put("Key", "Caution");
            MiscItemData120.put("Status", strcautionSpinner);
            MiscItemData120.put("Type", "");
            MiscItemData120.put("Size", "0");

            JSONObject MiscItemData121 = new JSONObject();
            MiscItemData121.put("Code", "1");
            MiscItemData121.put("Item", "Signage Board");
            MiscItemData121.put("Key", "Warning");
            MiscItemData121.put("Status", strwarningSpinner);
            MiscItemData121.put("Type", "");
            MiscItemData121.put("Size", "0");

            JSONArray MiscItemDataarray = new JSONArray();
            MiscItemDataarray.put(MiscItemData1);
            //  MiscItemDataarray.put(MiscItemData2);
            // MiscItemDataarray.put(MiscItemData3);
            MiscItemDataarray.put(MiscItemData4);
            MiscItemDataarray.put(MiscItemData5);
            MiscItemDataarray.put(MiscItemData6);
            MiscItemDataarray.put(MiscItemData8);
            MiscItemDataarray.put(MiscItemData9);
            MiscItemDataarray.put(MiscItemData10);
            MiscItemDataarray.put(MiscItemData11);
            MiscItemDataarray.put(MiscItemData20);
            MiscItemDataarray.put(MiscItemData12);
            MiscItemDataarray.put(MiscItemData13);
            MiscItemDataarray.put(MiscItemData14);
            MiscItemDataarray.put(MiscItemData15);
            MiscItemDataarray.put(MiscItemData16);
            MiscItemDataarray.put(MiscItemData17);
            MiscItemDataarray.put(MiscItemData120);
            MiscItemDataarray.put(MiscItemData121);
            MiscItemDataarray.put(MiscItemData18);
            MiscItemDataarray.put(MiscItemData19);
            jsonObject.put("MiscItemData", MiscItemDataarray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //add pm install images into MultipartBody for send as multipart
    private MultipartBody.Builder setMultipartBodyVaule() {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (shaterimage1File.exists()) {
            multipartBody.addFormDataPart(shaterimage1File.getName(), shaterimage1File.getName(), RequestBody.create(MEDIA_TYPE_PNG, shaterimage1File));
        }
        if (GateSizeimage4File.exists()) {
            multipartBody.addFormDataPart(GateSizeimage4File.getName(), GateSizeimage4File.getName(), RequestBody.create(MEDIA_TYPE_PNG, GateSizeimage4File));
        }
        if (Plotimage5File.exists()) {
            multipartBody.addFormDataPart(Plotimage5File.getName(), Plotimage5File.getName(), RequestBody.create(MEDIA_TYPE_PNG, Plotimage5File));
        }

        if (Approachimage6File.exists()) {
            multipartBody.addFormDataPart(Approachimage6File.getName(), Approachimage6File.getName(), RequestBody.create(MEDIA_TYPE_PNG, Approachimage6File));
        }

        if (DiagramFile.exists()) {
            multipartBody.addFormDataPart(DiagramFile.getName(), DiagramFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, DiagramFile));
        }
        if (SiteHygieneFile.exists()) {
            multipartBody.addFormDataPart(SiteHygieneFile.getName(), SiteHygieneFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, SiteHygieneFile));
        }


        return multipartBody;
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
        if (resultCode == Activity.RESULT_OK) {
            onCaptureImageResult();
        }
    }

    //capture image compress
    private void onCaptureImageResult() {
        if (ismage1) {
            String imageName = CurtomerSite_Id + "_MiscItem_1_Shelter.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, image1, imageName);
            }
        } else if (ismage4) {
            String imageName = CurtomerSite_Id + "_MiscItem_1_MainGateSize.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, image4, imageName);
            }
        } else if (ismage5) {
            String imageName = CurtomerSite_Id + "_MiscItem_1_Plot.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, image5, imageName);
            }
        } else if (ismage6) {
            String imageName = CurtomerSite_Id + "_MiscItem_1_ApproachRoad.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, image6, imageName);
            }
        } else if (isDiagramImage) {
            String imageName = CurtomerSite_Id + "_MiscItem_1_LayoutDiagram.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, DiagramImage, imageName);
            }
        } else {
            String imageName = CurtomerSite_Id + "_MiscItem_1_SiteHygiene.jpg";
            File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
            if (file.exists()) {
                compresImage(file, imageName, SiteHygieneImage, imageName);
            }
        }


    }


    //compres image
    private void compresImage(final File file, final String fileName,
                              final ImageView imageView, final String imageName) {
        new AsyncTask<Void, Void, Boolean>() {
            File imgFile;
            Uri uri;
            ASTProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = new ASTProgressBar(getContext());
                progressBar.show();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
//compress file
                Boolean flag = false;
                int ot = FilePickerHelper.getExifRotation(file);
                Bitmap bitmap = FilePickerHelper.compressImage(file.getAbsolutePath(), ot, 800.0f, 800.0f, imageName);
                if (bitmap != null) {
                    uri = FilePickerHelper.getImageUri(getContext(), bitmap);
//save compresed file into location
                    imgFile = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator, fileName);
                    try {
                        if (imgFile.exists()) {
                            imgFile.delete();
                        }
                        InputStream iStream = getContext().getContentResolver().openInputStream(uri);
                        byte[] inputData = FilePickerHelper.getBytes(iStream);

                        FileOutputStream fOut = new FileOutputStream(imgFile);
                        fOut.write(inputData);
                        //   bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        iStream.close();
                        flag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                super.onPostExecute(flag);
                if (ismage1) {
                    shaterimage1File = imgFile;
                } else if (ismage4) {
                    GateSizeimage4File = imgFile;
                } else if (ismage5) {
                    Plotimage5File = imgFile;
                } else if (ismage6) {
                    Approachimage6File = imgFile;
                } else if (isDiagramImage) {
                    DiagramFile = imgFile;
                } else {
                    SiteHygieneFile = imgFile;
                }


                imageView.setImageURI(uri);
                if (progressBar.isShowing())

                {
                    progressBar.dismiss();
                }
            }
        }.

                execute();

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


    protected CharSequence[] fcrselectItem = {"DG ", "AC", "PM Report"};
    protected ArrayList<CharSequence> selectedfaulityItem = new ArrayList<CharSequence>();

    protected void showSelectFaultyItemDialog() {
        boolean[] checkedItems = new boolean[fcrselectItem.length];
        int count = fcrselectItem.length;
        for (int i = 0; i < count; i++)
            checkedItems[i] = selectedfaulityItem.contains(fcrselectItem[i]);
        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked)
                    selectedfaulityItem.add(fcrselectItem[which]);
                else
                    selectedfaulityItem.remove(fcrselectItem[which]);
                onChangeSelectedItem();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select FSR Copy");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setMultiChoiceItems(fcrselectItem, checkedItems, coloursDialogListener);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    protected void onChangeSelectedItem() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence selectitem : selectedfaulityItem)
            stringBuilder.append(selectitem + ",");
        fSRCopyspinner.setText(stringBuilder.toString());

    }

}
