package com.example.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class FootPrint extends Fragment implements OnMapReadyCallback {
    ImageView btnCal;
    TextView btnToday;
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    GroundOverlayOptions videoMark;
    GoogleMap gMap;
    MapFragment mapFrag;
    public FootPrint() {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.footprint, container, false);
        btnCal = layout.findViewById(R.id.btnCal);
        btnToday = layout.findViewById(R.id.btnToday);
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);
        mapFrag = (MapFragment) getFragmentManager().findFragmentByTag(R.id.map);
        mapFrag.getMapAsync(this);


        btnCal.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.584,126.925),15));
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                videoMark=new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.marker2)).position(latLng,100f,100f);
                gMap.addGroundOverlay(videoMark);
            }
        });

    }
}
