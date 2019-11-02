package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseInfoUpdate {
    @SerializedName("success")
    private boolean success;

    public ResponseInfoUpdate(boolean _success){success=_success;}
    public boolean getUpdate(){return success;}
}
