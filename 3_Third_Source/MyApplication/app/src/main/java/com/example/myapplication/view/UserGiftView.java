package com.example.myapplication.view;

public interface UserGiftView
{
    public void setTVCoin(int coin);
    public void successGiftCoin();
    public void failedGiftCoinByLowBalance();
    public void failedGiftCoinByNotFountDSTUser();
    public void failedGiftCoinByNonError();
}
