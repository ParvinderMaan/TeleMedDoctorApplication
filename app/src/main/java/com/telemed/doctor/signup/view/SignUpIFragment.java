package com.telemed.doctor.signup.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.Status;
import com.telemed.doctor.signup.model.SignUpIRequest;
import com.telemed.doctor.signup.model.SignUpIResponse;
import com.telemed.doctor.signup.viewmodel.SignUpIViewModel;
import com.telemed.doctor.util.CustomAlertTextView;


public class SignUpIFragment extends BaseFragment {
    private final String TAG = SignUpIFragment.class.getSimpleName();
    private AppCompatEditText edtUsrEmail, edtUsrPassword, edtUsrConfirmPassword;
    private TextView tvCancel;
    private AppCompatButton btnContinue;
    private RouterFragmentSelectedListener mFragmentListener;
    private ProgressBar progressBar;
    private SignUpIViewModel mViewModel;
    private CustomAlertTextView tvAlertView;
    private String mUserEmail, mUserPassword, mUserConfirmPassword;

    public static SignUpIFragment newInstance() {
        return new SignUpIFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SignUpIViewModel.class);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_sign_up_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();

        mViewModel.getResultant().observe(this, signUpResponse -> {

            switch (signUpResponse.getStatus()) {
                case SUCCESS:
                    if (signUpResponse.getData() != null) {
                        if (mFragmentListener != null)
                            mFragmentListener.showFragment("OneTimePasswordFragment", null);
                    }

                    break;

                case FAILURE:
                    if (signUpResponse.getData() != null) {
                        tvAlertView.setText(signUpResponse.getData().getMessage());
                    }
                    break;

                case ERROR:
                    if (signUpResponse.getError() != null) {
                        tvAlertView.setText(signUpResponse.getError());
                    }
                    break;


            }

        });

        mViewModel.getProgress()
                .observe(this, isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

    }

    private void initListener() {
        tvCancel.setOnClickListener(mClickListener);
        btnContinue.setOnClickListener(mClickListener);

        edtUsrEmail.setOnEditorActionListener(mEditorActionListener);
        edtUsrPassword.setOnEditorActionListener(mEditorActionListener);
        edtUsrConfirmPassword.setOnEditorActionListener(mEditorActionListener);
    }

    private void initView(View v) {
        edtUsrEmail = v.findViewById(R.id.edt_email);
        edtUsrPassword = v.findViewById(R.id.edt_password);
        edtUsrConfirmPassword = v.findViewById(R.id.edt_confirm_password);
        btnContinue = v.findViewById(R.id.btn_continue);
        tvCancel = v.findViewById(R.id.tv_cancel);
        progressBar = v.findViewById(R.id.progress_bar);
        tvAlertView = v.findViewById(R.id.tv_alert_view);
        // @initialization
        progressBar.setVisibility(View.INVISIBLE);


    }

    private boolean isFormValid() {
        mUserEmail = edtUsrEmail.getText().toString();
        mUserPassword = edtUsrPassword.getText().toString();
        mUserConfirmPassword = edtUsrConfirmPassword.getText().toString();


        if (TextUtils.isEmpty(mUserEmail)) {
            edtUsrEmail.setError("Enter Email address");
            return false;
        }

        if (mUserEmail.contains(" ")) {
            edtUsrEmail.setError("No Spaces Allowed");
            return false;
        }

        if (!Validator.isEmailValid(mUserEmail)) {
            edtUsrEmail.setError("Enter valid email address");
            return false;
        }


        if (TextUtils.isEmpty(mUserPassword)) {
            edtUsrPassword.setError("Enter password");
            return false;
        }

        if (mUserPassword.contains(" ")) {
            edtUsrPassword.setError("No Spaces Allowed");
            return false;
        }

        if (!(mUserPassword.length() >= 6)) {
            edtUsrPassword.setError("Password is short");
            return false;
        }

        if (!Validator.isAlphaNumeric(mUserPassword)) {
            edtUsrPassword.setError("Password must be alphanumeric");
            return false;
        }


        if (TextUtils.isEmpty(mUserConfirmPassword)) {
            edtUsrConfirmPassword.setError("Enter confirm password");
            return false;
        }

        if (mUserConfirmPassword.contains(" ")) {
            edtUsrConfirmPassword.setError("No Spaces Allowed");
            return false;
        }

        if (!mUserConfirmPassword.contentEquals(mUserPassword)) {
            edtUsrConfirmPassword.setError("Your password is not matched");
            return false;
        }

        return true;
    }


