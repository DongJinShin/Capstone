package com.example.myapplication.view;

import com.example.myapplication.activity.CaptureActivityPortrait;
import com.example.myapplication.activity.LogInActivity;
import com.google.zxing.integration.android.IntentIntegrator;

public interface ManagerUseCoinView
{
    public void goToCaptureActivity();
    public void setTVCoin(int coin);
    public void successUseCoin();
    public void failedUseCoinByLowBalance();
    public void failedUseCoinByNonError();
}
