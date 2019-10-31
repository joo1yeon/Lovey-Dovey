package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseSaveStory {
    @SerializedName("Result") //php 의 변수명과 일치해야한다.
    private boolean result;


    public ResponseSaveStory(boolean _result) {
        result = _result;
    }

    public boolean setStoryData() {
        return result;
    }
}
