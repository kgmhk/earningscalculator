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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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

public class SubscriptionPlusFragment extends Fragment {
    private Spinner  hasHouseSpinner, noHousePeriodSpinner, numberOfFamilySpinner,
            subscriptionAccountPeriodSpinner;
    private static String TAG = "SUBSCRIPTION_FRAGMENT";
    private static Double PYEONG_TO_METER = 3.305785;
    private TextView totalSubscriptionPlusScore, noHousePeriodResult, noHousePeriodScoreResult,
            numberOfFamilyResult, numberOfFamilyScoreResult, subscriptionAccountPeriodResult,
            subscriptionAccountPeriodScoreResult;
    private Button calSubscriptionBtn;
    private EditText acquisitionSquareEdit, acquisitionPriceEdit;
    private LinearLayout acquisitionSquareLayout, acquisitionSquaredMethodLayout;
    private PopupWindow pwindo;
    private LinearLayout noHousePeriodlayout;

    private int hasHouseSpinnerPosition = 0;
    private int noHousePeriodSpinnerPosition = 0;
    private int numberOfFamilySpinnerPosition = 0;
    private int subscriptionAccountPeriodSpinnerPosition = 0;

    private String hasHouseSpinnerString = "0";
    private String noHousePeriodSpinnerString = "0년";
    private String numberOfFamilySpinnerString = "0명";
    private String subscriptionAccountPeriodSpinnerString = "0년";

