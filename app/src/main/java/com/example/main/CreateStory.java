package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CreateStory extends AppCompatActivity {

    public Button btnConfirm;
    public ImageView icCalendar;
    public Story mStory;
    private static final String DIALOG_DATE = "DialogDate";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_story);

        btnConfirm = findViewById(R.id.btn_confirm);
        icCalendar = findViewById(R.id.ic_calendar);

        icCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new  DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });

    }
}
