package com.example.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ContentHolder extends RecyclerView.ViewHolder {
    ImageView imgView;
    TextView tvView;
    public ContentHolder(@NonNull View itemView) {
        super(itemView);
        imgView=itemView.findViewById(R.id.imgView);
        tvView=itemView.findViewById(R.id.tvView);
    }
}
