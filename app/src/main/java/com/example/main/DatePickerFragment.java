package com.example.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment {

    public DatePicker mDatePicker;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        mDatePicker = v.findViewById(R.id.dialog_date_date_picker);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
<<<<<<< Updated upstream
                .setTitle("날짜 선택")
                .setPositiveButton("선택", null)
=======
                .setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();

                        Log.d("test", String.valueOf(year));

                        Intent intent = new Intent(getContext(), CreateStory.class);
                        intent.putExtra("Year", year);
                        intent.putExtra("Month", month);
                        intent.putExtra("Day", day);
                        startActivity(intent);
                    }
                })
>>>>>>> Stashed changes
                .create();
    }
}
