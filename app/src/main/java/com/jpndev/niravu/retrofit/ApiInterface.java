package com.jpndev.niravu.retrofit;

import com.google.gson.JsonObject;

import com.jpndev.niravu.MHomeScreenRoot;
import com.jpndev.niravu.MItemData;
import com.jpndev.niravu.MUpdateData;
import com.jpndev.niravu.register.RespAccessToekn;
import com.jpndev.niravu.register.RespLogin;
import com.jpndev.niravu.register.RespRegister;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {
/*
    @Multipart
    @Headers(
            {"support-app-id: appid","support-app-key: appkey","enctype: multipart/form-data"}
            )

    @POST("question")
    Call<SuccesMessage> getUploadedStatus(@Part("data") RequestBody data);
*/
/*
    @GET("/bitcoin")
    Call<MGraphRoot> getGraphBTCUsd();*/





    @GET
    public Call<MUpdateData> getMUpdateData(@Url String url);



    @GET
    public Call<MHomeScreenRoot> getHomeScreenData(@Url String url);

    @GET
    public Call<ArrayList<MItemData>> getMItemData(@Url String url,@Header("X-Access-Token") String access_token);



   /* @GET
    public Call<MDataRoot> getData(@Url String url);*/

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST
    public Call<MUpdateData> getMUpdateData(@Url String url, @Body JsonObject task);




    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST
    public Call<RespLogin> login(@Url String url, @Body JsonObject task);



    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST
    public Call<RespRegister> register(@Url String url, @Body JsonObject task);



    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST
    public Call<RespAccessToekn> accessToken(@Url String url, @Body JsonObject task);




}