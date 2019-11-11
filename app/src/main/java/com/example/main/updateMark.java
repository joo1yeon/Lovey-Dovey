package com.example.main;

import com.google.gson.annotations.SerializedName;

public class updateMark {
    @SerializedName("success")
    private boolean success;

    public updateMark(boolean _success){success=_success;}
    public boolean getSuccess(){return success;}
}
