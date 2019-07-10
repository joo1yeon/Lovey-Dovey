package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class Datecourse_Search extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datecourse_search);

        String[] items={"벚꽃 명소","홍대 맛집","루프탑","벚꽃 명소2","홍대 놀거리","벚꽃 보러간당"};

        AutoCompleteTextView editText= findViewById(R.id.editText);

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,items);
        editText.setAdapter(adapter);
    }
}
