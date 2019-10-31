package com.example.main;

import android.net.Uri;

import java.util.Date;
import java.util.UUID;

public class Story {
    private String mId, mWriter;
    private String mTitle, mContents_text;
    private int mYear, mMonth, mDay;
    private Uri mMainImg;

    public Story() {
        mId = UUID.randomUUID().toString();
    } //고유한 식별자 생성
    public String getId(){
        return mId;
    }
    public void setId(String storyid) {
        mId = storyid;
    }
    public String  getWriter(){
        return mWriter;
    }

    public void setWriter(String writer) {
        mWriter = writer;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
    }
    public String getContents_text() {
        return mContents_text;
    }
    public void setContents_text(String contents_text) {
        mContents_text = contents_text;
    }
    public int getYear() {
        return mYear;
    }
    public void setYear (int year) {
        mYear = year;
    }
    public int getMonth() {
        return mMonth;
    }
    public void setMonth(int month) {
        mMonth = month;
    }
    public int getDay() {
        return mDay;
    }
    public void setDay(int day) {
        mDay = day;
    }
    public Uri getMainImg() {
        return mMainImg;
    }
    public void setMainImg(Uri mainImg) {
        mMainImg = mainImg;
    }
}

class ContentsOfStory {
    public String text;
    public ContentsOfStory() { text = "test";}
}
