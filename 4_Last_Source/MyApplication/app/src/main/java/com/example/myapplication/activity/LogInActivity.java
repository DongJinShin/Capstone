package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dialog.DialogCustrom;
import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.LogInPresenter;
import com.example.myapplication.presenter.interfaces.LogInPresenterInterface;
import com.example.myapplication.view.LogInView;
import com.google.zxing.integration.android.IntentIntegrator;

public class LogInActivity extends AppCompatActivity implements LogInView
{
    private LogInPresenterInterface logInPresenterInterface;

    private TextView BTNManager;
    private TextView BTNUser;

    private TextView BTNLogIn;
    private TextView BTNSignUp;

    private EditText ETID;
    private EditText ETPassword;

    private DialogCustrom dialog;

    private boolean isUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.initLayout();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void initLayout()
    {
        this.setLogInPresenterInterface();
        this.setStatusBarColor();
        this.setBTNManager();
        this.setBTNUser();
        this.setBTNLogIn();
        this.setBTNSignUp();
        this.setETEMail();
        this.setETPassword();
    }

    public void setLogInPresenterInterface()
    {
        logInPresenterInterface = new LogInPresenter(this);
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

    public void setBTNManager()
    {
        BTNManager = (TextView)findViewById(R.id.btn_manager);

        if(isUser)
        {
            BTNManager.setBackgroundColor(getResources().getColor(R.color.viewfinder_mask));
        }
        else
        {
            BTNManager.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        BTNManager.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BTNManager.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                BTNUser.setBackgroundColor(getResources().getColor(R.color.viewfinder_mask));

                isUser = false;
            }
        });
    }

    public void setBTNUser()
    {
        BTNUser = (TextView)findViewById(R.id.btn_user);

        if(isUser)
        {
            BTNUser.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        else
        {
            BTNUser.setBackgroundColor(getResources().getColor(R.color.viewfinder_mask));
        }

        BTNUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BTNUser.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                BTNManager.setBackgroundColor(getResources().getColor(R.color.viewfinder_mask));

                isUser = true;
            }
        });
    }

    public void setBTNLogIn()
    {
        BTNLogIn = (TextView)findViewById(R.id.btn_login);

        BTNLogIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isUser)
                {
                    logInPresenterInterface.logIn(ETID.getText().toString(), ETPassword.getText().toString(), 1);
                }
                else
                {
                    logInPresenterInterface.logIn(ETID.getText().toString(), ETPassword.getText().toString(), 0);
                }
            }
        });
    }

    public void setBTNSignUp()
    {
        BTNSignUp = (TextView)findViewById(R.id.btn_signup);

        BTNSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                LogInActivity.this.startActivity(intent);
            }
        });
    }

    public void setETEMail()
    {
        ETID = (EditText)findViewById(R.id.et_id);
    }

    public void setETPassword()
    {
        ETPassword = (EditText)findViewById(R.id.et_password);
    }

    @Override
    public void goToUserMainActivity(String userID)
    {
        SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("user", true);
        editor.putBoolean("logout", false);
        editor.putString("ID", userID);
        editor.commit();

        Intent intent = new Intent(LogInActivity.this, UserMainActivity.class);
        intent.putExtra("userID", userID);
        startActivityForResult(intent, 0);
        finish();
    }

    @Override
    public void goToManagerMainActivity(String managerID)
    {
        SharedPreferences pref = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("user", false);
        editor.putBoolean("logout", false);
        editor.putString("ID", managerID);
        editor.commit();

        Intent intent = new Intent(LogInActivity.this, ManagerMainActivity.class);
        intent.putExtra("managerID", managerID);
        startActivityForResult(intent, 0);
        finish();
    }

    @Override
    public void failedLogIn()
    {
        dialog = new DialogCustrom(this,
                "Coin Management",
                "이메일 또는 비밀번호를\n확인해 주세요",
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }
}
