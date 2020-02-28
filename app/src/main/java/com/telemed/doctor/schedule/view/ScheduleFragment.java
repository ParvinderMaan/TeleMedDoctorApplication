package com.telemed.doctor.schedule.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.SychronizeScheduleDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;
import com.telemed.doctor.schedule.viewmodel.ScheduleViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class ScheduleFragment extends Fragment {
    private final String TAG = ScheduleFragment.class.getSimpleName();
    private Button btnSynchronizeSchedule;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;
    private ScheduleViewModel mViewModel;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private ProgressBar progressBar;
    private ImageButton ibtnPrevious, ibtNext;
    private TextView tvMonthName;
    private CustomAlertTextView tvAlertView;
    private MonthPagerAdapter mMonthPagerAdapter;
    private ViewPager vpPager;
    private SychronizeScheduleDialogFragment mScheduleDialogFragment;

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        SharedPrefHelper mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        initView(v);
        initListener();
        initObserver();
        mViewModel.fetchMonthlySchedules(mHeaderMap, 0);
    }



    private void initView(View v) {
        ibtnClose = v.findViewById(R.id.ibtn_close);
        btnSynchronizeSchedule = v.findViewById(R.id.btn_synchronize_schedule);

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);

        progressBar.setVisibility(View.INVISIBLE);


        vpPager = (ViewPager) v.findViewById(R.id.view_pager);
        mMonthPagerAdapter = new MonthPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(mMonthPagerAdapter);
        vpPager.setOffscreenPageLimit(2);

        ibtnPrevious = v.findViewById(R.id.ibtn_previous);
        tvMonthName = v.findViewById(R.id.tv_month_name);
        ibtNext = v.findViewById(R.id.ibtn_next);

    }

    private void initListener() {
        ibtnClose.setOnClickListener(v -> {
            if (mFragmentListener != null) {
                mFragmentListener.popTopMostFragment();
            }

        });
        btnSynchronizeSchedule.setOnClickListener(v -> {
            mViewModel.setDialogVisiblility(true);
        });
        ibtnPrevious.setOnClickListener(v -> {
            switch (vpPager.getCurrentItem()) {
                case 0:
                    // do nothing...
                    break;
                case 1:
                    vpPager.setCurrentItem(0);
                    break;
                case 2:
                    vpPager.setCurrentItem(1);
                    break;

            }
            vpPager.getCurrentItem();


        });
        ibtNext.setOnClickListener(v -> {
            switch (vpPager.getCurrentItem()) {
                case 0:
                    vpPager.setCurrentItem(1);
                    break;

                case 1:
                    vpPager.setCurrentItem(2);
                    break;

                case 2:
                    // do nothing...
                    break;
            }

        });
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ScheduleIMonthFragment fragmentI = (ScheduleIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(position);
                        setMonthName(fragmentI.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap, 0);
                        break;

                    case 1:
                        ScheduleIIMonthFragment fragmentII = (ScheduleIIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(position);
                        setMonthName(fragmentII.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap, 1);
                        break;

                    case 2:
                        ScheduleIIIMonthFragment fragmentIII = (ScheduleIIIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(position);
                        setMonthName(fragmentIII.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap, 2);
                        break;

                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }


    public void showProgress(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }


    public void showAlertMessage(String msg) {
        tvAlertView.showTopAlert(msg);
    }

    public void setMonthName(String monthName) {
        tvMonthName.setText(monthName);
    }


    public void showFragment(String fragment, Object date) {
        mFragmentListener.showFragment("DayWiseAvailabilityFragment", date);
    }


    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getDialogVisiblility()
                .observe(getViewLifecycleOwner(), isVisible -> {
                    if (isVisible) {
                        mScheduleDialogFragment = SychronizeScheduleDialogFragment.newInstance();
                        mScheduleDialogFragment.setOnScheduleDialogListener(new SychronizeScheduleDialogFragment.SychronizeScheduleDialogListener() {
                            @Override
                            public void onClickWeekWise() {
                                if (mFragmentListener != null)
                                    mFragmentListener.showFragment("WeekDaysScheduleFragment", null);
                            }

                            @Override
                            public void onClickDateWise() {
                                if (mFragmentListener != null) {
                                    mFragmentListener.showFragment("ScheduleSychronizeFragment", null);
                                }
                            }
                        });

                        mScheduleDialogFragment.show(getChildFragmentManager(), "TAG");
                    } else {
                        mScheduleDialogFragment.setOnScheduleDialogListener(null);
                        mScheduleDialogFragment.dismiss();
                    }

                });

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);


        mViewModel.getResultAllScheduleIMonth().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MonthlyScheduleResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getAvailableScheduleList() != null) {
                            ScheduleIMonthFragment fragment = (ScheduleIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(0);
                            fragment.updateUi(infoObj.getAvailableScheduleList());

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

        mViewModel.getResultAllScheduleIIMonth().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MonthlyScheduleResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getAvailableScheduleList() != null) {
                            ScheduleIIMonthFragment fragment = (ScheduleIIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(1);
                            fragment.updateUi(infoObj.getAvailableScheduleList());
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

        mViewModel.getResultAllScheduleIIIMonth().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MonthlyScheduleResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getAvailableScheduleList() != null) {
                            ScheduleIIIMonthFragment fragment = (ScheduleIIIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(2);
                            fragment.updateUi(infoObj.getAvailableScheduleList());
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

    private void resetEnableView(Boolean isView) { }

    static class MonthPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 3;
        private SparseArray<Fragment> registeredFragments = new SparseArray<>();
        public MonthPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ScheduleIMonthFragment.newInstance();
                case 1:
                    return ScheduleIIMonthFragment.newInstance();
                case 2:
                    return ScheduleIIIMonthFragment.newInstance();
                default:
                    return null;
            }
        }

        @NotNull
        @Override
        public Object instantiateItem(@NotNull ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }


        @Override
        public void destroyItem(@NotNull ViewGroup container, int position,@NotNull Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    public void fetchMonthlySchedules(int monthIndex){
        mViewModel.fetchMonthlySchedules(mHeaderMap, monthIndex);
    }

}
