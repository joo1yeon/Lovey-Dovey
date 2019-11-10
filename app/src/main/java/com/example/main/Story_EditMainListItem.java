package com.example.main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Story_EditMainListItem extends AppCompatActivity {
    TextView topTextView, tvPressIcon;
    Button btnCancel, btnConfirm;
    ImageView icCalendar, icSelectMainImg, ivStoryMainImg;
    EditText etStoryTitle, etWriteText;
    int year, month, day, story_index;
    Uri mUri;
    String story_id, mTitle, imgPath, contents;
//    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_CODE = 10; //갤러리 연동 요청 상수

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_create);

        topTextView = findViewById(R.id.topPanel);
        tvPressIcon = findViewById(R.id.tv_press_icon);
        btnCancel = findViewById(R.id.btn_cancel);
        btnConfirm = findViewById(R.id.btn_next);
        icCalendar = findViewById(R.id.ic_calendar);
        icSelectMainImg = findViewById(R.id.ic_select_main_img);
        etStoryTitle = findViewById(R.id.et_story_title);
        etWriteText = findViewById(R.id.et_write_text);
        ivStoryMainImg = findViewById(R.id.story_main_img);

        topTextView.setText("스토리 수정");

        Intent intent = getIntent();
        year = intent.getIntExtra("year", 0);
        month = intent.getIntExtra("month", 0);
        day = intent.getIntExtra("day", 0);
        mTitle = intent.getStringExtra("title");
        contents = intent.getStringExtra("contents");
        story_index = intent.getIntExtra("position", 0);
        story_id = intent.getStringExtra("story_id");

        etStoryTitle.setText(mTitle);
        tvPressIcon.setText(year + "년 " + month + "월 " + day + "일");
        etWriteText.setText(contents);

        Uri uri = Uri.parse(intent.getStringExtra("imgpath"));
        Glide.with(this).load(uri).into(ivStoryMainImg);
        Log.d("test", "파일 경로" + uri.getPath());
        mUri = uri;
        imgPath = uri.getPath();

        icCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dateDialog = new DatePickerDialog(Story_EditMainListItem.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int _year, int _month, int _dayOfMonth) {
                        tvPressIcon.setText(_year + "년 " + (_month+1) + "월 " + _dayOfMonth + "일");
                        year = _year; month = _month + 1; day = _dayOfMonth;
                    }
                }, year, month, day);
                dateDialog.show();
//                FragmentManager manager = getSupportFragmentManager();
//                DatePickerFragment dialog = new DatePickerFragment();
//                dialog.show(manager, DIALOG_DATE); //DialogFragment 를 화면에 보여주기 위해 FragmentManager가 onCreateDialog 호출
            }
        });

        icSelectMainImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnConfirm.setText("확인");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Story_EditMainListItem.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Album_singleton album_singleton = Album_singleton.get(Story_EditMainListItem.this);
                List<Story> stories = album_singleton.getStories();

                deleteStory_server(); //서버에서 story 삭제
                stories.remove(story_index);
                mTitle = etStoryTitle.getText().toString();
                contents = etWriteText.getText().toString();
                saveStoryData();
                editStory_singleton();
                finish();
            }
        });
    }

//    @Override
//    public void onDatePickerSet(int y, int m, int d){ //DatePickerFragment 로부터 날짜를 받아온다.
//        year = y;
//        month = m;
//        day = d;
//        if (year != 0 && month != 0 && day != 0) {
//            tvPressIcon.setText(year + "년 " + month + "월 " + day + "일");
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {

//                    InputStream in = getContentResolver().openInputStream(data.getData());
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
//                    ivStoryMainImg.setImageBitmap(img);

                    Uri uri = data.getData();
                    Glide.with(this).load(uri).into(ivStoryMainImg);
                    Log.d("test", "파일 경로" + uri.getPath());
                    mUri = uri;
                    imgPath = uri.getPath();
//                    uploadFile(); //서버에 이미지 업로드


                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //TODO 서버에 story data 저장하기
    public void saveStoryData() {
        Call<ResponseServer_Story> res = Net.getInstance().getApi().setStoryData(story_id, String.valueOf(MainActivity.coupleID), year, month, day, mTitle, imgPath, contents);
        res.enqueue(new Callback<ResponseServer_Story>() {
            @Override
            public void onResponse(Call<ResponseServer_Story> call, Response<ResponseServer_Story> response) {
                if (response.isSuccessful()) {
                    ResponseServer_Story responseGet = response.body();
                    if (responseGet.setStoryData() == true ) {
                        Toast.makeText(Story_EditMainListItem.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else Toast.makeText(Story_EditMainListItem.this,"서버 연결 ㅇㅇ 저장은 ㄴㄴ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseServer_Story> call, Throwable t) {
                Toast.makeText(Story_EditMainListItem.this,"통신 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO 서버에서 스토리 삭제
    void deleteStory_server() {
        Call<ResponseServer_Story> res = Net.getInstance().getApi().deleteStoryData(story_id);
        res.enqueue(new Callback<ResponseServer_Story>() {
            @Override
            public void onResponse(Call<ResponseServer_Story> call, Response<ResponseServer_Story> response) {
                if (response.isSuccessful()) {
                    ResponseServer_Story responseGet = response.body();
                    if (responseGet.deleteStoryData() ) {
//                        Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else Toast.makeText(Story_EditMainListItem.this,"통신1 에러",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseServer_Story> call, Throwable t) {
                Toast.makeText(Story_EditMainListItem.this,"통신 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editStory_singleton() {
        Story story = new Story();
        mTitle = etStoryTitle.getText().toString();
        story.setTitle(mTitle);
        story.setWriter(String.valueOf(MainActivity.coupleID));
        story.setYear(year);
        story.setMonth(month);
        story.setDay(day);
        story.setContents_text(etWriteText.getText().toString());
        story.setMainImg(mUri);
        story_id = story.getId();
        contents = etWriteText.getText().toString();
//        Album_singleton.get(getApplicationContext()).addStory(story);
    }
}
