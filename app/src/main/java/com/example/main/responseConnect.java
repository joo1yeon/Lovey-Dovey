package com.example.main;

import com.google.gson.annotations.SerializedName;

public class responseConnect {
    @SerializedName("connect")
    private boolean connect;

   public responseConnect(boolean _connect){connect=_connect;}

    public boolean getConnect() {
        return connect;
    }
}
