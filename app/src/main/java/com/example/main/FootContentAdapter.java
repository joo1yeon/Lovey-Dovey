package com.example.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//TODO 장소검색 어댑터
public class FootContentAdapter extends RecyclerView.Adapter<FootContentHolder> {
    ArrayList<FootContentList> tList = new ArrayList<>();

    public FootContentAdapter(ArrayList<FootContentList> _tList, Context _context) {
        tList = _tList;
    }

    @NonNull
    @Override
    public FootContentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_content, viewGroup, false);
        FootContentHolder footContentHolder = new FootContentHolder(view);
        return footContentHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull FootContentHolder holder, int position) {
        String location = tList.get(position).location;
        ((TextView) holder.tvView).setText(location);
    }

    @Override
    public int getItemCount() {
        return tList.size();
    }
}
