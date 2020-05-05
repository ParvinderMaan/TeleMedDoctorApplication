package com.telemed.doctor.schedule.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.TimePickerDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.model.DayAvailabilityModel;
import com.telemed.doctor.schedule.model.DayScheduleAlterationResponse;
import com.telemed.doctor.schedule.model.DayScheduleRequest;
import com.telemed.doctor.schedule.model.MonthlyScheduleResponse;
import com.telemed.doctor.schedule.viewmodel.CreateAvailabilityViewModel;
import com.telemed.doctor.schedule.viewmodel.DeleteAvailabilityViewModel;
import com.telemed.doctor.schedule.viewmodel.ScheduleSychronizeIViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class CreateAvailabilityFragment extends Fragment {
    private AppCompatTextView tvStartTime,tvEndTime,tvDate;
    private AppCompatButton btnStartTime,btnEndTime ,btnDone;
    private String mStartTime="",mEndTime="";
    private CreateAvailabilityViewModel mViewModel;
    private HomeFragmentSelectedListener mFragmentListener;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private ImageButton ibtnClose, ibtnBack;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private String mSelectedDate;

    public static CreateAvailabilityFragment newInstance(Object payload) {
        CreateAvailabilityFragment fragment=new CreateAvailabilityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("KEY_", (String) payload);
        fragment.setArguments(bundle);
        return fragment;
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
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);

        mViewModel = ViewModelProviders.of(this).get(CreateAvailabilityViewModel.class);
        // collect our intent
        if (getArguments() != null) {
            mSelectedDate = getArguments().getString("KEY_");
            // Log.e(TAG, mDateOfAppointment);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_create_availability, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initObserver();
        try {
            tvDate.setText(convert(mSelectedDate));
        } catch (ParseException e) {
            tvDate.setText(mSelectedDate);
        }
    }

    private void initView(View v) {
        ibtnClose = v.findViewById(R.id.ibtn_close);
        ibtnBack = v.findViewById(R.id.ibtn_back);

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        progressBar.setVisibility(View.INVISIBLE);

        tvDate = v.findViewById(R.id.tv_date);
        tvStartTime = v.findViewById(R.id.tv_start_time);
        tvEndTime = v.findViewById(R.id.tv_end_time);
        btnStartTime = v.findViewById(R.id.btn_start_time);
        btnEndTime = v.findViewById(R.id.btn_end_time);
        btnDone = v.findViewById(R.id.btn_done);
    }

    private void showTimePicker() {
        TimePickerDialogFragment mDialogFragment = TimePickerDialogFragment.newInstance();
        mDialogFragment.setOnTimePickerDialogFragmentListener((startTime, endTime) -> {
            mStartTime=startTime;
            mEndTime=endTime;
            tvStartTime.setText(mStartTime);
            tvEndTime.setText(mEndTime);
        });
        mDialogFragment.show(getChildFragmentManager(), "TAG");
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

        btnStartTime.setOnClickListener(v1 -> showTimePicker());
        btnEndTime.setOnClickListener(v1 -> showTimePicker());
        btnDone.setOnClickListener(v1 -> {
            if(mStartTime.isEmpty() && mEndTime.isEmpty()){
                Toast.makeText(requireActivity(), "please enter start and end time", Toast.LENGTH_SHORT).show();
                return;
            }


            List<String> lstOfDates=new ArrayList<>(1);
            lstOfDates.add(formatDate(mSelectedDate));

            List<Integer> lstOfDays=new ArrayList<>(1);
            lstOfDays.add(0);

            List<DayScheduleRequest.TimeSlot> lstOfTimeSlots=new ArrayList<>(1);
            DayScheduleRequest.TimeSlot timeSlot=new DayScheduleRequest.TimeSlot();
            timeSlot.setToTime(mEndTime);
            timeSlot.setFromTime(mStartTime);
            timeSlot.setTimeDiff(15);
            lstOfTimeSlots.add(timeSlot);

            DayScheduleRequest in=new DayScheduleRequest();
            in.setIsAvailable(true);
            in.setAvailableDates(lstOfDates);
            in.setAvailableDays(lstOfDays);
            in.setTimeSlots(lstOfTimeSlots);
            mViewModel.createDaySchedule(mHeaderMap,in);
        });

    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);

        mViewModel.getResultantDayScheduleCreation().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        String msg = response.getData().getMessage();
                        if (msg != null ) {
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            tvAlertView.showTopAlert(msg);
                            mFragmentListener.popTopMostFragment();
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

    public static String convert(String dateString) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = sdf.parse(dateString);
        return new SimpleDateFormat("dd  MMMM",Locale.US).format(date);
    }
    public static String formatDate(String oldDate) {
        DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date freshDate = null;
        try {
            freshDate = sdFormat.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdFormat.format(freshDate);
    }
}
