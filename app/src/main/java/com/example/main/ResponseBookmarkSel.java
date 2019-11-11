package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseBookmarkSel {
    @SerializedName("success")
    private boolean success;

    public ResponseBookmarkSel(boolean _success){
        success=_success;

    }
    public boolean getSuccess(){return success;}

}
