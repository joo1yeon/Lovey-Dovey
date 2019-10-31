package com.example.main;

import com.google.gson.annotations.SerializedName;

public class responseConnect {
    @SerializedName("connect")
    private boolean connect;
    @SerializedName("couple")
    private int couple;

    public responseConnect(boolean _connect, int _couple) {
        connect = _connect;
        couple = _couple;
    }

    public boolean getConnect() {
        return connect;
    }
    public int getCouple(){return couple;}
}
