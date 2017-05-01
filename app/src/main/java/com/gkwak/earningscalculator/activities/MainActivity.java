package com.gkwak.earningscalculator.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.gkwak.earningscalculator.R;
import com.gkwak.earningscalculator.adapters.MyAdapter;
import com.gkwak.earningscalculator.fragments.AcquisitionTaxFragment;
import com.gkwak.earningscalculator.fragments.CommissionFragment;
import com.gkwak.earningscalculator.fragments.EarningsFragment;
import com.gkwak.earningscalculator.fragments.InterestFragment;
import com.gkwak.earningscalculator.fragments.SquareMeterFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment(new EarningsFragment(), getResources().getString(R.string.fragment_1));
        adapter.addFragment(new InterestFragment(), getResources().getString(R.string.fragment_2));
        adapter.addFragment(new CommissionFragment(), getResources().getString(R.string.fragment_3));
        adapter.addFragment(new SquareMeterFragment(), getResources().getString(R.string.fragment_4));
        adapter.addFragment(new AcquisitionTaxFragment(), getResources().getString(R.string.fragment_5));
//        adapter.addFragment(new MyFragment(), "Category 4");
//        adapter.addFragment(new MyFragment(), "Category 5");
//        adapter.addFragment(new MyFragment(), "Category 6");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}