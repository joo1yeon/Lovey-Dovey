package com.example.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends Activity {
    MyDBHelper dbHelper = new MyDBHelper(this);
    SQLiteDatabase sqlDB;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        sqlDB = dbHelper.getReadableDatabase();
        animationView=findViewById(R.id.animation);
        animationView.setAnimation("heart3.json");
        animationView.loop(true);
       animationView.playAnimation();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() { //특정 시간 후 작업 처리하는 메소드
            @Override
            public void run() {
                Cursor cursor = sqlDB.rawQuery("select * from info;",null);
                Intent intent;
                if(cursor.getCount()!=0){
                    cursor.moveToFirst();
                    final String id=cursor.getString(0);
                    final String pw=cursor.getString(1);
//                    intent = new Intent(LoadingActivity.this,MainActivity.class);
//                    intent.putExtra("ID",id);
//                    Toast.makeText(LoadingActivity.this,id+"로 로그인",Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
                    Call<ResponseLogin> res = Net.getInstance().getApi().getThird(id, pw);
                    res.enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                            if (response.isSuccessful()) {
                                ResponseLogin responseGet = response.body();
                                if (responseGet.getLogin()) {
                                    if(responseGet.getCouple()){
                                        Log.d("LOGINN","자동로그인 성공");

                                        String nickname = responseGet.getNickname();
                                        String email = responseGet.getEmail();
                                        int coupleID=responseGet.getCoupleID();
                                        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                                        intent.putExtra("ID", id);
                                        intent.putExtra("NICK", nickname);
                                        intent.putExtra("EMAIL", email);
                                        intent.putExtra("C_ID",coupleID);

                                        startActivity(intent);
                                        finish();
                                    }else{
                                        String nickname = responseGet.getNickname();
                                        String email = responseGet.getEmail();
                                        Intent intent = new Intent(LoadingActivity.this, CoupleConnect.class);
                                        intent.putExtra("ID", id);
                                        intent.putExtra("NICK", nickname);
                                        intent.putExtra("EMAIL", email);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else
                                    Toast.makeText(LoadingActivity.this, "일치하는 아이디 또는 비밀번호가 없습니다.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(LoadingActivity.this, "통신1 에러", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            Toast.makeText(LoadingActivity.this, "로그인에러", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    intent = new Intent(LoadingActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 4000);    //5초 후에 MainActivity로 넘어감


    }
}
