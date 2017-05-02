package com.example.myapplication.presenter;

import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.interfaces.UserGiftPresenterInterface;
import com.example.myapplication.presenter.interfaces.UserMainPresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.view.UserGiftView;
import com.example.myapplication.view.UserMainView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WonjinKim on 2017-04-18.
 */

public class UserMainPresenter implements UserMainPresenterInterface
{
    UserMainView userMainView;

    public UserMainPresenter(UserMainView userMainView)
    {
        this.userMainView = userMainView;
    }

    @Override
    public void selectOneUserByEMail(String EMail)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<UserModel> productModel = retrofitinterface.selectOneUserByEMail(EMail);
        productModel.enqueue(new Callback<UserModel>()
        {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response)
            {
                userMainView.setTVCoin(response.body().getCoin());
                userMainView.setTVName(response.body().getName());
                userMainView.setTVEMail(response.body().getEMail());
                userMainView.setIVQRCode(response.body().getQRUri());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t)
            {

            }
        });
    }
}
