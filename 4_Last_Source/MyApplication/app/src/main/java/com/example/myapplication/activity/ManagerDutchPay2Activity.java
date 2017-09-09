package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dialog.DialogCustrom;
import com.example.myapplication.presenter.ManagerDutchPay2Presenter;
import com.example.myapplication.presenter.ManagerSaveCoinPresenter;
import com.example.myapplication.presenter.interfaces.ManagerDutchPay2PresenterInterface;
import com.example.myapplication.presenter.interfaces.ManagerSaveCoinPresenterInterface;
import com.example.myapplication.view.ManagerDutchPay2View;
import com.google.zxing.integration.android.IntentIntegrator;

public class ManagerDutchPay2Activity extends AppCompatActivity implements ManagerDutchPay2View
{
    private ManagerDutchPay2PresenterInterface managerDutchPay2PresenterInterface;

    private ImageView BTNBack;

    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout layout4;
    private LinearLayout layout5;
    private LinearLayout layout6;

    private LinearLayout line1;
    private LinearLayout line2;
    private LinearLayout line3;
    private LinearLayout line4;
    private LinearLayout line5;
    private LinearLayout line6;

    private TextView TVCertificationConfirm1;
    private TextView TVCertificationConfirm2;
    private TextView TVCertificationConfirm3;
    private TextView TVCertificationConfirm4;
    private TextView TVCertificationConfirm5;
    private TextView TVCertificationConfirm6;

    private TextView TVDutchPayCertification1;
    private TextView TVDutchPayCertification2;
    private TextView TVDutchPayCertification3;
    private TextView TVDutchPayCertification4;
    private TextView TVDutchPayCertification5;
    private TextView TVDutchPayCertification6;


    private TextView TVDutchPayAmount1;
    private TextView TVDutchPayAmount2;
    private TextView TVDutchPayAmount3;
    private TextView TVDutchPayAmount4;
    private TextView TVDutchPayAmount5;
    private TextView TVDutchPayAmount6;

    private Button BTNComplete;

    private boolean isConfirmed1 = false;
    private boolean isConfirmed2 = false;
    private boolean isConfirmed3 = false;
    private boolean isConfirmed4 = false;
    private boolean isConfirmed5 = false;
    private boolean isConfirmed6 = false;

    private String ID1;
    private String ID2;
    private String ID3;
    private String ID4;
    private String ID5;
    private String ID6;

    private DialogCustrom dialog;

    private int processingNum = 0;


    private int useAmount = 0;
    private int userNum = 0;

