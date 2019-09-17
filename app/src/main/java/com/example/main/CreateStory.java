package com.example.main;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateStory extends AppCompatActivity implements DatePickerFragment.OnDatePickerSetListener {

    Button btnNext,btnCancel;
    ImageView icCalendar, icSelectMainImg;
    EditText etStoryTitle;
    TextView tvPressIcon;
    public Story mStory;
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0; // DatePicker 에서 데이터 반환하기 위해 요청 코드 상수 정의
    DbOpenHelper mDbOpenHelper;
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
        setContentView(R.layout.create_story);

        btnCancel=findViewById(R.id.btn_cancel);
        btnNext = findViewById(R.id.btn_confirm);
        icCalendar = findViewById(R.id.ic_calendar);
        icSelectMainImg = findViewById(R.id.ic_select_main_img);
        etStoryTitle = findViewById(R.id.et_story_title);
        tvPressIcon = findViewById(R.id.tv_press_icon);

        icCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE); //DialogFragment 를 화면에 보여주기 위해 FragmentManager가 onCreateDialog 호출
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbOpenHelper = new DbOpenHelper(getApplicationContext());
                mDbOpenHelper.open();
                mDbOpenHelper.create();
                mDbOpenHelper.deleteAllColumns();
                //mDbOpenHelper.insertColumn(etStoryTitle.getText().toString(), year, month, day);
                Log.d("test", "DB에 저장됨/삭제됨");
                Story story = new Story();
                story.setTitle(etStoryTitle.getText().toString());
                story.setYear(year);
                story.setMonth(month);
                story.setDay(day);
                Album_singleton.get(getApplicationContext()).addStory(story);
                mDbOpenHelper.close();
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
