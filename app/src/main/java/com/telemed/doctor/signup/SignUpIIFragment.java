package com.telemed.doctor.signup;


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


public class SignUpIIFragment extends Fragment {
    private Button btnContinue;
    private TextView tvCancel;

    public SignUpIIFragment() {

    }

    public static SignUpIIFragment newInstance() {
        return new SignUpIIFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        btnContinue=v.findViewById(R.id.btn_continue);
        tvCancel=v.findViewById(R.id.tv_cancel);

        btnContinue.setOnClickListener(mClickListener);
        tvCancel.setOnClickListener(mClickListener);
    }


    private View.OnClickListener mClickListener= v -> {
         switch (v.getId()){

             case R.id.tv_cancel:
                 if(getActivity()!=null)
                     ((RouterActivity)getActivity()).popTopMostFragment();
                 break;

             case R.id.btn_continue:
                 if(getActivity()!=null)
                     ((RouterActivity)getActivity()).showSignUpIIIFragment();
                 break;
         }


    };
}
