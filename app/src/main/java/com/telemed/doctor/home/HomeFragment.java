package com.telemed.doctor.home;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;


public class HomeFragment extends Fragment {

    private AppCompatTextView tvMyProfile, tvMyConsults, tvMyDashboard,tvNotification, tvSetting, tvSignOut;
    private Button btnMySchedule;
    private HomeFragmentSelectedListener mFragmentListener;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);

    }

    private void initView(View v) {


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
}
