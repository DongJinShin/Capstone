package com.example.myapplication.view;

import com.example.myapplication.model.UserModel;

public interface SignUpView
{
    public void goToLoginActivity();
    public void failedSignUpByDuplicatedEMail();
}
