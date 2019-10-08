package com.example.main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    String strContent, strDate, strCoupleID = "couple0", strcheck;



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



        //체크되지 않은 투두 아이템 추가
        Item_show(adapter1, strCoupleID,false);
        /*adapter1.addItem("시험끝나고 미친듯이 놀기", "");
        adapter1.addItem("PC방 가서 하루종일 게임하기", "");
        adapter1.addItem("오류같이 찾고 기뻐하기 ㅎㅎ", "");
        adapter1.addItem("웃으면서 같이 코딩하기", "");
        adapter1.addItem("누워서 맘편히 잠자기", "");
        adapter1.addItem("종로가서 커플링 맞추기", "");
        adapter1.addItem("커플 키링 만들어보기", ""); */


        //체크된 투두 아이템 추가
        Item_show(adapter2, strCoupleID,true);
        /*adapter2.addItem("곱창 무한리필 먹기", "2019년 05월 12일");
        adapter2.addItem("바다가서 조개구이 먹기", "2019년 04월 09일");
        adapter2.addItem("해외여행 가기", "2019년 04월 08일");
        adapter2.addItem("동물카페 가서 놀기", "2019년 03월 04일");
        adapter2.addItem("찜질방 가서 하루 놀기", "2019년 03월 04일");
        adapter2.addItem("커플신발 맞춰보기", "2019년 02월 28일");*/



        //TODO 보충 : 전부 다 체크해제 상태여야 함, 체크했을 때 아래꺼까지 체크 안되는 방법
        //체크되지 않은 투두리스트 클릭했을 때
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDoList_ListItem listItem = adapter1.listViewItems.get(i);
                String content = listItem.getContent();


                Item_Click(adapter1,strCoupleID,true, content,i);
                adapter1.clearItem();
                Item_show(adapter1, strCoupleID,false);
                adapter2.clearItem();
                Item_show(adapter2, strCoupleID,true);
                adapter2.notifyDataSetChanged();


                /*listView1.setItemChecked(i,false);                     //선택한 인덱스의 체크 풀기
                adapter1.remove(i);                                       //선택한 adapter1 내용을 지우기
                adapter1.notifyDataSetChanged();                          //변경한 데이터 반영
                adapter2.addItem(content,Date());                         //adapter2에 adapter1에 있던 내용 추가
                adapter2.notifyDataSetChanged();                          //변경한 데이터 반영*/

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
                menuLayout = View.inflate(ToDoList.this, R.layout.list_menu, null);
                dl2.setView(menuLayout);
                dl2.show();

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
                                //TODO 수정하는 부분 해야함 데이터베이스에서 검색 후 해당하는 내용 부분 수정하면 될 듯


                                //데이터 수정하는 코드
                                /*listItem.setContent(text);
                                adapter1.notifyDataSetChanged();*/
                                Item_modify( adapter1, strCoupleID,text,listItem.getContent());
                                adapter1.clearItem();                                           //리스트뷰 초기화
                                Item_show(adapter1,strCoupleID,false);                   //데이터 베이스 검색 후 내용 받아옴
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

                        /*adapter1.remove(position);
                        adapter1.notifyDataSetChanged();*/

                        Item_Delete(adapter1,strCoupleID,listItem.getContent());
                        adapter1.clearItem();                                           //리스트뷰 초기화
                        Item_show(adapter1,strCoupleID,false);                   //데이터 베이스 검색 후 내용 받아옴
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


                Item_Click(adapter2,strCoupleID,false,content,i);
                adapter2.clearItem();
                Item_show(adapter2, strCoupleID,true);
                adapter1.clearItem();
                Item_show(adapter1, strCoupleID,false);
                adapter1.notifyDataSetChanged();

                /*listView2.setItemChecked(i,false);                  //선택한 인덱스의 체크 풀기
                adapter2.remove(i);                                        //선택한 adapter2 내용을 지우기
                adapter2.notifyDataSetChanged();                           //변경한 데이터 반영
                adapter1.addItem(content,"");                              //adapter1에 adapter2에 있던 내용 추가
                adapter1.notifyDataSetChanged();                          //변경한 데이터 반영*/
            }
        });


        //취소선하는법
        //textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        //textView.setPaintFlags(0);


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

                        Item_add(adapter1,strCoupleID,text);
                        adapter1.clearItem();                                           //리스트뷰 초기화
                        Item_show(adapter1,strCoupleID,false);                   //데이터 베이스 검색 후 내용 받아옴


                        /*adapter1.addItem(text, "");
                        contents.setText("");
                        adapter1.notifyDataSetChanged();*/
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

       /* public void checkedConfirm(int position) {
            ListData check = listData.get(position);
            // 체크된 아이템인지 판단할 boolean 변수
            for (int i = 0; i < listData.size(); i++) {
                if (check.checkBox.isChecked()==true) {
                    check.checkBox.setChecked(true);
                } else {
                    check.checkBox.setChecked(false);
                }
                notifyDataSetChanged();
            }
        }*/



       //To-Do-List 조회
       public void Item_show(ToDoList_ChoiceListAdapter adapter, String id, boolean check){
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
                else if(adapter == adapter2)
                    listView2.setItemChecked(i, true);
                }

            cursor.close();
            sqlDB.close();
       }

        //To-Do-List 추가
       public void Item_add(ToDoList_ChoiceListAdapter adapter, String id, String content){
           try{
               sqlDB = todoDB.getWritableDatabase();
               cursor = sqlDB.rawQuery("SELECT * FROM to_do_list WHERE couple_id='"+id+"';",null);
               int count = cursor.getCount();
               sqlDB.execSQL("INSERT INTO to_do_list VALUES (" + ++count + ",'" + id + "','','" + content + "','" + false + "');");
           }catch (Exception e){ }

           cursor.close();
           sqlDB.close();
       }


       //To-Do-List 수정
    public void Item_modify(ToDoList_ChoiceListAdapter adapter,String id, String content, String content_){
        sqlDB = todoDB.getWritableDatabase();
        sqlDB.execSQL("update to_do_list set content='" + content + "' where couple_id='" + id + "' and content='"+ content_ +"';");
        adapter.notifyDataSetChanged();
        sqlDB.close();

    }

    //To-Do-List 클릭했을 때 넘어가기
    public void Item_Click(ToDoList_ChoiceListAdapter adapter,String id, boolean b, String content, int i){
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

    }

       //To-Do-List 삭제
       public void Item_Delete(ToDoList_ChoiceListAdapter adapter,String id, String content){
           sqlDB = todoDB.getWritableDatabase();
           sqlDB.execSQL("DELETE FROM to_do_list WHERE content='" + content + "' AND couple_id = '"+id+"';");
           adapter.notifyDataSetChanged();
           sqlDB.close();
       }

}
