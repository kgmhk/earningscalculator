package com.gkwak.earningscalculator.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gkwak.earningscalculator.R;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class InterestFragment extends Fragment {

    private static String TAG = "INTEREST_FRAGMENT";
    private EditText loan_price_edit, loan_rate_edit, loan_duration_edit;
    private Spinner  loan_method_spinner;
    private TextView loan_price_result_edit, loan_rate_result_edit, loan_duration_result_edit,
        loan_method_result_edit, monthly_interest_result_edit, repayment_result_edit, total_interest_result_edit,
            original_payment_result_edit;
    private LinearLayout payment_monthly_loan_interest_result_layout, monthly_interest_result_layout,
            repayment_result_layout, original_payment_result_layout;
    final DecimalFormat df = new DecimalFormat("###,###.####");
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
        original_payment_result_edit = (TextView) rootView.findViewById(R.id.original_payment_result_edit);

        loan_price_edit = (EditText) rootView.findViewById(R.id.loan_price_edit);
        loan_rate_edit = (EditText) rootView.findViewById(R.id.loan_rate_edit);
        loan_duration_edit = (EditText) rootView.findViewById(R.id.loan_duration_edit);

        payment_monthly_loan_interest_result_layout = (LinearLayout) rootView.findViewById(R.id.payment_monthly_loan_interest_result_layout);
        monthly_interest_result_layout = (LinearLayout) rootView.findViewById(R.id.monthly_interest_result_layout);
        repayment_result_layout = (LinearLayout) rootView.findViewById(R.id.repayment_result_layout);
        original_payment_result_layout = (LinearLayout) rootView.findViewById(R.id.original_payment_result_layout);

        // Spinner
        loan_method_spinner = (Spinner) rootView.findViewById(R.id.loan_method_spinner);
        ArrayAdapter loanMethodAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.loan_method, android.R.layout.simple_spinner_item);
        loanMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loan_method_spinner.setAdapter(loanMethodAdapter);
        loan_method_spinner.setOnItemSelectedListener(mOnItemSelectedListener);

        // 1st Layout

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
            } else if (Integer.parseInt(loan_duration_edit.getText().toString()) < 12) {
                Toast.makeText(getActivity(), getResources().getString(R.string.loan_duration_warning_toast), Toast.LENGTH_LONG).show();
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
                    initMethod();
                    calFirstMethod(loanPrice, loanRate, loanDuration, selItem);
                    return;
                case 2:
                    Log.i(TAG, "select position 2");
                    initMethod();
                    calSecondMethod(loanPrice, loanRate, loanDuration, selItem);
                    return;
                case 3:
                    Log.i(TAG, "select position 3");
                    initMethod();
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
        if (payment_monthly_loan_interest_result_layout.getChildCount() > 0)
            payment_monthly_loan_interest_result_layout.removeAllViews();
    }

    private void calFirstMethod(Long loanPrice, Float loanRate, Long loanDuration, String selItem) {
        Log.i(TAG, "calFirstMethod");
        Log.i(TAG, "loanPrice : " + loanPrice);
        Log.i(TAG, "loanRate : " + loanRate);
        Log.i(TAG, "loanDuration : " + loanDuration);
        monthly_interest_result_layout.setVisibility(View.GONE);
        repayment_result_layout.setVisibility(View.GONE);
        original_payment_result_layout.setVisibility(View.VISIBLE);
        String currency = getResources().getString(R.string.currency);
        Long calInterestResult = 0L;
        Long calOriginalResult = loanPrice / loanDuration;
        Long calInterestTotalResult = 0L;

        loan_price_result_edit.setText(df.format(loanPrice));
        loan_rate_result_edit.setText(df.format(loanRate));
        loan_duration_result_edit.setText(df.format(loanDuration));
        loan_method_result_edit.setText(selItem);
        //상환금
        original_payment_result_edit.setText(df.format(calOriginalResult));
        /* Find Tablelayout defined in main.xml */

        // row layout
        int leftMargin=0;
        int topMargin=0;
        int rightMargin=0;
        int bottomMargin=15;
        TableLayout.LayoutParams tableRowParams=
                new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT, 1);
        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

        TableLayout tl = new TableLayout(this.getActivity());
        tl.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        for (int i=0; i<=loanDuration; i++) {
            TableRow tr = new TableRow(this.getActivity());
            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.drawable.table_row_border);
            if (i == 0) {
                TextView a = new TextView(this.getActivity());
                a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                a.setText(R.string.row_title_no);
                a.setGravity(Gravity.CENTER);
                a.setTypeface(null, Typeface.BOLD);
                tr.addView(a);

                TextView b = new TextView(this.getActivity());
                b.setText(R.string.row_title_repayment);
                b.setGravity(Gravity.CENTER);
                b.setTypeface(null, Typeface.BOLD);
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(b);

                TextView e = new TextView(this.getActivity());
                e.setText(R.string.row_title_original_payment);
                e.setGravity(Gravity.CENTER);
                e.setTypeface(null, Typeface.BOLD);
                e.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(e);

                TextView c = new TextView(this.getActivity());
                c.setText(R.string.row_title_interest);
                c.setGravity(Gravity.CENTER);
                c.setTypeface(null, Typeface.BOLD);
                c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(c);

                TextView d = new TextView(this.getActivity());
                d.setText(R.string.row_title_left_money);
                d.setGravity(Gravity.CENTER);
                d.setTypeface(null, Typeface.BOLD);
                d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(d);
            } else if (i == loanDuration) {
                calInterestResult = ((long)((float)(loanPrice - (calOriginalResult * (i - 1))) * (loanRate / 100)) / 12);
                calInterestTotalResult += calInterestResult;
                TextView a = new TextView(this.getActivity());
                a.setText(i + "");
                a.setGravity(Gravity.CENTER);
                a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(a);

                TextView b = new TextView(this.getActivity());
                b.setText(df.format(calOriginalResult + calInterestResult) + currency);
                b.setGravity(Gravity.RIGHT);
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(b);

                TextView c = new TextView(this.getActivity());
                c.setText(df.format(calOriginalResult) + currency);
                c.setGravity(Gravity.RIGHT);
                c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(c);

                TextView d = new TextView(this.getActivity());
                d.setText(df.format(calInterestResult) + currency);
                d.setGravity(Gravity.RIGHT);
                d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(d);

                TextView e = new TextView(this.getActivity());
                e.setText(df.format(0) + currency);
                e.setGravity(Gravity.RIGHT);
                e.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(e);
            } else {

                calInterestResult = ((long)((float)(loanPrice - (calOriginalResult * (i - 1))) * ((loanRate / 100 ) / 12)));
                calInterestTotalResult += calInterestResult;
                TextView a = new TextView(this.getActivity());
                a.setText(i + "");
                a.setGravity(Gravity.CENTER);
                a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(a);

                TextView b = new TextView(this.getActivity());
                b.setText(df.format(calOriginalResult + calInterestResult) + currency);
                b.setGravity(Gravity.RIGHT);
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(b);

                TextView c = new TextView(this.getActivity());
                c.setText(df.format(calOriginalResult) + currency);
                c.setGravity(Gravity.RIGHT);
                c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(c);

                TextView d = new TextView(this.getActivity());
                d.setText(df.format(calInterestResult) + currency);
                d.setGravity(Gravity.RIGHT);
                d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(d);

                TextView e = new TextView(this.getActivity());
                e.setText(df.format(loanPrice - (calOriginalResult * i)) + currency);
                e.setGravity(Gravity.RIGHT);
                e.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(e);
            }
            tl.addView(tr);
        }

        total_interest_result_edit.setText(df.format(calInterestTotalResult));
        payment_monthly_loan_interest_result_layout.addView(tl);


    }
    private void calSecondMethod(Long loanPrice, Float loanRate, Long loanDuration, String selItem) {
        Log.i(TAG, "calSecondMethod");
        Log.i(TAG, "loanPrice : " + loanPrice);
        Log.i(TAG, "loanRate : " + loanRate);
        Log.i(TAG, "loanDuration : " + loanDuration);

        monthly_interest_result_layout.setVisibility(View.GONE);
        repayment_result_layout.setVisibility(View.VISIBLE);
        original_payment_result_layout.setVisibility(View.GONE);

        loan_price_result_edit.setText(df.format(loanPrice));
        loan_rate_result_edit.setText(df.format(loanRate));
        loan_duration_result_edit.setText(df.format(loanDuration));
        loan_method_result_edit.setText(selItem);


        Double temp1 = (double)((float)loanPrice * ((loanRate / 100) / 12)) * (Math.pow(1+((loanRate / 100) / 12), loanDuration));
        Double temp2 = (Math.pow(1+((loanRate / 100) / 12), loanDuration)) - 1;
        Log.i(TAG,"temp 1 :" + temp1);
        Log.i(TAG,"temp 2 :" + temp2);
        // 상환금
        Double temp3 = temp1 / temp2;

        String currency = getResources().getString(R.string.currency);
        // 잔금
        Long calLoanPrice = loanPrice;
        // 총이자
        Long calTotalInterest = 0L;

        //상환금
        repayment_result_edit.setText(df.format(Math.round(temp3)));
        /* Find Tablelayout defined in main.xml */
        // row layout
        int leftMargin=0;
        int topMargin=0;
        int rightMargin=0;
        int bottomMargin=15;
        TableLayout.LayoutParams tableRowParams=
                new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT, 1);
        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

        TableLayout tl = new TableLayout(this.getActivity());
        tl.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        for (int i=0; i<=loanDuration; i++) {
            TableRow tr = new TableRow(this.getActivity());
            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.drawable.table_row_border);
            if (i == 0) {
                TextView a = new TextView(this.getActivity());
                a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                a.setText(R.string.row_title_no);
                a.setGravity(Gravity.CENTER);
                a.setTypeface(null, Typeface.BOLD);
                tr.addView(a);

                TextView b = new TextView(this.getActivity());
                b.setText(R.string.row_title_repayment);
                b.setGravity(Gravity.CENTER);
                b.setTypeface(null, Typeface.BOLD);
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(b);

                TextView e = new TextView(this.getActivity());
                e.setText(R.string.row_title_original_payment);
                e.setGravity(Gravity.CENTER);
                e.setTypeface(null, Typeface.BOLD);
                e.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(e);

                TextView c = new TextView(this.getActivity());
                c.setText(R.string.row_title_interest);
                c.setGravity(Gravity.CENTER);
                c.setTypeface(null, Typeface.BOLD);
                c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(c);

                TextView d = new TextView(this.getActivity());
                d.setText(R.string.row_title_left_money);
                d.setGravity(Gravity.CENTER);
                d.setTypeface(null, Typeface.BOLD);
                d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(d);
            } else if (i == loanDuration) {
                // 월 이자
                Long monthInterest = (long)((float)(calLoanPrice / 12) * (loanRate / 100));
                // 납입 원금
                Long tm = Math.round(temp3) -  monthInterest;
                Log.i(TAG, "temp 3: " + temp3);

                calTotalInterest += monthInterest;

                calLoanPrice -= tm;
                TextView a = new TextView(this.getActivity());
                a.setText(i + "");
                a.setGravity(Gravity.CENTER);
                a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(a);

                TextView b = new TextView(this.getActivity());
                b.setText(df.format(Math.round(temp3)) + currency);
                b.setGravity(Gravity.RIGHT);
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(b);

                TextView c = new TextView(this.getActivity());
                c.setText(df.format(tm) + currency);
                c.setGravity(Gravity.RIGHT);
                c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(c);

                TextView d = new TextView(this.getActivity());
                d.setText(df.format(monthInterest) + currency);
                d.setGravity(Gravity.RIGHT);
                d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(d);

                TextView e = new TextView(this.getActivity());
                e.setText(df.format(0) + currency);
                e.setGravity(Gravity.RIGHT);
                e.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(e);
            } else {
                // 월 이자
                Long monthInterest = (long)((float)(calLoanPrice / 12) * (loanRate / 100));
                // 납입 원금
                Long tm = Math.round(temp3) -  monthInterest;
                Log.i(TAG, "temp 3: " + temp3);

                calTotalInterest += monthInterest;
                calLoanPrice -= tm;
                TextView a = new TextView(this.getActivity());
                a.setText(i + "");
                a.setGravity(Gravity.CENTER);
                a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(a);

                TextView b = new TextView(this.getActivity());
                b.setText(df.format(Math.round(temp3)) + currency);
                b.setGravity(Gravity.RIGHT);
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(b);

                TextView c = new TextView(this.getActivity());
                c.setText(df.format(tm) + currency);
                c.setGravity(Gravity.RIGHT);
                c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(c);

                TextView d = new TextView(this.getActivity());
                d.setText(df.format(monthInterest) + currency);
                d.setGravity(Gravity.RIGHT);
                d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(d);

                TextView e = new TextView(this.getActivity());
                e.setText(df.format(calLoanPrice) + currency);
                e.setGravity(Gravity.RIGHT);
                e.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(e);
            }
            tl.addView(tr);
        }
        total_interest_result_edit.setText(df.format(calTotalInterest));
        payment_monthly_loan_interest_result_layout.addView(tl);
    }
    private void calThirdMethod(Long loanPrice, Float loanRate, Long loanDuration, String selItem) {
        Log.i(TAG, "calThirdMethod");
        Log.i(TAG, "loanPrice : " + loanPrice);
        Log.i(TAG, "loanRate : " + loanRate);
        Log.i(TAG, "loanDuration : " + loanDuration);

        monthly_interest_result_layout.setVisibility(View.VISIBLE);
        repayment_result_layout.setVisibility(View.VISIBLE);
        original_payment_result_layout.setVisibility(View.GONE);

        loan_price_result_edit.setText(df.format(loanPrice));
        loan_rate_result_edit.setText(df.format(loanRate));
        loan_duration_result_edit.setText(df.format(loanDuration));
        loan_method_result_edit.setText(selItem);
        String currency = getResources().getString(R.string.currency);

        Long totalInterest  = (long)((((float)loanPrice * (loanRate / 100)) / 12) * (float)(loanDuration));
        Long repayment = totalInterest / loanDuration;
        Long monthlyInterest = totalInterest / loanDuration;
        monthly_interest_result_edit.setText(df.format(monthlyInterest));
        repayment_result_edit.setText(df.format(repayment));
        total_interest_result_edit.setText(df.format(totalInterest));

        /* Find Tablelayout defined in main.xml */
        // row layout
        int leftMargin=0;
        int topMargin=0;
        int rightMargin=0;
        int bottomMargin=15;
        TableLayout.LayoutParams tableRowParams=
                new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT, 1);
        tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

        TableLayout tl = new TableLayout(this.getActivity());
        tl.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        for (int i=0; i<=loanDuration; i++) {
            TableRow tr = new TableRow(this.getActivity());
            tr.setLayoutParams(tableRowParams);
            tr.setBackgroundResource(R.drawable.table_row_border);
            if (i == 0) {
                TextView a = new TextView(this.getActivity());
                a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                a.setText(R.string.row_title_no);
                a.setTypeface(null, Typeface.BOLD);
                a.setGravity(Gravity.CENTER);
                tr.addView(a);

                TextView b = new TextView(this.getActivity());
                b.setText(R.string.row_title_repayment);
                b.setGravity(Gravity.CENTER);
                b.setTypeface(null, Typeface.BOLD);
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(b);

                TextView c = new TextView(this.getActivity());
                c.setText(R.string.row_title_interest);
                c.setGravity(Gravity.CENTER);
                c.setTypeface(null, Typeface.BOLD);
                c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(c);

                TextView d = new TextView(this.getActivity());
                d.setText(R.string.row_title_left_money);
                d.setGravity(Gravity.CENTER);
                d.setTypeface(null, Typeface.BOLD);
                d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));
                tr.addView(d);
            } else if (i == loanDuration) {
                TextView a = new TextView(this.getActivity());
                a.setText(i + "");
                a.setGravity(Gravity.CENTER);
                a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(a);

                TextView b = new TextView(this.getActivity());
                b.setText(df.format(loanPrice + monthlyInterest) + currency);
                b.setGravity(Gravity.RIGHT);
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(b);

                TextView c = new TextView(this.getActivity());
                c.setText(df.format(monthlyInterest) + currency);
                c.setGravity(Gravity.RIGHT);
                c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(c);

                TextView d = new TextView(this.getActivity());
                d.setText("0" + currency);
                d.setGravity(Gravity.RIGHT);
                d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(d);
            } else {

                TextView a = new TextView(this.getActivity());
                a.setText(i + "");
                a.setGravity(Gravity.CENTER);
                a.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(a);

                TextView b = new TextView(this.getActivity());
                b.setText(df.format(monthlyInterest) + currency);
                b.setGravity(Gravity.RIGHT);
                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(b);

                TextView c = new TextView(this.getActivity());
                c.setText(df.format(monthlyInterest) + currency);
                c.setGravity(Gravity.RIGHT);
                c.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(c);

                TextView d = new TextView(this.getActivity());
                d.setText(df.format(loanPrice) + currency);
                d.setGravity(Gravity.RIGHT);
                d.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1));

                tr.addView(d);
            }
            tl.addView(tr);
        }


        payment_monthly_loan_interest_result_layout.addView(tl);
    }
}
