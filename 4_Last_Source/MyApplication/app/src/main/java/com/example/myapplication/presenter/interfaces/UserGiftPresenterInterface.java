package com.example.myapplication.presenter.interfaces;

public interface UserGiftPresenterInterface
{
    public void selectOneUserCoinByID(String ID);
    public void giftCoin(String FromID, String DSTID, int amount);
}
