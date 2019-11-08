package com.example.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateImageClick extends AppCompatActivity {

    Context context;
    ListView listView;
    Datecourse_ListViewAdapter adapter;
    ArrayList<Datecourse_ListViewItem> date_listItem;
    String[] PlaceN = new String[6];
    String[] Placeimage = new String[6];
    String[] place_id = new String[6];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateimage_click);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);

        listView = findViewById(R.id.imagelist);
        date_listItem = new ArrayList<Datecourse_ListViewItem>();
        adapter = new Datecourse_ListViewAdapter(this, date_listItem,id);

        Call<List<ResponseDate_image2>> res = Net.getInstance().getApi().getDate_image2(id);
        res.enqueue(new Callback<List<ResponseDate_image2>>() {
            @Override
            public void onResponse(Call<List<ResponseDate_image2>> call, Response<List<ResponseDate_image2>> response) {
                if (response.isSuccessful()) {
                    List<ResponseDate_image2> responseGet = response.body();
                    int i = 0;
                    for (ResponseDate_image2 responseDate_Image2 : responseGet) {
                        PlaceN[i] = responseDate_Image2.getname();
                        Placeimage[i] = responseDate_Image2.getimage();
                        place_id[i] = responseDate_Image2.getPlace_id();
                        ++i;
                    }
                }
                for (int i = 0; i < PlaceN.length; i++) {
                    date_listItem.add(new Datecourse_ListViewItem(PlaceN[i]));
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseDate_image2>> call, Throwable t) {
                Log.d("III", "fail");
            }
        });

        //화면에 보여진 listView 클릭시 발생되는 이벤트 리스너

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id2) {
                Intent intent = new Intent(DateImageClick.this, Datecourse_Fragment.class);
                intent.putExtra("PlaceN", PlaceN[position]);
                intent.putExtra("Placeimage", Placeimage[position]);
                intent.putExtra("Placeid", place_id[position]);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
}
