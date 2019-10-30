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
    @SerializedName("content2")
    private String content2;
    @SerializedName("place")
    private String place;
    @SerializedName("image")
    private String image;
    @SerializedName("image2")
    private String image2;

    public ResponseDate_image2(String _name, String _time, String _tel, String _content, String _content2,String _place, String _image,String _image2) {
        name = _name;
        time=_time;
        tel=_tel;
        content=_content;
        content2=_content2;
        place=_place;
        image = _image;
        image2 = _image2;
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
    public String getcontent2() {
        return content2;
    }
    public String getplace() {
        return place;
    }
    public String getimage() {
        return image;
    }
    public String getimage2() {
        return image2;
    }
}
