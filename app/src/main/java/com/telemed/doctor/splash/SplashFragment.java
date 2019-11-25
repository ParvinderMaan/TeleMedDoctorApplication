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
import com.telemed.doctor.base.BaseFragment;
import com.telemed.doctor.interfacor.RouterFragmentSelectedListener;


public class SplashFragment extends BaseFragment {
    private static final int SPLASH_TIME_OUT = 5000;
    private RouterFragmentSelectedListener mFragmentListener;

    public static SplashFragment  newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFragmentListener = (RouterFragmentSelectedListener) context;
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
        mHandler.removeMessages(1);
        super.onDestroyView();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {

            if(mFragmentListener!=null)
                mFragmentListener.showFragment("SignInFragment",null);


        }
    };


}
