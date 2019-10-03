package com.example.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context){
        super(context,"whisper_DB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table location (num integer primary key autoincrement, content char(150) not null ); ");
        db.execSQL("create table info(id varchar(50) not null, passwd varchar(50) not null);");
        db.execSQL("create table  to_do_list(num integer primary key, couple_id char(20) not null, date_check char(15), content char(50) not null, checked char(5) not null);");

        //TODO_LIST 내용
        db.execSQL("INSERT INTO to_do_list VALUES( 0,'couple0',' ','인형뽑기에서 원하는 인형 뽑아주기','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 1,'couple0','2019년 9월 23일','PC방가서 게임하고 놀기','true');");
        db.execSQL("INSERT INTO to_do_list VALUES( 2,'couple0',' ','조개구이 무한리필가서 털고오기','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 3,'couple0','2019년 9월 22일','종로가서 커플링사고 오기','true');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists whisper_DB");
        onCreate(db);
    }
}
