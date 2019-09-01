package com.example.main;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Album_singleton { //Story 객체들을 저장하는 저장소
    private static Album_singleton sAlbum_singleton;
    private List<Story> mStories; //List에 사용자가 생성한 Story 객체 저장, 데이터 저장소

    public static Album_singleton get(Context context) {
        if (sAlbum_singleton == null) {
            sAlbum_singleton = new Album_singleton(context);
        }
        return sAlbum_singleton;
    }

    //TODO 생성자
    private Album_singleton(Context context) { //생성자가 private 이므로 다른 클래스에서 호출시 get() 메서드 호출
        mStories = new ArrayList<>(); //Story 객체를 저장하는 비어있는 List 생성
        /*for (int i = 0; i < 5; i++) {
            Story story = new Story();
            story.setTitle("제목");
            mStories.add(story);
        }*/
        DbOpenHelper mDbOpenHelper = new DbOpenHelper(context);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Cursor cursor = mDbOpenHelper.selectColumns();
        while (cursor.moveToNext()) {
            String tempTitle = cursor.getString(cursor.getColumnIndex("title"));
            int tempYear = cursor.getInt(cursor.getColumnIndex("year"));
            int tempMonth = cursor.getInt(cursor.getColumnIndex("month"));
            int tempDay = cursor.getInt(cursor.getColumnIndex("day"));
            Story story = new Story();
            story.setTitle(tempTitle);
            story.setYear(tempYear);
            story.setMonth(tempMonth);
            story.setDay(tempDay);
            mStories.add(story);
        }
        
    }

    //TODO 생성된 List를 반환하는 메서드
    public List<Story> getStories() {
        return mStories;
    }

    //TODO 지정된 ID를 갖는 Story 객체를 반환하는 메서드
    public Story getStory(UUID id) {
        for (Story story : mStories) {
            if (story.getId().equals(id)) {
                return story;
            }
        }
        return null;
    }
}
