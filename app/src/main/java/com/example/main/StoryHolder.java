package com.example.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
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
    Uri mUri;
    Album_singleton album_singleton;
    List<Story> stories;
    View alert_delete;

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

    public void bindStory(Story story) { //스토리 내용을 화면에 출력
        mStory = story;
        mStoryTitle.setText(mStory.getTitle());
        mStoryDate.setText(mStory.getYear() + "년 " + mStory.getMonth() + "월 " + mStory.getDay() + "일");
        mContentsText.setText(mStory.getContents_text());
        mUri = mStory.getMainImg();
        Glide.with(mContext).load(mUri).into(mStoryMainImg);
        Log.d("test", "이미지 할당"+mUri.toString());
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

            album_singleton = Album_singleton.get(mContext);
            stories = album_singleton.getStories();

            switch (menuItem.getItemId()) {
                case 1002: //수정 항목 선택시

                    story_id = stories.get(getAdapterPosition()).getId();

                    Intent intent = new Intent(mContext, Story_EditMainListItem.class);
                    intent.putExtra("story_id", stories.get(getAdapterPosition()).getId());
                    intent.putExtra("title", stories.get(getAdapterPosition()).getTitle());
                    intent.putExtra("year", stories.get(getAdapterPosition()).getYear());
                    intent.putExtra("month", stories.get(getAdapterPosition()).getMonth());
                    intent.putExtra("day", stories.get(getAdapterPosition()).getDay());
                    intent.putExtra("img_uri", stories.get(getAdapterPosition()).getMainImg().toString());
                    intent.putExtra("contents", stories.get(getAdapterPosition()).getContents_text());
                    intent.putExtra("position", getAdapterPosition());
                    mContext.startActivity(intent);

                    break;
                case 1003: //삭제 항목 선택시

                    AlertDialog.Builder dlg = new AlertDialog.Builder(mContext);
                    alert_delete = View.inflate(mContext, R.layout.alert_dialog_delete, null);
                    dlg.setView(alert_delete);
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            story_id = stories.get(getAdapterPosition()).getId();
                            stories.remove(getAdapterPosition());
                            deleteStory_server(); //서버에서 story 삭제
                            StoryListFragment.mAdapter.notifyItemRemoved(getAdapterPosition());
                            StoryListFragment.mAdapter.notifyItemRangeChanged(getAdapterPosition(), stories.size());
                        }
                    });
                    dlg.setNegativeButton("취소", null);
                    dlg.show();

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
                } else Toast.makeText(mContext,"삭제 실패",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseServer_Story> call, Throwable t) {
                Toast.makeText(mContext,"통신 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
