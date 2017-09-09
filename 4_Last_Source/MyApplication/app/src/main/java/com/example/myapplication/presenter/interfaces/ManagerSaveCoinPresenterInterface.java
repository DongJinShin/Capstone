package com.example.myapplication.presenter.interfaces;

public interface ManagerSaveCoinPresenterInterface
{
    public void selectOneUserCoinByID(String ID);
    public void saveCoin(String managerEMail, String userEMail, int amount);
}
