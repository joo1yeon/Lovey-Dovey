package com.example.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.main.R.layout.find_id;
import static com.example.main.R.layout.fragment_main;


public class MainActivity<insertDB> extends AppCompatActivity implements InsertDB {
    LinearLayout layout;
    MyDBHelper dbHelper = new MyDBHelper(this);
    SQLiteDatabase sqlDB;
    ViewGroup info, notice, background, bookmark;
    DrawerLayout drawerLayout;
    static String id;
    static int coupleID;
    static String nickname, email;
    NonSwipeViewPager viewPager;
    LinearLayout logout;
    ImageButton btnOverflow, btnBack;
    Intent intent;
    Toast toast;
    ImageView profile;
    View profileLayout1;
    ImageView storage;
    ImageView profile_img;
    EditText _email, _name;
    FloatingActionButton btnSave, btnGallery;
    int REQUEST_CODE = 2;
    String uri_;

    @Override
    public void insert(String name, String address, double latitude, double longitude, int year, int month, int date) {
        sqlDB = dbHelper.getWritableDatabase();
        sqlDB.execSQL("insert into marker values('" + name + "','" + address + "'," + latitude + "," + longitude + "," + year + "," + month + "," + date + ");");
        sqlDB.close();
    }

    @Override
    public void delete(double latitude, double longitude, int year, int month, int date) {
        sqlDB = dbHelper.getWritableDatabase();
        sqlDB.execSQL("delete from marker where latitude=" + latitude + " and longitude=" + longitude + " and year =" + year + " and month=" + month + " and date =" + date + ";");
        sqlDB.close();
    }

