package com.telemed.doctor.schedule.view;


import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.ColorRes;
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
import android.widget.TimePicker;
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
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.DateIterator;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.model.DayScheduleRequest;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;
import com.telemed.doctor.schedule.viewmodel.ScheduleSychronizeViewModel;
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


public class ScheduleSychronizeFragment extends Fragment {
    private final String TAG = ScheduleSychronizeFragment.class.getSimpleName();
    private List<DayViewDecorator> listOfDecoration = new ArrayList<>();
    private Button btnSynchronizeSchedule;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;
    private MaterialCalendarView calViewSchedule;
    private LinearLayout llSyncOptions;
    private RelativeLayout rlRoot;
    private TextView tvSyncDate, tvSyncWeekday, tvCancelOptions;
    private ScheduleSychronizeViewModel mViewModel;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;

    private List<DayViewDecorator> lstOfDisableDays = new ArrayList<>();
    ; // not used yet
    private List<DayViewDecorator> lstOfEnableDays = new ArrayList<>();
    ; // not used yet    private HashMap<String, Boolean> mHashMap;
    private String startTimeSelected, endTimeSelected, dateSelected;
    private List<DayViewDecorator> listOfDayDecoration = new ArrayList<>();
    private Calendar calendarObj;
    private HashMap<String, DayViewDecorator> mHashMapDD = new HashMap<>();
    private String mDayDecoratorDateSelected;
    private MonthPagerAdapter mMonthPagerAdapter;
    private ViewPager vpPager;
    private ImageButton ibtnPrevious, ibtNext;
    private TextView tvMonthName;

    public static ScheduleSychronizeFragment newInstance() {
        return new ScheduleSychronizeFragment();
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
        mViewModel = ViewModelProviders.of(this).get(ScheduleSychronizeViewModel.class);
        initView(v);
        initListener();
     //   initCalendarView(v);

        initObserver();
        mViewModel.fetchMonthlySchedules(mHeaderMap, 0); // 0,1,2
        /*
        lstOfAvailableDays.add("16-2-2020");
        lstOfAvailableDays.add("17-2-2020");
        lstOfAvailableDays.add("18-2-2020");
        lstOfAvailableDays.add("20-2-2020");
        lstOfAvailableDays.add("25-2-2020");
         */


//        listOfDecoration.add(0,new BlueColorDecorator("2020-2-2"));
//        listOfDecoration.add(1,new BlueColorDecorator("2020-2-3"));
//        listOfDecoration.add(2,new BlueColorDecorator("2020-2-4"));
//        listOfDecoration.add(3,new BlueColorDecorator("2020-2-5"));
//        listOfDecoration.add(4,new BlueColorDecorator("2020-2-7"));
//        listOfDecoration.add(5,new WhiteColorDecorator("2020-2-12"));
//        listOfDecoration.add(6,new WhiteColorDecorator("2020-2-22"));
//        listOfDecoration.add(7,new WhiteColorDecorator("2020-2-21"));
//        listOfDecoration.add(8,new WhiteColorDecorator("2020-2-23"));
//        listOfDecoration.add(9,new WhiteColorDecorator("2020-2-24"));
//        calViewSchedule.addDecorators(listOfDecoration);

        //   handler.sendEmptyMessageDelayed(0,5000);


    }


    private void initCalendarView(View v) {
        calViewSchedule = v.findViewById(R.id.calendar_view_schedule);
        LocalDate calendar = LocalDate.now();
//      calViewSchedule.setSelectedDate(calendar);

        final LocalDate minDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth(), 1);
        final LocalDate maxDateOfCalendar = minDateOfCalendar.plusMonths(1).minusDays(1);//minDateOfCalendar.plusMonths(1).minusDays(1);
        // replace with 3

        //  CalendarDay mAbc = CalendarDay.from(maxDateOfCalendar);


        // Add dates on calendars

        Calendar minDateSelect = Calendar.getInstance();
        minDateSelect.set(Calendar.DAY_OF_MONTH, 1);

        Calendar maxDateSelect = Calendar.getInstance();
        maxDateSelect.set(maxDateOfCalendar.getYear(), maxDateOfCalendar.getMonthValue(), maxDateOfCalendar.getDayOfMonth());

        calendarObj = Calendar.getInstance();

        // 2020-2-20
        String todaysDate = calendarObj.get(Calendar.YEAR) + "-" + (calendarObj.get(Calendar.MONTH) + 1) + "-" + calendarObj.get(Calendar.DAY_OF_MONTH);
        //  Toast.makeText(requireActivity(), "" + todays Date, Toast.LENGTH_LONG).show();


