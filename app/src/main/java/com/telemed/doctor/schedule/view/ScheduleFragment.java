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

import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.SychronizeScheduleDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.DateIterator;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;
import com.telemed.doctor.schedule.model.ScheduleModel;
import com.telemed.doctor.schedule.viewmodel.ScheduleViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class ScheduleFragment extends Fragment {
    private final String TAG = ScheduleFragment.class.getSimpleName();

    private Button btnSynchronizeSchedule;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;
    private MaterialCalendarView calViewSchedule;
    private HashMap<String, Boolean> mClientDateMap;
    private ScheduleModel mServerDates;
    //    private LinearLayout llSyncOptions;
    private RelativeLayout rlRoot;
    //    private TextView tvSyncDate, tvSyncWeekday, tvCancelOptions;
    private ScheduleViewModel mViewModel;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private ProgressBar progressBar;
    private ImageButton ibtnPrevious, ibtNext;
    private TextView tvMonthName;
    private CustomAlertTextView tvAlertView;
    private List<DayViewDecorator> lstOfDisableDays = new ArrayList<>();
    ; // not used yet
    private List<DayViewDecorator> lstOfEnableDays = new ArrayList<>();
    ; // not used yet

    private List<DayViewDecorator> listOfDayDecoration = new ArrayList<>();
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
        mClientDateMap = new HashMap<>();
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
        //  initCalendarView(v);
        initObserver();
//        ScheduleFirstMonthFragment fragmentI= (ScheduleFirstMonthFragment) mMonthPagerAdapter.getRegisteredFragment(0);
//        tvMonthName.setText(fragmentI.getMonthName());

        mViewModel.fetchMonthlySchedules(mHeaderMap, 0);




    }


    private void initCalendarView(View v) {
        calViewSchedule = v.findViewById(R.id.calendar_view_schedule);
        LocalDate calendar = LocalDate.now();
//      calViewSchedule.setSelectedDate(calendar);

        final LocalDate minDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth(), 1);
        final LocalDate maxDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth(), minDateOfCalendar.lengthOfMonth());
        // replace with 3 // minDateOfCalendar.plusMonths(3).minusDays(1);
        //   CalendarDay mAbc = CalendarDay.from(maxDateOfCalendar);


        // Add dates on calendars
        Calendar minDateSelect = Calendar.getInstance();
        minDateSelect.set(minDateSelect.get(Calendar.YEAR), minDateSelect.get(Calendar.MONTH), minDateSelect.getActualMinimum(Calendar.DATE));

        Calendar maxDateSelect = Calendar.getInstance();
        maxDateSelect.set(maxDateSelect.get(Calendar.YEAR), maxDateSelect.get(Calendar.MONTH), maxDateSelect.getActualMaximum(Calendar.DATE));


        Calendar calendarObj = Calendar.getInstance();

        String currDateInString = calendarObj.get(Calendar.YEAR)
                + "-" + (calendarObj.get(Calendar.MONTH) + 1)
                + "-" + calendarObj.get(Calendar.DAY_OF_MONTH);

//        Toast.makeText(requireActivity(),""+currDateInString,Toast.LENGTH_LONG).show();

        // Add current date in hash map and disable current date
        //  mClientDateMap.put(curr, true);
        Log.e(TAG, " dates: max" + maxDateSelect.getTimeInMillis());

        Iterator<Date> i = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
        while (i.hasNext()) {
            Date date = i.next();
            Log.e(TAG, "all dates: " + date);
            String dateee = null;
            dateee = formatDateZ(date);
            if (date.before(calendarObj.getTime())) {
//                lstOfDisableDays.add(new DisableDateDecorator(dateee)); old
                lstOfDisableDays.add(new DisableDateDecorator(dateee));
                Log.e(TAG, "all dates: before" + dateee);


            } else {
                //  lstOfEnableDays.add(new BlueColorDecorator(dateee)); old
                lstOfEnableDays.add(new BlueColorDecorator(dateee));
                Log.e(TAG, "all dates: after" + dateee);


            }
        }

        // for now....!!!
//        calViewSchedule.addDecorators(new BlueColorDecorator(),new WhiteColorDecorator(),new RedColorDecorator());
//        calViewSchedule.addDecorators(new BlueColorDecorator());


        // Commit calendar


        calViewSchedule.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay day, boolean selected) {

                String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
                date = formatDateII(date);

                if (mFragmentListener != null)
                    mFragmentListener.showFragment("DayWiseAvailabilityFragment", date);
            }
        });

