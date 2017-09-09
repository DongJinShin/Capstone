package com.example.myapplication.presenter;

import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.interfaces.ManagerSaveCoinPresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.view.ManagerSaveCoinView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagerSaveCoinPresenter implements ManagerSaveCoinPresenterInterface
{
    ManagerSaveCoinView managerSaveCoinView;

    public ManagerSaveCoinPresenter(ManagerSaveCoinView managerSaveCoinView)
    {
        this.managerSaveCoinView = managerSaveCoinView;
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
                managerSaveCoinView.setTVCoin(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {

            }
        });
    }

    @Override
    public void saveCoin(String managerID, String userID, int amount)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<String> productModel = retrofitinterface.saveCoin(managerID, userID, amount);
        productModel.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if(response.body().equalsIgnoreCase("200"))
                {
                    managerSaveCoinView.successSaveCoin();
                }
                else if(response.body().equalsIgnoreCase("500"))
                {
                    managerSaveCoinView.failedSaveCoin();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                int a = 10;
            }
        });
    }
}
