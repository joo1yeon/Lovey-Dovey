package com.example.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bumptech.glide.Glide;

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
    String[] PlaceUrl = new String[6];
    String[] PlaceTime = new String[6];
    String[] PlaceTel = new String[6];
    String[] PlaceC = new String[6];
    String[] PlaceP = new String[6];
    int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateimage_click);

        ImageButton btnHome = findViewById(R.id.btnHome);

        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);

        listView = findViewById(R.id.imagelist);
        date_listItem = new ArrayList<Datecourse_ListViewItem>();
        adapter = new Datecourse_ListViewAdapter(this, date_listItem, id, position);

        Call<List<ResponseDate_image2>> res = Net.getInstance().getApi().getDate_image2(id);
        res.enqueue(new Callback<List<ResponseDate_image2>>() {
            @Override
            public void onResponse(Call<List<ResponseDate_image2>> call, Response<List<ResponseDate_image2>> response) {
                if (response.isSuccessful()) {
                    List<ResponseDate_image2> responseGet = response.body();
                    int i = 0;
                    for (ResponseDate_image2 responseDate_Image2 : responseGet) {
                        PlaceN[i] = responseDate_Image2.getname();
                        PlaceUrl[i] = responseDate_Image2.getDate_image();
                        PlaceTime[i] = responseDate_Image2.gettime();
                        PlaceTel[i] = responseDate_Image2.gettel();
                        PlaceC[i] = responseDate_Image2.getcontent();
                        PlaceP[i] = responseDate_Image2.getplace();
                        ++i;
                    }
                    for (i = 0; i < PlaceN.length; i++) {
                        Log.d("time", PlaceTime[i]);
                        Log.d("tel", PlaceTel[i]);
                        Log.d("content", PlaceC[i]);
                        Log.d("place", PlaceP[i]);
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DateImageClick.this, Datecourse_Fragment.class);
                intent.putExtra("PlaceN", PlaceN[position]);
                intent.putExtra("PlaceUrl", PlaceUrl[position]);
                intent.putExtra("Position", position);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DateImageClick.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
