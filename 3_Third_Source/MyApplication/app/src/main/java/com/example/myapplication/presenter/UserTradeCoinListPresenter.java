package com.example.myapplication.presenter;

import com.example.myapplication.model.CoinSaveModel;
import com.example.myapplication.model.TradeModel;
import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.interfaces.UserTradeCoinListPresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.view.ManagerMainView;
import com.example.myapplication.view.UserTradeCoinListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WonjinKim on 2017-04-17.
 */

public class UserTradeCoinListPresenter implements UserTradeCoinListPresenterInterface
{
    UserTradeCoinListView userTradeCoinListView;

    public UserTradeCoinListPresenter(UserTradeCoinListView userTradeCoinListView)
    {
        this.userTradeCoinListView = userTradeCoinListView;
    }

    @Override
    public void selectOneUserCoinByEMail(String EMail)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<Integer> productModel = retrofitinterface.selectOneUserCoinByEMail(EMail);
        productModel.enqueue(new Callback<Integer>()
        {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                userTradeCoinListView.setTVBalance(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {

            }
        });
    }

    @Override
    public void getTradeCoinList(String EMail)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<List<TradeModel>> productModel = retrofitinterface.getTradeList(EMail);
        productModel.enqueue(new Callback<List<TradeModel>>()
        {
            @Override
            public void onResponse(Call<List<TradeModel>> call, Response<List<TradeModel>> response)
            {
                ArrayList<TradeModel> items = new ArrayList<TradeModel>();
                items.addAll(response.body());
                userTradeCoinListView.setLVTradeCoinList(items);
            }

            @Override
            public void onFailure(Call<List<TradeModel>> call, Throwable t)
            {

            }
        });
    }
}
