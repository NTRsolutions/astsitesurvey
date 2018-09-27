package com.telecom.ast.sitesurvey.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.constants.Contants;
import com.telecom.ast.sitesurvey.utils.ASTObjectUtil;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AttendanceReportActivity extends AppCompatActivity {

    private TextView etAutopopulateddate;
    private Spinner taskSpinner;
    private View RemarkLayout;
    private AppCompatEditText etRemark;
    private Button btnSubmit;
    String strTask, stretAutopopulateddate, stretRemark;

    private String currentLongitude = "";
    private String currentLatitude = "";

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);
        etAutopopulateddate = this.findViewById(R.id.etAutopopulateddate);
        taskSpinner = this.findViewById(R.id.taskSpinner);
        RemarkLayout = this.findViewById(R.id.RemarkLayout);
        etRemark = this.findViewById(R.id.etRemark);
        btnSubmit = this.findViewById(R.id.btnSubmit);
        setTskListValue();
        etAutopopulateddate.setText(getCurrentDate());
        datatoView();
    }

    public void datatoView() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {

                }
            }
        });

    }

    public void setTskListValue() {
        final String itemCondition_array[] = {"Select",
                "Diesel Filling",
                "Warehouse",
                "Tech PM",
                "EB Bill collection/ Rectification",
                "Meeting",
                "In Office",
                "Site Survey",
                "No Plan",
                "Leave",
                "Other",};
        ArrayAdapter<String> homeadapter = new ArrayAdapter<String>(this, R.layout.spinner_row, itemCondition_array);
        taskSpinner.setAdapter(homeadapter);

        taskSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                RemarkLayout.setVisibility(selectedItem.equalsIgnoreCase("Other") ? View.VISIBLE : View.GONE);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }


    public static String getCurrentDate() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

    public boolean isValidate() {
        stretRemark = etRemark.getText().toString();
        strTask = taskSpinner.getSelectedItem().toString();
        stretAutopopulateddate = etAutopopulateddate.getText().toString();
        if (strTask == "" || taskSpinner.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            Toast.makeText(this, "Please Select Task List", Toast.LENGTH_LONG).show();
            return false;
        } else if (stretRemark == "" && taskSpinner.getSelectedItem().toString().equalsIgnoreCase("Other")) {
            Toast.makeText(this, "PPlease Enter ReMark", Toast.LENGTH_LONG).show();

            return false;
        }
        return true;

    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
               /* for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                }*/
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
       /* mFusedLocationClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());*/
    }

    //get location change
    public void onLocationChanged(Location location) {
        // New location has now been determined
        currentLongitude = Double.toString(location.getLongitude());
        currentLatitude = Double.toString(location.getLatitude());

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Log.d(Contants.LOG_TAG, "Current Location*******" + msg);

      /*  Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
         LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());*/
    }

    //get last location
    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // GPS location can be null if GPS is switched off
                if (location != null) {
                    onLocationChanged(location);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Contants.LOG_TAG, "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFusedLocationClient != null) {
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    //stop location update
    private void stopLocationUpdates() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

}
