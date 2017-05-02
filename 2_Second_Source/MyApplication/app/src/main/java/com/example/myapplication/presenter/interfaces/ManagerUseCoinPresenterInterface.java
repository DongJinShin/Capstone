package com.example.myapplication.presenter.interfaces;

public interface ManagerUseCoinPresenterInterface
{
    public void selectOneUserCoinByEMail(String EMail);
    public void useCoin(String managerEMail, String userEMail, int amount);
}