//      calViewSchedule.setWeekDayLabels(new CharSequence[]{"S","M","T","W","T","F","S"});

        calViewSchedule.setWeekDayFormatter(new WeekDayFormatter() {
            @Override
            public CharSequence format(DayOfWeek dayOfWeek) {
                return dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault());
            }
        });

        calViewSchedule.setOnMonthChangedListener(new OnMonthChangedListener() {

            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay day) {
                String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
//              formatDate(date);
                Toast.makeText(requireActivity(), "" + date, Toast.LENGTH_SHORT).show();

                //  mViewModel.fetchMonthlySchedules(mHeaderMap,++pageCount);


            }
        });

        calViewSchedule.state().edit()
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMinimumDate(minDateOfCalendar)
                .setShowWeekDays(true)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMaximumDate(maxDateOfCalendar)
                .commit();

        calViewSchedule.setTopbarVisible(false);
        // Toast.makeText(requireActivity(), "" + maxDateOfCalendar.getMonth() + " " + maxDateOfCalendar.lengthOfMonth(), Toast.LENGTH_SHORT).show();

    }

    private void initView(View v) {
        rlRoot = v.findViewById(R.id.rl_root);
        ibtnClose = v.findViewById(R.id.ibtn_close);
        btnSynchronizeSchedule = v.findViewById(R.id.btn_synchronize_schedule);

//        llSyncOptions = v.findViewById(R.id.ll_sync_options);
//        tvSyncDate = v.findViewById(R.id.tv_sync_date);
//        tvSyncWeekday = v.findViewById(R.id.tv_sync_weekday);
//        tvCancelOptions = v.findViewById(R.id.tv_cancel_options);

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
        rlRoot.setOnClickListener(v -> {

//            if (llSyncOptions.getVisibility() == View.VISIBLE) {
//                llSyncOptions.setVisibility(View.INVISIBLE);
//            }


        });

        ibtnClose.setOnClickListener(v -> {
            if (mFragmentListener != null) {
                mFragmentListener.popTopMostFragment();
            }

        });
        btnSynchronizeSchedule.setOnClickListener(v -> {

//            if (llSyncOptions.getVisibility() == View.VISIBLE) {
//                llSyncOptions.setVisibility(View.INVISIBLE);
//            } else {
//                llSyncOptions.setVisibility(View.VISIBLE);
//            }

            mViewModel.setDialogVisiblility(true);
        });


//        tvSyncDate.setOnClickListener(v -> {
//            if (mFragmentListener != null) {
//                llSyncOptions.setVisibility(View.INVISIBLE);
//                mFragmentListener.showFragment("ScheduleSychronizeFragment", null);
//            }
//
//        });
//        tvSyncWeekday.setOnClickListener(v -> {
//            if (mFragmentListener != null) {
//                llSyncOptions.setVisibility(View.INVISIBLE);
//                mFragmentListener.showFragment("WeekDaysScheduleFragment", null);
//            }
//
//        });
//        tvCancelOptions.setOnClickListener(v -> {
//            llSyncOptions.setVisibility(View.INVISIBLE);
//        });


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
                        ScheduleFirstMonthFragment fragmentI = (ScheduleFirstMonthFragment) mMonthPagerAdapter.getRegisteredFragment(position);
//                     tvMonthName.setText(fragmentI.getMonthName());
                        setMonthName(fragmentI.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap, 0);
                        break;

                    case 1:
                        ScheduleSecondMonthFragment fragmentII = (ScheduleSecondMonthFragment) mMonthPagerAdapter.getRegisteredFragment(position);
//                     tvMonthName.setText(fragmentII.getMonthName());
                        setMonthName(fragmentII.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap, 1);
                        break;

                    case 2:
                        ScheduleThirdMonthFragment fragmentIII = (ScheduleThirdMonthFragment) mMonthPagerAdapter.getRegisteredFragment(position);
//                     tvMonthName.setText(fragmentIII.getMonthName());
                        setMonthName(fragmentIII.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap, 2);
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
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


    //  Disable dates decorator
    private class DisableDateDecorator implements DayViewDecorator {
        private String date;

        public DisableDateDecorator(String date) {
            this.date = date;
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            Log.e("DisableDateDecorator", "" + date);
            return this.date.equals(date);
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setDaysDisabled(true);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_circle_iii));

        }

    }


    private ScheduleModel fetchDataFromServer() {
        ScheduleModel scheduleModel = new ScheduleModel();
        List<String> lstOfBookedDays = new ArrayList<>();
        List<String> lstOfAvailableDays = new ArrayList<>();
        lstOfAvailableDays.add("16-2-2020");
        lstOfAvailableDays.add("17-2-2020");
        lstOfAvailableDays.add("18-2-2020");
        lstOfAvailableDays.add("20-2-2020");
        lstOfAvailableDays.add("25-2-2020");
        scheduleModel.setAvailableDays(lstOfAvailableDays);
        lstOfBookedDays.add("22-2-2020");
        lstOfBookedDays.add("23-2-2020");
        lstOfBookedDays.add("26-2-2020");
        scheduleModel.setBookedDays(lstOfBookedDays);
        return scheduleModel;
    }

    public String formatDateZ(Date oldDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String strDate = dateFormat.format(oldDate);
        return strDate;
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
//                          mViewModel.setScheduleList(infoObj.getAvailableScheduleList());
                            ScheduleFirstMonthFragment fragment = (ScheduleFirstMonthFragment) mMonthPagerAdapter.getRegisteredFragment(0);
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
//                            mViewModel.setScheduleList(infoObj.getAvailableScheduleList());
                            ScheduleSecondMonthFragment fragment = (ScheduleSecondMonthFragment) mMonthPagerAdapter.getRegisteredFragment(1);
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
//                            mViewModel.setScheduleList(infoObj.getAvailableScheduleList());
                            ScheduleThirdMonthFragment fragment = (ScheduleThirdMonthFragment) mMonthPagerAdapter.getRegisteredFragment(2);
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


        mViewModel.getAllSchedules()
                .observe(getViewLifecycleOwner(), lstOfSchedules -> {
                    if (!lstOfSchedules.isEmpty()) {


                        listOfDayDecoration.addAll(lstOfDisableDays);
                        listOfDayDecoration.addAll(lstOfEnableDays);
                        for (AllMonthSchedule item : lstOfSchedules) {
                            String freshDate = formatDate(item.getDate());

                            if (item.getAnyPendingAppointment()) {
                                listOfDayDecoration.add(new RedColorDecorator(freshDate));
                            } else {
                                listOfDayDecoration.add(new WhiteColorDecorator(freshDate));
                            }
                        }
                        calViewSchedule.addDecorators(listOfDayDecoration);

                    }

                });


    }

    private void resetEnableView(Boolean isView) {

    }


    private class WhiteColorDecorator implements DayViewDecorator {


        private String date;

        public WhiteColorDecorator(String date) {
            this.date = date;
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            Log.e("WhiteColorDecorator", "" + date);
            return this.date.equals(date);

        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlue)));
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_circle_vi));

        }
    }


    private class BlueColorDecorator implements DayViewDecorator {


        private String date;

        public BlueColorDecorator(String date) {
            this.date = date;
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            Log.e("BlueColorDecorator", "" + date);
            return this.date.equals(date);

//            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
//            Log.e("BlueColorDecorator",""+date);
//


        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setDaysDisabled(true);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_circle_iii));
        }
    }


    private class RedColorDecorator implements DayViewDecorator {
        private String date;

        public RedColorDecorator(String date) {
            this.date = date;
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            Log.e("RedColorDecorator", "" + date);
            return this.date.equals(date);
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_circle_iv));

        }
    }


    public String formatDate(String oldDate) {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        Date freshDate = null;
        try {
            freshDate = sdFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdFormat.format(freshDate);
    }

    public String formatDateII(String oldDate) {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date freshDate = null;
        try {
            freshDate = sdFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdFormat.format(freshDate);
    }

    public static class MonthPagerAdapter extends FragmentStatePagerAdapter {
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

        // Returns the fragment to display for that page
        @NotNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return ScheduleFirstMonthFragment.newInstance();
                case 1: // Fragment # 1 - This will show FirstFragment different title
                    return ScheduleSecondMonthFragment.newInstance();
                case 2: // Fragment # 2 - This will show SecondFragment
                    return ScheduleThirdMonthFragment.newInstance();
                default:
                    return null;
            }
        }


        // Register the fragment when the item is instantiated
        @NotNull
        @Override
        public Object instantiateItem(@NotNull ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        // Unregister when the item is inactive
        @NotNull
        @Override
        public void destroyItem(@NotNull ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        // Returns the fragment for the position (if instantiated)
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

    }

    public void fetchMonthlySchedules(int monthIndex){
        mViewModel.fetchMonthlySchedules(mHeaderMap, monthIndex);

    }


}
