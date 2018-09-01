package com.telecom.ast.sitesurvey.fragment.newsurveyfragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.component.ASTProgressBar;
import com.telecom.ast.sitesurvey.constants.Constant;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.framework.FileUploaderHelper;
import com.telecom.ast.sitesurvey.model.ContentData;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.ASTUtil;
import com.telecom.ast.sitesurvey.utils.FilePickerHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isEmptyStr;

public class EarthingSystemFragment extends MainFragment {
    private AppCompatEditText etVoltageEarth, etwireconnected, etvalueInterGrid;
    private String NoEarthPits, interConEarthPits, VoltageEarth, dgwireconnected, ebwireconnected;
    private Button btnSubmit;
    private String strUserId, strSiteId, CurtomerSite_Id;
    private SharedPreferences userPref;

    private Spinner dgnaturelConnectSpinner, ebnaturelConnectSpinner, InterEarthPitsSpinner,
            IGBStatusSpinner, EGBStatusSpinner, IGBconnectedStatusSpinner, egbconnectedStatusSpinner, etNoEarthPits;
    private String strIGBStatusSpinner, strEGBStatusSpinner, strIGBconnectedStatusSpinner, stregbconnectedStatusSpinner;

    private LinearLayout igbConnectedLayout, egbConnectedLayout;
    private LinearLayout ValueofEarthpitLayout1, ValueofEarthpitLayout2, ValueofEarthpitLayout3, ValueofEarthpitLayout4;
    private AppCompatEditText etValueofEarthpit1, etValueofEarthpit2, etValueofEarthpit3, etValueofEarthpit4;
    private String stretValueofEarthpit1="0", stretValueofEarthpit2, stretValueofEarthpit3, stretValueofEarthpit4, stretvalueInterGrid;
    private LinearLayout EarthPitsImageLayout;
    private ImageView EarthPitsImage;
    private static File EarthPitsFile;

    @Override
    protected int fragmentLayout() {
        return R.layout.earthingsystem_fragment;
    }

    @Override
    protected void loadView() {
        etNoEarthPits = this.findViewById(R.id.etNoEarthPits);
        etVoltageEarth = this.findViewById(R.id.etVoltageEarth);
        btnSubmit = this.findViewById(R.id.btnSubmit);
        dgnaturelConnectSpinner = this.findViewById(R.id.dgnaturelConnectSpinner);
        ebnaturelConnectSpinner = this.findViewById(R.id.ebnaturelConnectSpinner);
        InterEarthPitsSpinner = this.findViewById(R.id.InterEarthPitsSpinner);
        IGBStatusSpinner = this.findViewById(R.id.IGBStatusSpinner);
        EGBStatusSpinner = this.findViewById(R.id.EGBStatusSpinner);
        IGBconnectedStatusSpinner = this.findViewById(R.id.IGBconnectedStatusSpinner);
        egbconnectedStatusSpinner = this.findViewById(R.id.egbconnectedStatusSpinner);
        igbConnectedLayout = this.findViewById(R.id.igbConnectedLayout);
        egbConnectedLayout = this.findViewById(R.id.egbConnectedLayout);
        etvalueInterGrid = this.findViewById(R.id.etvalueInterGrid);
        ValueofEarthpitLayout1 = this.findViewById(R.id.ValueofEarthpitLayout1);
        ValueofEarthpitLayout2 = this.findViewById(R.id.ValueofEarthpitLayout2);
        ValueofEarthpitLayout3 = this.findViewById(R.id.ValueofEarthpitLayout3);
        ValueofEarthpitLayout4 = this.findViewById(R.id.ValueofEarthpitLayout4);

        etValueofEarthpit1 = this.findViewById(R.id.etValueofEarthpit1);
        etValueofEarthpit2 = this.findViewById(R.id.etValueofEarthpit2);
        etValueofEarthpit3 = this.findViewById(R.id.etValueofEarthpit3);
        etValueofEarthpit4 = this.findViewById(R.id.etValueofEarthpit4);
        EarthPitsImageLayout = this.findViewById(R.id.EarthPitsImageLayout);
        EarthPitsImage = this.findViewById(R.id.EarthPitsImage);
    }

    @Override
    protected void setClickListeners() {
        btnSubmit.setOnClickListener(this);
        EarthPitsImage.setOnClickListener(this);
    }

