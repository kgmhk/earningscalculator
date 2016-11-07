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

public class CommissionFragment extends Fragment {
    private static final String TAG = "COMMISSION_FRAGMENT";
    private Spinner  commission_method_spinner;
    private LinearLayout dealingLayout, leaseLayout, commissionMonthlyPriceLayout;
    private int spinnerPosition = 0;
    private Button calCommissionBtn;
    final DecimalFormat df = new DecimalFormat("###,###.####");
    private TextView max_commission_price_edit, upper_limit_price_edit, upper_limit_rate_edit,
            cal_commission_result_edit;

    private EditText dealingPriceEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_commission, container, false);

        dealingLayout = (LinearLayout) rootView.findViewById(R.id.dealing_layout);
        leaseLayout = (LinearLayout) rootView.findViewById(R.id.lease_layout);
        commissionMonthlyPriceLayout = (LinearLayout) rootView.findViewById(R.id.commission_monthly_pirce_layout);

        max_commission_price_edit = (TextView) rootView.findViewById(R.id.max_commission_price_edit);
        upper_limit_price_edit = (TextView) rootView.findViewById(R.id.upper_limit_price_edit);
        upper_limit_rate_edit = (TextView) rootView.findViewById(R.id.upper_limit_rate_edit);
        cal_commission_result_edit = (TextView) rootView.findViewById(R.id.cal_commission_result_edit);

        dealingPriceEdit = (EditText) rootView.findViewById(R.id.deal_price_edit);
        calCommissionBtn = (Button) rootView.findViewById(R.id.cal_commission_btn);

        // Spinner
        commission_method_spinner = (Spinner) rootView.findViewById(R.id.commission_method_spinner);
        ArrayAdapter commissionMethodAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.commission_method, android.R.layout.simple_spinner_item);
        commissionMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        commission_method_spinner.setAdapter(commissionMethodAdapter);
        commission_method_spinner.setOnItemSelectedListener(mOnItemSelectedListener);

        final String[] deal_price_edit_temp_result = {""};
        final String[] loan_rate_edit_temp_result = {""};
        final String[] loan_duration_edit_temp_result = {""};

        dealingPriceEdit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){

                if(!s.toString().equals(deal_price_edit_temp_result[0])){     // StackOverflow를 막기위해,
                    if (s.length() == 0) return;
                    deal_price_edit_temp_result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    dealingPriceEdit.setText(deal_price_edit_temp_result[0]);    // 결과 텍스트 셋팅.
                    dealingPriceEdit.setSelection(deal_price_edit_temp_result[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });

        calCommissionBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (spinnerPosition) {
                    case 0:
                        calDealingCommissionResult();
                        return;
                    case 1:
                        calLeaseCommissionResult();
                        return;
                    case 2:
                        calMonthlyPriceCommissionResult();
                        return;
                    default:
                        calDealingCommissionResult();
                        return;
                }

            }
        });

        return rootView;
    }

    private void calDealingCommissionResult() {
        Log.i(TAG, "calDealingCommissionResult");

        if (dealingPriceEdit.getText().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dealing_price_warning_toast), Toast.LENGTH_LONG).show();
            dealingPriceEdit.requestFocus();
        }

        Long dealPrice = Long.parseLong(dealingPriceEdit.getText().toString().replaceAll(",", ""));

        if (dealPrice < 50000000) {
            Long dealResult = (long)((float)dealPrice * 0.6);

            if(dealResult > 250000) dealResult = (long)2500000;

            cal_commission_result_edit.setText(dealingPriceEdit.getText());
            upper_limit_rate_edit.setText("0.6");
            upper_limit_price_edit.setText("250,000");
            max_commission_price_edit.setText(dealResult.toString());
        }

    }

    private void calLeaseCommissionResult() {
        Log.i(TAG, "calLeaseCommissionResult");
    }

    private void calMonthlyPriceCommissionResult() {
        Log.i(TAG, "calMonthlyPriceCommissionResult");
    }

    private AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Log.i(TAG, "onItemSelected() entered!!");
            String selItem= (String) commission_method_spinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);

            spinnerPosition = position;

            switch (position) {
                case 0:
                    dealingLayout.setVisibility(View.VISIBLE);
                    leaseLayout.setVisibility(View.GONE);
                    commissionMonthlyPriceLayout.setVisibility(View.GONE);
                    return;
                case 1:
                    Log.i(TAG, "select position 1");
                    dealingLayout.setVisibility(View.GONE);
                    leaseLayout.setVisibility(View.VISIBLE);
                    commissionMonthlyPriceLayout.setVisibility(View.GONE);
                    return;
                case 2:
                    Log.i(TAG, "select position 2");
                    dealingLayout.setVisibility(View.GONE);
                    leaseLayout.setVisibility(View.VISIBLE);
                    commissionMonthlyPriceLayout.setVisibility(View.VISIBLE);
                    return;
                default:
                    dealingLayout.setVisibility(View.VISIBLE);
                    leaseLayout.setVisibility(View.INVISIBLE);
                    commissionMonthlyPriceLayout.setVisibility(View.INVISIBLE);
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };
}
