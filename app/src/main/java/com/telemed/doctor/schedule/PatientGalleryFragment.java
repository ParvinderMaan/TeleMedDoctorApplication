package com.telemed.doctor.schedule;


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
import com.telemed.doctor.appointment.PatientGalleryAdapter;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;


public class PatientGalleryFragment extends Fragment {


    private RecyclerView rvPatientImages;
    private ImageButton ibtnClose;
    private HomeFragmentSelectedListener mFragmentListener;

    public PatientGalleryFragment() {
    }

    public static PatientGalleryFragment newInstance() {
        return new PatientGalleryFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patient_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initRecyclerView(v);

        ibtnClose=v.findViewById(R.id.ibtn_close);
        ibtnClose.setOnClickListener(v1 -> {

            if(getActivity()!=null){
                mFragmentListener.popTopMostFragment();
            }


        });
    }


    private void initRecyclerView(View v) {
        rvPatientImages=v.findViewById(R.id.rv_patient_images);
        rvPatientImages.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(getActivity());
        rvPatientImages.setLayoutManager(mLinearLayoutManager);

        PatientGalleryAdapter mAdapter=new PatientGalleryAdapter();
        rvPatientImages.setAdapter(mAdapter);


    }

}
