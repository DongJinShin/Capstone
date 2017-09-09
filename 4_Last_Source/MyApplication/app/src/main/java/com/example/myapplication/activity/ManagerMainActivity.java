package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.presenter.ManagerMainPresenter;
import com.example.myapplication.presenter.interfaces.ManagerMainPresenterInterface;
import com.example.myapplication.view.ManagerMainView;

public class ManagerMainActivity extends AppCompatActivity implements ManagerMainView
{
    private ManagerMainPresenterInterface managerMainPresenterInterface;

    private TextView BTNNotice;
    private TextView BTNUseCoin;
    private TextView BTNSaveCoin;
    private TextView BTNSetting;
    private TextView BTNDutchPay;

    private String managerID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        setContentView(R.layout.activity_manager_main);

        this.initLayout();
    }

    public void initLayout()
    {
        this.setManagerMainPresenterInterface();
        this.getManagerInfo();
        this.setStatusBarColor();
        this.setBTNNotice();
        this.setBTNSetting();
        this.setBTNSaveCoin();
        this.setBTNUseCoin();
        this.setBTNDutchPay();
    }

    public void setManagerMainPresenterInterface()
    {
        managerMainPresenterInterface = new ManagerMainPresenter(this);
    }

    public void getManagerInfo()
    {
        Intent intent = getIntent();

        managerID = intent.getStringExtra("managerID");
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

    public void setBTNSetting()
    {
        BTNSetting = (TextView)findViewById(R.id.btn_setting);

        BTNSetting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ManagerMainActivity.this, SettingActivity.class);
                ManagerMainActivity.this.startActivity(intent);
            }
        });
    }

    public void setBTNDutchPay()
    {
        BTNDutchPay = (TextView)findViewById(R.id.btn_dutch_pay);

        BTNDutchPay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ManagerMainActivity.this, ManagerDutchPay1Activity.class);
                intent.putExtra("managerID", managerID);
                ManagerMainActivity.this.startActivity(intent);
            }
        });
    }

    public void setBTNNotice()
    {
        BTNNotice = (TextView)findViewById(R.id.btn_notice);

        BTNNotice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ManagerMainActivity.this, NoticeActivity.class);
                ManagerMainActivity.this.startActivity(intent);
            }
        });
    }

    public void setBTNUseCoin()
    {
        BTNUseCoin = (TextView)findViewById(R.id.btn_use_coin);

        BTNUseCoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ManagerMainActivity.this, ManagerUseCoinActivity.class);
                intent.putExtra("managerID", managerID);
                ManagerMainActivity.this.startActivity(intent);
            }
        });
    }

    public void setBTNSaveCoin()
    {
        BTNSaveCoin = (TextView)findViewById(R.id.btn_save);

        BTNSaveCoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ManagerMainActivity.this, ManagerSaveCoinActivity.class);
                intent.putExtra("managerID", managerID);
                ManagerMainActivity.this.startActivity(intent);
            }
        });
    }
}
