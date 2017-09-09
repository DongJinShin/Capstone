package com.example.myapplication.presenter;

import com.example.myapplication.presenter.interfaces.ManagerSaveCoinPresenterInterface;
import com.example.myapplication.presenter.interfaces.ManagerUseCoinPresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.view.ManagerSaveCoinView;
import com.example.myapplication.view.ManagerUseCoinView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagerUseCoinPresenter implements ManagerUseCoinPresenterInterface
{
    ManagerUseCoinView managerUseCoinView;

    public ManagerUseCoinPresenter(ManagerUseCoinView managerUseCoinView)
    {
        this.managerUseCoinView = managerUseCoinView;
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
                managerUseCoinView.setTVCoin(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {

            }
        });
    }

    @Override
    public void useCoin(String managerID, String userID, int amount)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<String> productModel = retrofitinterface.useCoin(managerID, userID, amount);
        productModel.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.body().equalsIgnoreCase("200"))
                {
                    managerUseCoinView.successUseCoin();
                }
                else if(response.body().equalsIgnoreCase("500"))
                {
                    managerUseCoinView.failedUseCoinByNonError();
                }
                else if(response.body().equalsIgnoreCase("501"))
                {
                    managerUseCoinView.failedUseCoinByNonError();
                }
                else if(response.body().equalsIgnoreCase("502"))
                {
                    managerUseCoinView.failedUseCoinByLowBalance();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {

            }
        });
    }
}
