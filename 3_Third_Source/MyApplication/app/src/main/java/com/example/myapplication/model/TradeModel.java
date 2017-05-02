package com.example.myapplication.model;

/**
 * Created by WonjinKim on 2017-04-17.
 */

public class TradeModel
{
    String from;
    String DST;
    int amount;
    int balance;
    String time;
    int type;

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        from = from;
    }

    public String getDST()
    {
        return DST;
    }

    public void setDST(String DST)
    {
        this.DST = DST;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public int getBalance()
    {
        return balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}
