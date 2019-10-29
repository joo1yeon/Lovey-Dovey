package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseDate_image2 {
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String date_image;

    public ResponseDate_image2(String _name, String _date_image) {
        name=_name;
        date_image = _date_image;
    }

    public String getname() {
        return name;
    }
    public String getDate_image(){
        return date_image;
    }
}
