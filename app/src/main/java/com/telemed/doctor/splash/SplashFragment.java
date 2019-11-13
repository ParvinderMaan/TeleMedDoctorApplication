package com.telemed.doctor.splash;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.telemed.doctor.R;
import com.telemed.doctor.RouterActivity;


public class SplashFragment extends Fragment {
    private static final int SPLASH_TIME_OUT = 5000;

    public static SplashFragment  newInstance() {
        return new SplashFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Toast.makeText(getActivity(), ""+"onViewCreated", Toast.LENGTH_SHORT).show();
        mHandler.sendEmptyMessageDelayed(1, SPLASH_TIME_OUT);
    }


    @Override
    public void onDestroyView() {
        mHandler.removeMessages(1);
//        Toast.makeText(getActivity(), ""+"onDestroyView", Toast.LENGTH_SHORT).show();
        super.onDestroyView();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {

            if(getActivity()!=null)
               ((RouterActivity)getActivity()).showSignInFragment();


        }
    };


}
