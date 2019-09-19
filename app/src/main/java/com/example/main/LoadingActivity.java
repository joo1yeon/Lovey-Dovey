package com.example.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class LoadingActivity extends Activity {
    MyDBHelper dbHelper = new MyDBHelper(this);
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        sqlDB = dbHelper.getReadableDatabase();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() { //특정 시간 후 작업 처리하는 메소드
            @Override
            public void run() {
                Cursor cursor = sqlDB.rawQuery("select * from info;",null);
                Intent intent;
                if(cursor.getCount()!=0){
                    cursor.moveToFirst();
                    String id=cursor.getString(0);
                    intent = new Intent(LoadingActivity.this,MainActivity.class);
                    intent.putExtra("ID",id);
                    Toast.makeText(LoadingActivity.this,id+"로 로그인",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    intent = new Intent(LoadingActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 1700);    //1.7초 후에 MainActivity로 넘어감


    }
}
