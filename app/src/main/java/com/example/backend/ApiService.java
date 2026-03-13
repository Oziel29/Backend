package com.example.backend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("/")
    Call<String> checkApi();

    @POST("/register")
    Call<ResponseMessage> register(@Body User user);

    @POST("/login")
    Call<ResponseMessage> login(@Body User user);
}
