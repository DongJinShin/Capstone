package com.example.myapplication.FCM;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.myapplication.activity.SplashActivity;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIIDService";

    private String deviceID;

    public void registerServer(String deviceID)
    {
        this.deviceID = deviceID;
        onTokenRefresh();
    }

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();

        if(deviceID != null)
            sendRegistrationToServer(token, deviceID);
    }

    private void sendRegistrationToServer(String token, String deviceID) {
        // Add custom implementation, as needed.
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<String> productModel = retrofitinterface.FCMRegister(token, deviceID);
        productModel.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (response.body().equalsIgnoreCase("success"))
                {
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
            }
        });

    }
}
