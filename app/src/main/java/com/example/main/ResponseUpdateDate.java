package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateDate {
    @SerializedName("update")
    public boolean update;
    public ResponseUpdateDate(boolean _update){update=_update;}
    public boolean getUpdate(){return update;}
}
