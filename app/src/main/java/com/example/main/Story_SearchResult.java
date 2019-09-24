package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Story_SearchResult extends AppCompatActivity {
    RecyclerView mRecyclerView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_search_result);

        mRecyclerView = findViewById(R.id.story_search_result_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
    }

}
