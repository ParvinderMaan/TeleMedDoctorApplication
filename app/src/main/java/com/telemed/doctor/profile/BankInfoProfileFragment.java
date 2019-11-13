package com.telemed.doctor.profile;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankInfoProfileFragment extends Fragment {


    public BankInfoProfileFragment() {
        // Required empty public constructor
    }

    public static BankInfoProfileFragment newInstance() {
        return new BankInfoProfileFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bank_info_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String a="This is my first code testing commit";
    }
}
