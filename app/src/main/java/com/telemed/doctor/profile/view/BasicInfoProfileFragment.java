package com.telemed.doctor.profile.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;
import com.telemed.doctor.profile.viewmodel.BasicInfoProfileViewModel;


public class BasicInfoProfileFragment extends Fragment {
    private AppCompatEditText edtDocName, edtDocSurname, edtDob, edtBirthCity, edtBirthCountry, edtSpeciality,
            edtLanguageOne, edtLanguageTwo, edtAddr, edtEmail;
    private BasicInfoProfileViewModel mViewModel;

    public BasicInfoProfileFragment() {
        // Required empty public constructor
    }

    public static BasicInfoProfileFragment newInstance() {
        return new BasicInfoProfileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(BasicInfoProfileViewModel.class);
        return inflater.inflate(R.layout.fragment_basic_info_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);




    }

    private void initView(View v) {
        edtDocName = v.findViewById(R.id.edt_doc_name);
        edtDocSurname = v.findViewById(R.id.edt_doc_surname);
        edtDob = v.findViewById(R.id.edt_dob);
        edtBirthCity = v.findViewById(R.id.edt_birth_city);
        edtBirthCountry = v.findViewById(R.id.edt_birth_country);
        edtSpeciality = v.findViewById(R.id.edt_speciality);
        edtLanguageOne = v.findViewById(R.id.edt_language_one);
        edtLanguageTwo = v.findViewById(R.id.edt_language_two);
        edtAddr = v.findViewById(R.id.edt_addr);
        edtEmail = v.findViewById(R.id.edt_email);

  // setting up
        edtDocName.setText("");
        edtDocSurname.setText("");
        edtDob.setText("");
        edtBirthCity.setText("");
        edtBirthCountry.setText("");
        edtSpeciality.setText("");
        edtLanguageOne.setText("");
        edtLanguageTwo.setText("");
        edtAddr.setText("");
        edtEmail.setText("");

    }
}
