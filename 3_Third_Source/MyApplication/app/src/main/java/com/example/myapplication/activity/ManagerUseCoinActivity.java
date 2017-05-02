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
import com.example.myapplication.presenter.ManagerSaveCoinPresenter;
import com.example.myapplication.presenter.ManagerUseCoinPresenter;
import com.example.myapplication.presenter.interfaces.ManagerSaveCoinPresenterInterface;
import com.example.myapplication.presenter.interfaces.ManagerUseCoinPresenterInterface;
import com.example.myapplication.view.ManagerSaveCoinView;
import com.example.myapplication.view.ManagerUseCoinView;

public class ManagerUseCoinActivity extends AppCompatActivity implements ManagerUseCoinView
{
    private ManagerUseCoinPresenterInterface managerUseCoinPresenterInterface;

    private LinearLayout BTNUseComplete;

    private TextView TVCoin;

    private EditText ETUseAmount;

    private String userEMail;
    private String managerEMail;

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

        setContentView(R.layout.activity_manager_use_coin);

        this.initLayout();
    }

    public void initLayout()
    {
        this.setManagerUseCoinPresenterInterface();
        this.getUserInfo();
        this.getManagerInfo();
        this.setStatusBarColor();
        this.setETUseAmount();
        this.setBTNUseComplete();
    }

    public void setManagerUseCoinPresenterInterface()
    {
        managerUseCoinPresenterInterface = new ManagerUseCoinPresenter(this);
    }

    public void getUserInfo()
    {
        Intent intent = getIntent();

        userEMail = intent.getStringExtra("userEMail");

        managerUseCoinPresenterInterface.selectOneUserCoinByEMail(userEMail);
    }

    public void getManagerInfo()
    {
        Intent intent = getIntent();

        managerEMail = intent.getStringExtra("managerEMail");
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

    public void setBTNUseComplete()
    {
        BTNUseComplete = (LinearLayout)findViewById(R.id.btn_use_complete);
        BTNUseComplete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(ETUseAmount.getText().toString() == null)
                {
                    dialog = new DialogCustrom(ManagerUseCoinActivity.this,
                            "Coin Management",
                            "사용 금액을 확인해주세요.",
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
                else if(ETUseAmount.getText().toString().equalsIgnoreCase(""))
                {
                    dialog = new DialogCustrom(ManagerUseCoinActivity.this,
                            "Coin Management",
                            "사용 금액을 확인해주세요.",
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
                else if(ETUseAmount.getText().toString().equalsIgnoreCase("0"))
                {
                    dialog = new DialogCustrom(ManagerUseCoinActivity.this,
                            "Coin Management",
                            "사용 금액은 1coin 이상부터 입니다.",
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
                    managerUseCoinPresenterInterface.useCoin(managerEMail, userEMail, Integer.parseInt(ETUseAmount.getText().toString()));
                }
            }
        });
    }

    public void setETUseAmount()
    {
        ETUseAmount = (EditText)findViewById(R.id.et_use_amount);
    }

    @Override
    public void setTVCoin(int coin)
    {
        TVCoin = (TextView)findViewById(R.id.tv_coin);

        TVCoin.setText(coin + " coin");
    }

    @Override
    public void successUseCoin()
    {
        dialog = new DialogCustrom(this,
                                                "Coin Management",
                                                ETUseAmount.getText().toString() + " coin\n\n을 사용하였습니다.",
                                                new View.OnClickListener()
                                                {
                                                    @Override
                                                    public void onClick(View v)
                                                    {
                                                        ManagerUseCoinActivity.this.finish();
                                                    }
                                                });
        dialog.show();
    }

    @Override
    public void failedUseCoinByLowBalance()
    {
        dialog = new DialogCustrom(this,
                                                "Coin Management",
                                                "사용 가능 적립금이 부족합니다.",
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
    public void failedUseCoinByNonError()
    {
        dialog = new DialogCustrom(this,
                                                "Coin Management",
                                                "알 수 없는 이유로\n\n적립금 사용에 실패하였습니다.",
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
