package com.example.main;

import android.graphics.drawable.Drawable;
import android.widget.RatingBar;

public class Datecourse_ListViewItem {
    public String image;
    public String title;

    public Datecourse_ListViewItem(String image, String title){
        this.image=image;
        this.title=title;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }
}
