package com.example.main;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public MyDBHelper(Context context){
        super(context,"whisper_DB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        db=_db;
        db.execSQL("create table location (num integer primary key autoincrement, content char(150) not null ); ");

        db.execSQL("create table info(id varchar(50) primary key, passwd varchar(50) not null,nickname varchar(50) not null, email varchar(50) not null);");
        db.execSQL("create table  to_do_list(num integer primary key, couple_id char(20) not null, date_check char(15), content char(50) not null, checked char(5) not null);");

        db.execSQL("create table marker(name varchar(70) not null, address varchar(70) not null, latitude double not null, longitude double not null,year int not null,month int not null,date int not null)");
        db.execSQL("create table story(story_id integer primary key autoincrement not null, writer varchar(20) not null, year integer, month integer, day integer, title varchar(100), main_img varchar(100), contents varchar(200))");
        db.execSQL("create table back(id varchar(50) not null, path varchar(50) not null);");


        //story 내용
        db.execSQL("INSERT INTO story VALUES(0, 'test1', 2019, 10, 30, '이것은 테스트이다.', 'path', '꽁냥꽁냥')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists whisper_DB");
        onCreate(db);
    }
    public MyDBHelper open()throws SQLException{
        db=getWritableDatabase();
        return this;
    }
}
