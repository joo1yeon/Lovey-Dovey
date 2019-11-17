package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseConn {
    @SerializedName("oppo_name")
    public String oppo_name;
    @SerializedName("success")
    public boolean success;

    public ResponseConn(String _oppoName, boolean _success){
        oppo_name=_oppoName;success=_success;
    }

    public String getOppoName(){return oppo_name;};
    public boolean getSuccess(){return success;}
}
