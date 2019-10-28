package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseGetReview {
    @SerializedName("rate")
    private float rate;

    @SerializedName("nickname")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("year")
    private int year;

    @SerializedName("month")
    private int month;

    @SerializedName("day")
    private int day;

    public ResponseGetReview(float _rate,String _id,String _content, int _year, int _month, int _day){
        rate=_rate;
        id=_id;
        content=_content;
        year=_year;
        month=_month;
        day=_day;
    }

    public float getRate(){return rate;}
    public String getID(){return id;}
    public String getContent(){return content;}
    public int getYear(){return year;}
    public int getMonth(){return month;}
    public int getDay(){return day;}
}
