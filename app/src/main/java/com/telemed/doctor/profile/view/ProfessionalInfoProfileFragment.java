package com.telemed.doctor.profile.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.profile.model.BankInfoRequest;
import com.telemed.doctor.profile.model.ProfessionalInfoRequest;
import com.telemed.doctor.profile.model.ProfessionalInfoResponse;
import com.telemed.doctor.profile.model.ProfileUpdateResponse;
import com.telemed.doctor.profile.viewmodel.ProfessionalInfoProfileViewModel;


public class ProfessionalInfoProfileFragment extends Fragment {
    private AppCompatEditText edtMedicalDegree,edtMdWhere,edtOtherDegree,
            edtOmdWhere,edtDeaNo,edtNpiNo;
    private AppCompatButton btnEditSave;
    private String mMedicalDegree, mMdWhere, mOtherDegree, mMdOtrWhere, mDea, mNpiNo;

    private ProfessionalInfoProfileViewModel mViewModel;
    private ProgressBar progressBar;
    private String selectorState[]={"Edit","Save"};
    private SwipeRefreshLayout swipeRefreshLayout;

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
        initListener();
        initObserver();
        mViewModel.fetchProfessionalInfo();

    }

    private void initObserver() {

        mViewModel.getFetchResultant().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        ProfessionalInfoResponse.ProfessionalDetail infoObj = response.getData().getData();
                        setView(infoObj);
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        // tvAlertView.showTopAlert(response.getErrorMsg());
                        // handle later...
                    }

                    break;
            }


        });

        mViewModel.getResultantUpdateProfInfo().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        ProfileUpdateResponse infoObj = response.getData();
                        Toast.makeText(requireActivity(), infoObj.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        Toast.makeText(requireActivity(), response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        // tvAlertView.showTopAlert(response.getErrorMsg());
                        // handle later...
                    }
                    break;
            }


        });

        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading ->
//                        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));
        swipeRefreshLayout.setRefreshing(isLoading));


        mViewModel.getEditableView()
                .observe(getViewLifecycleOwner(), this::enableView);


        mViewModel.getEnableView().observe(getViewLifecycleOwner(), isEnable -> {
            btnEditSave.setEnabled(isEnable);
        });
    }


    private void initView(View v){
        edtMedicalDegree=v.findViewById(R.id.edt_medical_degree);
        edtMdWhere=v.findViewById(R.id.edt_md_where);
        edtOtherDegree=v.findViewById(R.id.edt_other_degree);
        edtOmdWhere=v.findViewById(R.id.edt_omd_where);
        edtDeaNo=v.findViewById(R.id.edt_dea_no);
        edtNpiNo=v.findViewById(R.id.edt_npi_no);
        btnEditSave=v.findViewById(R.id.btn_edit_save);
        btnEditSave.setText(selectorState[0]); // by default


        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);

    }

    private void setView(ProfessionalInfoResponse.ProfessionalDetail info) {
        edtMedicalDegree.setText(info.getMedicalDegree()!=null?info.getMedicalDegree():"");
        edtMdWhere.setText(info.getDegreeFrom()!=null?info.getDegreeFrom():"");
        edtOtherDegree.setText(info.getOtherDegree()!=null?info.getOtherDegree():"");
        edtOmdWhere.setText(info.getOtherDegreeFrom()!=null?info.getOtherDegreeFrom():"");
        edtDeaNo.setText(info.getDeaNumber()!=null?info.getDeaNumber():"");
        edtNpiNo.setText(info.getNpiNumber()!=null?info.getNpiNumber():"");
    }


    private boolean isFormValid() {

        mMedicalDegree=edtMedicalDegree.getText().toString();
        mMdWhere=edtMdWhere.getText().toString();
        mOtherDegree=edtOtherDegree.getText().toString().trim();
        mMdOtrWhere=edtOmdWhere.getText().toString().trim();
        mDea=edtDeaNo.getText().toString();
        mNpiNo=edtNpiNo.getText().toString();


        if (TextUtils.isEmpty(mMedicalDegree)) {
            edtMedicalDegree.setError("Enter medical degree");
            return false;
        }

//        if (mMedicalDegree.contains(" ")) {
//            edtMedicalDegree.setError("No Spaces Allowed");
//            return false;
//        }

        if (TextUtils.isEmpty(mMdWhere)) {
            edtMdWhere.setError("Enter medical degree place");
            return false;
        }

//        if (mMdWhere.contains(" ")) {
//            edtMdWhere.setError("No Spaces Allowed");
//            return false;
//        }


        if (TextUtils.isEmpty(mDea)) {
            edtDeaNo.setError("Enter Dea");
            return false;
        }

//        if (mDea.contains(" ")) {
//            edtDea.setError("No Spaces Allowed");
//            return false;
//        }


        if (TextUtils.isEmpty(mNpiNo)) {
            edtNpiNo.setError("Enter Npi");
            return false;
        }

//        if (mNpiNo.contains(" ")) {
//            edtNpiNo.setError("No Spaces Allowed");
//            return false;
//        }


        return true;
    }


    private void initListener() {
        btnEditSave.setOnClickListener(v1 -> {
            String tag= (String) btnEditSave.getText();
            switch (tag){
                case "Edit":
                    mViewModel.setEditableView(true);
                    btnEditSave.setText(selectorState[1]);
                    break;
                case "Save":
//                  mViewModel.setEnableView(false);
                    btnEditSave.setText(selectorState[0]);

                    if(isFormValid()){
                        ProfessionalInfoRequest in=new ProfessionalInfoRequest.Builder()
                              .setMedicalDegree(mMedicalDegree)
                                .setDegreeFrom(mMdWhere)
                                .setOtherDegree(mOtherDegree)
                                .setOtherDegreeFrom(mMdOtrWhere)
                                .setDeaNumber(mDea)
                                .setNpiNumber(mNpiNo)
                                .build();
                        mViewModel.updateProfessionalInfo(in);
                    }

                    break;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mViewModel.fetchProfessionalInfo();

        });
    }


    private void enableView(boolean isEnable) {
        edtMedicalDegree.setEnabled(isEnable);
        edtMdWhere.setEnabled(isEnable);
        edtOtherDegree.setEnabled(isEnable);
        edtOmdWhere.setEnabled(isEnable);
        edtDeaNo.setEnabled(isEnable);
        edtNpiNo.setEnabled(isEnable);


        if(isEnable){
            edtMedicalDegree.setSelection(edtMedicalDegree.getText()!=null?edtMedicalDegree.getText().length():0);
            edtMedicalDegree.requestFocus();
        }
    }
}
