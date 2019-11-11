package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseServer_Story {
    @SerializedName("Result") //php 의 변수명과 일치해야한다.
    private boolean result;


    public ResponseServer_Story(boolean _result) {
        result = _result;
    }

    public boolean setStoryData() {
        return result;
    }
    public boolean deleteStoryData() { return result; }
}
