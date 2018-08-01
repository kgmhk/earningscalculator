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
import com.gkwak.earningscalculator.interfaces.InterestRateEnum;
import com.gkwak.earningscalculator.interfaces.InterestTaxEnum;

import java.text.DecimalFormat;

public class InstallmentSavingsFragment extends Fragment {
    private Spinner  interestRateSpinner, interestTaxSpinner;
    private static String TAG = "INSTALLMENT_FRAGMENT";
//    private static Double PYEONG_TO_METER = 3.305785;
    private TextView principalSumResult, interestBeforeTaxResult, interestTaxResult, interestTotalResult;
    private Button calDepositBtn;
    private EditText depositAmountEdit, depositPeriodEdit, depositInterestRateEdit, depositTaxRateEdit;
//    private LinearLayout acquisitionSquareLayout, acquisitionSquaredMethodLayout;
//    private PopupWindow pwindo;
    private LinearLayout spcialTaxRateLayout;
//
    private InterestRateEnum interestRateSpinnerPosition;
    private InterestTaxEnum interestTaxSpinnerPosition;
//    private int numberOfFamilySpinnerPosition = 0;
//    private int subscriptionAccountPeriodSpinnerPosition = 0;
//
    private String interestRateSpinnerString = "0";
    private String interestTaxSpinnerString = "0년";
//    private String numberOfFamilySpinnerString = "0명";
//    private String subscriptionAccountPeriodSpinnerString = "0년";

    final DecimalFormat df = new DecimalFormat("###,###.####");
//
//    private int[] noHousePeriodScoreArray = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
//    private int[] numberOfFamilyScoreArray = {5, 10, 15, 20, 25, 30, 35};
//    private int[] periodOfSubscriptionAccountScoreArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_installment_savings, container, false);

        calDepositBtn = (Button) rootView.findViewById(R.id.cal_deposit_btn);
        spcialTaxRateLayout = (LinearLayout) rootView.findViewById(R.id.deposit_spcial_interest_rate_layout);

        depositAmountEdit = (EditText) rootView.findViewById(R.id.deposit_amount_edit);
        depositPeriodEdit = (EditText) rootView.findViewById(R.id.deposit_period_edit);
        depositInterestRateEdit = (EditText) rootView.findViewById(R.id.deposit_interest_rate_edit);
        depositTaxRateEdit = (EditText) rootView.findViewById(R.id.deposit_special_interest_rate_edit);

        principalSumResult = (TextView) rootView.findViewById(R.id.principal_sum_edit);
        interestBeforeTaxResult = (TextView) rootView.findViewById(R.id.interest_before_tax_edit);
        interestTaxResult = (TextView) rootView.findViewById(R.id.interest_tax_result_edit);
        interestTotalResult = (TextView) rootView.findViewById(R.id.interest_total_result_edit);

