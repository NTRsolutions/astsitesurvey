package com.telecom.ast.sitesurvey.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.AstAppUgradeDlgActivity;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.fragment.HeaderFragment;
import com.telecom.ast.sitesurvey.fragment.HomeFragment;
import com.telecom.ast.sitesurvey.fragment.MainFragment;
import com.telecom.ast.sitesurvey.runtimepermission.PermissionResultCallback;
import com.telecom.ast.sitesurvey.runtimepermission.PermissionUtils;
import com.telecom.ast.sitesurvey.utils.ASTReqResCode;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

import static com.telecom.ast.sitesurvey.ApplicationHelper.application;
import static com.telecom.ast.sitesurvey.utils.ASTObjectUtil.isNonEmptyStr;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    NavigationView navigationView;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private int REQUEST_CODE_GPS_PERMISSIONS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        application().setActivity(this);
        navigationView = this.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadPage();
        showNavigationMenuItem();
        runTimePermission();
        checkForceUpdateStatus();
        AstAppUgradeDlgActivity fnAppUgradeDlgActivity = new AstAppUgradeDlgActivity(MainActivity.this) {
            @Override
            public void onSkip() {
                ASTUIUtil.showToast("Please Update your App");
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        application().setActivity(this);
    }

    @Override
    public void onBackPressed() {
        if (this.closeDrawers()) {
            return;
        }
        if (this.getPageFragment() != null && this.getPageFragment().onBackPressed()) {
            if (this.getSupportFragmentManager().getBackStackEntryCount() == 1) {
                redirectToHomeMenu();
            } else {
                super.onBackPressed();
            }
        }
    }

    protected void loadPage() {
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        Bundle pageBundle = new Bundle();
        pageBundle.putString("headerTxt", "Home");
        pageBundle.putInt("MENU_ID", 0);
        application().lastMenuItem = menuItem;
        this.updateFragment(new HomeFragment(), pageBundle);
    }

    protected
    @IdRes
    int dataContainerResID() {
        try {
            return R.id.dataContainer;
        } catch (NoSuchFieldError e) {
            return 0;
        }
    }

    protected
    @IdRes
    int headerViewResID() {
        try {
            return R.id.headerFragment;
        } catch (NoSuchFieldError e) {
            return 0;
        }
    }

    protected
    @IdRes
    int navigationViewResID() {
        try {
            return R.id.nav_view;
        } catch (NoSuchFieldError e) {
            return 0;
        }
    }

    protected
    @IdRes
    int drawerLayoutResID() {
        try {
            return R.id.drawer_layout;
        } catch (NoSuchFieldError e) {
            return 0;
        }
    }


    @SuppressLint("ResourceType")
    public DrawerLayout drawerLayout() {
        if (dataContainerResID() > 0) {
            return (DrawerLayout) this.findViewById(drawerLayoutResID());
        }
        return null;
    }

    @SuppressLint("ResourceType")
    public View headerView() {
        if (headerViewResID() > 0) {
            return this.findViewById(headerViewResID());
        }
        return null;
    }

    @SuppressLint("ResourceType")
    public View navigationView() {
        if (navigationViewResID() > 0) {
            return this.findViewById(navigationViewResID());
        }
        return null;
    }

    public HeaderFragment headerFragment() {
        if (headerView() == null) {
            return null;
        }
        return (HeaderFragment) this.findFragmentById(headerViewResID());
    }

    public Fragment findFragmentById(int fragmentContainerID) {
        return this.getSupportFragmentManager().findFragmentById(fragmentContainerID);
    }

    protected MainFragment headerFragmentInstance() {
        return new HeaderFragment();
    }

    public void setDrawerState(boolean isEnabled) {
        if (this.drawerLayout() == null) {
            return;
        }
        if (isEnabled) {
            this.drawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            this.drawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public boolean closeDrawers() {
        if (this.drawerLayout() != null && this.drawerLayout().isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout().closeDrawers();
            return true;
        }
        return false;
    }

    public void showSideNavigationPanel() {
        if (drawerLayout() == null) {
            return;
        }
        if (this.drawerLayout().isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout().closeDrawer(GravityCompat.START);
        } else {
            this.hideKeyBoard();
            this.drawerLayout().openDrawer(GravityCompat.START);
        }
    }

    public void hideKeyBoard() {
        ASTUIUtil.hideKeyboard(this);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        bundle.putInt("MENU_ID", item.getItemId());
        if (id == R.id.nav_home) {
            bundle.putString("headerTxt", "Home");
            bundle.putInt("MENU_ID", 0);
        } else if (id == R.id.nav_tvComplaint) {
            //   bundle.putString("headerTxt", "Complaint");
            // bundle.putString("headerTxt", "About");
            Intent intent = new Intent(this, AttendanceReportActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void updateFragment(MainFragment pageFragment, Bundle bundle) {
        this.updateFragment(pageFragment, bundle, true, false);
    }


    public void updateFragment(MainFragment pageFragment, Bundle bundle, boolean replaceHeaderFragment, boolean animate) {
        this.hideKeyBoard();
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        String menuId = null;
        MainFragment headerFragment = replaceHeaderFragment ? headerFragmentInstance() : null;
        if (bundle != null) {
            pageFragment.setArguments(bundle);
            if (headerFragment != null) {
                headerFragment.setArguments(bundle);
            }
            menuId = String.valueOf(bundle.getInt("MENU_ID", 0));
        }
        if (isNonEmptyStr(menuId) && (!menuId.equalsIgnoreCase("0"))) {
            fragmentTransaction.addToBackStack(menuId);
            fragmentTransaction.replace(dataContainerResID(), pageFragment, menuId);
        } else {
            fragmentTransaction.addToBackStack(null);
            if (animate) {
                fragmentTransaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
            }
            if (pageFragment.getTargetFragment() == null) {
                pageFragment.setTargetFragment(getPageFragment(), 101);
            }
            fragmentTransaction.replace(dataContainerResID(), pageFragment);
        }

        //application().setLastPageID(pageFragment.getClass().getSimpleName());
        if (headerFragment != null) {
            fragmentTransaction.replace(headerViewResID(), headerFragment);
        }
        commitTransation(fragmentTransaction);
    }

    public MainFragment getPageFragment() {
        return dataContainer();
    }

    public MainFragment dataContainer() {
        Fragment fragment = this.findFragmentById(dataContainerResID());
        return fragment != null ? (MainFragment) fragment : null;
    }

    protected void commitTransation(FragmentTransaction fragmentTransaction) {
        if (application().isAppInForground()) {
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.commitAllowingStateLoss();
        }
        this.closeDrawers();
    }

    public void requestPermission(int permissionCode) {
        this.requestPermission(permissionCode, permissionCode);
    }

    public boolean isPermissionGranted(int permissionCode) {
        return isPermissionGranted(Manifest.permission.CALL_PHONE);
    }

    public boolean isPermissionGranted(String permission) {
        int rc = ActivityCompat.checkSelfPermission(this, permission);
        return rc == PackageManager.PERMISSION_GRANTED;
    }

    public void permissionGranted(int requestCode) {
        if (getPageFragment() != null) {
            getPageFragment().permissionGranted(requestCode);
        }
    }

    public void permissionDenied(int requestCode) {
        if (getPageFragment() != null) {
            getPageFragment().permissionDenied(requestCode);
        }
    }

    /**
     * Request for permission
     * If permission denied or app is first launched, request for permission
     * If permission denied and user choose 'Nerver Ask Again', show snackbar with an action that navigate to app settings
     */
    public void requestPermission(int permissionCode, int actionCode) {
        String permission = permissionForCode(permissionCode);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || isPermissionGranted(permission)) {
            this.permissionGranted(actionCode);
            return;
        }
        final String[] permissions = new String[]{permission};
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            ActivityCompat.requestPermissions(this, permissions, actionCode);
        } else {
            String permissionPref = permissionPref(permissionCode);
            if (!application().isPermissionRequested(permissionPref)) {
                application().setPermissionRequested(permissionPref);
                ActivityCompat.requestPermissions(this, permissions, actionCode);
            } else {
                // askUsertoChangePermission(messageIdForPermission(permissionCode));
            }
        }
    }


    private String permissionPref(int permissionCode) {
        switch (permissionCode) {
            case 508:
                return "callPhoneRequested";
        }

        return "PERMISSION_REQ_" + permissionCode;
    }

  /*  protected void askUsertoChangePermission(@StringRes int message) {
        AlertDialog deviceSettingsDialog = new AlertDialog.Builder(this)
                .setPositiveButton(R.string.openSett, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", getPackageName(), null));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        deviceSettingsDialog.setTitle(R.string.access_denied);
        deviceSettingsDialog.setMessage(ASTStringUtil.getStringForID(message));
        deviceSettingsDialog.show();
    }*/

    protected String permissionForCode(int permissionCode) {
        switch (permissionCode) {
            case ASTReqResCode.PERMISSION_REQ_ACCESS_COARSE_LOCATION:
                return Manifest.permission.ACCESS_COARSE_LOCATION;
            case ASTReqResCode.PERMISSION_REQ_ACCESS_FINE_LOCATION:
                return Manifest.permission.ACCESS_FINE_LOCATION;
            case ASTReqResCode.PERMISSION_REQ_CAMERA:
                return Manifest.permission.CAMERA;
            case ASTReqResCode.PERMISSION_REQ_READ_CONTACTS:
                return Manifest.permission.READ_CONTACTS;
            case ASTReqResCode.PERMISSION_REQ_READ_PHONE_STATE:
                return Manifest.permission.READ_PHONE_STATE;
            case ASTReqResCode.PERMISSION_REQ_RECORD_AUDIO:
                return Manifest.permission.RECORD_AUDIO;
            case ASTReqResCode.PERMISSION_REQ_WRITE_CALENDAR:
                return Manifest.permission.WRITE_CALENDAR;
            case ASTReqResCode.PERMISSION_REQ_WRITE_EXTERNAL_STORAGE:
                return Manifest.permission.WRITE_EXTERNAL_STORAGE;
            case ASTReqResCode.PERMISSION_REQ_CALL_PHONE:
                return Manifest.permission.CALL_PHONE;
        }

        return null;
    }


    /**
     * redirect to Home menu Navigation item
     */
    public void redirectToHomeMenu() {
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.getPageFragment() != null) {
            this.getPageFragment().updateOnResult(requestCode, resultCode, data);
        }
    }

    SharedPreferences pref;
    String userName;
    String[] menuIds;

    public void showNavigationMenuItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
    }

    //---------Run time permission---------
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void runTimePermission() {
        permissionUtils = new PermissionUtils(MainActivity.this);
        permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(android.Manifest.permission.CALL_PHONE);
        permissions.add(android.Manifest.permission.READ_PHONE_STATE);
        permissions.add(android.Manifest.permission.READ_CALL_LOG);
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.CAMERA);
        permissions.add(android.Manifest.permission.WAKE_LOCK);
        //permissions.add(Manifest.permission.READ_CONTACTS);
        permissions.add(Manifest.permission.SEND_SMS);
        permissions.add(android.Manifest.permission.MODIFY_AUDIO_SETTINGS);
        permissions.add(Manifest.permission.ACCESS_NOTIFICATION_POLICY);


        permissionUtils.check_permission(permissions, "Location, Phone and Storage Services Permissions are required for this App.", REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
    }

    @Override
    public void PermissionGranted(int request_code) {
        checkGpsEnable();
        //for
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
        //finish();
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
        //  finish();
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
        neverAskAgainAlert();
    }


    private void neverAskAgainAlert() {
        //Previously Permission Request was cancelled with 'Dont Ask Again',
        // Redirect to Settings after showing Information about why you need the permission
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Multiple Permissions");
        builder.setCancelable(false);
        builder.setMessage("Location, Phone and Storage Services Permissions are required for this App.");
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", ApplicationHelper.application().getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                // finish();
            }
        });
        builder.show();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_CODE_GPS_PERMISSIONS);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        checkGpsEnable();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    //    check gps enable in device or not
    private void checkGpsEnable() {
        try {
            boolean isGPSEnabled = false;
            boolean isNetworkEnabled = false;
            final LocationManager locationManager = (LocationManager) ApplicationHelper.application().getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                buildAlertMessageNoGps();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //check app need force update or not
    @SuppressLint("StaticFieldLeak")
    private void checkForceUpdateStatus() {
        final String cureentVersion = ASTUIUtil.getAppVersionName(this);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String latestVersion = null;
                try {
                    String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=com.telecom.ast.sitesurvey";
                    org.jsoup.nodes.Document doc = Jsoup.connect(urlOfAppFromPlayStore).get();
                    latestVersion = doc.getElementsByAttributeValue("itemprop", "softwareVersion").first().text();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return latestVersion;
            }

            @Override
            protected void onPostExecute(String latestVersion) {
                super.onPostExecute(latestVersion);
                if (latestVersion != null) {
                    if (!cureentVersion.equals(latestVersion)) {
                        AstAppUgradeDlgActivity fnAppUgradeDlgActivity = new AstAppUgradeDlgActivity(MainActivity.this) {
                            @Override
                            public void onSkip() {
                                ASTUIUtil.showToast("Please Update your App");
                            }
                        };
                        fnAppUgradeDlgActivity.show();
                    }
                }

            }
        }.execute(null, null, null);
    }

    //alert for force update app from play store
    public void alertForForceUpdateApp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //   builder.setIcon(R.mipmap.ic_launcher);
        // builder.setTitle(R.string.app_name);
        builder.setMessage("App New Version Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + ASTUIUtil.getAppPackageName(getBaseContext()))));
                dialog.cancel();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();


    }

    String currentVersion, latestVersion;

    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;
        new GetLatestVersion().execute();
    }

    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                String urlOfAppFromPlayStore = "https://play.google.com/store/apps/details?id=com.apitechnosoft.mrhelper";
                Document doc = Jsoup.connect(urlOfAppFromPlayStore).get();
                latestVersion = doc.getElementsByClass("htlgb").get(6).text();
            } catch (Exception e) {
                e.printStackTrace();

            }

            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    if (!isFinishing()) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
                        AstAppUgradeDlgActivity fnAppUgradeDlgActivity = new AstAppUgradeDlgActivity(MainActivity.this) {
                            @Override
                            public void onSkip() {
                                ASTUIUtil.showToast("Please Update your App");
                            }
                        };
                        fnAppUgradeDlgActivity.show();
                    }
                }
            } else
                super.onPostExecute(jsonObject);
        }
    }

}
