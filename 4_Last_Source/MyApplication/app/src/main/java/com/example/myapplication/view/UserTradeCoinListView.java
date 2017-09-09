package com.example.myapplication.view;

import com.example.myapplication.model.TradeModel;

import java.util.ArrayList;
import java.util.List;

public interface UserTradeCoinListView
{
    public void setTVBalance(int balance);
    public void setLVTradeCoinList(ArrayList<TradeModel> items);
}
