package com.example.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class Datecourse extends Fragment implements ViewPager.PageTransformer {
    String id;
    ViewPager viewPager;

    public Datecourse(String _id) {
        id = _id;
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.datecourse, container, false);
        // Fragment로 넘길 Image Resource

        //ArrayList에 해당 image를 넣는다.
        FloatingActionButton search;

        search = layout.findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Datecourse_Search.class);
                startActivity(intent);
            }
        });

        final ViewPager viewPager = layout.findViewById(R.id.viewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getContext());
        // ViewPager와  FragmentAdapter 연결
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setPageMargin(20);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(120, 0, 120, 0);

        /*viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View view, float v) {
                int pageWidth = viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight(); //getMeasuredWidth()=>view의 부모의 크기를 가져온다.
                int pageHeight = viewPager.getHeight();
                int paddingLeft = viewPager.getPaddingLeft();
                float transformPos = (float) (view.getLeft() - (viewPager.getScrollX() + paddingLeft)) / pageWidth;

                if (transformPos < -1 || transformPos > 1) {
                    view.setScaleY(0.7f);
                } else {
                    view.setScaleY(1f);
                }
            }
        });*/
        return layout;
    }

    @Override
    public void transformPage(@NonNull View view, float v) {
    }

    public class FragmentAdapter extends PagerAdapter {
        private Context context;
        String date_image[] = new String[5];

        public FragmentAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return date_image.length;
        }

        @NonNull
        @Override
        //position-> ViewPager의 getCount()에서 얻어온 Count의 Position별로 Pager에 등록할 item을 처리할 수 있는 메서드
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
            final View view = LayoutInflater.from(context).inflate(R.layout.date_image, container, false);

            final ImageView imageView = view.findViewById(R.id.imageView);

            Call<List<ResponseDate_image>> res = Net.getInstance().getApi().getDate_image();
            res.enqueue(new Callback<List<ResponseDate_image>>() {
                @Override
                public void onResponse(Call<List<ResponseDate_image>> call, Response<List<ResponseDate_image>> response) {
                    if (response.isSuccessful()) {
                        List<ResponseDate_image> responseGet = response.body();
                        int i = 0;
                        for (ResponseDate_image responseImage : responseGet) {
                            date_image[i++] = responseImage.getDate_image();
                        }
                        Log.d("PPP",date_image[0]);
                        Glide.with(getContext()).load(date_image[position]).into(imageView);
                    }
                }

                @Override
                public void onFailure(Call<List<ResponseDate_image>> call, Throwable t) {
                    Log.d("UUU", "fail");
                }
            });
            container.addView(view);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DateImageClick.class);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }
}
