package com.telemed.doctor.splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telemed.doctor.R;
import com.telemed.doctor.TeleMedApplication;
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;

import static com.telemed.doctor.helper.SharedPrefHelper.KEY_SIGN_IN;


public class SplashFragment extends BaseFragment {
    private static final int SPLASH_TIME_OUT = 5000;
    private RouterFragmentSelectedListener mFragmentListener;
    private SharedPrefHelper mSharedPrefHelper;
    private boolean isUserSignIn;

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
        mSharedPrefHelper = ((TeleMedApplication) context.getApplicationContext()).getSharedPrefInstance();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isUserSignIn = mSharedPrefHelper.read(KEY_SIGN_IN, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHandler.sendEmptyMessageDelayed(1, SPLASH_TIME_OUT);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {


            if (mFragmentListener != null) {

                if (!isUserSignIn) {
                    mFragmentListener.showFragment("SignInFragment", null);
                } else {
                    mFragmentListener.startActivity("HomeActivity", null);
                }

            }

        }
    };


    @Override
    public void onDestroy() {
        mHandler.removeMessages(1);
        super.onDestroy();
    }
}
