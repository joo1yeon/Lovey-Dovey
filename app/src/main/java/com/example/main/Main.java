package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Fragment {
    ImageView gift_Btn, profile_Btn1, profile_Btn2, storage, close;
    TextView to_do_Btn, date;
    View profileLayout1, profileLayout2;

    public Main(){}

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.main,container,false);

        to_do_Btn = layout.findViewById(R.id.to_do_Btn);
        gift_Btn = layout.findViewById(R.id.gift_Btn);
        date = layout.findViewById(R.id.date);

        profile_Btn1 = layout.findViewById(R.id.profile_Btn1);
        profile_Btn2 = layout.findViewById(R.id.profile_Btn2);


        //Date 날짜 계산 함수
        doDateSystem();



        //to_do_list 버튼 눌렀을 때 --> to_do 화면 이동
        /*to_do_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ToDoList.class);
                startActivity(intent);
            }
        });*/


        //선물버튼 눌렀을 때 --> 선물화면 이동
        gift_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Gift_Main.class);
                startActivity(intent);
            }
        });

/*
        //왼쪽 프로필을 누를 때 -->  정보 변경 가능한 다이얼로그 창
        profile_Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                final AlertDialog dl = dlg.create();
                profileLayout1 = View.inflate(getContext(), R.layout.activity_profile1, null);
                dl.setView(profileLayout1);
                dl.show();

                storage = profileLayout1.findViewById(R.id.storage);
                storage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dl.dismiss();
                    }
                });

            }
        });

        //오른쪽 프로필을 누를 때 -->  정보 변경 불가능한 다이얼로그 창
        profile_Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                final AlertDialog dl2 = dlg.create();
                profileLayout2 = View.inflate(getContext(), R.layout.activity_profile2, null);
                dl2.setView(profileLayout2);
                dl2.show();

                close = profileLayout2.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dl2.dismiss();
                    }
                });

            }
        });*/

        return layout;
    }

    public void doDateSystem(){
        String start = "2019-03-04";        // 사귄 날짜 입력

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(start);
            String end = formatter.format(new Date());
            Date endDate = formatter.parse(end);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff =  endDate.getTime() - beginDate.getTime();
            long coupleDays = diff / (24 * 60 * 60 * 1000);

            if(coupleDays > 1000) {
                date.setTextSize(40);
            }
            date.setText (coupleDays+" 일");


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
