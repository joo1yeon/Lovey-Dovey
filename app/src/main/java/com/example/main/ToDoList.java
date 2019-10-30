package com.example.main;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

    MyDBHelper todoDB;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    String strContent, strDate;

    int i1, i2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        todoDB = new MyDBHelper(this);          //헬퍼클래스 객체 생성

        //뒤로가기 버튼 인플레이트
        btnBack = findViewById(R.id.btnBack);

        //뒤로가기 버튼 클릭 시 투두화면 닫힘
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

        //TODO 보충 : 전부 다 체크해제 상태여야 함, 체크했을 때 아래꺼까지 체크 안되는 방법
        //체크되지 않은 투두리스트 클릭했을 때
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDoList_ListItem listItem = adapter1.listViewItems.get(i);
                String content = listItem.getContent();

                /*Item_Click(adapter1,strCoupleID,true, content,i);
                adapter1.clearItem();
                Item_show(adapter1, strCoupleID,false);
                adapter2.clearItem();
                Item_show(adapter2, strCoupleID,true);
                adapter2.notifyDataSetChanged();*/

            }
        });



        //체크하지 않은 리스트를 길게 클릭했을 때
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

                //수정버튼 클릭했을 때
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

                        //TODO 데이터 베이스에서 가져온 내용 수정하는 내용 에디트텍스트에 출력
                        contents.setText(listItem.getContent());        // 수정할 아이템의 내용을 EditText에 보여줌

                        //수정
                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text = contents.getText().toString();

                                Item_modify(listItem.getContent(),text);
                                Item_show();

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

                //삭제버튼 클릭했을 때
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO DB todoid 값 넘기는법... 삭제 후 조회가 안됨.. 왜징..
                        Item_Delete(listItem.getContent());
                        Item_show();

                        dl2.dismiss();
                    }
                });
                return true;
            }
        });


        //TODO 보충 : 전부 다 체크 상태여야 함, 체크했을 때 아래꺼까지 체크 안되는 방법
        //체크된 투두리스트 클릭했을 때
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDoList_ListItem listItem = adapter2.listViewItems.get(i);
                String content = listItem.getContent();

                /*Item_Click(adapter2,strCoupleID,false,content,i);
                adapter2.clearItem();
                Item_show(adapter2, strCoupleID,true);
                adapter1.clearItem();
                Item_show(adapter1, strCoupleID,false);
                adapter1.notifyDataSetChanged();*/

            }
        });


        //추가버튼 인플레이트
        btnAdd = findViewById(R.id.btnAdd);

        //투두리스트를 추가 버튼을 클릭했을 떄
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

                        Item_add(i1+i2,MainActivity.coupleID,text);
                        Item_show();

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

    //현재 날짜 찍기
    public String Date(){
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy년 MM월 dd일");
        Date date = new Date();
        String date_ = format.format(date);
        return date_;
    }

    //To-Do-List 조회
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
                            adapter1.addItem(responseTodo_.getContent_td(), responseTodo_.getDate_td());
                            listView1.setItemChecked(i1++,false);
                            adapter1.notifyDataSetChanged();
                        } else {
                            adapter2.addItem(responseTodo_.getContent_td(), responseTodo_.getDate_td());
                            listView2.setItemChecked(i2++,true);
                            adapter2.notifyDataSetChanged();
                        }
                    }
                }
                else Log.d("test", "통신 1 에러");
            }
            @Override
            public void onFailure(Call<List<ResponseTODO>> call, Throwable t) {
                Log.d("test", "통신3 에러" + t.getMessage());
            }
        });

    }

    //To-Do-List 추가
    public void Item_add(int todoID, int coupleID, String content){

        Call<ResponseTD_Insert> res = Net.getInstance().getApi().getTD_Add(todoID, coupleID," ", content,"false");
        res.enqueue(new Callback<ResponseTD_Insert>() {
            @Override
            public void onResponse(Call<ResponseTD_Insert> call, Response<ResponseTD_Insert> response) {
                if(response.isSuccessful()){
                    ResponseTD_Insert responseGet = response.body();
                    if (responseGet.setTDInsert() == true ) {
                    }
                }
                else Log.e("test", "통신1 에러");
            }
            @Override
            public void onFailure(Call<ResponseTD_Insert> call, Throwable t) {
                Log.e("test", "통신3 에러" + t.getMessage());
            }
        });

    }

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
       public void Item_modify(String content1, String content2){

        final Call<ResponseTD_update> res = Net.getInstance().getApi().getTD_Modify(MainActivity.coupleID, content1, content2);
        res.enqueue(new Callback<ResponseTD_update>() {
            @Override
            public void onResponse(Call<ResponseTD_update> call, Response<ResponseTD_update> response) {
                if(response.isSuccessful()){
                    ResponseTD_update responseGet = response.body();
                    if (responseGet.setTDModify() == true ) {
                        Toast.makeText(getApplication(),"수정 완료",Toast.LENGTH_SHORT).show();
                    }
                }
                else Log.e("test", "통신1 에러");

            }

            @Override
            public void onFailure(Call<ResponseTD_update> call, Throwable t) {
                Log.e("test", "통신3 에러" + t.getMessage());
            }
        });

       }

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

    //To-Do-List 삭제
    public void Item_Delete(String Content){
        Call<ResponseTD_delete> res = Net.getInstance().getApi().getTD_Delt(MainActivity.coupleID, Content);
        res.enqueue(new Callback<ResponseTD_delete>() {
            @Override
            public void onResponse(Call<ResponseTD_delete> call, Response<ResponseTD_delete> response) {
                if(response.isSuccessful()){
                    ResponseTD_delete responseGet = response.body();
                    if (responseGet.setTDDelt() == true ) {
                        Toast.makeText(getApplication(),"삭제완료",Toast.LENGTH_SHORT).show();
                    }
                }
                else Log.e("test", "통신1 에러");

            }

            @Override
            public void onFailure(Call<ResponseTD_delete> call, Throwable t) {
                Log.e("test", "통신3 에러" + t.getMessage());
            }
        });
    }

    /*//To-Do-List 삭제
       public void Item_Delete(ToDoList_ChoiceListAdapter adapter,String id, String content){
           sqlDB = todoDB.getWritableDatabase();
           sqlDB.execSQL("DELETE FROM to_do_list WHERE content='" + content + "' AND couple_id = '"+id+"';");
           adapter.notifyDataSetChanged();
           sqlDB.close();
       }*/

}
