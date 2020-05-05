package com.telemed.doctor.schedule.view;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.telemed.doctor.schedule.viewmodel.EditAvailabilityViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EditAvailabilityFragment extends Fragment {
    private EditAvailabilityViewModel mViewModel;
    private FloatingActionButton fbtnAddAvailability;
    private RecyclerView rvTimeSlot;
    private DayAvailabilityAdapter mAdapter;
    private ImageButton ibtnClose, ibtnBack;
    private CustomAlertTextView tvAlertView;
    private ProgressBar progressBar;
    private HomeFragmentSelectedListener mFragmentListener;
    private SharedPrefHelper mHelper;
    private String mAccessToken,selectedDate;
    private HashMap<String, String> mHeaderMap;
    private TextView tvDateSelected;


    public static EditAvailabilityFragment newInstance(Object payload) {
        EditAvailabilityFragment fragment = new EditAvailabilityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("KEY_", (String) payload);
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
        super.onCreate(savedInstanceState);
        mAdapter=new DayAvailabilityAdapter();
        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);
        // collect our intent
        if (getArguments() != null) {
            selectedDate = getArguments().getString("KEY_");
             Log.e("EditAvailFragment", selectedDate);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_edit_availability, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(EditAvailabilityViewModel.class);
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();
        initRecyclerView(v);
        initObserver();

        mViewModel.fetchDaySchedule(mHeaderMap,selectedDate); //"2020-05-08"


        try {
            tvDateSelected.setText(convert(selectedDate));
        } catch (ParseException e) {
            tvDateSelected.setText(selectedDate);
        }
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
            List<DayAvailabilityModel> lstOfPresviousSlot = mViewModel.getAllDaySchedule().getValue();

            List<DayScheduleRequest.TimeSlot> lstOfTimeSlots=new ArrayList<>();
            DayScheduleRequest.TimeSlot timeSlot=new DayScheduleRequest.TimeSlot();
            timeSlot.setToTime(endTime);
            timeSlot.setFromTime(startTime);
            timeSlot.setTimeDiff(15);
            lstOfTimeSlots.add(timeSlot);
            for(int i=0;i<lstOfPresviousSlot.size();i++){
                DayScheduleRequest.TimeSlot timeSlott=new DayScheduleRequest.TimeSlot();
                timeSlott.setToTime(lstOfPresviousSlot.get(i).getTimeTo());
                timeSlott.setFromTime(lstOfPresviousSlot.get(i).getTimeFrom());
                timeSlott.setTimeDiff(lstOfPresviousSlot.get(i).getTimeDiff());
                lstOfTimeSlots.add(timeSlott);
            }

            //call api....
            List<String> lstOfDates=new ArrayList<>(1);
            lstOfDates.add(formatDate(selectedDate));

            List<Integer> lstOfDays=new ArrayList<>(1);
            lstOfDays.add(0);



            DayScheduleRequest in=new DayScheduleRequest();
            in.setIsAvailable(true);
            in.setAvailableDates(lstOfDates);
            in.setAvailableDays(lstOfDays);
            in.setTimeSlots(lstOfTimeSlots);

            mViewModel.createDaySchedule(mHeaderMap,in);

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

    private void showTimePicker(DayAvailabilityModel model) {
        TimePickerDialogFragment mDialogFragment = TimePickerDialogFragment.newInstance();
        mDialogFragment.setOnTimePickerDialogFragmentListener((startTime, endTime) -> {
            //call api....
            EditDayScheduleRequest in=new EditDayScheduleRequest();
            in.setTimeFrom(startTime);
            in.setTimeTo(endTime);
            in.setTimeDiff(15);
            mViewModel.editDaySchedule(mHeaderMap,in,model.getId());

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

    private DayAvailabilityAdapter.OnItemClickListener mOnItemClickListener=new DayAvailabilityAdapter.OnItemClickListener() {
        @Override
        public void onItemEditClick(DayAvailabilityModel model, int position) {
             showTimePicker(model);
        }

        @Override
        public void onItemDeleteClick(DayAvailabilityModel model, int position) {
            mViewModel.deleteDaySchedule(mHeaderMap,model.getId());
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
                        DayScheduleResponse.Data infoObj = response.getData().getData();
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

        mViewModel.getAllDaySchedule().observe(getViewLifecycleOwner(),lstOfAllDaySchedule->{
            mAdapter.setItems(lstOfAllDaySchedule);
        });

        mViewModel.getResultDeleteSchedule().observe(getViewLifecycleOwner(),response ->{
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        DayScheduleResponse infoObj = response.getData();
                        if (infoObj.getMessage() != null ) {
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            tvAlertView.showTopAlert(infoObj.getMessage());
                            mViewModel.fetchDaySchedule(mHeaderMap,selectedDate); //"2020-05-08"
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
                            mViewModel.fetchDaySchedule(mHeaderMap,selectedDate); //"2020-05-08"
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
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            tvAlertView.showTopAlert(msg);
                            mViewModel.fetchDaySchedule(mHeaderMap,selectedDate);
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
        // System.out.println("Given date is " + dateString);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = sdf.parse(dateString);

        return new SimpleDateFormat("dd  MMMM",Locale.US).format(date);
    }

    public static String convert2(String dateString)  {
        // System.out.println("Given date is " + dateString);

        DateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.US);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new SimpleDateFormat("yyyy-MM-dd",Locale.US).format(date);
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
