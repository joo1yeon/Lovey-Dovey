package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllReview extends AppCompatActivity {

    ListView listView;
    TextView name;
    DateReview_listViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_reviewcheck);

        Intent intent = getIntent();
        String placeName = intent.getStringExtra("placeN");
        final String place_id = intent.getStringExtra("place_id");

        name = findViewById(R.id.name);
        name.setText(placeName);

        //리뷰 정렬하는 요소를 넣는 ArrayList 선언 및 생성, 어댑터
        final ArrayList<String> reviewList;
        ArrayAdapter reviewAdapter;
        String[] review = {"최신순", "별점 높은 순 ", "별점 낮은 순"};

        reviewList = new ArrayList<>();

        for (int i = 0; i < review.length; i++) {
            reviewList.add(review[i]);
        }
        final Spinner spinner = findViewById(R.id.spinner);

        reviewAdapter = new ArrayAdapter(AllReview.this, android.R.layout.simple_spinner_dropdown_item, reviewList);
        spinner.setAdapter(reviewAdapter);

        adapter = new DateReview_listViewAdapter();

        listView = findViewById(R.id.listView);
        //listView.setAdapter(((Date_Review) Date_Review.context).adapter);
        listView.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int tag = position;
                Call<List<ResponseAllReview>> res = Net.getInstance().getApi().getAllReview(tag, place_id);
                res.enqueue(new Callback<List<ResponseAllReview>>() {
                    @Override
                    public void onResponse(Call<List<ResponseAllReview>> call, Response<List<ResponseAllReview>> response) {
                        List<ResponseAllReview> responseGet = response.body();
                        adapter.review_listItem.clear();
                        for (ResponseAllReview responseAllReview : responseGet) {
                            adapter.addItem(responseAllReview.getRate(), responseAllReview.getContent(),
                                    responseAllReview.getYear() + "/" + responseAllReview.getMonth() + "/"
                                            + responseAllReview.getDay(), responseAllReview.getID());
                            adapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseAllReview>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}