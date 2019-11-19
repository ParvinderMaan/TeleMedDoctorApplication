package com.telemed.doctor.signin;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.telemed.doctor.Post;
import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.home.HomeActivity;
import com.telemed.doctor.network.ApiResponse;
import com.telemed.doctor.network.ServiceGenerator;
import com.telemed.doctor.network.Status;
import com.telemed.doctor.network.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInFragment extends BaseFragment {

    private AppCompatEditText edtUsrEmail, edtUsrPassword;
    private TextView tvSignUp, tvForgotPassword;
    private Button btnSignIn;
    private String mUserEmail, mUserPassword;
    private SignInViewModel mViewModel;
    private ProgressBar progressBar;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentTheme);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initView(v);
        initListener();


        mViewModel.getProgress()
                .observe(this, isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getShowHomeActivity().observe(this, aBoolean -> {
            if (aBoolean) {

                if (getActivity() != null) {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().finish();
                }
            }
        });


        mViewModel.fetchPost().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                 mViewModel.setProgress(false);
                if(apiResponse.status== Status.SUCCESS){
                    makeToast(""+apiResponse.data.toString());


                }else if(apiResponse.status==Status.ERROR){

                    makeToast(""+apiResponse.data.toString());

                }

            }
        });


    }


    private void initView(View v) {
        progressBar = v.findViewById(R.id.progress_bar);
        edtUsrEmail = v.findViewById(R.id.edt_usr_email);
        edtUsrPassword = v.findViewById(R.id.edt_usr_password);
        tvSignUp = v.findViewById(R.id.tv_sign_up);
        tvForgotPassword = v.findViewById(R.id.tv_forgot_password);
        btnSignIn = v.findViewById(R.id.btn_sign_in);

        progressBar.setVisibility(View.INVISIBLE);

    }

    private void initListener() {
        tvSignUp.setOnClickListener(mOnClickListener);
        tvForgotPassword.setOnClickListener(mOnClickListener);
        btnSignIn.setOnClickListener(mOnClickListener);
    }


    private View.OnClickListener mOnClickListener = v -> {
        switch (v.getId()) {

            case R.id.tv_sign_up:

                if (getActivity() != null)
                    ((RouterActivity) getActivity()).showSignUpIFragment();
                break;

            case R.id.tv_forgot_password:

                if (getActivity() != null)
                    ((RouterActivity) getActivity()).showForgotPasswordFragment();
                break;

            case R.id.btn_sign_in:

                if (!isNetAvail()) {
                    makeToast("no internet");
                    return;
                }





                if (isFormValid()) {
                    attemptSignIn();
//                    mViewModel.attemptSignIn();
                }
                break;
        }
    };

    private void attemptSignIn() {

        mViewModel.setProgress(true);
        mHandler.sendEmptyMessageDelayed(1, 3000);


    }

    @Override
    public void onDestroyView() {
        mOnClickListener = null;
        super.onDestroyView();
    }

    private boolean isFormValid() {

        mUserEmail = edtUsrEmail.getText().toString();
        mUserPassword = edtUsrPassword.getText().toString();

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


        if (mUserPassword.isEmpty()) {
            edtUsrPassword.setError("Enter Password");
            return false;

        }
        if (mUserPassword.contains(" ")) {
            edtUsrPassword.setError("No Spaces Allowed");
            return false;

        }

        return true;
    }


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mViewModel.setProgress(false);
            if (getActivity() != null) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().finish();
            }
//---------------------------------------------------------------

//---------------------------------------------------------------

        }
    };
}
