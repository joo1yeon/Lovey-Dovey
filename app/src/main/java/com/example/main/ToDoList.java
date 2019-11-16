package com.example.main;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToDoList extends AppCompatActivity {

    View addLayout, menuLayout;
    EditText contents;
    ImageView btnAdd, cancel, add, btnBack;
    TextView change, delete;
    ListView listView1, listView2;                                      //선택한거, 안한거
    ToDoList_ChoiceListAdapter adapter1, adapter2;                     //선택한거, 안한거
    Thread Renewal;


    //MyDBHelper todoDB;
    //SQLiteDatabase sqlDB;
    //Cursor cursor;
    //String strContent, strDate;

    int i1, i2;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    //TODO 투두리스트 화면 켜지기 전에 스레드 호출
    @Override
    protected void onStart() {
        super.onStart();
        Renewal = null;
        Renewal = new Item_renewal();
        Renewal.start();
    }

    //TODO 투두리스트 꺼지면 스레드 정지
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Renewal.interrupt();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        //todoDB = new MyDBHelper(this);          //헬퍼클래스 객체 생성

        //뒤로가기 버튼 인플레이트
        btnBack = findViewById(R.id.btnBack);

        //TODO 뒤로가기 버튼 클릭 시 투두화면 닫힘
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //리스트뷰 인플레이트
        listView1 = findViewById(R.id.list);
        listView2 = findViewById(R.id.checked_list);

        //Adapter 생성
        adapter1 = new ToDoList_ChoiceListAdapter();
        adapter2 = new ToDoList_ChoiceListAdapter();

        //Adapter 달기
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);


        //todolist 조회
        Item_show();




        //TODO# 체크되지 않은 투두리스트 클릭했을 때 (클릭제어)
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDoList_ListItem listItem = adapter1.listViewItems.get(i);
                String content = listItem.getContent();
                Item_Click(content,Date(),"true");
            }
        });



        //TODO# 체크하지 않은 리스트를 길게 클릭했을 때 (수정, 삭제)
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //final ListData mData = mAdapter1.listData.get(position);
                final ToDoList_ListItem listItem = adapter1.listViewItems.get(position);
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(ToDoList.this);
                final AlertDialog dl2 = dlg2.create();


                dl2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));//다이얼로그 배경 투명설정

                menuLayout = View.inflate(ToDoList.this, R.layout.list_menu, null);
                dl2.setView(menuLayout);
                dl2.show();

                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.copyFrom(dl2.getWindow().getAttributes());
                params.width = 550;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dl2.getWindow().setAttributes(params);


                change = menuLayout.findViewById(R.id.change);
                delete = menuLayout.findViewById(R.id.delete);

                //TODO# 수정버튼 클릭했을 때
                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dl2.dismiss();

                        AlertDialog.Builder dlg = new AlertDialog.Builder(ToDoList.this);
                        final AlertDialog dl = dlg.create();
                        addLayout = View.inflate(ToDoList.this, R.layout.list_add, null);
                        dl.setView(addLayout);
                        dl.show();

                        //다이얼로그 뷰들 인플레이트
                        add = addLayout.findViewById(R.id.add);
                        cancel = addLayout.findViewById(R.id.cancel);
                        contents = addLayout.findViewById(R.id.content);

                        contents.setText(listItem.getContent());        // 수정할 아이템의 내용을 EditText에 보여줌

                        //수정
                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text = contents.getText().toString();
                                Item_modify(listItem.getContent(),text);
                                dl.dismiss();
                            }
                        });

                        //취소
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dl.dismiss();
                            }
                        });

                    }
                });

                //TODO# 삭제버튼 클릭했을 때
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Item_Delete(listItem.getContent());
                        dl2.dismiss();
                    }
                });
                return true;
            }
        });


        //TODO# 체크된 투두리스트 클릭했을 때 (클릭제어)
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDoList_ListItem listItem = adapter2.listViewItems.get(i);
                String content = listItem.getContent();
                Item_Click(content,"","false");
            }
        });


        //추가버튼 인플레이트
        btnAdd = findViewById(R.id.btnAdd);

        //TODO# 투두리스트를 추가 버튼을 클릭했을 떄
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(ToDoList.this);
                final AlertDialog dl = dlg.create();
                addLayout = View.inflate(ToDoList.this, R.layout.list_add, null);
                dl.setView(addLayout);
                dl.show();

                //다이얼로그 뷰들 인플레이트
                add = addLayout.findViewById(R.id.add);
                cancel = addLayout.findViewById(R.id.cancel);
                contents = addLayout.findViewById(R.id.content);

                //추가
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = contents.getText().toString();
                        Item_add(MainActivity.coupleID,text);
                        dl.dismiss();

                    }
                });

                //취소
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dl.dismiss();
                    }
                });

            }
        });
    }

    //TODO# 현재 날짜 찍기
    public String Date(){
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");
        Date date = new Date();
        String date_ = format.format(date);
        return date_;
    }

    //TODO# To-Do-List 조회
    public void Item_show(){
        Call<List<ResponseTODO>> res = Net.getInstance().getApi().getInquiry(MainActivity.coupleID);
        res.enqueue(new Callback<List<ResponseTODO>>() {
            @Override
            public void onResponse(Call<List<ResponseTODO>> call, Response<List<ResponseTODO>> response) {
                if(response.isSuccessful()){

                    adapter1.clearItem();
                    adapter2.clearItem();
                    i1 = 0;
                    i2 = 0;

                    List<ResponseTODO> responseTodo = response.body();

                    for (ResponseTODO responseTodo_ : responseTodo){

                        if(false == Boolean.valueOf(responseTodo_.getChecked()).booleanValue()) {
                            Log.d("select","추가완료1"+responseTodo_.getContent_td());

                            adapter1.addItem(responseTodo_.getContent_td(), "");
                            listView1.setItemChecked(i1++,false);
                        } else {
                            Log.d("select","추가완료2"+ responseTodo_.getContent_td());

                            adapter2.addItem(responseTodo_.getContent_td(), responseTodo_.getDate_td());
                            listView2.setItemChecked(i2++,true);
                        }

                        adapter1.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();
                    }
                }
                else Log.d("select", "조회 통신 1 에러");
            }
            @Override
            public void onFailure(Call<List<ResponseTODO>> call, Throwable t) {
                Log.d("select", "조회 통신3 에러" + t.getMessage());
            }
        });

    }

    //TODO# To-Do-List 추가
    public void Item_add(int coupleID, String content){
        Log.d("INNN3","coupleID : "+coupleID);
        Call<ResponseTD_Insert> res = Net.getInstance().getApi().getTD_Add(coupleID,"", content,"false");
        res.enqueue(new Callback<ResponseTD_Insert>() {
            @Override
            public void onResponse(Call<ResponseTD_Insert> call, Response<ResponseTD_Insert> response) {
                if(response.isSuccessful()){
                if(response.body().getTDInsert()) {
                    Log.d("insert", "추가완료");
                    Item_show();
                }
                } else Log.d("insert", "추가 통신1 에러");
            }
            @Override
            public void onFailure(Call<ResponseTD_Insert> call, Throwable t) {
                Log.d("insert", "추가 통신3 에러" + t.getMessage());
            }
        });
    }

       //TODO# To-Do-List 수정
       public void Item_modify(String content1, String content2){
        final Call<ResponseTD_update> res = Net.getInstance().getApi().getTD_Modify(MainActivity.coupleID, content1, content2);
        res.enqueue(new Callback<ResponseTD_update>() {
            @Override
            public void onResponse(Call<ResponseTD_update> call, Response<ResponseTD_update> response) {
                if(response.isSuccessful()) {
                    if (response.body().getTDModify()) {
                        Log.d("update", "수정완료");
                        Item_show();
                    }
                }
                else Log.d("update", "수정 통신1 에러");
            }

            @Override
            public void onFailure(Call<ResponseTD_update> call, Throwable t) {
                Log.d("update", "수정 통신3 에러" + t.getMessage());
            }
        });

       }

    //TODO# To-Do-List 삭제
    public void Item_Delete(String Content){
        Call<ResponseTD_delete> res = Net.getInstance().getApi().getTD_Delt(MainActivity.coupleID, Content);
        res.enqueue(new Callback<ResponseTD_delete>() {
            @Override
            public void onResponse(Call<ResponseTD_delete> call, Response<ResponseTD_delete> response) {
                if(response.isSuccessful()){
                    if(response.body().getTDDelt()){
                        Log.d("delete","삭제완료");
                        Item_show();
                    }
                }
                else Log.d("delete", "삭제 통신1 에러");

            }

            @Override
            public void onFailure(Call<ResponseTD_delete> call, Throwable t) {
                Log.d("delete", "삭제 통신3 에러" + t.getMessage());
            }
        });
    }

    //TODO# To-Do-List 클릭했을 때 넘어가기(클릭제어)
    public void Item_Click(String Content,String Date,String bool){
        Call<ResponseTD_click> res = Net.getInstance().getApi().getTD_Click(MainActivity.coupleID, Content, Date, bool);
        res.enqueue(new Callback<ResponseTD_click>() {
            @Override
            public void onResponse(Call<ResponseTD_click> call, Response<ResponseTD_click> response) {
                if(response.isSuccessful()){
                    if(response.body().getTDClick()){
                        Log.d("click","클릭제어완료");
                        Item_show();
                    }
                }
                else Log.d("click","클릭 통신1 에러");
            }

            @Override
            public void onFailure(Call<ResponseTD_click> call, Throwable t) {
                Log.d("click", "클릭 통신3 에러");
            }
        });
    }

    public class Item_renewal extends Thread {

        boolean running = false;     //시작과 종료에 필요한 변수

        @Override
        public void run() {
            running = true;

            while (running) {                            //무한루프, Todolist 계속 돌아가게 함
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Item_show();
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    halt();
                    e.printStackTrace();
                    Log.e("TODO","스레드 멈춘거 맞아^^");
                }
            }
        }

        public void halt() {
            running = false;
        }

    }

    //TODO# 로컬디비 조회, 삭제, 수정, 클릭제어

    //To-Do-List 조회
      /* public void Item_show(ToDoList_ChoiceListAdapter adapter, String id, boolean check){
           sqlDB = todoDB.getReadableDatabase();

            cursor = sqlDB.rawQuery("SELECT * FROM to_do_list WHERE couple_id='"+id+"' AND checked = '"+check+"';",null);
            int count = cursor.getCount();


            for(int i=0;i<count;i++) {
                cursor.moveToNext();                                    //커서 넘기기

                strDate = cursor.getString(2);
                strContent = cursor.getString(3);
                adapter.addItem(strContent, strDate);

                //Check박스가 adapter1에서는 체크 X , adapter2 에서는 체크 O

                if(adapter == adapter1)
                    listView1.setItemChecked(i,false);
                else if(adapter == adapter2) {
                    listView2.setItemChecked(i, true);
                }
                }

            cursor.close();
            sqlDB.close();
       }*/

    //To-Do-List 추가
       /*public void Item_add(ToDoList_ChoiceListAdapter adapter, String id, String content){
           try{
               sqlDB = todoDB.getWritableDatabase();
               cursor = sqlDB.rawQuery("SELECT * FROM to_do_list WHERE couple_id='"+id+"';",null);
               int count = cursor.getCount();
               sqlDB.execSQL("INSERT INTO to_do_list VALUES (" + ++count + ",'" + id + "','','" + content + "','" + false + "');");
           }catch (Exception e){ }

           cursor.close();
           sqlDB.close();
       }*/

    //To-Do-List 수정
       /*public void Item_modify(ToDoList_ChoiceListAdapter adapter,String id, String content, String content_){
        sqlDB = todoDB.getWritableDatabase();
        sqlDB.execSQL("update to_do_list set content='" + content + "' where couple_id='" + id + "' and content='"+ content_ +"';");
        adapter.notifyDataSetChanged();
        sqlDB.close();

    }*/

    //To-Do-List 클릭했을 때 넘어가기
    /*public void Item_Click(ToDoList_ChoiceListAdapter adapter,String id, boolean b, String content, int i){
        sqlDB = todoDB.getWritableDatabase();
        String date;
        if(b == true)
            date = Date();
        else
            date = "";
        sqlDB.execSQL("update to_do_list set checked='" + b + "', date_check='"+date+"' where couple_id='" + id + "' and content='"+ content +"';");
        adapter.remove(i);
        adapter.notifyDataSetChanged();
        sqlDB.close();

    }*/

    /*//To-Do-List 삭제
       public void Item_Delete(ToDoList_ChoiceListAdapter adapter,String id, String content){
           sqlDB = todoDB.getWritableDatabase();
           sqlDB.execSQL("DELETE FROM to_do_list WHERE content='" + content + "' AND couple_id = '"+id+"';");
           adapter.notifyDataSetChanged();
           sqlDB.close();
       }*/

}
