package com.gkwak.earningscalculator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gkwak.earningscalculator.R;

public class SquareMeterFragment extends Fragment {
    private static String TAG = "INTEREST_FRAGMENT";
    private static Double METER_TO_PYEONG = 0.3025;
    private static Double METER_TO_FOOT = 10.76391;
    private static Double METER_TO_YARD = 1.19599;
    private static Double PYEONG_TO_METER = 3.305785;
    private static Double PYEONG_TO_FOOT = 35.583175;
    private static Double PYEONG_TO_YARD = 3.953686;
    private static Double FOOT_TO_PYEONG = 0.028103;
    private static Double FOOT_TO_METER = 0.092903;
    private static Double FOOT_TO_YARD = 0.111111;
    private static Double YARD_TO_PYEONG = 0.252929;
    private static Double YARD_TO_METER = 0.836127;
    private static Double YARD_TO_FOOT = 9.0;
    private TextView meterToPyeongResultText, meterToFootResultText, meterToYardResultText;
    private TextView pyeongToMeterResultText, pyeongToFootResultText, pyeongToYardResultText;
    private TextView footToPyeongResultText, footToMeterResultText, footToYardResultText;
    private TextView yardToPyeongResultText, yardToMeterResultText, yardToFootResultText;
    private EditText squareMeterEdit, pyeongEdit, footEdit, yardEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_square_meter, container, false);

        squareMeterEdit = (EditText) rootView.findViewById(R.id.square_meter_edit);
        pyeongEdit = (EditText) rootView.findViewById(R.id.pyeong_edit);
        footEdit = (EditText) rootView.findViewById(R.id.foot_edit);
        yardEdit = (EditText) rootView.findViewById(R.id.yard_edit);

        meterToPyeongResultText = (TextView) rootView.findViewById(R.id.square_meter_to_pyeong_result_text);
        meterToFootResultText = (TextView) rootView.findViewById(R.id.square_meter_to_square_foot_result_text);
        meterToYardResultText = (TextView) rootView.findViewById(R.id.square_meter_to_square_yard_result_text);

        pyeongToMeterResultText = (TextView) rootView.findViewById(R.id.pyeong_to_square_meter_result_text);
        pyeongToFootResultText = (TextView) rootView.findViewById(R.id.pyeong_to_square_foot_result_text);
        pyeongToYardResultText = (TextView) rootView.findViewById(R.id.pyeong_to_square_yard_result_text);

        footToPyeongResultText = (TextView) rootView.findViewById(R.id.square_foot_to_pyeong_result_text);
        footToMeterResultText = (TextView) rootView.findViewById(R.id.square_foot_to_square_meter_result_text);
        footToYardResultText = (TextView) rootView.findViewById(R.id.square_foot_to_square_yard_result_text);

        yardToPyeongResultText = (TextView) rootView.findViewById(R.id.square_yard_to_pyeong_result_text);
        yardToMeterResultText = (TextView) rootView.findViewById(R.id.square_yard_to_square_meter_result_text);
        yardToFootResultText = (TextView) rootView.findViewById(R.id.square_yard_to_square_foot_result_text);

        squareMeterEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    meterToPyeongResultText.setText("");
                    meterToFootResultText.setText("");
                    meterToYardResultText.setText("");
                    return;
                }
                Float squareMeter = Float.parseFloat(s.toString());
                Double meterToPyeong = squareMeter * METER_TO_PYEONG;
                Double meterToFoot = squareMeter * METER_TO_FOOT;
                Double meterToYard = squareMeter * METER_TO_YARD;

                meterToPyeongResultText.setText(String.format("%.2f", meterToPyeong));
                meterToFootResultText.setText(String.format("%.2f", meterToFoot));
                meterToYardResultText.setText(String.format("%.2f", meterToYard));
            }
        });

        pyeongEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    pyeongToMeterResultText.setText("");
                    pyeongToFootResultText.setText("");
                    pyeongToYardResultText.setText("");
                    return;
                }
                Float pyeong = Float.parseFloat(s.toString());
                Double pyeongToMeter = pyeong * PYEONG_TO_METER;
                Double pyeongToFoot = pyeong * PYEONG_TO_FOOT;
                Double pyeongToYard = pyeong * PYEONG_TO_YARD;

                pyeongToMeterResultText.setText(String.format("%.2f", pyeongToMeter));
                pyeongToFootResultText.setText(String.format("%.2f", pyeongToFoot));
                pyeongToYardResultText.setText(String.format("%.2f", pyeongToYard));
            }
        });

        footEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    footToPyeongResultText.setText("");
                    footToMeterResultText.setText("");
                    footToYardResultText.setText("");
                    return;
                }
                Float foot = Float.parseFloat(s.toString());
                Double footToPyeong = foot * FOOT_TO_PYEONG;
                Double footToMeter = foot * FOOT_TO_METER;
                Double footToYard = foot * FOOT_TO_YARD;

                footToPyeongResultText.setText(String.format("%.2f", footToPyeong));
                footToMeterResultText.setText(String.format("%.2f", footToMeter));
                footToYardResultText.setText(String.format("%.2f", footToYard));
            }
        });

        yardEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    yardToPyeongResultText.setText("");
                    yardToMeterResultText.setText("");
                    yardToFootResultText.setText("");
                    return;
                }
                Float yard = Float.parseFloat(s.toString());
                Double yardToPyeong = yard * YARD_TO_PYEONG;
                Double yardToMeter = yard * YARD_TO_METER;
                Double yardToFoot = yard * YARD_TO_FOOT;

                yardToPyeongResultText.setText(String.format("%.2f", yardToPyeong));
                yardToMeterResultText.setText(String.format("%.2f", yardToMeter));
                yardToFootResultText.setText(String.format("%.2f", yardToFoot));
            }
        });

        return rootView;
    }
}
