package com.telemed.doctor.schedule.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.helper.DateIterator;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.viewmodel.ScheduleIMonthViewModel;

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

import static com.telemed.doctor.schedule.view.ScheduleFragment.formatDate;
import static com.telemed.doctor.schedule.view.ScheduleFragment.formatDateII;
import static com.telemed.doctor.schedule.view.ScheduleFragment.formatDateIY;
import static com.telemed.doctor.schedule.view.ScheduleFragment.formatDateZ;


public class ScheduleIMonthFragment extends Fragment {
    private final String TAG = ScheduleIMonthFragment.class.getSimpleName();
    private MaterialCalendarView calViewSchedule;
    private List<DayViewDecorator> lstOfDisableDays,lstOfEnableDays, listOfDayDecoration;
    private HashMap<String, String> mHeaderMap;
    private String mAccessToken;
    private ScheduleIMonthViewModel mViewModel;
    private String monthName;
    private Calendar calendarObj;
    private SwipeRefreshLayout swipeRefreshLayout;

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public ScheduleIMonthFragment() {
        // Required empty public constructor
    }


    public static ScheduleIMonthFragment newInstance() {
        ScheduleIMonthFragment fragment = new ScheduleIMonthFragment();
        return fragment;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SharedPrefHelper mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);

        lstOfDisableDays=new ArrayList<>();;
        lstOfEnableDays=new ArrayList<>();;
        listOfDayDecoration=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_month, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScheduleIMonthViewModel.class);
        initCalendarView(v);
        initObserver();

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            ((ScheduleFragment)requireParentFragment()).fetchMonthlySchedules(0);
        });

    }



    private void initCalendarView(View v) {
        calViewSchedule = v.findViewById(R.id.calendar_view_schedule);
        LocalDate calendar = LocalDate.now();

        final LocalDate minDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth(), 1);
        final LocalDate maxDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth(), minDateOfCalendar.lengthOfMonth());

        // Add dates on calendars
        Calendar minDateSelect = Calendar.getInstance();
        minDateSelect.set(minDateSelect.get(Calendar.YEAR), minDateSelect.get(Calendar.MONTH), minDateSelect.getActualMinimum(Calendar.DATE));

        Calendar maxDateSelect = Calendar.getInstance();
        maxDateSelect.set(maxDateSelect.get(Calendar.YEAR), maxDateSelect.get(Calendar.MONTH), maxDateSelect.getActualMaximum(Calendar.DATE));


         Log.e(TAG,"month name--->"+minDateSelect.get(Calendar.MONTH));
         setMonthName(ScheduleFragment.getMonthName(minDateSelect.get(Calendar.MONTH))); // not working..
        ((ScheduleFragment)requireParentFragment()).setMonthName(ScheduleFragment.getMonthName(minDateSelect.get(Calendar.MONTH)));

        calendarObj = Calendar.getInstance();
        calendarObj.add(Calendar.DAY_OF_MONTH, -1);
        Iterator<Date> i = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
        while (i.hasNext()) {
            Date date = i.next();
//          Log.e(TAG, "all dates: " + date);
            String dateee = formatDateZ(date);
            if (date.before(calendarObj.getTime())) {
                lstOfDisableDays.add(new ScheduleFragment.DisableDateDecorator(dateee,getResources()));
           //  Log.e(TAG, "all dates: before" + dateee);
            } else {
                lstOfEnableDays.add(new ScheduleFragment.BlueColorDecorator(dateee,getResources()));
          //  Log.e(TAG, "all dates: after" + dateee);
            }


        }

        calViewSchedule.state().edit()
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMinimumDate(minDateOfCalendar)
                .setShowWeekDays(true)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMaximumDate(maxDateOfCalendar)
                .commit();

        calViewSchedule.setTopbarVisible(false);
        calViewSchedule.setOnDateChangedListener((widget, day, selected) -> {
            String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
            date=formatDateII(date);
            if (getParentFragment() != null)
                ((ScheduleFragment)requireParentFragment()).showFragment("DayWiseAvailabilityFragment", date);
        });

        calViewSchedule.setWeekDayFormatter(dayOfWeek ->
                dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()));
    }





    public void updateUi(List<AllMonthSchedule> availableScheduleList) {
        mViewModel.setScheduleList(availableScheduleList);

    }

    public void hideRefreshing() {
        if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
    }









    private void initObserver() {

        mViewModel.getAllSchedules()
                .observe(getViewLifecycleOwner(), lstOfSchedules -> {
                    if(swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    if (!lstOfSchedules.isEmpty()) {

                        List<AllMonthSchedule> lstOfSchedulesTemp = new ArrayList<>();
                        for (int i = 0; i < lstOfSchedules.size(); i++) {
                            Date date = formatDateIY(lstOfSchedules.get(i).getDate());
                            if (date.before(calendarObj.getTime())) {
                                // remove ....
                            } else {
                                // add
                                lstOfSchedulesTemp.add(lstOfSchedules.get(i));
                            }
                        }

                        // in order to refresh ,it needs to be clear ....
                        listOfDayDecoration.clear();
                        calViewSchedule.removeDecorators();

                        listOfDayDecoration.addAll(lstOfDisableDays);
                        listOfDayDecoration.addAll(lstOfEnableDays);
                        for(AllMonthSchedule item:lstOfSchedulesTemp){
                            String freshDate = formatDate(item.getDate());

                            if(item.getAnyPendingAppointment()){
                                listOfDayDecoration.add(new ScheduleFragment.RedColorDecorator(freshDate,getResources()));
                            }else {
                                listOfDayDecoration.add(new ScheduleFragment.WhiteColorDecorator(freshDate,getResources()));
                            }
                        }
                        calViewSchedule.addDecorators(listOfDayDecoration);
                    }
                });


    }











}
