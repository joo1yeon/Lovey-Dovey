package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseReview {
    @SerializedName("success")
    private boolean success;

    public ResponseReview(boolean _success){success=_success;}
    public boolean getReview(){return success;}
}
