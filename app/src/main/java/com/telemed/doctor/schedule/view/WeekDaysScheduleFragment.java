package com.telemed.doctor.schedule.view;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.TimePickerDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.model.WeekDaySchedule;
import com.telemed.doctor.schedule.model.WeekScheduleRequest;
import com.telemed.doctor.schedule.model.WeekScheduleResponse;
import com.telemed.doctor.schedule.viewmodel.WeekDaysScheduleViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class WeekDaysScheduleFragment extends Fragment {
    private WeekDaysScheduleViewModel mViewModel;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose, ibtnBack;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private String mAccessToken;
    private HashMap<String, String> mHeaderMap;
    private MaterialCardView cvSunday, cvMonday, cvTuesday, cvWednesday, cvThursday, cvFriday, cvSaturday;
    private TextView tvSunday, tvMonday, tvTuesday, tvWednesday, tvThursday, tvFriday, tvSaturday;
    private TextView tvSundayTimeSlot, tvMondayTimeSlot, tvTuesdayTimeSlot, tvWednesdayTimeSlot, tvThursdayTimeSlot,
            tvFridayTimeSlot, tvSaturdayTimeSlot;

    public static WeekDaysScheduleFragment newInstance(Object payload) {
        WeekDaysScheduleFragment fragment = new WeekDaysScheduleFragment();
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_week_days_schedule, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(WeekDaysScheduleViewModel.class);
        super.onViewCreated(v, savedInstanceState);
        initView(v);

      //  mViewModel.fetchWeekSchedules(mHeaderMap);
        initObserver();

    }

    private void initView(View v) {
        ibtnClose = v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment", 0);
        });

        ibtnBack = v.findViewById(R.id.ibtn_back);
        ibtnBack.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);

        progressBar.setVisibility(View.INVISIBLE);


        cvSunday = v.findViewById(R.id.cv_sunday);
        cvMonday = v.findViewById(R.id.cv_monday);
        cvTuesday = v.findViewById(R.id.cv_tuesday);
        cvWednesday = v.findViewById(R.id.cv_wednesday);
        cvThursday = v.findViewById(R.id.cv_thursday);
        cvFriday = v.findViewById(R.id.cv_friday);
        cvSaturday = v.findViewById(R.id.cv_saturday);

        tvSunday = v.findViewById(R.id.tv_sunday);
        tvMonday = v.findViewById(R.id.tv_monday);
        tvTuesday = v.findViewById(R.id.tv_tuesday);
        tvWednesday = v.findViewById(R.id.tv_wednesday);
        tvThursday = v.findViewById(R.id.tv_thursday);
        tvFriday = v.findViewById(R.id.tv_friday);
        tvSaturday = v.findViewById(R.id.tv_saturday);

        tvSundayTimeSlot = v.findViewById(R.id.tv_sunday_time_slot);
        tvMondayTimeSlot = v.findViewById(R.id.tv_monday_time_slot);
        tvTuesdayTimeSlot = v.findViewById(R.id.tv_tuesday_time_slot);
        tvWednesdayTimeSlot = v.findViewById(R.id.tv_wednesday_time_slot);
        tvThursdayTimeSlot = v.findViewById(R.id.tv_thursday_time_slot);
        tvFridayTimeSlot = v.findViewById(R.id.tv_friday_time_slot);
        tvSaturdayTimeSlot = v.findViewById(R.id.tv_saturday_time_slot);

