package com.example.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryHolder> { //Album_singleton에서 Story 리스트를 가져온다.
    //항목 구성 데이터
    private List<Story> mStories;
    private Context mContext;

    public StoryAdapter(List<Story> stories) {
        mStories = stories;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //레이아웃 XML 파일의 inflate를 담당
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_story, parent, false); //레이아웃 inflate
        return new StoryHolder(view); //레이아웃 초기화 후 StoryHolder 생성, StoryHolder 리턴
    }

    //TODO 각 항목을 구성하기 위해서 호출
    @Override
    public void onBindViewHolder(@NonNull StoryHolder holder, int position) {
        Story story = mStories.get(position);
        holder.bindStory(story);
    }

    //TODO 항목 개수
    @Override
    public int getItemCount() {
        return mStories.size();
    }
}
