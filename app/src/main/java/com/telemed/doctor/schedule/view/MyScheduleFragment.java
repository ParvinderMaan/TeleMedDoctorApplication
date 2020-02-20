package com.telemed.doctor.schedule.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;
import com.telemed.doctor.schedule.model.ScheduleModel;
import com.telemed.doctor.schedule.viewmodel.MyScheduleViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

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
import java.util.List;
import java.util.Locale;


public class MyScheduleFragment extends Fragment {
    private final String TAG = MyScheduleFragment.class.getSimpleName();

    private Button btnSynchronizeSchedule;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;
    private MaterialCalendarView calViewSchedule;
    private HashMap<String, Boolean> mClientDateMap;
    private ScheduleModel mServerDates;
    private LinearLayout llSyncOptions;
    private RelativeLayout rlRoot;
    private TextView tvSyncDate, tvSyncWeekday, tvCancelOptions;
    private MyScheduleViewModel mViewModel;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private List<DayViewDecorator> lstOfDisableDays=new ArrayList<>();; // not used yet
    private List<DayViewDecorator> lstOfEnableDays=new ArrayList<>();; // not used yet

    private int pageCount=0;
    private List<DayViewDecorator> listOfDayDecoration=new ArrayList<>();


    public static MyScheduleFragment newInstance() {
        return new MyScheduleFragment();
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
        return localInflater.inflate(R.layout.fragment_my_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyScheduleViewModel.class);


        initView(v);
        initListener();
        initCalendarView(v);
        initObserver();
        mViewModel.fetchMonthlySchedules(mHeaderMap, pageCount);

    }


    private void initCalendarView(View v) {
        calViewSchedule = v.findViewById(R.id.calendar_view_schedule);
        LocalDate calendar = LocalDate.now();
//      calViewSchedule.setSelectedDate(calendar);

        final LocalDate minDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth(), 1);
        final LocalDate maxDateOfCalendar = minDateOfCalendar.plusMonths(1).minusDays(1);
                                                                       // replace with 3

        //   CalendarDay mAbc = CalendarDay.from(maxDateOfCalendar);


        // Add dates on calendars
        Calendar minDateSelect = Calendar.getInstance();
        minDateSelect.set(Calendar.DAY_OF_MONTH, 1);

        Calendar maxDateSelect = Calendar.getInstance();
        maxDateSelect.set(maxDateOfCalendar.getYear(), maxDateOfCalendar.getMonthValue(), maxDateOfCalendar.getDayOfMonth());

        Calendar calendarObj = Calendar.getInstance();

        String currDateInString = calendarObj.get(Calendar.YEAR)
                + "-" + (calendarObj.get(Calendar.MONTH) + 1)
                + "-" + calendarObj.get(Calendar.DAY_OF_MONTH);

//        Toast.makeText(requireActivity(),""+currDateInString,Toast.LENGTH_LONG).show();

        // Add current date in hash map and disable current date
        //  mClientDateMap.put(curr, true);

        // Add dates in hash map to disable unavailable dates
        for (Calendar loopdate = minDateSelect;
             minDateSelect.before(maxDateSelect);
             minDateSelect.add(Calendar.DATE, 1), loopdate = minDateSelect) {
            String date = loopdate.get(Calendar.YEAR) + "-" + (loopdate.get(Calendar.MONTH) + 1) + "-" + loopdate.get(Calendar.DAY_OF_MONTH);

            if (loopdate.before(currDateInString)) {
//              mClientDateMap.put(date,true);
//                lstOfDisableDays.add(new DisableDateDecorator(date)); //"2020-2-24"     /// first step....
                lstOfDisableDays.add(new DisableDateDecorator(date));
            }
            else {
                lstOfEnableDays.add(new BlueColorDecorator(date));
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
                date=formatDateII(date);

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

        calViewSchedule.setOnMonthChangedListener(new OnMonthChangedListener(){

            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay day) {
                String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
//              formatDate(date);
                Toast.makeText(requireActivity(), "" + date , Toast.LENGTH_SHORT).show();

//                mViewModel.fetchMonthlySchedules(mHeaderMap,++pageCount);


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

    private void initView(View v) {
        rlRoot = v.findViewById(R.id.rl_root);
        ibtnClose = v.findViewById(R.id.ibtn_close);
        btnSynchronizeSchedule = v.findViewById(R.id.btn_synchronize_schedule);

        llSyncOptions = v.findViewById(R.id.ll_sync_options);
        tvSyncDate = v.findViewById(R.id.tv_sync_date);
        tvSyncWeekday = v.findViewById(R.id.tv_sync_weekday);
        tvCancelOptions = v.findViewById(R.id.tv_cancel_options);

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);

        progressBar.setVisibility(View.INVISIBLE);
    }

    private void initListener() {
        rlRoot.setOnClickListener(v -> {

            if (llSyncOptions.getVisibility() == View.VISIBLE) {
                llSyncOptions.setVisibility(View.INVISIBLE);
            }

        });

        ibtnClose.setOnClickListener(v -> {
            if (mFragmentListener != null) {
                mFragmentListener.popTopMostFragment();
            }

        });
        btnSynchronizeSchedule.setOnClickListener(v -> {

            if (llSyncOptions.getVisibility() == View.VISIBLE) {
                llSyncOptions.setVisibility(View.INVISIBLE);
            } else {
                llSyncOptions.setVisibility(View.VISIBLE);
            }
        });


        tvSyncDate.setOnClickListener(v -> {
            if (mFragmentListener != null) {
                llSyncOptions.setVisibility(View.INVISIBLE);
                mFragmentListener.showFragment("ScheduleSychronizeFragment", null);
            }

        });
        tvSyncWeekday.setOnClickListener(v -> {
            if (mFragmentListener != null) {
                llSyncOptions.setVisibility(View.INVISIBLE);
                mFragmentListener.showFragment("WeekDaysScheduleFragment", null);
            }

        });
        tvCancelOptions.setOnClickListener(v -> {
            llSyncOptions.setVisibility(View.INVISIBLE);
        });

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


    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);

        mViewModel.getResultantAllSchedule().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        MonthlyScheduleResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getAvailableScheduleList() != null) {
                            mViewModel.setScheduleList(infoObj.getAvailableScheduleList());
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

//                        HashMap<String,Boolean> map=new HashMap<>();
//                        for(AllMonthSchedule item:lstOfSchedules){
//                            map.put(item.getDate(),item.getAnyPendingAppointment());
//                        }

//                      calViewSchedule.removeDecorators();  //,new DisableDateDecorator(lstOfDisableDays)
//                        calViewSchedule.addDecorators(new BlueColorDecorator() ,new WhiteColorDecorator(map),new RedColorDecorator(map)); //,,new RedColorDecorator(map)
                        listOfDayDecoration.addAll(lstOfDisableDays);
                        listOfDayDecoration.addAll(lstOfEnableDays);
                        for(AllMonthSchedule item:lstOfSchedules){
                              String freshDate = formatDate(item.getDate());

                              if(item.getAnyPendingAppointment()){
                                  listOfDayDecoration.add(new RedColorDecorator(freshDate));
                              }else {
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


    public String formatDate(String oldDate)  {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        Date freshDate = null;
        try {
            freshDate = sdFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdFormat.format(freshDate);
    }

    public String formatDateII(String oldDate)  {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date freshDate = null;
        try {
            freshDate = sdFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdFormat.format(freshDate);
    }

//        System.out.println("The date 1 is: " + sdFormat.format(d1));
//        System.out.println("The date 2 is: " + sdFormat.format(d2));
}
