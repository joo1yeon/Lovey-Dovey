package com.example.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Overflow_Notice_Adapter extends BaseAdapter {

    public static Object context;
    TextView tvTitle;
    ArrayList<Overflow_Notice_Item> listItem = new ArrayList<Overflow_Notice_Item>();

    public Overflow_Notice_Adapter(){
    }
    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Context context=parent.getContext();
       if(convertView==null){
           LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView=inflater.inflate(R.layout.notice_list,parent,false);
       }
        tvTitle=convertView.findViewById(R.id.noticeTitle);
        Overflow_Notice_Item item=listItem.get(position);
        tvTitle.setText(item.getTitle());
        return convertView;
    }

    public void addItem(String title) {
        Overflow_Notice_Item item = new Overflow_Notice_Item();
        item.setTitle(title);
        listItem.add(item);
    }
}
