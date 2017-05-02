package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dialog.DialogCustrom;
import com.example.myapplication.presenter.ManagerMainPresenter;
import com.example.myapplication.presenter.ManagerSaveCoinPresenter;
import com.example.myapplication.presenter.interfaces.ManagerSaveCoinPresenterInterface;
import com.example.myapplication.view.ManagerSaveCoinView;

public class ManagerSaveCoinActivity extends AppCompatActivity implements ManagerSaveCoinView
{
    private ManagerSaveCoinPresenterInterface managerSaveCoinPresenterInterface;

    private LinearLayout BTNSaveComplete;

    private TextView TVCoin;

    private EditText ETSaveAmount;

    private String userEMail;

    private DialogCustrom dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        setContentView(R.layout.activity_manager_save_coin);

        this.initLayout();
    }

    public void initLayout()
    {
        this.setManagerSaveCoinPresenterInterface();
        this.getUserInfo();
        this.setStatusBarColor();
        this.setETSaveAmount();
        this.setBTNSaveComplete();
    }

    public void setManagerSaveCoinPresenterInterface()
    {
        managerSaveCoinPresenterInterface = new ManagerSaveCoinPresenter(this);
    }

    public void getUserInfo()
    {
        Intent intent = getIntent();

        userEMail = intent.getStringExtra("userEMail");

        managerSaveCoinPresenterInterface.selectOneUserCoinByEMail(userEMail);
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

    public void setBTNSaveComplete()
    {
        BTNSaveComplete = (LinearLayout)findViewById(R.id.btn_save_complete);
        BTNSaveComplete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ETSaveAmount.getText().toString() == null)
                {
                    dialog = new DialogCustrom(ManagerSaveCoinActivity.this,
                            "Coin Management",
                            "적립 금액을 확인해주세요.",
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
                else if(ETSaveAmount.getText().toString().equalsIgnoreCase(""))
                {
                    dialog = new DialogCustrom(ManagerSaveCoinActivity.this,
                            "Coin Management",
                            "적립 금액을 확인해주세요.",
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
                else if(ETSaveAmount.getText().toString().equalsIgnoreCase("0"))
                {
                    dialog = new DialogCustrom(ManagerSaveCoinActivity.this,
                            "Coin Management",
                            "적립 금액은 1coin 이상부터 입니다.",
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
                    managerSaveCoinPresenterInterface.saveCoin(userEMail, Integer.parseInt(ETSaveAmount.getText().toString()));
                }
            }
        });
    }

    public void setETSaveAmount()
    {
        ETSaveAmount = (EditText)findViewById(R.id.et_save_amount);
    }

    @Override
    public void setTVCoin(int coin)
    {
        TVCoin = (TextView)findViewById(R.id.tv_coin);

        TVCoin.setText(coin + " coin");
    }

    @Override
    public void successSaveCoin()
    {
        dialog = new DialogCustrom(this,
                "Coin Management",
                ETSaveAmount.getText().toString() + " coin\n\n을 적립하였습니다.",
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        ManagerSaveCoinActivity.this.finish();
                    }
                });
        dialog.show();
    }

    @Override
    public void failedSaveCoin()
    {
        dialog = new DialogCustrom(this,
                "Coin Management",
                "적립에 실패하였습니다.",
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
