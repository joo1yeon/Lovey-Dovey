package com.example.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity{
    String color = "#B8D9C0";
    ViewPager viewPager;
    ImageButton btnHome, btnOverflow;
    Button btnDate, btnFoot, btnGal;
    Main mainFrag;
    Datecourse dateFrag;
    FootPrint footFrag;
    Album albumFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.mainFrame);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

        btnHome = findViewById(R.id.btnHome);
        btnOverflow = findViewById(R.id.btnOverflow);
        btnDate = findViewById(R.id.btnDate);
        btnFoot = findViewById(R.id.btnFoot);
        btnGal = findViewById(R.id.btnGallery);

        mainFrag = new Main();
        dateFrag = new Datecourse();
        footFrag = new FootPrint();
        albumFrag = new Album();

        btnHome.setOnClickListener(movePageListener);
        btnHome.setTag(0);
        btnDate.setOnClickListener(movePageListener);
        btnDate.setTag(1);
        btnFoot.setOnClickListener(movePageListener);
        btnFoot.setTag(2);
        btnGal.setOnClickListener(movePageListener);
        btnGal.setTag(3);

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
                    return mainFrag;
                case 1:
                    return dateFrag;
                case 2:
                    return footFrag;
                case 3:
                    return albumFrag;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}

