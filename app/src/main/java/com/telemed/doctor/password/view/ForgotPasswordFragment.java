package com.telemed.doctor.password.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.password.viewmodel.ForgotPasswordViewModel;


public class ForgotPasswordFragment extends Fragment {


    private TextView tvCancel;
    private RouterFragmentSelectedListener mFragmentListener;
    private ForgotPasswordViewModel mViewModel;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
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
    }
}
