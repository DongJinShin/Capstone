package com.example.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dialog.DialogCustrom;
import com.example.myapplication.presenter.ManagerUseCoinPresenter;
import com.example.myapplication.presenter.UserGiftPresenter;
import com.example.myapplication.presenter.interfaces.ManagerUseCoinPresenterInterface;
import com.example.myapplication.presenter.interfaces.UserGiftPresenterInterface;
import com.example.myapplication.view.UserGiftView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserGiftActivity extends AppCompatActivity implements UserGiftView
{
    private UserGiftPresenterInterface userGiftPresenterInterface;

    private LinearLayout BTNGiftComplete;

    private TextView TVCoin;

    private EditText ETDSTEMail;
    private EditText ETGiftAmount;

    private String userEMail;

    private DialogCustrom dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        setContentView(R.layout.activity_user_gift);

        this.initLayout();
    }

    public void initLayout()
    {
        this.setUserGiftPresenterInterface();
        this.getUserInfo();
        this.setStatusBarColor();
        this.setETDSTEMail();
        this.setETGiftAmount();
        this.setBTNGiftComplete();
    }

    public void setUserGiftPresenterInterface()
    {
        userGiftPresenterInterface = new UserGiftPresenter(this);
    }

    public void getUserInfo()
    {
        Intent intent = getIntent();

        userEMail = intent.getStringExtra("userEMail");

        userGiftPresenterInterface.selectOneUserCoinByEMail(userEMail);
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

    public void setBTNGiftComplete()
    {
        BTNGiftComplete = (LinearLayout)findViewById(R.id.btn_gift_complete);
        BTNGiftComplete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!checkEMail(ETDSTEMail.getText().toString()))
                {
                    dialog = new DialogCustrom(UserGiftActivity.this,
                            "Coin Management",
                            "선물 받을 이메일의 형식을 확인해주세요.",
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
                else if(ETGiftAmount.getText().toString() == null)
                {
                    dialog = new DialogCustrom(UserGiftActivity.this,
                            "Coin Management",
                            "선물할 적립급을 확인해 주세요.",
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
                else if(ETGiftAmount.getText().toString().equalsIgnoreCase(""))
                {
                    dialog = new DialogCustrom(UserGiftActivity.this,
                            "Coin Management",
                            "선물할 적립급을 확인해 주세요.",
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
                else if(ETGiftAmount.getText().toString().equalsIgnoreCase("0"))
                {
                    dialog = new DialogCustrom(UserGiftActivity.this,
                            "Coin Management",
                            "선물할 적립급은 1coin 이상부터 입니다.",
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
                    userGiftPresenterInterface.giftCoin(userEMail, ETDSTEMail.getText().toString(), Integer.parseInt(ETGiftAmount.getText().toString()));
                }
            }
        });
    }

    public void setETGiftAmount()
    {
        ETGiftAmount = (EditText)findViewById(R.id.et_gift_amount);
    }

    public void setETDSTEMail()
    {
        ETDSTEMail = (EditText)findViewById(R.id.et_dst_user_email);
    }

    public boolean checkEMail(String EMail)
    {
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(EMail);

        boolean isNormal = m.matches();

        return isNormal;
    }


    @Override
    public void setTVCoin(int coin)
    {
        TVCoin = (TextView)findViewById(R.id.tv_coin);

        TVCoin.setText(coin + "  coin");
    }

    @Override
    public void successGiftCoin()
    {
        dialog = new DialogCustrom(this,
                "Coin Management",
                ETGiftAmount.getText().toString() + " coin을\n\n" + ETDSTEMail.getText().toString() + "\n\n회원에게 선물하였습니다.",
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        UserGiftActivity.this.finish();
                    }
                });
        dialog.show();
    }

    @Override
    public void failedGiftCoinByLowBalance()
    {
        dialog = new DialogCustrom(this,
                "Coin Management",
                "선물 가능 적립금이 부족합니다.",
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
    public void failedGiftCoinByNotFountDSTUser()
    {
        dialog = new DialogCustrom(this,
                "Coin Management",
                "선물 받을 회원이 존재하지 않습니다.",
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
    public void failedGiftCoinByNonError()
    {
        dialog = new DialogCustrom(this,
                "Coin Management",
                "알 수 없는 이유로\n\n적립금 선물에 실패하였습니다.",
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
