package com.example.main;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.http.HEAD;

public class Datecourse_Fragment extends AppCompatActivity implements View.OnClickListener {

    TextView placeN, dateInfo, dateReview, infoLine, reviewLine;
    Date_Info date_info;

    ImageView favorite, place;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_imageclick);

        placeN = findViewById(R.id.placeName);
//        place=findViewById(R.id.place);
        dateInfo = findViewById(R.id.dateInfo);
        dateReview = findViewById(R.id.dateReview);
        infoLine = findViewById(R.id.infoLine);
        reviewLine = findViewById(R.id.reviewLine);
        date_info = new Date_Info();

        favorite = findViewById(R.id.favorite);
        date_info = new Date_Info();

        Intent intent = getIntent();
        String PlaceName = intent.getStringExtra("PlaceN");

/*
        Intent intent2=getIntent();
        String PlaceUrl=intent2.getParcelableExtra("PlaceUrl");
*/
//        placeN.setText(PlaceName);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i % 2 != 0) {
                    favorite.setSelected(true);
                    Toast.makeText(Datecourse_Fragment.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    favorite.setSelected(false);
                    Toast.makeText(Datecourse_Fragment.this, "즐겨찾기에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        date_info = new Date_Info();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFrag, new Date_Info())
                    .commit();
        }
        dateInfo.setOnClickListener(this);
        dateReview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateInfo:
                dateInfo.setTextColor(Color.rgb(184, 217, 192));
                infoLine.setBackgroundColor(Color.rgb(184, 217, 192));
                dateReview.setTextColor(Color.rgb(140, 140, 140));
                reviewLine.setBackgroundColor(Color.rgb(140, 140, 140));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrag, new Date_Info())
                        .commit();
                break;
            case R.id.dateReview:
                dateInfo.setTextColor(Color.rgb(140, 140, 140));
                infoLine.setBackgroundColor(Color.rgb(140, 140, 140));
                dateReview.setTextColor(Color.rgb(184, 217, 192));
                reviewLine.setBackgroundColor(Color.rgb(184, 217, 192));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrag, new Date_Review())
                        .commit();
                break;
        }
    }
}