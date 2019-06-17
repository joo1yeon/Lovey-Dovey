package com.example.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Datecourse_Fragment extends AppCompatActivity implements View.OnClickListener {

    TextView dateInfo, dateReview,infoLine,reviewLine;
    ViewPager viewPager;
    Date_Info InfoFrag;
    Date_Review ReviewFrag;
    FragmentManager fm;
    FragmentTransaction tran;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_imageclick);

        dateInfo = findViewById(R.id.dateInfo);
        dateReview = findViewById(R.id.dateReview);
        infoLine=findViewById(R.id.infoLine);
        reviewLine=findViewById(R.id.reviewLine);

        dateInfo.setOnClickListener(this);
        dateReview.setOnClickListener(this);

        InfoFrag = new Date_Info();
        ReviewFrag = new Date_Review();
        setFrag(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateInfo:
                setFrag(0);
                dateInfo.setTextColor(Color.rgb(184,217,192));
                infoLine.setBackgroundColor(Color.rgb(184,217,192));
                dateReview.setTextColor(Color.rgb(140,140,140));
                reviewLine.setBackgroundColor(Color.rgb(140,140,140));
            case R.id.dateReview:
                setFrag(1);
                dateInfo.setTextColor(Color.rgb(140,140,140));
                infoLine.setBackgroundColor(Color.rgb(140,140,140));
                dateReview.setTextColor(Color.rgb(184,217,192));
                reviewLine.setBackgroundColor(Color.rgb(184,217,192));
        }
    }

    public void setFrag(int n) {    //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                tran.replace(R.id.mainFrame, InfoFrag);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
            case 1:
                tran.replace(R.id.mainFrame, ReviewFrag);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
        }
    }
}
