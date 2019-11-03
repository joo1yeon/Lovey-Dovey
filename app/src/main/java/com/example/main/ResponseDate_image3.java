package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseDate_image3 {
    @SerializedName("place")
    private String place;
    @SerializedName("time")
    private String time;
    @SerializedName("tel")
    private String tel;
    @SerializedName("content")
    private String content;
    @SerializedName("content2")
    private String content2;



    public ResponseDate_image3(String _place,String _time, String _tel,String _content, String _content2) {
        place=_place;
        time=_time;
        tel=_tel;
        content=_content;
        content2=_content2;
    }

    public String getplace() {
        return place;
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
}
