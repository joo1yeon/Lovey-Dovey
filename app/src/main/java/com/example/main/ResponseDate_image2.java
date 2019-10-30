package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseDate_image2 {
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("place_id")
    private String place_id;
  /*  @SerializedName("time")
    private String time;
    @SerializedName("tel")
    private String tel;
    @SerializedName("content")
    private String content;
    @SerializedName("content2")
    private String content2;
    @SerializedName("place")
    private String place;*/


    public ResponseDate_image2(String _name, String _image,String _place_id) {
        name = _name;
        image = _image;
        place_id=_place_id;
       /* time=_time;
        tel=_tel;
        content=_content;
        content2=_content2;
        place=_place;*/
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
    /*public String gettime() {
        return time;
    }
    public String gettel() {
        return tel;
    }
    public String getcontent() {
        return content;
    }
    public String getcontent2() {
        return content2;
    }
    public String getplace() {
        return place;
    }
    public String getimage2() {
        return image2;
    }*/
}
