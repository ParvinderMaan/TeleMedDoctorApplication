package com.telemed.doctor.signup.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;


public class SignUpIIIFragment extends BaseFragment {
    private AppCompatEditText edtMedicalDegree, edtMdWhere, edtOtherDegree, edtMdOtrWhere, edtDea, edtNpiNo;
    private TextView tvCancel;
    private Button btnContinue;
    private RouterFragmentSelectedListener mFragmentListener;
    private AppCompatCheckBox cboxReadAndAccept;
    private String mMedicalDegree, mMdWhere, mOtherDegree, mMdOtrWhere, mDea, mNpiNo;
    private boolean isReadAndAccept;


    public SignUpIIIFragment() {
        // Required empty public constructor
    }

    public static SignUpIIIFragment newInstance() {
        return new SignUpIIIFragment();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener(v);








    }

    private void initListener(View v) {
        tvCancel.setOnClickListener(v1 -> {

            if(mFragmentListener!=null)
                mFragmentListener.popTopMostFragment();


        });

        btnContinue.setOnClickListener(v12 -> {

            if(mFragmentListener!=null)
                mFragmentListener.showFragment("SignUpIVFragment",null );



        });

    }

    private void initView(View v) {
        tvCancel=v.findViewById(R.id.tv_cancel);
        btnContinue =v.findViewById(R.id.btn_continue);

        edtMedicalDegree=v.findViewById(R.id.edt_medical_degree);
        edtMdWhere=v.findViewById(R.id.edt_md_where);
        edtOtherDegree=v.findViewById(R.id.edt_other_degree);
        edtMdOtrWhere=v.findViewById(R.id.edt_md_otr_where);
        edtDea=v.findViewById(R.id.edt_dea);
        edtNpiNo=v.findViewById(R.id.edt_npi_no);
        cboxReadAndAccept=v.findViewById(R.id.cbox_read_and_accept);

//----------------------------------------------------------------

    }


    private boolean isFormValid() {

        mMedicalDegree=edtMedicalDegree.getText().toString();
        mMdWhere=edtMdWhere.getText().toString();;
//        mOtherDegree=edtOtherDegree.getText().toString();;
//        mMdOtrWhere=edtMdOtrWhere.getText().toString();;
        mDea=edtDea.getText().toString();;
        mNpiNo=edtNpiNo.getText().toString();;
        isReadAndAccept=cboxReadAndAccept.isChecked();


        if (TextUtils.isEmpty(mMedicalDegree)) {
            edtMedicalDegree.setError("Enter medical degree");
            return false;
        }

        if (mMedicalDegree.contains(" ")) {
            edtMedicalDegree.setError("No Spaces Allowed");
            return false;
        }

        if (TextUtils.isEmpty(mMdWhere)) {
            edtMdWhere.setError("Enter medical degree place");
            return false;
        }

        if (mMdWhere.contains(" ")) {
            edtMdWhere.setError("No Spaces Allowed");
            return false;
        }


        if (TextUtils.isEmpty(mDea)) {
            edtDea.setError("Enter Dea");
            return false;
        }

        if (mDea.contains(" ")) {
            edtDea.setError("No Spaces Allowed");
            return false;
        }


        if (TextUtils.isEmpty(mNpiNo)) {
            edtNpiNo.setError("Enter Npi");
            return false;
        }

        if (mNpiNo.contains(" ")) {
            edtNpiNo.setError("No Spaces Allowed");
            return false;
        }

        if (!isReadAndAccept) {
            makeToast("Please read the terms and conditions");
            return false;
        }

        return true;
    }
}
