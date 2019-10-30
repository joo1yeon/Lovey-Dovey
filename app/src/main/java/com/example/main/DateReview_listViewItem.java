package com.example.main;

import android.graphics.drawable.Drawable;
import android.widget.RatingBar;

public class DateReview_listViewItem {
    public String context;
    public float rating;
    public String date;
    public String id;

    public void setId(String _id) {
        id = _id;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return this.context;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return this.rating;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public String getID() {
        return this.id;
    }
}
