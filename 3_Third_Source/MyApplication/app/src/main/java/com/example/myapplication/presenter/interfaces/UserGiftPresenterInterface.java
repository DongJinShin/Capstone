package com.example.myapplication.presenter.interfaces;

public interface UserGiftPresenterInterface
{
    public void selectOneUserCoinByEMail(String EMail);
    public void giftCoin(String FromEMail, String DSTEMail, int amount);
}