    private String managerID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dutch_pay_2);
        this.initLayout();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void initLayout()
    {
        this.setManagerDutchPay2PresenterInterface();
        this.setStatusBarColor();
        this.setBTNBack();
        this.getData();
        this.setLayout();
        this.setLine();
        this.setTVDutchPayAmount();
        this.setTVCertificationConfirm();
        this.setTVDutchPayCertification();
        this.setBTNComplete();
    }

    public void setManagerDutchPay2PresenterInterface()
    {
        managerDutchPay2PresenterInterface = new ManagerDutchPay2Presenter(this);
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

    public void getData()
    {
        Intent intent = getIntent();
        useAmount = intent.getIntExtra("useAmount", 0);
        userNum = intent.getIntExtra("userNum", 0);
        managerID = intent.getStringExtra("managerID");
    }

    public void setLayout()
    {
        layout1 = (LinearLayout)findViewById(R.id.layout1);
        layout2 = (LinearLayout)findViewById(R.id.layout2);
        layout3 = (LinearLayout)findViewById(R.id.layout3);
        layout4 = (LinearLayout)findViewById(R.id.layout4);
        layout5 = (LinearLayout)findViewById(R.id.layout5);
        layout6 = (LinearLayout)findViewById(R.id.layout6);

        switch(userNum)
        {
            case 2:
                layout3.setVisibility(View.GONE);
                layout4.setVisibility(View.GONE);
                layout5.setVisibility(View.GONE);
                layout6.setVisibility(View.GONE);
                break;
            case 3:
                layout4.setVisibility(View.GONE);
                layout5.setVisibility(View.GONE);
                layout6.setVisibility(View.GONE);
                break;
            case 4:
                layout5.setVisibility(View.GONE);
                layout6.setVisibility(View.GONE);
                break;
            case 5:
                layout6.setVisibility(View.GONE);
                break;
        }
    }

    public void setLine()
    {
        line1 = (LinearLayout)findViewById(R.id.linearLayout1);
        line2 = (LinearLayout)findViewById(R.id.linearLayout2);
        line3 = (LinearLayout)findViewById(R.id.linearLayout3);
        line4 = (LinearLayout)findViewById(R.id.linearLayout4);
        line5 = (LinearLayout)findViewById(R.id.linearLayout5);
        line6 = (LinearLayout)findViewById(R.id.linearLayout6);

        switch(userNum)
        {
            case 2:
                line3.setVisibility(View.GONE);
                line4.setVisibility(View.GONE);
                line5.setVisibility(View.GONE);
                line6.setVisibility(View.GONE);
                break;
            case 3:
                line4.setVisibility(View.GONE);
                line5.setVisibility(View.GONE);
                line6.setVisibility(View.GONE);
                break;
            case 4:
                line5.setVisibility(View.GONE);
                line6.setVisibility(View.GONE);
                break;
            case 5:
                line6.setVisibility(View.GONE);
                break;
        }
    }

    public void setTVDutchPayAmount()
    {
        TVDutchPayAmount1 = (TextView)findViewById(R.id.tv_dutch_pay_amount1);
        TVDutchPayAmount2 = (TextView)findViewById(R.id.tv_dutch_pay_amount2);
        TVDutchPayAmount3 = (TextView)findViewById(R.id.tv_dutch_pay_amount3);
        TVDutchPayAmount4 = (TextView)findViewById(R.id.tv_dutch_pay_amount4);
        TVDutchPayAmount5 = (TextView)findViewById(R.id.tv_dutch_pay_amount5);
        TVDutchPayAmount6 = (TextView)findViewById(R.id.tv_dutch_pay_amount6);

        int mod = useAmount % userNum;

        int baseAmount = (useAmount - mod) / userNum;

        switch(userNum)
        {
            case 2:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount));
                TVDutchPayAmount2.setText(String.valueOf(baseAmount));
                break;
            case 3:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount));
                TVDutchPayAmount2.setText(String.valueOf(baseAmount));
                TVDutchPayAmount3.setText(String.valueOf(baseAmount));
                break;
            case 4:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount));
                TVDutchPayAmount2.setText(String.valueOf(baseAmount));
                TVDutchPayAmount3.setText(String.valueOf(baseAmount));
                TVDutchPayAmount4.setText(String.valueOf(baseAmount));
                break;
            case 5:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount));
                TVDutchPayAmount2.setText(String.valueOf(baseAmount));
                TVDutchPayAmount3.setText(String.valueOf(baseAmount));
                TVDutchPayAmount4.setText(String.valueOf(baseAmount));
                TVDutchPayAmount5.setText(String.valueOf(baseAmount));
                break;
            case 6:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount));
                TVDutchPayAmount2.setText(String.valueOf(baseAmount));
                TVDutchPayAmount3.setText(String.valueOf(baseAmount));
                TVDutchPayAmount4.setText(String.valueOf(baseAmount));
                TVDutchPayAmount5.setText(String.valueOf(baseAmount));
                TVDutchPayAmount6.setText(String.valueOf(baseAmount));
                break;
        }

        switch (mod)
        {
            case 1:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount + 1));
                break;
            case 2:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount2.setText(String.valueOf(baseAmount + 1));
                break;
            case 3:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount2.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount3.setText(String.valueOf(baseAmount + 1));
                break;
            case 4:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount2.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount3.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount4.setText(String.valueOf(baseAmount + 1));
                break;
            case 5:
                TVDutchPayAmount1.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount2.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount3.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount4.setText(String.valueOf(baseAmount + 1));
                TVDutchPayAmount5.setText(String.valueOf(baseAmount + 1));
                break;
        }
    }

    public void setTVCertificationConfirm()
    {
        TVCertificationConfirm1 = (TextView)findViewById(R.id.tv_certification_confirm1);
        TVCertificationConfirm2 = (TextView)findViewById(R.id.tv_certification_confirm2);
        TVCertificationConfirm3 = (TextView)findViewById(R.id.tv_certification_confirm3);
        TVCertificationConfirm4 = (TextView)findViewById(R.id.tv_certification_confirm4);
        TVCertificationConfirm5 = (TextView)findViewById(R.id.tv_certification_confirm5);
        TVCertificationConfirm6 = (TextView)findViewById(R.id.tv_certification_confirm6);
    }

    public void setTVDutchPayCertification()
    {
        TVDutchPayCertification1 = (TextView)findViewById(R.id.btn_dutch_pay_certification1);
        TVDutchPayCertification2 = (TextView)findViewById(R.id.btn_dutch_pay_certification2);
        TVDutchPayCertification3 = (TextView)findViewById(R.id.btn_dutch_pay_certification3);
        TVDutchPayCertification4 = (TextView)findViewById(R.id.btn_dutch_pay_certification4);
        TVDutchPayCertification5 = (TextView)findViewById(R.id.btn_dutch_pay_certification5);
        TVDutchPayCertification6 = (TextView)findViewById(R.id.btn_dutch_pay_certification6);

        TVDutchPayCertification1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToCaptureActivity(1);
                TVCertificationConfirm1.setText("인증 필요");
                TVCertificationConfirm1.setTextColor(getResources().getColor(R.color.red));
                isConfirmed1 = false;
            }
        });

        TVDutchPayCertification2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToCaptureActivity(2);
                TVCertificationConfirm2.setText("인증 필요");
                TVCertificationConfirm2.setTextColor(getResources().getColor(R.color.red));
                isConfirmed2 = false;
            }
        });

        TVDutchPayCertification3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToCaptureActivity(3);
                TVCertificationConfirm3.setText("인증 필요");
                TVCertificationConfirm3.setTextColor(getResources().getColor(R.color.red));
                isConfirmed3 = false;
            }
        });

        TVDutchPayCertification4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToCaptureActivity(4);
                TVCertificationConfirm4.setText("인증 필요");
                TVCertificationConfirm4.setTextColor(getResources().getColor(R.color.red));
                isConfirmed4 = false;
            }
        });

        TVDutchPayCertification5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToCaptureActivity(5);
                TVCertificationConfirm5.setText("인증 필요");
                TVCertificationConfirm5.setTextColor(getResources().getColor(R.color.red));
                isConfirmed5 = false;
            }
        });

        TVDutchPayCertification6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goToCaptureActivity(6);
                TVCertificationConfirm6.setText("인증 필요");
                TVCertificationConfirm6.setTextColor(getResources().getColor(R.color.red));
                isConfirmed6 = false;
            }
        });
    }

    public void setBTNComplete()
    {
        BTNComplete = (Button)findViewById(R.id.btn_complete);

        BTNComplete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch(userNum)
                {
                    case 2:
                        if(isConfirmed1 && isConfirmed2)
                        {
                            managerDutchPay2PresenterInterface.dutchPay2(managerID, ID1, ID2, Integer.parseInt(TVDutchPayAmount1.getText().toString()),Integer.parseInt(TVDutchPayAmount2.getText().toString()));
                        }
                        else
                        {
                            dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                                    "Coin Management",
                                    "인증을 모두 진행해주세요.",
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
                        break;
                    case 3:
                        if(isConfirmed1 && isConfirmed2 && isConfirmed3)
                        {
                            managerDutchPay2PresenterInterface.dutchPay3(managerID, ID1, ID2, ID3, Integer.parseInt(TVDutchPayAmount1.getText().toString()),Integer.parseInt(TVDutchPayAmount2.getText().toString()), Integer.parseInt(TVDutchPayAmount3.getText().toString()));
                        }
                        else
                        {
                            dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                                    "Coin Management",
                                    "인증을 모두 진행해주세요.",
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
                        break;
                    case 4:
                        if(isConfirmed1 && isConfirmed2 && isConfirmed3 && isConfirmed4)
                        {
                            managerDutchPay2PresenterInterface.dutchPay4(managerID, ID1, ID2, ID3, ID4, Integer.parseInt(TVDutchPayAmount1.getText().toString()),Integer.parseInt(TVDutchPayAmount2.getText().toString()), Integer.parseInt(TVDutchPayAmount3.getText().toString()), Integer.parseInt(TVDutchPayAmount4.getText().toString()));
                        }
                        else
                        {
                            dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                                    "Coin Management",
                                    "인증을 모두 진행해주세요.",
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
                        break;
                    case 5:
                        if(isConfirmed1 && isConfirmed2 && isConfirmed3 && isConfirmed4 && isConfirmed5)
                        {
                            managerDutchPay2PresenterInterface.dutchPay5(managerID, ID1, ID2, ID3, ID4, ID5, Integer.parseInt(TVDutchPayAmount1.getText().toString()),Integer.parseInt(TVDutchPayAmount2.getText().toString()), Integer.parseInt(TVDutchPayAmount3.getText().toString()), Integer.parseInt(TVDutchPayAmount4.getText().toString()), Integer.parseInt(TVDutchPayAmount5.getText().toString()));
                        }
                        else
                        {
                            dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                                    "Coin Management",
                                    "인증을 모두 진행해주세요.",
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
                        break;
                    case 6:
                        if(isConfirmed1 && isConfirmed2 && isConfirmed3 && isConfirmed4 && isConfirmed5 && isConfirmed6)
                        {
                            managerDutchPay2PresenterInterface.dutchPay6(managerID, ID1, ID2, ID3, ID4, ID5, ID6, Integer.parseInt(TVDutchPayAmount1.getText().toString()),Integer.parseInt(TVDutchPayAmount2.getText().toString()), Integer.parseInt(TVDutchPayAmount3.getText().toString()), Integer.parseInt(TVDutchPayAmount4.getText().toString()), Integer.parseInt(TVDutchPayAmount5.getText().toString()), Integer.parseInt(TVDutchPayAmount6.getText().toString()));
                        }
                        else
                        {
                            dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                                    "Coin Management",
                                    "인증을 모두 진행해주세요.",
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
                        break;
                }
            }
        });
    }

    public void goToCaptureActivity(int num)
    {
        processingNum = num;
        IntentIntegrator integrator = new IntentIntegrator(ManagerDutchPay2Activity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("회원 코드를 사각형 안에 비춰주세요.");
        integrator.setCameraId(0);
        integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            String userID = data.getStringExtra("SCAN_RESULT");
            int mode = processingNum;

            managerDutchPay2PresenterInterface.selectOneUserCoinByID(userID, mode);
        }
        else if (resultCode == RESULT_CANCELED)
        {
        }
    }

    @Override
    public void judgmentCoin(String userID, int coin, int mode)
    {
        if(userID.equalsIgnoreCase(ID1))
        {
            coin -= Integer.parseInt(TVDutchPayAmount1.getText().toString());
        }

        if(userID.equalsIgnoreCase(ID2))
        {
            coin -= Integer.parseInt(TVDutchPayAmount2.getText().toString());
        }

        if(userID.equalsIgnoreCase(ID3))
        {
            coin -= Integer.parseInt(TVDutchPayAmount3.getText().toString());
        }

        if(userID.equalsIgnoreCase(ID4))
        {
            coin -= Integer.parseInt(TVDutchPayAmount4.getText().toString());
        }

        if(userID.equalsIgnoreCase(ID5))
        {
            coin -= Integer.parseInt(TVDutchPayAmount5.getText().toString());
        }

        if(userID.equalsIgnoreCase(ID6))
        {
            coin -= Integer.parseInt(TVDutchPayAmount6.getText().toString());
        }





        switch(mode)
        {
            case 1:
                if(Integer.parseInt(TVDutchPayAmount1.getText().toString()) <= coin)
                {
                    isConfirmed1 = true;
                    ID1 = userID;
                    TVCertificationConfirm1.setText("인증 성공");
                    TVCertificationConfirm1.setTextColor(getResources().getColor(R.color.blue));
                }
                else
                {
                    dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                            "Coin Management",
                            "회원의 잔액이 부족합니다.",
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
                break;
            case 2:
                if(Integer.parseInt(TVDutchPayAmount2.getText().toString()) <= coin)
                {
                    isConfirmed2 = true;
                    ID2 = userID;
                    TVCertificationConfirm2.setText("인증 성공");
                    TVCertificationConfirm2.setTextColor(getResources().getColor(R.color.blue));
                }
                else
                {
                    dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                            "Coin Management",
                            "회원의 잔액이 부족합니다.",
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
                break;
            case 3:
                if(Integer.parseInt(TVDutchPayAmount3.getText().toString()) <= coin)
                {
                    isConfirmed3 = true;
                    ID3 = userID;
                    TVCertificationConfirm3.setText("인증 성공");
                    TVCertificationConfirm3.setTextColor(getResources().getColor(R.color.blue));
                }
                else
                {
                    dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                            "Coin Management",
                            "회원의 잔액이 부족합니다.",
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
                break;
            case 4:
                if(Integer.parseInt(TVDutchPayAmount4.getText().toString()) <= coin)
                {
                    isConfirmed4 = true;
                    ID4 = userID;
                    TVCertificationConfirm4.setText("인증 성공");
                    TVCertificationConfirm4.setTextColor(getResources().getColor(R.color.blue));
                }
                else
                {
                    dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                            "Coin Management",
                            "회원의 잔액이 부족합니다.",
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
                break;
            case 5:
                if(Integer.parseInt(TVDutchPayAmount5.getText().toString()) <= coin)
                {
                    isConfirmed5 = true;
                    ID5 = userID;
                    TVCertificationConfirm5.setText("인증 성공");
                    TVCertificationConfirm5.setTextColor(getResources().getColor(R.color.blue));
                }
                else
                {
                    dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                            "Coin Management",
                            "회원의 잔액이 부족합니다.",
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
                break;
            case 6:
                if(Integer.parseInt(TVDutchPayAmount6.getText().toString()) <= coin)
                {
                    isConfirmed6 = true;
                    ID6 = userID;
                    TVCertificationConfirm6.setText("인증 성공");
                    TVCertificationConfirm6.setTextColor(getResources().getColor(R.color.blue));
                }
                else
                {
                    dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                            "Coin Management",
                            "회원의 잔액이 부족합니다.",
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
                break;
        }
    }

    @Override
    public void successDutchPay()
    {
        dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                "Coin Management",
                "결제 성공",
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", false);
                        setResult(Activity.RESULT_OK,returnIntent);
                        ManagerDutchPay2Activity.this.finish();
                    }
                });
        dialog.show();
    }

    @Override
    public void failedDutchPay()
    {
        dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                "Coin Management",
                "결제 실패",
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", false);
                        setResult(Activity.RESULT_OK,returnIntent);
                        ManagerDutchPay2Activity.this.finish();
                    }
                });
        dialog.show();
    }

    @Override
    public void failedCertification()
    {
        dialog = new DialogCustrom(ManagerDutchPay2Activity.this,
                "Coin Management",
                "인증 실패",
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
