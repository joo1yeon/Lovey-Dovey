package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class Notice extends AppCompatActivity {
    ImageButton btnBack;
    ListView listView;
    Overflow_Notice_Adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overflow_notice);
        listView=findViewById(R.id.noticeList);
        btnBack=findViewById(R.id.btnBack);
        adapter = new Overflow_Notice_Adapter();
        listView.setAdapter(adapter);

        for(int i=0;i<10;i++){
            adapter.addItem("공지사항제목"+i,"공지사항내용"+i);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Notice.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
