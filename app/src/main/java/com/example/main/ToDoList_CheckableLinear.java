package com.example.main;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;

public class ToDoList_CheckableLinear extends ConstraintLayout implements Checkable {
    public ToDoList_CheckableLinear(Context context, AttributeSet att){
        super(context, att);
    }

    @Override
    public void setChecked(boolean b) {
        CheckBox cb = findViewById(R.id.checkbox1);

        if(cb.isChecked() != b){
            cb.setChecked(b);
        }

    }

    @Override
    public boolean isChecked() {    //현재 Checked 상태를 리턴
        CheckBox cb = findViewById(R.id.checkbox1);

        return cb.isChecked();
    }


    @Override
    public void toggle() {  //현재 Checked 상태를 바꿈
        CheckBox cb = findViewById(R.id.checkbox1);

        setChecked(cb.isChecked() ? false : true);

    }
}
