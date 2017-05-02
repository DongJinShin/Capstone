package com.example.myapplication.presenter.interfaces;

public interface ManagerSaveCoinPresenterInterface
{
    public void selectOneUserCoinByEMail(String EMail);
    public void saveCoin(String managerEMail, String userEMail, int amount);
}
