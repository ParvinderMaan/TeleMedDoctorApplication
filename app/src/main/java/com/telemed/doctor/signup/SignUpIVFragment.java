package com.telemed.doctor.signup;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpIVFragment extends Fragment {


    public SignUpIVFragment() {
        // Required empty public constructor
    }

    public static SignUpIVFragment newInstance() {
        return new SignUpIVFragment();
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

                       if(getActivity()!=null)
                           ((RouterActivity)getActivity()).showSignUpVFragment();

            }
        });
    }
}
