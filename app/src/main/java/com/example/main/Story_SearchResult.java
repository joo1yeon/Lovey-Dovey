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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Story_SearchResult extends AppCompatActivity {
    RecyclerView mRecyclerView;
    StoryAdapter mAdapter;
    TextView textResult;
    ImageButton btnBack, btnClear;
    int year, month, day, count = 0, tag;
    String storyTitle, storyWriter;
//    Album_singleton album_singleton;
    List<Story> stories; //Story 객체를 저장하는 비어있는 List 생성

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_search_result);

        mRecyclerView = findViewById(R.id.story_search_result_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        textResult = findViewById(R.id.text_result);
        btnBack = findViewById(R.id.btn_back);
        btnClear = findViewById(R.id.btn_clear);

        storyWriter = String.valueOf(MainActivity.coupleID);

        Intent intent = getIntent();
        tag = intent.getIntExtra("tag", -1);

        switch (tag) {
            case 0: //제목만 입력한 경우
                storyTitle = intent.getStringExtra("storyTitle");
                break;
            case 1: //날짜만 입력한 경우
                year = intent.getIntExtra("year", 0);
                month = intent.getIntExtra("month", 0);
                day = intent.getIntExtra("day", 0);
                break;
            case 2: //모두 입력한 경우
                storyTitle = intent.getStringExtra("storyTitle");
                year = intent.getIntExtra("year", 0);
                month = intent.getIntExtra("month", 0);
                day = intent.getIntExtra("day", 0);
                break;

                default:
                    Toast.makeText(this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                    storyTitle = "null";
        }

        stories = new ArrayList<>();
        searchStory();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Story_Search.mActivity.finish();
            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        updateUI();
//    }

    //TODO 스토리 검색
    public void searchStory() {
        Call<List<ResponseStory>> res;
        switch (tag) {
            case 0: //제목만 입력한 경우
                res = Net.getInstance().getApi().searchStory_title(storyTitle, storyWriter);
                break;
            case 1: //날짜만 입력한 경우
                res = Net.getInstance().getApi().searchStory_date(year, month, day, storyWriter);
                break;
            case 2: //모두 입력한 경우
                res = Net.getInstance().getApi().searchStory(year, month, day, storyTitle, storyWriter);
                break;

            default:
                Toast.makeText(this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                res = Net.getInstance().getApi().searchStory(year, month, day, storyTitle, storyWriter);
        }
        res.enqueue(new Callback<List<ResponseStory>>() {
            @Override
            public void onResponse(Call<List<ResponseStory>> call, Response<List<ResponseStory>> response) {
                if (response.isSuccessful()) {
                    Log.d("test", "통신 성공");

                    List<ResponseStory> responseGet = response.body();
                    if (response.body().isEmpty()) Log.d("test", "response body null");
                    for (ResponseStory responseStory : responseGet) {
                        Log.d("test", "for문 안으로 들어옴");
                        Story story = new Story();
                        story.setId(responseStory.getStoryID());
                        story.setWriter(responseStory.getWriter());
                        story.setYear(responseStory.getYear());
                        story.setMonth(responseStory.getMonth());
                        story.setDay(responseStory.getDay());
                        story.setTitle(responseStory.getTitle());
                        story.setMainImg(Uri.parse(responseStory.getImgPath()));
                        story.setContents_text(responseStory.getContents());
                        stories.add(story);
                        count += 1;
                        Log.d("test", "스토리 리스트에 추가");
                        Log.d("test", String.valueOf(count));

                    }
                } else Log.d("test", "통신 1 에러");

//                if (mAdapter == null) {
//                    mAdapter = new StoryAdapter(stories);
//                    mRecyclerView.setAdapter(mAdapter);
//                    Log.d("test", "if문");
//                } else {
//                    mAdapter.notifyItemRangeInserted(stories.size(), stories.size() + 1);
//                    mAdapter.notifyDataSetChanged(); //리스트 다시 로드하기
//                    Log.d("test", "리스트 다시 로드하기");
//                }

                mAdapter = new StoryAdapter(stories);
                mRecyclerView.setAdapter(mAdapter);

                if (count == 0) {
                    textResult.setText("검색 결과가 없습니다.");
                } else {
                    textResult.setText("검색 결과: " + count + "개의 스토리");
                }
            }

            @Override
            public void onFailure(Call<List<ResponseStory>> call, Throwable t) {
                Toast.makeText(Story_SearchResult.this, "통신 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
