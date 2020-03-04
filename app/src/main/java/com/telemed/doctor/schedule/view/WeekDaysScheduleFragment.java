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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.dialog.TimePickerDialogFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
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
    private RecyclerView rvSchedule;
    private HomeFragmentSelectedListener mFragmentListener;
    private ImageButton ibtnClose;
    private FloatingActionButton fbtnAddSchedule;
    private int sun = 0, mon = 0, tue = 0, wed = 0, thu = 0, fri = 0, sat = 0;
    private List<Integer> arrDaySelected=new ArrayList<>();
    private String startTimeSelected,endTimeSelected;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private String mAccessToken;
    private WeekDayScheduleAdapter mAdapter;
    private int itemToDeletePos;
    private HashMap<String, String> mHeaderMap;
    private TextView tvEmptyView;

    public static WeekDaysScheduleFragment newInstance(Object payload) {
        WeekDaysScheduleFragment fragment = new WeekDaysScheduleFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("KEY_", (UserInfoWrapper) payload); // SignUpIResponse.Data
//        fragment.setArguments(bundle);
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
        mHeaderMap.put("Authorization","Bearer "+mAccessToken);
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
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WeekDaysScheduleViewModel.class);
        initRecyclerView(v);

        tvEmptyView=v.findViewById(R.id.tv_empty_view);


        ibtnClose=v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {
            if(mFragmentListener!=null) mFragmentListener.popTopMostFragment();
        });

        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);

        progressBar.setVisibility(View.INVISIBLE);

        fbtnAddSchedule=v.findViewById(R.id.fbtn_add_schedule);
        fbtnAddSchedule.setOnClickListener(v1 -> {

           addScheduleAlert();

        });


        initObserver();

        mViewModel.fetchWeekSchedules(mHeaderMap);
    }

    private void initRecyclerView(View v) {
        rvSchedule=v.findViewById(R.id.rv_schedule);
        mAdapter = new WeekDayScheduleAdapter();
        mAdapter.setOnItemClickListener((model, pos) -> {

            itemToDeletePos=pos;
            mViewModel.deleteWeekDaySchedule(mHeaderMap,model.getId());


        });
        rvSchedule.setHasFixedSize(true);
        rvSchedule.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvSchedule.setAdapter(mAdapter);




    }


    private void addScheduleAlert() {

        final Dialog dialog = new Dialog(requireActivity(), R.style.MyAlertDialogTheme);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setContentView(R.layout.dialog_add_schedule);
        final TextView mTvSun = dialog.findViewById(R.id.tv_sun);
        final TextView mTvMon = dialog.findViewById(R.id.tv_mon);
        final TextView mTvTue = dialog.findViewById(R.id.tv_tue);
        final TextView mTvWed = dialog.findViewById(R.id.tv_wed);
        final TextView mTvThu = dialog.findViewById(R.id.tv_thu);
        final TextView mTvFri = dialog.findViewById(R.id.tv_fri);
        final TextView mTvSat = dialog.findViewById(R.id.tv_sat);

        mTvSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sun==1) {
                    sun = 0;
                    mTvSun.setSelected(false);
                    mTvSun.setTextColor(getResources().getColor(R.color.colorBlue));
                } else {
                    sun = 1;
                    mTvSun.setSelected(true);
                    mTvSun.setTextColor(getResources().getColor(R.color.colorWhite));
                }


            }
        });
        mTvMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mon==1) {
                    mon = 0;
                    mTvMon.setSelected(false);
                    mTvMon.setTextColor(getResources().getColor(R.color.colorBlue));

                } else {
                    mon = 1;
                    mTvMon.setSelected(true);
                    mTvMon.setTextColor(getResources().getColor(R.color.colorWhite));
                }


            }
        });
        mTvTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tue==1) {
                    tue = 0;
                    mTvTue.setSelected(false);
                    mTvTue.setTextColor(getResources().getColor(R.color.colorBlue));
                } else {
                    tue = 1;
                    mTvTue.setSelected(true);
                    mTvTue.setTextColor(getResources().getColor(R.color.colorWhite));
                }


            }
        });
        mTvWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wed==1) {
                    wed = 0;
                    mTvWed.setSelected(false);
                    mTvWed.setTextColor(getResources().getColor(R.color.colorBlue));
                } else {
                    wed = 1;
                    mTvWed.setSelected(true);
                    mTvWed.setTextColor(getResources().getColor(R.color.colorWhite));
                }


            }
        });
        mTvThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (thu==1) {
                    thu = 0;
                    mTvThu.setSelected(false);
                    mTvThu.setTextColor(getResources().getColor(R.color.colorBlue));
                } else {
                    thu = 1;
                    mTvThu.setSelected(true);
                    mTvThu.setTextColor(getResources().getColor(R.color.colorWhite));
                }


            }
        });
        mTvFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fri==1) {
                    fri =0;
                    mTvFri.setSelected(false);
                    mTvFri.setTextColor(getResources().getColor(R.color.colorBlue));
                } else {
                    fri = 1;
                    mTvFri.setSelected(true);
                    mTvFri.setTextColor(getResources().getColor(R.color.colorWhite));
                }


            }
        });
        mTvSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sat==1) {
                    sat = 0;
                    mTvSat.setSelected(false);
                    mTvSat.setTextColor(getResources().getColor(R.color.colorBlue));
                } else {
                    sat = 1;
                    mTvSat.setSelected(true);
                    mTvSat.setTextColor(getResources().getColor(R.color.colorWhite));
                }


            }
        });

        TextView tvDone = (TextView) dialog.findViewById(R.id.tv_done);
        TextView tvSelectAll = (TextView) dialog.findViewById(R.id.tv_select_all);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        tvDone.setOnClickListener(view -> {
            arrDaySelected.clear();
            if(sun==1){
                arrDaySelected.add(1);
            }
            if(mon==1){
                arrDaySelected.add(2);
            }
            if(tue==1){
                arrDaySelected.add(3);
            }
            if(wed==1){
                arrDaySelected.add(4);
            }
            if(thu==1){
                arrDaySelected.add(5);
            }
            if(fri==1){
                arrDaySelected.add(6);
            }
            if(sat==1){
                arrDaySelected.add(7);
            }


//       arrDaySelected = new Integer[]{sun, mon, tue, wed, thu, fri, sat};
            dialog.dismiss();
//            getFromTime();
            showStartTimePicker();
        });
        tvSelectAll.setOnClickListener(view -> {

            sun=mon=tue=wed=thu=fri=sat=1;
//            arrDaySelected = new String[]{sun,mon,tue,wed,thu,fri,sat};

            mTvSun.setSelected(true);
            mTvSun.setTextColor(getResources().getColor(R.color.colorWhite));

            mTvMon.setSelected(true);
            mTvMon.setTextColor(getResources().getColor(R.color.colorWhite));

            mTvTue.setSelected(true);
            mTvTue.setTextColor(getResources().getColor(R.color.colorWhite));

            mTvWed.setSelected(true);
            mTvWed.setTextColor(getResources().getColor(R.color.colorWhite));


            mTvThu.setSelected(true);
            mTvThu.setTextColor(getResources().getColor(R.color.colorWhite));


            mTvFri .setSelected(true);
            mTvFri.setTextColor(getResources().getColor(R.color.colorWhite));


            mTvSat.setSelected(true);
            mTvSat.setTextColor(getResources().getColor(R.color.colorWhite));
        });

        dialog.show();
    }

    private void getFromTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(),R.style.PickerStyle, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String fromTime = selectedHour + ":" + selectedMinute;
                Log.e("From: ", fromTime);
                getToTime();
                startTimeSelected=fromTime;
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
        mTimePicker = new TimePickerDialog(requireActivity(),R.style.PickerStyle, (timePicker, selectedHour, selectedMinute) -> {
            String to = selectedHour + ":" + selectedMinute;
            Log.e("To: ", to);
           // setUpRecyclerView();
            endTimeSelected=to;

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


            WeekScheduleRequest in=new WeekScheduleRequest();
            in.setRecSecId(0); // no need only distration
            in.setFromTime(startTimeSelected);
            in.setToTime(endTimeSelected);
            in.setWeekDays(arrDaySelected);
            mViewModel.createWeekDaySchedule(mHeaderMap,in);
           //call apii....

        }, hour, minute, true); // Yes 24 hour time
        //      mTimePicker.setTitle("To");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_title, null);
        mTimePicker.setCustomTitle(dialogView);
        TextView editText = (TextView) dialogView.findViewById(R.id.tv_title);
        editText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        editText.setText("Ending Time :");
        mTimePicker.show();

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
                        if (mFragmentListener != null){
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            mViewModel.fetchWeekSchedules(mHeaderMap);
                            if(mFragmentListener !=null) mFragmentListener.refreshFragment("ScheduleFragment");
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
                        if(infoObj.getScheduleList()!=null ){
                            mViewModel.setScheduleList(infoObj.getScheduleList());
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
                    if(!lstOfSchedules.isEmpty()){
                        mAdapter.setItems(lstOfSchedules);
                        rvSchedule.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.INVISIBLE);
                    }else {
                        rvSchedule.setVisibility(View.INVISIBLE);
                        tvEmptyView.setVisibility(View.VISIBLE);
                    }

                });


        mViewModel.getResultantDeleteSchedule().observe(getViewLifecycleOwner(),response->{
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null){
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                             mAdapter.deleteItem(itemToDeletePos);
                            if(mFragmentListener !=null) mFragmentListener.refreshFragment("ScheduleFragment");


                            if(mAdapter.getItemCount()==0){
                                rvSchedule.setVisibility(View.INVISIBLE);
                                tvEmptyView.setVisibility(View.VISIBLE);
                            }else {
                                rvSchedule.setVisibility(View.VISIBLE);
                                tvEmptyView.setVisibility(View.INVISIBLE);
                            }
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

    private void resetEnableView(Boolean isView) {

    }


    private void showStartTimePicker() {
        TimePickerDialogFragment mDialogFragment = TimePickerDialogFragment.newInstance();
        mDialogFragment.setOnTimePickerDialogFragmentListener((startTime, endTime) -> {
            //call api....
            WeekScheduleRequest in=new WeekScheduleRequest();
            in.setRecSecId(0); // no need only distration !!!!
            in.setFromTime(startTimeSelected);
            in.setToTime(endTimeSelected);
            in.setWeekDays(arrDaySelected);
            mViewModel.createWeekDaySchedule(mHeaderMap,in);
        });

        mDialogFragment.show(getChildFragmentManager(), "TAG");

    }




}
