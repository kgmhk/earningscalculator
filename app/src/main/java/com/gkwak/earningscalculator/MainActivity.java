package com.gkwak.earningscalculator;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    // Remove the below line after defining your own ad unit ID.
    private static final String TOAST_TEXT = "Test ads are being shown. "
            + "To show live ads, replace the ad unit ID in res/values/strings.xml with your own ad unit ID.";
    private static final String TAG = "MAIN_ACTIVITY";

    private EditText monthly_rent_edit, rent_deposit_edit, market_price_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        assert mAdView != null;
//        mAdView.loadAd(adRequest);

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show();


        rent_deposit_edit = (EditText) findViewById(R.id.rent_deposit_edit);
        monthly_rent_edit = (EditText) findViewById(R.id.monthly_rent_edit);
        market_price_edit = (EditText) findViewById(R.id.market_price_edit);

        // 세자리로 끊어서 쉼표 보여주고, 소숫점 셋째짜리까지 보여준다.
        final DecimalFormat df = new DecimalFormat("###,###.####");
// 값 셋팅시, StackOverFlow를 막기 위해서, 바뀐 변수를 저장해준다.
        final String[] result = {""};

        rent_deposit_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){

                if(!s.toString().equals(result[0])){     // StackOverflow를 막기위해,
                    result[0] = df.format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
                    rent_deposit_edit.setText(result[0]);    // 결과 텍스트 셋팅.
                    rent_deposit_edit.setSelection(result[0].length());     // 커서를 제일 끝으로 보냄.
                }

//                Log.i(TAG, " a : " + s.toString());
//                if (s.length() == 0) rent_deposit_edit.setText(0+"");
//                int rentDeposit = Integer.parseInt(rent_deposit_edit.getText().toString());
//                int monthlyRent = Integer.parseInt(monthly_rent_edit.getText().toString());
//                int result = rentDeposit + (monthlyRent * 200);
//                market_price_edit.setText(result+"");
            }
        });
        monthly_rent_edit.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){
                Log.i(TAG, " a : " + s.toString());
                if (s.length() == 0) monthly_rent_edit.setText(0+"");
                int rentDeposit = Integer.parseInt(rent_deposit_edit.getText().toString());
                int monthlyRent = Integer.parseInt(monthly_rent_edit.getText().toString());
                int result = rentDeposit + (monthlyRent * 200);
                market_price_edit.setText(result+"");
            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
