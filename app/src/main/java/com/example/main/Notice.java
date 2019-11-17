package com.example.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notice extends AppCompatActivity {
    ImageButton btnBack;
    ListView listView;
    Overflow_Notice_Adapter adapter;
    String id;
    String nickname;
    String email;
    int couple;
    View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overflow_notice);
        listView = findViewById(R.id.noticeList);
        btnBack = findViewById(R.id.btnBack);
        adapter = new Overflow_Notice_Adapter();
        listView.setAdapter(adapter);
        id = MainActivity.id;
        nickname = MainActivity.nickname;
        couple = MainActivity.coupleID;
        email = MainActivity.email;

//        for(int i=0;i<10;i++){
//            adapter.addItem("공지사항제목"+i,"공지사항내용"+i);
//        }

        Call<List<ResponseNotice>> res = Net.getInstance().getApi().getNotice();
        res.enqueue(new Callback<List<ResponseNotice>>() {
            @Override
            public void onResponse(Call<List<ResponseNotice>> call, Response<List<ResponseNotice>> response) {
                if (response.isSuccessful()) {
                    List<ResponseNotice> responseNotices = response.body();
                    for (ResponseNotice responseNotice : responseNotices) {
                        adapter.addItem(responseNotice.getTitle());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseNotice>> call, Throwable t) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notice.this, MainActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("NICK", nickname);
                intent.putExtra("EMAIL", email);
                intent.putExtra("COUPLE", couple);
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view = View.inflate(Notice.this, R.layout.notice_dialog, null);
                final TextView title = view.findViewById(R.id.textTitle);
                final TextView content = view.findViewById(R.id.textContent);
                final TextView date = view.findViewById(R.id.textDate);
                Call<NoticeInfo> res = Net.getInstance().getApi().getNoticeInfo(position);
                res.enqueue(new Callback<NoticeInfo>() {
                    @Override
                    public void onResponse(Call<NoticeInfo> call, Response<NoticeInfo> response) {
                        title.setText(response.body().getTitle());
                        content.setText(response.body().getContent());
                        date.setText(response.body().getYear() + "년도 " + response.body().getMonth() + "월 " + response.body().getDay()+"일");
                    }

                    @Override
                    public void onFailure(Call<NoticeInfo> call, Throwable t) {

                    }
                });

                AlertDialog dlg = new AlertDialog.Builder(Notice.this)
                        .setView(view)
                        .setPositiveButton("닫기", null).show();


                WindowManager.LayoutParams params = dlg.getWindow().getAttributes();
                params.width=800;
                dlg.getWindow().setAttributes(params);

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Notice.this,MainActivity.class);
        intent.putExtra("ID",MainActivity.id);
        intent.putExtra("NICK",MainActivity.nickname);
        intent.putExtra("EMAIL",MainActivity.email);
        intent.putExtra("C_ID",MainActivity.coupleID);
        startActivity(intent);
        finish();
    }

}
