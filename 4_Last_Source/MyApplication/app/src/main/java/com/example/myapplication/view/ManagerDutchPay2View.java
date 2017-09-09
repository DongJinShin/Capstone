package com.example.myapplication.view;

public interface ManagerDutchPay2View
{
    public void judgmentCoin(String userID, int coin, int mode);
    public void successDutchPay();
    public void failedDutchPay();
    public void failedCertification();
}
