package com.example.myapplication.presenter.interfaces;

public interface ManagerDutchPay2PresenterInterface
{
    public void selectOneUserCoinByID(String ID, int mode);
    public void dutchPay2(String managerID, String ID1, String ID2, int amount1, int amount2);
    public void dutchPay3(String managerID, String ID1, String ID2, String ID3, int amount1, int amount2, int amount3);
    public void dutchPay4(String managerID, String ID1, String ID2, String ID3, String ID4, int amount1, int amount2, int amount3, int amount4);
    public void dutchPay5(String managerID, String ID1, String ID2, String ID3, String ID4, String ID5, int amount1, int amount2, int amount3, int amount4, int amount5);
    public void dutchPay6(String managerID, String ID1, String ID2, String ID3, String ID4, String ID5, String ID6, int amount1, int amount2, int amount3, int amount4, int amount5, int amount6);
}
