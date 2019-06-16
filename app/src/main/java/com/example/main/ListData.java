package com.example.main;

import android.widget.CheckBox;

import java.text.Collator;
import java.util.Comparator;

public class ListData {

    public boolean isChecked = false;
    public CheckBox checkBox;
    public String content;
    public String date;

    //알파벳으로 이름 정렬
    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListDate_1, ListData mListDate_2) {
            return sCollator.compare(mListDate_1.content ,mListDate_2.content);
        }
    };

}
