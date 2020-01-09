package com.telemed.doctor.schedule;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MyScheduleFragment extends Fragment {

    private WeeklyScheduleAdapter mAdapter;
    private Button btnSynchronizeSchedule;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;
    private MaterialCalendarView calViewSchedule;
    private HashMap<String, Boolean> calDatesMap;
    private List<Integer> listOfDays;





    public static MyScheduleFragment newInstance() {
        return new MyScheduleFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listOfDays = new ArrayList<>();
        calDatesMap = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        initView(v);
        initListener();
        initCalendarView(v);



    }
    private void initCalendarView(View v) {
        calViewSchedule = v.findViewById(R.id.calendar_view_schedule);
        calViewSchedule.addDecorator(new PrimeDayDisableDecorator());


         LocalDate calendar = LocalDate.now();
         calViewSchedule.setSelectedDate(calendar);

         final LocalDate minDate = LocalDate.of(calendar.getYear(), calendar.getMonth(), 1);
         final LocalDate maxDate = minDate.plusMonths(3).minusDays(1);


        CalendarDay mAbc = CalendarDay.from(maxDate);


        // Add dates on calendars
        Calendar minDateSelect = Calendar.getInstance();
        minDateSelect.set(Calendar.DAY_OF_MONTH, 1);

        Calendar maxDateSelect = Calendar.getInstance();
        maxDateSelect.set(maxDate.getYear(), maxDate.getMonthValue(),maxDate.getDayOfMonth());

        Calendar calendar1 = Calendar.getInstance();
        String curr = calendar1.get(Calendar.DAY_OF_MONTH) + "/" + (calendar1.get(Calendar.MONTH) + 1) + "/" + calendar1.get(Calendar.YEAR);

        // Add current date in hash map and disable current date
        calDatesMap.put(curr,true);

        // Add dates in hash map to disable unavailable dates
        for (Calendar loopdate = minDateSelect; minDateSelect.before(maxDateSelect); minDateSelect.add(Calendar.DATE, 1), loopdate = minDateSelect) {
            String date = loopdate.get(Calendar.DAY_OF_MONTH) + "/" + (loopdate.get(Calendar.MONTH) + 1) + "/" + loopdate.get(Calendar.YEAR);


             if(loopdate.before(curr)){


             }



        }
calViewSchedule.setOnDateChangedListener(new OnDateSelectedListener() {
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
    }
});
        // Commit calendar
//        calViewSchedule.setWeekDayLabels(new CharSequence[]{"S","M","T","W","T","F","S"});
        calViewSchedule.setWeekDayFormatter(new WeekDayFormatter() {
            @Override
            public CharSequence format(DayOfWeek dayOfWeek) {
                return dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault());
            }
        });

        calViewSchedule.state().edit()
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMinimumDate(minDate)
                .setShowWeekDays(true)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMaximumDate(maxDate)

                .commit();





    }

    private void initView(View v) {
         ibtnClose = v.findViewById(R.id.ibtn_close);
         btnSynchronizeSchedule = v.findViewById(R.id.btn_synchronize_schedule);

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

    }




    //  Disable dates decorator
    private class PrimeDayDisableDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(final CalendarDay day) {
            final Calendar calendar = Calendar.getInstance();
//            day.getDay();
//            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
//            calendar.set(Calendar.MONTH, day.getMonth());
            // calendar.set(day.getYear(), day.getMonth(), day.getDay());
//            String date = calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
            String date = day.getDay() + "/" + day.getMonth() + "/" + day.getYear();

            if (calDatesMap.get(date) != null)
                if (calDatesMap.get(date)) {
                    return true;
                } else {
                    return false;
                }
            else
                return false;
        }

        @Override
        public void decorate(final DayViewFacade view) {
            view.setDaysDisabled(true);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_circle_ii));

        }

    }

}
