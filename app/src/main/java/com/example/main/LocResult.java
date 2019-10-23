package com.example.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocResult extends Activity {
    Geocoder geocoder;
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
    ListView listView;
    LocListView adapter;
    ArrayList<LocItem> listItem;
    TextView tvSearch;
    ImageButton btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loc_result);
        tvSearch = findViewById(R.id.txtSearch);
        btnCancel = findViewById(R.id.btnRemove);
        geocoder = new Geocoder(this);
        myHelper = new MyDBHelper(this);
        sqlDB = myHelper.getWritableDatabase();
        listView = findViewById(R.id.locList);
        listItem = new ArrayList<LocItem>();
        adapter = new LocListView(this, listItem);
        listView.setAdapter(adapter);
        listItem.clear();

        Intent intent = getIntent();
        final String str = intent.getStringExtra("Location");
        tvSearch.setText(str);
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(str, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
//                Log.i("test","3번확인");
        if (addressList != null) {
            if (addressList.size() == 0) {
                Toast.makeText(getApplicationContext(), "해당하는 주소 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < addressList.size(); i++) {
                    String[] splitStr = addressList.get(i).toString().split(",");
//                    String locName= addressList.get(i).get
                    String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);
                    double latitude = addressList.get(i).getLatitude();
                    double longitude = addressList.get(i).getLongitude();
                    listItem.add(new LocItem(address, latitude, longitude));
                    if (str != null) {
                        sqlDB.execSQL("insert into location values (null,'" + str + "');");
                        sqlDB.close();
                    }
                }

            }
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(LocResult.this, LocSearch.class);
                startActivity(intent3);
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                FootPrint fragment = new FootPrint().getInstance(str,listItem.get(position).getLocAddress(),listItem.get(position).getLocLatitude(),listItem.get(position).getLocLongitude());
//                Bundle bundle = new Bundle();
//                bundle.putString("name",str);
//                bundle.putDouble("latitude",listItem.get(position).getLocLatitude());
//                bundle.putDouble("longitude",listItem.get(position).getLocLongitude());
//                bundle.putString("address",listItem.get(position).getLocAddress());
//                Log.d("LLL",str);
//                Log.d("LLL",listItem.get(position).getLocAddress());
//                Log.d("LLL",listItem.get(position).getLocLatitude()+"");
//                Log.d("LLL",listItem.get(position).getLocLongitude()+"");
//
//                fragment.setArguments(bundle);
//                Log.d("LLL",bundle.toString());
//                adapter.addFragment(fragment);
                finish();
            }
        });

    }
}
