package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseStory {
    @SerializedName("story_id")
    private String storyID;
    @SerializedName("writer")
    private String writer;
    @SerializedName("year")
    private int year;
    @SerializedName("month")
    private int month;
    @SerializedName("day")
    private int day;
    @SerializedName("title")
    private String title;
    @SerializedName("main_img")
    private String imgPath;
    @SerializedName("contents")
    private String contents;

    public ResponseStory(String _storyID, String _writer, int _year, int _month, int _day, String _title, String _imgPath, String _contents) {
        storyID = _storyID;
        writer = _writer;
        year = _year;
        month = _month;
        day = _day;
        title = _title;
        imgPath = _imgPath;
        contents = _contents;
    }

    public String getStoryID() {
        return storyID;
    }

    public String getWriter() {
        return writer;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getContents() {
        return contents;
    }
}
