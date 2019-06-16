package com.example.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Datecourse_ListViewAdapter extends BaseAdapter {
    ArrayList<Datecourse_ListViewItem> date_listItem = new ArrayList<Datecourse_ListViewItem>();

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
        }

        ImageView image = convertView.findViewById(R.id.image);
        TextView placeName = convertView.findViewById(R.id.placeName);

        Datecourse_ListViewItem listItem = date_listItem.get(position);

        image.setImageDrawable(listItem.getImage());
        placeName.setText(listItem.getTitle());

        return convertView;
    }

    public void addItem(String title,Drawable image) {
        Datecourse_ListViewItem item = new Datecourse_ListViewItem();

        item.setImage(image);
        item.setTitle(title);

        date_listItem.add(item);
    }
}
