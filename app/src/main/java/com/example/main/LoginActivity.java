//로그인 액티비티
package com.example.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText edtID;
    EditText edtPW;
    Button btnLogin;
    CheckBox btnAutoLogin;
    MyDBHelper dbHelper = new MyDBHelper(this);
    SQLiteDatabase sqlDB;
    TextView btnJoin, btnFindID, btnFindPW,txtAutoLogin;
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
        txtAutoLogin=findViewById(R.id.AutoLogin);
        txtAutoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnAutoLogin.isChecked()){
                    btnAutoLogin.setChecked(false);
                }else btnAutoLogin.setChecked(true);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString();
                String pw = edtPW.getText().toString();
                if (!id.isEmpty() && !pw.isEmpty()) {
                    if (btnAutoLogin.isChecked()) {
                        sqlDB.execSQL("insert into info values('" + id + "','" + pw + "')");
                    }

                    Toast.makeText(LoginActivity.this, id + "로 로그인", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("ID", id);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(LoginActivity.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
        btnFindID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findIDView = View.inflate(LoginActivity.this, R.layout.find_id, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(LoginActivity.this);
                dlg.setTitle("아이디 찾기");
                dlg.setView(findIDView);
                final EditText edtName, edtEmail, edtDomain;
                edtName = findIDView.findViewById(R.id.edtName);
                edtEmail = findIDView.findViewById(R.id.edtEmail);
                edtDomain = findIDView.findViewById(R.id.edtDomain);
                final String name = edtName.getText().toString();
                final String Email = edtEmail.getText().toString() ;
                final String Domain= edtDomain.getText().toString();
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!name.isEmpty()&&!Email.isEmpty()&&!Domain.isEmpty()) {
                            Toast.makeText(LoginActivity.this, "이름 : " + name + "\n이메일 주소 : " + Email + " @ " + Domain, Toast.LENGTH_SHORT).show();
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
                final EditText edtName, edtEmail, edtDomain;
                edtName = findPWView.findViewById(R.id.edtName);
                edtID=findPWView.findViewById(R.id.edtID);
                edtEmail = findPWView.findViewById(R.id.edtEmail);
                edtDomain = findPWView.findViewById(R.id.edtDomain);
                final String name = edtName.getText().toString();
                final String id = edtID.getText().toString();
                final String Email = edtEmail.getText().toString() ;
                final String Domain= edtDomain.getText().toString();
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!name.isEmpty()&&!Email.isEmpty()&&!Domain.isEmpty()&&!id.isEmpty()) {
                            Toast.makeText(LoginActivity.this, "이름 : " + name +"\n아이디 : " + id + "\n이메일 주소 : " + Email + " @ " + Domain, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(LoginActivity.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }

}
