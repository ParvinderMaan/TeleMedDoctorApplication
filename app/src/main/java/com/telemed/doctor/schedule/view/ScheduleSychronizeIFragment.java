package com.telemed.doctor.schedule.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.schedule.DateIterator;
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

// month one..
public class ScheduleSychronizeIFragment extends Fragment {
    private final String TAG = ScheduleSychronizeIFragment.class.getSimpleName();

    private MaterialCalendarView calViewSchedule;
    private HashMap<String, DayViewDecorator> mHashMapDD = new HashMap<>();
    private String startTimeSelected, endTimeSelected, dateSelected;
    private String mDayDecoratorDateSelected;

    private ScheduleSychronizeIViewModel mViewModel;
    private Calendar calendarObj;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
//    private List<DayViewDecorator> lstOfDisableDays = new ArrayList<>();
//    private List<DayViewDecorator> lstOfEnableDays = new ArrayList<>();
    private List<DayViewDecorator> listOfDayDecoration = new ArrayList<>();
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
                        mDeleteDialogFragment = AlertDialogFragment.newInstance();
                        mDeleteDialogFragment.show(getChildFragmentManager(), "TAG");
                        mDeleteDialogFragment.setOnAlertDialogListener(() -> {
                            DayScheduleRequest in=new DayScheduleRequest();
                            in.setId(0);
                            in.setAvailableDate(formatDateIII(dateSelected));
                            in.setFromTime("00:00");
                            in.setToTime("00:00");
                            in.setIsAvailable(false);
                            mDeleteDialogFragment.dismiss();
                            mViewModel.deleteWeekSchedule(mHeaderMap,in); // id
                        });
                    }else {
                        mDeleteDialogFragment.dismiss();
                        mDeleteDialogFragment.setOnAlertDialogListener(null);
                    }

                });

//        mViewModel.getEnableView()
//                .observe(getViewLifecycleOwner(), this::resetEnableView);

//        mViewModel.getResultAllScheduleIMonth().observe(getViewLifecycleOwner(), response -> {
//
//            switch (response.getStatus()) {
//                case SUCCESS:
//                    if (response.getData() != null) {
//                        MonthlyScheduleResponse.Data infoObj = response.getData().getData();
//                        if (infoObj.getAvailableScheduleList() != null) {
//                            ScheduleSychronizeIFragment fragment = (ScheduleSychronizeIFragment) mMonthPagerAdapter.getRegisteredFragment(0);
//                            fragment.updateUi(infoObj.getAvailableScheduleList());
//                        }
//                    }
//
//                    break;
//
//                case FAILURE:
//                    if (response.getErrorMsg() != null) {
//                        tvAlertView.showTopAlert(response.getErrorMsg());
//
//                    }
//                    break;
//            }
//        });

//        mViewModel.getResultAllScheduleIIMonth().observe(getViewLifecycleOwner(), response -> {
//
//            switch (response.getStatus()) {
//                case SUCCESS:
//                    if (response.getData() != null) {
//                        MonthlyScheduleResponse.Data infoObj = response.getData().getData();
//                        if (infoObj.getAvailableScheduleList() != null) {
//                            ScheduleSychronizeIIFragment fragment = (ScheduleSychronizeIIFragment) mMonthPagerAdapter.getRegisteredFragment(0);
//                            fragment.updateUi(infoObj.getAvailableScheduleList());
//                        }
//                    }
//
//                    break;
//
//                case FAILURE:
//                    if (response.getErrorMsg() != null) {
//                        tvAlertView.showTopAlert(response.getErrorMsg());
//
//                    }
//                    break;
//            }
//        });
//
//
//        mViewModel.getResultAllScheduleIIIMonth().observe(getViewLifecycleOwner(), response -> {
//
//            switch (response.getStatus()) {
//                case SUCCESS:
//                    if (response.getData() != null) {
//                        MonthlyScheduleResponse.Data infoObj = response.getData().getData();
//                        if (infoObj.getAvailableScheduleList() != null) {
//                            ScheduleSychronizeIIIFragment fragment = (ScheduleSychronizeIIIFragment) mMonthPagerAdapter.getRegisteredFragment(0);
//                            fragment.updateUi(infoObj.getAvailableScheduleList());
//                        }
//                    }
//
//                    break;
//
//                case FAILURE:
//                    if (response.getErrorMsg() != null) {
//                        tvAlertView.showTopAlert(response.getErrorMsg());
//
//                    }
//                    break;
//            }
//        });


        mViewModel.getResultantDayScheduleCreation().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
