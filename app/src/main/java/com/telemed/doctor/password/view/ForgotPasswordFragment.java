package com.telemed.doctor.password.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.password.viewmodel.ForgotPasswordViewModel;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;


public class ForgotPasswordFragment extends BaseFragment {

    private ProgressBar progressBar;
    private CustomAlertTextView tvAlertView;
    private TextView tvCancel;
    private RouterFragmentSelectedListener mFragmentListener;
    private ForgotPasswordViewModel mViewModel;
    private RelativeLayout rlRoot;
    private AppCompatEditText edtUsrEmail;
    private Button btnSend;
    private String mUsrEmail;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
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
        return localInflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);

        initView(v);
        initListener();

        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {
            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        if (mFragmentListener != null) {
                            tvAlertView.showTopAlert(response.getData().getMessage());
                            tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                            UserInfoWrapper in=new UserInfoWrapper();
                            in.setEmail(mUsrEmail);
                            in.setOtpCode(response.getData().getData().getOtpCode());
                            mFragmentListener.showFragment("ResetPasswordFragment",in);
                        }
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;

            }

        });


        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));

        mViewModel.getViewClickable()
                .observe(getViewLifecycleOwner(), isView -> rlRoot.setClickable(isView));


    }

    private void initListener() {
        btnSend.setOnClickListener(v1 -> {

            if (isFormValid()) {
                edtUsrEmail.clearFocus();
                mViewModel.attemptForgotPassword(mUsrEmail);
            }


        });

        tvCancel.setOnClickListener(v1 -> {
            if (mFragmentListener != null)
                mFragmentListener.popTopMostFragment();
        });

        edtUsrEmail.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO){
                if (isFormValid()) {
                    mFragmentListener.hideSoftKeyboard();
                    edtUsrEmail.clearFocus();
                    mViewModel.attemptForgotPassword(mUsrEmail);

                }
            }
            return false;
        });
    }

    private void initView(View v) {

        rlRoot = v.findViewById(R.id.rl_root);
        tvCancel = v.findViewById(R.id.tv_cancel);
        btnSend = v.findViewById(R.id.btn_send);
        edtUsrEmail = v.findViewById(R.id.edt_user_email);



        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorBlue), android.graphics.PorterDuff.Mode.SRC_IN);

        tvAlertView = v.findViewById(R.id.tv_alert_view);


    }
    private boolean isFormValid() {
        mUsrEmail = edtUsrEmail.getText().toString();

        if (TextUtils.isEmpty(mUsrEmail)) {
            edtUsrEmail.setError("Enter Email address");
            return false;
        }

        if (mUsrEmail.contains(" ")) {
            edtUsrEmail.setError("No Spaces Allowed");
            return false;
        }

        if (!Validator.isEmailValid(mUsrEmail)) {
            edtUsrEmail.setError("Enter valid email address");
            return false;
        }



        return true;
    }

}
/*

{
  "status": false,
  "message": "This Email is not registered with us.",
  "data": null
}

{
  "status": true,
  "message": "OTP has been sent on your email. Please Check your email.",
  "data": {
    "otpCode": 8992
  }
}



 */