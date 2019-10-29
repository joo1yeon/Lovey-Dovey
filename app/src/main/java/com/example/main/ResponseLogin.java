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
    @SerializedName("coupleID")
    private int coupleID;


    public ResponseLogin(boolean _login, String _nickname, String _email,boolean _oppo,int _coupleID) {
        login = _login;
        nickname = _nickname;
        email = _email;
        oppo=_oppo;
        coupleID=_coupleID;
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
    public int getCoupleID(){return coupleID;}
}
