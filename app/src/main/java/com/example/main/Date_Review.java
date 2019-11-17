package com.example.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class Date_Review extends Fragment {
    Button review, OK, cancel;
    EditText addText;
    ImageView add;
    RatingBar _ratingbar;
    ListView listView;
    String place_id, placeN;
    public DateReview_listViewAdapter adapter;
    public static Date_Review context;

    public Date_Review(String place_id, String placeN) {
        this.place_id = place_id;
        this.placeN = placeN;
    }


    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.date_review, container, false);
        context = this;
        printReview();

        listView = layout.findViewById(R.id.listView);
        adapter = new DateReview_listViewAdapter();
        listView.setAdapter(adapter);
        review = layout.findViewById(R.id.review);
        add = layout.findViewById(R.id.add);

        long now = System.currentTimeMillis();
        final Date ndate = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String Date = mFormat.format(ndate);

        //리뷰를 추가할 수 있는 dialog를 띄운다.
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dig = new AlertDialog.Builder(getContext());
                final AlertDialog _dig = dig.create();
                final View digView = View.inflate(getContext(), R.layout.dialog_date_review, null);

                OK = digView.findViewById(R.id.OK);
                cancel = digView.findViewById(R.id.cancel);
                _ratingbar = digView.findViewById(R.id._ratingBar);

                _dig.setCancelable(false);
                _dig.setView(digView);
                _dig.show();

                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addText = digView.findViewById(R.id.addText);
                        final String text = addText.getText().toString();
                        int year = ndate.getYear() + 1900;
                        int month = ndate.getMonth() + 1;
                        int day = ndate.getDate();
                        final Call<ResponseReview> res = Net.getInstance().getApi().getReview(place_id, _ratingbar.getRating(), MainActivity.id, text, year, month, day);
                        res.enqueue(new Callback<ResponseReview>() {
                            @Override
                            public void onResponse(Call<ResponseReview> call, Response<ResponseReview> response) {
                                if (response.body().getReview()) {
                                    printReview();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseReview> call, Throwable t) {

                            }
                        });
                        Toast.makeText(getContext(), "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        _dig.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _dig.dismiss();
                    }
                });
            }
        });
        return layout;
    }

    public void printReview() {
        Call<List<ResponseGetReview>> res = Net.getInstance().getApi().getPrintReview(place_id);
        res.enqueue(new Callback<List<ResponseGetReview>>() {
            @Override
            public void onResponse(Call<List<ResponseGetReview>> call, Response<List<ResponseGetReview>> response) {
                final List<ResponseGetReview> responseGet = response.body();
                adapter.review_listItem.clear();
                for (ResponseGetReview responseReview : responseGet) {
                    adapter.addItem(responseReview.getRate(), responseReview.getContent(), responseReview.getYear() + "/" + responseReview.getMonth() + "/" + responseReview.getDay(), responseReview.getID());
                    adapter.notifyDataSetChanged();
                }
                //전체리뷰 보기 클릭시 발생하는 이벤트 리스러
                review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), AllReview.class);
                        intent.putExtra("placeN", placeN);
                        intent.putExtra("place_id",place_id);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ResponseGetReview>> call, Throwable t) {
            }
        });
    }
}
