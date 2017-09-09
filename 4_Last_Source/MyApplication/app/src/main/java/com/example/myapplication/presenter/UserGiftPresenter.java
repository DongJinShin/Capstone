package com.example.myapplication.presenter;

import com.example.myapplication.presenter.interfaces.UserGiftPresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.view.UserGiftView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserGiftPresenter implements UserGiftPresenterInterface
{
    UserGiftView userGiftView;

    public UserGiftPresenter(UserGiftView userGiftView)
    {
        this.userGiftView = userGiftView;
    }

    @Override
    public void selectOneUserCoinByID(String ID)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<Integer> productModel = retrofitinterface.selectOneUserCoinByID(ID);
        productModel.enqueue(new Callback<Integer>()
        {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                userGiftView.setTVCoin(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {

            }
        });
    }

    @Override
    public void giftCoin(String FromID, String DSTID, int amount)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<String> productModel = retrofitinterface.giftCoin(FromID, DSTID, amount);
        productModel.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.body().equalsIgnoreCase("200"))
                {
                    userGiftView.successGiftCoin();
                }
                else if(response.body().equalsIgnoreCase("500"))
                {
                    userGiftView.failedGiftCoinByNonError();
                }
                else if(response.body().equalsIgnoreCase("501"))
                {
                    userGiftView.failedGiftCoinByNonError();
                }
                else if(response.body().equalsIgnoreCase("502"))
                {
                    userGiftView.failedGiftCoinByLowBalance();
                }
                else if(response.body().equalsIgnoreCase("503"))
                {
                    userGiftView.failedGiftCoinByNotFountDSTUser();
                }
                else if(response.body().equalsIgnoreCase("504"))
                {
                    userGiftView.failedGiftCoinByNonError();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {

            }
        });
    }
}
