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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;


public class Datecourse extends Fragment implements ViewPager.PageTransformer {
    public Datecourse() {
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
        return layout;
    }

    @Override
    public void transformPage(@NonNull View view, float v) {

    }

    public class FragmentAdapter extends PagerAdapter {

        private Context context;
        private String[] image={"http://mjckjs.gabia.io//whispering/image/datecourse/date_bloosom1.jpg",
                "http://mjckjs.gabia.io//whispering/image/datecourse/date_bar1.jpg",
                "http://mjckjs.gabia.io//whispering/image/datecourse/date_park1.jpg",
                "http://mjckjs.gabia.io//whispering/image/datecourse/date_rooftop1.jpg",
                "http://mjckjs.gabia.io//whispering/image/datecourse/date_view1.jpg"};

        public FragmentAdapter(Context context){
            this.context=context;
        }
        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view=LayoutInflater.from(context).inflate(R.layout.date_image,container,false);

            ImageView imageView=view.findViewById(R.id.imageView);
            Glide.with(context).load(image[position]).into(imageView);
            container.addView(view);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DateImageClick.class);
                    startActivity(intent);
                }
            });
            return  view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
