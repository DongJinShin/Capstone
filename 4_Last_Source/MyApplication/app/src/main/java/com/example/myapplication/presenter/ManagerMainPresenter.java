package com.example.myapplication.presenter;

import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.interfaces.ManagerMainPresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.view.LogInView;
import com.example.myapplication.view.ManagerMainView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagerMainPresenter implements ManagerMainPresenterInterface
{
    ManagerMainView managerMainView;

    public ManagerMainPresenter(ManagerMainView managerMainView)
    {
        this.managerMainView = managerMainView;
    }

    @Override
    public void selectOneUserByEMail(String EMail)
    {

    }
}
