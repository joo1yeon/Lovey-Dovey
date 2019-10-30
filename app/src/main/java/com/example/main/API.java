package com.example.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    final String Base_URL = "http://mjckjs.gabia.io/whispering/php";

    @GET("FindID.php")
    Call<ResponseGet> getFirst(@Query("NAME") String name, @Query("EMAIL") String email);

    @GET("FindPW.php")
    Call<ResponsePW> getSecond(@Query("NAME") String name, @Query("EMAIL") String email, @Query("ID") String id);

    @GET("Login.php")
    Call<ResponseLogin>getThird(@Query("ID") String id, @Query("PW") String pw);

    @GET("RepeatCheck.php")
    Call<ResponseRepeat> getRepeat(@Query("ID") String id);

    @GET("printMarker.php")
    Call<List<ResponseMarker>> getMarker(@Query("coupleID") String id, @Query("year") int year, @Query("month") int month, @Query("day")int day);

    @GET("Join.php")
    Call<ResponseJoin> getJoin(@Query ("NAME") String name, @Query("ID") String id, @Query("PW") String pw,@Query("NICK") String nick, @Query("DATE") String date, @Query("GENDER") String gender,@Query("NUM") int num,@Query("EMAIL") String email);

    @GET("addMarker.php")
    Call<ResAddMarker> getAdd(@Query("NAME") String name, @Query("ADDRESS") String address, @Query("LAT") double latitude, @Query("LNG") double longitude, @Query("YEAR") int year, @Query("MONTH") int month, @Query("DATE") int date);

    @GET("resetTable.php")
    Call<ResReset> getReset();

    /*@GET("todo_select.php")
    Call<ResponseTODO> getInquiry(@Query("todo_id") int id, @Query("coupleID") String c_id, @Query("Date") String date, @Query("Content") String content, @Query("Checked") String checked );*/
}

