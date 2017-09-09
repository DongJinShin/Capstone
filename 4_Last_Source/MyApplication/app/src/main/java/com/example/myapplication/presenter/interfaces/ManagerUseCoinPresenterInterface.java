package com.example.myapplication.presenter.interfaces;

public interface ManagerUseCoinPresenterInterface
{
    public void selectOneUserCoinByID(String ID);
    public void useCoin(String managerID, String userID, int amount);
}
