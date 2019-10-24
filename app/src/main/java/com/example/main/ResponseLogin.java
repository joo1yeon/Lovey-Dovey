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
    @SerializedName("num")
    private int num;


    public ResponseLogin(boolean _login, String _nickname, String _email,boolean _oppo, int _num) {
        login = _login;
        nickname = _nickname;
        email = _email;
        oppo=_oppo;
        num=_num;
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
    public int getNum(){return num;}
}
