package com.example.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Album_singleton { //Story 객체들을 저장하는 저장소
    private static Album_singleton sAlbum_singleton;
    public List<Story> mStories; //List에 사용자가 생성한 Story 객체 저장, 데이터 저장소

    public static Album_singleton get(Context context) {
        if (sAlbum_singleton == null) {
            sAlbum_singleton = new Album_singleton(context);
        }
        return sAlbum_singleton;
    }

    //TODO 생성자
    private Album_singleton(Context context) { //생성자가 private 이므로 다른 클래스에서 호출시 get() 메서드 호출
        mStories = new ArrayList<>(); //Story 객체를 저장하는 비어있는 List 생성


//        DbOpenHelper mDbOpenHelper = new DbOpenHelper(context);
//        mDbOpenHelper.open();
//        mDbOpenHelper.create();
//
//        Cursor cursor = mDbOpenHelper.selectColumns();
//        while (cursor.moveToNext()) {
//            String tempTitle = cursor.getString(cursor.getColumnIndex("title"));
//            int tempYear = cursor.getInt(cursor.getColumnIndex("year"));
//            int tempMonth = cursor.getInt(cursor.getColumnIndex("month"));
//            int tempDay = cursor.getInt(cursor.getColumnIndex("day"));
//            Story story = new Story();
//            story.setTitle(tempTitle);
//            story.setYear(tempYear);
//            story.setMonth(tempMonth);
//            story.setDay(tempDay);
//            mStories.add(story);
//        }
//        MyDBHelper storyDB;
//        SQLiteDatabase sqlDB;
//        Cursor cursor;
//
//        storyDB = new MyDBHelper(context);
//        try {
//            sqlDB = storyDB.getWritableDatabase();
//            cursor = sqlDB.rawQuery("SELECT * FROM story;", null);
//            int count = cursor.getCount();
////            sqlDB.execSQL("INSERT INTO to_do_list VALUES (" + ++count + ",'" + id + "','','" + content + "','" + false + "');");
//        } catch (Exception e) {}

        Call<List<ResponseStory>> res = Net.getInstance().getApi().getStoryData();
        res.enqueue(new Callback<List<ResponseStory>>() {
            @Override
            public void onResponse(Call<List<ResponseStory>> call, Response<List<ResponseStory>> response) {
                if (response.isSuccessful()) {
                    List<ResponseStory> responseGet = response.body();
                    for (ResponseStory responseStory : responseGet) {

                        Story story = new Story();
                        story.setId(responseStory.getStoryID());
                        story.setWriter(responseStory.getWriter());
                        story.setYear(responseStory.getYear());
                        story.setMonth(responseStory.getMonth());
                        story.setDay(responseStory.getDay());
                        story.setTitle(responseStory.getTitle());
                        story.setMainImg(Uri.parse(responseStory.getImgPath()));
                        story.setContents_text(responseStory.getContents());
                        mStories.add(story);
                    }
                } else Log.d("test", "통신 1 에러");
            }

            @Override
            public void onFailure(Call<List<ResponseStory>> call, Throwable t) {
                Log.d("test", "통신 실패" + t.getMessage());
            }
        });
        
    }
    //TODO 새로운 story 객체를 리스트에 추가하는 메소드
    public void addStory(Story s) {
        mStories.add(0, s);
    } // 첫번째 줄에 새로 추가됨

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
