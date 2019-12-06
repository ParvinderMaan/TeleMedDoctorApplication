package com.telemed.doctor.signin;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.telemed.doctor.R;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.Validator;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;
import com.telemed.doctor.util.CustomAlertTextView;


public class SignInFragment extends BaseFragment {
    private AppCompatEditText edtUsrEmail, edtUsrPassword;
    private TextView tvSignUp, tvForgotPassword;
    private RelativeLayout rlRoot;
    private Button btnSignIn;
    private SignInViewModel mViewModel;
    private ProgressBar progressBar;
    private RouterFragmentSelectedListener mFragmentListener;
    private String mUserEmail, mUserPassword;
    private CustomAlertTextView tvAlert;

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
        mViewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.FragmentThemeOne);
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

                if (mFragmentListener != null) {
                    mFragmentListener.startActivity("HomeActivity", null );
                    getActivity().finish();
                }
            }
        });


//        mViewModel.fetchPost().observe(this, new Observer<ApiResponse>() {
//            @Override
//            public void onChanged(ApiResponse apiResponse) {
//                 mViewModel.setProgress(false);
//                if(apiResponse.status== Status.SUCCESS){
//                    makeToast(""+apiResponse.data.toString());
//
//
//                }else if(apiResponse.status==Status.ERROR){
//
//                    makeToast(""+apiResponse.data.toString());
//
//                }
//
//            }
//        });




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


        tvAlert = v.findViewById(R.id.tv_alert);



    }

    private void initListener() {
        tvSignUp.setOnClickListener(mOnClickListener);
        tvForgotPassword.setOnClickListener(mOnClickListener);
        btnSignIn.setOnClickListener(mOnClickListener);

        edtUsrEmail.setOnEditorActionListener(mEditorActionListener);
        edtUsrPassword.setOnEditorActionListener(mEditorActionListener);
    }


    private View.OnClickListener mOnClickListener = v -> {
        switch (v.getId()) {

            case R.id.tv_sign_up:

                if (mFragmentListener != null)
                    mFragmentListener.showFragment("SignUpIFragment", null);
                break;

            case R.id.tv_forgot_password:

                if (mFragmentListener != null)
                    mFragmentListener.showFragment("ForgotPasswordFragment",null );
                break;

            case R.id.btn_sign_in:

                if (!isNetAvail()) {
                    tvAlert.showTopAlert("no internet");
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
        mEditorActionListener=null;
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
//            if (mFragmentListener!= null) {
//                mFragmentListener.startActivity("HomeActivity");
//                getActivity().finish();
//            }


//---------------------------------------------------------------

//---------------------------------------------------------------

        }
    };


    private EditText.OnEditorActionListener mEditorActionListener=new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (v.getId()) {

                case R.id.edt_usr_email:
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if(edtUsrEmail.isFocused()) edtUsrPassword.requestFocus();
                        return true;
                    }
                    break;


                case R.id.edt_usr_password:
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if(edtUsrPassword.isFocused()) edtUsrPassword.clearFocus(); hideSoftKeyboard(getActivity());
                        return true;
                    }

                    break;



            }


            return false;
        }
    };

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() !=null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

//    private void hideViews() {
//        tvAlert.animate().translationY(-tvAlert.getHeight()).setInterpolator(new AccelerateInterpolator(1));
//
////        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
////        int fabBottomMargin = lp.bottomMargin;
////        mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
//    }
//
//    private void showViews() {
//        tvAlert.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
////        mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//    }

    // To animate view slide out from top to bottom
    public void slideToBottom(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0,0,0,0);
        animate.setDuration(2000);
        animate.setFillAfter(false);
        view.startAnimation(animate);
        view.setVisibility(View.INVISIBLE);
    }
}
