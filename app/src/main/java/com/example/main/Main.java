package com.example.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Main extends Fragment {
    /*원래 자바 코드에서 전역변수 선언 부분*/

    public Main(){}

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_main,container,false);

        //인플레이트
        to_do_Btn = layout.findViewById(R.id.to_do_Btn);                            //투두리스트 버튼
        gift_Btn = layout.findViewById(R.id.gift_Btn);                              //선물버튼
        date = layout.findViewById(R.id.date);                                      //메인화면 사귄날짜

        profile_Btn1 = layout.findViewById(R.id.profile_Btn1);                      //프로필사진1(나) 버튼
        profile_Btn2 = layout.findViewById(R.id.profile_Btn2);                      //프로필사진2(상대방) 버튼


        //Date 날짜 계산 함수
        doDateSystem();



        //to_do_list 버튼 눌렀을 때 --> to_do 화면 전환
        to_do_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ToDoList.class);
                startActivity(intent);
            }
        });


        //선물버튼 눌렀을 때 --> 선물화면 전환
        gift_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.main,container,false);
        /*이 부분에 원래 자바 코드의 onCreate에 있는 부분 작성!!!
            findViewByid 는 layout.findViewByID로 바꾸면 사용가능!!!!
            MainActivity.this 이런거는 getContext()로 사용가능!!!
        */

        return layout;
    }
}
