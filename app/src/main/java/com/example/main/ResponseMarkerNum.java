package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseMarkerNum {
    @SerializedName("NUM")
    public int num;
    public ResponseMarkerNum(int _num){num=_num;}
    public int getNum(){return num;}
}
