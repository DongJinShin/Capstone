package com.example.myapplication.view;

import com.example.myapplication.model.UserModel;

public interface SignUpView
{
    public void goToUserMainActivity(UserModel userModel);
    public void failedSignUpByDuplicatedEMail();
}
