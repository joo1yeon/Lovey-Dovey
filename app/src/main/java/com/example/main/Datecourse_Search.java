package com.example.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Datecourse_Search extends AppCompatActivity {

    View background;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datecourse_search);

        ImageButton btnHome = findViewById(R.id.btnHome);
        background = findViewById(R.id.background);
        final boolean[] color = {false};

        editText = findViewById(R.id.editText);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color[0] = true;
                if (color[0] == true) {
                    background.setBackgroundColor(Color.rgb(189, 189, 189));
                }
                background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        background.setBackgroundColor(Color.rgb(255, 255, 255));
                    }
                });
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Datecourse_Search.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
