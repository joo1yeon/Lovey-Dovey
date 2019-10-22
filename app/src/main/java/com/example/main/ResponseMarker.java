package com.example.main;

import com.google.gson.annotations.SerializedName;

public class ResponseMarker {
    @SerializedName( "name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;

    public ResponseMarker(String _name,String _address,double _lat,double _lng) {
        name=_name;
        address=_address;
        lat=_lat;
        lng=_lng;
    }

    public String getName(){return name;}
    public String getAddress(){return address;}
    public double getLat(){return lat;}
    public double getLng(){return lng;}

}
