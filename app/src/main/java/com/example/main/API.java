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
    Call<List<ResponseMarker>> getMarker(@Query("coupleID") int id, @Query("year") int year, @Query("month") int month, @Query("day") int day);

    @GET("Join.php")
    Call<ResponseJoin> getJoin(@Query("NAME") String name, @Query("ID") String id, @Query("PW") String pw, @Query("NICK") String nick, @Query("DATE") String date, @Query("GENDER") String gender, @Query("NUM") int num, @Query("EMAIL") String email);

    @GET("date_img.php")
    Call<List<ResponseDate_image>> getDate_image();

    @GET("date_img2.php")
    Call<List<ResponseDate_image2>> getDate_image2(@Query("ID") int id);
    Call<ResponseJoin> getJoin(@Query ("NAME") String name, @Query("ID") String id, @Query("PW") String pw,@Query("NICK") String nick, @Query("DATE") String date, @Query("GENDER") String gender,@Query("EMAIL") String email);
    Call<List<ResponseDate_image2>> getDate_image2();

    @GET("date_img3.php")
    Call<ResponseDate_image3> getDate_image3(@Query("Place_id")String place_id,@Query("id") int id);

    @GET("addMarker.php")
    Call<ResAddMarker> getAdd(@Query("NAME") String name, @Query("ADDRESS") String address, @Query("LAT") double latitude, @Query("LNG") double longitude, @Query("YEAR") int year, @Query("MONTH") int month, @Query("DATE") int date, @Query("COUPLE") int couple);

    @GET("resetTable.php")
    Call<ResReset> getReset();

    @GET("checkID.php")
    Call<ResponseID> getID(@Query("ID") String id);

    @GET("updateOppoID.php")
    Call<UpdateOppoID> getUpdate(@Query("ID") String id, @Query("OPPO") String oppo);

    @GET("connectCouple.php")
    Call<responseConnect> getConn(@Query("ID") String id, @Query("OPPO") String oppo);

    @GET("addReview.php")
    Call<ResponseReview> getReview(@Query("Place_id") String place_id, @Query("RATE") float rate, @Query("ID") String id, @Query("CONTENT") String content, @Query("YEAR") int year, @Query("MONTH") int month, @Query("DAY")int day);

    @GET("printReview.php")
    Call<List<ResponseGetReview>> getPrintReview(@Query("Place_id") String place_id);

    @GET("printAllReview.php")
    Call<List<ResponseAllReview>> getAllReview(@Query("tag") int tag,@Query("place_id") String place_id);

   
    @GET("profile.php")
    Call<ResponseProfile> getProfile(@Query("ID") String id);

    @GET("save_storyData.php")
    Call<ResponseSaveStory> setStoryData(@Query("STORY_ID") String story_id, @Query("WRITER") String writer, @Query("YEAR") int year, @Query("MONTH") int month, @Query("DAY") int day, @Query("STORY_TITLE") String title, @Query("IMG_PATH") String img_path, @Query("CONTENTS") String contents);

    @GET("printStoryData.php")
    Call<List<ResponseStory>> getStoryData();

    @Multipart
    @POST("upload_img.php")
    Call<ResponseImgUpload> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );

    @GET("updateProfile.php")
    Call<ResponseInfoUpdate> getInfoUpdate(@Query("ID") String id, @Query("NICK") String nickname, @Query("EMAIL") String email);

    @GET("printNotice.php")
    Call<List<ResponseNotice>> getNotice();

    @GET("noticeInfo.php")
    Call<NoticeInfo> getNoticeInfo(@Query("ID") int id);
}

