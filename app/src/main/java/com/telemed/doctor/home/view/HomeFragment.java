package com.telemed.doctor.home.view;


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
import com.telemed.doctor.home.model.WelcomeInfoResponse;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.miscellaneous.viewmodel.HomeViewModel;

import java.util.HashMap;


public class HomeFragment extends Fragment {
    private final String TAG=HomeFragment.class.getSimpleName();
    private AppCompatTextView tvMyProfile, tvMyConsults, tvMyDashboard,tvNotification,
            tvSetting, tvSignOut,tvDocWelcome,  tvPendingAppmntCount, tvUpcomingAppmntCount, tvNotificationCount;
    private Button btnMySchedule;
    private ProgressBar progressBar;
    private HomeFragmentSelectedListener mFragmentListener;
    private HomeViewModel mViewModel;
    private String mFirstName;
    private SharedPrefHelper mHelper;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
        mFirstName=mHelper.read(SharedPrefHelper.KEY_FIRST_NAME, "");
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization","Bearer "+mAccessToken);
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
        initListener();
        initObserver();
        String welcomeTxt = getResources().getString(R.string.title_one) + " " + mFirstName + ".";
        tvDocWelcome.setText(welcomeTxt);
        mViewModel.fetchWelcomeInfo(mHeaderMap);
    }


    private void initView(View v) {
        tvDocWelcome = v.findViewById(R.id.tv_doc_welcome);
        btnMySchedule = v.findViewById(R.id.btn_my_schedule);
        tvMyProfile = v.findViewById(R.id.tv_my_profile);
        tvMyConsults = v.findViewById(R.id.tv_my_consults);
        tvMyDashboard = v.findViewById(R.id.tv_my_dashboard);
        tvNotification = v.findViewById(R.id.tv_notification);
        tvSetting = v.findViewById(R.id.tv_setting);
        tvSignOut = v.findViewById(R.id.tv_sign_out);

        progressBar=v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorBlue), android.graphics.PorterDuff.Mode.SRC_IN);


        tvPendingAppmntCount= v.findViewById(R.id.tv_pending_appmnt_count);
        tvUpcomingAppmntCount= v.findViewById(R.id.tv_upcoming_appmnt_count);
        tvNotificationCount= v.findViewById(R.id.tv_notification_count);

    }
    private void initListener() {
        btnMySchedule.setOnClickListener(mClickListener);
        tvMyProfile.setOnClickListener(mClickListener);
        tvMyConsults.setOnClickListener(mClickListener);
        tvMyDashboard.setOnClickListener(mClickListener);
        tvNotification.setOnClickListener(mClickListener);
        tvSetting.setOnClickListener(mClickListener);
        tvSignOut.setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener = v -> {
        switch (v.getId()) {
            case R.id.tv_my_profile:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("ProfileFragment", null);
                break;

            case R.id.tv_my_consults:
                if (mFragmentListener!= null)
                    mFragmentListener.showFragment("MyConsultFragment", null);
                break;

            case R.id.tv_my_dashboard:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("MyDashboardFragment", null);
                break;

            case R.id.tv_notification:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("NotificationFragment", null);
                break;

            case R.id.tv_setting:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("SettingFragment", null);
                break;
            case R.id.tv_sign_out:

                if (mFragmentListener != null)
                    mFragmentListener.showDialog("SignOutDialog");
                break;
            case R.id.btn_my_schedule:
                if (mFragmentListener != null)
                    mFragmentListener.showFragment("ScheduleFragment", null);
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
//        mViewModel.getProgress()
//                .observe(getViewLifecycleOwner(), isLoading -> {
//                progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE); });

        mViewModel.getViewEnabled()
                .observe(getViewLifecycleOwner(), this::resetEnableView);

        mViewModel.getSignOutResultant().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null){
                            mHelper.clear(); // clearing sharedPref
                            mFragmentListener.startActivity("RouterActivity", null, null);
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        if (mFragmentListener != null){
                            mHelper.clear(); // clearing sharedPref
                            mFragmentListener.startActivity("RouterActivity",null , null);
                        }
                    }
                    break;
            }

        });

        mViewModel.getResultantWelcomeInfoFirst().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        refreshUi(response.getData().getData());
                    }
                    break;

                case FAILURE:
                        if(response.getErrorMsg() != null && response.getErrorMsg().equals("Unauthorised User")){
                            mHelper.clear(); // clearing sharedPref
                            mFragmentListener.startActivity("RouterActivity", null, null);
                        }
            }
        });

    }

    private void refreshUi(WelcomeInfoResponse.Data data) {
        if(data.getDashboardInfo().getPendingAppointmentsCount()!=null &&
                data.getDashboardInfo().getPendingAppointmentsCount()!=0){
            tvPendingAppmntCount.setVisibility(View.VISIBLE);
            tvPendingAppmntCount.setText(String.valueOf(data.getDashboardInfo().getPendingAppointmentsCount()));
        }else  tvPendingAppmntCount.setVisibility(View.INVISIBLE);

        if(data.getDashboardInfo().getUpcomingAppointmentsCount()!=null &&
                data.getDashboardInfo().getUpcomingAppointmentsCount()!=0){
            tvUpcomingAppmntCount.setVisibility(View.VISIBLE);
            tvUpcomingAppmntCount.setText(String.valueOf(data.getDashboardInfo().getUpcomingAppointmentsCount()));
        }else  tvUpcomingAppmntCount.setVisibility(View.INVISIBLE);

        if(data.getDashboardInfo().getNotificationsCount()!=null &&
                data.getDashboardInfo().getNotificationsCount()!=0){
            tvNotificationCount.setVisibility(View.VISIBLE);
            tvNotificationCount.setText(String.valueOf(data.getDashboardInfo().getNotificationsCount()));
        }else  tvNotificationCount.setVisibility(View.INVISIBLE);

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

    @Override
    public void onDestroyView() {
         removeListener();
        super.onDestroyView();
    }

    private void removeListener() {
        btnMySchedule.setOnClickListener(null);
        tvMyProfile.setOnClickListener(null);
        tvMyConsults.setOnClickListener(null);
        tvMyDashboard.setOnClickListener(null);
        tvNotification.setOnClickListener(null);
        tvSetting.setOnClickListener(null);
        tvSignOut.setOnClickListener(null);
    }
}
