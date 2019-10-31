package com.example.main;

import com.google.gson.annotations.SerializedName;

public class NoticeInfo {
    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("year")
    private int year;

    @SerializedName("month")
    private int month;

    @SerializedName("day")
    private int day;

    public NoticeInfo(String _title, String _content, int _year, int _month, int _day) {
        title = _title;
        content=_content;
        year=_year;
        month=_month;
        day=_day;
    }

    public String getTitle(){return  title;}
    public String getContent(){return content;}
    public int getYear(){return year;}
    public int getMonth(){return month;}
    public int getDay(){return day;}
}
