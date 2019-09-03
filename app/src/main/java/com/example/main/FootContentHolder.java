package com.example.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;
//TODO 장소검색 어댑터 ViewHolder
public class FootContentHolder extends RecyclerView.ViewHolder {
    ImageView imgView;
    TextView tvView;
    public FootContentHolder(@NonNull View itemView) {
        super(itemView);
        imgView=itemView.findViewById(R.id.imgView);
        tvView=itemView.findViewById(R.id.tvView);
    }
}