        interestRateSpinner = (Spinner) rootView.findViewById(R.id.deposit_interest_rate_spinner);
        ArrayAdapter interestRateAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.deposit_interest_rate, android.R.layout.simple_spinner_item);
        interestRateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestRateSpinner.setAdapter(interestRateAdapter);
        interestRateSpinner.setOnItemSelectedListener(mOnInterestRateSpinnerListener);

        interestTaxSpinner = (Spinner) rootView.findViewById(R.id.deposit_interest_tax_spinner);
        ArrayAdapter interestTaxSpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.deposit_interest_tax, android.R.layout.simple_spinner_item);
        interestTaxSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestTaxSpinner.setAdapter(interestTaxSpinnerAdapter);
        interestTaxSpinner.setOnItemSelectedListener(mOnInterestTaxSpinnerListener);

        final String[] depositAmountEditTemp = {""};
        final String[] depositPeriodEditTemp = {""};

        depositAmountEdit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){

                if(!s.toString().equals(depositAmountEditTemp[0])){     // StackOverflow를 막기위해,
                    if (s.length() == 0) return;
                    depositAmountEditTemp[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    depositAmountEdit.setText(depositAmountEditTemp[0]);    // 결과 텍스트 셋팅.
                    depositAmountEdit.setSelection(depositAmountEditTemp[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });

        calDepositBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            Log.i(TAG, "amount = " + depositAmountEdit.getText().toString());
            Log.i(TAG, "period = " + depositPeriodEdit.getText().toString());
            Log.i(TAG, "rate = " + depositInterestRateEdit.getText().toString());
            Log.i(TAG, "tax = " + depositTaxRateEdit.getText().toString());
            Log.i(TAG, "interest rate  = "+ interestRateSpinnerPosition.ordinal());

            if (depositAmountEdit.getText().length() == 0
                    || depositPeriodEdit.getText().length() ==0
                    || depositInterestRateEdit.getText().length() ==0
                    || depositTaxRateEdit.getText().length() ==0) {
                Toast.makeText(getActivity(), getResources().getString(R.string.deposit_input_warning_toast), Toast.LENGTH_LONG).show();
                return;
            }

            Float depositAmount = Float.valueOf(depositAmountEdit.getText().toString().replaceAll(",", ""));
            Float depositPeriod = Float.valueOf(depositPeriodEdit.getText().toString());
            Float depositInterestRate = Float.valueOf(depositInterestRateEdit.getText().toString());
            Float depositInterestTax = Float.valueOf(depositTaxRateEdit.getText().toString());

            Float monthlyArg = depositPeriod * ( depositPeriod + 1 ) / 2;

            if (InterestRateEnum.SimpleInterest == interestRateSpinnerPosition) {
                Float interestAmount = depositAmount * ( depositInterestRate / 100 ) * monthlyArg / 12;
                Float interestTax = interestAmount * ( depositInterestTax / 100 );
                Log.i(TAG, "interestAmount = " + interestAmount);
                Log.i(TAG, "interestTax = " + interestTax);

                principalSumResult.setText(df.format(depositAmount.longValue() * depositPeriod.longValue()));
                interestBeforeTaxResult.setText(df.format(interestAmount.longValue()));
                interestTaxResult.setText("- " + df.format(interestTax.longValue()));
                interestTotalResult.setText(df.format((long)((depositAmount * depositPeriod) + interestAmount - interestTax)));
            } else {
                float monthlyInterestRate = ((depositInterestRate / 100) / 12 );
                double totalResult = depositAmount *
                        (1 + monthlyInterestRate) *
                        (Math.pow((1 + monthlyInterestRate), depositPeriod) - 1) /
                        monthlyInterestRate;

                double interestAmount = totalResult - ( depositAmount * depositPeriod );
                double interestTax = (interestAmount) * ( depositInterestTax / 100 );


                Log.i(TAG, "interestAmount = " + interestAmount);
                Log.i(TAG, "interestTax = " + interestTax);
                Log.i(TAG, "totalResult = " + totalResult);

                principalSumResult.setText(df.format(depositAmount.longValue() * depositPeriod.longValue()));
                interestBeforeTaxResult.setText(df.format((long)(interestAmount)));
                interestTaxResult.setText("- " + df.format((long)(interestTax)));
                interestTotalResult.setText(df.format((long)(totalResult - interestTax)));

            }








            Log.i(TAG, "button clicked");
            }
        });

        return rootView;
    }


    private AdapterView.OnItemSelectedListener mOnInterestRateSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Log.i(TAG, "mOnHasHouseSpinnerListener() entered!!");
            String selItem= (String) interestRateSpinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);
            interestRateSpinnerPosition = InterestRateEnum.SimpleInterest;
            interestRateSpinnerString = selItem;

            switch (position) {
                case 0:
                    Log.i(TAG, "clicked positon 0");
                    interestRateSpinnerPosition = InterestRateEnum.SimpleInterest;
//                    noHousePeriodlayout.setVisibility(View.GONE);
//                    noHousePeriodSpinnerPosition = 0;
//                    noHousePeriodSpinnerString = "0년";
//
//                    noHousePeriodSpinner.setSelection(0);
//                    numberOfFamilySpinner.setSelection(0);
//                    subscriptionAccountPeriodSpinner.setSelection(0);
//
//                    clearAllOfText();
                    return;
                default:
                    interestRateSpinnerPosition = InterestRateEnum.CompoundInterest;
//                    noHousePeriodlayout.setVisibility(View.VISIBLE);
//                    noHousePeriodSpinner.setSelection(0);
//                    numberOfFamilySpinner.setSelection(0);
//                    subscriptionAccountPeriodSpinner.setSelection(0);
//
//                    clearAllOfText();
                    Log.i(TAG, "clicked positon 1");
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };
//
    private AdapterView.OnItemSelectedListener mOnInterestTaxSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            Log.i(TAG, "mOnNoHousePeriodSpinnerListener() entered!!");
            String selItem= (String) interestTaxSpinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);

            interestTaxSpinnerPosition = InterestTaxEnum.GeneralTaxation;
            interestTaxSpinnerString = selItem;

            switch (position) {
                case 0:
                    spcialTaxRateLayout.setVisibility(View.GONE);
                    interestTaxSpinnerPosition = InterestTaxEnum.GeneralTaxation;
                    depositTaxRateEdit.setText("15.4");
                    return;
                case 1:
                    Log.i(TAG, "select position 1");
                    spcialTaxRateLayout.setVisibility(View.GONE);
                    interestTaxSpinnerPosition = InterestTaxEnum.TaxExemption;
                    depositTaxRateEdit.setText("15.4");
                    return;
                default:
                    Log.i(TAG, "selected position default");
                    spcialTaxRateLayout.setVisibility(View.VISIBLE);
                    interestTaxSpinnerPosition = InterestTaxEnum.TaxBreak;
                    depositTaxRateEdit.setText("9.5");
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };
}
