package com.example.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

public class DialogCustrom extends Dialog
{
    private int dialogType = 1;

    private TextView TVTitle;
    private TextView TVContent;

    private TextView BTNConfirm;
    private TextView BTNCancel;

    private String title;
    private String content;

    private View.OnClickListener BTNConfirmClickListener;
    private View.OnClickListener BTNCancelClickListener;


    public DialogCustrom(Context context, String title, String content, View.OnClickListener BTNConfirmClickListener)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.title = title;
        this.content = content;
        this.BTNConfirmClickListener = BTNConfirmClickListener;
        this.dialogType = 1;
    }

    public DialogCustrom(Context context, String title, String content, View.OnClickListener BTNConfirmClickListener, View.OnClickListener BTNCancelClickListener)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.title = title;
        this.content = content;
        this.BTNConfirmClickListener = BTNConfirmClickListener;
        this.BTNCancelClickListener = BTNCancelClickListener;
        this.dialogType = 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.initLayout();
    }

    public void initLayout()
    {
        this.setTranslucentBackground();

        setContentView(R.layout.dialog_custom);

        this.setTVTitle(title);
        this.setTVContent(content);
        this.setBTNConfirm(BTNConfirmClickListener);

        if(dialogType == 2)
        {
            this.setBTNCancel(BTNCancelClickListener);
        }
    }

    public void setTranslucentBackground()
    {
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
    }

    public void setTVTitle(String title)
    {
        TVTitle = (TextView) findViewById(R.id.tv_title);

        TVTitle.setText(title);
    }

    public void setTVContent(String content)
    {
        TVContent = (TextView) findViewById(R.id.tv_content);

        TVContent.setText(content);
    }

    public void setBTNConfirm(View.OnClickListener BTNConfirmClickListener)
    {
        BTNConfirm = (TextView)findViewById(R.id.btn_confirm);

        BTNConfirm.setOnClickListener(BTNConfirmClickListener);

        if(dialogType == 1)
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);

            BTNConfirm.setLayoutParams(params);
        }
    }

    public void setBTNCancel(View.OnClickListener BTNCancelClickListener)
    {
        BTNCancel = (TextView)findViewById(R.id.btn_cancel);

        BTNCancel.setOnClickListener(BTNCancelClickListener);
    }
}
