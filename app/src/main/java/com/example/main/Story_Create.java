package com.example.main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Story_Create extends AppCompatActivity {
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DATE);

    Button btnNext,btnCancel;
    ImageView icCalendar, icSelectMainImg, ivStoryMainImg;
    EditText etStoryTitle, etWriteText;
    TextView tvPressIcon;
    public Story mStory;
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_CODE = 10;
    //private static final int REQUEST_DATE = 0; // DatePicker 에서 데이터 반환하기 위해 요청 코드 상수 정의
    DbOpenHelper mDbOpenHelper;
    Uri mUri;
    String mTitle, story_id, contents, imgPath;
    String imgFileLocation = "";

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_create);

        btnCancel=findViewById(R.id.btn_cancel);
        btnNext = findViewById(R.id.btn_next);
        icCalendar = findViewById(R.id.ic_calendar);
        icSelectMainImg = findViewById(R.id.ic_select_main_img);
        ivStoryMainImg = findViewById(R.id.story_main_img);
        etStoryTitle = findViewById(R.id.et_story_title);
        etWriteText = findViewById(R.id.et_write_text);
        tvPressIcon = findViewById(R.id.tv_press_icon);

        icCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dateDialog = new DatePickerDialog(Story_Create.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvPressIcon.setText(year + "년 " + (month+1) + "월 " + dayOfMonth + "일");
                    }
                }, year, month, day);
                dateDialog.show();

//                FragmentManager manager = getSupportFragmentManager();
//                DatePickerFragment dialog = new DatePickerFragment();
//                dialog.show(manager, DIALOG_DATE); //DialogFragment 를 화면에 보여주기 위해 FragmentManager가 onCreateDialog 호출
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbOpenHelper = new DbOpenHelper(getApplicationContext());
                mDbOpenHelper.open();
                mDbOpenHelper.create();
                mDbOpenHelper.deleteAllColumns();
                mDbOpenHelper.insertColumn(etStoryTitle.getText().toString(), year, month, day);
                Log.d("test", "DB에 저장됨/삭제됨");
                Story story = new Story();
                mTitle = etStoryTitle.getText().toString();
                story.setTitle(mTitle);
                story.setYear(year);
                story.setMonth(month);
                story.setDay(day);
                story.setContents_text(etWriteText.getText().toString());
                story.setMainImg(mUri);
                story_id = story.getId().toString();
                contents = etWriteText.getText().toString();
                Album_singleton.get(getApplicationContext()).addStory(story);
                mDbOpenHelper.close();
//                Intent intent = new Intent(Story_Create.this, Story_EditContents.class); //스토리 수정 화면으로 이동
//                startActivity(intent);
//                uploadFile(); //서버에 이미지 업로드
                saveStoryData(); //서버에 story data 저장
                finish();
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


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Story_Create.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {

                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    ivStoryMainImg.setImageBitmap(img);

//                    InputStream in = getContentResolver().openInputStream(data.getData());
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
//
//                    ivStoryMainImg.setImageBitmap(img);
                    Uri uri = data.getData();
                    Glide.with(this).load(uri).into(ivStoryMainImg);
                    Log.d("test", "파일 경로" + uri.getPath());
                    mUri = uri;
                    imgPath = uri.getPath();
                    uploadFile(); //서버에 이미지 업로드


                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //TODO 서버에 이미지 저장하기
    void uploadFile() {
        if (imgPath == null || imgPath.equals("")) { //선택된 이미지가 없는 경우
            Toast.makeText(this, "이미지를 선택하세요", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //showpDialog();

            Map<String, RequestBody> map = new HashMap<>();
            File file = new File(imgPath);


            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file); //File 형태로 변환(parsing)
            map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
            API getResponse = Net.getInstance().getApi();
            Call<ResponseImgUpload> call = getResponse.upload("token", map);
            call.enqueue(new Callback<ResponseImgUpload>() {
                @Override
                public void onResponse(Call<ResponseImgUpload> call, Response<ResponseImgUpload> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            //hidepDialog();
                            ResponseImgUpload responseImgUpload = response.body();
                            Log.d("test", "서버연동4");
                            Toast.makeText(Story_Create.this, "호롤로"+ responseImgUpload.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("test", "서버연동6");
                        }
                    } else {
                        //hidepDialog();
                        Log.d("test", "서버연동5");
                        Toast.makeText(Story_Create.this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseImgUpload> call, Throwable t) {
                    //hidepDialog();
                    Log.d("test", t.getMessage());
                }
            });
        }
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        else {
            return false;
        }
    }

    //TODO 서버에 story data 저장하기
    public void saveStoryData() { //서버에 저장은 되는데 통신3에러가 뜬다.
        Call<ResponseSaveStory> res = Net.getInstance().getApi().setStoryData(story_id, MainActivity.id, year, month, day, mTitle, imgPath, contents);
        res.enqueue(new Callback<ResponseSaveStory>() {
            @Override
            public void onResponse(Call<ResponseSaveStory> call, Response<ResponseSaveStory> response) {
                if (response.isSuccessful()) {
                    if (response.body().setStoryData()) {
                        Toast.makeText(Story_Create.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else Toast.makeText(Story_Create.this,"통신1 에러",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseSaveStory> call, Throwable t) {
                Toast.makeText(Story_Create.this,"통신3 에러",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
