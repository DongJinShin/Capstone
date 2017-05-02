package com.example.myapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.UserGiftPresenter;
import com.example.myapplication.presenter.UserMainPresenter;
import com.example.myapplication.presenter.interfaces.UserMainPresenterInterface;
import com.example.myapplication.view.UserMainView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

public class UserMainActivity extends AppCompatActivity implements UserMainView
{
    private UserMainPresenterInterface userMainPresenterInterface;

    private String userEMail;

    private LinearLayout BTNUserGift;
    private LinearLayout BTNUserTradeCoinList;

    private ImageView IVQRCode;

    private TextView TVEMail;
    private TextView TVName;
    private TextView TVCoin;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        setContentView(R.layout.activity_user_main);

        initLayout();
    }

    public void initLayout()
    {
        this.setUserMainPresenterInterface();
        this.setStatusBarColor();
        this.setUserInfo();
        this.setBTNUserGift();
        this.setBTNUserTradeCoinList();

        this.userMainPresenterInterface.selectOneUserByEMail(userEMail);
    }

    public void setUserMainPresenterInterface()
    {
        userMainPresenterInterface = new UserMainPresenter(this);
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

    public void setUserInfo()
    {
        Intent intent = getIntent();

        this.userEMail = intent.getStringExtra("userEMail");
    }

    public void setIVQRCode(final String QRUri)
    {
        IVQRCode = (ImageView)findViewById(R.id.iv_qrcode);

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                try
                {
                    URL url = new URL(QRUri);
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable()
                    {

                        @Override
                        public void run() {
                            IVQRCode.setImageBitmap(bm);
                        }
                    });
                    //IVQRCode.setImageBitmap(bm); //비트맵 객체로 보여주기
                }
                catch(Exception e)
                {

                }

            }
        });
        thread.start();
    }

    public void setTVEMail(String EMail)
    {
        TVEMail = (TextView)findViewById(R.id.tv_email);

        TVEMail.setText("이메일 : " + EMail);
    }

    public void setTVName(String name)
    {
        TVName = (TextView)findViewById(R.id.tv_name);

        TVName.setText("이름 : " + name);
    }

    public void setTVCoin(int coin)
    {
        TVCoin = (TextView)findViewById(R.id.tv_coin);

        TVCoin.setText("적립금 : " + coin + " coin");
    }

    public void setBTNUserGift()
    {
        BTNUserGift = (LinearLayout)findViewById(R.id.btn_gift);

        BTNUserGift.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UserMainActivity.this, UserGiftActivity.class);
                intent.putExtra("userEMail", userEMail);
                UserMainActivity.this.startActivity(intent);
            }
        });
    }

    public void setBTNUserTradeCoinList()
    {
        BTNUserTradeCoinList = (LinearLayout)findViewById(R.id.btn_user_trade_coin_list);

        BTNUserTradeCoinList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UserMainActivity.this, UserTradeCoinListActivity.class);
                intent.putExtra("userEMail", userEMail);
                UserMainActivity.this.startActivity(intent);
            }
        });
    }
}
