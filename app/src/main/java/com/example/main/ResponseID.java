package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseID {
    @SerializedName("success")
    private boolean success;

    public ResponseID(boolean _success){
        success=_success;
    }

    public boolean getID(){return success;}
}
