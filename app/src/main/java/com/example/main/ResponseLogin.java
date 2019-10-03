package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("Login")
    private boolean login;

    public ResponseLogin(boolean _login) {
        login=_login;
    }

    public boolean getLogin(){
        return login;
    }

}
