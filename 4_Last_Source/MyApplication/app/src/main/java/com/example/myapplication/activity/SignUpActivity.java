package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dialog.DialogCustrom;
import com.example.myapplication.mail.GMailSender;
import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.SignUpPresenter;
import com.example.myapplication.presenter.interfaces.SignUpPresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.view.SignUpView;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements SignUpView
{
    private SignUpPresenterInterface signUpPresenterInterface;

    private EditText ETID;
    private EditText ETEMail;
    private EditText ETPassword;
    private EditText ETPasswordConfirm;
    private EditText ETName;
    private EditText ETCertificationCode;

    private TextView BTNConfirmDuplicatedID;
    private TextView BTNComplete;
    private TextView BTNSendCertificationCode;
    private TextView BTNConfirmCertificationCode;

    private ImageView BTNBack;

    private String certificationCode = "";

    private DialogCustrom dialog;

    private boolean isCertificated = false;
    private boolean isConfirmDuplicatedID = false;

    private InputFilter filterKor = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[ㄱ-ㅣ가-힣]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initLayout();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void initLayout()
    {
        this.setStatusBarColor();
        this.setSignUpPresenterInterface();
        this.setETID();
        this.setBTNConfirmDuplicatedID();
        this.setETEMail();
        this.setETPassword();
        this.setETPasswordConfirm();
        this.setETName();
        this.setBTNComplete();
        this.setBTNConfirmCertificationCode();
        this.setBTNSendCertificationCode();
        this.setETCertificationCode();
        this.setBTNBack();
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

    public void setSignUpPresenterInterface()
    {
        signUpPresenterInterface = new SignUpPresenter(this);
    }

    public void setETID()
    {
        ETID = (EditText)findViewById(R.id.et_id);

        ETID.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                isConfirmDuplicatedID = false;
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    public void setBTNConfirmDuplicatedID()
    {
        BTNConfirmDuplicatedID = (TextView) findViewById(R.id.btn_confirm_duplicated_id);

        BTNConfirmDuplicatedID.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ETID.getText().toString().length() < 6 || ETID.getText().toString().length() > 15)
                {
                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "아이디는 6 ~ 15 자리 사이입니다.",
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
                else
                {
                    signUpPresenterInterface.confirmDuplicatedID(ETID.getText().toString());
                }
            }
        });
    }

    public void setETEMail()
    {
        ETEMail = (EditText)findViewById(R.id.et_email);

        ETEMail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                ETCertificationCode.setText("");

                isCertificated = false;
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    public void setETCertificationCode()
    {
        ETCertificationCode = (EditText)findViewById(R.id.et_certification_code);
    }

    public void setBTNBack()
    {
        BTNBack = (ImageView)findViewById(R.id.back);

        BTNBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ETID.getWindowToken(), 0);
                onBackPressed();
            }
        });
    }

    public void setBTNConfirmCertificationCode()
    {
        BTNConfirmCertificationCode = (TextView)findViewById(R.id.btn_confirm_certification_code);

        BTNConfirmCertificationCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ETCertificationCode.getText().toString().equalsIgnoreCase(certificationCode))
                {
                    isCertificated = true;

                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "인증에 성공하였습니다.",
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
                else
                {
                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "인증번호가 잘못 되었습니다.",
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
        });
    }

    public void setBTNSendCertificationCode()
    {
        BTNSendCertificationCode = (TextView)findViewById(R.id.btn_send_certification_code);

        BTNSendCertificationCode.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!checkEMail(ETEMail.getText().toString()))
                {
                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "이메일 형식이 잘못 되었습니다.",
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
                else
                {
                    ETCertificationCode.setVisibility(View.VISIBLE);
                    BTNConfirmCertificationCode.setVisibility(View.VISIBLE);

                    sendEMail(ETEMail.getText().toString());

                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "인증번호를 전송하였습니다.",
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
        });
    }

    public void setETPassword()
    {
        ETPassword = (EditText)findViewById(R.id.et_passwd);
    }

    public void setETPasswordConfirm()
    {
        ETPasswordConfirm = (EditText)findViewById(R.id.et_passwd_confirm);
    }

    public void setETName()
    {
        ETName = (EditText)findViewById(R.id.et_name);
        ETName.setFilters(new InputFilter[]{filterKor});
    }

    public void setBTNComplete()
    {
        BTNComplete = (TextView)findViewById(R.id.btn_complete);
        BTNComplete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!isConfirmDuplicatedID)
                {
                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "아이디 중복 확인을 해주세요.",
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
                else if(!checkEMail(ETEMail.getText().toString()))
                {
                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "이메일 형식이 잘못 되었습니다.",
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
                else if(!isCertificated)
                {
                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "이메일 인증을 받아주세요.",
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
                else if(!ETPassword.getText().toString().equalsIgnoreCase(ETPasswordConfirm.getText().toString()))
                {
                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "비밀번호를 확인해 주세요.",
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
                else if(ETPassword.getText().toString().length() < 6 || ETPassword.getText().toString().length() > 15)
                {
                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "비밀번호는 6 ~ 15 자리 사이입니다.",
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
                else if(ETName.getText().toString().length() < 3 || ETName.getText().toString().length() > 10)
                {
                    dialog = new DialogCustrom(SignUpActivity.this,
                            "Coin Management",
                            "닉네임은 3 ~ 10 자리 사이입니다.",
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
                else
                {
                    signUpPresenterInterface.signUp(ETID.getText().toString(), ETEMail.getText().toString(), ETPassword.getText().toString(), ETName.getText().toString());
                }
            }
        });
    }

    public boolean checkEMail(String EMail)
    {
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(EMail);

        boolean isNormal = m.matches();

        return isNormal;
    }

    public void sendEMail(final String EMail)
    {
        Random random = new Random();

        certificationCode = "";

        for(int i = 0; i < 6; i++)
        {
            certificationCode += String.valueOf(random.nextInt(10));
        }

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    GMailSender sender = new GMailSender("Gmail Email Address", "Gmail Email Password");
                    sender.sendMail("Coin Management 이메일 인증입니다.",
                            "회원님의 인증 코드는 [" + certificationCode + "] 입니다.",
                            "Gmail Email Address", // The security issue can not write
                            EMail);
                }
                catch (Exception e)
                {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        }).start();
    }

    @Override
    public void successConfirmDuplicatedID()
    {
        isConfirmDuplicatedID = true;

        dialog = new DialogCustrom(SignUpActivity.this,
                "Coin Management",
                "사용 가능한 아이디 입니다.",
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

    @Override
    public void failedConfirmDuplicatedID()
    {
        isConfirmDuplicatedID = false;

        dialog = new DialogCustrom(SignUpActivity.this,
                "Coin Management",
                "이미 사용중인 아이디 입니다.",
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

    @Override
    public void goToLoginActivity()
    {
        Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void failedSignUpByDuplicatedEMail()
    {
        dialog = new DialogCustrom(SignUpActivity.this,
                "Coin Management",
                "이미 등록된 이메일입니다.",
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
