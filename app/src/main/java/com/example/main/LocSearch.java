package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class LocSearch extends AppCompatActivity {
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

        final ArrayList<FootContentList> locList = new ArrayList<>();
        final FootContentAdapter footContentAdapter = new FootContentAdapter(locList, getApplicationContext());
        recyclerView.setAdapter(footContentAdapter);

        //TODO 검색버튼을 누르면 ArrayList에 검색 내역 추가
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtLocation.getText().toString()!=null){
                    locList.add(new FootContentList(edtLocation.getText().toString()));
                    footContentAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
