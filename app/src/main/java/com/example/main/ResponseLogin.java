package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("Login")
    private boolean login;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("email")
    private String email;
    @SerializedName("oppo")
    private boolean oppo;


    public ResponseLogin(boolean _login, String _nickname, String _email,boolean _oppo) {
        login = _login;
        nickname = _nickname;
        email = _email;
        oppo=_oppo;
    }

    public boolean getLogin() {
        return login;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }
    public boolean getCouple(){return oppo;}
}
