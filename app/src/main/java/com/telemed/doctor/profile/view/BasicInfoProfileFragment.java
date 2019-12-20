package com.telemed.doctor.profile.view;


import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.profile.model.BasicInfoResponse;
import com.telemed.doctor.profile.viewmodel.BasicInfoProfileViewModel;

import java.util.HashMap;


public class BasicInfoProfileFragment extends Fragment {
    private AppCompatEditText edtDocName, edtDocSurname, edtDob, edtBirthCity, edtBirthCountry, edtSpeciality,
            edtLanguageOne, edtLanguageTwo, edtAddr, edtEmail, edtNationality , edtCountry, edtState, edtCity;
    private BasicInfoProfileViewModel mViewModel;
    private ProgressBar progressBar;
    private Button btnEditSave;


    public BasicInfoProfileFragment() {
        // Required empty public constructor
    }

    public static BasicInfoProfileFragment newInstance() {
        return new BasicInfoProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic_info_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BasicInfoProfileViewModel.class);

        initView(v);


        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        BasicInfoResponse.Data infoObj = response.getData().getData();
                        setView(infoObj);
                    }

                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                       // tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;

            }


        });

        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.fetchBasicInfo();

    }

    private void setView(BasicInfoResponse.Data info) {
        // setting up
        edtDocName.setText(info.getFirstName() != null ? info.getFirstName() : "");
        edtDocSurname.setText(info.getLastName() != null ? info.getLastName() : "");
        edtDob.setText(info.getDateOfBirth() != null ? info.getDateOfBirth() : "");
        edtBirthCity.setText(info.getBirthCity() != null ? info.getBirthCity() : "");
        edtBirthCountry.setText(info.getBirthCountry() != null ? info.getBirthCountry() : "");
        edtSpeciality.setText(info.getSpeciality() != null ? info.getSpeciality() : "");
        edtLanguageOne.setText(info.getPrimaryLanguage() != null ? info.getPrimaryLanguage() : "");
        edtLanguageTwo.setText(info.getSecondaryLanguage() != null ? info.getSecondaryLanguage() : "");
        edtNationality.setText(info.getNationality() != null ? info.getNationality() : "");
        edtCountry.setText(info.getCountryName() != null ? info.getCountryName() : "");
        edtState.setText(info.getStateName() != null ? info.getStateName() : "");
        edtCity.setText(info.getCity() != null ? info.getCity() : "");
        edtAddr.setText(info.getAddress1() != null ? info.getCity() : "");
        edtEmail.setText(info.getEmail()!=null?info.getEmail():"");


        //----------
        if (getParentFragment() != null) {
            ((ProfileFragment)getParentFragment()).updateUi(info);
        }

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
        edtNationality = v.findViewById(R.id.edt_nationality);
        edtCountry = v.findViewById(R.id.edt_country);
        edtState = v.findViewById(R.id.edt_state);
        edtCity = v.findViewById(R.id.edt_city);


        edtAddr = v.findViewById(R.id.edt_addr);
        edtEmail = v.findViewById(R.id.edt_email);
        btnEditSave = v.findViewById(R.id.btn_edit_save);

        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        btnEditSave.setOnClickListener(vv->{


        });

    }
}
