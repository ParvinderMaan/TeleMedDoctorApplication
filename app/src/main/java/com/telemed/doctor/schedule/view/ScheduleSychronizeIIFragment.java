package com.telemed.doctor.schedule.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.AlertDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.helper.DateIterator;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.viewmodel.ScheduleSychronizeIIViewModel;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

// month two..
public class ScheduleSychronizeIIFragment extends Fragment {
    private final String TAG = ScheduleSychronizeIIFragment.class.getSimpleName();
    private ScheduleSychronizeIIViewModel mViewModel;
    private MaterialCalendarView calViewSchedule;
    private HashMap<String, DayViewDecorator> mHashMapDD = new HashMap<>();
    private String dateSelected;
    private String mDayDecoratorDateSelected;
    private Calendar calendarObj;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private AlertDialogFragment mDeleteDialogFragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ScheduleSychronizeIIFragment newInstance() {
        return new ScheduleSychronizeIIFragment();
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
        mViewModel = ViewModelProviders.of(this).get(ScheduleSychronizeIIViewModel.class);
        initCalendarView(v);
        initObserver();

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            ((ScheduleSychronizeFragment)requireParentFragment()).fetchMonthlySchedules(1);

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



        mViewModel.getAllSchedules()
                .observe(getViewLifecycleOwner(), lstOfSchedules -> {

                    if(swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }


                    if (!lstOfSchedules.isEmpty()) {

                        for (AllMonthSchedule item : lstOfSchedules) {
                            String freshDate = ScheduleSychronizeFragment.formatDate(item.getDateValue());

                            if (item.getAnyPendingAppointment()) {
                                mHashMapDD.put(freshDate, new ScheduleSychronizeFragment.RedColorDecorator(freshDate,getResources()));
                            } else {
                                mHashMapDD.put(freshDate, new ScheduleSychronizeFragment.WhiteColorDecorator(freshDate,getResources()));
                            }
                        }

                        calViewSchedule.addDecorators(new ArrayList<>(mHashMapDD.values()));
                    }else {
                        calViewSchedule.addDecorators(new ArrayList<>(mHashMapDD.values()));
                     //   Toast.makeText(requireActivity(), "empty", Toast.LENGTH_SHORT).show();
                    }

                });


    }

    private void resetEnableView(Boolean aBoolean) { }

    private void initCalendarView(View v) {
        calViewSchedule = v.findViewById(R.id.calendar_view_schedule);
        LocalDate calendar = LocalDate.now();

        final LocalDate minDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth().getValue()+1, 1);
        final LocalDate maxDateOfCalendar = LocalDate.of(calendar.getYear(), calendar.getMonth().getValue()+1, minDateOfCalendar.lengthOfMonth());

        // Add dates on calendars
        Calendar minDateSelect = Calendar.getInstance();
        minDateSelect.add(Calendar.MONTH, 1);
        minDateSelect.set(minDateSelect.get(Calendar.YEAR), minDateSelect.get(Calendar.MONTH), minDateSelect.getActualMinimum(Calendar.DATE));

        Calendar maxDateSelect = Calendar.getInstance();
        maxDateSelect.add(Calendar.MONTH, 1);
        maxDateSelect.set(maxDateSelect.get(Calendar.YEAR), maxDateSelect.get(Calendar.MONTH), maxDateSelect.getActualMaximum(Calendar.DATE));

        calendarObj = Calendar.getInstance();



        Log.e(TAG,"month name--->"+minDateSelect.get(Calendar.MONTH));
        setMonthName(ScheduleSychronizeFragment.getMonthName(minDateSelect.get(Calendar.MONTH))); // not working..


        Iterator<Date> i = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
        while (i.hasNext()) {
            Date date = i.next();
//            Log.e(TAG, "all dates: " + date);
            String dateee = null;
            dateee = ScheduleSychronizeFragment.formatDateZ(date);
            mHashMapDD.put(dateee, new ScheduleSychronizeFragment.BlueColorDecorator(dateee,getResources()));
        }



        calViewSchedule.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay day, boolean selected) {
                dateSelected = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
                if (mHashMapDD.containsKey(dateSelected)) {
                    mDayDecoratorDateSelected=dateSelected;
                    if(mHashMapDD.get(dateSelected) instanceof ScheduleSychronizeFragment.RedColorDecorator){
                        /// show EditAvail Fragment.......
                        ((ScheduleSychronizeFragment)requireParentFragment()).showEditAvailabilityFragment(dateSelected);
                    }else if(mHashMapDD.get(dateSelected) instanceof ScheduleSychronizeFragment.WhiteColorDecorator){
                        ///  show EditAvail Fragment.......
                        ((ScheduleSychronizeFragment)requireParentFragment()).showEditAvailabilityFragment(dateSelected);
                    }else if(mHashMapDD.get(dateSelected) instanceof ScheduleSychronizeFragment.BlueColorDecorator){
                        /// show CreateAvail Fragment......
                        ((ScheduleSychronizeFragment)requireParentFragment()).showCreateAvailabilityFragment(dateSelected);
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


    public void refreshUi() {
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

