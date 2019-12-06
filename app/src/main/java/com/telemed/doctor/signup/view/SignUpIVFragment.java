package com.telemed.doctor.signup.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpIVFragment extends Fragment {


    private RouterFragmentSelectedListener mFragmentListener;
    private Button btnContinue;
    private TextView tvCancel;
    private AppCompatEditText edtRoutingNumber, edtAccountNumber, edtCity, edtPostCode;
    private String mRoutingNumber,mAccountNumber,mCity,mPostCode;
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
        initView(v);
        initListener(v);


    }

    private void initListener(View v) {

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFragmentListener != null)
                    mFragmentListener.showFragment("SignUpVFragment", null);

            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFragmentListener != null)
                    mFragmentListener.abortSignUp();

            }
        });
    }

    private void initView(View v) {
        btnContinue = v.findViewById(R.id.btn_continue);
        tvCancel = v.findViewById(R.id.tv_cancel);

        edtRoutingNumber= v.findViewById(R.id.edt_routing_number);
        edtAccountNumber= v.findViewById(R.id.edt_account_number);
        edtCity = v.findViewById(R.id.edt_city);
        edtPostCode = v.findViewById(R.id.edt_post_code);
    }

    private boolean isFormValid() {
        mRoutingNumber=edtRoutingNumber.getText().toString();
        mAccountNumber=edtAccountNumber.getText().toString();
        mCity=edtCity.getText().toString();
        mPostCode=edtPostCode.getText().toString();



        if (TextUtils.isEmpty(mRoutingNumber)) {
            edtRoutingNumber.setError("Enter Routing number");
            return false;
        }

        if (mRoutingNumber.contains(" ")) {
            edtRoutingNumber.setError("No Spaces Allowed");
            return false;
        }

        if (TextUtils.isEmpty(mAccountNumber)) {
            edtAccountNumber.setError("Enter account number");
            return false;
        }

        if (mAccountNumber.contains(" ")) {
            edtAccountNumber.setError("No Spaces Allowed");
            return false;
        }


        if (TextUtils.isEmpty(mCity)) {
            edtCity.setError("Enter City");
            return false;
        }

        if (mCity.contains(" ")) {
            edtCity.setError("No Spaces Allowed");
            return false;
        }


        if (TextUtils.isEmpty(mPostCode)) {
            edtPostCode.setError("Enter post code");
            return false;
        }

        if (mPostCode.contains(" ")) {
            edtPostCode.setError("No Spaces Allowed");
            return false;
        }



        return true;
    }
}
