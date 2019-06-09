package com.example.main;

import android.content.Context;

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
        for (int i = 0; i < 5; i++) {
            Story story = new Story();
            story.setTitle("제목");
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
