package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseTD_Insert {
    @SerializedName("Result")           //php 변수명하고 일치해야함
    private boolean result;


    public ResponseTD_Insert(boolean _result) {
        result = _result;
    }

    public boolean getTDInsert() {
        return result;
    }

}
