package com.example.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LocListView extends BaseAdapter {
    Context context;
    ArrayList<LocItem> listItem = new ArrayList<LocItem>();
    LocListView.ViewHolder viewHolder;

    public LocListView(Context context, ArrayList<LocItem>_listItem){
        this.context=context;
        this.listItem=_listItem;
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

    @Override   //position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.loc_result_list, parent, false);
            viewHolder = new LocListView.ViewHolder();

            viewHolder.locAddress= convertView.findViewById(R.id.LocAddress);
            viewHolder.locLatitude=convertView.findViewById(R.id.Longitude);
            viewHolder.locLongitude=convertView.findViewById(R.id.Latitude);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LocListView.ViewHolder) convertView.getTag();
        }
        viewHolder.locAddress.setText(listItem.get(position).getLocAddress());
        viewHolder.locLatitude.setText("위도 : "+listItem.get(position).getLocLatitude());
        viewHolder.locLongitude.setText("경도 : "+listItem.get(position).getLocLongitude());
        return convertView;
    }

    class ViewHolder {
        TextView locAddress;
        TextView locLatitude;
        TextView locLongitude;
    }

}
