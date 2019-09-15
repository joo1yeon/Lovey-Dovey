package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchStory extends AppCompatActivity implements DatePickerFragment.OnDatePickerSetListener {

    ImageView icCalendar;
    Button btnConfirm, btnCancel;
    TextView tvPressIcon;
    private static final String DIALOG_DATE = "DialogDate";
    int year, month, day;

    @Override
    public void onDatePickerSet(int y, int m, int d){ //DatePickerFragment 로부터 날짜를 받아온다.
        year = y;
        month = m;
        day = d;
        if (year != 0 && month != 0 && day != 0) {
            tvPressIcon.setText(year + "년 " + month + "월 " + day + "일");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_story);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);
        icCalendar = findViewById(R.id.ic_calendar);
        tvPressIcon = findViewById(R.id.tv_press_icon);
        icCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
