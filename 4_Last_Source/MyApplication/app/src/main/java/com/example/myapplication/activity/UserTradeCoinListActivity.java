package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TradeCoinListAdapter;
import com.example.myapplication.dialog.DialogCustrom;
import com.example.myapplication.model.TradeModel;
import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.LogInPresenter;
import com.example.myapplication.presenter.UserTradeCoinListPresenter;
import com.example.myapplication.presenter.interfaces.LogInPresenterInterface;
import com.example.myapplication.presenter.interfaces.UserTradeCoinListPresenterInterface;
import com.example.myapplication.view.LogInView;
import com.example.myapplication.view.UserTradeCoinListView;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

public class UserTradeCoinListActivity extends AppCompatActivity implements UserTradeCoinListView
{
    private UserTradeCoinListPresenterInterface userTradeCoinListPresenterInterface;

    private TextView TVBalance;
    private ListView LVTradeCoinList;

    private String userID;

    private ImageView BTNBack;

    private ImageView BTNRenew;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_trade_coin_list);

        this.initLayout();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void initLayout()
    {
        this.setUserTradeCoinListPresenterInterface();
        this.setStatusBarColor();
        this.getUserInfo();
        this.setBTNBack();
        this.setBTNRenew();
        this.userTradeCoinListPresenterInterface.selectOneUserCoinByID(userID);
        this.userTradeCoinListPresenterInterface.getTradeCoinList(userID);
    }

    public void setUserTradeCoinListPresenterInterface()
    {
        userTradeCoinListPresenterInterface = new UserTradeCoinListPresenter(this);
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

    public void getUserInfo()
    {
        Intent intent = getIntent();

        userID = intent.getStringExtra("userID");
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

    public void setBTNRenew()
    {
        BTNRenew = (ImageView)findViewById(R.id.renew);

        BTNRenew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UserTradeCoinListActivity.this.initLayout();
            }
        });
    }

    @Override
    public void setTVBalance(int balance)
    {
        TVBalance = (TextView) findViewById(R.id.tv_balance);
        TVBalance.setText(balance + " coin");
    }

    @Override
    public void setLVTradeCoinList(ArrayList<TradeModel> items)
    {
        LVTradeCoinList = (ListView) findViewById(R.id.lv_trade);
        LVTradeCoinList.setAdapter(new TradeCoinListAdapter(this, R.layout.item_trade_coin_list, items, userID));
    }
}