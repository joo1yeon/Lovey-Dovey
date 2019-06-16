package com.example.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//TODO 장소검색 어댑터
public class ContentAdapter extends RecyclerView.Adapter<ContentHolder> {
    ArrayList<ContentList> tList = new ArrayList<>();

    public ContentAdapter(ArrayList<ContentList> _tList, Context _context) {
        tList = _tList;
    }

    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_content, viewGroup, false);
        ContentHolder contentHolder = new ContentHolder(view);
        return contentHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        String location = tList.get(position).location;
        ((TextView) holder.tvView).setText(location);
    }

    @Override
    public int getItemCount() {
        return tList.size();
    }
}
