package com.example.myapplication.view;

public interface ManagerUseCoinView
{
    public void setTVCoin(int coin);
    public void successUseCoin();
    public void failedUseCoinByLowBalance();
    public void failedUseCoinByNonError();
}
