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

        db.execSQL("create table info(id varchar(50) not null, passwd varchar(50) not null,nickname varchar(50) not null, email varchar(50) not null);");
        db.execSQL("create table  to_do_list(num integer primary key, couple_id char(20) not null, date_check char(15), content char(50) not null, checked char(5) not null);");
        db.execSQL("create table marker(name varchar(70) not null, address varchar(70) not null, latitude double not null, longitude double not null,year int not null,month int not null,date int not null)");

        //TODO_LIST 내용
        db.execSQL("INSERT INTO to_do_list VALUES( 0,'couple0',' ','인형뽑기에서 원하는 인형 뽑아주기','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 1,'couple0','2019년 9월 23일','PC방가서 게임하고 놀기','true');");
        db.execSQL("INSERT INTO to_do_list VALUES( 2,'couple0',' ','조개구이 무한리필가서 털고오기','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 3,'couple0','2019년 9월 22일','종로가서 커플링사고 오기','true');");
        db.execSQL("INSERT INTO to_do_list VALUES( 4,'couple0','','시험끝나고 미친듯이 놀기','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 5,'couple0','','PC방 가서 하루종일 게임하기','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 6,'couple0','','오류같이 찾고 기뻐하기 ㅎㅎ','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 7,'couple0','','누워서 맘편히 잠자기','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 8,'couple0','','웃으면서 같이 코딩하기','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 9,'couple0','','종로가서 커플링 맞추기','false');");
        db.execSQL("INSERT INTO to_do_list VALUES( 10,'couple0','','커플 키링 만들어보기','false');");




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
