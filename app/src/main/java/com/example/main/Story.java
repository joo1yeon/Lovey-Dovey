package com.example.main;

import java.util.Date;
import java.util.UUID;

public class Story {
    private UUID mId;
    private String mTitle;
    private Date mDate;

    public Story() {
        mId = UUID.randomUUID();
        mDate = new Date();
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
    public Date getDate() {
        return mDate;
    }
    public void setDate (Date date) {
        mDate = date;
    }
}
