package com.example.main;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FootPrint extends Fragment implements OnMapReadyCallback {
    ImageButton btnTomorrow, btnYesterday;
    TextView tvToday;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DATE);
    MapView map;
    Date today = cal.getTime();
    GroundOverlayOptions videoMark;
    GoogleMap gMap;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton btnFab, fabSearch, fabCal, fabToday;
    ListView listView;
    private BottomSheetDialog modalBottomSheet;

    public FootPrint() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_foot, container, false);
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);

        btnFab = layout.findViewById(R.id.btnFab);
        fabToday = layout.findViewById(R.id.fabToday);
        fabSearch = layout.findViewById(R.id.fabSearch);
        fabCal = layout.findViewById(R.id.fabCal);
        tvToday = layout.findViewById(R.id.tvToday);
        btnTomorrow = layout.findViewById(R.id.btnTomorrow);
        btnYesterday = layout.findViewById(R.id.btnYesterday);
        tvToday.setText(sdf.format(cal.getTime()));
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
                tvToday.setText(sdf.format(today));

            }
        });

        //TODO 장소 검색 화면으로 이동
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent = new Intent(getContext(), LocSearch.class);
                startActivity(intent);
                Toast.makeText(getContext(), "장소 검색하기", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "선택한 날짜로 이동합니당", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "어제 날짜로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO 날짜 이동 버튼(하루 후)
        btnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.DATE, +1);
                tvToday.setText(sdf.format(cal.getTime()));
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.584, 126.925), 15));
        gMap.getUiSettings().setZoomControlsEnabled(false);
        gMap.getUiSettings().isMyLocationButtonEnabled();
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("mark",100,120)));
                markerOptions.position(latLng);

                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        View view = getLayoutInflater().inflate(R.layout.bottom_sheet,null);
                        Button  btnAddPicture,btnDelMark;
                        btnAddPicture=view.findViewById(R.id.btnAddPicture);
                        btnDelMark=view.findViewById(R.id.btnDelMark);
                        btnAddPicture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(),"사진추가하기",Toast.LENGTH_SHORT).show();
                            }
                        });
                        btnDelMark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                marker.remove();
                                modalBottomSheet.dismiss();
                            }
                        });
                        modalBottomSheet = new BottomSheetDialog(getContext());
                        modalBottomSheet.setContentView(view);
                        modalBottomSheet.show();
                        return false;
                    }
                });
                gMap.addMarker(markerOptions);
            }
        });
    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
//    class MyGeocodingThread extends Thread {
//        LatLng latLng;
//
//        public MyGeocodingThread(LatLng _latLng) {
//            latLng = _latLng;
//        }
//
//        @Override
//        public void run() {
//            Geocoder geocoder = new Geocoder(getContext());
//            List<Address> addresses = null;
//            String addressText = "";
//            try {
//                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                Thread.sleep(500);
//                if (addresses != null && addresses.size() > 0) {
//                    Address address = addresses.get(0);
//                    addressText = address.getAdminArea() + "" + (address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : address.getLocality()) + "";
//                    String txt = address.getSubLocality();
//                    if (txt != null) addressText += txt + "";
//                    addressText += address.getThoroughfare() + "" + address.getSubThoroughfare();
//
//                    Message msg = new Message();
//                    msg.what = 100;
//                    msg.obj = addressText;
//                    handler.sendMessage(msg);
//                }
//            } catch (Exception e) {
//            }
//        }
//    }
//
//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 100:
//                    Toast.makeText(getContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };
//

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
}

