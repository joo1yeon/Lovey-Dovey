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

import com.bumptech.glide.Glide;

import java.util.List;

public class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
    public Story mStory;
    public ImageView mStoryMainImg;
    public TextView mStoryTitle, mStoryDate, mContentsText;
    private Context mContext;

    //TODO 항목 구성을 위한 뷰들을 findViewById 해주는 역할
    public StoryHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);

        //필요한 View를 findViewById
        mStoryMainImg = (ImageView) itemView.findViewById(R.id.story_mainImg1);
        mStoryTitle = (TextView) itemView.findViewById(R.id.story_title1);
        mStoryDate = (TextView) itemView.findViewById(R.id.story_date1);
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
        //mStoryMainImg.setImageDrawable();
        mStoryTitle.setText(mStory.getTitle());
        mStoryDate.setText(mStory.getYear() + "년 " + mStory.getMonth() + "월 " + mStory.getDay() + "일");
        mContentsText.setText(mStory.getContents_text());
        Uri uri = mStory.getMainImg();
//            Log.d("test", uri.toString());
//        Glide.with(getContext()).load(uri).into(mStoryMainImg);
        Glide.with(mContext).load(uri).into(mStoryMainImg);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        MenuItem Gotofootprint = contextMenu.add(Menu.NONE, 1001, 1, "발자국으로 이동");
        MenuItem Edit = contextMenu.add(Menu.NONE, 1002, 2, "수정");
        MenuItem Delete = contextMenu.add(Menu.NONE, 1003, 3, "삭제");

        Edit.setOnMenuItemClickListener(onEditMenu);
        Delete.setOnMenuItemClickListener(onEditMenu);
    }

    final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case 1002: //수정 항목 선택시
//                    Intent intent = new Intent(getActivity(), Story_EditMainListItem.class);
                    Intent intent = new Intent(mContext, Story_EditMainListItem.class);
//                    startActivity(intent);

                    break;
                case 1003: //삭제 항목 선택시
//                    Album_singleton album_singleton = Album_singleton.get(getActivity());
                    Album_singleton album_singleton = Album_singleton.get(mContext);
                    List<Story> stories = album_singleton.getStories();
                    stories.remove(getAdapterPosition());
//                    mAdapter.notifyItemRemoved(getAdapterPosition());
//                    mAdapter.notifyItemRangeChanged(getAdapterPosition(), stories.size());
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
}
