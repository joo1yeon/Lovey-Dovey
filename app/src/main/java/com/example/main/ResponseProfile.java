package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseProfile {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;


    public ResponseProfile(String _name, String _email){
        name=_name;
        email=_email;
    }

    public String getName(){return name;}
    public String getEmail(){return email;}
}
