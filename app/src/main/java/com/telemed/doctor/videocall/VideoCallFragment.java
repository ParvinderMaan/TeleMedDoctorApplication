package com.telemed.doctor.videocall;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;


public class VideoCallFragment extends Fragment {


    public static VideoCallFragment newInstance() {
        return new VideoCallFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_call, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        v.findViewById(R.id.ibtn_star_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getActivity() !=null)
                    ((HomeActivity)getActivity()).showDoctorDocumentFragment();

            }
        });


        v.findViewById(R.id.ibtn_star_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getActivity() !=null)
                    ((HomeActivity)getActivity()).showAppointmentSummaryFragment();

            }
        });

        v.findViewById(R.id.ibtn_star_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getActivity() !=null)
                    ((HomeActivity)getActivity()).showPatientGalleryFragment();

            }
        });


    }


}
