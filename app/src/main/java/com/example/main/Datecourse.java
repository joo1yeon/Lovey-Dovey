package com.example.main;

import android.graphics.Bitmap;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.http.HEAD;

public class Datecourse extends Fragment {
    Main mainFrag;

    public Datecourse(){}

    RequestQueue queue;
    NetworkImageView imageView;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.datecourse,container,false);
        // Fragment로 넘길 Image Resource

        //ArrayList에 해당 image를 넣는다.
        FloatingActionButton search;

        search=layout.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Datecourse_Search.class);
                startActivity(intent);
            }
        });

        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.image1);
        listImage.add(R.drawable.image2);
        listImage.add(R.drawable.image3);
        listImage.add(R.drawable.image4);

      /* imageView=layout.findViewById(R.id.imageView);

        JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.GET, "http://10.0.2.2/teamProject/test.json", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String imgFile = response.getString("file");
                            if (imgFile != null && !imgFile.equals("")) {
                                ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
                                    @Override
                                    public Bitmap getBitmap(String url) {
                                        return null; }
                                    @Override
                                    public void putBitmap(String url, Bitmap bitmap) { }
                                });
                                imageView.setImageUrl("http://10.0.2.2/teamProject/" + imgFile, imageLoader);
                            }
                        } catch (Exception e) { }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", error.toString());
            }
        });
        
        queue = (RequestQueue) Volley.newRequestQueue(getContext());
        queue.add(jsonRequest);*/

        ViewPager viewPager = layout.findViewById(R.id.viewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager());
        // ViewPager와  FragmentAdapter 연결
        viewPager.setAdapter(fragmentAdapter);

        viewPager.setClipToPadding(false);
        int dpValue = 60;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
        for (int i = 0; i < listImage.size(); i++) {
            DateImage imageFragment = new DateImage();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }
        fragmentAdapter.notifyDataSetChanged();
        return layout;
    }
    class FragmentAdapter extends FragmentPagerAdapter {

        // ViewPager에 들어갈 Fragment들을 담을 리스트
        private ArrayList<Fragment> fragments = new ArrayList<>();

        // 필수 생성자
        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        // List에 Fragment를 담을 함수
        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }
    }
}