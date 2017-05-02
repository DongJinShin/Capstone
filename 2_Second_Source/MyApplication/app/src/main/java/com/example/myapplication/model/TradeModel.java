package com.example.myapplication.model;

/**
 * Created by WonjinKim on 2017-04-17.
 */

public class TradeModel
{
    String FromEMail;
    String DSTEMail;
    int amount;
    int balance;
    String time;
    int type;

    public String getFromEMail()
    {
        return FromEMail;
    }

    public void setFromEMail(String fromEMail)
    {
        FromEMail = fromEMail;
    }

    public String getDSTEMail()
    {
        return DSTEMail;
    }

    public void setDSTEMail(String DSTEMail)
    {
        this.DSTEMail = DSTEMail;
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
