package com.telemed.doctor.schedule.view;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;
import com.telemed.doctor.schedule.viewmodel.DeleteAvailabilityViewModel;
import com.telemed.doctor.schedule.viewmodel.ScheduleSychronizeViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DeleteAvailabilityFragment extends Fragment {
    private final String TAG = DeleteAvailabilityFragment.class.getSimpleName();
    private Button btnSynchronizeSchedule;
    private HashMap<String, String> mHeaderMap;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private ImageButton ibtnClose,ibtnBack,ibtnPrevious, ibtNext;
    private TextView tvMonthName;
    private ViewPager vpPager;
    private MonthPagerAdapter mMonthPagerAdapter;
    private HomeFragmentSelectedListener mFragmentListener;
    private String mAccessToken;
    private DeleteAvailabilityViewModel mViewModel;

    public static DeleteAvailabilityFragment newInstance() {
        return new DeleteAvailabilityFragment();
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

        mMonthPagerAdapter = new MonthPagerAdapter(getChildFragmentManager());

        mViewModel = ViewModelProviders.of(this).get(DeleteAvailabilityViewModel.class);
        mViewModel.fetchMonthlySchedules(mHeaderMap, 0); // 0,1,2
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_delete_availability, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initObserver();
    }

    private void initView(View v) {
        ibtnClose = v.findViewById(R.id.ibtn_close);

        ibtnClose=v.findViewById(R.id.ibtn_close);


        ibtnBack=v.findViewById(R.id.ibtn_back);


        btnSynchronizeSchedule = v.findViewById(R.id.btn_synchronize_schedule);

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        progressBar.setVisibility(View.INVISIBLE);

        //  btnSynchronizeSchedule.setVisibility(View.INVISIBLE);

        vpPager = (ViewPager) v.findViewById(R.id.view_pager);
        vpPager.setAdapter(mMonthPagerAdapter);
        vpPager.setOffscreenPageLimit(2);

        ibtnPrevious = v.findViewById(R.id.ibtn_previous);
        tvMonthName = v.findViewById(R.id.tv_month_name);
        ibtNext = v.findViewById(R.id.ibtn_next);

        TextView tvHeader = v.findViewById(R.id.tv_header);
        //  tvHeader.setText("My Availability");

    }

    private void initListener() {
        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment",0);
        });

        ibtnBack.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
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
                        ScheduleSychronizeIFragment fragmentI= (ScheduleSychronizeIFragment) mMonthPagerAdapter.getRegisteredFragment(position);
                        setMonthName(fragmentI.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap,0);
                        break;

                    case 1:
                        ScheduleSychronizeIIFragment fragmentII= (ScheduleSychronizeIIFragment) mMonthPagerAdapter.getRegisteredFragment(position);
                        setMonthName(fragmentII.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap,1);
                        break;

                    case 2:
                        ScheduleSychronizeIIIFragment fragmentIII= (ScheduleSychronizeIIIFragment) mMonthPagerAdapter.getRegisteredFragment(position);
                        setMonthName(fragmentIII.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap,2);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });


    }

    public void fetchMonthlySchedules(int monthIndex) {
        mViewModel.fetchMonthlySchedules(mHeaderMap, monthIndex); // 0,1,2
    }


    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getResultAllScheduleIMonth().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MonthlyScheduleResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getAvailableScheduleList() != null) {
                            ScheduleSychronizeIFragment fragment = (ScheduleSychronizeIFragment) mMonthPagerAdapter.getRegisteredFragment(0);
                            fragment.updateUi(infoObj.getAvailableScheduleList());
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                        ScheduleSychronizeIFragment fragment = (ScheduleSychronizeIFragment) mMonthPagerAdapter.getRegisteredFragment(0);
                        fragment.refreshUi();
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
                            ScheduleSychronizeIIFragment fragment = (ScheduleSychronizeIIFragment) mMonthPagerAdapter.getRegisteredFragment(1);
                            fragment.updateUi(infoObj.getAvailableScheduleList());
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                        ScheduleSychronizeIIFragment fragment = (ScheduleSychronizeIIFragment) mMonthPagerAdapter.getRegisteredFragment(1);
                        fragment.refreshUi();
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
                            ScheduleSychronizeIIIFragment fragment = (ScheduleSychronizeIIIFragment) mMonthPagerAdapter.getRegisteredFragment(2);
                            fragment.updateUi(infoObj.getAvailableScheduleList());
                        }
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                        ScheduleSychronizeIIIFragment fragment = (ScheduleSychronizeIIIFragment) mMonthPagerAdapter.getRegisteredFragment(2);
                        fragment.refreshUi();
                    }
                    break;
            }
        });

    }


    static class MonthPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 3;
        // Sparse array to keep track of registered fragments in memory
        private SparseArray<Fragment> registeredFragments = new SparseArray<>();


        public MonthPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return ScheduleSychronizeIFragment.newInstance();
                case 1: return ScheduleSychronizeIIFragment.newInstance();
                case 2: return ScheduleSychronizeIIIFragment.newInstance();
                default: return null;
            }
        }

        @NotNull
        @Override
        public Object instantiateItem(@NotNull ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        // Unregister when the item is inactive
        @Override
        public void destroyItem(@NotNull ViewGroup container, int position,@NotNull Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        // Returns the fragment for the position (if instantiated)
        Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

    }

    void setMonthName(String monthName) {
        tvMonthName.setText(monthName);

    }


    void showAlertView(String msg, String colorName){
        tvAlertView.setBackgroundColor(colorName.equals("GREEN")?getResources().getColor(R.color.colorGreen)
                :getResources().getColor(R.color.colorRed));
        tvAlertView.showTopAlert(msg);


    }


    void refreshPreviousUi(){
        if(mFragmentListener!=null) mFragmentListener.refreshFragment("ScheduleFragment");
    }


    static class BlueColorDecorator implements DayViewDecorator {

        private String date;
        private Resources resource;

        public BlueColorDecorator(String date,Resources resource) {
            this.date = date;
            this.resource = resource;
        }


        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            //      Log.e("BlueColorDecorator", "" + date);
            return this.date.equals(date);

        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setBackgroundDrawable(resource.getDrawable(R.drawable.custom_circle_iii));
        }
    }

    //  Disable dates decorator
    static class DisableDateDecorator implements DayViewDecorator {

        private String date;

        private Resources resource;

        public DisableDateDecorator(String date,Resources resource) {
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

    static class RedColorDecorator implements DayViewDecorator {
        private String date;

        private Resources resource;

        public RedColorDecorator(String date,Resources resource) {
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

    static class WhiteColorDecorator implements DayViewDecorator {


        private String date;

        private Resources resource;

        public WhiteColorDecorator(String date,Resources resource) {
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

    static String getMonthName(int i) {
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        return monthName[i];
    }

    static String formatDateZ(Date oldDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String strDate = dateFormat.format(oldDate);
        return strDate;
    }

    static Date formatDateII(String oldDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    static String formatDateIII(String inputText) {
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        DateFormat inputFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());


        Date date = null;
        try {
            date = inputFormat.parse(inputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);
        return outputText;
    }

    static String formatDate(String oldDate) {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        Date freshDate = null;
        try {
            freshDate = sdFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdFormat.format(freshDate);
    }

}
