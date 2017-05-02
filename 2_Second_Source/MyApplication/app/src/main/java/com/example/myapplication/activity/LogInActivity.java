package com.example.myapplication.activity;

import android.content.Intent;
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

    private EditText ETEMail;
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
            BTNManager.setTextColor(getResources().getColor(R.color.colorWhite));
            BTNManager.setBackgroundColor(getResources().getColor(R.color.colorBTN));
        }
        else
        {
            BTNManager.setTextColor(getResources().getColor(R.color.colorBlack));
            BTNManager.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }

        BTNManager.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BTNManager.setTextColor(getResources().getColor(R.color.colorBlack));
                BTNManager.setBackgroundColor(getResources().getColor(R.color.colorWhite));

                BTNUser.setTextColor(getResources().getColor(R.color.colorWhite));
                BTNUser.setBackgroundColor(getResources().getColor(R.color.colorBTN));

                isUser = false;
            }
        });
    }

    public void setBTNUser()
    {
        BTNUser = (TextView)findViewById(R.id.btn_user);

        if(isUser)
        {
            BTNUser.setTextColor(getResources().getColor(R.color.colorBlack));
            BTNUser.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }
        else
        {
            BTNUser.setTextColor(getResources().getColor(R.color.colorWhite));
            BTNUser.setBackgroundColor(getResources().getColor(R.color.colorBTN));
        }

        BTNUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BTNUser.setTextColor(getResources().getColor(R.color.colorBlack));
                BTNUser.setBackgroundColor(getResources().getColor(R.color.colorWhite));

                BTNManager.setTextColor(getResources().getColor(R.color.colorWhite));
                BTNManager.setBackgroundColor(getResources().getColor(R.color.colorBTN));

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
                    logInPresenterInterface.logIn(ETEMail.getText().toString(), ETPassword.getText().toString(), 1);
                }
                else
                {
                    logInPresenterInterface.logIn(ETEMail.getText().toString(), ETPassword.getText().toString(), 0);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            String userEMail = data.getStringExtra("SCAN_RESULT");
            this.goToManagerMainActivity(ETEMail.getText().toString(), userEMail);
            LogInActivity.this.finish();
        }
        else if (resultCode == RESULT_CANCELED)
        {
            LogInActivity.this.finish();
        }
    }

    public void setETEMail()
    {
        ETEMail = (EditText)findViewById(R.id.et_email);
    }

    public void setETPassword()
    {
        ETPassword = (EditText)findViewById(R.id.et_password);
    }

    @Override
    public void goToUserMainActivity(String userEMail)
    {
        Intent intent = new Intent(LogInActivity.this, UserMainActivity.class);
        intent.putExtra("userEMail", userEMail);
        startActivityForResult(intent, 0);
    }

    @Override
    public void goToManagerMainActivity(String managerEMail, String userEMail)
    {
        Intent intent = new Intent(LogInActivity.this, ManagerMainActivity.class);
        intent.putExtra("userEMail", userEMail);
        intent.putExtra("managerEMail", managerEMail);
        startActivityForResult(intent, 0);
    }

    @Override
    public void goToCaptureActivity()
    {
        IntentIntegrator integrator = new IntentIntegrator(LogInActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("회원 코드를 사각형 안에 비춰주세요.");
        integrator.addExtra("SCAN_MODE", "SCAN_MODE");
        integrator.setCameraId(0);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();
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
