package com.example.myapplication.view;

import com.example.myapplication.model.UserModel;

public interface LogInView
{
    public void goToUserMainActivity(UserModel userModel);
    public void goToManagerMainActivity(String userEMail);
    public void goToCaptureActivity();
    public void failedLogIn();
}
