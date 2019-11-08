package com.example.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
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

import static com.example.main.R.drawable.ic_star_black_24dp;
import static com.example.main.R.drawable.ic_star_border_black_24dp;

public class Datecourse_Fragment extends AppCompatActivity implements View.OnClickListener {

    TextView placeN, dateInfo, dateReview, infoLine, reviewLine;
    Date_Info date_info;
    Date_Review date_review;
    ImageView favorite, place;
    String PlaceName, Placeimage, place_id;
    int id;
    String nickname = MainActivity.id;
    int star;

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
                if (!favorite.isSelected()) {
                    favorite.setSelected(true);
                    star=0;
                    Toast.makeText(Datecourse_Fragment.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    favorite.setSelected(false);
                    star=1;
                    Toast.makeText(Datecourse_Fragment.this, "즐겨찾기에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
                Call<ResponseBookmark> res = Net.getInstance().getApi().getBookmark(place_id, PlaceName, Placeimage, star, nickname);
                Log.d("EYE", "성공");
                res.enqueue(new Callback<ResponseBookmark>() {
                    @Override
                    public void onResponse(Call<ResponseBookmark> call, Response<ResponseBookmark> response) {
                        if (response.isSuccessful()) {
                            Log.d("EYE", "성공2");
                            ResponseBookmark responseGet = response.body();
                            favorite.setBackgroundResource(ic_star_black_24dp);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBookmark> call, Throwable t) {
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