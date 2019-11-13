package com.telemed.doctor.signup;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;
import com.telemed.doctor.helper.Validator;


public class SignUpIFragment extends Fragment {
    private AppCompatEditText edtUsrEmail, edtUsrPassword, edtUsrConfirmPassword;
    private TextView tvCancel;
    private Button btnContinue;

    public static SignUpIFragment newInstance() {
        return new SignUpIFragment();
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_sign_up_one, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        tvCancel.setOnClickListener(mClickListener);
        btnContinue.setOnClickListener(mClickListener);

    }
    private void initView(View v) {
        edtUsrEmail=v.findViewById(R.id.edt_email);
        edtUsrPassword=v.findViewById(R.id.edt_password);
        edtUsrConfirmPassword=v.findViewById(R.id.edt_confirm_password);
        btnContinue=v.findViewById(R.id.btn_continue);
        tvCancel=v.findViewById(R.id.tv_cancel);
    }
    private boolean isFormValid() {
        String mUserEmail  = edtUsrEmail.getText().toString();
        String mUserPassword  = edtUsrPassword.getText().toString();
        String mUserConfirmPassword  = edtUsrConfirmPassword.getText().toString();




        if(TextUtils.isEmpty(mUserEmail)) {
            edtUsrEmail.setError("Enter Email address");
            return false;
        }

        if (mUserEmail.contains(" ")) {
            edtUsrEmail.setError("No Spaces Allowed");
            return false;
        }

        if(!Validator.isEmailValid(mUserEmail)){
            edtUsrEmail.setError("Enter valid email address");
            return false;
        }




        if(TextUtils.isEmpty(mUserPassword)) {
            edtUsrPassword.setError("Enter password");
            return false;
        }

        if (mUserPassword.contains(" ")) {
            edtUsrPassword.setError("No Spaces Allowed");
            return false;
        }


        if(TextUtils.isEmpty(mUserConfirmPassword)) {
            edtUsrConfirmPassword.setError("Enter confirm password");
            return false;
        }

        if (mUserConfirmPassword.contains(" ")) {
            edtUsrConfirmPassword.setError("No Spaces Allowed");
            return false;
        }

        if(!mUserConfirmPassword.contentEquals(mUserPassword)){
            edtUsrConfirmPassword.setError("Your password is not matched");
            return false;
        }

        return true;
    }


    private View.OnClickListener mClickListener= v -> {

        switch (v.getId()){

            case R.id.tv_cancel:
                if(getActivity()!=null)
                    ((RouterActivity)getActivity()).popTopMostFragment();
                break;


            case R.id.btn_continue:
                if(isFormValid()){
                    if(getActivity()!=null)
                        ((RouterActivity)getActivity()).showSignUpIIFragment();
                }
                break;

        }


    };
}
