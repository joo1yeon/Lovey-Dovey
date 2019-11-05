package com.example.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Datecourse_Fragment extends AppCompatActivity implements View.OnClickListener {

    TextView placeN, dateInfo, dateReview, infoLine, reviewLine;
    Date_Info date_info;
    Date_Review date_review;
    ImageView favorite, place;
    String PlaceName, Placeimage, place_id;
    int id;
    int i=0;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_imageclick);

        placeN = findViewById(R.id.plaveN);
        place = findViewById(R.id.place);
        dateInfo = findViewById(R.id.dateInfo);
        dateReview = findViewById(R.id.dateReview);
        infoLine = findViewById(R.id.infoLine);
        reviewLine = findViewById(R.id.reviewLine);
        favorite = findViewById(R.id.favorite);

        Intent intent = getIntent();
        PlaceName = intent.getStringExtra("PlaceN");
        Placeimage = intent.getStringExtra("Placeimage");
        place_id = intent.getStringExtra("Placeid");
        id = intent.getIntExtra("id", 0);

        placeN.setText(PlaceName);
        Glide.with(this).load(Placeimage).into(place);

        date_info = new Date_Info(place_id, id);
        date_review = new Date_Review(place_id, PlaceName);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++i;
                if (i % 2 != 0) {
                    favorite.setSelected(true);
                    Toast.makeText(Datecourse_Fragment.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    favorite.setSelected(false);
                    Toast.makeText(Datecourse_Fragment.this, "즐겨찾기에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
                Log.d("favorite", i + "");
                Call<List<ResponseBookmark>> res = Net.getInstance().getApi().getBookmark(place_id, PlaceName, Placeimage, i);
                res.enqueue(new Callback<List<ResponseBookmark>>() {
                    @Override
                    public void onResponse(Call<List<ResponseBookmark>> call, Response<List<ResponseBookmark>> response) {
                    }
                    @Override
                    public void onFailure(Call<List<ResponseBookmark>> call, Throwable t) {
                        Log.d("III", "fail");
                    }
                });
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFrag, new Date_Info(place_id, id))
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
                        .replace(R.id.mainFrag, new Date_Info(place_id, id))
                        .commit();
                break;
            case R.id.dateReview:
                dateInfo.setTextColor(Color.rgb(140, 140, 140));
                infoLine.setBackgroundColor(Color.rgb(140, 140, 140));
                dateReview.setTextColor(Color.rgb(184, 217, 192));
                reviewLine.setBackgroundColor(Color.rgb(184, 217, 192));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrag, new Date_Review(place_id, PlaceName))
                        .commit();
                break;
        }
    }
}