package com.example.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Datecourse_ListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Datecourse_ListViewItem> date_listItem = new ArrayList<Datecourse_ListViewItem>();
    ViewHolder viewHolder;

    public Datecourse_ListViewAdapter(Context context, ArrayList<Datecourse_ListViewItem>date_listItem){
        this.context=context;
        this.date_listItem=date_listItem;
    }

    @Override
    public int getCount() {
        return date_listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return date_listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override   //position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dateimage_listview, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.placeName = convertView.findViewById(R.id.placeName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.placeName.setText(date_listItem.get(position).getTitle());
        Glide.with(context).load(date_listItem.get(position).getImage()).into(viewHolder.image);
        return convertView;
    }
    class ViewHolder {
        ImageView image;
        TextView placeName;
    }
}
