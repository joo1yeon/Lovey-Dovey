package com.example.main;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class FootPrint extends Fragment implements OnMapReadyCallback {
    InsertDB insert;

    String id;
    MarkerOptions markerOptions = new MarkerOptions();
    GoogleMap gMap;
    ImageButton btnTomorrow, btnYesterday;
    TextView tvToday;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DATE);
    MapView map;
    Date today = cal.getTime();
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton btnFab, fabSearch, fabCal, fabToday, btnSave;
    private BottomSheetDialog modalBottomSheet;

    public FootPrint() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //    public static FootPrint getInstance(String name,String address,double latitude,double longitude){
//        FootPrint f = new FootPrint();
//        Bundle args = new Bundle();
//        args.putString("name",name);
//        args.putString("address",address);
//        args.putDouble("latitude",latitude);
//        args.putDouble("longitude",longitude);
//        f.setArguments(args);
//        return f;
//    }
    @Nullable
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_foot, container, false);
//        sqlDB=dbHelper.getWritableDatabase();
//        Bundle bundle = this.getArguments();
////        savedInstanceState=this.getArguments();
//        if(bundle!=null){
//            Log.d("AAA","제발...");
//
//            bundle=getArguments();
//            Toast.makeText(getContext(),"값있음",Toast.LENGTH_SHORT).show();
//
//            Log.d("AAA",bundle.toString());
//
//            String name=bundle.getString("name");
//            String address=bundle.getString("address");
//            double latitude = bundle.getDouble("latitude",0);
//            double longitude=bundle.getDouble("longitude",0);
//            Log.d("AAA",name);
//            Log.d("AAA",address);
//            Log.d("AAA",latitude+"");
//            Log.d("AAA",longitude+"");
//            LatLng point = new LatLng(latitude, longitude);
//            markerOptions.title(name);
//            markerOptions.snippet(address);
//            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker2", 100, 120)));
//            markerOptions.position(point);
//            gMap.addMarker(markerOptions);
//            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
//        }else{
//            Toast.makeText(getContext(),"null값",Toast.LENGTH_SHORT).show();
//        }

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        Log.d("date", year + "" + month + 1 + "" + day);

        btnFab = layout.findViewById(R.id.btnFab);
        fabToday = layout.findViewById(R.id.fabToday);
        fabSearch = layout.findViewById(R.id.fabSearch);
        fabCal = layout.findViewById(R.id.fabCal);
        tvToday = layout.findViewById(R.id.tvToday);
        btnTomorrow = layout.findViewById(R.id.btnTomorrow);
        btnYesterday = layout.findViewById(R.id.btnYesterday);
        tvToday.setText(sdf.format(cal.getTime()));
        btnSave = layout.findViewById(R.id.btnSave);

        //TODO 버튼을 클릭하면 FloatingActionButton 애니메이션 실행
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });

        //TODO 오늘 날짜로 이동
        fabToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Toast.makeText(getContext(), "오늘 날짜로 이동", Toast.LENGTH_SHORT).show();
                cal.set(year, month, day);
                printMarker(gMap, year, month, day);
                tvToday.setText(sdf.format(today));

            }
        });

        //TODO 장소 검색 화면으로 이동
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent = new Intent(getContext(), LocSearch.class);
                startActivityForResult(intent, 0);
            }
        });

        //TODO DatePickerDialog 출력
        fabCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                DatePickerDialog dateDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvToday.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                        printMarker(gMap, year, month + 1, dayOfMonth);
                        Toast.makeText(getContext(), "선택한 날짜로 이동합니당", Toast.LENGTH_SHORT).show();
                        cal.set(year, month, dayOfMonth);
                    }
                }, year, month, day);
                dateDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.getTime().getYear()+1900;
                int month = cal.getTime().getMonth()+1;
                int date = cal.getTime().getDate();
                Toast.makeText(getContext(), "마커 저장", Toast.LENGTH_SHORT).show();
                insert.save(year,month,date);
            }
        });
        tvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvToday.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                        printMarker(gMap, year, month + 1, dayOfMonth);
                        Log.d("DDD", year + "/" + (month + 1) + "/" + dayOfMonth);

                        Toast.makeText(getContext(), "선택한 날짜로 이동합니당", Toast.LENGTH_SHORT).show();
                        cal.set(year, month, dayOfMonth);
                    }
                }, year, month, day);
                dateDialog.show();

            }
        });
        //TODO 날짜 이동 버튼(하루 전)
        btnYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.DATE, -1);
                tvToday.setText(sdf.format(cal.getTime()));
                printMarker(gMap, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));

                Log.d("DDD", cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE));
                Toast.makeText(getContext(), "어제 날짜로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO 날짜 이동 버튼(하루 후)
        btnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.DATE, +1);
                tvToday.setText(sdf.format(cal.getTime()));
                printMarker(gMap, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
                Log.d("DDD", cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE));
                Toast.makeText(getContext(), "내일 날짜로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });
        map = layout.findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        map.getMapAsync(this);


        return layout;
    }

    //TODO FloatingActionButton에 애니메이션 설정
    public void anim() {
        if (isFabOpen) {
            fabCal.startAnimation(fab_close);
            fabSearch.startAnimation(fab_close);
            fabToday.startAnimation(fab_close);
            fabCal.setClickable(false);
            fabSearch.setClickable(false);
            fabToday.setClickable(false);
            isFabOpen = false;
        } else {
            fabCal.startAnimation(fab_open);
            fabSearch.startAnimation(fab_open);
            fabToday.startAnimation(fab_open);
            fabCal.setClickable(true);
            fabSearch.setClickable(true);
            fabToday.setClickable(true);
            isFabOpen = true;
        }
    }

    public void setGoogleMap(GoogleMap m) {
        gMap = m;
    }

    //TODO OnMapReady
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        printMarker(gMap, year, month + 1, day);
        setGoogleMap(googleMap);

        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.584, 126.925), 15));
        gMap.getUiSettings().setZoomControlsEnabled(false);
        gMap.getUiSettings().isMyLocationButtonEnabled();
        //TODO 지도위에 마커 찍기
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                int year = cal.getTime().getYear()+1900;
                int month = cal.getTime().getMonth()+1;
                int date = cal.getTime().getDate();
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> list = null;
                try {
                    list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("TTT", list.get(0).toString());
                String[] splitStr = list.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);
                markerOptions.title(address);
                markerOptions.snippet(address);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker2", 100, 120)));
                markerOptions.position(latLng);
                gMap.addMarker(markerOptions);
                insert.insert(address,address,latLng.latitude,latLng.longitude,year, month , date);
