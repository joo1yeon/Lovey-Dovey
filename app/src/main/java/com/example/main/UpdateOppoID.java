package com.example.main;

import com.google.gson.annotations.SerializedName;

public class UpdateOppoID {
    @SerializedName("success")
    private boolean update;

    UpdateOppoID(boolean _update){
        update=_update;
    }

    public boolean getUpdate(){
        return update;
    }
}
