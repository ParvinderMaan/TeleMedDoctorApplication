package com.telemed.doctor.consult;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.util.DividerItemDecoration;


public class MyConsultFragment extends Fragment {


    private RecyclerView rvAppointmentsUpcoming,rvAppointmentsHistory;

    public static MyConsultFragment newInstance() {
        return new MyConsultFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_consult, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initUpcomingRecyclerView(v);
        initHistoryAppointmentRecyclerView(v);

        v.findViewById(R.id.ibtn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null)
                ((HomeActivity)getActivity()).popTopMostFragment();
            }
        });

    }



    private void initUpcomingRecyclerView(View v) {
        rvAppointmentsUpcoming =v.findViewById(R.id.rv_upcoming_appointment);
        rvAppointmentsUpcoming.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvAppointmentsUpcoming.setLayoutManager(mLinearLayoutManager);

        AppointmentUpcomingAdapter mAdapter=new AppointmentUpcomingAdapter();
        rvAppointmentsUpcoming.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AppointmentUpcomingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if(getActivity() !=null){
                    ((HomeActivity)getActivity()).showVideoCallTriggerFragment();

                }
            }

            @Override
            public void onItemClickMore(int pos) {

//                if(getActivity() !=null){
//                    ((HomeActivity)getActivity()).showMedicalRecordFragment();
//
//                }
            }
        });



        if(getActivity()!=null) {
            Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider_white);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
            rvAppointmentsUpcoming.addItemDecoration(dividerItemDecoration);
        }

       ViewCompat.setNestedScrollingEnabled(rvAppointmentsUpcoming, false);

    }

    private void initHistoryAppointmentRecyclerView(View v) {
        rvAppointmentsHistory =v.findViewById(R.id.rv_last_appointment);
        rvAppointmentsHistory.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvAppointmentsHistory.setLayoutManager(mLinearLayoutManager);

        AppointmentHistoryAdapter mAdapter=new AppointmentHistoryAdapter();
        rvAppointmentsHistory.setAdapter(mAdapter);

        if(getActivity()!=null) {
            Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider_white);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
            rvAppointmentsHistory.addItemDecoration(dividerItemDecoration);
        }

        ViewCompat.setNestedScrollingEnabled(rvAppointmentsHistory, false);

    }



}
