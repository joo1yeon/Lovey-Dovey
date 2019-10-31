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
    StoryListFragment.StoryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_search_result);

        mRecyclerView = findViewById(R.id.story_search_result_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
    }

    public void updateUI() { //singleton으로 생성된 스토리를 리스트에 할당
        Album_singleton album_singleton = Album_singleton.get(getApplication());
        List<Story> stories = album_singleton.getStories();

        if (mAdapter == null) {
//            mAdapter = new StoryListFragment.StoryAdapter(stories);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemRangeInserted(stories.size(), stories.size() + 1);
            mAdapter.notifyDataSetChanged(); //리스트 다시 로드하기
            Log.d("test", "리스트 다시 로드하기");
        }
    }

}
