package com.example.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CoupleConnect extends Activity {
    String id,pw,email,nickname;
    int num;
    TextView myNum;
    Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.couple_connect);
        Intent intent=getIntent();
        id=intent.getStringExtra("ID");
        pw=intent.getStringExtra("PW");
        email=intent.getStringExtra("EMAIL");
        nickname=intent.getStringExtra("NICK");
        num=intent.getIntExtra("NUM",-1);
        myNum=findViewById(R.id.myNum);
        myNum.setText("내 코드는? "+num);
        btnStart=findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CoupleConnect.this,MainActivity.class);
                intent1.putExtra("ID",id);
                intent1.putExtra("NICK",nickname);
                intent1.putExtra("EMAIL",email);
                startActivity(intent1);
                finish();
            }
        });
    }
}
