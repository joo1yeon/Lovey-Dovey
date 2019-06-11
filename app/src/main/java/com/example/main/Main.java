package com.example.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.main,container,false);
        /*이 부분에 원래 자바 코드의 onCreate에 있는 부분 작성!!!
            findViewByid 는 layout.findViewByID로 바꾸면 사용가능!!!!
            MainActivity.this 이런거는 getContext()로 사용가능!!!
        */

        return layout;
    }
}