    @Override
    public void save() {
        Toast.makeText(this, "서버 저장", Toast.LENGTH_SHORT).show();
        sqlDB = dbHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("select * from marker ;", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String address = cursor.getString(1);
            double latitude = cursor.getDouble(2);
            double longitude = cursor.getDouble(3);
            int year = cursor.getInt(4);
            int month = cursor.getInt(5);
            int date = cursor.getInt(6);
            Call<ResAddMarker> res = Net.getInstance().getApi().getAdd(name, address, latitude, longitude, year, month, date, coupleID);
            Log.d("III", "이름" + name);
            res.enqueue(new Callback<ResAddMarker>() {
                @Override
                public void onResponse(Call<ResAddMarker> call, Response<ResAddMarker> response) {
                    if (response.isSuccessful()) {
                        ResAddMarker responseGet = response.body();

                    } else Toast.makeText(MainActivity.this, "통신1 에러", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResAddMarker> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "통신3 에러", Toast.LENGTH_SHORT).show();
                }
            });

        }
        sqlDB.execSQL("delete from marker;");
        sqlDB.close();
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LOGINN", "메인화면");
        super.onCreate(savedInstanceState);
        layout = findViewById(R.id.layout);

        Intent intent1 = getIntent();
        id = intent1.getStringExtra("ID");
        nickname = intent1.getStringExtra("NICK");
        email = intent1.getStringExtra("EMAIL");
        coupleID = intent1.getIntExtra("C_ID", 0);
        Log.d("C_ID", "커플아이디:" + coupleID);
        Toast.makeText(MainActivity.this, id + "로 로그인", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_main);
        sqlDB = dbHelper.getWritableDatabase();
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
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab);
        logout = findViewById(R.id.btnLogout);
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
//                layout.setClickable(false);
                drawerLayout.openDrawer(Gravity.RIGHT);
//                Cursor cursor = sqlDB.rawQuery("select nickname, email from info where id='" + id + "';", null);
//                if (cursor.getCount() >= 1) {
//                    cursor.moveToFirst();
//                    nickname = cursor.getString(0);
//                    email = cursor.getString(1);
//                }
//                cursor.close();
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
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);                            //Main 화면에 다이얼로그 생성
                final AlertDialog dl = dlg.create();                                                        //빌더를 이용해 AlertDialog 객체 생성
                profileLayout1 = View.inflate(MainActivity.this, R.layout.profile1, null);                 //레이아웃뷰인 profileLayout을 메인에 profile.xml 사용해서 인플레이트
                dl.setView(profileLayout1);                                                                 //porofileLayout을 AlertDialog 객체로 보여줌
                dl.show();                                                                                  //다이얼로그 보여줌

                //메인화면 다이얼로그에 들어가는 profile1의 뷰들 인플레이트
                storage = profileLayout1.findViewById(R.id.storage);
                profile_img = profileLayout1.findViewById(R.id.profile_img);
                _email = profileLayout1.findViewById(R.id.et_email);
                _name = profileLayout1.findViewById(R.id.name);

                _name.setText(MainActivity.nickname);
                _email.setText(MainActivity.email);


                //입력 유형 이메일로 설정
                _email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                //저장된 값 보여주기
                Glide.with(MainActivity.this)
                        .load(Main.uri_)
                        .centerCrop()
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(MainActivity.this))
                        .override(70, 70)
                        .into(profile_img);


                profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "사진 선택"), 1);
                    }
                });

                //저장을 버튼을 클릭했을 때 수정된 내용을 저장한 후 다이얼로그 종료
                storage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //메인화면 프로필 변경
                        Glide.with(MainActivity.this)
                                .load(Main.uri_)
                                .centerCrop()
                                .crossFade()
                                .bitmapTransform(new CropCircleTransformation(MainActivity.this))
                                .override(70, 70)
                                .into(Main.profile_Btn1);

                        //EditText에 변경한 값 받아오기
                        Call<ResponseInfoUpdate> res = Net.getInstance().getApi().getInfoUpdate(MainActivity.id, MainActivity.nickname, MainActivity.email);
                        res.enqueue(new Callback<ResponseInfoUpdate>() {
                            @Override
                            public void onResponse(Call<ResponseInfoUpdate> call, Response<ResponseInfoUpdate> response) {
                                if (response.body().getUpdate()) {
                                    Toast.makeText(MainActivity.this, "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                    MainActivity.email = _email.getText().toString();
                                    MainActivity.nickname = _name.getText().toString();
                                    dl.dismiss();
                                } else {
                                    Toast.makeText(MainActivity.this, "정보가 저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseInfoUpdate> call, Throwable t) {

                            }
                        });
                    }
                });
                drawerLayout.closeDrawer(Gravity.RIGHT);
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
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "즐겨찾기", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB.execSQL("delete from info where id='" + id + "';");
                Toast.makeText(MainActivity.this, id + "로그아웃", Toast.LENGTH_SHORT).show();
                Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logout);
                finish();
            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            try {
                Uri uri = data.getData();

                profile_img = profileLayout1.findViewById(R.id.profile_img);
                Glide.with(MainActivity.this)
                        .load(uri)
                        .centerCrop()
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(MainActivity.this))
                        .override(70, 70)
                        .into(profile_img);

                Main.uri_ = uri;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            try {
                Uri uri = data.getData();
                uri_ = String.valueOf(uri);
                drawerLayout.closeDrawer(Gravity.RIGHT);

                Glide.with(this).load(uri_).into(Main.img_ground);

                btnSave = findViewById(R.id.btnSave);
                btnGallery = findViewById(R.id.btnGallery);

                btnSave.setVisibility(View.VISIBLE);
                btnGallery.setVisibility(View.VISIBLE);

                btnGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sqlDB = dbHelper.getWritableDatabase();
                        sqlDB.execSQL("insert into back values('" + id + "','" + uri_ + "');");
                        Toast.makeText(MainActivity.this, "배경으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        btnSave.setVisibility(View.INVISIBLE);
                        btnGallery.setVisibility(View.INVISIBLE);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    class MyPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<Fragment>();
        private String titles[] = new String[]{"홈", "데이트코스", "발자국", "앨범"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new Main());
            fragments.add(new Datecourse());
            fragments.add(new FootPrint());
            fragments.add(new StoryListFragment()); //Ablum 대신 StoryListFragment로 변경
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
    public void onBackPressed() {
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

