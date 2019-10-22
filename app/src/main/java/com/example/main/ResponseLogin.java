package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("Login")
    private boolean login;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("email")
    private String email;

    public ResponseLogin(boolean _login, String _nickname,String _email) {
        login = _login;
        nickname = _nickname;
        email=_email;
    }

    public boolean getLogin() {
        return login;
    }

    public String getNickname(){
        return nickname;
    }
    public String getEmail(){
        return email;
    }
}
