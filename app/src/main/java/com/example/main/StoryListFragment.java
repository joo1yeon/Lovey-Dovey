package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StoryListFragment extends Fragment { //앨범 버튼을 눌렀을 때 뜨는 화면

    public RecyclerView mStoryRecyclerView;
    public StoryAdapter mAdapter;
    public Button addBtn;
    public FloatingActionButton searchBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        mStoryRecyclerView = view.findViewById(R.id.story_recycler_view);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        addBtn = view.findViewById(R.id.add_btn);
        searchBtn = view.findViewById(R.id.search_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateStory.class);
                startActivity(intent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchStory.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(); //리스트로 돌아오기 위해 뒤로가기 버튼 눌렀을 때 StoryListFragment 재실행
        Log.d("test", "onResume 실행");
    }

    public void updateUI() { //singleton으로 생성된 스토리를 리스트에 할당
        Album_singleton album_singleton = Album_singleton.get(getActivity());
        List<Story> stories = album_singleton.getStories();

        if (mAdapter == null) {
            mAdapter = new StoryAdapter(stories);
            mStoryRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged(); //리스트 다시 로드하기
            Log.d("test", "리스트 다시 로드하기");
        }
        //mAdapter.notifyItemInserted(stories.size());
    }

    public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Story mStory;
        public ImageView mStoryMainImg;
        public TextView mStoryTitle;
        public TextView mStoryDate;

        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mStoryMainImg = (ImageView) itemView.findViewById(R.id.story_mainImg1);
            mStoryTitle = (TextView) itemView.findViewById(R.id.story_title1);
            mStoryDate = (TextView) itemView.findViewById(R.id.story_date1);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), StoryActivity.class);
            startActivity(intent); //스토리 대표사진을 누르면 액티비티 시작
        }

        public void bindStory(Story story) { //제목과 날짜를 화면에 출력
            mStory = story;
            //mStoryMainImg.setImageDrawable();
            mStoryTitle.setText(mStory.getTitle());
            mStoryDate.setText(mStory.getYear() + "년 " + mStory.getMonth() + "월 " + mStory.getDay() + "일");
        }
    }

    public class StoryAdapter extends RecyclerView.Adapter<StoryHolder> { //Album_singleton에서 Story 리스트를 가져온다.
        private List<Story> mStories;

        public StoryAdapter(List<Story> stories) {
            mStories = stories;
        }

        @NonNull
        @Override
        public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_story, parent, false);
            return new StoryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StoryHolder holder, int position) {
            Story story = mStories.get(position);
            holder.bindStory(story);
        }

        @Override
        public int getItemCount() {
            return mStories.size();
        }
    }
}
