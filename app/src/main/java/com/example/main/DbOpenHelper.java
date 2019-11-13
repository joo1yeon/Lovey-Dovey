//package com.example.main;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DbOpenHelper {
//    private static final String DATABASE_NAME = "StoryDatabase(SQLite).db";
//    private static final int DATABASE_VERSION = 1;
//    public static SQLiteDatabase mDB;
//    private DatabaseHelper mDatabaseHelper;
//    private Context mContext;
//
//    private class DatabaseHelper extends SQLiteOpenHelper {
//        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//            super(context, name, factory, version);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase sqLiteDatabase) {
//            sqLiteDatabase.execSQL(StoryDB.CreateStoryDB._CREATE0);
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StoryDB.CreateStoryDB._TABLENAME0);
//            onCreate(sqLiteDatabase);
//        }
//    }
//
//    public DbOpenHelper(Context context) {
//        this.mContext = context;
//    }
//
//    public DbOpenHelper open() throws SQLException {
//        mDatabaseHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
//        mDB = mDatabaseHelper.getWritableDatabase();
//        return this;
//    }
//
//    public void create() {
//        mDatabaseHelper.onCreate(mDB);
//    }
//
//    public void close(){
//        mDB.close();
//    }
//
//    public long insertColumn(String title, int year, int month, int day) {
//        ContentValues values = new ContentValues();
//        values.put(StoryDB.CreateStoryDB.TITLE, title);
//        values.put(StoryDB.CreateStoryDB.YEAR, year);
//        values.put(StoryDB.CreateStoryDB.MONTH, month);
//        values.put(StoryDB.CreateStoryDB.DAY, day);
//        return mDB.insert(StoryDB.CreateStoryDB._TABLENAME0, null, values);
//    }
//
//    public Cursor selectColumns() {
//        return mDB.query(StoryDB.CreateStoryDB._TABLENAME0, null, null, null, null, null, null);
//    }
//
//    public boolean updateColumn(long id, String title, int year, int month, int day) {
//        ContentValues values = new ContentValues();
//        values.put(StoryDB.CreateStoryDB.TITLE, title);
//        values.put(StoryDB.CreateStoryDB.YEAR, year);
//        values.put(StoryDB.CreateStoryDB.MONTH, month);
//        values.put(StoryDB.CreateStoryDB.DAY, day);
//        return mDB.update(StoryDB.CreateStoryDB._TABLENAME0, values, "_id=" + id, null) > 0;
//    }
//
//    //전체 지우기
//    public void deleteAllColumns() {
//        mDB.delete(StoryDB.CreateStoryDB._TABLENAME0, null, null);
//    }
//
//    //열 지우기
//    public boolean deleteColumn(long id) {
//        return mDB.delete(StoryDB.CreateStoryDB._TABLENAME0, "_id=" + id, null) > 0;
//    }
//}
