package com.telemed.doctor.schedule.view;

import android.content.Context;
import android.os.Bundle;
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
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.helper.DateIterator;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.viewmodel.ScheduleIIMonthViewModel;

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

public class ScheduleIIMonthFragment extends Fragment {
    private final String TAG = ScheduleIIMonthFragment.class.getSimpleName();
    private MaterialCalendarView calViewSchedule;
    private List<DayViewDecorator> lstOfDisableDays, lstOfEnableDays,listOfDayDecoration;
    private String mAccessToken;
    private ScheduleIIMonthViewModel mViewModel;
    private HashMap<String, String> mHeaderMap;
    private Calendar calendarObj;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ScheduleIIMonthFragment() {
        // Required empty public constructor
    }


    public static ScheduleIIMonthFragment newInstance() {
        ScheduleIIMonthFragment fragment = new ScheduleIIMonthFragment();
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
        lstOfDisableDays=new ArrayList<>();; // not used yet
        lstOfEnableDays=new ArrayList<>();; // not used yet
        listOfDayDecoration=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_month, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScheduleIIMonthViewModel.class);
        initCalendarView(v);
        initObserver();

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            ((ScheduleFragment)requireParentFragment()).fetchMonthlySchedules(1);
        });
    }

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

        Iterator<Date> i = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
        while (i.hasNext()) {
            Date date = i.next();
//            Log.e(TAG, "all dates: " + date);
            String dateee = null;
            dateee = formatDateZ(date);
            if (date.before(calendarObj.getTime())) {
                lstOfDisableDays.add(new ScheduleFragment.DisableDateDecorator(dateee,getResources()));
//                Log.e(TAG, "all dates: before" + dateee);
            } else {
                lstOfEnableDays.add(new ScheduleFragment.BlueColorDecorator(dateee,getResources()));
//                Log.e(TAG, "all dates: after" + dateee);
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

        calViewSchedule.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay day, boolean selected) {
                String date = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
                date=formatDateII(date);
                ((ScheduleFragment)requireParentFragment()).showFragment("DayWiseAvailabilityFragment", date);
            }
        });

        calViewSchedule.setWeekDayFormatter(new WeekDayFormatter() {
            @Override
            public CharSequence format(DayOfWeek dayOfWeek) {
                return dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault());
            }
        });
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
                            Date date = formatDateIY(lstOfSchedules.get(i).getDateValue());
                            if (date.before(calendarObj.getTime())) {
                                // remove ....
                            } else {
                                // add
                                lstOfSchedulesTemp.add(lstOfSchedules.get(i));
                            }


                        }


                        listOfDayDecoration.clear();
                        calViewSchedule.removeDecorators();
                        listOfDayDecoration.addAll(lstOfDisableDays);
                        listOfDayDecoration.addAll(lstOfEnableDays);
                        for(AllMonthSchedule item:lstOfSchedulesTemp){
                            String freshDate = formatDate(item.getDateValue());
                            if(item.getAnyPendingAppointment()){
                                listOfDayDecoration.add(new ScheduleFragment.RedColorDecorator(freshDate,getResources()));
                            }else {
                                listOfDayDecoration.add(new ScheduleFragment.WhiteColorDecorator(freshDate,getResources()));
                            }
                        }
                        calViewSchedule.addDecorators(listOfDayDecoration);

                    }else {
                        listOfDayDecoration.clear();
                        calViewSchedule.removeDecorators();
                        listOfDayDecoration.addAll(lstOfDisableDays);
                        listOfDayDecoration.addAll(lstOfEnableDays);
                        calViewSchedule.addDecorators(listOfDayDecoration);
                    }

                });


    }

    private void resetEnableView(Boolean isView) {}





    public void updateUi(List<AllMonthSchedule> availableScheduleList) {
        mViewModel.setScheduleList(availableScheduleList);

    }



    public void hideRefreshing() {

        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }

    }

}
