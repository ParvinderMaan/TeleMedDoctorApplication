package com.telemed.doctor.schedule;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentConfirmIFragment extends Fragment {



    public static AppointmentConfirmIFragment newInstance() {
        return new AppointmentConfirmIFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment_confirm_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        TextView tvConfirm = v.findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(v1 -> {


//            if (getActivity() != null)
//                ((HomeActivity) getActivity()).showPatientGalleryFragment();

        });



        TextView btnMedicalRecord = v.findViewById(R.id.btn_medical_record);
        btnMedicalRecord.setOnClickListener(v1 -> {


            if (getActivity() != null)
                ((HomeActivity) getActivity()).showMedicalRecordFragment();

        });


        v.findViewById(R.id.ibtn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null)
                    ((HomeActivity)getActivity()).popTopMostFragment();
            }
        });
    }


}
