package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseNotice {
    @SerializedName("title")
    private String title;

    public ResponseNotice(String _title){title=_title;}
    public String getTitle(){return title;}
}
