package com.telemed.doctor.schedule.view;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.ViewModelProviders;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.model.DayScheduleResponse;
import com.telemed.doctor.schedule.model.DeleteWeekDayScheduleRequest;
import com.telemed.doctor.schedule.model.WeekScheduleResponse;
import com.telemed.doctor.schedule.viewmodel.WeekDaysScheduleViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class WeekDaysScheduleFragment extends Fragment {
    int itemToDelete=0;
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
    private ImageButton ibtnSunMore, ibtnMonMore, ibtnTuesMore, ibtnWedMore, ibtnThurMore, ibtnFriMore, ibtnSatMore;
    private View.OnClickListener mOnMoreClickListener= v -> {
                    showMorePopUpMenu((ImageButton) v);

    };
    private View.OnClickListener mOnCardViewClickListener= v -> {
        Integer tag = ((Integer) v.getTag());
        if(mFragmentListener!=null)
            mFragmentListener.showFragment("WeekDayEditAvailabilityFragment",tag);

    };

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
        initListener();
        mViewModel.fetchWeekSchedules(mHeaderMap);
        initObserver();

    }

    private void initListener() {
        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment", 0);
        });
        ibtnBack.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        cvSunday.setOnClickListener(mOnCardViewClickListener);
        cvMonday.setOnClickListener(mOnCardViewClickListener);
        cvTuesday.setOnClickListener(mOnCardViewClickListener);
        cvWednesday.setOnClickListener(mOnCardViewClickListener);
        cvThursday.setOnClickListener(mOnCardViewClickListener);
        cvFriday.setOnClickListener(mOnCardViewClickListener);
        cvSaturday.setOnClickListener(mOnCardViewClickListener);


    }

    private void initView(View v) {
        ibtnClose = v.findViewById(R.id.ibtn_close);


        ibtnBack = v.findViewById(R.id.ibtn_back);


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


        ibtnSunMore = v.findViewById(R.id.ibtn_sun_more);
        ibtnMonMore = v.findViewById(R.id.ibtn_mon_more);
        ibtnTuesMore = v.findViewById(R.id.ibtn_tues_more);
        ibtnWedMore = v.findViewById(R.id.ibtn_wed_more);
        ibtnThurMore = v.findViewById(R.id.ibtn_thur_more);
        ibtnFriMore = v.findViewById(R.id.ibtn_fri_more);
        ibtnSatMore = v.findViewById(R.id.ibtn_sat_more);

//        tvSundayTimeSlot.setVisibility(View.GONE);
//        tvMondayTimeSlot.setVisibility(View.GONE);
//        tvTuesdayTimeSlot.setVisibility(View.GONE);
//        tvWednesdayTimeSlot.setVisibility(View.GONE);
//        tvThursdayTimeSlot.setVisibility(View.GONE);
//        tvFridayTimeSlot.setVisibility(View.GONE);
//        tvSaturdayTimeSlot.setVisibility(View.GONE);

        cvSunday.setTag(1);
        cvMonday.setTag(2);
        cvTuesday.setTag(3);
        cvWednesday.setTag(4);
        cvThursday.setTag(5);
        cvFriday.setTag(6);
        cvSaturday.setTag(7);
    }


    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);




        mViewModel.getResultantAllSchedule().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        WeekScheduleResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getReturnTimeSlots() != null) {
                            mViewModel.setScheduleList(infoObj.getReturnTimeSlots());
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
                        updateUi(lstOfSchedules);
                    }
                });


        mViewModel.getResultantDeleteSchedule()
                .observe(getViewLifecycleOwner(), response -> {
                    switch (response.getStatus()){
                        case SUCCESS:
                            DayScheduleResponse infoObj = response.getData();
                            if (infoObj.getMessage() != null ) {
                                tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                                tvAlertView.showTopAlert(infoObj.getMessage());
                                updateUi2(itemToDelete);
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

    private void updateUi2(int itemToDelete) {
        switch (itemToDelete) {
            case 1:
                tvSunday.setTextColor(getResources().getColor(R.color.colorBlue));
                cvSunday.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvSundayTimeSlot.setTextColor(getResources().getColor(R.color.colorBlue));
                tvSundayTimeSlot.setText("");
                ibtnSunMore.setTag(null);
                ibtnSunMore.setOnClickListener(null);

                break;
            case 2:
                tvMonday.setTextColor(getResources().getColor(R.color.colorBlue));
                cvMonday.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvMondayTimeSlot.setTextColor(getResources().getColor(R.color.colorBlue));
                tvMondayTimeSlot.setText("");
                ibtnMonMore.setTag(null);
                ibtnMonMore.setOnClickListener(null);
                break;
            case 3:
                tvTuesday.setTextColor(getResources().getColor(R.color.colorBlue));
                cvTuesday.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvTuesdayTimeSlot.setTextColor(getResources().getColor(R.color.colorBlue));
                ibtnTuesMore.setTag(null);
                ibtnTuesMore.setOnClickListener(null);

                break;
            case 4:
                tvWednesday.setTextColor(getResources().getColor(R.color.colorBlue));
                cvWednesday.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvWednesdayTimeSlot.setTextColor(getResources().getColor(R.color.colorBlue));
                tvWednesdayTimeSlot.setText("");
                ibtnWedMore.setTag(null);
                ibtnWedMore.setOnClickListener(null);

                break;
            case 5:
                tvThursday.setTextColor(getResources().getColor(R.color.colorBlue));
                cvThursday.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvThursdayTimeSlot.setTextColor(getResources().getColor(R.color.colorBlue));
                tvThursdayTimeSlot.setText("");
                ibtnThurMore.setTag(null);
                ibtnThurMore.setOnClickListener(null);

                break;
            case 6:
                tvFriday.setTextColor(getResources().getColor(R.color.colorBlue));
                cvFriday.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvFridayTimeSlot.setTextColor(getResources().getColor(R.color.colorBlue));
                tvFridayTimeSlot.setText("");
                ibtnFriMore.setTag(null);
                ibtnFriMore.setOnClickListener(mOnMoreClickListener);
                ibtnFriMore.setOnClickListener(null);
                break;
            case 7:
                tvSaturday.setTextColor(getResources().getColor(R.color.colorWhite));
                cvSaturday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                tvSaturdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                tvSaturdayTimeSlot.setText("");
                ibtnSatMore.setTag(null);
                ibtnSatMore.setOnClickListener(null);
                break;



        }

    }

    private void updateUi(List<WeekScheduleResponse.ReturnTimeSlot> lstOfCustomWeekDays) {

        for (int i = 0; i < lstOfCustomWeekDays.size(); i++) {

            List<WeekScheduleResponse.TimeSlotList> timeSlotLists = lstOfCustomWeekDays.get(i).getTimeSlotList();
            StringBuilder timeSlotBuilder = new StringBuilder();
            for (int ii = 0; ii < timeSlotLists.size(); ii++) {
                String xx = timeSlotLists.get(ii).getFromTime() + " - " + timeSlotLists.get(ii).getToTime();
                timeSlotBuilder.append(xx);
                timeSlotBuilder.append("\n");
            }

            switch (lstOfCustomWeekDays.get(i).getScheduleDay()) {
                case 1:
                    tvSunday.setTextColor(getResources().getColor(R.color.colorWhite));
                    cvSunday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                    tvSundayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvSundayTimeSlot.setText(timeSlotBuilder.toString());

                    ibtnSunMore.setOnClickListener(mOnMoreClickListener);
                    break;
                case 2:
                    tvMonday.setTextColor(getResources().getColor(R.color.colorWhite));
                    cvMonday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                    tvMondayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvMondayTimeSlot.setText(timeSlotBuilder.toString());

                    ibtnMonMore.setOnClickListener(mOnMoreClickListener);
                    break;
                case 3:
                    tvTuesday.setTextColor(getResources().getColor(R.color.colorWhite));
                    cvTuesday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                    tvTuesdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvTuesdayTimeSlot.setText(timeSlotBuilder.toString());
                    ibtnTuesMore.setOnClickListener(mOnMoreClickListener);
                    break;
                case 4:
                    tvWednesday.setTextColor(getResources().getColor(R.color.colorWhite));
                    cvWednesday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                    tvWednesdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvWednesdayTimeSlot.setText(timeSlotBuilder.toString());

                    ibtnWedMore.setOnClickListener(mOnMoreClickListener);
                    break;
                case 5:
                    tvThursday.setTextColor(getResources().getColor(R.color.colorWhite));
                    cvThursday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                    tvThursdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvThursdayTimeSlot.setText(timeSlotBuilder.toString());

                    ibtnThurMore.setOnClickListener(mOnMoreClickListener);
                    cvThursday.setOnClickListener(mOnCardViewClickListener);
                    break;
                case 6:
                    tvFriday.setTextColor(getResources().getColor(R.color.colorWhite));
                    cvFriday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                    tvFridayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvFridayTimeSlot.setText(timeSlotBuilder.toString());
                    ibtnFriMore.setOnClickListener(mOnMoreClickListener);
                    cvFriday.setOnClickListener(mOnCardViewClickListener);
                    break;
                case 7:
                    tvSaturday.setTextColor(getResources().getColor(R.color.colorWhite));
                    cvSaturday.setCardBackgroundColor(getResources().getColor(R.color.colorBlue));
                    tvSaturdayTimeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
                    tvSaturdayTimeSlot.setText(timeSlotBuilder.toString());
                    ibtnSatMore.setOnClickListener(mOnMoreClickListener);
                    break;



            }

        }

    }
    private void showMorePopUpMenu(ImageButton v) {
        PopupMenu popupMenu = new PopupMenu(requireActivity(), v);
        Menu menu = popupMenu.getMenu();
        menu.add(0, 1, 0, "Delete");
        popupMenu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) item -> {
            Integer day=(Integer) v.getTag();
            List<Integer> lstOfDeleteDays=new ArrayList<>(1); //empty
            lstOfDeleteDays.add(day);
            DeleteWeekDayScheduleRequest in=new DeleteWeekDayScheduleRequest();
            in.setWeekDays(lstOfDeleteDays);
            itemToDelete=day;
            mViewModel.deleteWeekDaySchedule(mHeaderMap,in);

            return false;
        });


        popupMenu.show();


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
