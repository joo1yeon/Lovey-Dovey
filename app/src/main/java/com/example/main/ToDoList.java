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
    private ListView mListView1, mListView2;
    private ListViewAdapter mAdapter1, mAdapter2;
    SlidingDrawer handle;

    ImageButton btnHome, btnOverflow;
    Button btnDate, btnFoot, btnGal;
    Main mainFrag;
    Datecourse dateFrag;
    FootPrint footFrag;
    Album albumFrag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        btnHome = findViewById(R.id.btnHome);
        btnOverflow = findViewById(R.id.btnOverflow);
        btnDate = findViewById(R.id.btnDate);
        btnFoot = findViewById(R.id.btnFoot);
        btnGal = findViewById(R.id.btnGallery);

        mainFrag = new Main();
       /*dateFrag = new Datecourse();
        footFrag = new FootPrint();
        albumFrag = new Album();*/

       handle = findViewById(R.id.slidingdrawer);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(mainFrag);
                btnAdd.setVisibility(View.INVISIBLE);
                handle.setVisibility(View.INVISIBLE);
            }
        });

        /*btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(dateFrag);
            }
        });

        btnFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(footFrag);
            }
        });

        btnGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(albumFrag);
            }
        });*/



        //리스트뷰 인플레이트
        mListView1 = findViewById(R.id.list);
        mListView2 = findViewById(R.id.checked_list);


        //Adapter 생성
        mAdapter1 = new ListViewAdapter(this);
        mAdapter2 = new ListViewAdapter(this);

        //Adapter 달기
        mListView1.setAdapter(mAdapter1);
        mListView2.setAdapter(mAdapter2);


        //체크안된 투두 아이템 추가
        mAdapter1.addItem("시험끝나고 미친듯이 놀기", "");
        mAdapter1.addItem( "PC방 가서 하루종일 게임하기", "");
        mAdapter1.addItem( "오류같이 찾고 기뻐하기 ㅎㅎ", "");
        mAdapter1.addItem("웃으면서 같이 코딩하기", "");
        mAdapter1.addItem("누워서 맘편히 잠자기", "");
        mAdapter1.sort();


        //체크된 투두 아이템 추가
        mAdapter2.addItem( "베가보쌈에서 식사하기", "");
        mAdapter2.addItem("카페에서 회의하기", "");


        //체크되지 않은 투두리스트 클릭했을 때
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListData mData = mAdapter1.listData.get(i);
                Toast.makeText(ToDoList.this, "체크됨" , Toast.LENGTH_SHORT).show();
            }
        });

        //체크하지 않은 리스트를 길게 클릭했을 때
        mListView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final ListData mData = mAdapter1.listData.get(position);
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

                        contents.setText(mData.content);

                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text = contents.getText().toString();

                                //데이터 수정하는 코드
                                mAdapter1.notifyDataSetChanged();
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
                        mAdapter1.remove(position);
                        Toast.makeText(ToDoList.this,"삭제되었습니다.",Toast.LENGTH_SHORT);
                        dl2.dismiss();
                    }
                });
                return true;
            }
        });

        //체크된 투두리스트 클릭했을 때
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListData mData = mAdapter2.listData.get(i);
                //Toast.makeText(MainActivity.this, mData.content, Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd = findViewById(R.id.btnAdd);

        //취소선하는법
        //textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        //textView.setPaintFlags(0);



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


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = contents.getText().toString();
                        mAdapter1.addItem(text, "");
                        contents.setText("");
                        mAdapter1.notifyDataSetChanged();
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
    }

    //액티비티에서 프레그먼트 화면전환
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.commit();
    }

    //아이템 요소들 선언
    class ViewHolder {
        public CheckBox checkBox;
        public TextView content;
        public TextView date;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        ArrayList<ListData> listData = new ArrayList<ListData>();
        private boolean[] isCheckedConfrim;
        private LayoutInflater inflater = null;


        public ListViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        //각 아이템에 들어 갈 데이터들의 전체 개수를 리턴
        public int getCount() {
            return this.listData.size();
        }

        //아이템의 위치에 해당하는 데이터의 객체를 반환
        public Object getItem(int position) {
            return this.listData.get(position);
        }

        //아이템의 위치에 해당하는 id를 반환
        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ListData data = (ListData) listData.get(position);    //ListData의 위치를 받아옴



            if (convertView == null) {
                holder = new ViewHolder();  //view가 매번 인플레이트를 하지않게 저장해놓음
                LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item, (ViewGroup) null); //xml 파일을 converView 객체로 만듬

                //converView에 들어갈 요소들 인플레이트
                holder.checkBox = convertView.findViewById(R.id.checkBox);
                holder.content = convertView.findViewById(R.id.content);
                holder.date = convertView.findViewById(R.id.date);

                convertView.setTag(holder); //converView를 식별할 태그 정보를 추가
            } else {
                holder = (ViewHolder) convertView.getTag(); //converView에 holder라는 태그를 달음
            }

            //채크박스 기본 리벤트리스너 제거
            holder.checkBox.setClickable(false);
            holder.checkBox.setFocusable(false);

            holder.content.setText(data.content);
            holder.date.setText(data.date);

            /*boolean getChecked = !data.checkBox.isChecked();
            holder.checkBox.setChecked(getChecked);
            listData.get(position).checkBox.setChecked(getChecked );
            notifyDataSetInvalidated();*/

            return convertView; //View를 반환
        }

        //아이템 추가
        public void addItem(String name, String date) {
            ListData addInfo = new ListData();      //ListData 객체 생성
            //받은 값들을 넣어줌
            addInfo.content = name;
            addInfo.date = date;

            this.listData.add(addInfo);        //ListView에 ListData 추가
        }

        //아이템을 제거
        public void remove(int position){
            listData.remove(position);
            dataChange();
        }

        //아이템을 바르게 배열
        public void sort(){
            Collections.sort(listData, ListData.ALPHA_COMPARATOR);
            dataChange();
        }

        //데이터를 변경한 후 호출해야 함
        public void dataChange(){
            mAdapter1.notifyDataSetChanged();
            mAdapter2.notifyDataSetChanged();
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


    }
}
