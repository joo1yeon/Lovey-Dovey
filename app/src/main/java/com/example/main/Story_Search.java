package com.example.main;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Story_Search extends AppCompatActivity implements DatePickerFragment.OnDatePickerSetListener {

    ImageView icCalendar;
    Button btnConfirm, btnCancel;
    ImageButton btnBack;
    TextView tvPressIcon;
    EditText etTitleSearch;
    RadioGroup mRadioGroup;
    RadioButton rdo_user1, rdo_user2;
    private static final String DIALOG_DATE = "DialogDate";
    int year, month, day, tag;
    String storyTitle, storyWriter;
    static Activity mActivity;

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
        btnBack = findViewById(R.id.btn_back);

        mActivity = Story_Search.this;

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
                if (year == 0 && month == 0 && day == 0 && storyTitle.equals("")) {
                    Toast.makeText(Story_Search.this, "입력된 항목이 없습니다.", Toast.LENGTH_SHORT).show();
                }else if (year == 0 || month == 0 || day == 0) {
                    tag = 0;
                    Intent intent = new Intent(Story_Search.this, Story_SearchResult.class);
                    intent.putExtra("tag", tag);
                    intent.putExtra("storyTitle", storyTitle);
                    startActivity(intent);
                }else if (storyTitle.equals("")) {
                    tag = 1;
                    Intent intent = new Intent(Story_Search.this, Story_SearchResult.class);
                    intent.putExtra("tag", tag);
                    intent.putExtra("year", year);
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    startActivity(intent);
                }else {
                    tag = 2;
                    Intent intent = new Intent(Story_Search.this, Story_SearchResult.class);
                    intent.putExtra("tag", tag);
                    intent.putExtra("year", year);
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    intent.putExtra("storyTitle", storyTitle);
                    Log.d("test", "인텐트로 보내는 제목:" + storyTitle);
                    startActivity(intent);
//                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Story_Search.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
