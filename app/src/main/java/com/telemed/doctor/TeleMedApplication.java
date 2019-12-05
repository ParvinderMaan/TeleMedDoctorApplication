package com.telemed.doctor;

import android.app.Application;

import com.telemed.doctor.network.ServiceGenerator;
import com.telemed.doctor.network.WebService;

import java.io.File;

//import leakcanary.AppWatcher;
//import leakcanary.LeakCanary;
//import leakcanary.ObjectWatcher;

public class TeleMedApplication extends Application {
    private static TeleMedApplication currentApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        currentApplication = this;


    }

    // region Helper Methods
    public static TeleMedApplication getInstance() {
        return currentApplication;
    }

    public static File getCacheDirectory()  {
        return currentApplication.getCacheDir();
    }


    public WebService getRetrofitInstance() {
        return ServiceGenerator.createService(WebService.class);

    }
}
