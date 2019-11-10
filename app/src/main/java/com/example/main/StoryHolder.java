package com.example.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
    public Story mStory;
    public ImageView mStoryMainImg;
    public TextView mStoryTitle, mStoryDate, mContentsText;
    private Context mContext;
    String story_id;

    //TODO 항목 구성을 위한 뷰들을 findViewById 해주는 역할
    public StoryHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);

        //필요한 View를 findViewById
        mStoryMainImg = itemView.findViewById(R.id.story_mainImg1);
        mStoryTitle = itemView.findViewById(R.id.story_title1);
        mStoryDate = itemView.findViewById(R.id.story_date1);
        mContentsText = itemView.findViewById(R.id.contents_text);

        itemView.setOnCreateContextMenuListener(this); //OnCreateContextMenuListener를 현재 클래스에서 구현한다고 설정
    }

    @Override
    public void onClick(View view) {
//            Intent intent = new Intent(getActivity(), Story_Contents.class);
//            startActivity(intent); //스토리 대표사진을 누르면 액티비티 시작
    }

    public void bindStory(Story story) { //제목과 날짜를 화면에 출력
        mStory = story;
        mStoryTitle.setText(mStory.getTitle());
        mStoryDate.setText(mStory.getYear() + "년 " + mStory.getMonth() + "월 " + mStory.getDay() + "일");
        mContentsText.setText(mStory.getContents_text());
        Uri uri = mStory.getMainImg();
        Log.d("test", uri.toString());
        Glide.with(mContext).load(uri).into(mStoryMainImg);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//        MenuItem Gotofootprint = contextMenu.add(Menu.NONE, 1001, 1, "발자국으로 이동");
        MenuItem Edit = contextMenu.add(Menu.NONE, 1002, 2, "수정");
        MenuItem Delete = contextMenu.add(Menu.NONE, 1003, 3, "삭제");

        Edit.setOnMenuItemClickListener(onEditMenu);
        Delete.setOnMenuItemClickListener(onEditMenu);
    }

    final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            Album_singleton album_singleton = Album_singleton.get(mContext);
            List<Story> stories = album_singleton.getStories();

            switch (menuItem.getItemId()) {
                case 1002: //수정 항목 선택시

                    story_id = stories.get(getAdapterPosition()).getId();

                    Intent intent = new Intent(mContext, Story_EditMainListItem.class);
                    intent.putExtra("story_id", stories.get(getAdapterPosition()).getId());
                    intent.putExtra("title", stories.get(getAdapterPosition()).getTitle());
                    intent.putExtra("year", stories.get(getAdapterPosition()).getYear());
                    intent.putExtra("month", stories.get(getAdapterPosition()).getMonth());
                    intent.putExtra("day", stories.get(getAdapterPosition()).getDay());
                    intent.putExtra("imgpath", stories.get(getAdapterPosition()).getMainImg().toString());
                    intent.putExtra("contents", stories.get(getAdapterPosition()).getContents_text());
                    intent.putExtra("position", getAdapterPosition());
                    mContext.startActivity(intent);

                    break;
                case 1003: //삭제 항목 선택시

                    story_id = stories.get(getAdapterPosition()).getId();
                    stories.remove(getAdapterPosition());
                    deleteStory_server(); //서버에서 story 삭제
                    StoryListFragment.mAdapter.notifyItemRemoved(getAdapterPosition());
                    StoryListFragment.mAdapter.notifyItemRangeChanged(getAdapterPosition(), stories.size());
                    //TODO DB에서 data 삭제
//                    mDbOpenHelper = new DbOpenHelper(getActivity());
//                    mDbOpenHelper.open();
//                    mDbOpenHelper.create();
////                        mDbOpenHelper.deleteColumn();
//                    Log.d("test", "db에서 삭제");
//                    mDbOpenHelper.close();
                    break;

            }
            return true;
        }
    };

    //TODO 서버에서 스토리 삭제
    void deleteStory_server() {
        Call<ResponseServer_Story> res = Net.getInstance().getApi().deleteStoryData(story_id);
        res.enqueue(new Callback<ResponseServer_Story>() {
            @Override
            public void onResponse(Call<ResponseServer_Story> call, Response<ResponseServer_Story> response) {
                if (response.isSuccessful()) {
                    ResponseServer_Story responseGet = response.body();
                    if (responseGet.deleteStoryData() ) {
                        Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else Toast.makeText(mContext,"통신1 에러",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseServer_Story> call, Throwable t) {
                Toast.makeText(mContext,"통신 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
