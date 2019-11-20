package com.telemed.doctor.consult;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.telemed.doctor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryOptionsBottomSheetFragment extends BottomSheetDialogFragment {


    private LinearLayout llRateAppointment,llPrescribeAppointment;

    public HistoryOptionsBottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_sheet_history_option,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);



    }

    private void initView(View v) {
        llRateAppointment=v.findViewById(R.id.ll_rate_the_appointment);
        llPrescribeAppointment=v.findViewById(R.id.ll_precribe_the_appointment);




        llRateAppointment.setOnClickListener(v12 -> {

            if(getParentFragment()!=null){

                ((MyConsultFragment)getParentFragment()).showFragment("TAG_RATE_APPOINTMENT");
            }


        });
        llPrescribeAppointment=v.findViewById(R.id.ll_precribe_the_appointment);
        llPrescribeAppointment.setOnClickListener(v1 -> {
            if(getParentFragment()!=null){

                ((MyConsultFragment)getParentFragment()).showFragment("TAG_PRESCRIBE_PATIENT");
            }

        });


    }
}
