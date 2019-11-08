package com.example.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ValidFragment")
public class Date_Info extends Fragment {

    String place_id;
    int id;
    TextView place, time, tel, tvContent,content,tvContent2 ,content2;

    public Date_Info(String place_id, int id) {
        this.place_id = place_id;
        this.id = id;
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.date_info, container, false);

        place = layout.findViewById(R.id.place);
        time = layout.findViewById(R.id.time);
        tel = layout.findViewById(R.id.tel);
        tvContent = layout.findViewById(R.id.tvContent);
        content = layout.findViewById(R.id.content);
        tvContent2 = layout.findViewById(R.id.tvContent2);
        content2 = layout.findViewById(R.id.content2);

        Call<ResponseDate_image3> res = Net.getInstance().getApi().getDate_image3(place_id, id);
        res.enqueue(new Callback<ResponseDate_image3>() {
            @Override
            public void onResponse(Call<ResponseDate_image3> call, Response<ResponseDate_image3> response) {
                if (response.isSuccessful()) {
                    ResponseDate_image3 responseGet = response.body();
                    place.setText(responseGet.getplace());
                    time.setText(responseGet.gettime());
                    tel.setText(responseGet.gettel());

                    if (id % 2 != 0) {
                        content.setText(responseGet.getcontent());
                        content2.setText(responseGet.getcontent2());
                    } else {
                        tvContent.setText("상세정보 :");
                        content.setText(responseGet.getcontent());
                        tvContent2.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDate_image3> call, Throwable t) {

            }
        });

        return layout;
    }
}
