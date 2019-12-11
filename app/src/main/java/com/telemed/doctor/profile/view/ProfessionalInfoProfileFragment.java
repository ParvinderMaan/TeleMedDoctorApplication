package com.telemed.doctor.profile.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;


public class ProfessionalInfoProfileFragment extends Fragment {
    private AppCompatEditText edtMedicalDegree,edtMdWhere,edtOtherDegree,
            edtOmdWhere,edtDeaNo,edtNpiNo;

    public ProfessionalInfoProfileFragment() {
        // Required empty public constructor
    }

    public static ProfessionalInfoProfileFragment newInstance() {
        return new ProfessionalInfoProfileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_professional_info_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);

    }

    private void initView(View v){
        edtMedicalDegree=v.findViewById(R.id.edt_medical_degree);
        edtMdWhere=v.findViewById(R.id.edt_md_where);
        edtOtherDegree=v.findViewById(R.id.edt_other_degree);
        edtOmdWhere=v.findViewById(R.id.edt_omd_where);
        edtDeaNo=v.findViewById(R.id.edt_dea_no);
        edtNpiNo=v.findViewById(R.id.edt_npi_no);


    }
}
