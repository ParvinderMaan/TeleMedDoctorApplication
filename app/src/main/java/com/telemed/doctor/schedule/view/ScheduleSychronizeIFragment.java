package com.telemed.doctor.schedule.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.telemed.doctor.dialog.TimePickerDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.helper.DateIterator;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.model.DayScheduleRequest;
import com.telemed.doctor.schedule.viewmodel.ScheduleSychronizeIViewModel;

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

import static com.telemed.doctor.schedule.view.ScheduleSychronizeFragment.formatDate;
import static com.telemed.doctor.schedule.view.ScheduleSychronizeFragment.formatDateII;
import static com.telemed.doctor.schedule.view.ScheduleSychronizeFragment.formatDateIII;

// month one..
public class ScheduleSychronizeIFragment extends Fragment {
    private final String TAG = ScheduleSychronizeIFragment.class.getSimpleName();
    private MaterialCalendarView calViewSchedule;
    private HashMap<String, DayViewDecorator> mHashMapDD;
    private String startTimeSelected, endTimeSelected, dateSelected;
    private String mDayDecoratorDateSelected;
    private ScheduleSychronizeIViewModel mViewModel;
    private Calendar calendarObj;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private AlertDialogFragment mDeleteDialogFragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ScheduleSychronizeIFragment newInstance() {
        return new ScheduleSychronizeIFragment();
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
        mHashMapDD = new HashMap<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_month, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ScheduleSychronizeIViewModel.class);
        initCalendarView(v);
        initObserver();

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            ((ScheduleSychronizeFragment)requireParentFragment()).fetchMonthlySchedules(0);

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
                        mDeleteDialogFragment = AlertDialogFragment.newInstance("delete");
                        mDeleteDialogFragment.show(getChildFragmentManager(), "TAG");
                        mDeleteDialogFragment.setOnAlertDialogListener(new AlertDialogFragment.AlertDialogListener() {
                            @Override
                            public void onClickYes() {
                                    DayScheduleRequest in=new DayScheduleRequest();
                                    in.setId(0);
                                    in.setAvailableDate(formatDateIII(dateSelected));
                                    in.setFromTime("00:00");
                                    in.setToTime("00:00");
                                    in.setIsAvailable(false);
                                    mDeleteDialogFragment.dismiss();
                                    mViewModel.deleteWeekSchedule(mHeaderMap,in); // id
                            }
                            @Override
                            public void onClickNo() { }
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
                        ScheduleSychronizeFragment.WhiteColorDecorator decorator=new ScheduleSychronizeFragment.WhiteColorDecorator(mDayDecoratorDateSelected,getResources());
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
                        ScheduleSychronizeFragment.BlueColorDecorator decorator=new ScheduleSychronizeFragment.BlueColorDecorator(mDayDecoratorDateSelected,getResources());
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
                        List<AllMonthSchedule> lstOfSchedulesTemp = new ArrayList<>();
                        for (int i = 0; i < lstOfSchedules.size(); i++) {
                            Date date = ScheduleSychronizeFragment.formatDateII(lstOfSchedules.get(i).getDate());
                            if (date.before(calendarObj.getTime())) {
                                // remove ....
                            } else {
                                // add
                                lstOfSchedulesTemp.add(lstOfSchedules.get(i));
                            }


                        }


                        for (AllMonthSchedule item : lstOfSchedulesTemp) {
                            String freshDate = formatDate(item.getDate());

                            if (item.getAnyPendingAppointment()) {
                                mHashMapDD.put(freshDate, new ScheduleSychronizeFragment.RedColorDecorator(freshDate,getResources()));
                            } else {
                                mHashMapDD.put(freshDate, new ScheduleSychronizeFragment.WhiteColorDecorator(freshDate,getResources()));
                            }
                        }

                        calViewSchedule.addDecorators(new ArrayList<>(mHashMapDD.values()));
                    }

                });


        mViewModel.getEnableView().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                resetEnableView(aBoolean);
            }
        });
    }

    private void resetEnableView(Boolean aBoolean) { }

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
        setMonthName(ScheduleSychronizeFragment.getMonthName(minDateSelect.get(Calendar.MONTH))); // not working..

        if(requireParentFragment() instanceof ScheduleSychronizeFragment){
            ((ScheduleSychronizeFragment)requireParentFragment()).setMonthName(ScheduleSychronizeFragment.getMonthName(minDateSelect.get(Calendar.MONTH)));
        }
        if(requireParentFragment() instanceof DeleteAvailabilityFragment){
            ((DeleteAvailabilityFragment)requireParentFragment()).setMonthName(ScheduleSychronizeFragment.getMonthName(minDateSelect.get(Calendar.MONTH)));
        }

        calendarObj = Calendar.getInstance();
       //calendarObj.add(Calendar.DAY_OF_MONTH, -1);

        Iterator<Date> i = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
        while (i.hasNext()) {
            Date date = i.next();
//            Log.e(TAG, "all dates: " + date);
            String dateee = null;
            dateee = ScheduleSychronizeFragment.formatDateZ(date);
            if (date.before(calendarObj.getTime())) {
                 mHashMapDD.put(dateee, new ScheduleSychronizeFragment.DisableDateDecorator(dateee,getResources()));
//                Log.e(TAG, "all dates: before" + dateee);
            } else {
                mHashMapDD.put(dateee, new ScheduleSychronizeFragment.BlueColorDecorator(dateee,getResources()));
//                Log.e(TAG, "all dates: after" + dateee);
            }
        }

        calViewSchedule.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay day, boolean selected) {
                dateSelected = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();

                if (mHashMapDD.containsKey(dateSelected)) {
                    mDayDecoratorDateSelected=dateSelected;
                    if(mHashMapDD.get(dateSelected) instanceof ScheduleSychronizeFragment.RedColorDecorator){
                        /// delete.......
                        mViewModel.setDeleteDialogVisibility(true);
                    }else if(mHashMapDD.get(dateSelected) instanceof ScheduleSychronizeFragment.WhiteColorDecorator){
                        /// delete.......
                        mViewModel.setDeleteDialogVisibility(true);
                    }else if(mHashMapDD.get(dateSelected) instanceof ScheduleSychronizeFragment.BlueColorDecorator){
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

    private void showTimePicker() {
          TimePickerDialogFragment mDialogFragment = TimePickerDialogFragment.newInstance();
          mDialogFragment.setOnTimePickerDialogFragmentListener((startTime, endTime) -> {
            // call api....
            DayScheduleRequest in = new DayScheduleRequest();
            in.setId(0);
            in.setAvailableDate(formatDateIII(dateSelected));
            in.setFromTime(startTime);
            in.setToTime(endTime);
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
