package com.example.main;

//TODO 장소검색 ArrayList 객체
public class FootContentList {
    String location;
    int index;

    public FootContentList(int _index, String _location) {
        index = _index;
        location = _location;
    }

    public String getLocation() {
        return location;
    }
    public int getIndex(){
        return index;
    }
}
