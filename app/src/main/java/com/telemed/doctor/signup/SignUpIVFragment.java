package com.telemed.doctor.signup;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpIVFragment extends Fragment {


    private RouterFragmentSelectedListener mFragmentListener;

    public SignUpIVFragment() {
        // Required empty public constructor
    }

    public static SignUpIVFragment newInstance() {
        return new SignUpIVFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_iv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);



        v.findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                       if(mFragmentListener!=null)
                           mFragmentListener.showFragment("SignUpVFragment");

            }
        });

        v.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mFragmentListener!=null)
                    mFragmentListener.popTopMostFragment();

            }
        });


    }
}