//                        tvAlertView.showTopAlert(response.getData().getMessage());
//                        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        ((ScheduleSychronizeFragment)requireParentFragment()).showAlertView(response.getData().getMessage(),R.color.colorGreen);
                        WhiteColorDecorator decorator=new WhiteColorDecorator(mDayDecoratorDateSelected);
                        mHashMapDD.put(mDayDecoratorDateSelected,decorator);
                        calViewSchedule.addDecorator(decorator);
//                        calViewSchedule.invalidateDecorators();

                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
//                        tvAlertView.showTopAlert(response.getErrorMsg());
                        ((ScheduleSychronizeFragment)requireParentFragment()).showAlertView(response.getErrorMsg(),R.color.colorRed);

                    }
                    break;
            }
        });


        mViewModel.getResultantDayScheduleDeletion().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
//                        tvAlertView.showTopAlert(response.getData().getMessage());
//                        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        ((ScheduleSychronizeFragment)requireParentFragment()).showAlertView(response.getData().getMessage(),R.color.colorGreen);
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
//                        tvAlertView.showTopAlert(response.getErrorMsg());
                        ((ScheduleSychronizeFragment)requireParentFragment()).showAlertView(response.getErrorMsg(),R.color.colorRed);

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
                            Date date = formatDateII(lstOfSchedules.get(i).getDate());
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
                                mHashMapDD.put(freshDate, new RedColorDecorator(freshDate));
                            } else {
                                mHashMapDD.put(freshDate, new WhiteColorDecorator(freshDate));
                            }
                        }

                        calViewSchedule.addDecorators(new ArrayList<>(mHashMapDD.values()));
                    }

                });


    }

    private void resetEnableView(Boolean aBoolean) {

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
        setMonthName(getMonthName(minDateSelect.get(Calendar.MONTH))); // not working..
        ((ScheduleSychronizeFragment)requireParentFragment()).setMonthName(getMonthName(minDateSelect.get(Calendar.MONTH)));

        calendarObj = Calendar.getInstance();

        Iterator<Date> i = new DateIterator(minDateSelect.getTime(), maxDateSelect.getTime());
        while (i.hasNext()) {
            Date date = i.next();
            Log.e(TAG, "all dates: " + date);
            String dateee = null;
            dateee = formatDateZ(date);
            if (date.before(calendarObj.getTime())) {
                 mHashMapDD.put(dateee, new DisableDateDecorator(dateee));
//                lstOfDisableDays.add(new DisableDateDecorator(dateee));
                Log.e(TAG, "all dates: before" + dateee);


            } else {
//                lstOfEnableDays.add(new BlueColorDecorator(dateee));
                mHashMapDD.put(dateee, new BlueColorDecorator(dateee));
                Log.e(TAG, "all dates: after" + dateee);


            }
        }



        calViewSchedule.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay day, boolean selected) {
                dateSelected = day.getYear() + "-" + day.getMonth() + "-" + day.getDay();
//                Toast.makeText(requireActivity(), "" + selectedDate, Toast.LENGTH_SHORT).show();
                if (mHashMapDD.containsKey(dateSelected)) {
                    mDayDecoratorDateSelected=dateSelected;
                    if(mHashMapDD.get(dateSelected) instanceof RedColorDecorator){
//                        Toast.makeText(requireActivity(), "" + "RedColorDecorator", Toast.LENGTH_SHORT).show();
                        /// delete.......
                        mViewModel.setDeleteDialogVisibility(true);


                    }else if(mHashMapDD.get(dateSelected) instanceof WhiteColorDecorator){
//                        Toast.makeText(requireActivity(), "" + "WhiteColorDecorator", Toast.LENGTH_SHORT).show();
                        /// delete.......

                        mViewModel.setDeleteDialogVisibility(true);



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


    public String formatDateZ(Date oldDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String strDate = dateFormat.format(oldDate);
        return strDate;
    }

//    public String formatDateII(String oldDate)  {
//        DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        Date freshDate = null;
//        try {
//            freshDate = sdFormat.parse(oldDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return sdFormat.format(freshDate);
//    }

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


    public String formatDate(Date oldDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String strDate = dateFormat.format(oldDate);
        return strDate;
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

}
