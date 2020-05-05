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
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.AlertDialogFragment;
import com.telemed.doctor.helper.DateIterator;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.schedule.model.AllMonthSchedule;
import com.telemed.doctor.schedule.model.DayScheduleResponse;
import com.telemed.doctor.schedule.model.DeleteScheduleRequest;
import com.telemed.doctor.schedule.viewmodel.DeleteAvailabilityTwoViewModel;
import com.telemed.doctor.schedule.viewmodel.ScheduleSychronizeIIViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

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


public class DeleteAvailabilityTwoFragment extends Fragment {
    private final String TAG = DeleteAvailabilityTwoFragment.class.getSimpleName();
    private DeleteAvailabilityTwoViewModel mViewModel;
    private MaterialCalendarView calViewSchedule;
    private HashMap<String, DayViewDecorator> mHashMapDD = new HashMap<>();
    private String dateSelected;
    private String mDayDecoratorDateSelected;
    private Calendar calendarObj;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
 //   private CustomAlertTextView tvAlertView;
    private List<String> lstOfDeleteDates;

    public static DeleteAvailabilityTwoFragment newInstance() {
        return new DeleteAvailabilityTwoFragment();
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
        lstOfDeleteDates=new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete_availability_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DeleteAvailabilityTwoViewModel.class);
 //       tvAlertView = v.findViewById(R.id.tv_alert_view);
        initCalendarView(v);
        initObserver();

    }











    private void initObserver() {
        mViewModel.getResultDeleteSchedule()
                .observe(getViewLifecycleOwner(), response -> {
                    switch (response.getStatus()){
                        case SUCCESS:
                            DayScheduleResponse infoObj = response.getData();
                            if (infoObj.getMessage() != null ) {
//                                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
//                                tvAlertView.showTopAlert(infoObj.getMessage());
                                for(int i=0;i<this.lstOfDeleteDates.size();i++){
                                    String oldDate = this.lstOfDeleteDates.get(i);
                                    calViewSchedule.removeDecorator(mHashMapDD.get(oldDate));
                                    mHashMapDD.remove(oldDate);
                                }
                                lstOfDeleteDates.clear();
                            }

                            break;
                        case FAILURE:
                            if (response.getErrorMsg() != null) {
                              //  tvAlertView.showTopAlert(response.getErrorMsg());

                            }
                            break;

                    }

                });


        mViewModel.getAllSchedules()
                .observe(getViewLifecycleOwner(), lstOfSchedules -> {



                    if (!lstOfSchedules.isEmpty()) {
                        List<AllMonthSchedule> lstOfSchedulesTemp = new ArrayList<>();
                        for (int i = 0; i < lstOfSchedules.size(); i++) {
                            Date date = DeleteAvailabilityFragment.formatDateII(lstOfSchedules.get(i).getDateValue());
                            if (date.before(calendarObj.getTime())) {
                                // remove ....
                            } else {
                                // add
                                lstOfSchedulesTemp.add(lstOfSchedules.get(i));
                            }


                        }


                        for (AllMonthSchedule item : lstOfSchedulesTemp) {
                            String freshDate = DeleteAvailabilityFragment.formatDate(item.getDateValue());

//                            if (item.getAnyPendingAppointment()) {
//                                mHashMapDD.put(freshDate, new DeleteAvailabilityFragment.RedColorDecorator(freshDate,getResources()));
//                            } else {
//                                mHashMapDD.put(freshDate, new DeleteAvailabilityFragment.WhiteColorDecorator(freshDate,getResources()));
//                            }
                            mHashMapDD.put(freshDate, new DeleteAvailabilityFragment.WhiteColorDecorator(freshDate,getResources()));

                        }

                        calViewSchedule.addDecorators(new ArrayList<>(mHashMapDD.values()));
                    }

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
            dateee = DeleteAvailabilityFragment.formatDateZ(date);
            mHashMapDD.put(dateee, new DeleteAvailabilityFragment.BlueColorDecorator(dateee,getResources()));
        }



        calViewSchedule.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay day, boolean selected) {
                dateSelected = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
                if (mHashMapDD.containsKey(dateSelected)) {
                    mDayDecoratorDateSelected=dateSelected;
                    if(mHashMapDD.get(dateSelected) instanceof DeleteAvailabilityFragment.WhiteColorDecorator){
                        DeleteAvailabilityFragment.RedColorDecorator decorator=new DeleteAvailabilityFragment.RedColorDecorator(mDayDecoratorDateSelected,getResources());
                        calViewSchedule.removeDecorator(mHashMapDD.get(mDayDecoratorDateSelected));
                        calViewSchedule.addDecorator(decorator);
                        mHashMapDD.put(mDayDecoratorDateSelected,decorator);
                        lstOfDeleteDates.add(dateSelected);
                        return;
                    }
                    if(mHashMapDD.get(dateSelected) instanceof DeleteAvailabilityFragment.RedColorDecorator){
                        DeleteAvailabilityFragment.WhiteColorDecorator decorator=new DeleteAvailabilityFragment.WhiteColorDecorator(mDayDecoratorDateSelected,getResources());
                        calViewSchedule.removeDecorator(mHashMapDD.get(mDayDecoratorDateSelected));
                        calViewSchedule.addDecorator(decorator);
                        mHashMapDD.put(mDayDecoratorDateSelected,decorator);
                        lstOfDeleteDates.remove(dateSelected);
                        return;
                    }


                }
            }
        });

        calViewSchedule.setWeekDayFormatter(dayOfWeek -> dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()));
        calViewSchedule.setTopbarVisible(false);
        calViewSchedule.state().edit()
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMinimumDate(minDateOfCalendar)
                .setShowWeekDays(true)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .setMaximumDate(maxDateOfCalendar)
                .commit();
    }



    public void updateUi(List<AllMonthSchedule> availableScheduleList) {
        mViewModel.setScheduleList(availableScheduleList);

    }

    public void deleteSchedule() {
        if(this.lstOfDeleteDates.isEmpty()) return;


        // Log.e("abc:  --> ",lstOfDeleteDates.toString());
        List<String> lstOfDeleteDates=new ArrayList<>();
        List<Integer> lstOfDeleteDays=new ArrayList<>();
        lstOfDeleteDays.add(0); // empty

        for(int i=0;i<this.lstOfDeleteDates.size();i++){
            String oldDate = this.lstOfDeleteDates.get(i);
            lstOfDeleteDates.add(DeleteAvailabilityFragment.convert(oldDate));
        }
//        Log.e("lstOfDeleteDates:  --> ",lstOfDeleteDates.toString());
//        Log.e("lstOfDeleteDays:  --> ",lstOfDeleteDays.toString());

        DeleteScheduleRequest in=new DeleteScheduleRequest();
        in.setSelectedDays(lstOfDeleteDays);
        in.setSelectedDates(lstOfDeleteDates);
        mViewModel.deleteDatesSchedule(mHeaderMap,in);

    }
}
