package com.example.myapplication.retrofit;

import com.example.myapplication.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Retrofitinterface
{
    public static String API_URL = "Web Server Address"; // The security issue can not write

    @FormUrlEncoded
    @POST("SignUp.php")
    Call<UserModel> signUp(@Field("EMail") String EMail, @Field("password") String password, @Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("LogIn.php")
    Call<UserModel> logIn(@Field("EMail") String EMail, @Field("password") String password, @Field("userType") int userType);

    @FormUrlEncoded
    @POST("User.php")
    Call<UserModel> selectOneUserByEMail(@Field("EMail") String EMail);

    @FormUrlEncoded
    @POST("UserCoin.php")
    Call<Integer> selectOneUserCoinByEMail(@Field("EMail") String EMail);

    @FormUrlEncoded
    @POST("SaveCoin.php")
    Call<String> saveCoin(@Field("EMail") String EMail, @Field("amount") int amount);

    @FormUrlEncoded
    @POST("UseCoin.php")
    Call<String> useCoin(@Field("EMail") String EMail, @Field("amount") int amount);

    @FormUrlEncoded
    @POST("GiftCoin.php")
    Call<String> giftCoin(@Field("FromEMail") String FromEMail, @Field("DSTEMail") String DSTEMail, @Field("amount") int amount);
}
