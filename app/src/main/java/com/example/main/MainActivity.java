package com.example.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ViewGroup info,notice,background,bookmark;
    DrawerLayout drawerLayout;
    String color = "#B8D9C0";
    ViewPager viewPager;
    LinearLayout linearLayout;
    ImageButton  btnOverflow,btnBack;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBack=findViewById(R.id.btnBack);
        btnOverflow=findViewById(R.id.btnOverflow);
        drawerLayout=findViewById(R.id.drawer_layout);
        info=findViewById(R.id.btnInfo);
        notice=findViewById(R.id.btnNotice);
        background=findViewById(R.id.btnBG);
        bookmark=findViewById(R.id.btnBookMark);
        linearLayout = findViewById(R.id.main_content);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //TODO 오버플로우버튼 클릭
        btnOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        //TODO 내비게이션뷰 닫기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "개인정보 수정", Toast.LENGTH_SHORT).show();
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "공지사항", Toast.LENGTH_SHORT).show();
            }
        });
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "배경화면 설정", Toast.LENGTH_SHORT).show();
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "즐겨찾기", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<Fragment>();
        private String titles[] = new String[]{"홈","데이트코스", "발자국", "앨범"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new Main());
            fragments.add(new Datecourse());
            fragments.add(new FootPrint());
            fragments.add(new Album());
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    Main mainFragment = new Main();
                    return mainFragment;
                case 1:
                    Datecourse dateFragment = new Datecourse();
                    return dateFragment;
                case 2:
                    FootPrint footFragment = new FootPrint();
                    return footFragment;
                case 3:
                    StoryListFragment albumFragment = new StoryListFragment(); // 여기서 Album() 말고 StoryListFragment() 로 변경!
                    return albumFragment;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}

