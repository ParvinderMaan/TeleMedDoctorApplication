package com.telemed.doctor.schedule.view;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.model.ScheduleTimeSlotResponse;
import com.telemed.doctor.schedule.viewmodel.DayWiseAvailabiltyViewModel;
import com.telemed.doctor.util.CustomAlertTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class DayWiseAvailabilityFragment extends Fragment {
    private final String TAG= DayWiseAvailabilityFragment.class.getSimpleName();

    private DayWiseAvailabiltyViewModel mViewModel;
    private RecyclerView rvTimeSlot;
    private ImageButton ibtnClose,ibtnBack;
    private HomeFragmentSelectedListener mFragmentListener;
    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private TextView tvTimeSlot;
    private DayWiseAvailabilityAdapter mAdapter;
    private String mDateOfAppointment;
    private HashMap<String, String> mHeaderMap;
    private String mAccessToken;
    private SwipeRefreshLayout swipeRefreshLayout;


    public static DayWiseAvailabilityFragment newInstance(Object payload) {
        DayWiseAvailabilityFragment fragment = new DayWiseAvailabilityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("KEY_", (String) payload); // SignUpIResponse.Data
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
        // collect our intent
        if (getArguments() != null) {
             mDateOfAppointment = getArguments().getString("KEY_");
            Log.e(TAG, mDateOfAppointment);
        }

        mHeaderMap = new HashMap<>();
        mHeaderMap.put("content-type", "application/json");
        mHeaderMap.put("Authorization", "Bearer " + mAccessToken);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_day_wise_availability, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(DayWiseAvailabiltyViewModel.class);
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initRecyclerView(v);
        initObserver();

        try {
            tvTimeSlot.setText(convert(mDateOfAppointment));
        } catch (ParseException e) {
         //   e.printStackTrace();
            tvTimeSlot.setText(mDateOfAppointment);
        }

        mViewModel.fetchScheduleTimeSlots(mHeaderMap,mDateOfAppointment);

    }


    private void initView(View v) {
        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);

        tvTimeSlot = v.findViewById(R.id.tv_time_slot);

        progressBar.setVisibility(View.INVISIBLE); // not used here

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.setOnRefreshListener(() -> mViewModel.fetchScheduleTimeSlots(mHeaderMap,mDateOfAppointment));



        ibtnClose=v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTillFragment("HomeFragment",0);
        });

        ibtnBack=v.findViewById(R.id.ibtn_back);
        ibtnBack.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });


    }



    private void initRecyclerView(View v) {
        rvTimeSlot=v.findViewById(R.id.rv_time_slot);
        mAdapter = new DayWiseAvailabilityAdapter();
        mAdapter.setOnItemClickListener((position, model) -> {
          Log.e(TAG,model.toString());
            if(mFragmentListener!=null) {
                mFragmentListener.showFragment("AppointmentConfirmIFragment",model);
            }


        });
        rvTimeSlot.setHasFixedSize(true);
        rvTimeSlot.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvTimeSlot.setAdapter(mAdapter);


    }


    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> swipeRefreshLayout.setRefreshing(isLoading));

        mViewModel.getEnableView()
                .observe(getViewLifecycleOwner(), this::resetEnableView);

        mViewModel.getResultantTimeSlot().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        ScheduleTimeSlotResponse.Data infoObj = response.getData().getData();
                        if (infoObj.getAvailableTimeSlots() != null) {
                            mViewModel.setTimeSlotList(infoObj.getAvailableTimeSlots());
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


        mViewModel.getAllTimeSlot()
                .observe(getViewLifecycleOwner(), lstOfSchedules -> {
                    if (!lstOfSchedules.isEmpty()) {
                        mAdapter.setItems(lstOfSchedules);
                    }
                });


    }

    private void resetEnableView(Boolean isView) {

    }



    public  String convert(String dateString) throws ParseException {
        // System.out.println("Given date is " + dateString);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        Date date = sdf.parse(dateString);

        return new SimpleDateFormat("dd  MMMM",Locale.US).format(date);

    }


    public void refreshUi() {
        mViewModel.fetchScheduleTimeSlots(mHeaderMap,mDateOfAppointment);
        // it means we have to change previous fragment too !!! checky...
        if(mFragmentListener!=null){
            mFragmentListener.refreshFragment("ScheduleFragment");
        }
    }
}
