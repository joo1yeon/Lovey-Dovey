package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseDate_image2 {
    @SerializedName("name")
    private String name;
    @SerializedName("time")
    private String time;
    @SerializedName("tel")
    private String tel;
    @SerializedName("content")
    private String content;
    @SerializedName("place")
    private String place;
    @SerializedName("image")
    private String date_image;

    public ResponseDate_image2(String _name, String _time, String _tel, String _content, String _place, String _date_image) {
        name = _name;
        time=_time;
        tel=_tel;
        content=_content;
        place=_place;
        date_image = _date_image;
    }

    public String getname() {
        return name;
    }
    public String gettime() {
        return time;
    }
    public String gettel() {
        return tel;
    }
    public String getcontent() {
        return content;
    }
    public String getplace() {
        return place;
    }
    public String getDate_image() {
        return date_image;
    }
}
