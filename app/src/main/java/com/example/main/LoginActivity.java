//로그인 액티비티
package com.example.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    EditText edtID;
    EditText edtPW;
    Button btnLogin;
    CheckBox btnAutoLogin;
    MyDBHelper dbHelper = new MyDBHelper(this);
    SQLiteDatabase sqlDB;
    TextView btnJoin, btnFindID, btnFindPW, txtAutoLogin;
    View findIDView, findPWView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edtID = findViewById(R.id.edtID);
        edtPW = findViewById(R.id.edtPW);
        btnLogin = findViewById(R.id.btnLogin);
        btnAutoLogin = findViewById(R.id.btnAutoLogin);
        sqlDB = dbHelper.getWritableDatabase();
        btnJoin = findViewById(R.id.btnJoin);
        btnFindID = findViewById(R.id.btnFindID);
        btnFindPW = findViewById(R.id.btnFindPW);
        txtAutoLogin = findViewById(R.id.AutoLogin);
        txtAutoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnAutoLogin.isChecked()) {
                    btnAutoLogin.setChecked(false);
                } else btnAutoLogin.setChecked(true);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = edtID.getText().toString();
                final String pw = edtPW.getText().toString();
                if (!id.isEmpty() && !pw.isEmpty()) {
                    Call<ResponseLogin> res = Net.getInstance().getApi().getThird(id, pw);
                    res.enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                            if (response.isSuccessful()) {
                                ResponseLogin responseGet = response.body();
                                if (responseGet.getLogin()) {
                                    if(responseGet.getCouple()){
                                        String nickname = responseGet.getNickname();
                                        String email = responseGet.getEmail();
                                        int coupleID=responseGet.getCoupleID();
                                        if (btnAutoLogin.isChecked()) {
                                            sqlDB.execSQL("insert into info values('" + id + "','" + pw + "','"+nickname+"','"+email+"')");
                                        }

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("ID", id);
                                        intent.putExtra("NICK", nickname);
                                        intent.putExtra("EMAIL", email);
                                        intent.putExtra("C_ID",coupleID);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        String nickname = responseGet.getNickname();
                                        String email = responseGet.getEmail();
                                        Intent intent = new Intent(LoginActivity.this, CoupleConnect.class);
                                        intent.putExtra("ID", id);
                                        intent.putExtra("NICK", nickname);
                                        intent.putExtra("EMAIL", email);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else
                                    Toast.makeText(LoginActivity.this, "일치하는 아이디 또는 비밀번호가 없습니다.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(LoginActivity.this, "통신1 에러", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "통신3 에러", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else
                    Toast.makeText(LoginActivity.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnFindID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findIDView = View.inflate(LoginActivity.this, R.layout.find_id, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(LoginActivity.this);
                dlg.setTitle("아이디 찾기");
                dlg.setView(findIDView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edtName, edtEmail, edtDomain;

                        edtName = findIDView.findViewById(R.id.edtName);
                        edtEmail = findIDView.findViewById(R.id.edtEmail);
                        edtDomain = findIDView.findViewById(R.id.edtDomain);
                        final String name = edtName.getText().toString();
                        final String email = edtEmail.getText().toString();
                        final String domain = edtDomain.getText().toString();
                        final String eml = email + '@' + domain;

                        if (!name.isEmpty() && !email.isEmpty() && !domain.isEmpty()) {

                            Call<ResponseGet> res = Net.getInstance().getApi().getFirst(name, eml);
                            res.enqueue(new Callback<ResponseGet>() {
                                @Override
                                public void onResponse(Call<ResponseGet> call, Response<ResponseGet> response) {
                                    if (response.isSuccessful()) {
                                        ResponseGet responseGet = response.body();
                                        Toast.makeText(LoginActivity.this, "ID : " + responseGet.getId(), Toast.LENGTH_LONG).show();

                                    } else
                                        Toast.makeText(LoginActivity.this, "통신1 에러", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponseGet> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, "통신3 에러", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else
                            Toast.makeText(LoginActivity.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        btnFindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPWView = View.inflate(LoginActivity.this, R.layout.find_pw, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(LoginActivity.this);
                dlg.setTitle("비밀번호 찾기");
                dlg.setView(findPWView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edtName, edtEmail, edtDomain, edtId;
                        edtName = findPWView.findViewById(R.id.edtName);
                        edtEmail = findPWView.findViewById(R.id.edtEmail);
                        edtDomain = findPWView.findViewById(R.id.edtDomain);
                        edtId = findPWView.findViewById(R.id.edtID);
                        String name = edtName.getText().toString();
                        String email = edtEmail.getText().toString();
                        String domain = edtDomain.getText().toString();
                        String id = edtId.getText().toString();
                        String eml = email + '@' + domain;

                        if (!name.isEmpty() && !email.isEmpty() && !domain.isEmpty() && !id.isEmpty()) {
                            Call<ResponsePW> res = Net.getInstance().getApi().getSecond(name, eml, id);
                            res.enqueue(new Callback<ResponsePW>() {
                                @Override
                                public void onResponse(Call<ResponsePW> call, Response<ResponsePW> response) {
                                    if (response.isSuccessful()) {
                                        ResponsePW responsePW = response.body();
                                        Toast.makeText(LoginActivity.this, "PW : " + responsePW.getPw(), Toast.LENGTH_LONG).show();

                                    } else
                                        Toast.makeText(LoginActivity.this, "통신1 에러", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ResponsePW> call, Throwable t) {
                                    Toast.makeText(LoginActivity.this, "통신3 에러", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else
                            Toast.makeText(LoginActivity.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }

}
