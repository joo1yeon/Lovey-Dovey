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
    public void onCreate(@Nullable Bundle savedInstanceState) { //전체 스토리 정보를 보여줌, 컨트롤러 객체
        super.onCreate(savedInstanceState); //인스턴스를 보존하기 위해 갖는 멤버변수
        mStory = new Story();
    }

    @Nullable
    @Override
    //fragment 생명주기 메서드는 public
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Fragment View의 layout을 inflate한 후 View를 호스팅 액티비티에 반환하는 메서드
        //프래그먼트 뷰의 레이아웃을 인플레이트 한 후 View를 호스팅 Activity에 반환
        View v = inflater.inflate(R.layout.fragment_album, container, false);
        return v;
    }
}
