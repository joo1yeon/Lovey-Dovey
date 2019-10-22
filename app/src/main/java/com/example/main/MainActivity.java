package com.example.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;


public class MainActivity extends AppCompatActivity {
    MyDBHelper dbHelper = new MyDBHelper(this);
    SQLiteDatabase sqlDB;
    ViewGroup info, notice, background, bookmark;
    DrawerLayout drawerLayout;
    String id,nickname,email;
    ViewPager viewPager;
    LinearLayout linearLayout,logout;
    ImageButton btnOverflow, btnBack;
    Intent intent;
    Toast toast;
    ImageView profile;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1=getIntent();
        id=intent1.getStringExtra("ID");
        nickname=intent1.getStringExtra("NICK");
        email=intent1.getStringExtra("EMAIL");

        setContentView(R.layout.activity_main);
        sqlDB=dbHelper.getWritableDatabase();
//        Cursor cursor = sqlDB.rawQuery("select * from info;",null);
//        if(cursor.getCount()!=0){
//            cursor.moveToFirst();
//            id=cursor.getString(0);
//        }

        profile = findViewById(R.id.profile);

        btnBack = findViewById(R.id.btnBack);
        btnOverflow = findViewById(R.id.btnOverflow);
        drawerLayout = findViewById(R.id.drawer_layout);
        info = findViewById(R.id.btnInfo);
        notice = findViewById(R.id.btnNotice);
        background = findViewById(R.id.btnBG);
        bookmark = findViewById(R.id.btnBookMark);
        linearLayout = findViewById(R.id.main_content);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab);
        logout=findViewById(R.id.btnLogout);
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

                //저장된 프로필 보여주기
                Glide.with(getApplicationContext())
                        .load(Main.uri_)
                        .centerCrop()
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                        .override(70, 70)
                        .into(profile);
                TextView tvNick = drawerLayout.findViewById(R.id.nickname);
                TextView tvEmail = drawerLayout.findViewById(R.id.email);
                tvNick.setText(nickname);
                tvEmail.setText(email);
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
                intent = new Intent(getApplicationContext(), Notice.class);
                startActivity(intent);
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


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB.execSQL("delete from info where id='"+id+"';");
                Toast.makeText(MainActivity.this,id+"로그아웃",Toast.LENGTH_SHORT).show();
                Intent logout = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(logout);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        id=data.getStringExtra("ID");
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<Fragment>();
        private String titles[] = new String[]{"홈", "데이트코스", "발자국", "앨범"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new Main(id));
            fragments.add(new Datecourse(id));
            fragments.add(new FootPrint(id));
            fragments.add(new StoryListFragment(id)); //Ablum 대신 StoryListFragment로 변경
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    Main mainFragment = new Main(id);
                    return mainFragment;
                case 1:
                    Datecourse dateFragment = new Datecourse(id);
                    return dateFragment;
                case 2:
                    FootPrint footFragment = new FootPrint(id);
                    return footFragment;
                case 3:
                    StoryListFragment albumFragment = new StoryListFragment(id); // 여기서 Album() 말고 StoryListFragment() 로 변경!
                    return albumFragment;
                default:
                    return null;
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
    //앱종료시간체크
    long backKeyPressedTime;    //앱종료 위한 백버튼 누른시간

    //TODO 뒤로가기 2번하면 앱종료
    @Override
    public void onBackPressed () {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }

    

}

