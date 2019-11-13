package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseProfile_m {
    @SerializedName("Result1")           //php 변수명하고 일치해야함
    private boolean result;


    public ResponseProfile_m(boolean _result) {
        result = _result;
    }

    public boolean getmProfile() {
        return result;
    }

}
