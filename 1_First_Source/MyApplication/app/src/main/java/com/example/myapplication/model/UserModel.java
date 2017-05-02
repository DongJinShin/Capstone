package com.example.myapplication.model;

import java.io.Serializable;

public class UserModel implements Serializable
{
    int userID;
    String EMail;
    String password;
    String nickname;
    String QRUri;
    int coin;

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getEMail()
    {
        return EMail;
    }

    public void setEMail(String EMail)
    {
        this.EMail = EMail;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getQRUri()
    {
        return QRUri;
    }

    public void setQRUri(String QRUri)
    {
        this.QRUri = QRUri;
    }

    public int getCoin()
    {
        return coin;
    }

    public void setCoin(int coin)
    {
        this.coin = coin;
    }
}
