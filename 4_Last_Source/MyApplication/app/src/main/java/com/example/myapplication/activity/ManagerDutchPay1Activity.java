package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.dialog.DialogCustrom;

public class ManagerDutchPay1Activity extends AppCompatActivity
{
    private ImageView BTNBack;

    private EditText ETUseAmount;
    private EditText ETUserNum;

    private Button BTNNext;

    private DialogCustrom dialog;

    private String managerID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dutch_pay);
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
        this.getManagerInfo();
        this.setBTNBack();
        this.setETUseAmount();
        this.setETUserNum();
        this.setBTNNext();
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

    public void getManagerInfo()
    {
        Intent intent = getIntent();

        managerID = intent.getStringExtra("managerID");
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

    public void setETUseAmount()
    {
        ETUseAmount = (EditText)findViewById(R.id.et_use_amount);
    }

    public void setETUserNum()
    {
        ETUserNum = (EditText)findViewById(R.id.et_user_num);
    }

    public void setBTNNext()
    {
        BTNNext = (Button)findViewById(R.id.btn_next);

        BTNNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String useAmount = ETUseAmount.getText().toString();
                String userNum = ETUserNum.getText().toString();

                if(!useAmount.equalsIgnoreCase("") && !userNum.equalsIgnoreCase(""))
                {
                    int useAmountInt = Integer.parseInt(useAmount);
                    int userNumInt = Integer.parseInt(userNum);

                    if(useAmountInt >= 1000)
                    {
                        if(userNumInt <= 6 && userNumInt >= 2)
                        {
                            goToManagerDutchPay2Activity(useAmountInt, userNumInt);
                        }
                        else
                        {
                            dialog = new DialogCustrom(ManagerDutchPay1Activity.this,
                                    "Coin Management",
                                    "인원수는 2 - 6명만 가능합니다.",
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
                    else
                    {
                        dialog = new DialogCustrom(ManagerDutchPay1Activity.this,
                                "Coin Management",
                                "사용금액은 1000원부터 가능합니다.",
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
                else
                {
                    dialog = new DialogCustrom(ManagerDutchPay1Activity.this,
                            "Coin Management",
                            "사용금액, 인원수를 모두 입력해 주세요.",
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

    public void goToManagerDutchPay2Activity(int useAmount, int userNum)
    {
        Intent intent = new Intent(ManagerDutchPay1Activity.this, ManagerDutchPay2Activity.class);
        intent.putExtra("managerID", managerID);
        intent.putExtra("useAmount", useAmount);
        intent.putExtra("userNum", userNum);
        ManagerDutchPay1Activity.this.startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                boolean result = data.getBooleanExtra("result", true);

                if (!result)
                {
                    ManagerDutchPay1Activity.this.finish();
                }
            }
        }
    }
}
