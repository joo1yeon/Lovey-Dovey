package com.example.main;

import android.provider.BaseColumns;

public final class StoryDB {
    public static final class CreateStoryDB implements BaseColumns {
        public static final String TITLE = "title";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
        public static final String DAY = "day";
        public static final String _TABLENAME0 = "storytable";
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + _ID + " integer primary key autoincrement, "
                + TITLE + " text not null, "
                + YEAR + " integer not null, "
                + MONTH + " integer not null, "
                + DAY + " integer not null );";
    }
}
