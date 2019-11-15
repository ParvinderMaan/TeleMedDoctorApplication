package com.telemed.doctor.home;


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
import com.telemed.doctor.miscellaneous.SignOutDialogFragment;


public class HomeFragment extends Fragment {

    private AppCompatTextView tvMyProfile, tvMyConsults, tvMyDashboard,tvNotification, tvSetting, tvSignOut;
    private Button btnMySchedule;

    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        btnMySchedule.setOnClickListener(mClickListener);






        tvMyProfile = v.findViewById(R.id.tv_my_profile);
        tvMyProfile.setOnClickListener(mClickListener);


        tvMyConsults = v.findViewById(R.id.tv_my_consults);
        tvMyConsults.setOnClickListener(mClickListener);


        tvMyDashboard = v.findViewById(R.id.tv_my_dashboard);
        tvMyDashboard.setOnClickListener(mClickListener);

        tvNotification = v.findViewById(R.id.tv_notification);
        tvNotification.setOnClickListener(mClickListener);


        tvSetting = v.findViewById(R.id.tv_setting);
        tvSetting.setOnClickListener(mClickListener);


        tvSignOut = v.findViewById(R.id.tv_sign_out);
        tvSignOut.setOnClickListener(mClickListener);
    }


    private View.OnClickListener mClickListener = v -> {
        switch (v.getId()) {
            case R.id.tv_my_profile:
                if (getActivity() != null)
                    ((HomeActivity) getActivity()).showMyProfileFragment();
                break;

            case R.id.tv_my_consults:
                if (getActivity() != null)
                    ((HomeActivity) getActivity()).showMyConsultsFragment();
                break;

            case R.id.tv_my_dashboard:
                if (getActivity() != null)
                    ((HomeActivity) getActivity()).showMyDashboardFragment();
                break;

            case R.id.tv_notification:
                if (getActivity() != null)
                    ((HomeActivity) getActivity()).showNotificationFragment();
                break;

            case R.id.tv_setting:
                if (getActivity() != null)
                    ((HomeActivity) getActivity()).showSettingFragment();
                break;
            case R.id.tv_sign_out:

                if (getActivity() != null)
                    ((HomeActivity) getActivity()).showSignOutDialog();
                break;
            case R.id.btn_my_schedule:
                if (getActivity() != null)
                    ((HomeActivity) getActivity()).showMyScheduleFragment();
                break;
        }


    };
}
