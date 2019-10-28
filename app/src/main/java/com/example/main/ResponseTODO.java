package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseTODO {
    @SerializedName("todoID")
    private int todoID;

    @SerializedName("coupleID")
    private int coupleID;

    @SerializedName("DATE")
    private String date;

    @SerializedName("CONTENT")
    private String content;

    @SerializedName("CHECKED")
    private String checked;

    public ResponseTODO(int _todoID, int _coupleID, String _date, String _content, String _checked){
        todoID = _todoID;
        coupleID = _coupleID;
        date = _date;
        content = _content;
        checked = _checked;
    }

    public int getID(){return todoID;}
    public int getCoupleID() {return coupleID;}
    public String getDate_td(){return date;}
    public String getContent_td(){return content;}
    public String getChecked(){return checked;}
}
