package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class StoryActivity extends FragmentActivity { //story Fragment를 담을 액티비티
    //Fragment를 Activity에 추가하기 위해 Activity의 FragmentManager를 명시적으로 호출

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = new StoryFragment();
            //Fragment 리스트에 Fragment를 추가, 삭제, 첨부, 분리, 변경하는 역할, 런타임시에 화면 구성 또는 재구성
            fm.beginTransaction() //새로운 프래그먼트 트랜잭션 인스턴스 생성
                    .add(R.id.fragment_container, fragment) //인스턴스에 프래그먼트 객체 추가
                    .commit();
        }
    }
}
