package com.example.main;


import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
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

    @GET("save_storyData.php")
    Call<ResponseSaveStory> setStoryData(@Query("storyID") String story_id, @Query("year") int year, @Query("month") int month, @Query("day") int day, @Query("storyTitle") String mTitle, @Query("contents") String contents);

    @Multipart
    @POST("upload_img.php")
    Call<ResponseImgUpload> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );
}

