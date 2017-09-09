package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NoticeAdapter;
import com.example.myapplication.adapter.TradeCoinListAdapter;
import com.example.myapplication.model.NoticeModel;
import com.example.myapplication.presenter.NoticePresenter;
import com.example.myapplication.presenter.UserTradeCoinListPresenter;
import com.example.myapplication.presenter.interfaces.NoticePresenterInterface;
import com.example.myapplication.presenter.interfaces.UserTradeCoinListPresenterInterface;
import com.example.myapplication.view.NoticeView;
import com.example.myapplication.view.UserTradeCoinListView;

import java.util.ArrayList;

public class NoticeActivity  extends AppCompatActivity implements NoticeView
{
    private NoticePresenterInterface noticePresenterInterface;

    private ListView LVNoticeList;

    private ImageView BTNBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        this.initLayout();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public void initLayout()
    {
        this.setNoticePresenterInterface();
        this.setStatusBarColor();
        this.noticePresenterInterface.getNoticeList();
        this.setBTNBack();
    }

    public void setNoticePresenterInterface()
    {
        noticePresenterInterface = new NoticePresenter(this);
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

    public void goToNoticeDetailActivity(NoticeModel noticeModel)
    {
        Intent intent = new Intent(NoticeActivity.this, NoticeDetailActivity.class);
        intent.putExtra("notice", noticeModel);
        this.startActivity(intent);

    }

    @Override
    public void setLVNoticeList(final ArrayList<NoticeModel> items)
    {
        LVNoticeList = (ListView) findViewById(R.id.lv_notice);

        LVNoticeList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                goToNoticeDetailActivity(items.get(position));
            }
        });

        LVNoticeList.setAdapter(new NoticeAdapter(this, R.layout.item_notice, items));
    }
}
