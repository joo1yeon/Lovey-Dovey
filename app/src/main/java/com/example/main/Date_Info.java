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

@SuppressLint("ValidFragment")
public class Date_Info extends Fragment {

    String placeP, placeTime, placeTel, placeC, placeC2;
    int id;
    TextView place,time,tel,content,content2;

    public Date_Info(String placeP, String placeTime, String placeTel, String placeC, String placeC2,int id) {
        this.placeP = placeP;
        this.placeTime = placeTime;
        this.placeTel = placeTel;
        this.placeC = placeC;
        this.placeC2 = placeC2;
        this.id=id;
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.date_info, container, false);

        place = layout.findViewById(R.id.place);
        time = layout.findViewById(R.id.time);
        tel = layout.findViewById(R.id.tel);
        content = layout.findViewById(R.id.content);
        content2 = layout.findViewById(R.id.content2);

        place.setText(placeP);
        time.setText(placeTime);
        tel.setText(placeTel);

        if(id==1){
            content.setText("입장료 : "+ placeC);
            content2.setText("역 정보 : "+placeC2);
        }else {
            content.setText("상세정보 : "+placeC);
        }

        return layout;
    }
}
