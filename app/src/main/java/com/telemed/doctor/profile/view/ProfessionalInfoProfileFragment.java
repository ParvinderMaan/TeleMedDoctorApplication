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
import android.widget.ProgressBar;

import com.telemed.doctor.R;
import com.telemed.doctor.profile.model.ProfessionalInfoResponse;
import com.telemed.doctor.profile.viewmodel.ProfessionalInfoProfileViewModel;


public class ProfessionalInfoProfileFragment extends Fragment {
    private AppCompatEditText edtMedicalDegree,edtMdWhere,edtOtherDegree,
            edtOmdWhere,edtDeaNo,edtNpiNo;
    private ProfessionalInfoProfileViewModel mViewModel;
    private ProgressBar progressBar;

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
        mViewModel = ViewModelProviders.of(this).get(ProfessionalInfoProfileViewModel.class);
        initView(v);

        mViewModel.fetchProfessionalInfo();


        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        ProfessionalInfoResponse.Data infoObj = response.getData().getData();
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

    }



    private void initView(View v){
        edtMedicalDegree=v.findViewById(R.id.edt_medical_degree);
        edtMdWhere=v.findViewById(R.id.edt_md_where);
        edtOtherDegree=v.findViewById(R.id.edt_other_degree);
        edtOmdWhere=v.findViewById(R.id.edt_omd_where);
        edtDeaNo=v.findViewById(R.id.edt_dea_no);
        edtNpiNo=v.findViewById(R.id.edt_npi_no);

        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void setView(ProfessionalInfoResponse.Data info) {
        edtMedicalDegree.setText(info.getMedicalDegree()!=null?info.getMedicalDegree():"");
        edtMdWhere.setText(info.getDegreeFrom()!=null?info.getDegreeFrom():"");
        edtOtherDegree.setText(info.getOtherDegree()!=null?info.getOtherDegree():"");
        edtOmdWhere.setText(info.getOtherDegreeFrom()!=null?info.getOtherDegreeFrom():"");
        edtDeaNo.setText(info.getDeaNumber()!=null?info.getDeaNumber():"");
        edtNpiNo.setText(info.getNpiNumber()!=null?info.getNpiNumber():"");
    }
}