//                sqlDB.execSQL("insert into marker values('" + address + "','" + address + "','" + latLng.latitude + "','" + latLng.longitude + "','" + (day + "/" + month + "/" + date) + ");");

            }
        });
        //TODO 마커 클릭 이벤트1
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                final int year = cal.getTime().getYear()+1900;
                final int month = cal.getTime().getMonth()+1;
                final int date = cal.getTime().getDate();
                Log.i("tt", "마커클릭이벤트 호출");
                View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                TextView placeName, placeAddress;
                placeName = view.findViewById(R.id.placeName);
                placeAddress = view.findViewById(R.id.placeAddress);
                placeName.setText(marker.getTitle());
                if (marker.getSnippet() != null) {
                    placeAddress.setText(marker.getSnippet());
                } else placeAddress.setText("");
                Button btnAddPicture, btnDelMark;
                btnAddPicture = view.findViewById(R.id.btnAddPicture);
                btnDelMark = view.findViewById(R.id.btnDelMark);
                btnAddPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "사진추가하기", Toast.LENGTH_SHORT).show();
                    }
                });
                btnDelMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        marker.remove();
                        insert.delete(marker.getPosition().latitude,marker.getPosition().longitude,year , month , date);
//                        sqlDB.execSQL("delete from marker where latitude=" + marker.getPosition().latitude + " and longitude=" + marker.getPosition().longitude + " and date =" + (day + "/" + month + "/" + date));

                        modalBottomSheet.dismiss();
                    }
                });
                modalBottomSheet = new BottomSheetDialog(getContext());
                modalBottomSheet.setContentView(view);
                modalBottomSheet.show();
                return false;
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        insert=(InsertDB)context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            Double latitude = data.getDoubleExtra("latitude", 0);
            Double longitude = data.getDoubleExtra("longitude", 0);
            String address = data.getStringExtra("address");
            LatLng point = new LatLng(latitude, longitude);
            markerOptions.title(name);
            markerOptions.snippet(address);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker2", 100, 120)));
            markerOptions.position(point);
            gMap.addMarker(markerOptions);
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));

        }
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        map.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    public void printMarker(GoogleMap _gMap, int year, int month, int day) {
        final GoogleMap gMap = _gMap;
        gMap.clear();
        Call<List<ResponseMarker>> res = Net.getInstance().getApi().getMarker("c1", year, month, day);
        res.enqueue(new Callback<List<ResponseMarker>>() {
            @Override
            public void onResponse(Call<List<ResponseMarker>> call, Response<List<ResponseMarker>> response) {
                if (response.isSuccessful()) {
                    List<ResponseMarker> responseGet = response.body();
                    for (ResponseMarker responseMarker : responseGet) {
                        if (responseMarker.getName() != null && responseMarker.getAddress() != null && responseMarker.getLng() != 0 && responseMarker.getLat() != 0) {
                            LatLng point = new LatLng(responseMarker.getLat(), responseMarker.getLng());
                            Log.d("marker", "위도경도 : " + point);

                            markerOptions.title(responseMarker.getName());
                            markerOptions.snippet(responseMarker.getAddress());
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker2", 100, 120)));
                            markerOptions.position(point);
                            gMap.addMarker(markerOptions);
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));

                        } else {
                            gMap.clear();
                        }

                    }
                } else Toast.makeText(getContext(), "통신1 에러", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ResponseMarker>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TTT", t.getMessage());
            }
        });
    }
}