//        tvSundayTimeSlot.setVisibility(View.GONE);
//        tvMondayTimeSlot.setVisibility(View.GONE);
//        tvTuesdayTimeSlot.setVisibility(View.GONE);
//        tvWednesdayTimeSlot.setVisibility(View.GONE);
//        tvThursdayTimeSlot.setVisibility(View.GONE);
//        tvFridayTimeSlot.setVisibility(View.GONE);
//        tvSaturdayTimeSlot.setVisibility(View.GONE);
    }


    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);


        mViewModel.getCreateResultant().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null) {
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            mViewModel.fetchWeekSchedules(mHeaderMap);
                            if (mFragmentListener != null)
                                mFragmentListener.refreshFragment("ScheduleFragment");
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


        mViewModel.getResultantAllSchedule().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        WeekScheduleResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getWeekDayTimeSlots() != null) {
                            mViewModel.setScheduleList(infoObj.getWeekDayTimeSlots());
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


        mViewModel.getAllSchedules()
                .observe(getViewLifecycleOwner(), lstOfSchedules -> {
                    if (!lstOfSchedules.isEmpty()) {
                        List<WeekDaySchedule> lstOfCustomWeekDays = new ArrayList<>();
                        for (int i = 0; i < lstOfSchedules.size(); i++) {
                            List<WeekScheduleResponse.WeekDayDetail> xxx = lstOfSchedules.get(i).getWeekDayDetail();
                            List<WeekScheduleResponse.TimeSlot> yyy = lstOfSchedules.get(i).getTimeSlots();
                            WeekDaySchedule aa = new WeekDaySchedule();
                            aa.setTimeSlots(yyy);
                            aa.setWeekDayDetail(xxx);
                            lstOfCustomWeekDays.add(aa);
                        }

                        updateUi(lstOfCustomWeekDays);
                    }
                });


//        mViewModel.getResultantDeleteSchedule().observe(getViewLifecycleOwner(),response->{
//            switch (response.getStatus()) {
//                case SUCCESS:
//                    if (response.getData() != null) {
//                        if (mFragmentListener != null){
//                            tvAlertView.showTopAlert(response.getData().getMessage());
//                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
//                            mAdapter.deleteItem(itemToDeletePos);
//                            if(mFragmentListener !=null) mFragmentListener.refreshFragment("ScheduleFragment");
//
//
//                            if(mAdapter.getItemCount()==0){
//                                rvSchedule.setVisibility(View.INVISIBLE);
//                                tvEmptyView.setVisibility(View.VISIBLE);
//                            }else {
//                                rvSchedule.setVisibility(View.VISIBLE);
//                                tvEmptyView.setVisibility(View.INVISIBLE);
//                            }
//                        }
//                    }
//                    break;
//
//                case FAILURE:
//                    if (response.getErrorMsg() != null) {
//                        tvAlertView.showTopAlert(response.getErrorMsg());
//
//                    }
//                    break;
//            }
//
//        });
    }

    private void updateUi(List<WeekDaySchedule> lstOfCustomWeekDays) {

        for (int i = 0; i < lstOfCustomWeekDays.size(); i++) {

            List<WeekScheduleResponse.WeekDayDetail> aaa = lstOfCustomWeekDays.get(i).getWeekDayDetail();
            for (int j = 0; j < aaa.size(); j++) {

                switch (aaa.get(j).getWeekDay()) {
                    case 1:
                        tvSunday.setTextColor(getResources().getColor(R.color.colorWhite));
                        cvSunday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                        tvSundayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                        break;
                    case 2:
                        tvMonday.setTextColor(getResources().getColor(R.color.colorWhite));
                        cvMonday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                        tvMondayTimeSlot .setTextColor(getResources().getColor(R.color.colorWhite));

                        break;
                    case 3:
                        tvTuesday.setTextColor(getResources().getColor(R.color.colorWhite));
                        cvTuesday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                        tvTuesdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));

                        break;
                    case 4:
                        tvWednesday.setTextColor(getResources().getColor(R.color.colorWhite));
                        cvWednesday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                        tvWednesdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));

                        break;
                    case 5:
                        tvThursday.setTextColor(getResources().getColor(R.color.colorWhite));
                        cvThursday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                        tvThursdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));

                        break;
                    case 6:
                        tvFriday.setTextColor(getResources().getColor(R.color.colorWhite));
                        cvFriday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                        tvFridayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));


                        break;
                    case 7:
                        tvSaturday.setTextColor(getResources().getColor(R.color.colorWhite));
                        cvSaturday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                        tvThursdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                        tvSaturdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                        break;


                }


            }


        }


    }

    private void resetEnableView(Boolean isView) {

    }
/*
create weekday request:

isDate=false

{
  "weekDays": [
    1
  ],
  "availableDates": [

  ],
  "isAvailable": true,
  "timeSlots": [
    {
      "fromTime": "1:00",
      "toTime": "5:00",
      "timeDiff": 15
    }
  ]
}


create weekday response :

          {
  "status": true,
  "message": "Schedule has been Created Successfully!",
  "data": {}
}

 */

}
