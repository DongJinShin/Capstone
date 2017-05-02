package com.example.myapplication.view;

import com.example.myapplication.model.TradeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WonjinKim on 2017-04-17.
 */

public interface UserTradeCoinListView
{
    public void setTVBalance(int balance);
    public void setLVTradeCoinList(ArrayList<TradeModel> items);
}
