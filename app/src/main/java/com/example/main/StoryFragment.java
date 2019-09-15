package com.example.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StoryFragment extends Fragment { //전체 스토리 정보를 보여줌, 컨트롤러 객체
    public Story mStory; //인스턴스를 보존하기 위해 갖는 멤버변수

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStory = new Story();
    }

    @Nullable
    @Override
    //fragment 생명주기 메서드는 public
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.album, container, false);
        return v;
    }
}
