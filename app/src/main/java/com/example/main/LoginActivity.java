//로그인 액티비티
package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText edtID;
    EditText edtPW;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edtID = findViewById(R.id.edtID);
        edtPW = findViewById(R.id.edtPW);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString();
                String pw = edtPW.getText().toString();
                if (!id.isEmpty() && !pw.isEmpty()) {
                    Toast.makeText(LoginActivity.this, id + "로 로그인", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("ID",id);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(LoginActivity.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
