package com.gkwak.earningscalculator.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gkwak.earningscalculator.R;
import com.gkwak.earningscalculator.adapters.MyAdapter;
import com.gkwak.earningscalculator.fragments.AcquisitionTaxFragment;
import com.gkwak.earningscalculator.fragments.CommissionFragment;
import com.gkwak.earningscalculator.fragments.DepositInterestFragment;
import com.gkwak.earningscalculator.fragments.EarningsFragment;
import com.gkwak.earningscalculator.fragments.InstallmentSavingsFragment;
import com.gkwak.earningscalculator.fragments.InterestFragment;
import com.gkwak.earningscalculator.fragments.MonthlyFeeIncreaseRateFragment;
import com.gkwak.earningscalculator.fragments.SquareMeterFragment;
import com.gkwak.earningscalculator.fragments.SubscriptionPlusFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load an ad into the AdMob banner view.
        MobileAds.initialize(this, "ca-app-pub-2778546304304506~7180980878");


        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("693B42DE09B51B778B3FDA8211FAA4B7") // test device
//                .setRequestAgent("android_studio:ad_template").build();
                .build();
        adView.loadAd(adRequest);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment(new EarningsFragment(), getResources().getString(R.string.fragment_1));
        adapter.addFragment(new InterestFragment(), getResources().getString(R.string.fragment_2));
        adapter.addFragment(new CommissionFragment(), getResources().getString(R.string.fragment_3));
        adapter.addFragment(new SquareMeterFragment(), getResources().getString(R.string.fragment_4));
        adapter.addFragment(new AcquisitionTaxFragment(), getResources().getString(R.string.fragment_5));
        adapter.addFragment(new SubscriptionPlusFragment(), getResources().getString(R.string.fragment_6));
        adapter.addFragment(new DepositInterestFragment(), getResources().getString(R.string.fragment_7));
        adapter.addFragment(new InstallmentSavingsFragment(), getResources().getString(R.string.fragment_8));
        adapter.addFragment(new MonthlyFeeIncreaseRateFragment(), getResources().getString(R.string.fragment_9));
//        adapter.addFragment(new MyFragment(), "Category 4");
//        adapter.addFragment(new MyFragment(), "Category 5");
//        adapter.addFragment(new MyFragment(), "Category 6");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}