package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Story_Search extends AppCompatActivity implements DatePickerFragment.OnDatePickerSetListener {

    ImageView icCalendar;
    Button btnConfirm, btnCancel;
    TextView tvPressIcon;
    EditText etTitleSearch;
    RadioGroup mRadioGroup;
    RadioButton rdo_user1, rdo_user2;
    private static final String DIALOG_DATE = "DialogDate";
    int year, month, day;
    String storyTitle, storyWriter;

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
        setContentView(R.layout.story_search);
        etTitleSearch = findViewById(R.id.et_title_search);
        icCalendar = findViewById(R.id.ic_calendar);
        tvPressIcon = findViewById(R.id.tv_press_icon);
//        mRadioGroup = findViewById(R.id.rGroup1);
//        rdo_user1 = findViewById(R.id.radio1);
//        rdo_user2 = findViewById(R.id.radio2);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);

//        rdo_user1.setText(MainActivity.nickname);

//        if (storyTitle.equals("")) storyTitle;

        Log.d("test","1" + storyTitle );
        Log.d("test","2" + storyWriter );

//        mRadioGroup.check(R.id.radio1);
//        rdo_user1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                storyWriter = MainActivity.id;
//                Toast.makeText(Story_Search.this, MainActivity.nickname + "선택", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        rdo_user2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Toast.makeText(Story_Search.this, "사용자2 선택", Toast.LENGTH_SHORT).show();
//            }
//        });


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
                storyTitle = etTitleSearch.getText().toString();
                if (year == 0 || month == 0 || day == 0) {
                    Toast.makeText(Story_Search.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else if (storyTitle.equals("")) {
                    Toast.makeText(Story_Search.this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Story_Search.this, Story_SearchResult.class);
                    intent.putExtra("year", year);
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    intent.putExtra("storyTitle", storyTitle);
                    Log.d("test", "인텐트로 보내는 제목:" + storyTitle);
                    startActivity(intent);
                    finish();
                }
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
