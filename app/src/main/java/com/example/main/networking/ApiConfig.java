package com.example.main.networking;

import com.example.main.ResponseImgUpload;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ApiConfig {

    @Multipart
    @POST("images/upload_image.php")
    Call<ResponseImgUpload> upload(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
            );
}
