package com.example.main.networking;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    //변수 이름이 php의 json response 에서 일치해야함
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

}
