package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Datecourse_Fragment extends AppCompatActivity {

    TextView dateInfo,dateReview;
    ViewPager viewPager;
    Date_Info  InfoFrag;
    Date_Review ReviewFrag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_imageclick);

        viewPager = findViewById(R.id.mainFrame);
        viewPager.setAdapter(new Datecourse_Fragment.PagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

        dateInfo=findViewById(R.id.dateInfo);
        dateReview=findViewById(R.id.dateReview);

        InfoFrag = new Date_Info();
        ReviewFrag=new Date_Review();

        dateInfo.setOnClickListener(movePageListener);
        dateInfo.setTag(0);
        dateReview.setOnClickListener(movePageListener);
        dateInfo.setTag(1);
    }
    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            viewPager.setCurrentItem(tag);
        }
    };
    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return InfoFrag;
                case 1:
                    return ReviewFrag;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
