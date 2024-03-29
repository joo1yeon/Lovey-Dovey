package com.example.main;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class Main extends Fragment {

    String id;

    static int REQUEST_CODE = 1;
    private Context context;

    static ImageView profile_Btn1;
    static ImageView profile_Btn2, storage, close, profile_img;
    TextView date, textView;
    View profileLayout1, profileLayout2;
    ArrayList<String> todo = new ArrayList<String>();
    TextSwitcher to_do_Btn;
    Thread todoThread;
    EditText email, name;
    String path;
    static ImageView img_ground;

    //sqlite 관련 변수
    MyDBHelper mainDB;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    int i;

    static Uri uri_ = Uri.parse("android.resource://com.example.main/drawable/basic");
    Bitmap bitmap;
    String url="android.resource://com.example.main/drawable/basic";
    //화면 보여주기 전에 todolist content가 담긴 ArrayList 삭제 및 초기화 후 추가
    @Override
    public void onStart() {
        super.onStart();
        Item_Content();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };



    @SuppressLint("ValidFragment")
    public Main() {

    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_main, container, false);

        id = MainActivity.id;
        mainDB = new MyDBHelper(getContext());          //헬퍼클래스 객체 생성
        context = getContext();

        //인플레이트
        to_do_Btn = layout.findViewById(R.id.to_do_Btn);                            //투두리스트 버튼, To-do-list 보여주기
        date = layout.findViewById(R.id.date);
        img_ground = layout.findViewById(R.id.img_ground);

        sqlDB = mainDB.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("select path from back where id='" + id + "';", null);
        while (cursor.moveToNext()) {
            path = cursor.getString(0);
        }
        if (path != null) {
            Glide.with(this).load(path).into(img_ground);
        } else {
            Glide.with(this).load(R.drawable.ground).into(img_ground);
        }

        //메인화면 사귄날짜
        profile_Btn1 = layout.findViewById(R.id.profile_Btn1);                      //프로필사진1(나) 버튼
        profile_Btn2 = layout.findViewById(R.id.profile_Btn2);                      //프로필사진2(상대방) 버튼

        //profile1 저장된 사진 보여주기
        Glide.with(context)
                .load(uri_)
                .centerCrop()
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .override(70, 70)
                .into(profile_Btn1);

        //텍스트 바꾸기
        to_do_Btn.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView = new TextView(getContext());
                textView.setTextColor(Color.parseColor("#FFFFFF"));

                //폰트설정
                Typeface typeFace = ResourcesCompat.getFont(getContext(), R.font.netmarble_b);
                textView.setTypeface(typeFace);
                textView.setTextSize(15);   //폰트사이즈 설정
                return textView;
            }
        });

        //애니메이션 객체 생성 및 액션 정보 로딩
        Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.up_down);
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.down_up);

        //애니메이션 할당
        to_do_Btn.setInAnimation(in);
        to_do_Btn.setOutAnimation(out);

        //Date 날짜 계산 함수
        DateSystem();


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseDate> res = Net.getInstance().getApi().getDate(MainActivity.id);
                res.enqueue(new Callback<ResponseDate>() {
                    @Override
                    public void onResponse(Call<ResponseDate> call, Response<ResponseDate> response) {
                        if (response.isSuccessful()) {
                            String date = response.body().getDate_m();
                            String d[]=date.split("-");
                            DatePickerDialog dateDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    final int y=year,m=month+1,d=dayOfMonth;
                                    Call<ResponseUpdateDate> res= Net.getInstance().getApi().getUpdate(id,y+"-"+m+"-"+d);
                                    res.enqueue(new Callback<ResponseUpdateDate>() {
                                        @Override
                                        public void onResponse(Call<ResponseUpdateDate> call, Response<ResponseUpdateDate> response) {
                                            if(response.body().getUpdate()){
                                                doDateSystem(y+"-"+m+"-"+d);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseUpdateDate> call, Throwable t) {

                                        }
                                    });
                                }
                            }, Integer.parseInt(d[0]), Integer.parseInt(d[1])-1, Integer.parseInt(d[2]));
                            dateDialog.show();

                        } else Log.d("test", "사귄날짜 통신1 에러");
                    }

                    @Override
                    public void onFailure(Call<ResponseDate> call, Throwable t) {
                        Log.d("test", "사귄날짜 통신3 에러");
                    }
                });


            }
        });

        //TODO to_do_list 버튼 눌렀을 때 --> to_do 화면 전환
        to_do_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ToDoList.class);                                   //인텐트 선언 및 생성
                startActivity(intent);
            }

        });


        //TODO 왼쪽 프로필을 누를 때 -->  정보 변경 가능한 다이얼로그 창
        profile_Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());                            //Main 화면에 다이얼로그 생성
                final AlertDialog dl = dlg.create();                                                        //빌더를 이용해 AlertDialog 객체 생성
                profileLayout1 = View.inflate(getContext(), R.layout.profile1, null);                 //레이아웃뷰인 profileLayout을 메인에 profile.xml 사용해서 인플레이트
                dl.setView(profileLayout1);                                                                 //porofileLayout을 AlertDialog 객체로 보여줌
                dl.show();                                                                                  //다이얼로그 보여줌

                //메인화면 다이얼로그에 들어가는 profile1의 뷰들 인플레이트
                storage = profileLayout1.findViewById(R.id.storage);
                profile_img = profileLayout1.findViewById(R.id.profile_img);
                email = profileLayout1.findViewById(R.id.et_email);
                name = profileLayout1.findViewById(R.id.name);

                name.setText(MainActivity.nickname);
                email.setText(MainActivity.email);

                Log.d("PPPPP", name.getText().toString());

                //입력 유형 이메일로 설정
                email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                //저장된 값 보여주기
                Glide.with(context)
                        .load(uri_)
                        .centerCrop()
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(context))
                        .override(70, 70)
                        .into(profile_img);


                profile_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "사진 선택"), REQUEST_CODE);
                    }
                });

                //TODO 저장을 버튼을 클릭했을 때 수정된 내용을 저장한 후 다이얼로그 종료
                storage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //profile_UpLoad();

                        //메인화면 프로필 변경
                        Glide.with(context)
                                .load(uri_)
                                .centerCrop()
                                .crossFade()
                                .bitmapTransform(new CropCircleTransformation(context))
                                .override(70, 70)
                                .into(profile_Btn1);

                        //EditText에 변경한 값 받아오기
                        Call<ResponseInfoUpdate> res = Net.getInstance().getApi().getInfoUpdate(MainActivity.id, name.getText().toString(), email.getText().toString());
                        res.enqueue(new Callback<ResponseInfoUpdate>() {
                            @Override
                            public void onResponse(Call<ResponseInfoUpdate> call, Response<ResponseInfoUpdate> response) {
                                if (response.body().getUpdate()) {
                                    Toast.makeText(getContext(), "정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                    MainActivity.email = email.getText().toString();
                                    MainActivity.nickname = name.getText().toString();
                                    dl.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "정보가 저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseInfoUpdate> call, Throwable t) {

                            }
                        });
                    }
                });

            }
        });


        //TODO 오른쪽 프로필을 누를 때 -->  정보 변경 불가능한 다이얼로그 창
        profile_Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());                         //Main 화면에 다이얼로그 생성
                final AlertDialog dl2 = dlg.create();                                                    //빌더를 이용해 AlertDialog 객체 생성
                profileLayout2 = View.inflate(getContext(), R.layout.profile2, null);              //레이아웃뷰인 profileLayout2을 메인에 profile2.xml 사용해서 인플레이트
                dl2.setView(profileLayout2);                                                             //porofileLayout2을 AlertDialog 객체로 보여줌
                dl2.show();                                                                              //다이얼로그 보여줌

                //메인화면 다이얼로그에 들어가는 profile2의 뷰들 인플레이트
                close = profileLayout2.findViewById(R.id.close);
                final TextView email = profileLayout2.findViewById(R.id.oppo_email);
                final TextView name = profileLayout2.findViewById(R.id.oppo_name);

                //저장된 값 보여주기
                Call<ResponseProfile> res= Net.getInstance().getApi().getProfile(MainActivity.id);
                res.enqueue(new Callback<ResponseProfile>() {
                    @Override
                    public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                        if(response.isSuccessful()){
                            email.setText(response.body().getEmail());
                            name.setText(response.body().getName());
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseProfile> call, Throwable t) {

                    }
                });
                //닫기 버튼 클릭했을 때 다이얼로그 종료
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dl2.dismiss();
                    }
                });
            }
        });

        return layout;
    }


    //TODO 스레드 오류 해결
   @Override
   public void setUserVisibleHint(boolean isVisibleToUser) {
           if (isVisibleToUser) {
               //스레드 객체 생성 및 시작
               todoThread = null;
               todoThread = new TodoThread();
               todoThread.start();
               Log.e("screen", "현재화면");
           } else {
               try {
                   Log.e("screen", "다른화면");
                   todoThread.interrupt();             //스레드 멈추기
                   Log.e("screen", "스레드 정지");
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       }


       //TODO Data 날짜 계산 함수
       public void doDateSystem (String start){

           try {
               SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");        //SimpleDataFormat 형태의 변수를 년-월-일로 생성
               Date beginDate = formatter.parse(start);                                        //사귄날짜를 입력받은 문자열을 date 형식으로 변경
               String end = formatter.format(new Date());                                      //현재날짜를 문자열로 받아옴
               Date endDate = formatter.parse(end);                                            //현재날짜를 받아온 문자열을 date 형식으로 변경

               // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
               long diff = endDate.getTime() - beginDate.getTime();
               long coupleDays = diff / (24 * 60 * 60 * 1000) + 1;

               //사귄 날짜가 1000일이 넘으면 textSize 변경
               if (coupleDays > 1000) {
                   date.setTextSize(30);
               }
               date.setText(coupleDays + " 일");     //사귄날짜 + 일 출력


           } catch (ParseException e) {
               e.printStackTrace();
           }
       }

       //TODO 사귄날짜 가져오기
       public void DateSystem () {
           Call<ResponseDate> res = Net.getInstance().getApi().getDate(MainActivity.id);
           res.enqueue(new Callback<ResponseDate>() {
               @Override
               public void onResponse(Call<ResponseDate> call, Response<ResponseDate> response) {
                   if (response.isSuccessful()) {
                       Log.d("test", "사귄날짜 계산 성공");
                       ResponseDate responseDate = response.body();
                       doDateSystem(responseDate.getDate_m());
                   } else Log.d("test", "사귄날짜 통신1 에러");
               }

               @Override
               public void onFailure(Call<ResponseDate> call, Throwable t) {
                   Log.d("test", "사귄날짜 통신3 에러");
               }
           });

       }

       //TODO ToDoList Check false인 내용 순서대로 삽입
       public void Item_Content () {
           i = 0;
           todo.clear();

           Call<List<ResponseTODO>> res = Net.getInstance().getApi().getInquiry(MainActivity.coupleID);
           res.enqueue(new Callback<List<ResponseTODO>>() {
               @Override
               public void onResponse(Call<List<ResponseTODO>> call, Response<List<ResponseTODO>> response) {
                   if (response.isSuccessful()) {
                       List<ResponseTODO> responseTodo = response.body();
                       for (ResponseTODO responseTodo_ : responseTodo) {
                           if (false == Boolean.valueOf(responseTodo_.getChecked()).booleanValue()) {
                               todo.add(responseTodo_.getContent_td());
                               i++;
                           }
                       }
                   } else Log.d("Todo", "Todo 내용 통신1 에러");
               }

               @Override
               public void onFailure(Call<List<ResponseTODO>> call, Throwable t) {
                   Log.d("Todo", "Todo 내용 통신3 에러" + t.getMessage());
               }
           });
       }


       //TODO TextSwitcher 스레드
       public class TodoThread extends Thread {
           boolean running = false;     //시작과 종료에 필요한 변수
           int index = 0;

           @Override
           public void run() {
               running = true;

               while (running) {                            //무한루프, Todolist 계속 돌아가게 함
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           if (todo.isEmpty()) {              //todoArrayList 배열에 아무것도 들어있지 않을 때
                               to_do_Btn.setText("•  TODO_LIST에 내용을 입력해주세요");
                           } else {
                               to_do_Btn.setText("•  " + todo.get(index++));
                               to_do_Btn.invalidate();
                           }
                       }
                   }, 1200);

                   try {

                       Thread.sleep(3000);
                   } catch (InterruptedException e) {
                       halt();
                       e.printStackTrace();
                   }
                   if (index >= todo.size()) {   //String 배열 때length
                       index = 0;
                   }

               }
           }

           public void halt() {
               running = false;
           }
       }

       //TODO 앨범들어가서 사진 크롭하기
       @Override
       public void onActivityResult ( int requestCode, int resultCode, Intent data){
           super.onActivityResult(requestCode, resultCode, data);
           if (requestCode == REQUEST_CODE) {
               if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

                   try {
                       Uri uri = data.getData();

                       profile_img = profileLayout1.findViewById(R.id.profile_img);
                       Glide.with(context)
                               .load(uri)
                               .centerCrop()
                               .crossFade()
                               .bitmapTransform(new CropCircleTransformation(context))
                               .override(70, 70)
                               .into(profile_img);

                       Log.e("uri",uri.toString());
                       uri_=uri;
                       //uri_ = Uri.parse("file://" + data.getDataString());
                       //bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                       Log.e("uri",uri_.toString());

                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               } else if (resultCode == Activity.RESULT_CANCELED) {
                   Toast.makeText(getContext(), "사진 선택 취소", Toast.LENGTH_SHORT).show();
               }
           }
           }


    //TODO 이미지 업로드 하기
    public void profile_UpLoad(){
        //Map<String, RequestBody> map = new HashMap<>();
//"file://" + getRealPathFromURI(uri_)
        File file = new File(getRealPathFromURI(uri_));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
        MultipartBody.Part upLoad = MultipartBody.Part.createFormData("uploadfile", file.getName(),requestBody);
        Log.e("profile", "되라얍"+ file);
        Log.e("profile", "되라얍"+ requestBody.toString());
        Log.e("profile", "되라얍"+ upLoad.toString());

        Call<ResponseProfile_m> res = Net.getInstance().getApi().getLoad(upLoad);
        res.enqueue(new Callback<ResponseProfile_m>() {
            @Override
            public void onResponse(Call<ResponseProfile_m> call, Response<ResponseProfile_m> response) {
                if(response.isSuccessful()){
                    Log.e("profile", "성공!");
                    if(response.body().getmProfile()) {
                        Toast.makeText(getContext(), "사진전송!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Log.d("profile", "프로필 내용 통신2 에러");
            }

            @Override
            public void onFailure(Call<ResponseProfile_m> call, Throwable t) {
                Log.d("profile", "프로필 통신3 에러" + t.getMessage());
            }
        });

    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

}
