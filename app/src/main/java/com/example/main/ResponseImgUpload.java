package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseImgUpload {
    //변수 이름이 php의 json response 에서 일치해야함
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public ResponseImgUpload(boolean _success, String _message) {
        success = _success;
        message = _message;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

}