    @Override
    protected void setAccessibility() {

    }

    private void getUserPref() {
        userPref = getContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        strUserId = userPref.getString("USER_ID", "");
        strSiteId = userPref.getString("Site_ID", "");
        CurtomerSite_Id = userPref.getString("CurtomerSite_Id", "");
    }

    @Override
    protected void dataToView() {
        getUserPref();
        setSpinnerValue();

        IGBStatusSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Available")) {
                    igbConnectedLayout.setVisibility(View.VISIBLE);

                } else {
                    igbConnectedLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        EGBStatusSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Available")) {
                    egbConnectedLayout.setVisibility(View.VISIBLE);

                } else {
                    egbConnectedLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        InterEarthPitsSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("Connected")) {
                    EarthPitsImageLayout.setVisibility(View.VISIBLE);

                } else {
                    EarthPitsImageLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        etNoEarthPits.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                if (selectedItem.equalsIgnoreCase("2")) {
                    ValueofEarthpitLayout1.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout2.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout3.setVisibility(View.GONE);
                    ValueofEarthpitLayout4.setVisibility(View.GONE);
                } else if (selectedItem.equalsIgnoreCase("3")) {
                    ValueofEarthpitLayout1.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout2.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout3.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout4.setVisibility(View.GONE);
                } else if (selectedItem.equalsIgnoreCase("4")) {
                    ValueofEarthpitLayout1.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout2.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout3.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout4.setVisibility(View.VISIBLE);
                } else {
                    ValueofEarthpitLayout1.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout2.setVisibility(View.VISIBLE);
                    ValueofEarthpitLayout3.setVisibility(View.GONE);
                    ValueofEarthpitLayout4.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



   /*     etNoEarthPits.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (etNoEarthPits.getText().toString().length() > 0) {
                    List<EditText> allEditTextList = new ArrayList<EditText>();

                    try {
                        lnrDynamicEditTextHolder.removeAllViews();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    int length = Integer.parseInt(etNoEarthPits.getText().toString());

                    for (int i = 0; i < length; i++) {
                        EditText editText = new EditText(getContext());
                        allEditTextList.add(editText);
                        editText.setId(i + 1);
                        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        editText.setHint("Value of Earthpit  " + (i + 1));
                        lnrDynamicEditTextHolder.addView(editText);
                    }
                    String[] strings = new String[allEditTextList.size()];
                    for (int i = 0; i < allEditTextList.size(); i++) {
                        strings[i] = allEditTextList.get(i).getText().toString();
                    }
                }
            }
        });*/
    }


    public void setSpinnerValue() {
        final String etfiredetectSpineer_array[] = {"Connected", "Not Connected"};
        ArrayAdapter<String> etfiredetect = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineer_array);
        dgnaturelConnectSpinner.setAdapter(etfiredetect);


        final String etfiredetectSpineerarray[] = {"Connected", "Not Connected"};
        ArrayAdapter<String> etty = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, etfiredetectSpineerarray);
        ebnaturelConnectSpinner.setAdapter(etty);


