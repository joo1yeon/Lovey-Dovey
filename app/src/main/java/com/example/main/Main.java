package com.example.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.ViewSwitcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Fragment {
    ImageView profile_Btn1, profile_Btn2, storage, close;
    TextView  date, textView;
    View profileLayout1, profileLayout2;
    String[] todo = {"시험끝나고 미친듯이 놀기", "PC방 가서 하루종일 게임하기", "오류같이 찾고 기뻐하기 ㅎㅎ", "웃으면서 같이 코딩하기", "누워서 맘편히 잠자기", "종로가서 커플링 맞추기","커플 키링 만들어보기"};
    TextSwitcher to_do_Btn;
    Thread todoThread;
    EditText email, birthday, name;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public Main(){}

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_main,container,false);

        //인플레이트
        to_do_Btn = layout.findViewById(R.id.to_do_Btn);                            //투두리스트 버튼, To-do-list 보여주기
        date = layout.findViewById(R.id.date);                                      //메인화면 사귄날짜

        profile_Btn1 = layout.findViewById(R.id.profile_Btn1);                      //프로필사진1(나) 버튼
        profile_Btn2 = layout.findViewById(R.id.profile_Btn2);                      //프로필사진2(상대방) 버튼



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

        //스레드 객체 생성 및 시작
        todoThread = new TodoThread();
        todoThread.start();


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
                email = profileLayout1.findViewById(R.id.et_email);
                birthday = profileLayout1.findViewById(R.id.et_birthday);
                name = profileLayout1.findViewById(R.id.name);

                //저장을 버튼을 클릭했을 때 수정된 내용을 저장한 후 다이얼로그 종료
                storage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //EditText에 변경한 값 받아오기
                        String em = email.getText().toString();
                        String bth = birthday.getText().toString();
                        String nm = name.getText().toString();
                        //TODO 생각보다 어려웟따고한다.
                        //받아온 값으로 변경
                        email.setText(em);
                        birthday.setText(bth);
                        name.setText(nm);

                        dl.dismiss();                       //다이얼로그 닫기
                    }
                });

            }
        });

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

    //ToDoList 함수 TODO 탭 변경시 겹치는 오류 runOnUiThread 가 필요한가?
    class TodoThread extends Thread{
        boolean running =false;     //시작과 종료에 필요한 변수
        int index = 0;

        @Override
        public void run() {
            running=true;

            while (running){                            //무한루프, Todolist 계속 돌아가게 함
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        to_do_Btn.setText("•  " + todo[index]);
                        to_do_Btn.invalidate();
                    }
                });

                try { Thread.sleep(2000); }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                index++;
                if(index >= todo.length){
                    index=0;
                }

            }
        }
        //TODO# 스레드 멈추는 함수 필요할려나?
        public void halt(){
            running=false;
        }
    }
}
