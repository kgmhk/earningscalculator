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
import com.gkwak.earningscalculator.utils.Keyborad;

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

    private EditText dealingPriceEdit, depositPriceEdit, commissionMonthlyPriceEdit;

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
        depositPriceEdit = (EditText) rootView.findViewById(R.id.deposit_price_edit);
        commissionMonthlyPriceEdit = (EditText) rootView.findViewById(R.id.commission_monthly_price_edit);
        calCommissionBtn = (Button) rootView.findViewById(R.id.cal_commission_btn);

        // Spinner
        commission_method_spinner = (Spinner) rootView.findViewById(R.id.commission_method_spinner);
        ArrayAdapter commissionMethodAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.commission_method, android.R.layout.simple_spinner_item);
        commissionMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        commission_method_spinner.setAdapter(commissionMethodAdapter);
        commission_method_spinner.setOnItemSelectedListener(mOnItemSelectedListener);

        final String[] deal_price_edit_temp_result = {""};
        final String[] deposit_price_edit_temp_result = {""};
        final String[] commission_monthly_price_edit_temp_result = {""};

        commissionMonthlyPriceEdit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){

                if(!s.toString().equals(commission_monthly_price_edit_temp_result[0])){     // StackOverflow를 막기위해,
                    if (s.length() == 0) return;
                    commission_monthly_price_edit_temp_result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    commissionMonthlyPriceEdit.setText(commission_monthly_price_edit_temp_result[0]);    // 결과 텍스트 셋팅.
                    commissionMonthlyPriceEdit.setSelection(commission_monthly_price_edit_temp_result[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });

        depositPriceEdit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){

                if(!s.toString().equals(deposit_price_edit_temp_result[0])){     // StackOverflow를 막기위해,
                    if (s.length() == 0) return;
                    deposit_price_edit_temp_result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    depositPriceEdit.setText(deposit_price_edit_temp_result[0]);    // 결과 텍스트 셋팅.
                    depositPriceEdit.setSelection(deposit_price_edit_temp_result[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });

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

                if (dealingPriceEdit.getText().length() == 0 && spinnerPosition == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.commission_dealing_price_warning_toast), Toast.LENGTH_LONG).show();
                    dealingPriceEdit.requestFocus();
                    return;
                }

                if (depositPriceEdit.getText().length() == 0 && spinnerPosition == 1) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.commission_deposit_price_warning_toast), Toast.LENGTH_LONG).show();
                    depositPriceEdit.requestFocus();
                    return;
                }

                if (depositPriceEdit.getText().length() == 0 && spinnerPosition == 2) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.commission_deposit_price_warning_toast), Toast.LENGTH_LONG).show();
                    depositPriceEdit.requestFocus();
                    return;
                }

                if (commissionMonthlyPriceEdit.getText().length() == 0 && spinnerPosition == 2) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.commission_monthly_price_warning_toast), Toast.LENGTH_LONG).show();
                    commissionMonthlyPriceEdit.requestFocus();
                    return;
                }

                Keyborad.hideKeyboard(getContext(), dealingPriceEdit);
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
            Long dealResult = (long)((float)dealPrice * (0.006));

            if(dealResult > 250000) dealResult = (long)2500000;

            cal_commission_result_edit.setText(dealingPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.6");
            upper_limit_price_edit.setText("250,000");
        } else if (dealPrice >= 50000000 && dealPrice < 200000000) {
            Long dealResult = (long)((float)dealPrice * (0.005));

            if(dealResult > 800000) dealResult = (long)800000;

            cal_commission_result_edit.setText(dealingPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.5");
            upper_limit_price_edit.setText("800,000");
        } else if (dealPrice >= 200000000 && dealPrice < 600000000) {
            Long dealResult = (long)((float)dealPrice * (0.004));

            cal_commission_result_edit.setText(dealingPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.4");
            upper_limit_price_edit.setText("-");
        } else if (dealPrice >= 600000000 && dealPrice < 900000000) {
            Long dealResult = (long)((float)dealPrice * (0.005));

            cal_commission_result_edit.setText(dealingPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.5");
            upper_limit_price_edit.setText("-");
        } else {
            Long dealResult = (long)((float)dealPrice * (0.009));

            cal_commission_result_edit.setText(dealingPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.9");
            upper_limit_price_edit.setText("-");
        }

    }

    private void calLeaseCommissionResult() {
        Log.i(TAG, "calLeaseCommissionResult");

        if (depositPriceEdit.getText().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dealing_price_warning_toast), Toast.LENGTH_LONG).show();
            depositPriceEdit.requestFocus();
        }

        Long dealPrice = Long.parseLong(depositPriceEdit.getText().toString().replaceAll(",", ""));

        if (dealPrice < 50000000) {
            Long dealResult = (long)((float)dealPrice * (0.005));

            if(dealResult > 200000) dealResult = (long)2000000;

            cal_commission_result_edit.setText(depositPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.5");
            upper_limit_price_edit.setText("200,000");
        } else if (dealPrice >= 50000000 && dealPrice < 100000000) {
            Long dealResult = (long)((float)dealPrice * (0.004));

            if(dealResult > 300000) dealResult = (long)300000;

            cal_commission_result_edit.setText(depositPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.4");
            upper_limit_price_edit.setText("300,000");
        } else if (dealPrice >= 100000000 && dealPrice < 300000000) {
            Long dealResult = (long)((float)dealPrice * (0.003));

            cal_commission_result_edit.setText(depositPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.3");
            upper_limit_price_edit.setText("-");
        } else if (dealPrice >= 300000000 && dealPrice < 600000000) {
            Long dealResult = (long)((float)dealPrice * (0.004));

            cal_commission_result_edit.setText(depositPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.4");
            upper_limit_price_edit.setText("-");
        } else {
            Long dealResult = (long)((float)dealPrice * (0.008));

            cal_commission_result_edit.setText(depositPriceEdit.getText());
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.8");
            upper_limit_price_edit.setText("-");
        }
    }

    private void calMonthlyPriceCommissionResult() {
        Log.i(TAG, "calMonthlyPriceCommissionResult");
//        commissionMonthlyPriceEdit

        if (depositPriceEdit.getText().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dealing_price_warning_toast), Toast.LENGTH_LONG).show();
            depositPriceEdit.requestFocus();
        }

        if (commissionMonthlyPriceEdit.getText().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dealing_price_warning_toast), Toast.LENGTH_LONG).show();
            commissionMonthlyPriceEdit.requestFocus();
        }

        Long depositPrice = Long.parseLong(depositPriceEdit.getText().toString().replaceAll(",", ""));
        Long monthlyPrcie = Long.parseLong(commissionMonthlyPriceEdit.getText().toString().replaceAll(",", ""));

        Long resultDealPrice = depositPrice + (monthlyPrcie * 100);

        if (resultDealPrice < 50000000) {
            resultDealPrice = depositPrice + (monthlyPrcie * 70);
        }

        if (resultDealPrice < 50000000) {
            Long dealResult = (long)((float)resultDealPrice * (0.005));

            if(dealResult > 200000) dealResult = (long)2000000;

            cal_commission_result_edit.setText(df.format(resultDealPrice));
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.5");
            upper_limit_price_edit.setText("200,000");
        } else if (resultDealPrice >= 50000000 && resultDealPrice < 100000000) {
            Long dealResult = (long)((float)resultDealPrice * (0.004));

            if(dealResult > 300000) dealResult = (long)300000;

            cal_commission_result_edit.setText(df.format(resultDealPrice));
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.4");
            upper_limit_price_edit.setText("300,000");
        } else if (resultDealPrice >= 100000000 && resultDealPrice < 300000000) {
            Long dealResult = (long)((float)resultDealPrice * (0.003));

            cal_commission_result_edit.setText(df.format(resultDealPrice));
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.3");
            upper_limit_price_edit.setText("-");
        } else if (resultDealPrice >= 300000000 && resultDealPrice < 600000000) {
            Long dealResult = (long)((float)resultDealPrice * (0.004));

            cal_commission_result_edit.setText(df.format(resultDealPrice));
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.4");
            upper_limit_price_edit.setText("-");
        } else {
            Long dealResult = (long)((float)resultDealPrice * (0.008));

            cal_commission_result_edit.setText(df.format(resultDealPrice));
            max_commission_price_edit.setText(df.format(dealResult));
            upper_limit_rate_edit.setText("0.8");
            upper_limit_price_edit.setText("-");
        }
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
