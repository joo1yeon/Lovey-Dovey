package com.example.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
    //Alert Dialog 가 DialogFragment 의 인스턴스에 포함되어 같이 동작

    public DatePicker mDatePicker;
    OnDatePickerSetListener onDatePickerSetListener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        mDatePicker = v.findViewById(R.id.dialog_date_date_picker);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
//                .setTitle("날짜 선택")
                .setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth() + 1;
                        int day = mDatePicker.getDayOfMonth();
                        onDatePickerSetListener.onDatePickerSet(year, month, day);

                        Log.d("test", String.valueOf(month));
                    }
                })
                .create();
    }

    public interface OnDatePickerSetListener {
        void onDatePickerSet(int y, int m, int d);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDatePickerSetListener) {
            onDatePickerSetListener = (OnDatePickerSetListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDatePickerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDatePickerSetListener = null;
    }
}
