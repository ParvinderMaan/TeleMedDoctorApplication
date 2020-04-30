package com.telemed.doctor.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SplashViewModel extends AndroidViewModel {
    private MutableLiveData<String> showFragment;

    public MutableLiveData<String> getShowFragment() {
        return showFragment;
    }

    public void showFragment(String showFragment) {
        this.showFragment.setValue(showFragment);
    }

    public MutableLiveData<String> getShowActivity() {
        return showActivity;
    }

    public void showActivity(String showActivity) {
        this.showActivity.setValue(showActivity);
    }

    private MutableLiveData<String> showActivity;



    public SplashViewModel(@NonNull Application application) {
        super(application);
        showFragment = new MutableLiveData<>();
        showActivity= new MutableLiveData<>();
    }




}