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

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentSummaryFragment extends Fragment {


    public static AppointmentSummaryFragment newInstance() {
       return new AppointmentSummaryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);


         v.findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if (getActivity() != null)
                     ((HomeActivity) getActivity()).showPatientRatingFragment();
             }
         });

    }
}
