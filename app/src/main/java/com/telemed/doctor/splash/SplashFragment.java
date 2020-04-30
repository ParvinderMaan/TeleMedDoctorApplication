package com.telemed.doctor.splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

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
import java.util.TimeZone;

import static com.telemed.doctor.helper.SharedPrefHelper.KEY_SIGN_IN;


public class SplashFragment extends BaseFragment {
    private static final int SPLASH_TIME_OUT = 3000;
    private RouterFragmentSelectedListener mFragmentListener;
    private SharedPrefHelper mSharedPrefHelper;
    private boolean isUserSignIn;
    private WeakHandler mWeakHandler;
    private SplashViewModel mViewModel;

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
        mViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        super.onViewCreated(view, savedInstanceState);
        mWeakHandler=new WeakHandler(this);
        mWeakHandler.sendEmptyMessageDelayed(1, SPLASH_TIME_OUT);
        initObsever();
        /*
               check googleplay services .....
         */
    }

    private void initObsever() {

        mViewModel.getShowActivity()
                .observe(getViewLifecycleOwner(),
                        showActivity -> mFragmentListener.startActivity(showActivity, null));

        mViewModel.getShowFragment()
                .observe(getViewLifecycleOwner(),
                        showFragment ->mFragmentListener.showFragment(showFragment, null));

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
//                    fragment.get().mFragmentListener.showFragment("SignInFragment", null);
                    fragment.get().mViewModel.showFragment("SignInFragment");
                } else {
//                    fragment.get().mFragmentListener.startActivity("HomeActivity", null);
                    fragment.get().mViewModel.showActivity("HomeActivity");
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        if(mWeakHandler!=null) mWeakHandler.removeMessages(1);
        super.onDestroyView();
    }


}
