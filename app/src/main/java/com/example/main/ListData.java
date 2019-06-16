package com.example.main;

import android.widget.CheckBox;

public class ListData {

    public CheckBox checkBox;
    public String content;
    public String date;

    /**
     * 알파벳 이름으로 정렬

    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListDate_1, ListData mListDate_2) {
            return sCollator.compare(mListDate_1.mTitle, mListDate_2.mTitle);
        }
    };*/

}
