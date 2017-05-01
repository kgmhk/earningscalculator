package com.gkwak.earningscalculator.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkwak.earningscalculator.R;

import java.text.DecimalFormat;

public class AcquisitionTaxFragment extends Fragment {
    private Spinner  squareMethodSpinner, acquisitionTypeSpinner;
    private static String TAG = "ACQUISITION_FRAGMENT";
    private static Double PYEONG_TO_METER = 3.305785;
    private int squareMathodePosition, acquisitionTypePosition;
    private TextView acquisition_square_mark_text, calAcquisitionPriceText, calAcquisitionRateText;
    private Button calAcquisitionBtn;
    private EditText acquisitionSquareEdit, acquisitionPriceEdit;
    private LinearLayout acquisitionSquareLayout, acquisitionSquaredMethodLayout;
    final DecimalFormat df = new DecimalFormat("###,###.####");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_acquisition_tax, container, false);

        acquisitionSquareLayout = (LinearLayout) rootView.findViewById(R.id.acquisition_square_layout);
        acquisitionSquaredMethodLayout = (LinearLayout) rootView.findViewById(R.id.acquisition_square_method_layout);

        acquisition_square_mark_text = (TextView) rootView.findViewById(R.id.acquisition_square_mark_text);
        calAcquisitionPriceText = (TextView) rootView.findViewById(R.id.cal_acquisition_price_edit);
        calAcquisitionRateText = (TextView) rootView.findViewById(R.id.cal_acquisition_rate_edit);
        calAcquisitionBtn = (Button) rootView.findViewById(R.id.cal_acquisition_btn);
        acquisitionSquareEdit = (EditText) rootView.findViewById(R.id.acquisition_square_edit);
        acquisitionPriceEdit = (EditText) rootView.findViewById(R.id.acquisition_deal_price_edit);

        acquisitionTypeSpinner = (Spinner) rootView.findViewById(R.id.acquisition_type_spinner);
        ArrayAdapter acquisitionTypeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.acquisition_type, android.R.layout.simple_spinner_item);
        acquisitionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acquisitionTypeSpinner.setAdapter(acquisitionTypeAdapter);
        acquisitionTypeSpinner.setOnItemSelectedListener(mOnAcquisitionTypeSelectedListener);

        squareMethodSpinner = (Spinner) rootView.findViewById(R.id.acquisition_square_method_spinner);
        ArrayAdapter squareMethodAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.square_method, android.R.layout.simple_spinner_item);
        squareMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        squareMethodSpinner.setAdapter(squareMethodAdapter);
        squareMethodSpinner.setOnItemSelectedListener(mOnSquareMethodSelectedListener);

        final String[] acquisitionSquareEditTempResult = {""};
        final String[] acquisitionPriceEditTempResult = {""};

        acquisitionPriceEdit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){

                if(!s.toString().equals(acquisitionPriceEditTempResult[0])){     // StackOverflow를 막기위해,
                    if (s.length() == 0) return;
                    acquisitionPriceEditTempResult[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    acquisitionPriceEdit.setText(acquisitionPriceEditTempResult[0]);    // 결과 텍스트 셋팅.
                    acquisitionPriceEdit.setSelection(acquisitionPriceEditTempResult[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });

        acquisitionSquareEdit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
//
//                if(!s.toString().equals(acquisitionSquareEditTempResult[0])){     // StackOverflow를 막기위해,
//                    if (s.length() == 0) return;
//                    acquisitionSquareEditTempResult[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
//                    acquisitionSquareEdit.setText(acquisitionSquareEditTempResult[0]);    // 결과 텍스트 셋팅.
//                    acquisitionSquareEdit.setSelection(acquisitionSquareEditTempResult[0].length());     // 커서를 제일 끝으로 보냄.
//                }
            }
        });

        calAcquisitionBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (acquisitionPriceEdit.getText().length() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.acquisition_dealing_price_warning_toast), Toast.LENGTH_LONG).show();
                    acquisitionPriceEdit.requestFocus();
                }

                if (acquisitionSquareEdit.getText().length() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.acquisition_square_warning_toast), Toast.LENGTH_LONG).show();
                    acquisitionSquareEdit.requestFocus();
                }

                double square;
                Long price;

                square =  Double.parseDouble(acquisitionSquareEdit.getText().toString());
                price = Long.parseLong(acquisitionPriceEdit.getText().toString().replaceAll(",", ""));;
                switch (squareMathodePosition) {
                    case 1:
                        calAcquisition(convertPyeongToMeter(square), price);
                        return;
                    default:
                        calAcquisition(square, price);
                        return;
                }

            }
        });

        return rootView;
    }

    private double convertPyeongToMeter(double pyeong) {
        Double pyeongToMeter = pyeong * PYEONG_TO_METER;
        return pyeongToMeter;
    }

    private void calAcquisition(double square, Long price) {
        switch (acquisitionTypePosition) {
            case 0:
                calAcquisitionHouse(square, price);
                return;
            case 1:
                calAcquisitionExceptionHouse(price);
                return;
            case 2:
                calAcquisitionInherit(price);
                return;
            case 3:
                calAcquisitionFree(price);
                return;
            default:
                calAcquisitionHouse(square, price);
                return;
        }
    }

    private void calAcquisitionFree(Long price) {
        double result = price * 0.04;
        calAcquisitionPriceText.setText(df.format(result));
        calAcquisitionRateText.setText("4");
    }

    private void calAcquisitionInherit(Long price) {
        double result = price * 0.0316;
        calAcquisitionPriceText.setText(df.format(result));
        calAcquisitionRateText.setText("3.16");
    }

    private void calAcquisitionExceptionHouse(Long price) {
        double result = price * 0.046;
        calAcquisitionPriceText.setText(df.format(result));
        calAcquisitionRateText.setText("4.6");
    }

    private void calAcquisitionHouse(double square, Long price) {
        if (price <= 600000000 && square <= 85) {
            double result = price * 0.011;
            calAcquisitionPriceText.setText(df.format(result));
            calAcquisitionRateText.setText("1.1");
        } else if (price <= 600000000 && square > 85) {
            double result = price * 0.013;
            calAcquisitionPriceText.setText(df.format(result));
            calAcquisitionRateText.setText("1.3");
        } else if ((price > 600000000 && price <= 900000000) && square <= 85) {
            Log.i(TAG, "85이하 " + square);
            double result = price * 0.022;
            calAcquisitionPriceText.setText(df.format(result));
            calAcquisitionRateText.setText("2.2");
        } else if ((price > 600000000 && price <= 900000000) && square > 85) {
            Log.i(TAG, "85초과 " + square);
            double result = price * 0.024;
            calAcquisitionPriceText.setText(df.format(result));
            calAcquisitionRateText.setText("2.4");
        } else if (price > 900000000 && square <= 85) {
            double result = price * 0.033;
            calAcquisitionPriceText.setText(df.format(result));
            calAcquisitionRateText.setText("3.3");
        } else {
            double result = price * 0.035;
            calAcquisitionPriceText.setText(df.format(result));
            calAcquisitionRateText.setText("3.5");
        }
    }

    private AdapterView.OnItemSelectedListener mOnAcquisitionTypeSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Log.i(TAG, "onItemSelected() entered!!");
            String selItem= (String) acquisitionTypeSpinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);
            acquisitionTypePosition = position;

            switch (position) {
                case 0:
                    acquisitionSquareLayout.setVisibility(View.VISIBLE);
                    acquisitionSquaredMethodLayout.setVisibility(View.VISIBLE);
                    return;
                default:
                    acquisitionSquareLayout.setVisibility(View.INVISIBLE);
                    acquisitionSquaredMethodLayout.setVisibility(View.INVISIBLE);
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };

    private AdapterView.OnItemSelectedListener mOnSquareMethodSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Log.i(TAG, "onItemSelected() entered!!");
            String selItem= (String) squareMethodSpinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);

            squareMathodePosition = position;

            switch (position) {
                case 0:
                    acquisition_square_mark_text.setText(getResources().getString(R.string.square_meter_symbol));
                    return;
                case 1:
                    Log.i(TAG, "select position 1");
                    acquisition_square_mark_text.setText(getResources().getString(R.string.pyeong_symbol));
                    return;
                default:
                    acquisition_square_mark_text.setText(getResources().getString(R.string.square_meter_symbol));
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };
}
