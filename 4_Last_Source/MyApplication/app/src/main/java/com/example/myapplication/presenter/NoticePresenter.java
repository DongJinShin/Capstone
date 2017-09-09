package com.example.myapplication.presenter;

import com.example.myapplication.model.NoticeModel;
import com.example.myapplication.model.TradeModel;
import com.example.myapplication.presenter.interfaces.NoticePresenterInterface;
import com.example.myapplication.retrofit.Retrofitinterface;
import com.example.myapplication.view.LogInView;
import com.example.myapplication.view.NoticeView;
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

public class NoticePresenter implements NoticePresenterInterface
{
    NoticeView noticeView;

    public NoticePresenter(NoticeView noticeView)
    {
        this.noticeView = noticeView;
    }

    @Override
    public void getNoticeList()
    {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Retrofitinterface.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Retrofitinterface retrofitinterface = retrofit.create(Retrofitinterface.class);

        Call<List<NoticeModel>> noticeModelList = retrofitinterface.getNoticeList();
        noticeModelList.enqueue(new Callback<List<NoticeModel>>()
        {
            @Override
            public void onResponse(Call<List<NoticeModel>> call, Response<List<NoticeModel>> response)
            {
                if (response.code() == 200)
                {
                    ArrayList<NoticeModel> items = new ArrayList<NoticeModel>();
                    items.addAll(response.body());
                    noticeView.setLVNoticeList(items);
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<List<NoticeModel>> call, Throwable t)
            {

            }
        });
    }
}
