package com.example.main;

import android.app.Activity;
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
    public static String oppoId;

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
                Call<ResponseID> res = Net.getInstance().getApi().getID(edtID.getText().toString());
                res.enqueue(new Callback<ResponseID>() {
                    @Override
                    public void onResponse(Call<ResponseID> call, Response<ResponseID> response) {
                        if (response.body().getID()) {
                            Call<UpdateOppoID> r = Net.getInstance().getApi().getUpdate(id, edtID.getText().toString());
                            r.enqueue(new Callback<UpdateOppoID>() {
                                @Override
                                public void onResponse(Call<UpdateOppoID> call, Response<UpdateOppoID> response) {
                                    if (response.body().getUpdate()) {

                                        Call<responseConnect> res=Net.getInstance().getApi().getConn(id,edtID.getText().toString());
                                        res.enqueue(new Callback<responseConnect>() {
                                            @Override
                                            public void onResponse(Call<responseConnect> call, Response<responseConnect> response) {
                                                if(response.body().getConnect()){
                                                    Intent intent1 = new Intent(CoupleConnect.this, MainActivity.class);
                                                    oppoId=edtID.getText().toString();
                                                    intent1.putExtra("ID", id);
                                                    intent1.putExtra("NICK", nickname);
                                                    intent1.putExtra("EMAIL", email);
                                                    intent1.putExtra("COUPLE",response.body().getCouple());
                                                    startActivity(intent1);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<responseConnect> call, Throwable t) {

                                            }
                                        });
                                    }else{
                                        Toast.makeText(CoupleConnect.this,"정보가 입력되지 않았습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<UpdateOppoID> call, Throwable t) {

                                }
                            });
                        }else{
                            Toast.makeText(CoupleConnect.this,"해당되는 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseID> call, Throwable t) {

                    }
                });
            }
        });
    }
}