        final String InterEarthPitsSpinneraray[] = {"Connected", "Not Connected"};
        ArrayAdapter<String> InterEartharrat = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, InterEarthPitsSpinneraray);
        InterEarthPitsSpinner.setAdapter(InterEartharrat);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.EarthPitsImage) {
            String imageName = CurtomerSite_Id + "_Earthing_1_InterGridofEarthPits.jpg";
            FilePickerHelper.cameraIntent(getHostActivity(), imageName);
        } else if (view.getId() == R.id.btnSubmit) {
            if (isValidate()) {
                saveBasicDataonServer();
            }

        }
    }

    public boolean isValidate() {
        NoEarthPits = etNoEarthPits.getSelectedItem().toString();
        interConEarthPits = InterEarthPitsSpinner.getSelectedItem().toString();
        VoltageEarth = etVoltageEarth.getText().toString();
        dgwireconnected = dgnaturelConnectSpinner.getSelectedItem().toString();
        ebwireconnected = ebnaturelConnectSpinner.getSelectedItem().toString();
        strIGBStatusSpinner = IGBStatusSpinner.getSelectedItem().toString();
        strEGBStatusSpinner = EGBStatusSpinner.getSelectedItem().toString();
        strIGBconnectedStatusSpinner = IGBconnectedStatusSpinner.getSelectedItem().toString();
        stregbconnectedStatusSpinner = egbconnectedStatusSpinner.getSelectedItem().toString();
        stretvalueInterGrid = etvalueInterGrid.getText().toString();

        stretValueofEarthpit1 = etValueofEarthpit1.getText().toString();
        stretValueofEarthpit2 = etValueofEarthpit2.getText().toString();
        stretValueofEarthpit3 = etValueofEarthpit3.getText().toString();
        stretValueofEarthpit4 = etValueofEarthpit4.getText().toString();


        if (isEmptyStr(NoEarthPits)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter No of EarthPits");
            return false;
        }  else if (isEmptyStr(interConEarthPits)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Inter Connectivity of EarthPits");
            return false;
        } else if (isEmptyStr(VoltageEarth)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "Please Enter Voltage between Earth and Neutral");
            return false;
        } else if (isEmptyStr(dgwireconnected)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "DG neutral wire connected with earthing");
            return false;
        } else if (isEmptyStr(ebwireconnected)) {
            ASTUIUtil.shownewErrorIndicator(getContext(), "EB neutral wire connected with earthing");
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
            try {
                jsonObject.put("Site_ID", strSiteId);
                jsonObject.put("User_ID", strUserId);
                jsonObject.put("Activity", "Earthing");
                JSONObject EarthingData = new JSONObject();
                EarthingData.put("NoofEarthPits", NoEarthPits);
                EarthingData.put("InterGridConnectivityofEarthPits", interConEarthPits);
                EarthingData.put("DGneutralwireconnectedwithearthing", dgwireconnected);
                EarthingData.put("EBneutralwireconnectedwithearthing", ebwireconnected);
                EarthingData.put("VoltagebetweenEarthandNeutral", VoltageEarth);

                if (strIGBStatusSpinner.equalsIgnoreCase("Available")) {
                    EarthingData.put("IGB_Status", strIGBconnectedStatusSpinner);
                } else {
                    EarthingData.put("IGB_Status", strIGBStatusSpinner);
                }
                if (strEGBStatusSpinner.equalsIgnoreCase("Available")) {
                    EarthingData.put("EGB_Status", stregbconnectedStatusSpinner);
                } else {
                    EarthingData.put("EGB_Status", strEGBStatusSpinner);
                }
                EarthingData.put("EarthPit1Value", stretValueofEarthpit1);
                EarthingData.put("EarthPit2Value", stretValueofEarthpit2);
                EarthingData.put("EarthPit3Value", stretValueofEarthpit3);
                EarthingData.put("EarthPit4Value", stretValueofEarthpit4);
                EarthingData.put("InterGridValue", stretvalueInterGrid);
                jsonObject.put("EarthingData", EarthingData);
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
                            ASTUIUtil.showToast("Your Earthing System  Data save Successfully");
                            reloadBackScreen();
                        } else {
                            ASTUIUtil.alertForErrorMessage(Contants.Error, getContext());
                        }
                    } else {
                        ASTUIUtil.showToast(" Your Earthing System  Data   has not been updated!");
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
        if (EarthPitsFile != null && EarthPitsFile.exists()) {
            multipartBody.addFormDataPart(EarthPitsFile.getName(), EarthPitsFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, EarthPitsFile));
        }


        return multipartBody;
    }

    @Override
    public void updateOnResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            onCaptureImageResult();
        }
    }

    //capture image compress
    private void onCaptureImageResult() {
        String imageName = CurtomerSite_Id + "_Earthing_1_InterGridofEarthPits.jpg";
        File file = new File(ASTUtil.getExternalStorageFilePathCreateAppDirectory(getContext()) + File.separator + imageName);
        if (file.exists()) {
            compresImage(file, imageName, EarthPitsImage);
        }

    }


    //compres image
    private void compresImage(final File file, final String fileName, final ImageView imageView) {
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
                Bitmap bitmap = FilePickerHelper.compressImage(file.getAbsolutePath(), ot, 800.0f, 800.0f);
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
                // imageView.setImageBitmap(bitmap);
                EarthPitsFile = imgFile;

                imageView.setImageURI(uri);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        }.execute();

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
