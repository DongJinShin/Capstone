package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.myapplication.R;

public class SettingActivity extends AppCompatActivity
{
    private ImageView BTNBack;

    private Switch SWAutoLogin;
    private Switch SWPushAlarm;

    private Button BTNLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.initLayout();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void initLayout()
    {
        this.setStatusBarColor();
        this.setBTNBack();
        this.setSWAutoLogin();
        this.setSWPushAlarm();
        this.setBTNLogout();
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

    public void setBTNBack()
    {
        BTNBack = (ImageView)findViewById(R.id.back);

        BTNBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }

    public void setSWAutoLogin()
    {
        SWAutoLogin = (Switch)findViewById(R.id.sw_auto_login);

        SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        boolean isAutoLogin = pref.getBoolean("autoLogin", true);

        if (isAutoLogin)
        {
            SWAutoLogin.setChecked(true);
        }
        else
        {
            SWAutoLogin.setChecked(false);
        }

        SWAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("autoLogin", isChecked);
                editor.commit();
            }
        });
    }

    public void setSWPushAlarm()
    {
        SWPushAlarm = (Switch)findViewById(R.id.sw_push_alarm);

        SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        boolean isPushAlarm = pref.getBoolean("pushAlarm", true);

        if (isPushAlarm)
        {
            SWPushAlarm.setChecked(true);
        }
        else
        {
            SWPushAlarm.setChecked(false);
        }

        SWPushAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("pushAlarm", isChecked);
                editor.commit();
            }
        });
    }

    public void setBTNLogout()
    {
        BTNLogout = (Button)findViewById(R.id.btn_logout);

        BTNLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("logout", true);
                editor.commit();

                Intent intent = new Intent(SettingActivity.this, LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
