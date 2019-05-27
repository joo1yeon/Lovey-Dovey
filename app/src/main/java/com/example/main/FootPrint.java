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

import java.util.Calendar;

public class FootPrint extends Fragment {
    ImageView btnCal;
    TextView btnToday;
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);

    public FootPrint() {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.footprint, container, false);
        btnCal = layout.findViewById(R.id.btnCal);
        btnToday = layout.findViewById(R.id.btnToday);
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
/*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/
        /*주-ㅁㄴ오햠ㅇ냫8ㅔㅁㅇ냐ㅐㅗㅁ내ㅑ옿ㅁㄴㅇ호메8ㅇ녀헴ㄴ애ㅑㅠㅁㄴ야ㅕㅎㅁ냐ㅕ아ㅓ*/

        return layout;
    }


}
