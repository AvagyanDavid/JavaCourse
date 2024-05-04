package com.example.course_mobile_3.api.service;


import com.example.course_mobile_3.api.body.User;

import java.util.Map;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IUserService {


    @POST("/auth/login")
    Call<Map<String,Object>> login(@Body RequestBody body);

    @POST("/auth/registration")
    Call<Map<String,String>> reg(@Body RequestBody body);



}
