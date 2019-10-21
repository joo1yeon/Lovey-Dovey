package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseJoin {
    @SerializedName("success")
    private boolean success;

    public ResponseJoin(String _success) {
        success = Boolean.valueOf(_success);
    }

    public boolean getJoin() {
        return success;
    }
}
