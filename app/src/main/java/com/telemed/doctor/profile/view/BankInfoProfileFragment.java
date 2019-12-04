package com.telemed.doctor.profile.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;


public class BankInfoProfileFragment extends Fragment {
    private AppCompatEditText edtRoutingNumber, edtAccountNumber, edtCity, edtPostCode;
    private String mRoutingNumber,mAccountNumber,mCity,mPostCode;

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
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);

    }

    private void initView(View v) {
        edtRoutingNumber= v.findViewById(R.id.edt_routing_number);
        edtAccountNumber= v.findViewById(R.id.edt_account_number);
        edtCity = v.findViewById(R.id.edt_city);
        edtPostCode = v.findViewById(R.id.edt_post_code);
    }
}
