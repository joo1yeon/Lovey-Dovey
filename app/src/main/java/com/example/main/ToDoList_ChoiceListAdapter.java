package com.example.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoList_ChoiceListAdapter extends BaseAdapter {
    final ArrayList<ToDoList_ListItem> listViewItems = new ArrayList<ToDoList_ListItem>();

    //각 아이템에 들어 갈 데이터들의 전체 개수를 리턴
    @Override
    public int getCount() {
        return listViewItems.size();
    }

    //아이템의 위치에 해당하는 데이터의 객체를 반환
    @Override
    public Object getItem(int i) {
        return listViewItems.get(i);
    }

    //아이템의 위치에 해당하는 id를 반환
    @Override
    public long getItemId(int i) {
        return i;
    }

    //어댑터가 가진 데이터를 보여줄 때
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        //"list_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false); //xml 파일을 View 객체로 만듬
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textTextView = (TextView) view.findViewById(R.id.tv1);
        TextView date = view.findViewById(R.id.dateTv);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ToDoList_ListItem listViewItem = listViewItems.get(i);

        // 아이템 내 각 위젯에 데이터 반영
        textTextView.setText(listViewItem.getContent());
        date.setText(listViewItem.getDate());

        return view;    //view 반환

    }

    //아이템 추가
    public void addItem(String content,String date){
        ToDoList_ListItem item = new ToDoList_ListItem();        //ListViewItem 객체 생성

        item.setContent(content);                               //내용할당
        item.setDate(date);                                     //시간할당

        listViewItems.add(item);                                //listViewItems 에
    }

    public void clearItem(){
        listViewItems.clear();
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

 /* public void checkedConfirm(int position) {
            ListData check = listData.get(position);
            // 체크된 아이템인지 판단할 boolean 변수
            for (int i = 0; i < listData.size(); i++) {
                if (check.checkBox.isChecked()==true) {
                    check.checkBox.setChecked(true);
                } else {
                    check.checkBox.setChecked(false);
                }
                notifyDataSetChanged();
            }
        }*/

}
