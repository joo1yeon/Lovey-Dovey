package com.example.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StoryContentsFragment extends Fragment { //하루의 스토리(사진, 글)을 보여주는 컨트롤러 객체
    private Story mStory;
    private TextView mTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStory = new Story();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        mTitle = v.findViewById(R.id.story_title);
        //mDate.setText(mStory.getYear());

        return v;
    }
}
