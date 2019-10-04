package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseMarker {
    @SerializedName("lat")
    private double lat;
    private double lng;

    public ResponseMarker(double _lat,double _lng) {
        lat=_lat; lng=_lng;
    }

    public double getLat(){return lat;}
    public double getLng(){return lng;}

}
