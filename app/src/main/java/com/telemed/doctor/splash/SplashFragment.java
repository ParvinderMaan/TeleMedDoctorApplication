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

import java.lang.ref.WeakReference;

import static com.telemed.doctor.helper.SharedPrefHelper.KEY_SIGN_IN;


public class SplashFragment extends BaseFragment {
    private static final int SPLASH_TIME_OUT = 3000;
    private RouterFragmentSelectedListener mFragmentListener;
    private SharedPrefHelper mSharedPrefHelper;
    private boolean isUserSignIn;
    private WeakHandler mWeakHandler;

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
        mWeakHandler=new WeakHandler(this);
        mWeakHandler.sendEmptyMessageDelayed(1, SPLASH_TIME_OUT);

    }

    static class WeakHandler extends Handler {
        private WeakReference<SplashFragment> fragment;

         WeakHandler(SplashFragment fragment) {
            this.fragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (fragment.get().mFragmentListener != null) {

                if (!fragment.get().isUserSignIn) {
                    fragment.get().mFragmentListener.showFragment("SignInFragment", null);
                } else {
                    fragment.get().mFragmentListener.startActivity("HomeActivity", null);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        mWeakHandler.removeMessages(1);
        super.onDestroyView();
    }


}
