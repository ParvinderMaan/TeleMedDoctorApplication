package com.telemed.doctor;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telemed.doctor.home.HomeActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorDocumentFragment extends Fragment {


    public static DoctorDocumentFragment newInstance() {
        return new DoctorDocumentFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doctor_document, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        v.findViewById(R.id.ibtn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null)
                    ((HomeActivity)getActivity()).popTopMostFragment();
            }
        });


        TextView btnMedicalRecord = v.findViewById(R.id.btn_more);
        btnMedicalRecord.setOnClickListener(v1 -> {


            if (getActivity() != null)
                ((HomeActivity) getActivity()).showMedicalRecordFragment();

        });
    }
}
