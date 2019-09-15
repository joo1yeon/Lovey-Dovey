package com.example.main;

import java.util.Date;
import java.util.UUID;

public class Story {
    private UUID mId;
    private String mTitle;
    private int mYear, mMonth, mDay;

    public Story() {
        mId = UUID.randomUUID();
    }
    public UUID getId(){
        return mId;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
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
}
