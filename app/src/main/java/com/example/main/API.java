package com.example.main;


import com.example.main.networking.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    final String Base_URL = "http://mjckjs.gabia.io/whispering/php";

    @GET("FindID.php")
    Call<ResponseGet> getFirst(@Query("NAME") String name, @Query("EMAIL") String email);

    @GET("FindPW.php")
    Call<ResponsePW> getSecond(@Query("NAME") String name, @Query("EMAIL") String email, @Query("ID") String id);

    @GET("Login.php")
    Call<ResponseLogin> getThird(@Query("ID") String id, @Query("PW") String pw);

    @GET("RepeatCheck.php")
    Call<ResponseRepeat> getRepeat(@Query("ID") String id);

    @GET("printMarker.php")
    Call<List<ResponseMarker>> getMarker(@Query("coupleID") String id, @Query("year") int year, @Query("month") int month, @Query("day") int day);

    @Multipart
    @POST("upload_img.php")
    Call<ServerResponse> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );
}

