package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseBookmark {
    @SerializedName("success")
    private boolean success;

    public ResponseBookmark(boolean _success){success=_success;}
    public boolean getReview(){return success;}
}
