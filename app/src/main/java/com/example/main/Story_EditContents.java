package com.example.main;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Story_EditContents extends AppCompatActivity {
    final int GET_IMG_CODE = 30;
    Button addImg;
    String imagePath1;
    private RecyclerView mRecyclerView;
    public Story_EditContents.EditContentsAdapter mAdapter;
    public ArrayList<Uri> imageListUri;
    ContentsOfStory mContentsOfStory;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_edit_contents);

        addImg = findViewById(R.id.addImg_btn);

        mRecyclerView = findViewById(R.id.story_contents_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        Log.d("test", "setLayoutManager 실행");

        updateUI();

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImages();
            }
        });
    }

    public void updateUI() { //스토리 내용을 리스트에 할당
        List<ContentsOfStory> contentsOfStoryList;
        contentsOfStoryList = new ArrayList<ContentsOfStory>();
        for (int i = 0; i < 3; i++) {
            ContentsOfStory contents = new ContentsOfStory();
            contentsOfStoryList.add(contents);
        }

        if (mAdapter == null) {
            mAdapter = new Story_EditContents.EditContentsAdapter(contentsOfStoryList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemRangeInserted(contentsOfStoryList.size(), contentsOfStoryList.size() + 1);
            mAdapter.notifyDataSetChanged(); //리스트 다시 로드하기
            Log.d("test", "스토리 내용 리스트 다시 로드하기");
        }
    }

    class EditContentsHolder extends RecyclerView.ViewHolder {
        private ContentsOfStory mContentsOfStory;
        public ImageView contentsImg;
        public EditText contentsText;

        public EditContentsHolder(View itemView) {
            super(itemView);

            contentsImg = itemView.findViewById(R.id.contents_img);
            contentsText = itemView.findViewById(R.id.contents_text);
        }

        public void bindEditContents(ContentsOfStory contentsOfStory) {
            mContentsOfStory = contentsOfStory;
            contentsText.setText(contentsOfStory.text);
            Log.d("test", "set text 실행");
        }

    }

    public class EditContentsAdapter extends RecyclerView.Adapter<Story_EditContents.EditContentsHolder> { //
        private List<ContentsOfStory> mContentsOfStoryList;
        private Context mContext;

        public EditContentsAdapter(List<ContentsOfStory> contentsOfStoryList) {
            mContentsOfStoryList = contentsOfStoryList;
            Log.d("test", "storycontentadapter 생성자 실행");
        }

        @NonNull
        @Override
        public Story_EditContents.EditContentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplication());
            View view = layoutInflater.inflate(R.layout.list_item_story_contents, parent, false);
            Log.d("test", "storycontentholder oncreateviewholder 실행");
            return new Story_EditContents.EditContentsHolder(view);
        }

        @Override
        public void onBindViewHolder(Story_EditContents.EditContentsHolder holder, int position) {
            ContentsOfStory contentsOfStory = mContentsOfStoryList.get(position);
            holder.bindEditContents(contentsOfStory);
            Log.d("test", "onbindviewholder 실행");
        }

        @Override
        public int getItemCount() {
            return mContentsOfStoryList.size();
        }
    }

    public void getImages() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GET_IMG_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        imageListUri = new ArrayList<Uri>();

        if (requestCode == GET_IMG_CODE) {
            if (data == null) {

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (data.getClipData() == null) {
                        Toast.makeText(this, "다중선택이 불가능한 기기입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        ClipData clipData = data.getClipData();
                        Log.d("clipdata", String.valueOf(clipData.getItemCount()));

                        if (clipData.getItemCount() > 9) {
                            Toast.makeText(Story_EditContents.this, "사진은 9장 까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                        } else if (clipData.getItemCount() == 1) {
                            imagePath1 = getPath(clipData.getItemAt(0).getUri());
                            File f1 = new File(imagePath1);
//                            imgView.setAdjustViewBounds(true);
//                            imgView.setImageURI(Uri.fromFile(f1));
                        } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 9) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Log.d("test", String.valueOf(clipData.getItemAt(i).getUri()));
                                imageListUri.add(clipData.getItemAt(i).getUri());
                                mContentsOfStory.text = clipData.getItemAt(i).getUri().toString();
                            }
                            imagePath1 = getPath(imageListUri.get(0));
                            File f2 = new File(imagePath1);
//                            imgView.setAdjustViewBounds(true);
//                            imgView.setImageURI(Uri.fromFile(f2));

                        }
                    }
                }
            }
        }
    }

    private String getPath(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }
}
