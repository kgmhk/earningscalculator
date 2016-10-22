package com.gkwak.earningscalculator;

import com.gkwak.earningscalculator.interfaces.PopupEnum;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";
    private static final String TAG = "MAIN_ACTIVITY";

    private PopupWindow pwindo;
    private EditText monthly_rent_edit, rent_deposit_edit, buy_total_price_edit,
            loan_price_edit, loan_rate_edit;
    private TextView market_price_edit, rent_rate_edit, yearly_rent_revenu_edit,
            yearly_interest_edit, real_investment_edit, yearly_pure_revenu_edit,
            monthly_pure_revenu_edit, monthly_interest_edit;
    private ImageButton expect_market_price_help_button, cal_rent_revenu_help_button, cal_rent_revenu_result_help_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        // 1st Layout
        rent_deposit_edit = (EditText) findViewById(R.id.rent_deposit_edit);
        monthly_rent_edit = (EditText) findViewById(R.id.monthly_rent_edit);
        market_price_edit = (TextView) findViewById(R.id.market_price_edit);

        // 2st Layout
        buy_total_price_edit = (EditText) findViewById(R.id.buy_total_price_edit);
        loan_price_edit = (EditText) findViewById(R.id.loan_price_edit);
        loan_rate_edit = (EditText) findViewById(R.id.loan_rate_edit);

        // 3st Layout
        rent_rate_edit = (TextView) findViewById(R.id.rent_rate_edit);
        yearly_rent_revenu_edit = (TextView) findViewById(R.id.yearly_rent_revenu_edit);
        yearly_interest_edit = (TextView) findViewById(R.id.yearly_interest_edit);
        real_investment_edit = (TextView) findViewById(R.id.real_investment_edit);
        yearly_pure_revenu_edit = (TextView) findViewById(R.id.yearly_pure_revenu_edit);
        monthly_pure_revenu_edit = (TextView) findViewById(R.id.monthly_pure_revenu_edit);
        monthly_interest_edit = (TextView) findViewById(R.id.monthly_interest_edit);

        expect_market_price_help_button = (ImageButton) findViewById(R.id.expect_market_price_help_button);
        cal_rent_revenu_help_button = (ImageButton) findViewById(R.id.cal_rent_revenu_help_button);
        cal_rent_revenu_result_help_button = (ImageButton) findViewById(R.id.cal_rent_revenu_result_help_button);

        // 세자리로 끊어서 쉼표 보여주고, 소숫점 셋째짜리까지 보여준다.
        final DecimalFormat df = new DecimalFormat("###,###.####");
