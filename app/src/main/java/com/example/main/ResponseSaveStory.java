package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseSaveStory {
    @SerializedName("Title") //php 의 변수명과 일치해야한다.
    private boolean title;

    public ResponseSaveStory(boolean _title) {
        title = _title;
    }

    public boolean setStoryData() {
        return title;
    }
}
