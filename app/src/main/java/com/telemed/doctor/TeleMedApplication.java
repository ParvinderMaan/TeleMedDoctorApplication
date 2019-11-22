package com.telemed.doctor;

import android.app.Application;

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
//implementation 'pub.devrel:easypermissions:3.0.0'

//        ObjectWatcher mObjectWatcher= AppWatcher.INSTANCE.getObjectWatcher();
//        AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = false)

    }

    // region Helper Methods
    public static TeleMedApplication getInstance() {
        return currentApplication;
    }

    public static File getCacheDirectory()  {
        return currentApplication.getCacheDir();
    }


    public void helpMe() {


    }
}
