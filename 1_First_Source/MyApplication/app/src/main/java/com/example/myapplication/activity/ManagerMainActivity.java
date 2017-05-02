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

    private LinearLayout BTNCheckCoin;
    private LinearLayout BTNUseCoin;
    private LinearLayout BTNSaveCoin;

    private TextView TVNickname;
    private TextView TVCoin;

    private String userEMail;

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
        this.getUserInfo();
        this.setStatusBarColor();
        this.setBTNCheckCoin();
        this.setBTNSaveCoin();
        this.setBTNUseCoin();
    }

    public void setManagerMainPresenterInterface()
    {
        managerMainPresenterInterface = new ManagerMainPresenter(this);
    }

    public void getUserInfo()
    {
        Intent intent = getIntent();

        userEMail = intent.getStringExtra("userEMail");

        managerMainPresenterInterface.selectOneUserByEMail(userEMail);
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

    public void setBTNCheckCoin()
    {
        BTNCheckCoin = (LinearLayout)findViewById(R.id.btn_check_coin);

        BTNCheckCoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ManagerMainActivity.this, SaveCoinCheckActivity.class);
                intent.putExtra("userEMail", userEMail);
                ManagerMainActivity.this.startActivity(intent);
            }
        });
    }

    public void setBTNUseCoin()
    {
        BTNUseCoin = (LinearLayout)findViewById(R.id.btn_use_coin);

        BTNUseCoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ManagerMainActivity.this, ManagerUseCoinActivity.class);
                intent.putExtra("userEMail", userEMail);
                ManagerMainActivity.this.startActivity(intent);
            }
        });
    }

    public void setBTNSaveCoin()
    {
        BTNSaveCoin = (LinearLayout)findViewById(R.id.btn_save);

        BTNSaveCoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ManagerMainActivity.this, ManagerSaveCoinActivity.class);
                intent.putExtra("userEMail", userEMail);
                ManagerMainActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void setTVNickName(String nickname)
    {
        TVNickname = (TextView)findViewById(R.id.tv_nickname);

        TVNickname.setText("사용자 닉네임 : " +  nickname);
    }

    @Override
    public void setTVCoin(int coin)
    {
        TVCoin = (TextView)findViewById(R.id.tv_coin);

        TVCoin.setText("현재 적립금 : " +  coin + " coin");
    }
}
