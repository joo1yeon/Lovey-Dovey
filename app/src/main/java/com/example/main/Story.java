package com.example.main;

import android.net.Uri;

import java.util.Date;
import java.util.UUID;

public class Story {
    private UUID mId;
    private String mTitle, mContents_text;
    private int mYear, mMonth, mDay;
    private Uri mMainImg;

    public Story() {
        mId = UUID.randomUUID();
    } //고유한 식별자 생성
    public UUID getId(){
        return mId;
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
