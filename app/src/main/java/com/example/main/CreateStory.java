package com.example.main;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateStory extends AppCompatActivity {

    Button btnConfirm,btnCancel;
    ImageView icCalendar;
    EditText etStoryTitle;
    TextView tvPressIcon;
    public Story mStory;
    private static final String DIALOG_DATE = "DialogDate";
    DbOpenHelper mDbOpenHelper;
    int year, month, day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_story);

        btnCancel=findViewById(R.id.btn_cancel);
        btnConfirm = findViewById(R.id.btn_confirm);
        icCalendar = findViewById(R.id.ic_calendar);
        etStoryTitle = findViewById(R.id.et_story_title);
        tvPressIcon = findViewById(R.id.tv_press_icon);

        Intent intent = getIntent();
        year = intent.getIntExtra("Year", 0);
        month = intent.getIntExtra("Month", 0);
        day = intent.getIntExtra("Day", 0);

        icCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);

                //Log.d("test", "받아온 값" + String.valueOf(year));
            }
        });

        Log.d("test", "캘린더 닫혔을 때 year 값" + String.valueOf(year));
        if (year != 0 && month != 0 && day != 0) {
            tvPressIcon.setText(year + "년 " + month + "월 " + day + "일");
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbOpenHelper = new DbOpenHelper(getApplicationContext());
                mDbOpenHelper.open();
                mDbOpenHelper.create();
                mDbOpenHelper.insertColumn(etStoryTitle.getText().toString(), year, month, day);
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
