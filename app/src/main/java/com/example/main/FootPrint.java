package com.example.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class FootPrint extends Fragment {
    ImageView btnCal;
    TextView btnToday;
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    GroundOverlayOptions videoMark;
//    GoogleMap gMap;
//    MapFragment mapFrag;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton btnFab, fabSearch, fabCal, fabToday;

    public FootPrint() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.footprint, container, false);
        btnCal = layout.findViewById(R.id.btnCal);
        btnToday = layout.findViewById(R.id.btnToday);
        btnCal.setOnClickListener(new View.OnClickListener() {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_foot, container, false);
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
                Intent intent = new Intent(getContext(), LocSearch.class);
                startActivity(intent);
                Toast.makeText(getContext(),"장소 검색하기",Toast.LENGTH_SHORT).show();
            }
        });

        //TODO DatePickerDialog 출력
        fabCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(getContext(), year + "년도 " + (month + 1) + "월 " + dayOfMonth + "일", Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
                dateDialog.show();
            }
        });

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "오늘 날짜로 이동", Toast.LENGTH_SHORT).show();
            }
        });

        return layout;
    }


}
