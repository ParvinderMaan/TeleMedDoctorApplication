package com.telemed.doctor.consult;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.util.DividerItemDecoration;


public class MyConsultFragment extends Fragment {

    private PopupMenu mPopupMenu;

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

        v.findViewById(R.id.ibtn_close).setOnClickListener(v1 -> {
            if(getActivity()!=null)
            ((HomeActivity)getActivity()).popTopMostFragment();
        });

    }



    private void initUpcomingRecyclerView(View v) {
        rvAppointmentsUpcoming =v.findViewById(R.id.rv_upcoming_appointment);
        rvAppointmentsUpcoming.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvAppointmentsUpcoming.setLayoutManager(mLinearLayoutManager);
        AppointmentUpcomingAdapter mAdapter=new AppointmentUpcomingAdapter( getActivity());
        rvAppointmentsUpcoming.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AppointmentUpcomingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if(getActivity() !=null){
                    ((HomeActivity)getActivity()).showVideoCallTriggerFragment();

                }
            }

            @Override
            public void onItemClickMore(String tag, int pos) {
                                       if(getActivity() !=null) ((HomeActivity)getActivity()).showChatFragment();

//               switch (tag){
//
//
//                   case "TAG_CHAT":
//                       if(getActivity() !=null) ((HomeActivity)getActivity()).showChatFragment();
//                       break;
//                   case "TAG_GALLERY":
//                       if(getActivity() !=null) ((HomeActivity)getActivity()).showPatientGalleryFragment();
//
//                       break;
//                   case "TAG_MEDICAL_RECORD":
//                       if(getActivity() !=null) ((HomeActivity)getActivity()).showMedicalRecordFragment();
//
//                       break;
//               }

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
