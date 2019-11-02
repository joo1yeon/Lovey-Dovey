package com.example.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Story_SearchResult extends AppCompatActivity {
    RecyclerView mRecyclerView;
    StoryAdapter mAdapter;
    int year, month, day;
    String storyTitle, storyWriter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_search_result);

        mRecyclerView = findViewById(R.id.story_search_result_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

        updateUI();

        Intent intent = getIntent();
        year = intent.getIntExtra("year", 0);
        month = intent.getIntExtra("month", 0);
        day = intent.getIntExtra("day", 0);
        storyTitle = intent.getStringExtra("storyTitle");
        storyWriter = intent.getStringExtra("storyWriter");

    }

    public void updateUI() { //singleton으로 생성된 스토리를 리스트에 할당
        Album_singleton album_singleton = Album_singleton.get(getApplication());
        List<Story> stories = album_singleton.getStories();

        if (mAdapter == null) {
            mAdapter = new StoryAdapter(stories);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemRangeInserted(stories.size(), stories.size() + 1);
            mAdapter.notifyDataSetChanged(); //리스트 다시 로드하기
            Log.d("test", "리스트 다시 로드하기");
        }
    }

    //TODO 스토리 검색
    public void searchStory() {

    }

}
