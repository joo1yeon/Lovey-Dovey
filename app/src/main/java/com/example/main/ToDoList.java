package com.example.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ToDoList extends AppCompatActivity {

    View addLayout, menuLayout;
    EditText contents;
    ImageView btnAdd, cancel, add;
    TextView change, delete;
    ListView listView1, listView2;                                      //선택한거, 안한거
    CustomChoiceListViewAdapter adapter1, adapter2;                     //선택한거, 안한거
    SlidingDrawer handle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);



        //리스트뷰 인플레이트
        listView1 = findViewById(R.id.list);
        listView2 = findViewById(R.id.checked_list);


        //Adapter 생성
        //adapter1 = new ListViewAdapter(this);
        //adapter2 = new ListViewAdapter(this);

        //Adapter 달기
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);


        //체크안된 투두 아이템 추가
        adapter1.addItem("시험끝나고 미친듯이 놀기", "");
        adapter1.addItem( "PC방 가서 하루종일 게임하기", "");
        adapter1.addItem( "오류같이 찾고 기뻐하기 ㅎㅎ", "");
        adapter1.addItem("웃으면서 같이 코딩하기", "");
        adapter1.addItem("누워서 맘편히 잠자기", "");
        //adapter1.sort();


        //체크된 투두 아이템 추가
        adapter2.addItem("시험끝나고 미친듯이 놀기", "2019년 05월 12일");
        adapter2.addItem( "PC방 가서 하루종일 게임하기", "2019년 04월 09일");
        adapter2.addItem( "오류같이 찾고 기뻐하기 ㅎㅎ", "2019년 04월 08일");
        adapter2.addItem("웃으면서 같이 코딩하기", "2019년 3월 4일");
        adapter2.addItem("누워서 맘편히 잠자기", "2019년 2월 28일");


        //체크되지 않은 투두리스트 클릭했을 때
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String listViewItem = adapter1.getItem(listView1.getCheckedItemPosition()).toString();
                Toast.makeText(ToDoList.this, "체크됨" , Toast.LENGTH_SHORT).show();
            }
        });


        //체크하지 않은 리스트를 길게 클릭했을 때
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //final ListData mData = mAdapter1.listData.get(position);
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

                        add = addLayout.findViewById(R.id.add);
                        cancel = addLayout.findViewById(R.id.cancel);
                        contents = addLayout.findViewById(R.id.content);

                        //contents.setText(mData.content);

                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text = contents.getText().toString();

                                //데이터 수정하는 코드
                                //mAdapter1.notifyDataSetChanged();
                                dl.dismiss();

                            }
                        });

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
                        //mAdapter1.remove(position);
                        Toast.makeText(ToDoList.this,"삭제되었습니다.",Toast.LENGTH_SHORT);
                        dl2.dismiss();
                    }
                });
                return true;
            }
        });

        /*//체크된 투두리스트 클릭했을 때
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListData mData = mAdapter2.listData.get(i);
                //Toast.makeText(MainActivity.this, mData.content, Toast.LENGTH_SHORT).show();
            }
        });*/



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

                add = addLayout.findViewById(R.id.add);
                cancel = addLayout.findViewById(R.id.cancel);
                contents = addLayout.findViewById(R.id.content);


                //추가
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = contents.getText().toString();
                        //mAdapter1.addItem(text, "");
                        contents.setText("");
                        //mAdapter1.notifyDataSetChanged();
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

    /* 왠만해서 사라질 거
    //액티비티에서 프레그먼트 화면전환
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.commit();
    }*/


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


}
