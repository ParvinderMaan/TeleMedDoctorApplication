package com.telemed.doctor;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentSummaryFragment extends Fragment {


   // private HomeFragmentSelectedListener mFragmentListener;

    public static AppointmentSummaryFragment newInstance() {
       return new AppointmentSummaryFragment();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
     //   mFragmentListener = (HomeFragmentSelectedListener) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

         v.findViewById(R.id.btn_finish).setOnClickListener(v1 -> {
//                 if (mFragmentListener != null)
//                     mFragmentListener.showFragment("PatientRatingFragment", null);
             ((SecondaryActivity)requireActivity()).showFragmentPatientRating();
         });

    }
}
