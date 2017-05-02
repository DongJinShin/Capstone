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

import com.example.myapplication.R;
import com.example.myapplication.model.UserModel;

import java.io.InputStream;
import java.net.URL;

public class UserMainActivity extends AppCompatActivity
{
    private UserModel userModel;

    private LinearLayout BTNUserGift;

    private ImageView IVQRCode;

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
        this.setStatusBarColor();
        this.setUser();
        this.setIVQRCode();
        this.setBTNUserGift();
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

    public void setUser()
    {
        Intent intent = getIntent();

        UserModel userModel = (UserModel)intent.getSerializableExtra("user");

        this.userModel = userModel;
    }

    public void setIVQRCode()
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
                    URL url = new URL(userModel.getQRUri());
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

    public void setBTNUserGift()
    {
        BTNUserGift = (LinearLayout)findViewById(R.id.btn_gift);

        BTNUserGift.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UserMainActivity.this, UserGiftActivity.class);
                intent.putExtra("userEMail", userModel.getEMail());
                UserMainActivity.this.startActivity(intent);
            }
        });
    }
}
