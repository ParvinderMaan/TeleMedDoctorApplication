package com.telemed.doctor.schedule.view;

import android.content.Context;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.AlertDialogFragment;
import com.telemed.doctor.dialog.TimePickerDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.helper.DateIterator;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.model.DayScheduleRequest;
import com.telemed.doctor.schedule.viewmodel.ScheduleSychronizeIIIViewModel;

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

// month three..
public class ScheduleSychronizeIIIFragment extends Fragment {
    private final String TAG = ScheduleSychronizeIIIFragment.class.getSimpleName();
    private ScheduleSychronizeIIIViewModel mViewModel;
    private MaterialCalendarView calViewSchedule;
    private HashMap<String, DayViewDecorator> mHashMapDD = new HashMap<>();
    private String startTimeSelected, endTimeSelected, dateSelected;
    private String mDayDecoratorDateSelected;
    private Calendar calendarObj;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private AlertDialogFragment mDeleteDialogFragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ScheduleSychronizeIIIFragment newInstance() {
        return new ScheduleSychronizeIIIFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
        return inflater.inflate(R.layout.fragment_schedule_month, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScheduleSychronizeIIIViewModel.class);
         initCalendarView(v);
         initObserver();

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            ((ScheduleSychronizeFragment)requireParentFragment()).fetchMonthlySchedules(2);
        });

    }



    private String monthName;

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }


    public void updateUi(List<AllMonthSchedule> availableScheduleList) {
        mViewModel.setScheduleList(availableScheduleList);

    }



    private void initObserver() {
        mViewModel.getDeleteDialogVisibility()
                .observe(getViewLifecycleOwner(), isVisibile -> {
                    if(isVisibile){
                        mDeleteDialogFragment = AlertDialogFragment.newInstance();
                        mDeleteDialogFragment.show(getChildFragmentManager(), "TAG");
                        mDeleteDialogFragment.setOnAlertDialogListener(() -> {
                            DayScheduleRequest in=new DayScheduleRequest();
                            in.setId(0);
                            in.setAvailableDate(formatDateIII(dateSelected));
                            in.setFromTime("00:00");
                            in.setToTime("00:00");
                            in.setIsAvailable(false);
                            mViewModel.deleteWeekSchedule(mHeaderMap,in); // id
                        });
                    }else {
                        mDeleteDialogFragment.dismiss();
                        mDeleteDialogFragment.setOnAlertDialogListener(null);
                    }

                });


        mViewModel.getResultantDayScheduleCreation().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        ((ScheduleSychronizeFragment)requireParentFragment()).showAlertView(response.getData().getMessage(),"GREEN");
                        WhiteColorDecorator decorator=new WhiteColorDecorator(mDayDecoratorDateSelected);
                        mHashMapDD.put(mDayDecoratorDateSelected,decorator);
                        calViewSchedule.addDecorator(decorator);
                        ((ScheduleSychronizeFragment)requireParentFragment()).refreshPreviousUi();
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        ((ScheduleSychronizeFragment)requireParentFragment()).showAlertView(response.getErrorMsg(),"RED");
                    }
                    break;
            }
        });


        mViewModel.getResultantDayScheduleDeletion().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        ((ScheduleSychronizeFragment)requireParentFragment()).showAlertView(response.getData().getMessage(),"GREEN");
                        BlueColorDecorator decorator=new BlueColorDecorator(mDayDecoratorDateSelected);
                        calViewSchedule.removeDecorator(mHashMapDD.get(mDayDecoratorDateSelected));
                        calViewSchedule.addDecorator(decorator);
                        mHashMapDD.put(mDayDecoratorDateSelected,decorator);
                        ((ScheduleSychronizeFragment)requireParentFragment()).refreshPreviousUi();
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        ((ScheduleSychronizeFragment)requireParentFragment()).showAlertView(response.getErrorMsg(),"RED");
                    }
                    break;
            }
        });


        mViewModel.getAllSchedules()
                .observe(getViewLifecycleOwner(), lstOfSchedules -> {
                    if(swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }


                    if (!lstOfSchedules.isEmpty()) {
                        for (AllMonthSchedule item : lstOfSchedules) {
                            String freshDate = formatDate(item.getDate());

                            if (item.getAnyPendingAppointment()) {
                                mHashMapDD.put(freshDate, new RedColorDecorator(freshDate));
                            } else {
                                mHashMapDD.put(freshDate, new WhiteColorDecorator(freshDate));
                            }
                        }
                        calViewSchedule.addDecorators(new ArrayList<>(mHashMapDD.values()));
                    }
                });


        mViewModel.getEnableView().observe(getViewLifecycleOwner(), this::resetEnableView);

    }

    private void resetEnableView(Boolean aBoolean) { }

    private void initCalendarView(View v) {
        calViewSchedule = v.findViewById(R.id.calendar_view_schedule);
        LocalDate calendar = LocalDate.now();

        final LocalDate minDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth().getValue()+2, 1);
        final LocalDate maxDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth().getValue()+2, minDateOfCalendar.lengthOfMonth());


        // Add dates on calendars
        Calendar minDateSelect = Calendar.getInstance();
        minDateSelect.add(Calendar.MONTH, 2);
        minDateSelect.set(minDateSelect.get(Calendar.YEAR), minDateSelect.get(Calendar.MONTH), minDateSelect.getActualMinimum(Calendar.DATE));

        Calendar maxDateSelect = Calendar.getInstance();
        maxDateSelect.add(Calendar.MONTH, 2);
        maxDateSelect.set(maxDateSelect.get(Calendar.YEAR), maxDateSelect.get(Calendar.MONTH), maxDateSelect.getActualMaximum(Calendar.DATE));

        Log.e(TAG,"month name--->"+minDateSelect.get(Calendar.MONTH));
        setMonthName(getMonthName(minDateSelect.get(Calendar.MONTH)));

        calendarObj = Calendar.getInstance();

        Iterator<Date> i = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
        while (i.hasNext()) {
            Date date = i.next();
//          Log.e(TAG, "all dates: " + date);
            String dateee = null;
            dateee = formatDateZ(date);
            mHashMapDD.put(dateee, new BlueColorDecorator(dateee));
        }

        calViewSchedule.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay day, boolean selected) {
                dateSelected = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
                if (mHashMapDD.containsKey(dateSelected)) {
                    mDayDecoratorDateSelected=dateSelected;
                    if(mHashMapDD.get(dateSelected) instanceof RedColorDecorator){
                        /// delete.......
                        mViewModel.setDeleteDialogVisibility(true);
                    }else if(mHashMapDD.get(dateSelected) instanceof WhiteColorDecorator){
                        /// delete.......
                        mViewModel.setDeleteDialogVisibility(true);
                    }else if(mHashMapDD.get(dateSelected) instanceof BlueColorDecorator){
                        /// create.......
                        showTimePicker();
                    }
                }
            }
        });

        calViewSchedule.setWeekDayFormatter(new WeekDayFormatter() {
            @Override
            public CharSequence format(DayOfWeek dayOfWeek) {
                return dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault());
            }
        });

        calViewSchedule.setTopbarVisible(false);

        calViewSchedule.state().edit()
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMinimumDate(minDateOfCalendar)
                .setShowWeekDays(true)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMaximumDate(maxDateOfCalendar)
                .commit();
    }

    private String getMonthName(int i) {
        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        return monthName[i];
    }


    private String formatDateZ(Date oldDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String strDate = dateFormat.format(oldDate);
        return strDate;
    }

    private class BlueColorDecorator implements DayViewDecorator {
        private String date;
        public BlueColorDecorator(String date) {
            this.date = date;
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
//            Log.e("BlueColorDecorator", "" + date);
            return this.date.equals(date);
        }

        @Override
        public void decorate(final DayViewFacade view) {
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
//            Log.e("RedColorDecorator", "" + date);
            return this.date.equals(date);
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_circle_iv));
        }
    }

    private class WhiteColorDecorator implements DayViewDecorator {
        private String date;

        public WhiteColorDecorator(String date) {
            this.date = date;
        }

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
//            Log.e("WhiteColorDecorator", "" + date);
            return this.date.equals(date);
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlue)));
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_circle_vi));

        }
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

    private void showTimePicker() {
        TimePickerDialogFragment mDialogFragment = TimePickerDialogFragment.newInstance();
        mDialogFragment.setOnTimePickerDialogFragmentListener((startTime, endTime) -> {
            //call api....
            DayScheduleRequest in = new DayScheduleRequest();
            in.setId(0);
            in.setAvailableDate(formatDateIII(dateSelected));
            in.setFromTime(startTimeSelected);
            in.setToTime(endTimeSelected);
            in.setIsAvailable(true);
            Log.e(TAG,in.toString());
            mViewModel.createDaySchedule(mHeaderMap, in);
        });

        mDialogFragment.show(getChildFragmentManager(), "TAG");

    }



    public void refreshUi() {
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}