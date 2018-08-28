package com.telecom.ast.sitesurvey.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.telecom.ast.sitesurvey.ApplicationHelper;
import com.telecom.ast.sitesurvey.AstAppUgradeDlgActivity;
import com.telecom.ast.sitesurvey.R;
import com.telecom.ast.sitesurvey.utils.ASTUIUtil;
import com.telecom.ast.sitesurvey.utils.GCM_Registration;

import org.jsoup.Jsoup;


/**
 * Created by Narayan Semwal on 12-09-2017.
 */

public class SplashScreen extends AppCompatActivity {
    String gcmRegId;
    SharedPreferences pref;
    GCM_Registration gcmClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ApplicationHelper.application().initIcons();
        pref = getApplicationContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
        String userId = pref.getString("userId", "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pref = getApplicationContext().getSharedPreferences("SharedPref", MODE_PRIVATE);
                String userId = pref.getString("userId", "");
                if (!userId.equals("")) {
                    Intent intentLoggedIn = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intentLoggedIn);
                } else {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 1000);



    }





}
