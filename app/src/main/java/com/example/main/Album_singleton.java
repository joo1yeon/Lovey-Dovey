package com.example.main;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Album_singleton { //싱글톤 만들기
    private static Album_singleton sAlbum_singleton;
    private static List<Story> mStories; //여기 왜 static???

    public static Album_singleton get(Context context) {
        if (sAlbum_singleton == null) {
            sAlbum_singleton = new Album_singleton(context);
        }
        return sAlbum_singleton;
    }

    private Album_singleton(Context context) {
        mStories = new ArrayList<>();
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

    public static List<Story> getStories() {
        return mStories;
    }

    public Story getStory(UUID id) {
        for (Story story : mStories) {
            if (story.getId().equals(id)) {
                return story;
            }
        }
        return null;
    }
}
