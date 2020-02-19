package com.telemed.doctor.schedule.view;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.schedule.viewmodel.DayWiseAvailabiltyViewModel;
import com.telemed.doctor.schedule.model.TimeSlotModel;
import com.telemed.doctor.signup.model.UserInfoWrapper;

import java.util.ArrayList;
import java.util.List;


public class DayWiseAvailabilityFragment extends Fragment {

    private DayWiseAvailabiltyViewModel mViewModel;
    private RecyclerView rvTimeSlot;
    private ImageButton ibtnClose;
    private HomeFragmentSelectedListener mFragmentListener;

    public static DayWiseAvailabilityFragment newInstance(Object payload) {
        DayWiseAvailabilityFragment fragment = new DayWiseAvailabilityFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY_", (UserInfoWrapper) payload); // SignUpIResponse.Data
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_wise_availability, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DayWiseAvailabiltyViewModel.class);
        initRecyclerView(v);

        ibtnClose=v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {
            if(mFragmentListener!=null) mFragmentListener.popTopMostFragment();
        });


    }

    private void initRecyclerView(View v) {
        rvTimeSlot=v.findViewById(R.id.rv_time_slot);
        DayWiseAvailabilityAdapter mAdapter = new DayWiseAvailabilityAdapter();
        mAdapter.setOnItemClickListener(position -> {
            if(mFragmentListener!=null) mFragmentListener.showFragment("AppointmentConfirmIFragment",null);

        });
        mAdapter.setItems(fetchTimeSlot());
        rvTimeSlot.setHasFixedSize(true);
        rvTimeSlot.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rvTimeSlot.setAdapter(mAdapter);

    }


    public  List<TimeSlotModel>   fetchTimeSlot() {
        List<TimeSlotModel> lstOfAvailableSlot=new ArrayList<>();
        //String id, int status, String firmName, String timeSlot, String patientName
        lstOfAvailableSlot.add(new TimeSlotModel("1",0,"Infinity Doc","8:00 / 8:30","John Ali"));
        lstOfAvailableSlot.add(new TimeSlotModel("2",0,"Infinity Doc","8:30 / 9:00","Mic Laki"));
        lstOfAvailableSlot.add(new TimeSlotModel("3",1,"Infinity Doc","9:00 / 9:30","Ahil Ali"));
        lstOfAvailableSlot.add(new TimeSlotModel("4",1,"Infinity Doc","10:00 / 10:30","John Ali"));
        lstOfAvailableSlot.add(new TimeSlotModel("5",2,"Infinity Doc","10:30 / 11:00","John Ali"));
        lstOfAvailableSlot.add(new TimeSlotModel("5",2,"Infinity Doc","10:30 / 11:00","John Ali"));

        return lstOfAvailableSlot;
    }





}
