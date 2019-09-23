package com.example.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Story_Contents extends AppCompatActivity { //하루의 스토리(사진, 글)을 보여주는 액티비티
    private Story mStory;
    private TextView mTitle;
    private RecyclerView mRecyclerView;
    public StoryContentsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_activity);
        Log.d("test", "setContentView 실행");
        mTitle = findViewById(R.id.story_title);
        mRecyclerView = findViewById(R.id.story_contents_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        Log.d("test", "setLayoutManager 실행");

        updateUI();
        Log.d("test", "updateUI 실행");
    }

    public void updateUI() { //스토리 내용을 리스트에 할당
        List<ContentsOfStory> contentsOfStoryList;
        contentsOfStoryList = new ArrayList<ContentsOfStory>();
        for (int i = 0; i < 3; i++) {
            ContentsOfStory contents = new ContentsOfStory();
            contentsOfStoryList.add(contents);
        }

        if (mAdapter == null) {
            mAdapter = new StoryContentsAdapter(contentsOfStoryList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemRangeInserted(contentsOfStoryList.size(), contentsOfStoryList.size() + 1);
            mAdapter.notifyDataSetChanged(); //리스트 다시 로드하기
            Log.d("test", "스토리 내용 리스트 다시 로드하기");
        }
    }

    class StoryContentsHolder extends RecyclerView.ViewHolder {
        private ContentsOfStory mContentsOfStory;
        public ImageView contentsImg;
        public TextView contentsText;

        public StoryContentsHolder(View itemView) {
            super(itemView);

            contentsImg = itemView.findViewById(R.id.contents_img);
            contentsText = itemView.findViewById(R.id.contents_text);
        }

        public void bindStoryContents(ContentsOfStory contentsOfStory) {
            mContentsOfStory = contentsOfStory;
            contentsText.setText(contentsOfStory.text);
            Log.d("test", "set text 실행");
        }

    }

    public class StoryContentsAdapter extends RecyclerView.Adapter<StoryContentsHolder> { //
        private List<ContentsOfStory> mContentsOfStoryList;
        private Context mContext;

        public StoryContentsAdapter(List<ContentsOfStory> contentsOfStoryList) {
            mContentsOfStoryList = contentsOfStoryList;
            Log.d("test", "storycontentadapter 생성자 실행");
        }

        @NonNull
        @Override
        public StoryContentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplication());
            View view = layoutInflater.inflate(R.layout.list_item_story_contents, parent, false);
            Log.d("test", "storycontentholder oncreateviewholder 실행");
            return new StoryContentsHolder(view);
        }

        @Override
        public void onBindViewHolder(StoryContentsHolder holder, int position) {
            ContentsOfStory contentsOfStory = mContentsOfStoryList.get(position);
            holder.bindStoryContents(contentsOfStory);
            Log.d("test", "onbindviewholder 실행");
        }

        @Override
        public int getItemCount() {
            return mContentsOfStoryList.size();
        }
    }
}
