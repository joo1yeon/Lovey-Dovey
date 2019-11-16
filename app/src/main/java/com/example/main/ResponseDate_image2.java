package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseDate_image2 {
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("image2")
    private String image2;
    @SerializedName("place_id")
    private String place_id;

    public ResponseDate_image2(String _name, String _image, String _image2, String _place_id) {
        name = _name;
        image = _image;
        image2 = _image2;
        place_id = _place_id;
    }

    public String getname() {
        return name;
    }

    public String getimage() {
        return image;
    }

    public String getimage2() {
        return image2;
    }

    public String getPlace_id() {
        return place_id;
    }

}