        Iterator<Date> i = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
        while (i.hasNext()) {
            Date date = i.next();
            Log.e(TAG, "all dates: " + date);
            String dateee = null;
            dateee = formatDate(date);
            if (date.before(calendarObj.getTime())) {
//                lstOfDisableDays.add(new DisableDateDecorator(dateee)); old
                mHashMapDD.put(dateee, new DisableDateDecorator(dateee));
                Log.e(TAG, "all dates: before" + dateee);


            } else {
                //  lstOfEnableDays.add(new BlueColorDecorator(dateee)); old
                mHashMapDD.put(dateee, new BlueColorDecorator(dateee));
                Log.e(TAG, "all dates: after" + dateee);


            }
        }


//            if (loopdate.before(todaysDate)) {
//                lstOfDisableDays.add(new DisableDateDecorator(date));
//                Log.e(TAG,"all dates before:"+date);
//            } else {
//                lstOfEnableDays.add(new BlueColorDecorator(date));
//                Log.e(TAG,"all dates after:"+date);
//            }


        // for now....!!!
//        calViewSchedule.addDecorators(new BlueColorDecorator(),new WhiteColorDecorator(),new RedColorDecorator());

//          calViewSchedule.addDecorators(new BlueColorDecorator());


        // Commit calendar


        calViewSchedule.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay day, boolean selected) {
                dateSelected = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
//                Toast.makeText(requireActivity(), "" + selectedDate, Toast.LENGTH_SHORT).show();
                if (mHashMapDD.containsKey(dateSelected)) {
                    mDayDecoratorDateSelected=dateSelected;
                    if(mHashMapDD.get(dateSelected) instanceof RedColorDecorator){
                        Toast.makeText(requireActivity(), "" + "RedColorDecorator", Toast.LENGTH_SHORT).show();
                        /// delete.......



                    }else if(mHashMapDD.get(dateSelected) instanceof WhiteColorDecorator){
                        Toast.makeText(requireActivity(), "" + "WhiteColorDecorator", Toast.LENGTH_SHORT).show();
                        /// delete.......
                        DayScheduleRequest in=new DayScheduleRequest();
                        in.setId(0);
                        in.setAvailableDate(formatDateIII(dateSelected));
                        in.setFromTime("00:00");
                        in.setToTime("00:00");
                        in.setIsAvailable(false);

                        mViewModel.deleteWeekSchedule(mHeaderMap,in);// id

                    }else if(mHashMapDD.get(dateSelected) instanceof BlueColorDecorator){
                        Toast.makeText(requireActivity(), "" + "BlueColorDecorator", Toast.LENGTH_SHORT).show();
                        /// create.......
                        getFromTime();


                    }


                }


//                try {
//                    dateSelected=formatDate(selectedDate);
//
//                    if(mHashMap.containsKey(dateSelected)){           // ---> white  -->  create
//                        getFromTime();
//
//                    }else {                                             // ---> blue  -->   delete
//
//                        DayScheduleRequest in=new DayScheduleRequest();
//                        in.setId(0);
//                        in.setAvailableDate(selectedDate);
//                        in.setFromTime("00:00:00");
//                        in.setToTime("00:00:00");
//                        in.setIsAvailable(false);
//                        mViewModel.deleteWeekSchedule(mHeaderMap,in); // it's a delete api ....
//                    }
//
//                 //   mViewModel.deleteWeekSchedule(mHeaderMap,selectedDate);// id
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

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

//              mViewModel.fetchMonthlySchedules(mHeaderMap,++pageCount);


            }
        });

        calViewSchedule.state().edit()
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMinimumDate(minDateOfCalendar)
                .setShowWeekDays(true)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMaximumDate(maxDateOfCalendar)
                .commit();
    }

    private Date getDateFromString(String date) {

        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-M-d", Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
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

        btnSynchronizeSchedule.setVisibility(View.INVISIBLE);


        vpPager = (ViewPager) v.findViewById(R.id.view_pager);
        mMonthPagerAdapter = new MonthPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(mMonthPagerAdapter);
        vpPager.setOffscreenPageLimit(2);

        ibtnPrevious = v.findViewById(R.id.ibtn_previous);
        tvMonthName = v.findViewById(R.id.tv_month_name);
        ibtNext = v.findViewById(R.id.ibtn_next);


        TextView tvHeader = v.findViewById(R.id.tv_header);
     //   tvHeader.setText("Sychronize Schedule");

    }

    private void initListener() {
//        rlRoot.setOnClickListener(v -> {
//
//            if (llSyncOptions.getVisibility() == View.VISIBLE) {
//                llSyncOptions.setVisibility(View.INVISIBLE);
//            }
//
//        });

        ibtnClose.setOnClickListener(v -> {
            if (mFragmentListener != null) {
                mFragmentListener.popTopMostFragment();
            }

        });
//        btnSynchronizeSchedule.setOnClickListener(v -> {
//
////            if (llSyncOptions.getVisibility() == View.VISIBLE) {
////                llSyncOptions.setVisibility(View.INVISIBLE);
////            } else {
////                llSyncOptions.setVisibility(View.VISIBLE);
////            }
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
                        ScheduleSychronizeIFragment fragmentI= (ScheduleSychronizeIFragment) mMonthPagerAdapter.getRegisteredFragment(position);
//                     tvMonthName.setText(fragmentI.getMonthName());
                        setMonthName(fragmentI.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap,0);
                        break;

                    case 1:
                        ScheduleSychronizeIIFragment fragmentII= (ScheduleSychronizeIIFragment) mMonthPagerAdapter.getRegisteredFragment(position);
//                     tvMonthName.setText(fragmentII.getMonthName());
                        setMonthName(fragmentII.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap,1);
                        break;

                    case 2:
                        ScheduleSychronizeIIIFragment fragmentIII= (ScheduleSychronizeIIIFragment) mMonthPagerAdapter.getRegisteredFragment(position);
//                     tvMonthName.setText(fragmentIII.getMonthName());
                        setMonthName(fragmentIII.getMonthName());
                        mViewModel.fetchMonthlySchedules(mHeaderMap,2);
                        break;



                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }




    //  Disable dates decorator
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
                        if (infoObj.getAvailableScheduleList() != null) {
                            ScheduleSychronizeIFragment fragment = (ScheduleSychronizeIFragment) mMonthPagerAdapter.getRegisteredFragment(0);
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
                            ScheduleSychronizeIIFragment fragment = (ScheduleSychronizeIIFragment) mMonthPagerAdapter.getRegisteredFragment(1);
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
                            ScheduleSychronizeIIIFragment fragment = (ScheduleSychronizeIIIFragment) mMonthPagerAdapter.getRegisteredFragment(2);
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


        mViewModel.getResultantDayScheduleCreation().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        tvAlertView.showTopAlert(response.getData().getMessage());
                        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        WhiteColorDecorator decorator=new WhiteColorDecorator(mDayDecoratorDateSelected);
                        mHashMapDD.put(mDayDecoratorDateSelected,decorator);
                        calViewSchedule.addDecorator(decorator);
//                        calViewSchedule.invalidateDecorators();

                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());

                    }
                    break;
            }
        });


        mViewModel.getResultantDayScheduleDeletion().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        tvAlertView.showTopAlert(response.getData().getMessage());
                        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        BlueColorDecorator decorator=new BlueColorDecorator(mDayDecoratorDateSelected);
                        calViewSchedule.removeDecorator(mHashMapDD.get(mDayDecoratorDateSelected));
                        calViewSchedule.addDecorator(decorator);
                        mHashMapDD.put(mDayDecoratorDateSelected,decorator);

                       //  calViewSchedule.addDecorator(decorator);
                      //   calViewSchedule.removeDecorator(listOfDecoration.get(3));
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
                        List<AllMonthSchedule> lstOfSchedulesTemp = new ArrayList<>();
                        for (int i = 0; i < lstOfSchedules.size(); i++) {

                            Date date = formatDateII(lstOfSchedules.get(i).getDate());
                            if (date.before(calendarObj.getTime())) {
                                // remove ....
                            } else {
                                // add
                                lstOfSchedulesTemp.add(lstOfSchedules.get(i));
                            }


                        }

                        listOfDayDecoration.addAll(lstOfDisableDays);
                        listOfDayDecoration.addAll(lstOfEnableDays);
                        for (AllMonthSchedule item : lstOfSchedulesTemp) {
                            String freshDate = formatDate(item.getDate());

                            if (item.getAnyPendingAppointment()) {
//                                listOfDayDecoration.add(new RedColorDecorator(freshDate)); old
                                mHashMapDD.put(freshDate, new RedColorDecorator(freshDate));
                            } else {
//                                listOfDayDecoration.add(new WhiteColorDecorator(freshDate)); old
                                mHashMapDD.put(freshDate, new WhiteColorDecorator(freshDate));
                            }
                        }

