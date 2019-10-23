package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseDate_image {
    @SerializedName("date_image")
    private String date_image;

    public ResponseDate_image(String _date_image) {
        date_image=_date_image;
    }

    public String getDate_image(){
        return date_image;
    }
}
