package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResReset {
    @SerializedName("reset")
    private boolean reset;

    public ResReset(String _reset) {
        reset= Boolean.valueOf(_reset);
    }

    public boolean getReset() {
        return reset;
    }

}
