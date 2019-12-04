package com.telemed.doctor.signup.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;


public class SignUpIFragment extends BaseFragment {
    private AppCompatEditText edtUsrEmail, edtUsrPassword, edtUsrConfirmPassword;
    private TextView tvCancel;
    private AppCompatButton btnContinue;
    private RouterFragmentSelectedListener mFragmentListener;
    private ProgressBar progressBar;

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
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_sign_up_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();

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
        // @initialization
        progressBar.setVisibility(View.INVISIBLE);


    }

    private boolean isFormValid() {
        String mUserEmail = edtUsrEmail.getText().toString();
        String mUserPassword = edtUsrPassword.getText().toString();
        String mUserConfirmPassword = edtUsrConfirmPassword.getText().toString();


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
                    makeToast("no internet");
                    return;
                }


                if (isFormValid()) {

                    attemptSignUp();

                }

                break;

        }


    };

    private void attemptSignUp() {
        if (mFragmentListener != null)
            mFragmentListener.showFragment("OneTimePasswordFragment",null );

    }

    private EditText.OnEditorActionListener mEditorActionListener=new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {

                case R.id.edt_email:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if(edtUsrEmail.isFocused()) edtUsrPassword.requestFocus();
                        return true;
                    }
                    break;


                case R.id.edt_password:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if(edtUsrPassword.isFocused()) edtUsrConfirmPassword.requestFocus();
                        return true;
                    }

                    break;

                case R.id.edt_confirm_password:
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (edtUsrConfirmPassword.isFocused()){
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
        mEditorActionListener=null;
    }




    public void setViewState(boolean b){
        edtUsrEmail.setEnabled(b);
        edtUsrPassword.setEnabled(b);
        edtUsrConfirmPassword.setEnabled(b);
        btnContinue.setEnabled(b);

    }
}
