package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseDate_image2 {
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("place_id")
    private String place_id;

    public ResponseDate_image2(String _name, String _image,String _place_id) {
        name = _name;
        image = _image;
        place_id=_place_id;
    }

    public String getname() {
        return name;
    }
    public String getimage() {
        return image;
    }
    public String getPlace_id() {
        return place_id;
    }

}
