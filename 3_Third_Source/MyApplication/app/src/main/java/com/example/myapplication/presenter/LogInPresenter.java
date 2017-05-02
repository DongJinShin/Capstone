package com.example.myapplication.presenter;

import com.example.myapplication.model.UserModel;
import com.example.myapplication.presenter.interfaces.LogInPresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.utils.SecurityUtil;
import com.example.myapplication.view.LogInView;
import com.example.myapplication.view.SignUpView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInPresenter implements LogInPresenterInterface
{
    LogInView logInView;

    public LogInPresenter(LogInView logInView)
    {
        this.logInView = logInView;
    }

    @Override
    public void logIn(String EMail, String password, final int userType)
    {
        String securityPassword = password;

        if(userType == 1)
        {
            securityPassword = SecurityUtil.SHA256(password);
        }

        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<UserModel> productModel = retrofitinterface.logIn(EMail, securityPassword, userType);
        productModel.enqueue(new Callback<UserModel>()
        {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response)
            {
                if(userType == 0)
                {
                    logInView.goToCaptureActivity();
                }
                else
                {
                    logInView.goToUserMainActivity(response.body().getEMail());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t)
            {
                logInView.failedLogIn();
            }
        });
    }
}
