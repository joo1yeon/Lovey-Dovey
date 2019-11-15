package com.example.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoupleConnect extends Activity {
    String id, pw, email, nickname;
    int num;
    Button btnStart;
    EditText edtID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.couple_connect);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        pw = intent.getStringExtra("PW");
        email = intent.getStringExtra("EMAIL");
        nickname = intent.getStringExtra("NICK");
        num = intent.getIntExtra("NUM", -1);
        btnStart = findViewById(R.id.btnStart);
        edtID = findViewById(R.id.edtID);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtID.getText().toString().getBytes().length>0){
                    Call<ResponseConn> res = Net.getInstance().getApi().getConnect(edtID.getText().toString());
                    res.enqueue(new Callback<ResponseConn>() {
                        @Override
                        public void onResponse(Call<ResponseConn> call, Response<ResponseConn> response) {
                            if(response.isSuccessful()){
                                AlertDialog.Builder dlg = new AlertDialog.Builder(CoupleConnect.this);
                                dlg.setMessage(response.body().getOppoName()+"과 연결하시겠습니까?")
                                        .setNegativeButton("취소",null)
                                        .setPositiveButton("연결하기", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Call<responseConnect> res=Net.getInstance().getApi().getConn(id,edtID.getText().toString());
                                                res.enqueue(new Callback<responseConnect>() {
                                                    @Override
                                                    public void onResponse(Call<responseConnect> call, Response<responseConnect> response) {
                                                        if(response.body().getConnect()){
                                                            Intent intent1 = new Intent(CoupleConnect.this, MainActivity.class);
                                                            intent1.putExtra("ID", id);
                                                            intent1.putExtra("NICK", nickname);
                                                            intent1.putExtra("EMAIL", email);
                                                            intent1.putExtra("C_ID", response.body().getCouple());
                                                            Log.d("INNN1", "coupleID : " + response.body().getCouple());
                                                            startActivity(intent1);
                                                            finish();
                                                        }else Toast.makeText(CoupleConnect.this,"상대방이 다른 사용자와 연결되어있습니다.",Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<responseConnect> call, Throwable t) {
                                                        Toast.makeText(CoupleConnect.this,"상대방과 연결할 수 없습니다.",Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }
                                        }).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseConn> call, Throwable t) {

                        }
                    });

                }else Toast.makeText(CoupleConnect.this,"상대방의 아이디를 입력해주세요.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(CoupleConnect.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
