package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponsePW{
        @SerializedName("PW")
        private String pw;

        public ResponsePW(String _pw) {
            pw = _pw;
        }

        public String getPw() {
            return pw;
        }
}
