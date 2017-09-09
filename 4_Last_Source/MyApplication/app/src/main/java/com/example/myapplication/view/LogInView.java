package com.example.myapplication.view;

import com.example.myapplication.model.UserModel;

public interface LogInView
{
    public void goToUserMainActivity(String userEMail);
    public void goToManagerMainActivity(String managerEMail);
    public void failedLogIn();
}
