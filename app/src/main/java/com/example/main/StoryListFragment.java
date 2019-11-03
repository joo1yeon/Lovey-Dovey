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

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class StoryListFragment extends Fragment { //앨범 버튼을 눌렀을 때 뜨는 화면
    String id;
    public RecyclerView mStoryRecyclerView;
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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(); //리스트로 돌아오기 위해 뒤로가기 버튼 눌렀을 때 StoryListFragment 재실행
        Log.d("test", "onResume 실행");
    }

    public void updateUI() { //singleton으로 생성된 스토리를 리스트에 할당
        album_singleton = Album_singleton.get(getActivity());
        stories = album_singleton.getStories();

        if (mAdapter == null) {

            //adapter가 null 일때 서버에서 스토리 가져온다.
            Call<List<ResponseStory>> res = Net.getInstance().getApi().getStoryData();
            res.enqueue(new Callback<List<ResponseStory>>() {
                @Override
                public void onResponse(Call<List<ResponseStory>> call, Response<List<ResponseStory>> response) {
                    if (response.isSuccessful()) {
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
                            stories.add(story);
                            Log.d("test", "스토리 내용 추가");
                        }

                        mAdapter = new StoryAdapter(stories);
                        mStoryRecyclerView.setAdapter(mAdapter);

                    } else Log.d("test", "통신 1 에러");
                }

                @Override
                public void onFailure(Call<List<ResponseStory>> call, Throwable t) {
                    Log.d("test", "통신 실패" + t.getMessage());
                }
            });

        } else {
            mAdapter.notifyItemRangeInserted(stories.size(), stories.size() + 1);
            mAdapter.notifyDataSetChanged(); //리스트 다시 로드하기
            Log.d("test", "리스트 다시 로드하기");
        }

    }

//    public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
//        public Story mStory;
//        public ImageView mStoryMainImg;
//        public TextView mStoryTitle, mStoryDate, mContentsText;
//
//        public StoryHolder(View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
//
//            mStoryMainImg = (ImageView) itemView.findViewById(R.id.story_mainImg1);
//            mStoryTitle = (TextView) itemView.findViewById(R.id.story_title1);
//            mStoryDate = (TextView) itemView.findViewById(R.id.story_date1);
//            mContentsText = itemView.findViewById(R.id.contents_text);
//
//            itemView.setOnCreateContextMenuListener(this); //OnCreateContextMenuListener를 현재 클래스에서 구현한다고 설정
//        }

//        @Override
//        public void onClick(View view) {
////            Intent intent = new Intent(getActivity(), Story_Contents.class);
////            startActivity(intent); //스토리 대표사진을 누르면 액티비티 시작
//        }

//        public void bindStory(Story story) { //제목과 날짜를 화면에 출력
//            mStory = story;
//            //mStoryMainImg.setImageDrawable();
//            mStoryTitle.setText(mStory.getTitle());
//            mStoryDate.setText(mStory.getYear() + "년 " + mStory.getMonth() + "월 " + mStory.getDay() + "일");
//            mContentsText.setText(mStory.getContents_text());
//            Uri uri = mStory.getMainImg();
////            Log.d("test", uri.toString());
//            Glide.with(getContext()).load(uri).into(mStoryMainImg);
//        }

//        @Override
//        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//            MenuItem Gotofootprint = contextMenu.add(Menu.NONE, 1001, 1, "발자국으로 이동");
//            MenuItem Edit = contextMenu.add(Menu.NONE, 1002, 2, "수정");
//            MenuItem Delete = contextMenu.add(Menu.NONE, 1003, 3, "삭제");
//
//            Edit.setOnMenuItemClickListener(onEditMenu);
//            Delete.setOnMenuItemClickListener(onEditMenu);
//        }
//
//        final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//
//                switch (menuItem.getItemId()) {
//                    case 1002: //수정 항목 선택시
//                        Intent intent = new Intent(getActivity(), Story_EditMainListItem.class);
//                        startActivity(intent);
//
//                        break;
//                    case 1003: //삭제 항목 선택시
//                        Album_singleton album_singleton = Album_singleton.get(getActivity());
//                        List<Story> stories = album_singleton.getStories();
//                        stories.remove(getAdapterPosition());
//                        mAdapter.notifyItemRemoved(getAdapterPosition());
//                        mAdapter.notifyItemRangeChanged(getAdapterPosition(), stories.size());
//                        //TODO DB에서 data 삭제
//                        mDbOpenHelper = new DbOpenHelper(getActivity());
//                        mDbOpenHelper.open();
//                        mDbOpenHelper.create();
////                        mDbOpenHelper.deleteColumn();
//                        Log.d("test", "db에서 삭제");
//                        mDbOpenHelper.close();
//                        break;
//
//                }
//                return true;
//            }
//        };
//    }

//    public class StoryAdapter extends RecyclerView.Adapter<StoryHolder> { //Album_singleton에서 Story 리스트를 가져온다.
//        private List<Story> mStories;
//        private Context mContext;
//
//        public StoryAdapter(List<Story> stories) {
//            mStories = stories;
//        }
//
//        @NonNull
//        @Override
//        public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            View view = layoutInflater.inflate(R.layout.list_item_story, parent, false);
//            return new StoryHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull StoryHolder holder, int position) {
//            Story story = mStories.get(position);
//            holder.bindStory(story);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mStories.size();
//        }
//    }
}
