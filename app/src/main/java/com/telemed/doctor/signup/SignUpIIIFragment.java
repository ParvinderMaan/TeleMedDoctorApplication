package com.telemed.doctor.signup;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;


public class SignUpIIIFragment extends Fragment {


    private TextView tvCancel;
    private Button btnContinue;
    private RouterFragmentSelectedListener mFragmentListener;

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
        tvCancel=v.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mFragmentListener!=null)
                    mFragmentListener.popTopMostFragment();


            }
        });

        btnContinue =v.findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(mFragmentListener!=null)
                     mFragmentListener.showFragment("SignUpIVFragment");



            }
        });


    }
}
