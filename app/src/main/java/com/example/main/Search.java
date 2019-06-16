package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    EditText edtLocation;
    ImageView btnSearch;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        edtLocation=findViewById(R.id.edtSearch);
        btnSearch=findViewById(R.id.btnSearch);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<ContentList> locList = new ArrayList<>();
        final ContentAdapter contentAdapter = new ContentAdapter(locList, getApplicationContext());
        recyclerView.setAdapter(contentAdapter);

        //TODO 검색버튼을 누르면 ArrayList에 검색 내역 추가
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtLocation.getText().toString()!=null){
                    locList.add(new ContentList(edtLocation.getText().toString()));
                    contentAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