    private View.OnClickListener mClickListener = v -> {

        switch (v.getId()) {

            case R.id.tv_cancel:
                if (mFragmentListener != null)
                    mFragmentListener.popTopMostFragment();
                break;


            case R.id.btn_continue:

                if (!isNetAvail()) {
                    tvAlertView.showTopAlert("No Internet");
                    return;
                }


                if (isFormValid()) {
                    SignUpIRequest in = new SignUpIRequest.Builder()
                            .setEmail(mUserEmail)
                            .setPassword(mUserPassword)
                            .setConfirmPassword(mUserConfirmPassword)
                            .build();
                    Log.e(TAG,in.toString());
                    mViewModel.attemptSignUp(in);


                }

                break;

        }


    };

//    private void attemptSignUp() {
//        if (mFragmentListener != null)
//            mFragmentListener.showFragment("OneTimePasswordFragment",null );
//
//    }

    private EditText.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {

                case R.id.edt_email:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (edtUsrEmail.isFocused()) edtUsrPassword.requestFocus();
                        return true;
                    }
                    break;


                case R.id.edt_password:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (edtUsrPassword.isFocused()) edtUsrConfirmPassword.requestFocus();
                        return true;
                    }

                    break;

                case R.id.edt_confirm_password:
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (edtUsrConfirmPassword.isFocused()) {
                            edtUsrConfirmPassword.clearFocus();
                            mFragmentListener.hideSoftKeyboard();
                        }
                        return true;
                    }

                    break;

            }


            return false;
        }
    };

    @Override
    public void onDestroyView() {
        releaseResource();
        super.onDestroyView();
    }

    private void releaseResource() {
        tvCancel.setOnClickListener(null);
        btnContinue.setOnClickListener(null);
        edtUsrEmail.setOnEditorActionListener(null);
        edtUsrPassword.setOnEditorActionListener(null);
        edtUsrConfirmPassword.setOnEditorActionListener(null);
        mClickListener = null;
        mEditorActionListener = null;
    }


    public void setViewState(boolean b) {
        edtUsrEmail.setEnabled(b);
        edtUsrPassword.setEnabled(b);
        edtUsrConfirmPassword.setEnabled(b);
        btnContinue.setEnabled(b);

    }


    /*
    failure  ---->
    {
  "status": false,
  "data": {},
  "message": "PasswordRequiresNonAlphanumeric"
}

    success ----->
{
  "status": true,
  "data": {},
  "message": "You are registered Successfully.",
  "otpCode": 9913,
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOiIzMTMwYWI5Mi1iZTcyLTQxNGItYWY3OS1jZjQ3ODFiMWYzM2MiLCJyb2xlIjoiRG9jdG9yIiwiRGV2aWNlSWQiOiJ0ZXN0IERldmljZUlEIiwibmJmIjoxNTc1NTQ0MzA2LCJleHAiOjE1NzU2MzA3MDYsImlhdCI6MTU3NTU0NDMwNn0.Hy9HcI-y5IMdPpoapSSamrjiq7Oxz3fsD5qh4apFMZg"
}
     */


    /*
     {"status":true,"data":{},"message":"You are registered Successfully.","otpCode":8297,"accessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOiI2ZDA1M2VhZC05OTA5LTQwZDAtODgwYy02YzViODk4YzZiNzUiLCJyb2xlIjoiRG9jdG9yIiwiRGV2aWNlSWQiOiJ0ZXN0IERldmljZUlEIiwibmJmIjoxNTc1NTU0ODI4LCJleHAiOjE1NzU2NDEyMjgsImlhdCI6MTU3NTU1NDgyOH0._ZdYkK2V8es6HxRZ6WjRRH2Er8zpsBvA8kuq_Hy9DYI"}
     */
}
