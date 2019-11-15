package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseConn {
    @SerializedName("oppo_name")
    public String oppo_name;

    public ResponseConn(String _oppoName){
        oppo_name=_oppoName;
    }

    public String getOppoName(){return oppo_name;};
}
