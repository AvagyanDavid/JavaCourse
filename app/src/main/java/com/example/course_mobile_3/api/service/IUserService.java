package com.example.course_mobile_3.api.service;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUserService {

    @POST("/auth/login")
    Call<Map<String,Object>> login(@Body RequestBody body);

    @POST("/auth/registration")
    Call<Map<String,String>> reg(@Body RequestBody body);

    @POST("/api/sessions/getOtherSession")
    Call<Map<String, Object>> getOtherSession(@Body RequestBody body);

    @GET("/api/friend/getAllFriends" )
    Call<List<Map<String,Object>>> getAllFriends(@Query("id") Long id);

}
