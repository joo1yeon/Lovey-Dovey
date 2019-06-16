package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DateImageClick extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateimage_click);

        ListView listView;
        Datecourse_ListViewAdapter adapter;

        adapter = new Datecourse_ListViewAdapter();

        listView = findViewById(R.id.imagelist);
        listView.setAdapter(adapter);

        Integer 

        adapter.addItem("석촌 호수", ContextCompat.getDrawable(this, R.drawable.dateimage1));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DateImageClick.this, Datecourse_Fragment.class);
                startActivity(intent);
            }
        });
    }
}
