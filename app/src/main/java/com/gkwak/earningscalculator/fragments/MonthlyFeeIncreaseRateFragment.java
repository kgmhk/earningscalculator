package com.gkwak.earningscalculator.fragments;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gkwak.earningscalculator.R;
import com.gkwak.earningscalculator.utils.Keyborad;
import com.gkwak.earningscalculator.utils.Text;

import java.text.DecimalFormat;

public class MonthlyFeeIncreaseRateFragment extends Fragment {
    private Spinner  squareMethodSpinner, acquisitionTypeSpinner;
    private static String TAG = "MONTHLY_FEE_FRAGMENT";
    private static Double PYEONG_TO_METER = 3.305785;
    private int squareMathodePosition, acquisitionTypePosition;
    private TextView increaseRateResult, depositResult, monthlyFeeResult, yearlyFeeResult;
    private Button calMonthlyFeeRateBtn;
    private EditText beforeDepositEdit, beforeMonthlyFeeEdit, afterDepositEdit, increaseRateEdit;
    private PopupWindow pwindo;
    final DecimalFormat df = new DecimalFormat("###,###.####");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_monthly_fee_increase, container, false);

        increaseRateResult = (TextView) rootView.findViewById(R.id.increase_rate_result_edit);
        depositResult = (TextView) rootView.findViewById(R.id.deposit_result_edit);
        monthlyFeeResult = (TextView) rootView.findViewById(R.id.monthly_fee_result_edit);
        yearlyFeeResult = (TextView) rootView.findViewById(R.id.yearly_fee_result_edit);

        beforeDepositEdit = (EditText) rootView.findViewById(R.id.before_deposit_edit);
        beforeMonthlyFeeEdit = (EditText) rootView.findViewById(R.id.before_monthly_fee_edit);
        afterDepositEdit = (EditText) rootView.findViewById(R.id.after_deposit_edit);
        increaseRateEdit = (EditText) rootView.findViewById(R.id.increase_rate_edit);

        calMonthlyFeeRateBtn = (Button) rootView.findViewById(R.id.cal_monthly_fee_btn);

        final String[] beforeDepositEditTempResult = {""};
        final String[] beforeMonthlyFeeEditTempResult = {""};
        final String[] afterDepositEditTempResult = {""};

        beforeDepositEdit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                if(!s.toString().equals(beforeDepositEditTempResult[0])){     // StackOverflow를 막기위해,
                    if (s.length() == 0) return;
                    beforeDepositEditTempResult[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    beforeDepositEdit.setText(beforeDepositEditTempResult[0]);    // 결과 텍스트 셋팅.
                    beforeDepositEdit.setSelection(beforeDepositEditTempResult[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });
        beforeMonthlyFeeEdit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                if(!s.toString().equals(beforeMonthlyFeeEditTempResult[0])){     // StackOverflow를 막기위해,
                    if (s.length() == 0) return;
                    beforeMonthlyFeeEditTempResult[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    beforeMonthlyFeeEdit.setText(beforeMonthlyFeeEditTempResult[0]);    // 결과 텍스트 셋팅.
                    beforeMonthlyFeeEdit.setSelection(beforeMonthlyFeeEditTempResult[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });
        afterDepositEdit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                if(!s.toString().equals(afterDepositEditTempResult[0])){     // StackOverflow를 막기위해,
                    if (s.length() == 0) return;
                    afterDepositEditTempResult[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    afterDepositEdit.setText(afterDepositEditTempResult[0]);    // 결과 텍스트 셋팅.
                    afterDepositEdit.setSelection(afterDepositEditTempResult[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });

        calMonthlyFeeRateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double beforeDeposit;
                double beforeMonthlyFee;
                double afterDeposit;
                double increaseRate;

                if (beforeDepositEdit.getText().length() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.before_deposit_warning_toast), Toast.LENGTH_LONG).show();
                    beforeDepositEdit.requestFocus();
                    return;
                }
                if (beforeMonthlyFeeEdit.getText().length() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.before_monthly_fee_warning_toast), Toast.LENGTH_LONG).show();
                    beforeMonthlyFeeEdit.requestFocus();
                    return;
                }
                if (afterDepositEdit.getText().length() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.after_deposit_warning_toast), Toast.LENGTH_LONG).show();
                    afterDepositEdit.requestFocus();
                    return;
                }
                if (increaseRateEdit.getText().length() == 0) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.increase_rate_warning_toast), Toast.LENGTH_LONG).show();
                    increaseRateEdit.requestFocus();
                    return;
                }

                beforeDeposit =  Double.parseDouble(beforeDepositEdit.getText().toString().replaceAll(",", ""));
                beforeMonthlyFee = Double.parseDouble(beforeMonthlyFeeEdit.getText().toString().replaceAll(",", ""));
                afterDeposit = Double.parseDouble(afterDepositEdit.getText().toString().replaceAll(",", ""));
                increaseRate = Double.parseDouble(increaseRateEdit.getText().toString());

                double result = ((((beforeDeposit * (1 + (increaseRate/100))) - afterDeposit) * (increaseRate/100)) / 12 ) + (beforeMonthlyFee * (1 + (increaseRate/100)));
                double yearlyFee = result * 12;

                increaseRateResult.setText(increaseRateEdit.getText());
                depositResult.setText(df.format((long)afterDeposit));
                monthlyFeeResult.setText(df.format((long)result));
                yearlyFeeResult.setText(df.format((long)yearlyFee));
            }
        });


        return rootView;
    }

    private double convertPyeongToMeter(double pyeong) {
        Double pyeongToMeter = pyeong * PYEONG_TO_METER;
        return pyeongToMeter;
    }

    private void helpPopupWindow() {
        final View layout;
        try {
            //  LayoutInflater 객체와 시킴
            final LayoutInflater inflater = (LayoutInflater) MonthlyFeeIncreaseRateFragment.this.getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            layout = inflater.inflate(R.layout.help_popup,
                    (ViewGroup) getActivity().findViewById(R.id.help_popup_element));

            final LinearLayout top = (LinearLayout) layout.findViewById(R.id.help_popup_linear);
            TextView title = (TextView) layout.findViewById(R.id.help_popup_title);
            Button help_popup_btn = (Button) layout.findViewById(R.id.help_popup_btn);

//            if (mHeightPixels <= 800) windowHeight = 200;
            pwindo = new PopupWindow(layout,
                    RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            TextView tv1 = new TextView(this.getActivity());
            TextView tv2 = new TextView(this.getActivity());

            title.setText(R.string.acquisition_help_title);
            tv1.setText(R.string.acquisition_help_detail1);
            tv2.setText(R.string.acquisition_help_detail2);
            top.addView(tv1);
            top.addView(tv2);

            help_popup_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pwindo.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
