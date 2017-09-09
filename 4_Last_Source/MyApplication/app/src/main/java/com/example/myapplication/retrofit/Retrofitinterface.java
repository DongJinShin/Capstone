package com.example.myapplication.retrofit;

import com.example.myapplication.model.NoticeModel;
import com.example.myapplication.model.TradeModel;
import com.example.myapplication.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Retrofitinterface
{
    public static String API_URL = "Web Server Address"; // The security issue can not write

    @FormUrlEncoded
    @POST("ConfirmDuplicatedID.php")
    Call<Void> confirmDuplicatedID(@Field("ID") String ID);

    @FormUrlEncoded
    @POST("SignUp.php")
    Call<UserModel> signUp(@Field("ID") String ID, @Field("EMail") String EMail, @Field("password") String password, @Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("LogIn.php")
    Call<UserModel> logIn(@Field("ID") String ID, @Field("password") String password, @Field("userType") int userType);

    @FormUrlEncoded
    @POST("User.php")
    Call<UserModel> selectOneUserByID(@Field("ID") String ID);

    @FormUrlEncoded
    @POST("UserCoin.php")
    Call<Integer> selectOneUserCoinByID(@Field("ID") String ID);

    @FormUrlEncoded
    @POST("SaveCoin.php")
    Call<String> saveCoin(@Field("managerID") String managerID, @Field("userID") String userID, @Field("amount") int amount);

    @FormUrlEncoded
    @POST("UseCoin.php")
    Call<String> useCoin(@Field("managerID") String managerID, @Field("userID") String userID, @Field("amount") int amount);

    @FormUrlEncoded
    @POST("GiftCoin.php")
    Call<String> giftCoin(@Field("FromID") String FromID, @Field("DSTID") String DSTID, @Field("amount") int amount);

    @FormUrlEncoded
    @POST("TradeList.php")
    Call<List<TradeModel>> getTradeList(@Field("ID") String ID);

    @GET("NoticeList.php")
    Call<List<NoticeModel>> getNoticeList();

    @FormUrlEncoded
    @POST("DutchPay2.php")
    Call<Void> dutchPay2(@Field("managerID") String managerID, @Field("ID1") String ID1, @Field("ID2") String ID2, @Field("amount1") int amount1, @Field("amount2") int amount2);

    @FormUrlEncoded
    @POST("DutchPay3.php")
    Call<Void> dutchPay3(@Field("managerID") String managerID, @Field("ID1") String ID1, @Field("ID2") String ID2, @Field("ID3") String ID3, @Field("amount1") int amount1, @Field("amount2") int amount2, @Field("amount3") int amount3);

    @FormUrlEncoded
    @POST("DutchPay4.php")
    Call<Void> dutchPay4(@Field("managerID") String managerID, @Field("ID1") String ID1, @Field("ID2") String ID2, @Field("ID3") String ID3, @Field("ID4") String ID4, @Field("amount1") int amount1, @Field("amount2") int amount2, @Field("amount3") int amount3, @Field("amount4") int amount4);

    @FormUrlEncoded
    @POST("DutchPay5.php")
    Call<Void> dutchPay5(@Field("managerID") String managerID, @Field("ID1") String ID1, @Field("ID2") String ID2, @Field("ID3") String ID3, @Field("ID4") String ID4, @Field("ID5") String ID5, @Field("amount1") int amount1, @Field("amount2") int amount2, @Field("amount3") int amount3, @Field("amount4") int amount4, @Field("amount5") int amount5);

    @FormUrlEncoded
    @POST("DutchPay6.php")
    Call<Void> dutchPay6(@Field("managerID") String managerID, @Field("ID1") String ID1, @Field("ID2") String ID2, @Field("ID3") String ID3, @Field("ID4") String ID4, @Field("ID5") String ID5, @Field("ID6") String ID6, @Field("amount1") int amount1, @Field("amount2") int amount2, @Field("amount3") int amount3, @Field("amount4") int amount4, @Field("amount5") int amount5, @Field("amount6") int amount6);

    @FormUrlEncoded
    @POST("FCMRegister.php")
    Call<String> FCMRegister(@Field("registrationID") String registrationID, @Field("deviceID") String deviceID);

    @FormUrlEncoded
    @POST("FCMRegisterID.php")
    Call<String> FCMRegisterID(@Field("ID") String ID, @Field("deviceID") String deviceID);
}
