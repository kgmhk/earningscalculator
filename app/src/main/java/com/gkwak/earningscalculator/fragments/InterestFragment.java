package com.gkwak.earningscalculator.fragments;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkwak.earningscalculator.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class InterestFragment extends Fragment {

    private static String TAG = "INTEREST_FRAGMENT";
    private EditText loan_price_edit, loan_rate_edit, loan_duration_edit;
    private Spinner  loan_method_spinner;
    private TextView loan_price_result_edit, loan_rate_result_edit, loan_duration_result_edit,
        loan_method_result_edit, monthly_interest_result_edit, repayment_result_edit, total_interest_result_edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_interest, container, false);

        loan_price_result_edit = (TextView) rootView.findViewById(R.id.loan_price_result_edit);
        loan_rate_result_edit = (TextView) rootView.findViewById(R.id.loan_rate_result_edit);
        loan_duration_result_edit = (TextView) rootView.findViewById(R.id.loan_duration_result_edit);
        loan_method_result_edit = (TextView) rootView.findViewById(R.id.loan_method_result_edit);
        monthly_interest_result_edit = (TextView) rootView.findViewById(R.id.monthly_interest_result_edit);
        repayment_result_edit = (TextView) rootView.findViewById(R.id.repayment_result_edit);
        total_interest_result_edit = (TextView) rootView.findViewById(R.id.total_interest_result_edit);

        loan_price_edit = (EditText) rootView.findViewById(R.id.loan_price_edit);
        loan_rate_edit = (EditText) rootView.findViewById(R.id.loan_rate_edit);
        loan_duration_edit = (EditText) rootView.findViewById(R.id.loan_duration_edit);

        // Spinner
        loan_method_spinner = (Spinner) rootView.findViewById(R.id.loan_method_spinner);
        ArrayAdapter loanMethodAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.loan_method, android.R.layout.simple_spinner_item);
        loanMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loan_method_spinner.setAdapter(loanMethodAdapter);
        loan_method_spinner.setOnItemSelectedListener(mOnItemSelectedListener);

        // 1st Layout
        final DecimalFormat df = new DecimalFormat("###,###.####");
        final String[] loan_price_edit_temp_result = {""};
        final String[] loan_rate_edit_temp_result = {""};
        final String[] loan_duration_edit_temp_result = {""};

        loan_price_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){

                loan_method_spinner.setSelection(0);
                if(!s.toString().equals(loan_price_edit_temp_result[0])){     // StackOverflow를 막기위해,
                    if (s.length() == 0) return;
                    loan_price_edit_temp_result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    loan_price_edit.setText(loan_price_edit_temp_result[0]);    // 결과 텍스트 셋팅.
                    loan_price_edit.setSelection(loan_price_edit_temp_result[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });
        loan_rate_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                loan_method_spinner.setSelection(0);
            }
        });
        loan_duration_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                loan_method_spinner.setSelection(0);
            }
        });

        return rootView;
    }

    private AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Log.i(TAG, "onItemSelected() entered!!");
            String selItem= (String) loan_method_spinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);

            if (loan_price_edit.getText().toString().length() == 0) {
                loan_price_edit.requestFocus();
                loan_method_spinner.setSelection(0);
                initMethod();
                return;
            } else if (loan_rate_edit.getText().toString().length() == 0) {
                loan_rate_edit.requestFocus();
                loan_method_spinner.setSelection(0);
                initMethod();
                return;
            } else if (loan_duration_edit.getText().toString().length() == 0) {
                loan_duration_edit.requestFocus();
                loan_method_spinner.setSelection(0);
                initMethod();
                return;
            }

            Long loanPrice = 0L;
            Float loanRate = 0F;
            Long loanDuration = 0L;
            if (loan_price_edit.getText().toString().length() != 0)
                loanPrice = Long.parseLong(loan_price_edit.getText().toString().replaceAll(",", ""));
            if (loan_rate_edit.getText().toString().length() != 0)
                loanRate = Float.parseFloat(loan_rate_edit.getText().toString().replaceAll(",", ""));
            if (loan_duration_edit.getText().toString().length() != 0)
                loanDuration = Long.parseLong(loan_duration_edit.getText().toString().replaceAll(",", ""));

            switch (position) {
                case 0:
                    Log.i(TAG, "select position 0");
                    initMethod();
                    return;
                case 1:
                    Log.i(TAG, "select position 1");
                    calFirstMethod(loanPrice, loanRate, loanDuration, selItem);
                    return;
                case 2:
                    Log.i(TAG, "select position 2");
                    calSecondMethod(loanPrice, loanRate, loanDuration, selItem);
                    return;
                case 3:
                    Log.i(TAG, "select position 3");
                    calThirdMethod(loanPrice, loanRate, loanDuration, selItem);
                    return;
                default:
                    Log.i(TAG, "select default");
                    initMethod();
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };

    private void initMethod() {
        loan_price_result_edit.setText("");
        loan_rate_result_edit.setText("");
        loan_duration_result_edit.setText("");
        loan_method_result_edit.setText("");
        monthly_interest_result_edit.setText("");
        repayment_result_edit.setText("");
        total_interest_result_edit.setText("");
    }

    private void calFirstMethod(Long loanPrice, Float loanRate, Long loanDuration, String selItem) {
        Log.i(TAG, "calFirstMethod");
        Log.i(TAG, "loanPrice : " + loanPrice);
        Log.i(TAG, "loanRate : " + loanRate);
        Log.i(TAG, "loanDuration : " + loanDuration);
        loan_price_result_edit.setText(loanPrice.toString());
        loan_rate_result_edit.setText(loanRate.toString());
        loan_duration_result_edit.setText(loanDuration.toString());
        loan_method_result_edit.setText(selItem);
    }
    private void calSecondMethod(Long loanPrice, Float loanRate, Long loanDuration, String selItem) {
        Log.i(TAG, "calSecondMethod");
        Log.i(TAG, "loanPrice : " + loanPrice);
        Log.i(TAG, "loanRate : " + loanRate);
        Log.i(TAG, "loanDuration : " + loanDuration);
        loan_price_result_edit.setText(loanPrice.toString());
        loan_rate_result_edit.setText(loanRate.toString());
        loan_duration_result_edit.setText(loanDuration.toString());
        loan_method_result_edit.setText(selItem);
    }
    private void calThirdMethod(Long loanPrice, Float loanRate, Long loanDuration, String selItem) {
        Log.i(TAG, "calThirdMethod");
        Log.i(TAG, "loanPrice : " + loanPrice);
        Log.i(TAG, "loanRate : " + loanRate);
        Log.i(TAG, "loanDuration : " + loanDuration);
        loan_price_result_edit.setText(loanPrice.toString());
        loan_rate_result_edit.setText(loanRate.toString());
        loan_duration_result_edit.setText(loanDuration.toString());
        loan_method_result_edit.setText(selItem);


        Long totalInterest  = (long)((float)loanPrice * (loanRate / 100) * (float)(loanDuration/12));
        Long repayment = totalInterest / loanDuration;
        Long monthlyInterest = totalInterest / loanDuration;
        monthly_interest_result_edit.setText(monthlyInterest.toString());
        repayment_result_edit.setText(repayment.toString());
        total_interest_result_edit.setText(totalInterest.toString());
    }
}
