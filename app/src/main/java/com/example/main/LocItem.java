package com.example.main;

public class LocItem {
    public String locAddress;
    public double locLatitude;
    public double locLongitude;

    public LocItem( String _locAddress,double _latitude,double _longitude) {
        locAddress = _locAddress;
        locLatitude=_latitude;
        locLongitude=_longitude;
    }

    public void setLocAddress(String _locAddress) {
        locAddress = _locAddress;
    }
    public void setLocLatitude(double _locLatitude){locLatitude=_locLatitude;}
    public void setLocLongitude(double _locLongitude){locLongitude=_locLongitude;}
    public String getLocAddress(){return locAddress;}
    public double getLocLatitude(){return locLatitude;}
    public double getLocLongitude(){return locLongitude;}
}
