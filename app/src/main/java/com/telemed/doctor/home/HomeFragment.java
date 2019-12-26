package com.telemed.doctor.home;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.miscellaneous.viewmodel.HomeViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.HashMap;


public class HomeFragment extends Fragment {
    private final String TAG=HomeFragment.class.getSimpleName();
    private AppCompatTextView tvMyProfile, tvMyConsults, tvMyDashboard,tvNotification, tvSetting, tvSignOut,tvDocWelcome;
    private Button btnMySchedule;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private HomeFragmentSelectedListener mFragmentListener;
    private HomeViewModel mViewModel;
    private String mFirstName;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPrefHelper mHelper = ((TeleMedApplication) getActivity().getApplicationContext()).getSharedPrefInstance();
        mFirstName=mHelper.read(SharedPrefHelper.KEY_FIRST_NAME, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initObserver();
        String welcomeTxt = getResources().getString(R.string.title_one) + " " + mFirstName + ".";
        tvDocWelcome.setText(welcomeTxt);
    }


    private void initView(View v) {
        progressBar=v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorBlue), android.graphics.PorterDuff.Mode.SRC_IN);

        tvAlertView = v.findViewById(R.id.tv_alert_view);

        tvDocWelcome = v.findViewById(R.id.tv_doc_welcome);



        btnMySchedule = v.findViewById(R.id.btn_my_schedule);
        btnMySchedule.setOnClickListener(mOnClickListener);

        tvMyProfile = v.findViewById(R.id.tv_my_profile);
        tvMyProfile.setOnClickListener(mOnClickListener);


        tvMyConsults = v.findViewById(R.id.tv_my_consults);
        tvMyConsults.setOnClickListener(mOnClickListener);


        tvMyDashboard = v.findViewById(R.id.tv_my_dashboard);
        tvMyDashboard.setOnClickListener(mOnClickListener);

        tvNotification = v.findViewById(R.id.tv_notification);
        tvNotification.setOnClickListener(mOnClickListener);


        tvSetting = v.findViewById(R.id.tv_setting);
        tvSetting.setOnClickListener(mOnClickListener);


        tvSignOut = v.findViewById(R.id.tv_sign_out);
        tvSignOut.setOnClickListener(mOnClickListener);
    }


    private View.OnClickListener mOnClickListener = v -> {
        switch (v.getId()) {
            case R.id.tv_my_profile:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("ProfileFragment");
                break;

            case R.id.tv_my_consults:
                if (mFragmentListener!= null)
                    mFragmentListener.showFragment("MyConsultFragment");
                break;

            case R.id.tv_my_dashboard:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("MyDashboardFragment");
                break;

            case R.id.tv_notification:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("NotificationFragment");
                break;

            case R.id.tv_setting:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("SettingFragment");
                break;
            case R.id.tv_sign_out:

                if (mFragmentListener != null)
                    mFragmentListener.showDialog("SignOutDialog");
                break;
            case R.id.btn_my_schedule:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("MyScheduleFragment");
                break;
        }


    };

    public void attemptSignOut(String accessToken) {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("content-type", "application/json");     //  additional
        headerMap.put("Authorization","Bearer "+accessToken);
        mViewModel.attemptSignOut(headerMap);

    }

    private void initObserver() {

        mViewModel.getProgress()
                .observe(this, isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getViewEnabled()
                .observe(this, this::resetEnableView);

        mViewModel.getSignOutResultant().observe(this, response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null){

                            mFragmentListener.startActivity("RouterActivity");
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;
            }

        });
    }

    private void resetEnableView(Boolean isView) {
        btnMySchedule.setEnabled(isView);
        tvMyProfile.setEnabled(isView);
        tvMyConsults.setEnabled(isView);
        tvMyDashboard.setEnabled(isView);
        tvNotification.setEnabled(isView);
        tvSetting.setEnabled(isView);
        tvSignOut.setEnabled(isView);
    }


}
