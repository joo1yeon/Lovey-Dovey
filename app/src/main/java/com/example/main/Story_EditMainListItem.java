package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Story_EditMainListItem extends AppCompatActivity implements DatePickerFragment.OnDatePickerSetListener {
    TextView topTextView, tvPressIcon;
    Button btnCancel, btnConfirm;
    ImageView icCalendar;
    Story_Create mStoryCreate;
    int year, month, day;
    private static final String DIALOG_DATE = "DialogDate";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_create);

        topTextView = findViewById(R.id.topPanel);
        tvPressIcon = findViewById(R.id.tv_press_icon);
        btnCancel = findViewById(R.id.btn_cancel);
        btnConfirm = findViewById(R.id.btn_next);
        icCalendar = findViewById(R.id.ic_calendar);

        topTextView.setText("스토리 수정");

        icCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE); //DialogFragment 를 화면에 보여주기 위해 FragmentManager가 onCreateDialog 호출
            }
        });

        btnConfirm.setText("확인");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onDatePickerSet(int y, int m, int d){ //DatePickerFragment 로부터 날짜를 받아온다.
        year = y;
        month = m;
        day = d;
        if (year != 0 && month != 0 && day != 0) {
            tvPressIcon.setText(year + "년 " + month + "월 " + day + "일");
        }
    }
}
