package com.gkwak.earningscalculator.utils;

import android.widget.EditText;
import android.widget.TextView;

public class Text {
    public static void resetEditText(EditText editText) {
        editText.setText("");
    }
    public static void resetTextView(TextView textView) { textView.setText("0"); }
}
