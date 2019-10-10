package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class DateImageClick extends AppCompatActivity {

    ListView listView;
    Datecourse_ListViewAdapter adapter;
    ArrayList<Datecourse_ListViewItem> date_listItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateimage_click);

        ImageButton btnHome=findViewById(R.id.btnHome);
        final String[] PlaceUrl={"http://mjckjs.gabia.io//whispering/image/datecourse/date_seok.jpg", "http://mjckjs.gabia.io//whispering/image/datecourse/date_child.jpg",
                "http://mjckjs.gabia.io//whispering/image/datecourse/date_incheon.jpg", "http://mjckjs.gabia.io//whispering/image/datecourse/date_suwon.jpg",
                "http://mjckjs.gabia.io//whispering/image/datecourse/date_yeouido.jpg", "http://mjckjs.gabia.io//whispering/image/datecourse/date_gyeongpo.jpg"};
        final String[] PlaceN={"석촌호수","어린이 대공원","인천대공원","수원 경기도청","여의도","경포호수"};

        listView = findViewById(R.id.imagelist);
        date_listItem=new ArrayList<Datecourse_ListViewItem>();
        adapter = new Datecourse_ListViewAdapter(this,date_listItem);

        for(int i=0;i<PlaceN.length;i++){
        date_listItem.add(new Datecourse_ListViewItem( PlaceUrl[i],PlaceN[i]));
        listView.setAdapter(adapter);
        }


        //화면에 보여진 listView 클릭시 발생되는 이벤트 리스너

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DateImageClick.this, Datecourse_Fragment.class);
                intent.putExtra("PlaceN",PlaceN[position]);
                intent.putExtra("PlaceUrl",PlaceUrl[position]);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DateImageClick.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
