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
import android.widget.EditText;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class FootPrint extends Fragment implements OnMapReadyCallback {
    String[] markerSrc={"marker1","marker2","marker3","marker4","marker5","marker6","marker7","marker8","marker9","marker10"};
    InsertDB insert;
    int index=0;
    String id = MainActivity.id;
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

    @Nullable
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_foot, container, false);

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
                printMarker(gMap,year, month, day);
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

        //TODO 마커저장버튼 클릭
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.getTime().getYear() + 1900;
                int month = cal.getTime().getMonth() + 1;
                int date = cal.getTime().getDate();
                Toast.makeText(getContext(), "마커 저장", Toast.LENGTH_SHORT).show();
                insert.save();
            }
        });

        //TODO 오늘 날짜 클릭 이벤트
        tvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        //TODO 날짜 이동 버튼(하루 전)
        btnYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.DATE, -1);
                tvToday.setText(sdf.format(cal.getTime()));
                printMarker(gMap, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
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
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.566660, 126.978393), 15));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        gMap.getUiSettings().setZoomControlsEnabled(false);
        gMap.getUiSettings().isMyLocationButtonEnabled();
        //TODO 지도위에 마커 찍기
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                int year = cal.getTime().getYear() + 1900;
                int month = cal.getTime().getMonth() + 1;
                int date = cal.getTime().getDate();
                Geocoder geocoder = new Geocoder(getContext());
                List<Address> list = null;
                try {
                    list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] splitStr = list.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);
                markerOptions.title(address);
                markerOptions.snippet(address);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(markerSrc[index], 150, 170)));
                markerOptions.position(latLng);
                gMap.addMarker(markerOptions);
                insert.insert(address, address, latLng.latitude, latLng.longitude, year, month, date,index);
                index++;
            }
        });
        //TODO 마커 클릭 이벤트1
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                final int year = cal.getTime().getYear() + 1900;
                final int month = cal.getTime().getMonth() + 1;
                final int date = cal.getTime().getDate();
                final View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                TextView placeAddress;
                final EditText placeName;
                placeName = view.findViewById(R.id.placeName);
                placeAddress = view.findViewById(R.id.placeAddress);
                placeName.setText(marker.getTitle());
                if (marker.getSnippet() != null) {
                    placeAddress.setText(marker.getSnippet());
                } else placeAddress.setText("");
                Button btnModifyMark, btnDelMark;
                btnModifyMark = view.findViewById(R.id.btnModifyMark);
                btnDelMark = view.findViewById(R.id.btnDelMark);
                //TODO 마커수정하기 버튼 클릭
                btnModifyMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<updateMark> r = Net.getInstance().getApi().getUpdateMark(placeName.getText().toString(), marker.getPosition().latitude, marker.getPosition().longitude, year, month, date, MainActivity.coupleID);

                        r.enqueue(new Callback<updateMark>() {
                            @Override
                            public void onResponse(Call<updateMark> call, Response<updateMark> response) {
                                if (response.body().getSuccess()) {
                                    printMarker(gMap, year, month, date);
                                    modalBottomSheet.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<updateMark> call, Throwable t) {

                            }
                        });
                    }
                });
                //TODO 마커삭제하기 버튼 클릭
                btnDelMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<deleteMark> res = Net.getInstance().getApi().getDeleteMark(marker.getTitle(), marker.getSnippet(), marker.getPosition().latitude, marker.getPosition().longitude, year, month, date, MainActivity.coupleID);
                        res.enqueue(new Callback<deleteMark>() {
                            @Override
                            public void onResponse(Call<deleteMark> call, Response<deleteMark> response) {
                                printMarker(gMap, year, month, date);
                                modalBottomSheet.dismiss();
                            }

                            @Override
                            public void onFailure(Call<deleteMark> call, Throwable t) {

                            }
                        });
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
        insert = (InsertDB) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int year = cal.getTime().getYear() + 1900;
            int month = cal.getTime().getMonth() + 1;
            int date = cal.getTime().getDate();
            String name = data.getStringExtra("name");
            Double latitude = data.getDoubleExtra("latitude", 0);
            Double longitude = data.getDoubleExtra("longitude", 0);
            String address = data.getStringExtra("address");
            LatLng point = new LatLng(latitude, longitude);
            markerOptions.title(name);
            markerOptions.snippet(address);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(markerSrc[index], 150, 170)));
            markerOptions.position(point);
            gMap.addMarker(markerOptions);
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(17));

            insert.insert(name, markerOptions.getSnippet(), latitude, longitude, year, month, date,index);
            index++;
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

    //TODO 마커 출력 메소드
    public void printMarker(GoogleMap _gMap, int year, int month, int day) {
        index=0;
        final GoogleMap gMap = _gMap;
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.566660, 126.978393), 15));

        gMap.clear();
        Call<List<ResponseMarker>> res = Net.getInstance().getApi().getMarker(MainActivity.coupleID, year, month, day);
        res.enqueue(new Callback<List<ResponseMarker>>() {
            @Override
            public void onResponse(Call<List<ResponseMarker>> call, Response<List<ResponseMarker>> response) {
                if (response.isSuccessful()) {
                    List<ResponseMarker> responseGet = response.body();
                    for (ResponseMarker responseMarker : responseGet) {
                        if (responseMarker.getName() != null && responseMarker.getAddress() != null && responseMarker.getLng() != 0 && responseMarker.getLat() != 0) {
                            LatLng point = new LatLng(responseMarker.getLat(), responseMarker.getLng());
                            markerOptions.title(responseMarker.getName());
                            markerOptions.snippet(responseMarker.getAddress());
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(markerSrc[index++], 150, 170)));
                            markerOptions.position(point);
                            gMap.addMarker(markerOptions);
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
                            gMap.animateCamera(CameraUpdateFactory.zoomTo(17));


                        } else {
                            gMap.clear();
                        }

                    }
                } else Toast.makeText(getContext(), "통신1 에러", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ResponseMarker>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

