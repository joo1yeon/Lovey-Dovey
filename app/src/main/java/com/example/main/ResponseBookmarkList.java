package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseBookmarkList {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;

    public ResponseBookmarkList(String _id,String _name,String _image){
        id=_id;
        name=_name;
        image=_image;
    }
    public String getId(){return id;}
    public String getName(){return name;}
    public String getImage(){return image;}

}
