package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseTD_click {
    @SerializedName("Result")           //php 변수명하고 일치해야함
    private boolean result;


    public ResponseTD_click(boolean _result) {
        result = _result;
    }

    public boolean getTDClick() {
        return result;
    }

}
