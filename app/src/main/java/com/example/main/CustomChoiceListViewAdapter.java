package com.example.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomChoiceListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>();

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        //"list_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textTextView = (TextView) view.findViewById(R.id.tv1);
        TextView date = view.findViewById(R.id.dateTv);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItems.get(i);

        // 아이템 내 각 위젯에 데이터 반영
        textTextView.setText(listViewItem.getContent());
        date.setText(listViewItem.getDate());

        return view;

    }

    public void addItem(String content,String date){
        ListViewItem item = new ListViewItem();

        item.setContent(content);
        item.setDate(date);

        listViewItems.add(item);
    }


        //아이템을 제거
        public void remove(int position){
            listViewItems.remove(position);
        }
/*
        //아이템을 바르게 배열
        public void sort(){
            Collections.sort(listData, ListData.ALPHA_COMPARATOR);

        }
*/

}
