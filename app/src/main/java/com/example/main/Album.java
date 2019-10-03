package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Album extends Fragment {

    /*원래 자바 코드에서 전역변수 선언 부분*/
    Button addBtn;
    ImageView searchBtn;
    public Album(){}
    public Story mStory;
    public TextView storyTitle, storyDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStory = new Story();
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout =(LinearLayout) inflater.inflate(R.layout.fragment_album,container,false);
        storyTitle = layout.findViewById(R.id.story_title1);
        storyDate = layout.findViewById(R.id.story_date1);

        addBtn = layout.findViewById(R.id.add_btn);
        searchBtn = layout.findViewById(R.id.search_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Story_Create.class);
                startActivity(intent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getContext(), Story_Search.class);
                startActivity(intent2);
            }
        });


        return layout;
    }
}
