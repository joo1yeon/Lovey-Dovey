package com.example.main;

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
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.http.HEAD;

public class Datecourse extends Fragment implements ViewPager.PageTransformer {
    public Datecourse() {
    }
    public String[] image={"http://mjckjs.gabia.io//whispering/image/datecourse/date_bloosom1.jpg",
            "http://mjckjs.gabia.io//whispering/image/datecourse/date_bar1.jpg"};
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

        //ArrayList에 해당 image를 넣는다.
        //ArrayList<Integer> listImage = new ArrayList<>();
        /*listImage.add(R.drawable.image1);
        listImage.add(R.drawable.image2);
        listImage.add(R.drawable.image3);
        listImage.add(R.drawable.image4);*/

        final ViewPager viewPager = layout.findViewById(R.id.viewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager());
        // ViewPager와  FragmentAdapter 연결
        viewPager.setAdapter(fragmentAdapter);

        viewPager.setPageMargin(20);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(120, 0, 120, 0);

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
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
        });

        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
       /* for (int i = 0; i <image.length; i++) {
            DateImage imageFragment = new DateImage();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }
        fragmentAdapter.notifyDataSetChanged();*/
        return layout;
    }

    @Override
    public void transformPage(@NonNull View view, float v) {

    }

    class FragmentAdapter extends FragmentPagerAdapter {

        // ViewPager에 들어갈 Fragment들을 담을 리스트
        private ArrayList<Fragment> fragments = new ArrayList<>();
        private Context context;
        /*public String[] image={"http://mjckjs.gabia.io//whispering/image/datecourse/date_bloosom1.jpg",
                "http://mjckjs.gabia.io//whispering/image/datecourse/date_bar1.jpg"};*/

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
           return image.length;
        }

        // List에 Fragment를 담을 함수
        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view=LayoutInflater.from(context).inflate(R.layout.date_image,container,false);

            ImageView imageView=view.findViewById(R.id.imageView);
            Glide.with(context).load(image[position]).into(imageView);
            container.addView(view);
            return  view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
