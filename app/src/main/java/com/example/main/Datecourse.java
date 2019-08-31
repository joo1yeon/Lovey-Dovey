package com.example.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Datecourse extends Fragment {
    /*원래 자바 코드에서 전역변수 선언 부분*/

    public Datecourse(){}

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.datecourse,container,false);
        /*이 부분에 원래 자바 코드의 onCreate에 있는 부분 작성!!!
            findViewByid 는 layout.findViewByID로 바꾸면 사용가능!!!!
            MainActivity.this 이런거는 getContext()로 사용가능!!!
        */
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_date,container,false);
        // Fragment로 넘길 Image Resource

        //ArrayList에 해당 image를 넣는다.
        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.image1);
        listImage.add(R.drawable.image2);
        listImage.add(R.drawable.image3);
        listImage.add(R.drawable.image4);

        ViewPager viewPager = layout.findViewById(R.id.date_viewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager());
        // ViewPager와  FragmentAdapter 연결
        viewPager.setAdapter(fragmentAdapter);

        return layout;
    }
}
