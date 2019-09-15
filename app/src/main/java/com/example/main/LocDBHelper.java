package com.example.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocDBHelper extends SQLiteOpenHelper {
    public LocDBHelper(Context context){
        super(context,"whisper_DB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table location ( content char(100) not null ); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists whisper_db");
        onCreate(db);
    }
}
