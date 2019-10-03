package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseGet {
    @SerializedName("ID")
    private String Id;

    public ResponseGet(String _id) {
        Id = _id;
    }

    public String getId() {
        return Id;
    }
}
