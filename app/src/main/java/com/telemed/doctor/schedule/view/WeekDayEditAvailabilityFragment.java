package com.telemed.doctor.schedule.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.TimePickerDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.model.DayAvailabilityModel;
import com.telemed.doctor.schedule.model.DayScheduleRequest;
import com.telemed.doctor.schedule.model.DayScheduleResponse;
import com.telemed.doctor.schedule.model.EditDayScheduleRequest;
import com.telemed.doctor.schedule.model.WeekDayAvailabilityResponse;
import com.telemed.doctor.schedule.viewmodel.EditAvailabilityViewModel;
import com.telemed.doctor.schedule.viewmodel.WeekDayEditAvailabilityViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class WeekDayEditAvailabilityFragment extends Fragment {
    private WeekDayEditAvailabilityViewModel mViewModel;
    private FloatingActionButton fbtnAddAvailability;
    private RecyclerView rvTimeSlot;
    private WeekDayAvailabilityAdapter mAdapter;
    private ImageButton ibtnClose, ibtnBack;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private HomeFragmentSelectedListener mFragmentListener;
    private SharedPrefHelper mHelper;
    private String mAccessToken;
    private int selectedDay;
    private HashMap<String, String> mHeaderMap;
    private TextView tvDateSelected;


    public static WeekDayEditAvailabilityFragment newInstance(Object payload) {
        WeekDayEditAvailabilityFragment fragment = new WeekDayEditAvailabilityFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("KEY_", (Integer) payload);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
        mHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();
        mAccessToken = mHelper.read(SharedPrefHelper.KEY_ACCESS_TOKEN, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(WeekDayEditAvailabilityViewModel.class);
        super.onCreate(savedInstanceState);
        mAdapter= new WeekDayAvailabilityAdapter();
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "applicat" +
                "ion/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);
        // collect our intent
        if (getArguments() != null) {
            selectedDay = getArguments().getInt("KEY_");

        }
        mViewModel.fetchWeekDaySchedule(mHeaderMap, selectedDay);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_week_day_edit_availability, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initRecyclerView(v);
        initObserver();

         tvDateSelected.setText(getDayOfWeek(selectedDay));
    }

    private void initListener() {

        ibtnClose.setOnClickListener(v -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment",0);
        });

        ibtnBack.setOnClickListener(v -> {
            if (mFragmentListener != null) {
                mFragmentListener.popTopMostFragment();
            }
        });

        fbtnAddAvailability.setOnClickListener(vv -> showTimePicker());

    }

    private void showTimePicker() {
        TimePickerDialogFragment mDialogFragment = TimePickerDialogFragment.newInstance();
        mDialogFragment.setOnTimePickerDialogFragmentListener((startTime, endTime) -> {
            List<DayScheduleRequest.TimeSlot> lstOfTimeSlots=new ArrayList<>();
            //new
            DayScheduleRequest.TimeSlot newTimeSlot=new DayScheduleRequest.TimeSlot();
            newTimeSlot.setToTime(endTime);
            newTimeSlot.setFromTime(startTime);
            newTimeSlot.setTimeDiff(15);
            lstOfTimeSlots.add(newTimeSlot);
            // old
            List<WeekDayAvailabilityResponse.AvailableTime> lstOfOldTimeSlots = mViewModel.getAllDaySchedule().getValue();
            if (lstOfOldTimeSlots != null && !lstOfOldTimeSlots.isEmpty()) {

                for (int i = 0; i < lstOfOldTimeSlots.size(); i++) {
                    DayScheduleRequest.TimeSlot timeSlott = new DayScheduleRequest.TimeSlot();
                    timeSlott.setToTime(lstOfOldTimeSlots.get(i).getTimeTo());
                    timeSlott.setFromTime(lstOfOldTimeSlots.get(i).getTimeFrom());
                    timeSlott.setTimeDiff(15);
                    lstOfTimeSlots.add(timeSlott);
                }
            }


            //call api....
            List<String> lstOfDates=new ArrayList<>();

            List<Integer> lstOfDays=new ArrayList<>();
            lstOfDays.add(selectedDay);



            DayScheduleRequest in=new DayScheduleRequest();
            in.setIsAvailable(true);
            in.setAvailableDates(lstOfDates);
            in.setAvailableDays(lstOfDays);
            in.setTimeSlots(lstOfTimeSlots);

            mViewModel.createWeekDaySchedule(mHeaderMap,in);

        });

        mDialogFragment.show(getChildFragmentManager(), "TAG");
    }

    private void initView(View v) {
        ibtnClose = v.findViewById(R.id.ibtn_close);
        ibtnBack = v.findViewById(R.id.ibtn_back);

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        progressBar.setVisibility(View.INVISIBLE);

        fbtnAddAvailability=v.findViewById(R.id.fbtn_add_availability);
        tvDateSelected = v.findViewById(R.id.tv_time_slot);

    }

    private void showTimePicker(WeekDayAvailabilityResponse.AvailableTime model ) {
        TimePickerDialogFragment mDialogFragment = TimePickerDialogFragment.newInstance();
        mDialogFragment.setOnTimePickerDialogFragmentListener((startTime, endTime) -> {
            //call api....
            EditDayScheduleRequest in=new EditDayScheduleRequest();
            in.setTimeFrom(startTime);
            in.setTimeTo(endTime);
            in.setTimeDiff(15);
            long id = model.getId();int idd = (int) id;
            mViewModel.editDaySchedule(mHeaderMap,in,idd);

        });

        mDialogFragment.show(getChildFragmentManager(), "TAG");

    }

    private void initRecyclerView(View v) {
        rvTimeSlot = v.findViewById(R.id.rv_time_slot);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        rvTimeSlot.setLayoutManager(linearLayoutManager);
        rvTimeSlot.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        rvTimeSlot.setItemAnimator(new DefaultItemAnimator());
    }

    private WeekDayAvailabilityAdapter.OnItemClickListener mOnItemClickListener=new WeekDayAvailabilityAdapter.OnItemClickListener() {
        @Override
        public void onItemEditClick(WeekDayAvailabilityResponse.AvailableTime model, int position) {
           showTimePicker(model);
        }

        @Override
        public void onItemDeleteClick(WeekDayAvailabilityResponse.AvailableTime model, int position) {
            long id = model.getId();int idd = (int) id;
            mViewModel.deleteDaySchedule(mHeaderMap,idd);
        }
    };

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);

        mViewModel.getResultAllDaySchedule().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        WeekDayAvailabilityResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getAvailableTimes() != null && !infoObj.getAvailableTimes().isEmpty() ) {
                            mViewModel.setAllDayScheduleList(infoObj.getAvailableTimes());
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
                        String msg = response.getData().getMessage();
                        if (msg != null ) {
                            mViewModel.fetchWeekDaySchedule(mHeaderMap,selectedDay);
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            tvAlertView.showTopAlert(msg);
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


        mViewModel.getAllDaySchedule().observe(getViewLifecycleOwner(),lstOfAllDaySchedule->{
            mAdapter.setItems(lstOfAllDaySchedule);
        });



        mViewModel.getResultDeleteSchedule().observe(getViewLifecycleOwner(),response ->{
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        DayScheduleResponse infoObj = response.getData();
                        if (infoObj.getMessage() != null ) {
                            mViewModel.fetchWeekDaySchedule(mHeaderMap,selectedDay); //"2020-05-08"
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            tvAlertView.showTopAlert(infoObj.getMessage());
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


        mViewModel.getResultEditSchedule().observe(getViewLifecycleOwner(),response ->{
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        DayScheduleResponse infoObj = response.getData();
                        if (infoObj.getMessage() != null ) {
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            tvAlertView.showTopAlert(infoObj.getMessage());
                            mViewModel.fetchWeekDaySchedule(mHeaderMap,selectedDay); //"2020-05-08"
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


    }

    private void resetEnableView(Boolean aBoolean) {

    }
    public static String getDayOfWeek(int day) {
        switch (day) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return "";
    }


}
