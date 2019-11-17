package com.example.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class StoryListFragment extends Fragment { //앨범 버튼을 눌렀을 때 뜨는 화면
    String id;
    public RecyclerView mStoryRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    static public StoryAdapter mAdapter;
    public Button addBtn;
    public FloatingActionButton searchBtn;
    Album_singleton album_singleton;
    List<Story> stories;

    public StoryListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        mStoryRecyclerView = view.findViewById(R.id.story_recycler_view);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        updateUI();
        album_singleton = Album_singleton.get(getActivity());
        stories = album_singleton.getStories();

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        addBtn = view.findViewById(R.id.add_btn);
        searchBtn = view.findViewById(R.id.search_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Story_Create.class);
                startActivity(intent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Story_Search.class);
                startActivity(intent);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUI();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(
//                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light
        );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(); //리스트로 돌아오기 위해 뒤로가기 버튼 눌렀을 때 StoryListFragment 재실행
        Log.d("test", "onResume 실행");
    }

    public void updateUI() { //singleton으로 생성된 스토리를 리스트에 할당

//        stories = album_singleton.getStories();

//        mAdapter = null;
//        if (mAdapter == null) {
//            adapter가 null 일때 서버에서 스토리 가져온다.
            Call<List<ResponseStory>> res = Net.getInstance().getApi().getStoryData(String.valueOf(MainActivity.coupleID));
            res.enqueue(new Callback<List<ResponseStory>>() {
                @Override
                public void onResponse(Call<List<ResponseStory>> call, Response<List<ResponseStory>> response) {
                    if (response.isSuccessful()) {
                        List<Story> stories_tmp = new ArrayList<Story>();
                        List<ResponseStory> responseGet = response.body();
                        for (ResponseStory responseStory : responseGet) {
                            Story story = new Story();
                            story.setId(responseStory.getStoryID());
                            story.setWriter(responseStory.getWriter());
                            story.setYear(responseStory.getYear());
                            story.setMonth(responseStory.getMonth());
                            story.setDay(responseStory.getDay());
                            story.setTitle(responseStory.getTitle());
                            story.setMainImg(Uri.parse(responseStory.getImgPath()));
                            story.setContents_text(responseStory.getContents());
                            stories_tmp.add(story);
                            Log.d("test", "스토리 내용 추가");
                        }

                        stories.clear();
                        stories.addAll(stories_tmp);
                        mAdapter = new StoryAdapter(stories);
                        mStoryRecyclerView.setAdapter(mAdapter);

                        mSwipeRefreshLayout.setRefreshing(false);
                    } else Toast.makeText(getContext(), "스토리 로딩 실패", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<ResponseStory>> call, Throwable t) {
                    Log.d("test", "통신 실패" + t.getMessage());
                }
            });

        /*} else {
            mAdapter.notifyItemRangeInserted(stories.size(), stories.size() + 1);
            mAdapter.notifyDataSetChanged(); //리스트 다시 로드하기
        }*/
    }
}
