package com.telemed.doctor.medicalrecord;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;

public class MedicalRecordFragment extends Fragment {
    private RecyclerView rvPatientDrug,rvMedicalHistory,rvCurrentMedication;
    private ImageButton ibtnClose;


    public static MedicalRecordFragment newInstance() {
        return new MedicalRecordFragment() ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_medical_record, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initDrugRecyclerView(v);
        initMedicalHistoryRecyclerView(v);
        initCurrentMedicationRecyclerView(v);

        ibtnClose=(ImageButton)v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {

            if(getActivity()!=null){
                ((HomeActivity)getActivity()).popTopMostFragment();
            }
        });


    }

    private void initCurrentMedicationRecyclerView(View v) {
        rvCurrentMedication =v.findViewById(R.id.rv_current_medication);
        rvCurrentMedication.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvCurrentMedication.setLayoutManager(mLinearLayoutManager);

        CurrentMedicationAdapter mAdapter=new CurrentMedicationAdapter();
        rvCurrentMedication.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new AppointmentUpcomingAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                if(getActivity() !=null){
//                    ((HomeActivity)getActivity()).showVideoCallTriggerFragment();
//
//                }
//            }
//        });





        ViewCompat.setNestedScrollingEnabled(rvCurrentMedication, false);
    }

    private void initMedicalHistoryRecyclerView(View v) {
        rvMedicalHistory =v.findViewById(R.id.rv_medical_history);
        rvMedicalHistory.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvMedicalHistory.setLayoutManager(mLinearLayoutManager);

        MedicalHistoryAdapter mAdapter=new MedicalHistoryAdapter();
        rvMedicalHistory.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new AppointmentUpcomingAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                if(getActivity() !=null){
//                    ((HomeActivity)getActivity()).showVideoCallTriggerFragment();
//
//                }
//            }
//        });





        ViewCompat.setNestedScrollingEnabled(rvMedicalHistory, false);

    }

    private void initDrugRecyclerView(View v) {
        rvPatientDrug =v.findViewById(R.id.rv_patient_drug);
        rvPatientDrug.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvPatientDrug.setLayoutManager(mLinearLayoutManager);

        AllergyDrugAdapter mAdapter=new AllergyDrugAdapter();
        rvPatientDrug.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new AppointmentUpcomingAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                if(getActivity() !=null){
//                    ((HomeActivity)getActivity()).showVideoCallTriggerFragment();
//
//                }
//            }
//        });





        ViewCompat.setNestedScrollingEnabled(rvPatientDrug, false);

    }
}