    private int[] noHousePeriodScoreArray = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
    private int[] numberOfFamilyScoreArray = {5, 10, 15, 20, 25, 30, 35};
    private int[] periodOfSubscriptionAccountScoreArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_subscription, container, false);

        calSubscriptionBtn = (Button) rootView.findViewById(R.id.cal_subscription_btn);
        noHousePeriodlayout = (LinearLayout) rootView.findViewById(R.id.no_house_layout);

        totalSubscriptionPlusScore = (TextView) rootView.findViewById(R.id.total_subscription_plus_score_edit);
        noHousePeriodResult = (TextView) rootView.findViewById(R.id.no_house_period_result);
        noHousePeriodScoreResult = (TextView) rootView.findViewById(R.id.no_house_period_score_result);
        numberOfFamilyResult = (TextView) rootView.findViewById(R.id.number_of_family_result);
        numberOfFamilyScoreResult = (TextView) rootView.findViewById(R.id.number_of_family_score_result);
        subscriptionAccountPeriodResult = (TextView) rootView.findViewById(R.id.subscription_account_period_result);
        subscriptionAccountPeriodScoreResult = (TextView) rootView.findViewById(R.id.subscription_account_period_score_result);

        hasHouseSpinner = (Spinner) rootView.findViewById(R.id.has_house_spinner);
        ArrayAdapter hasHouseSpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.has_house, android.R.layout.simple_spinner_item);
        hasHouseSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hasHouseSpinner.setAdapter(hasHouseSpinnerAdapter);
        hasHouseSpinner.setOnItemSelectedListener(mOnHasHouseSpinnerListener);

        noHousePeriodSpinner = (Spinner) rootView.findViewById(R.id.no_house_period_spinner);
        ArrayAdapter noHousePeriodSpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.no_house_period, android.R.layout.simple_spinner_item);
        noHousePeriodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noHousePeriodSpinner.setAdapter(noHousePeriodSpinnerAdapter);
        noHousePeriodSpinner.setOnItemSelectedListener(mOnNoHousePeriodSpinnerListener);

        numberOfFamilySpinner = (Spinner) rootView.findViewById(R.id.number_of_family_spinner);
        ArrayAdapter numberOfFamilySpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.number_of_family, android.R.layout.simple_spinner_item);
        numberOfFamilySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfFamilySpinner.setAdapter(numberOfFamilySpinnerAdapter);
        numberOfFamilySpinner.setOnItemSelectedListener(mOnNumberOfFamilySpinnerListener);

        subscriptionAccountPeriodSpinner = (Spinner) rootView.findViewById(R.id.subscription_account_period_spinner);
        ArrayAdapter subscriptionAccountPeriodSpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.subscription_account_period, android.R.layout.simple_spinner_item);
        subscriptionAccountPeriodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subscriptionAccountPeriodSpinner.setAdapter(subscriptionAccountPeriodSpinnerAdapter);
        subscriptionAccountPeriodSpinner.setOnItemSelectedListener(mOnSubscriptionAccountPeriodSpinnerListener);

        final String[] acquisitionSquareEditTempResult = {""};
        final String[] acquisitionPriceEditTempResult = {""};

        calSubscriptionBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int noHouseScore = noHousePeriodScoreArray[noHousePeriodSpinnerPosition];
                int numberOfFamilyScore = numberOfFamilyScoreArray[numberOfFamilySpinnerPosition];
                int subscriptionAccountScore = periodOfSubscriptionAccountScoreArray[subscriptionAccountPeriodSpinnerPosition];

                int result = noHouseScore + numberOfFamilyScore + subscriptionAccountScore;

                totalSubscriptionPlusScore.setText(result + "");

                noHousePeriodResult.setText(noHousePeriodSpinnerString);
                noHousePeriodScoreResult.setText(noHouseScore + "점");

                numberOfFamilyResult.setText(numberOfFamilySpinnerString);
                numberOfFamilyScoreResult.setText(numberOfFamilyScore + "점");

                subscriptionAccountPeriodResult.setText(subscriptionAccountPeriodSpinnerString);
                subscriptionAccountPeriodScoreResult.setText(subscriptionAccountScore + "점");
            }
        });

        return rootView;
    }


    private AdapterView.OnItemSelectedListener mOnHasHouseSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Log.i(TAG, "mOnHasHouseSpinnerListener() entered!!");
            String selItem= (String) hasHouseSpinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);
            hasHouseSpinnerPosition = position;
            hasHouseSpinnerString = selItem;

            switch (position) {
                case 0:
                    Log.i(TAG, "clicked positon 0");
                    noHousePeriodlayout.setVisibility(View.GONE);
                    noHousePeriodSpinnerPosition = 0;
                    noHousePeriodSpinnerString = "0년";

                    noHousePeriodSpinner.setSelection(0);
                    numberOfFamilySpinner.setSelection(0);
                    subscriptionAccountPeriodSpinner.setSelection(0);

                    clearAllOfText();
                    return;
                default:
                    noHousePeriodlayout.setVisibility(View.VISIBLE);
                    noHousePeriodSpinner.setSelection(0);
                    numberOfFamilySpinner.setSelection(0);
                    subscriptionAccountPeriodSpinner.setSelection(0);

                    clearAllOfText();
                    Log.i(TAG, "clicked positon 1");
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };

    private AdapterView.OnItemSelectedListener mOnNoHousePeriodSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            Log.i(TAG, "mOnNoHousePeriodSpinnerListener() entered!!");
            String selItem= (String) noHousePeriodSpinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);

            noHousePeriodSpinnerPosition = position;
            noHousePeriodSpinnerString = selItem;

            switch (position) {
                case 0:
                    return;
                case 1:
                    Log.i(TAG, "select position 1");
                    return;
                default:
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };

    private AdapterView.OnItemSelectedListener mOnNumberOfFamilySpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            Log.i(TAG, "mOnNumberOfFamilySpinnerListener() entered!!");
            String selItem= (String) numberOfFamilySpinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);

            numberOfFamilySpinnerPosition = position;
            numberOfFamilySpinnerString = selItem;

            switch (position) {
                case 0:
                    return;
                case 1:
                    Log.i(TAG, "select position 1");
                    return;
                default:
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };

    private AdapterView.OnItemSelectedListener mOnSubscriptionAccountPeriodSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            Log.i(TAG, "mOnNumberOfFamilySpinnerListener() entered!!");
            String selItem= (String) subscriptionAccountPeriodSpinner.getSelectedItem();
            Log.i(TAG, "Spinner selected item = "+selItem);

            subscriptionAccountPeriodSpinnerPosition = position;
            subscriptionAccountPeriodSpinnerString = selItem;

            switch (position) {
                case 0:
                    return;
                case 1:
                    Log.i(TAG, "select position 1");
                    return;
                default:
                    return;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.i(TAG, "onNothingSelected() entered!!");
        }
    };

    private void helpPopupWindow() {
        final View layout;
        try {
            //  LayoutInflater 객체와 시킴
            final LayoutInflater inflater = (LayoutInflater) SubscriptionPlusFragment.this.getActivity()
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

    private void clearAllOfText() {
        totalSubscriptionPlusScore.setText("0");

        noHousePeriodResult.setText("0");
        noHousePeriodScoreResult.setText("0");

        numberOfFamilyResult.setText("0");
        numberOfFamilyScoreResult.setText("0");

        subscriptionAccountPeriodResult.setText("0");
        subscriptionAccountPeriodScoreResult.setText("0");
    }
}
