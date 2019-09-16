package com.example.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocSearch extends AppCompatActivity {
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
    EditText edtLocation;
    Geocoder geocoder;
    ImageView btnSearch;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        geocoder=new Geocoder(this);
        edtLocation=findViewById(R.id.edtSearch);
        btnSearch=findViewById(R.id.btnSearch);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<FootContentList> locList = new ArrayList<>();
        final FootContentAdapter footContentAdapter = new FootContentAdapter(locList, getApplicationContext());
        recyclerView.setAdapter(footContentAdapter);
        myHelper=new MyDBHelper(this);
        sqlDB=myHelper.getWritableDatabase();
//        sqlDB.execSQL("delete from location; ");
        final Cursor cursor=sqlDB.rawQuery("select * from location;",null);
        if(cursor.getCount()>0){
            Cursor cursor1 = sqlDB.rawQuery("select content from location order by num desc ;",null);
            while(cursor1.moveToNext()){
                locList.add(new FootContentList(cursor1.getString(0)));
            }
            footContentAdapter.notifyDataSetChanged();
            cursor1.close();
        }
        //TODO 검색버튼을 누르면 ArrayList에 검색 내역 추가
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("test","1번확인");

                if(edtLocation.getText().toString()!=null){
                    locList.clear();
//                    Log.i("test","2번확인");
                    sqlDB.execSQL("insert into location values (null,'"+edtLocation.getText().toString()+"');");
                    sqlDB.close();
                }
                String str =edtLocation.getText().toString();
                List<Address> addressList = null;
                try{
                    addressList = geocoder.getFromLocationName(str,1);
                }catch (IOException e){
                    e.printStackTrace();
                }
//                Log.i("test","3번확인");

                String []splitStr = addressList.get(0).toString().split(",");
//                Log.i("test","3-1"+splitStr[0]);
//                Log.i("test","3-1"+splitStr[10]);
//                Log.i("test","3-1"+splitStr[12]);
//
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);
                double latitude =addressList.get(0).getLatitude();
                double longitude=addressList.get(0).getLongitude();
//                Log.i("test","3-"+latitude);
//                Log.i("test","3-"+longitude);
//                Log.i("test","3-"+address);
//
                Intent outIntent = new Intent(getApplicationContext(),FootPrint.class);
                outIntent.putExtra("name",edtLocation.getText().toString());
                outIntent.putExtra("latitude", latitude);
                outIntent.putExtra("longitude",longitude);
                outIntent.putExtra("address",address);
//                Log.i("test","4번확인");
//                Log.i("test","4-"+latitude);
//                Log.i("test","4-"+longitude);
//                Log.i("test","4-"+address);

                setResult(RESULT_OK,outIntent);
                finish();
            }
        });

    }
}
