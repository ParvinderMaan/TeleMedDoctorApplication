package com.telemed.doctor.schedule.view;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.SychronizeScheduleDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;
import com.telemed.doctor.schedule.viewmodel.ScheduleViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class ScheduleFragment extends Fragment {
    private final String TAG = ScheduleFragment.class.getSimpleName();
    private Button btnSynchronizeSchedule;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose, ibtnBack;
    private ScheduleViewModel mViewModel;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private ProgressBar progressBar;
    private ImageButton ibtnPrevious, ibtNext;
    private TextView tvMonthName;
    private CustomAlertTextView tvAlertView;
    private MonthPagerAdapter mMonthPagerAdapter;
    private ViewPager vpPager;
    private SharedPrefHelper mHelper;
    private int monthOne,monthTwo,monthThree;
    private int yearOne,yearTwo,yearThree;

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);
        mMonthPagerAdapter = new MonthPagerAdapter(getChildFragmentManager());
        LocalDate calendar = LocalDate.now();
        monthOne=calendar.getMonthValue();
        yearOne=calendar.getYear();
        final LocalDate twoCal = LocalDate.of(calendar.getYear(), calendar.getMonth().getValue()+1, 1);
        monthTwo= twoCal.getMonthValue();
        yearTwo= twoCal.getYear();
        final LocalDate threeCal = LocalDate.of(calendar.getYear(), calendar.getMonth().getValue()+2, 1);
        monthThree= threeCal.getMonthValue();
        yearThree= threeCal.getYear();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initObserver();

        setMonthName(ScheduleFragment.getMonthName(monthOne-1));
        switch (vpPager.getCurrentItem()) {
            case 0:
                mViewModel.fetchFirstMonthSchedule(mHeaderMap,monthOne,yearOne);
                break;
            case 1:
                mViewModel.fetchSecondMonthSchedule(mHeaderMap, monthTwo, yearTwo);
                break;
            case 2:
                mViewModel.fetchThirdMonthSchedule(mHeaderMap, monthThree, yearThree);
                break;
        }
    }

    private void initView(View v) {
        ibtnClose = v.findViewById(R.id.ibtn_close);
        ibtnBack = v.findViewById(R.id.ibtn_back);
        ibtnBack.setVisibility(View.INVISIBLE);
        btnSynchronizeSchedule = v.findViewById(R.id.btn_synchronize_schedule);

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        progressBar.setVisibility(View.INVISIBLE);


        vpPager = v.findViewById(R.id.view_pager);
        vpPager.setAdapter(mMonthPagerAdapter);

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
            if (mFragmentListener != null) {
                mFragmentListener.showFragment("ScheduleSychronizeFragment", null);
            }
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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setMonthName(ScheduleFragment.getMonthName(monthOne-1));
                        mViewModel.fetchFirstMonthSchedule(mHeaderMap, monthOne, yearOne);
                        break;

                    case 1:
                        setMonthName(ScheduleFragment.getMonthName(monthTwo-1));
                        mViewModel.fetchSecondMonthSchedule(mHeaderMap,monthTwo, yearTwo);
                        break;

                    case 2:
                        setMonthName(ScheduleFragment.getMonthName(monthThree-1));
                        mViewModel.fetchThirdMonthSchedule(mHeaderMap, monthThree, yearThree);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);

        mViewModel.getResultAllScheduleIMonth().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MonthlyScheduleResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getAvailableScheduleList() != null ) {
                            ScheduleIMonthFragment fragment = (ScheduleIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(0);
                            if(fragment!=null && fragment.isVisible())
                                fragment.updateUi(infoObj.getAvailableScheduleList());
                        }
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                        ScheduleIMonthFragment fragment = (ScheduleIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(0);
                        if(fragment!=null && fragment.isVisible())
                        fragment.hideRefreshing();
                    }
                    if (response.getErrorMsg() != null && response.getErrorMsg().equals("Unauthorised User")) {
                        mHelper.clear(); // clearing sharedPref
                        mFragmentListener.startActivity("RouterActivity", null, null);
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
                            if(fragment!=null && fragment.isVisible())
                            fragment.updateUi(infoObj.getAvailableScheduleList());
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                        ScheduleIIMonthFragment fragment = (ScheduleIIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(1);
                        if(fragment!=null && fragment.isVisible())
                        fragment.hideRefreshing();
                    }
                    if (response.getErrorMsg() != null && response.getErrorMsg().equals("Unauthorised User")) {
                        mHelper.clear(); // clearing sharedPref
                        mFragmentListener.startActivity("RouterActivity", null, null);
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
                            if(fragment!=null && fragment.isVisible())
                            fragment.updateUi(infoObj.getAvailableScheduleList());
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                        ScheduleIIIMonthFragment fragment = (ScheduleIIIMonthFragment) mMonthPagerAdapter.getRegisteredFragment(2);
                        if(fragment!=null && fragment.isVisible())
                        fragment.hideRefreshing();
                    }
                    if (response.getErrorMsg() != null && response.getErrorMsg().equals("Unauthorised User")) {
                        mHelper.clear(); // clearing sharedPref
                        mFragmentListener.startActivity("RouterActivity", null, null);
                    }
                    break;
            }
        });


    }

    private void resetEnableView(Boolean isView) {
    }


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
        public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }

    public void fetchMonthlySchedules(int monthIndex) {
        switch (monthIndex){
            case 0:
                mViewModel.fetchFirstMonthSchedule(mHeaderMap, monthOne, yearOne);
                break;
            case 1:
                mViewModel.fetchSecondMonthSchedule(mHeaderMap,monthTwo, yearTwo);
                break;
            case 2:
                mViewModel.fetchThirdMonthSchedule(mHeaderMap, monthThree, yearThree);
                break;
        }
    }


    public static class BlueColorDecorator implements DayViewDecorator {
        private String date;
        private Resources resource;

        public BlueColorDecorator(String date, Resources resource) {
            this.date = date;
            this.resource = resource;
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            //            Log.e("BlueColorDecorator", "" + date);
            return this.date.equals(date);
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setDaysDisabled(true);
            view.setBackgroundDrawable(resource.getDrawable(R.drawable.custom_circle_iii));
        }
    }

    //  Disable dates decorator
    public static class DisableDateDecorator implements DayViewDecorator {

        private String date;


        private Resources resource;

        public DisableDateDecorator(String date, Resources resource) {
            this.date = date;
            this.resource = resource;
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            //            Log.e("DisableDateDecorator", "" + date);
            return this.date.equals(date);
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setDaysDisabled(true);
            view.setBackgroundDrawable(resource.getDrawable(R.drawable.custom_circle_iii));

        }

    }

    public static class RedColorDecorator implements DayViewDecorator {
        private String date;

        private Resources resource;

        public RedColorDecorator(String date, Resources resource) {
            this.date = date;
            this.resource = resource;
        }


        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            //            Log.e("RedColorDecorator", "" + date);
            return this.date.equals(date);
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setBackgroundDrawable(resource.getDrawable(R.drawable.custom_circle_iv));

        }
    }

    public static class WhiteColorDecorator implements DayViewDecorator {

        private String date;
        private Resources resource;

        public WhiteColorDecorator(String date, Resources resource) {
            this.date = date;
            this.resource = resource;
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            //      Log.e("WhiteColorDecorator", "" + date);
            return this.date.equals(date);

        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(resource.getColor(R.color.colorBlue)));
            view.setBackgroundDrawable(resource.getDrawable(R.drawable.custom_circle_vi));

        }
    }

    public static String formatDateZ(Date oldDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        return dateFormat.format(oldDate);
    }

    public static String formatDateII(String oldDate) {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date freshDate = null;
        try {
            freshDate = sdFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdFormat.format(freshDate);
    }

    public static Date formatDateIY(String oldDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDate(String oldDate) {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        Date freshDate = null;
        try {
            freshDate = sdFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdFormat.format(freshDate);
    }

    public static String getMonthName(int i) {
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        return monthName[i];
    }
}
