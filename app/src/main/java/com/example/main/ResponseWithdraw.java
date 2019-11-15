package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseWithdraw {
    @SerializedName("success")
    public boolean success;

    public ResponseWithdraw(boolean _success) {
        success = _success;
    }

    public boolean getSuccess() {
        return success;
    }
}
