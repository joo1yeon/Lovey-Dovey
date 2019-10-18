package com.example.main;


import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

@SuppressLint("ValidFragment")
public class Main extends Fragment {

    String id;


    private static int REQUEST_CODE = 1;
    private Context context;

    ConstraintLayout photo_change;
    ImageView profile_Btn1, profile_Btn2, storage, close, profile_img;
    TextView  date, textView;
    View profileLayout1, profileLayout2;
    ArrayList<String> todo = new ArrayList<String>();
    TextSwitcher to_do_Btn;
    Thread todoThread;
    EditText email, birthday, name;
    String em1, em2, bth1, bth2, nm1, nm2;
    String strCoupleID = "couple0";

    //sqlite 관련 변수
    MyDBHelper mainDB;
    SQLiteDatabase sqlDB;
    Cursor cursor;

    static Uri uri_=Uri.parse("android.resource://com.example.main/drawable/basic");

    //화면 보여주기 전에 todolist content가 담긴 ArrayList 삭제 및 초기화 후 추가
    @Override
    public void onStart() {
        super.onStart();
        todo.clear();
        Item_Content(strCoupleID);
    }


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @SuppressLint("ValidFragment")
    public Main(String _id){id=_id;}

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_main,container,false);


        mainDB = new MyDBHelper(getContext());          //헬퍼클래스 객체 생성
        context = getContext();
        todo.clear();
        Item_Content(strCoupleID);


        //인플레이트
        to_do_Btn = layout.findViewById(R.id.to_do_Btn);                            //투두리스트 버튼, To-do-list 보여주기
        date = layout.findViewById(R.id.date);                                      //메인화면 사귄날짜

        profile_Btn1 = layout.findViewById(R.id.profile_Btn1);                      //프로필사진1(나) 버튼
        profile_Btn2 = layout.findViewById(R.id.profile_Btn2);                      //프로필사진2(상대방) 버튼

        //profile1 저장된 사진 보여주기
        Glide.with(context)
                .load(uri_)
                .centerCrop()
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .override(70, 70)
                .into(profile_Btn1);

        //텍스트 바꾸기
        to_do_Btn.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView = new TextView(getContext());
                textView.setTextColor(Color.parseColor("#FFFFFF"));

                //폰트설정
                Typeface typeFace = ResourcesCompat.getFont(getContext(), R.font.netmarble_b);
                textView.setTypeface(typeFace);
                textView.setTextSize(15);   //폰트사이즈 설정
                return textView;
            }
        });

        //애니메이션 객체 생성 및 액션 정보 로딩
        Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.up_down);
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.down_up);

        //애니메이션 할당
        to_do_Btn.setInAnimation(in);
        to_do_Btn.setOutAnimation(out);

        //Date 날짜 계산 함수
        doDateSystem();


        //to_do_list 버튼 눌렀을 때 --> to_do 화면 전환
        to_do_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ToDoList.class);                                   //인텐트 선언 및 생성
                startActivity(intent);
            }
        });


        bth1 = "1998년 5월 19일";
        nm1 =  "꽁순이";
        em1 =  "abc123@maver.com";

        //왼쪽 프로필을 누를 때 -->  정보 변경 가능한 다이얼로그 창
        profile_Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());                            //Main 화면에 다이얼로그 생성
                final AlertDialog dl = dlg.create();                                                        //빌더를 이용해 AlertDialog 객체 생성
                profileLayout1 = View.inflate(getContext(), R.layout.profile1, null);                 //레이아웃뷰인 profileLayout을 메인에 profile.xml 사용해서 인플레이트
                dl.setView(profileLayout1);                                                                 //porofileLayout을 AlertDialog 객체로 보여줌
                dl.show();                                                                                  //다이얼로그 보여줌

                //메인화면 다이얼로그에 들어가는 profile1의 뷰들 인플레이트
                storage = profileLayout1.findViewById(R.id.storage);
                profile_img = profileLayout1.findViewById(R.id.profile_img);
                email = profileLayout1.findViewById(R.id.et_email);
                birthday = profileLayout1.findViewById(R.id.et_birthday);
                name = profileLayout1.findViewById(R.id.name);

                photo_change = profileLayout1.findViewById(R.id.photo_change);

                //입력 유형 이메일로 설정
                email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                //저장된 값 보여주기
                Glide.with(context)
                        .load(uri_)
                        .centerCrop()
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(context))
                        .override(70, 70)
                        .into(profile_img);

                email.setText(em1);
                birthday.setText(bth1);
                name.setText(nm1);

                photo_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "사진 선택"), REQUEST_CODE);
                    }
                });

                //저장을 버튼을 클릭했을 때 수정된 내용을 저장한 후 다이얼로그 종료
                storage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //메인화면 프로필 변경
                        Glide.with(context)
                                .load(uri_)
                                .centerCrop()
                                .crossFade()
                                .bitmapTransform(new CropCircleTransformation(context))
                                .override(70, 70)
                                .into(profile_Btn1);

                        //EditText에 변경한 값 받아오기
                        bth1 = birthday.getText().toString();
                        nm1 = name.getText().toString();
                        em1 = email.getText().toString();

                        //변경한 값 보여주기
                        email.setText(em1);
                        birthday.setText(bth1);
                        name.setText(nm1);

                        dl.dismiss();                       //다이얼로그 닫기
                    }
                });

            }
        });


        bth2 = "2000년 07월 07일";
        nm2 =  "꽉냥이";
        em2 =  "qwerty15@naver.com";

        //TODO# 데이터 베이스로 상태방 정보 불러오기
        //오른쪽 프로필을 누를 때 -->  정보 변경 불가능한 다이얼로그 창
        profile_Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());                         //Main 화면에 다이얼로그 생성
                final AlertDialog dl2 = dlg.create();                                                    //빌더를 이용해 AlertDialog 객체 생성
                profileLayout2 = View.inflate(getContext(), R.layout.profile2, null);              //레이아웃뷰인 profileLayout2을 메인에 profile2.xml 사용해서 인플레이트
                dl2.setView(profileLayout2);                                                             //porofileLayout2을 AlertDialog 객체로 보여줌
                dl2.show();                                                                              //다이얼로그 보여줌

                //메인화면 다이얼로그에 들어가는 profile2의 뷰들 인플레이트
                close = profileLayout2.findViewById(R.id.close);
                email = profileLayout2.findViewById(R.id.et_email);
                birthday = profileLayout2.findViewById(R.id.et_birthday);
                name = profileLayout2.findViewById(R.id.name);

                //저장된 값 보여주기
                email.setText(em2);
                birthday.setText(bth2);
                name.setText(nm2);


                //닫기 버튼 클릭했을 때 다이얼로그 종료
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dl2.dismiss();
                    }
                });
            }
        });

        return layout;
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            //스레드 객체 생성 및 시작
            todoThread=null;
            todoThread = new TodoThread();
            todoThread.start();
            Log.e("화면켜졌을 때", "나 켜졌어!");
            }
           else {
            try
            {
                Log.e("화면꺼졌을 때","나 다른화면에 있다!?" );
                todoThread.interrupt();             //스레드 멈추기
                Log.e("화면 멈췄다면...","스레드는 잘 멈췄어!" );
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


           }
    }




    //TODO# Data 날짜 계산 함수 -> 데이터베이스로 사귄날짜 받아오기
    public void doDateSystem(){
        String start = "2019-03-04";        // 사귄 날짜 입력

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");        //SimpleDataFormat 형태의 변수를 년-월-일로 생성
            Date beginDate = formatter.parse(start);                                        //사귄날짜를 입력받은 문자열을 date 형식으로 변경
            String end = formatter.format(new Date());                                      //현재날짜를 문자열로 받아옴
            Date endDate = formatter.parse(end);                                            //현재날짜를 받아온 문자열을 date 형식으로 변경

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff =  endDate.getTime() - beginDate.getTime();
            long coupleDays = diff / (24 * 60 * 60 * 1000);

            //사귄 날짜가 1000일이 넘으면 textSize 변경
            if(coupleDays > 1000) {
                date.setTextSize(30);
            }
            date.setText (coupleDays+" 일");     //사귄날짜 + 일 출력


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //ToDoList Check false인 내용 순서대로 삽입
    public void Item_Content(String id){
        sqlDB = mainDB.getReadableDatabase();

        cursor = sqlDB.rawQuery("SELECT * FROM to_do_list WHERE couple_id='"+id+"' AND checked = '"+ false +"';",null);
        int count = cursor.getCount();

        for(int i=0;i<count;i++) {
            cursor.moveToNext();                                    //커서 넘기기
            todo.add(cursor.getString(3));   //체크하지 않은 내용 넣기
            //String 배열 때 todo[i] = cursor.getString(3);
        }

        //todoArrayList 배열에 아무것도 들어있지 않을 때
        if (count==0){
            todo.add("TODO_LIST에 내용을 입력해주세요");
        }

        cursor.close();
        sqlDB.close();
    }

    //ToDoList 함수 TODO 탭 변경시 겹치는 오류..
    public class TodoThread extends Thread{
        boolean running =false;     //시작과 종료에 필요한 변수
        int index = 0;

        @Override
        public void run() {
            running=true;

            while (running){                            //무한루프, Todolist 계속 돌아가게 함
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        to_do_Btn.setText("•  " + todo.get(index)); //String 배열 때 todo[index]
                        to_do_Btn.invalidate();
                    }
                });

                try {

                    Thread.sleep(3000);
                }
                catch (InterruptedException e) {
                    halt();
                    e.printStackTrace();
                }
                index++;
                if(index >= todo.size()){   //String 배열 때length
                    index=0;}

            }
        }
        public void halt(){
            running=false;
        }


    }

    //앨범들어가서 사진 크롭하기
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            try {
                Uri uri = data.getData();

                profile_img = profileLayout1.findViewById(R.id.profile_img);
                Glide.with(context)
                        .load(uri)
                        .centerCrop()
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(context))
                        .override(70, 70)
                        .into(profile_img);

                uri_ = uri;


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
