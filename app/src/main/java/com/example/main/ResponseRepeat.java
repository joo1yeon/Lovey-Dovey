package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseRepeat {
    @SerializedName("newID")
    private boolean newId;

    public ResponseRepeat(boolean _newId) {
        newId = _newId;
    }

    public boolean getRepeat(){
        return newId;
    }

}
