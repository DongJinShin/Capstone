package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.NoticeModel;

import org.w3c.dom.Text;

public class NoticeDetailActivity extends AppCompatActivity
{
    private TextView TVTitle;
    private TextView TVTime;
    private TextView TVContent;

    private ImageView BTNBack;

    private NoticeModel noticeModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
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
        this.setBTNBack();
        this.getNoticeModel();
        this.setTVTitle();
        this.setTVTime();
        this.setTVContent();
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

    public void getNoticeModel()
    {
        Intent intent = getIntent();
        noticeModel = (NoticeModel)intent.getSerializableExtra("notice");
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

    public void setTVTitle()
    {
        TVTitle = (TextView)findViewById(R.id.tv_title);
        TVTitle.setText(noticeModel.getTitle());
    }

    public void setTVTime()
    {
        TVTime = (TextView)findViewById(R.id.tv_time);
        TVTime.setText(noticeModel.getTime());
    }

    public void setTVContent()
    {
        TVContent = (TextView)findViewById(R.id.tv_content);
        TVContent.setText(noticeModel.getContent());
    }
}
