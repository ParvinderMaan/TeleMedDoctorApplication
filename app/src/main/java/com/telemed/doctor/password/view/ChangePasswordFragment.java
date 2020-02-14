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

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.HomeFragmentSelectedListener;
import com.telemed.doctor.password.viewmodel.ChangePasswordViewModel;


public class ChangePasswordFragment extends Fragment {


    private HomeFragmentSelectedListener mFragmentListener;
    private ChangePasswordViewModel mViewModel;

    public ChangePasswordFragment() {
        // Required empty public
    }

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (HomeFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel.class);
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        v.findViewById(R.id.ibtn_close).setOnClickListener(v1 -> {
            if(mFragmentListener!=null)
                mFragmentListener.popTopMostFragment();

        });
    }

}
