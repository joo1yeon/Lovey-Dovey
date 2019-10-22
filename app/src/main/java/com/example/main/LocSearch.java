package com.example.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocSearch extends Activity {
    ImageButton btnHome;
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

        btnHome = findViewById(R.id.btnHome);
        geocoder = new Geocoder(this);
        edtLocation = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<FootContentList> locList = new ArrayList<>();
        final FootContentAdapter footContentAdapter = new FootContentAdapter(locList, getApplicationContext());
        recyclerView.setAdapter(footContentAdapter);
        myHelper = new MyDBHelper(this);
        sqlDB = myHelper.getWritableDatabase();
        final Cursor cursor = sqlDB.rawQuery("select * from location;", null);
        if (cursor.getCount() > 0) {
            Cursor cursor1 = sqlDB.rawQuery("select * from location ;", null);
            while (cursor1.moveToNext()) {
                locList.add(new FootContentList(cursor1.getInt(0), cursor1.getString(1)));
            }
            footContentAdapter.notifyDataSetChanged();
            cursor1.close();
        }

        //TODO 홈버튼 클릭
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //TODO 리사이클러뷰 아이템 클릭이벤트
        footContentAdapter.setItemClick(new FootContentAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                String location = locList.get(position).getLocation();
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String[] splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);
                double latitude = addressList.get(0).getLatitude();
                double longitude = addressList.get(0).getLongitude();
                Intent outIntent = new Intent(getApplicationContext(), FootPrint.class);
                outIntent.putExtra("name", location);
                outIntent.putExtra("latitude", latitude);
                outIntent.putExtra("longitude", longitude);
                outIntent.putExtra("address", address);
                setResult(RESULT_OK, outIntent);
                finish();
//                getAddress(locList.get(position).toString());
            }

        });


        //TODO 검색버튼을 누르면 ArrayList에 검색 내역 추가
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("test","1번확인");

                String str = edtLocation.getText().toString();
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocationName(str, 20);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Log.i("test","3번확인");
                if(addressList!=null){
                    if(addressList.size()==0){
                        Toast.makeText(getApplicationContext(),"해당하는 주소 정보가 없습니다.",Toast.LENGTH_SHORT).show();
                    }else{
                        if (edtLocation.getText().toString() != null) {
                            locList.clear();
//                    Log.i("test","2번확인");
                            sqlDB.execSQL("insert into location values (null,'" + edtLocation.getText().toString() + "');");
                            sqlDB.close();
                        }

                        String[] splitStr = addressList.get(0).toString().split(",");
                        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);
                        double latitude = addressList.get(0).getLatitude();
                        double longitude = addressList.get(0).getLongitude();
                        Intent outIntent = new Intent(getApplicationContext(), FootPrint.class);
                        outIntent.putExtra("name", edtLocation.getText().toString());
                        outIntent.putExtra("latitude", latitude);
                        outIntent.putExtra("longitude", longitude);
                        outIntent.putExtra("address", address);

                        setResult(RESULT_OK, outIntent);
                        finish();

                    }
                }
//                Intent intent = new Intent(LocSearch.this, LocResult.class);
//                startActivity(intent);
//                getAddress(edtLocation.getText().toString());
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent outIntent = new Intent(getApplicationContext(), FootPrint.class);
        setResult(RESULT_CANCELED, outIntent);
        finish();
    }

//    public void getAddress(String location){
//        List<Address> addressList = null;
//        try{
//            addressList = geocoder.getFromLocationName(location,1);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
////                Log.i("test","3번확인");
//
//        String []splitStr = addressList.get(0).toString().split(",");
//        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);
//        double latitude =addressList.get(0).getLatitude();
//        double longitude=addressList.get(0).getLongitude();
//        Intent outIntent = new Intent(getApplicationContext(),FootPrint.class);
//        outIntent.putExtra("name",location);
//        outIntent.putExtra("latitude", latitude);
//        outIntent.putExtra("longitude",longitude);
//        outIntent.putExtra("address",address);
//
//        setResult(RESULT_OK,outIntent);
//        finish();
//
//    }
}