//                       calViewSchedule.addDecorators(listOfDayDecoration); old
                        calViewSchedule.addDecorators(new ArrayList<DayViewDecorator>(mHashMapDD.values()));


//                      calViewSchedule.removeDecorators();  //,new DisableDateDecorator(lstOfDisableDays)
//                      calViewSchedule.addDecorators(new BlueColorDecorator() ,new WhiteColorDecorator(mHashMap),new RedColorDecorator(mHashMap)); //,,new RedColorDecorator(map)
//                        calViewSchedule.addDecorators(listOfDecoration);
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
//            view.setDaysDisabled(true);
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

    public String formatDate(Date oldDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String strDate = dateFormat.format(oldDate);
        return strDate;
    }

    public Date formatDateII(String oldDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void getFromTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), R.style.PickerStyle, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String fromTime = selectedHour + ":" + selectedMinute;
                Log.e("From: ", fromTime);

                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(fromTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "HH:mm",Locale.getDefault());
                String date = simpleDateFormat.format(date1);
                startTimeSelected = date;
                getToTime();


            }
        }, hour, minute, true);//Yes 24 hour time
//      mTimePicker.setTitle("From");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        mTimePicker.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        editText.setText("Starting Time :");
        mTimePicker.show();
    }

    private void getToTime() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(requireActivity(), R.style.PickerStyle, (timePicker, selectedHour, selectedMinute) -> {
            String to = selectedHour + ":" + selectedMinute;
            Log.e("To: ", to);
            Date date1 = null;
            try {
                date1 = new SimpleDateFormat("HH:mm", Locale.getDefault()).parse(to);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "HH:mm",Locale.getDefault());
            String date = simpleDateFormat.format(date1);

            //call api....
            endTimeSelected = date;
            DayScheduleRequest in = new DayScheduleRequest();
            in.setId(0);
            in.setAvailableDate(formatDateIII(dateSelected));
            in.setFromTime(startTimeSelected);
            in.setToTime(endTimeSelected);
            in.setIsAvailable(true);

            Log.e(TAG,in.toString());
            mViewModel.createDaySchedule(mHeaderMap, in);

            // to stop ...
//            Calendar datetime = Calendar.getInstance();
//            Calendar c = Calendar.getInstance();
//            datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
//            datetime.set(Calendar.MINUTE, selectedMinute);
//            if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
//                //it's after current
//
//
//            } else {
//                //it's before current'
//                Toast.makeText(requireActivity(), "Invalid Time", Toast.LENGTH_LONG).show();
//            }


        }, hour, minute, true); // Yes 24 hour time
        //      mTimePicker.setTitle("To");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        mTimePicker.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        editText.setText("Ending Time :");
        mTimePicker.show();

}

    private String formatDateIII(String inputText) {
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

//
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            Toast.makeText(requireActivity(), "hiii", Toast.LENGTH_SHORT).show();
//
////            DayViewDecorator xxx = listOfDecoration.get(3);
////            listOfDecoration.set(3,xxx("2020-2-5"));
//            calViewSchedule.removeDecorator(listOfDecoration.get(3));
//            calViewSchedule.addDecorator(new WhiteColorDecorator("2020-2-13"));
//            calViewSchedule.invalidateDecorators();
//
//        }
//    };

//        System.out.println("The date 1 is: " + sdFormat.format(d1));
//        System.out.println("The date 2 is: " + sdFormat.format(d2));


    //  Disable dates decorator
    private class DefaultDateDecorator implements DayViewDecorator {

        private String date;

        public DefaultDateDecorator(String date) {
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
                    return ScheduleSychronizeIFragment.newInstance();
                case 1: // Fragment # 1 - This will show FirstFragment different title
                    return ScheduleSychronizeIIFragment.newInstance();
                case 2: // Fragment # 2 - This will show SecondFragment
                    return ScheduleSychronizeIIIFragment.newInstance();
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
        public  Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

    }

    public void setMonthName(String monthName) {
        tvMonthName.setText(monthName);

    }


    @SuppressLint("ResourceAsColor")
    public void showAlertView(String msg, @ColorRes int colorId){
        tvAlertView.showTopAlert(msg);
        tvAlertView.setBackgroundColor(colorId);


    }
}
