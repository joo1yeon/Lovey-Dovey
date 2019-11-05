package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseDate {
    @SerializedName("Date")
    private String date;

    public ResponseDate(String _date) {
        date = _date;
    }

    public String getDate_m() {
        return date;
    }

}
