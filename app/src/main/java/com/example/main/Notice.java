package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class Notice extends AppCompatActivity {
    ListView listView;
    Overflow_Notice_Adapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overflow_notice);
        listView=findViewById(R.id.noticeList);
        adapter = new Overflow_Notice_Adapter();
        listView.setAdapter(adapter);

        for(int i=0;i<10;i++){
            adapter.addItem("공지사항제목"+i,"공지사항내용"+i);
        }
    }
}
