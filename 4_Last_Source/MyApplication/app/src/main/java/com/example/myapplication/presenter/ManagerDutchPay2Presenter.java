package com.example.myapplication.presenter;

import com.example.myapplication.presenter.interfaces.ManagerDutchPay2PresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.view.LogInView;
import com.example.myapplication.view.ManagerDutchPay2View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagerDutchPay2Presenter implements ManagerDutchPay2PresenterInterface
{
    ManagerDutchPay2View managerDutchPay2View;

    public ManagerDutchPay2Presenter(ManagerDutchPay2View managerDutchPay2View)
    {
        this.managerDutchPay2View = managerDutchPay2View;
    }

    @Override
    public void selectOneUserCoinByID(final String ID, final int mode)
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
                managerDutchPay2View.judgmentCoin(ID, response.body(), mode);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {
                managerDutchPay2View.failedCertification();
            }
        });
    }

    @Override
    public void dutchPay2(String managerID, String ID1, String ID2, int amount1, int amount2)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<Void> productModel = retrofitinterface.dutchPay2(managerID, ID1, ID2, amount1, amount2);
        productModel.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.code() == 200)
                {
                    managerDutchPay2View.successDutchPay();
                }
                else
                {
                    managerDutchPay2View.failedDutchPay();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                managerDutchPay2View.failedDutchPay();
            }
        });
    }

    @Override
    public void dutchPay3(String managerID, String ID1, String ID2, String ID3, int amount1, int amount2, int amount3)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<Void> productModel = retrofitinterface.dutchPay3(managerID, ID1, ID2, ID3, amount1, amount2, amount3);
        productModel.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.code() == 200)
                {
                    managerDutchPay2View.successDutchPay();
                }
                else
                {
                    managerDutchPay2View.failedDutchPay();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                managerDutchPay2View.failedDutchPay();
            }
        });
    }

    @Override
    public void dutchPay4(String managerID, String ID1, String ID2, String ID3, String ID4, int amount1, int amount2, int amount3, int amount4)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<Void> productModel = retrofitinterface.dutchPay4(managerID, ID1, ID2, ID3, ID4, amount1, amount2, amount3, amount4);
        productModel.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.code() == 200)
                {
                    managerDutchPay2View.successDutchPay();
                }
                else
                {
                    managerDutchPay2View.failedDutchPay();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                managerDutchPay2View.failedDutchPay();
            }
        });
    }

    @Override
    public void dutchPay5(String managerID, String ID1, String ID2, String ID3, String ID4, String ID5, int amount1, int amount2, int amount3, int amount4, int amount5)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<Void> productModel = retrofitinterface.dutchPay5(managerID, ID1, ID2, ID3, ID4, ID5, amount1, amount2, amount3, amount4, amount5);
        productModel.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.code() == 200)
                {
                    managerDutchPay2View.successDutchPay();
                }
                else
                {
                    managerDutchPay2View.failedDutchPay();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                managerDutchPay2View.failedDutchPay();
            }
        });
    }

    @Override
    public void dutchPay6(String managerID, String ID1, String ID2, String ID3, String ID4, String ID5, String ID6, int amount1, int amount2, int amount3, int amount4, int amount5, int amount6)
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<Void> productModel = retrofitinterface.dutchPay6(managerID, ID1, ID2, ID3, ID4, ID5, ID6, amount1, amount2, amount3, amount4, amount5, amount6);
        productModel.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                if (response.code() == 200)
                {
                    managerDutchPay2View.successDutchPay();
                }
                else
                {
                    managerDutchPay2View.failedDutchPay();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                managerDutchPay2View.failedDutchPay();
            }
        });
    }
}
