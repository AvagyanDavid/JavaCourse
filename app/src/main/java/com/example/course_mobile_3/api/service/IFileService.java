package com.example.course_mobile_3.api.service;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IFileService {

//    @GET("/api/sessions/downloadFile")
//    Call<ResponseParams> fileGet(@Query());


    @POST("/api/sessions/addFriendToSession")
    Call<Map<String,Object>> addFriendsToSession(@Body RequestBody body);

    @POST("/api/sessions/updateFile")
    Call<Map<String,Object>> updateFile(@Body RequestBody body);

    @GET("/api/sessions/downloadFile")
    Call<Map<String,String>> downloadFile(@Query("session_id") Long id);

}
