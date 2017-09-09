package com.example.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.FCM.FirebaseInstanceIDService;
import com.example.myapplication.R;
import com.example.myapplication.mail.GMailSender;

public class SplashActivity extends AppCompatActivity
{
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    public static String token;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        if (this.checkPermission()) {
            TelephonyManager telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String deviceID = telephony.getDeviceId();

            FirebaseInstanceIDService firebaseInstanceIDService = new FirebaseInstanceIDService();
            firebaseInstanceIDService.registerServer(deviceID);

            this.initLayout();
        }


    }

    public boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void initLayout()
    {
        setContentView(R.layout.activity_splash);

        this.setStatusBarColor();
        this.setSplashHandler();
    }

    public void setStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void setSplashHandler()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
                boolean isAutoLogin = pref.getBoolean("autoLogin", true);
                boolean isLogout = pref.getBoolean("logout", true);
                boolean isUser = pref.getBoolean("user", true);
                String ID = pref.getString("ID", "none");

                if(isAutoLogin && !isLogout && !ID.equalsIgnoreCase("none"))
                {
                    if(isUser)
                    {
                        Intent intent = new Intent(SplashActivity.this, UserMainActivity.class);
                        intent.putExtra("userID", ID);
                        startActivityForResult(intent, 0);
                        SplashActivity.this.finish();
                    }
                    else
                    {
                        Intent intent = new Intent(SplashActivity.this, ManagerMainActivity.class);
                        intent.putExtra("managerID", ID);
                        startActivityForResult(intent, 0);
                        SplashActivity.this.finish();
                    }
                }
                else
                {
                    Intent mainIntent = new Intent(SplashActivity.this, LogInActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String deviceID = telephony.getDeviceId();

            FirebaseInstanceIDService firebaseInstanceIDService = new FirebaseInstanceIDService();
            firebaseInstanceIDService.registerServer(deviceID);

            this.initLayout();
        } else {
            finish();
        }
    }
}
