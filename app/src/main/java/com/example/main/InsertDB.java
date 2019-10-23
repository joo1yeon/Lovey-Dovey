package com.example.main;

public interface InsertDB {
    public void insert(String name, String address, double latitude, double longitude, int year,int month,int date);
    public void delete(double latitude,double longitude, int year,int month,int date);
    public void save(int year,int month,int date);
}
