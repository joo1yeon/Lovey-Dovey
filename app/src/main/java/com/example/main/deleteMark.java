package com.example.main;

import com.google.gson.annotations.SerializedName;

public class deleteMark {
    @SerializedName("success")
    private String success;

    public deleteMark(String _success) {
        success = _success;
    }

    public String getSuccess() {
        return success;
    }
}
