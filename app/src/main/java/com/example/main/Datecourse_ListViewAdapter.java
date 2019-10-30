package com.example.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Datecourse_ListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Datecourse_ListViewItem> date_listItem = new ArrayList<Datecourse_ListViewItem>();
    String[] PlaceUrl = new String[6];
    int id;
    int position;

    public Datecourse_ListViewAdapter(Context context, ArrayList<Datecourse_ListViewItem> date_listItem, int id, int position) {
        this.context = context;
        this.date_listItem = date_listItem;
        this.id = id;
        this.position = position;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dateimage_listview, parent, false);
        }
        final ImageView image = convertView.findViewById(R.id.image);
        TextView placeName = convertView.findViewById(R.id.placeName);

        placeName.setText(date_listItem.get(position).getTitle());

        Call<List<ResponseDate_image2>> res = Net.getInstance().getApi().getDate_image2(id);
        res.enqueue(new Callback<List<ResponseDate_image2>>() {
            @Override
            public void onResponse(Call<List<ResponseDate_image2>> call, Response<List<ResponseDate_image2>> response) {
                if (response.isSuccessful()) {
                    List<ResponseDate_image2> responseGet = response.body();
                    int i = 0;
                    for (ResponseDate_image2 responseDate_Image2 : responseGet) {
                        PlaceUrl[i] = responseDate_Image2.getDate_image();
                        Log.d("OKK", PlaceUrl[i]);
                        ++i;
                    }
                    Glide.with(context).load(PlaceUrl[position]).into(image);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseDate_image2>> call, Throwable t) {
                Log.d("III", "fail");
            }
        });

        return convertView;
    }
}
