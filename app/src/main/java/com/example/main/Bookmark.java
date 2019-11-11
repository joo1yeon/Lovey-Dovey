package com.example.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bookmark extends AppCompatActivity {

    ListView bookmarklist;
    Bookmark_ListViewAdapter adapter;
    ArrayList<Datecourse_ListViewItem> bookmark;
    String[] PlaceN = new String[15];
    String[] place_id = new String[15];
    int id;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);

        bookmarklist = findViewById(R.id.bookmark);
        bookmark = new ArrayList<Datecourse_ListViewItem>();
        adapter = new Bookmark_ListViewAdapter(this, bookmark);

        Call<List<ResponseBookmarkList>> res = Net.getInstance().getApi().getBookmarkList();
        res.enqueue(new Callback<List<ResponseBookmarkList>>() {
            @Override
            public void onResponse(Call<List<ResponseBookmarkList>> call, Response<List<ResponseBookmarkList>> response) {
                if (response.isSuccessful()) {
                    List<ResponseBookmarkList> responseGet = response.body();
                    int i = 0;
                    for (ResponseBookmarkList responseBookmarkList : responseGet) {
                        PlaceN[i] = responseBookmarkList.getName();
                        place_id[i] = responseBookmarkList.getId();
                        ++i;
                    }
                }
                for (int i = 0; i < PlaceN.length; i++) {
                    bookmark.add(new Datecourse_ListViewItem(PlaceN[i]));
                    bookmarklist.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseBookmarkList>> call, Throwable t) {
                Log.d("III", "fail");
            }
        });
    }

    public class Bookmark_ListViewAdapter extends BaseAdapter {
        Context context;
        ArrayList<Datecourse_ListViewItem> bookmark = new ArrayList<Datecourse_ListViewItem>();
        String[] Placeimage = new String[15];


        public Bookmark_ListViewAdapter(Context context, ArrayList<Datecourse_ListViewItem> _bookmark) {
            this.context = context;
            this.bookmark = _bookmark;
        }

        @Override
        public int getCount() {
            return bookmark.size();
        }

        @Override
        public Object getItem(int position) {
            return bookmark.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override   //position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.dateimage_listview, parent, false);
            }
            final ImageView image = convertView.findViewById(R.id.image);
            TextView placeName = convertView.findViewById(R.id.placeName);

            placeName.setText(bookmark.get(position).getTitle());

            Call<List<ResponseBookmarkList>> res = Net.getInstance().getApi().getBookmarkList();
            res.enqueue(new Callback<List<ResponseBookmarkList>>() {
                @Override
                public void onResponse(Call<List<ResponseBookmarkList>> call, Response<List<ResponseBookmarkList>> response) {
                    if (response.isSuccessful()) {
                        List<ResponseBookmarkList> responseGet = response.body();
                        int i = 0;

                        for (ResponseBookmarkList responseBookmarkList : responseGet) {
                            Placeimage[i] = responseBookmarkList.getImage();
                            ++i;
                        }
                        Glide.with(context).load(Placeimage[position]).into(image);
                        Log.d("WWW",id+"");
                    }
                }
                @Override
                public void onFailure(Call<List<ResponseBookmarkList>> call, Throwable t) {
                    Log.d("III", "fail");
                }
            });

            return convertView;
        }

    }

}