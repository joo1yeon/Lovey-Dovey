package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResAddMarker {
    @SerializedName("success")
    private boolean success;

    public ResAddMarker(String _success) {
        success = Boolean.valueOf(_success);
    }

    public boolean getSuccess() {
        return success;
    }
}
