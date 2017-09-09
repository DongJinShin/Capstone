package com.example.myapplication.presenter.interfaces;

public interface SignUpPresenterInterface
{
    public void confirmDuplicatedID(String ID);
    public void signUp(String ID, String EMail, String password, String nickname);
}
