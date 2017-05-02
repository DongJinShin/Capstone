package com.example.myapplication.presenter;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.myapplication.activity.SignUpActivity;
import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.interfaces.SignUpPresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.utils.SecurityUtil;
import com.example.myapplication.view.SignUpView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpPresenter implements SignUpPresenterInterface
{
    SignUpView signUpView;

    public SignUpPresenter(SignUpView signUpView)
    {
        this.signUpView = signUpView;
    }

    @Override
    public void signUp(String EMail, String password, String nickname)
    {
        String securityPassword = SecurityUtil.SHA256(password);

        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<UserModel> productModel = retrofitinterface.signUp(EMail, securityPassword, nickname);
        productModel.enqueue(new Callback<UserModel>()
        {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response)
            {
                signUpView.goToLoginActivity();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t)
            {
                signUpView.failedSignUpByDuplicatedEMail();
            }
        });
    }
}