// 값 셋팅시, StackOverFlow를 막기 위해서, 바뀐 변수를 저장해준다.
        // 1st Layout
        final String[] rent_deposit_result = {""};
        final String[] monthly_rent_result = {""};

        // 2st Layout
        final String[] buy_total_price_result = {""};
        final String[] loan_price_result = {""};
        final String[] loan_rate_result = {""};

        // help button
        expect_market_price_help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "expect_market_price_help_button");
                helpPopupWindow(PopupEnum.EXPECT_MARKET_PRICE);
            }
        });
        cal_rent_revenu_help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "cal_rent_revenu_help_button");
                helpPopupWindow(PopupEnum.CAL_RENT_REVENU);
            }
        });
        cal_rent_revenu_result_help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "cal_rent_revenu_result_help_button");
                helpPopupWindow(PopupEnum.CAL_RESULT_RENT_REVENU);
            }
        });

        // 1st Layout
        rent_deposit_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){

                if(!s.toString().equals(rent_deposit_result[0])){     // StackOverflow를 막기위해,

                    if (s.length() == 0) return;
                    Long rentDeposit = Long.parseLong(rent_deposit_edit.getText().toString().replaceAll(",", ""));
                    Long monthlyRent = 0L;
                    Long buyTotalPrice = 0L;
                    Long loanPrice = 0L;
                    if (monthly_rent_edit.getText().toString().length() != 0)
                        monthlyRent = Long.parseLong(monthly_rent_edit.getText().toString().replaceAll(",", ""));
                    if (buy_total_price_edit.getText().toString().length() != 0)
                        buyTotalPrice = Long.parseLong(buy_total_price_edit.getText().toString().replaceAll(",", ""));
                    if (loan_price_edit.getText().toString().length() != 0)
                        loanPrice = Long.parseLong(loan_price_edit.getText().toString().replaceAll(",", ""));

                    Long calResult = rentDeposit + (monthlyRent * 200);
                    market_price_edit.setText(df.format(calResult)+"");

                    Long realInvestmentCalResult = buyTotalPrice - rentDeposit - loanPrice;
                    real_investment_edit.setText(df.format(realInvestmentCalResult)); // 실투자비

                    rent_deposit_result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    rent_deposit_edit.setText(rent_deposit_result[0]);    // 결과 텍스트 셋팅.
                    rent_deposit_edit.setSelection(rent_deposit_result[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });
        monthly_rent_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(monthly_rent_result[0])) {
                    Log.i(TAG, " a : " + s.toString());
                    if (s.length() == 0) return;
                    Long rentDeposit = 0L;
                    Long monthlyRent = 0L;
                    if (rent_deposit_edit.getText().toString().length() != 0)
                        rentDeposit = Long.parseLong(rent_deposit_edit.getText().toString().replaceAll(",", ""));
                    if (monthly_rent_edit.getText().toString().length() != 0)
                        monthlyRent = Long.parseLong(monthly_rent_edit.getText().toString().replaceAll(",", ""));

                    Long calResult = rentDeposit + (monthlyRent * 200);
                    market_price_edit.setText(df.format(calResult) + ""); // 적정시세

                    Long yearlyRentRevenuCalResult = monthlyRent * 12;
                    yearly_rent_revenu_edit.setText(df.format(yearlyRentRevenuCalResult) + ""); // 연간 임대료 수입

                    monthly_rent_result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    monthly_rent_edit.setText(monthly_rent_result[0]);    // 결과 텍스트 셋팅.
                    monthly_rent_edit.setSelection(monthly_rent_result[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });

        //2st Layout
        buy_total_price_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(buy_total_price_result[0])) {
                    Log.i(TAG, " a : " + s.toString());
                    if (s.length() == 0) return;
                    Long rentDeposit = 0L;
                    Long loanPrice = 0L;
                    Long buyTotalPrice = 0L;
                    if (rent_deposit_edit.getText().toString().length() != 0)
                        rentDeposit = Long.parseLong(rent_deposit_edit.getText().toString().replaceAll(",", ""));
                    if (buy_total_price_edit.getText().toString().length() != 0)
                        buyTotalPrice = Long.parseLong(buy_total_price_edit.getText().toString().replaceAll(",", ""));
                    if (loan_price_edit.getText().toString().length() != 0)
                        loanPrice = Long.parseLong(loan_price_edit.getText().toString().replaceAll(",", ""));
                    Long realInvestmentCalResult = buyTotalPrice - rentDeposit - loanPrice;
                    real_investment_edit.setText(df.format(realInvestmentCalResult)); // 실투자비

                    buy_total_price_result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    buy_total_price_edit.setText(buy_total_price_result[0]);    // 결과 텍스트 셋팅.
                    buy_total_price_edit.setSelection(buy_total_price_result[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });
        loan_price_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(loan_price_result[0])) {
                    Log.i(TAG, " a : " + s.toString());
                    if (s.length() == 0) return;
                    Float loanPrice = 0F;
                    Float loanRate = 0F;
                    if (loan_price_edit.getText().toString().length() != 0)
                        loanPrice = Float.parseFloat(loan_price_edit.getText().toString().replaceAll(",", ""));
                    if (loan_rate_edit.getText().toString().length() != 0)
                        loanRate = Float.parseFloat(loan_rate_edit.getText().toString().replaceAll(",", ""));

                    Float yearlyRevenuCalResult = (loanPrice * loanRate) / 100;
                    yearly_interest_edit.setText(df.format(Math.round(yearlyRevenuCalResult)));


                    Long rentDeposit = 0L;
                    Long buyTotalPrice = 0L;
                    if (rent_deposit_edit.getText().toString().length() != 0)
                        rentDeposit = Long.parseLong(rent_deposit_edit.getText().toString().replaceAll(",", ""));
                    if (buy_total_price_edit.getText().toString().length() != 0)
                        buyTotalPrice = Long.parseLong(buy_total_price_edit.getText().toString().replaceAll(",", ""));
                    Long realInvestmentCalResult = buyTotalPrice - rentDeposit - Math.round(loanPrice);
                    real_investment_edit.setText(df.format(realInvestmentCalResult)); // 실투자비

                    loan_price_result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    loan_price_edit.setText(loan_price_result[0]);    // 결과 텍스트 셋팅.
                    loan_price_edit.setSelection(loan_price_result[0].length());     // 커서를 제일 끝으로 보냄.
                }
            }
        });
        loan_rate_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(loan_rate_result[0])) {
                    if (s.length() == 0) return;

                    Float loanPrice = 0F;
                    Float loanRate = 0F;
                    if (loan_price_edit.getText().toString().length() != 0)
                        loanPrice = Float.parseFloat(loan_price_edit.getText().toString().replaceAll(",", ""));
                    if (loan_rate_edit.getText().toString().length() != 0)
                        loanRate = Float.parseFloat(loan_rate_edit.getText().toString().replaceAll(",", ""));

                    Float yearlyRevenuCalResult = (loanPrice * loanRate) / 100;
                    yearly_interest_edit.setText(df.format(Math.round(yearlyRevenuCalResult)));
                }
            }
        });

        // 3st Layout
        yearly_rent_revenu_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Long yearlyRentRevenu = 0L;
                Long yearlyInterest = 0L;
                if (yearly_rent_revenu_edit.getText().toString().length() != 0)
                    yearlyRentRevenu = Long.parseLong(yearly_rent_revenu_edit.getText().toString().replaceAll(",", ""));
                if (yearly_interest_edit.getText().toString().length() != 0)
                    yearlyInterest = Long.parseLong(yearly_interest_edit.getText().toString().replaceAll(",", ""));

                Long yearlyPureRevenuCalResult = yearlyRentRevenu - yearlyInterest;
                yearly_pure_revenu_edit.setText(df.format(yearlyPureRevenuCalResult));
            }
        });
        yearly_interest_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Long yearlyRentRevenu = 0L;
                Long yearlyInterest = 0L;
                if (yearly_rent_revenu_edit.getText().toString().length() != 0)
                    yearlyRentRevenu = Long.parseLong(yearly_rent_revenu_edit.getText().toString().replaceAll(",", ""));
                if (yearly_interest_edit.getText().toString().length() != 0)
                    yearlyInterest = Long.parseLong(yearly_interest_edit.getText().toString().replaceAll(",", ""));

                Long yearlyPureRevenuCalResult = yearlyRentRevenu - yearlyInterest;
                Long monthlyInterest = yearlyInterest / 12;
                monthly_interest_edit.setText(df.format(monthlyInterest));
                yearly_pure_revenu_edit.setText(df.format(yearlyPureRevenuCalResult));
            }
        });
        yearly_pure_revenu_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Float yearlyPureRevenu = 0F;
                if (yearly_pure_revenu_edit.getText().toString().length() != 0)
                    yearlyPureRevenu = Float.parseFloat(yearly_pure_revenu_edit.getText().toString().replaceAll(",", ""));

                Long monthlyPureRevenuCalResult = (long)Math.round(yearlyPureRevenu)/12;
                monthly_pure_revenu_edit.setText(df.format(monthlyPureRevenuCalResult)); // 월 순수익

                Float realInvestment = 0F;
                if (real_investment_edit.getText().toString().length() != 0)
                    realInvestment = Float.parseFloat(real_investment_edit.getText().toString().replaceAll(",", ""));
                Float rentRevenuRate = (yearlyPureRevenu / realInvestment) * 100;
                rent_rate_edit.setText(String.format("%.2f", rentRevenuRate));
            }
        });
        real_investment_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Float yearlyPureRevenu = 0F;
                Float realInvestment = 0F;
                if (yearly_pure_revenu_edit.getText().toString().length() != 0)
                    yearlyPureRevenu = Float.parseFloat(yearly_pure_revenu_edit.getText().toString().replaceAll(",", ""));
                if (real_investment_edit.getText().toString().length() != 0)
                    realInvestment = Float.parseFloat(real_investment_edit.getText().toString().replaceAll(",", ""));
                Float rentRevenuRate = (yearlyPureRevenu / realInvestment) * 100;
                rent_rate_edit.setText(String.format("%.2f", rentRevenuRate));
            }
        });

    }

    private void helpPopupWindow(PopupEnum popupEnum) {
        final View layout;
        try {
            //  LayoutInflater 객체와 시킴
            final LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            layout = inflater.inflate(R.layout.help_popup,
                    (ViewGroup) findViewById(R.id.help_popup_element));

            final LinearLayout top = (LinearLayout) layout.findViewById(R.id.help_popup_linear);
            TextView title = (TextView) layout.findViewById(R.id.help_popup_title);
            Button help_popup_btn = (Button) layout.findViewById(R.id.help_popup_btn);

//            if (mHeightPixels <= 800) windowHeight = 200;
            pwindo = new PopupWindow(layout,
                    RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            TextView tv1 = new TextView(this);
            TextView tv2 = new TextView(this);

            switch (popupEnum) {
                case EXPECT_MARKET_PRICE:
                    title.setText(R.string.expect_market_price);
                    tv1.setText(R.string.expect_market_price_detail_1);
                    tv2.setText(R.string.expect_market_price_detail_2);
                    top.addView(tv1);
                    top.addView(tv2);
                    break;
                case CAL_RENT_REVENU:
                    title.setText(R.string.cal_rent_revenu_title);
                    tv1.setText(R.string.cal_rent_revenu_detail_1);
                    tv2.setText(R.string.cal_rent_revenu_detail_2);
                    top.addView(tv1);
                    top.addView(tv2);
                    break;
                case CAL_RESULT_RENT_REVENU:
                    title.setText(R.string.cal_rent_revenu_result_title);
                    tv1.setText(R.string.cal_rent_revenu_result_detail_1);
                    tv2.setText(R.string.cal_rent_revenu_result_detail_2);
                    top.addView(tv1);
                    top.addView(tv2);
                    break;
                default:
                    title.setText(R.string.expect_market_price);
                    break;
            }

            help_popup_btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pwindo.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
