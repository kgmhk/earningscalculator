package com.gkwak.earningscalculator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkwak.earningscalculator.R;
import com.gkwak.earningscalculator.interfaces.InterestRateEnum;
import com.gkwak.earningscalculator.interfaces.InterestTaxEnum;
import com.gkwak.earningscalculator.utils.Text;

import java.text.DecimalFormat;

public class DepositInterestFragment extends Fragment {
    private Spinner  interestRateSpinner, interestTaxSpinner;
    private static String TAG = "DEPOSIT_FRAGMENT";
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
        View rootView = inflater.inflate(R.layout.fragment_deposit, container, false);

        calDepositBtn = (Button) rootView.findViewById(R.id.cal_deposit_btn);
        spcialTaxRateLayout = (LinearLayout) rootView.findViewById(R.id.deposit_spcial_interest_rate_layout);
//
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
//
        interestTaxSpinner = (Spinner) rootView.findViewById(R.id.deposit_interest_tax_spinner);
        ArrayAdapter interestTaxSpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.deposit_interest_tax, android.R.layout.simple_spinner_item);
        interestTaxSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestTaxSpinner.setAdapter(interestTaxSpinnerAdapter);
        interestTaxSpinner.setOnItemSelectedListener(mOnInterestTaxSpinnerListener);
//
//        numberOfFamilySpinner = (Spinner) rootView.findViewById(R.id.number_of_family_spinner);
//        ArrayAdapter numberOfFamilySpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
//                R.array.number_of_family, android.R.layout.simple_spinner_item);
//        numberOfFamilySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        numberOfFamilySpinner.setAdapter(numberOfFamilySpinnerAdapter);
//        numberOfFamilySpinner.setOnItemSelectedListener(mOnNumberOfFamilySpinnerListener);
//
//        subscriptionAccountPeriodSpinner = (Spinner) rootView.findViewById(R.id.subscription_account_period_spinner);
//        ArrayAdapter subscriptionAccountPeriodSpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
//                R.array.subscription_account_period, android.R.layout.simple_spinner_item);
//        subscriptionAccountPeriodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        subscriptionAccountPeriodSpinner.setAdapter(subscriptionAccountPeriodSpinnerAdapter);
//        subscriptionAccountPeriodSpinner.setOnItemSelectedListener(mOnSubscriptionAccountPeriodSpinnerListener);

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

            if (InterestRateEnum.SimpleInterest == interestRateSpinnerPosition) {
                Float interestAmount = depositAmount * ( depositInterestRate / 100 ) * ( depositPeriod / 12 );
                Float interestTax = interestAmount * ( depositInterestTax / 100 );
                Log.i(TAG, "interestAmount = " + interestAmount);
                Log.i(TAG, "interestTax = " + interestTax);

                principalSumResult.setText(df.format(depositAmount.longValue()));
                interestBeforeTaxResult.setText(df.format(interestAmount.longValue()));
                interestTaxResult.setText("- " + df.format(interestTax.longValue()));
                interestTotalResult.setText(df.format((long)(depositAmount + interestAmount - interestTax)));
            } else {
                double tempRate = (1 + (depositInterestRate / 100) / 12 );
                double interestAmount = depositAmount * Math.pow( tempRate, depositPeriod);
                double interestTax = (interestAmount - depositAmount) * ( depositInterestTax / 100 );
                Log.i(TAG, "interestAmount = " + interestAmount);
                Log.i(TAG, "interestTax = " + interestTax);

                principalSumResult.setText(df.format(depositAmount.longValue()));
                interestBeforeTaxResult.setText(df.format((long)(interestAmount - depositAmount)));
                interestTotalResult.setText(df.format((long)(interestAmount - interestTax)));
                interestTaxResult.setText("- " + df.format((long)(interestTax)));
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
                    depositTaxRateEdit.setText("0");
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

//    private AdapterView.OnItemSelectedListener mOnNumberOfFamilySpinnerListener = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//            Log.i(TAG, "mOnNumberOfFamilySpinnerListener() entered!!");
//            String selItem= (String) numberOfFamilySpinner.getSelectedItem();
//            Log.i(TAG, "Spinner selected item = "+selItem);
//
//            numberOfFamilySpinnerPosition = position;
//            numberOfFamilySpinnerString = selItem;
//
//            switch (position) {
//                case 0:
//                    return;
//                case 1:
//                    Log.i(TAG, "select position 1");
//                    return;
//                default:
//                    return;
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//            Log.i(TAG, "onNothingSelected() entered!!");
//        }
//    };
//
//    private AdapterView.OnItemSelectedListener mOnSubscriptionAccountPeriodSpinnerListener = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//            Log.i(TAG, "mOnNumberOfFamilySpinnerListener() entered!!");
//            String selItem= (String) subscriptionAccountPeriodSpinner.getSelectedItem();
//            Log.i(TAG, "Spinner selected item = "+selItem);
//
//            subscriptionAccountPeriodSpinnerPosition = position;
//            subscriptionAccountPeriodSpinnerString = selItem;
//
//            switch (position) {
//                case 0:
//                    return;
//                case 1:
//                    Log.i(TAG, "select position 1");
//                    return;
//                default:
//                    return;
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//            Log.i(TAG, "onNothingSelected() entered!!");
//        }
//    };
//
//    private void helpPopupWindow() {
//        final View layout;
//        try {
//            //  LayoutInflater 객체와 시킴
//            final LayoutInflater inflater = (LayoutInflater) DepositInterestFragment.this.getActivity()
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            layout = inflater.inflate(R.layout.help_popup,
//                    (ViewGroup) getActivity().findViewById(R.id.help_popup_element));
//
//            final LinearLayout top = (LinearLayout) layout.findViewById(R.id.help_popup_linear);
//            TextView title = (TextView) layout.findViewById(R.id.help_popup_title);
//            Button help_popup_btn = (Button) layout.findViewById(R.id.help_popup_btn);
//
////            if (mHeightPixels <= 800) windowHeight = 200;
//            pwindo = new PopupWindow(layout,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
//
//            TextView tv1 = new TextView(this.getActivity());
//            TextView tv2 = new TextView(this.getActivity());
//
//            title.setText(R.string.acquisition_help_title);
//            tv1.setText(R.string.acquisition_help_detail1);
//            tv2.setText(R.string.acquisition_help_detail2);
//            top.addView(tv1);
//            top.addView(tv2);
//
//            help_popup_btn.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    pwindo.dismiss();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void clearAllOfText() {
//        totalSubscriptionPlusScore.setText("0");
//
//        noHousePeriodResult.setText("0");
//        noHousePeriodScoreResult.setText("0");
//
//        numberOfFamilyResult.setText("0");
//        numberOfFamilyScoreResult.setText("0");
//
//        subscriptionAccountPeriodResult.setText("0");
//        subscriptionAccountPeriodScoreResult.setText("0");
//    }
}
