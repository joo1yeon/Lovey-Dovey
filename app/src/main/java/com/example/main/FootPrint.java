package com.example.main;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class FootPrint extends Fragment  {
    ImageView btnCal;
    TextView btnToday,tvYesterday,tvToday,tvTomorrow;
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH)+1;
    int day = cal.get(Calendar.DAY_OF_MONTH);
//    GroundOverlayOptions videoMark;
//    GoogleMap gMap;
//    MapFragment mapFrag;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton btnFab, fabSearch, fabCal, fabToday;

    public FootPrint() {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.footprint, container, false);
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);

        btnFab = layout.findViewById(R.id.btnFab);
        fabToday = layout.findViewById(R.id.fabToday);
        fabSearch = layout.findViewById(R.id.fabSearch);
        fabCal = layout.findViewById(R.id.fabCal);
        tvYesterday=layout.findViewById(R.id.tvYesterday);
        tvToday=layout.findViewById(R.id.tvToday);
        tvTomorrow=layout.findViewById(R.id.tvTomorrow);


        tvYesterday.setText(year+"년 "+month+"월 "+(day-1)+"일");
        tvToday.setText(year+"년 "+month+"월 "+day+"일");
        tvTomorrow.setText(year+"년 "+month+"월 "+(day+1)+"일");


        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MODE_PRIVATE);
        //mapFrag=(MapFragment)getFragmentManager().findFragmentById(R.id.map);

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
                tvYesterday.setText(year+"년 "+month+"월 "+(day-1)+"일");
                tvToday.setText(year+"년 "+month+"월 "+day+"일");
                tvTomorrow.setText(year+"년 "+month+"월 "+(day+1)+"일");

            }
        });

        //TODO 장소 검색 화면으로 이동
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent = new Intent(getContext(),Search.class);
                startActivity(intent);
                Toast.makeText(getContext(),"장소 검색하기",Toast.LENGTH_SHORT).show();
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
                        tvYesterday.setText(year+"년 "+(month+1)+"월 "+(dayOfMonth-1)+"일");
                        tvToday.setText(year+"년 "+(month+1)+"월 "+dayOfMonth+"일");
                        tvTomorrow.setText(year+"년 "+(month+1)+"월 "+(dayOfMonth+1)+"일");

                    }
                }, year, month-1, day);
                dateDialog.show();

            }
        });

        /*btnCal = layout.findViewById(R.id.btnCal);
        btnToday = layout.findViewById(R.id.btnToday);
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);
//        mapFrag =(MapFragment) layout.findViewById(R.id.map);
//        mapFrag.getMapAsync(this);


        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/

        return layout;
    }

    //TODO FloatingActionButton에 애니메이션 설정
    public void anim() {
        if(isFabOpen){
            fabCal.startAnimation(fab_close);
            fabSearch.startAnimation(fab_close);
            fabToday.startAnimation(fab_close);
            fabCal.setClickable(false);
            fabSearch.setClickable(false);
            fabToday.setClickable(false);
            isFabOpen=false;
        }else{
            fabCal.startAnimation(fab_open);
            fabSearch.startAnimation(fab_open);
            fabToday.startAnimation(fab_open);
            fabCal.setClickable(true);
            fabSearch.setClickable(true);
            fabToday.setClickable(true);
            isFabOpen=true;
        }
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        gMap = googleMap;
//        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.584,126.925),15));
//        gMap.getUiSettings().setZoomControlsEnabled(true);
//        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                videoMark=new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.marker2)).position(latLng,100f,100f);
//                gMap.addGroundOverlay(videoMark);
//            }
//        });
//
//    }
}
