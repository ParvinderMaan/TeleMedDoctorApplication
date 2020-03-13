package com.telemed.doctor.signin.view;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.base.BaseTextWatcher;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.signin.viewmodel.SignInViewModel;
import com.telemed.doctor.signin.model.SignInRequest;
import com.telemed.doctor.signin.model.SignInResponse;
import com.telemed.doctor.signup.model.UserInfoWrapper;
import com.telemed.doctor.util.CustomAlertTextView;


public class SignInFragment extends BaseFragment {
    private final String DEVICE_TYPE = "android"; // ios
    private final String DISCRIMINATOR_TYPE = "Doctor"; // Patient

    private AppCompatEditText edtUsrEmail, edtUsrPassword;
    private TextView tvSignUp, tvForgotPassword;
    private RelativeLayout rlRoot;
    private Button btnSignIn;
    private SignInViewModel mViewModel;
    private ProgressBar progressBar;
    private RouterFragmentSelectedListener mFragmentListener;
    private String mUserEmail, mUserPassword;
    private CustomAlertTextView tvAlertView;
    private AppCompatTextView tvEmail;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeOne);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        return localInflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        initView(v);
        initListener();
        initObserver();
    }

    private void initObserver() {
        mViewModel.getProgress()
                .observe(getViewLifecycleOwner(), isLoading -> progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE));


        mViewModel.getViewEnabled()
                .observe(getViewLifecycleOwner(), isView -> {
                    edtUsrEmail.setEnabled(isView);
                    edtUsrPassword.setEnabled(isView);
                    tvSignUp.setEnabled(isView);
                    tvForgotPassword.setEnabled(isView);
                    btnSignIn.setEnabled(isView);
                });



        mViewModel.getResultant().observe(getViewLifecycleOwner(), response -> {

            switch (response.getStatus()) {
                case SUCCESS:
                    if (response.getData() != null) {
                        SignInResponse.Data infoObj = response.getData().getData(); // adding Additional Info
                        infoObj.setEmail(mUserEmail);
                        tvAlertView.showTopAlert(response.getData().getMessage());
                        tvAlertView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                        edtUsrEmail.setText("");edtUsrPassword.setText("");
                        if (mFragmentListener != null)
                            routeNavigationFragment(infoObj);
                    }
                    break;

                case FAILURE:
                    if (response.getErrorMsg() != null) {
                        tvAlertView.showTopAlert(response.getErrorMsg());
                    }
                    break;

            }

        });

    }

    private void routeNavigationFragment(SignInResponse.Data infoObj) {
        UserInfoWrapper infoWrapper = new UserInfoWrapper();
        infoWrapper.setAccessToken(infoObj.getAccessToken());
        infoWrapper.setEmail(infoObj.getEmail());
        infoWrapper.setEmailConfirmed(infoObj.getEmailConfirmed());
        infoWrapper.setLastScreenId(infoObj.getLastScreenId());
        infoWrapper.setFirstName(infoObj.getFirstName());
        infoWrapper.setLastName(infoObj.getLastName());
        infoWrapper.setProfilePic(infoObj.getProfilePic());

        switch (infoObj.getLastScreenId()) {

            case 1: // ---> 2
                mFragmentListener.showFragment("OneTimePasswordFragment", infoWrapper);
                break;
            case 2: // ---> 3
                mFragmentListener.showFragment("SignUpIIFragment", infoWrapper);
                break;
            case 3:  // ---> 4
                mFragmentListener.showFragment("SignUpIIIFragment", infoWrapper);
                break;
            case 4:  // ---> 5
                mFragmentListener.showFragment("SignUpIVFragment", infoWrapper);
                break;
            case 5:  // ---> 6
                mFragmentListener.showFragment("SignUpVFragment", infoWrapper);
                break;
            default:  // ---> default
                mFragmentListener.startActivity("HomeActivity", infoWrapper);

        }

    }


    private void initView(View v) {
        rlRoot = v.findViewById(R.id.rl_root);
        progressBar = v.findViewById(R.id.progress_bar);
        edtUsrEmail = v.findViewById(R.id.edt_usr_email);
        edtUsrPassword = v.findViewById(R.id.edt_usr_password);
        tvSignUp = v.findViewById(R.id.tv_sign_up);
        tvForgotPassword = v.findViewById(R.id.tv_forgot_password);
        btnSignIn = v.findViewById(R.id.btn_sign_in);
        progressBar.setVisibility(View.INVISIBLE);
        tvAlertView = v.findViewById(R.id.tv_alert);

//        tvEmail = v.findViewById(R.id.tv_email);

    }

    private void initListener() {
        tvSignUp.setOnClickListener(mOnClickListener);
        tvForgotPassword.setOnClickListener(mOnClickListener);
        btnSignIn.setOnClickListener(mOnClickListener);

        edtUsrEmail.setOnEditorActionListener(mEditorActionListener);
        edtUsrPassword.setOnEditorActionListener(mEditorActionListener);



//        edtUsrEmail.addTextChangedListener(new BaseTextWatcher() {
//            @Override
//            public void onTextChanged(int start, int before, int count, CharSequence s) {
//                if(s.length()==0){
//                    tvEmail.setText("");
//                    return;
//                }
//                if (!Validator.isEmailValid(String.valueOf(s))) {
//                    tvEmail.setText("Invalid email");
//                }else {
//                    tvEmail.setText("");
//                }
//            }
//        });


    }


    private View.OnClickListener mOnClickListener = v -> {
        switch (v.getId()) {

            case R.id.tv_sign_up:

                if (mFragmentListener != null)
                    mFragmentListener.showFragment("SignUpIFragment", null);

                break;

            case R.id.tv_forgot_password:

                if (mFragmentListener != null)
                    mFragmentListener.showFragment("ForgotPasswordFragment", null);
                break;

            case R.id.btn_sign_in:
                attemptSignIn();
                break;
        }
    };

    private void attemptSignIn() {

        if (isFormValid()) {
            SignInRequest in = new SignInRequest.Builder()
                    .setEmail(mUserEmail)
                    .setPassword(mUserPassword)
                    .setDeviceType(DEVICE_TYPE)
                    .setDeviceId("12345")
                    .setDiscriminator(DISCRIMINATOR_TYPE)
                    .build();
            mViewModel.setSignInInfo(in);
            edtUsrEmail.clearFocus();
            edtUsrPassword.clearFocus();
            mViewModel.attemptSignIn();
        }

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

    private EditText.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {

                case R.id.edt_usr_email:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (edtUsrEmail.isFocused()) edtUsrPassword.requestFocus();
                        return true;
                    }
                    break;


                case R.id.edt_usr_password:
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        mFragmentListener.hideSoftKeyboard();
                        attemptSignIn();
                        return true;
                    }

                    break;
            }

            return false;
        }
    };

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }


        /*


        {
    "status": true,
    "message": "Please Confirm Your Email to continue.",
    "data": {
        "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySWQiOiI2ZDA1M2VhZC05OTA5LTQwZDAtODgwYy02YzViODk4YzZiNzUiLCJyb2xlIjoiRG9jdG9yIiwiRGV2aWNlSWQiOiJ0ZXN0IERldmljZUlEIiwibmJmIjoxNTc2MDQ2OTUzLCJleHAiOjE1NzYxMzMzNTMsImlhdCI6MTU3NjA0Njk1M30.AE8wPmMKk-m7PduK3U-tr-EL65OUNzo0jT6GDiSQJbQ",
        "lastScreenId": 1,
        "emailConfirmed": false
       }
      }
         */
    }

    @Override
    public void onDestroyView() {
        releaseResources();
        super.onDestroyView();
    }

    private void releaseResources() {
        tvSignUp.setOnClickListener(null);
        tvForgotPassword.setOnClickListener(null);
        btnSignIn.setOnClickListener(null);
        edtUsrEmail.setOnEditorActionListener(null);
        edtUsrPassword.setOnEditorActionListener(null);
    }
}
