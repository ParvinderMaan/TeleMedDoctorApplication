package com.telemed.doctor;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.firebase.FirebaseApp;
import com.telemed.doctor.helper.Common;
import com.telemed.doctor.helper.SharedPrefHelper;
import com.telemed.doctor.network.ServiceGenerator;
import com.telemed.doctor.network.WebService;

import java.io.File;



public class TeleMedApplication extends Application  {     //implements LifecycleObserver
    private static TeleMedApplication currentApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        currentApplication = this;
     //  ProcessLifecycleOwner.get().getLifecycle().addObserver(this); // no need
    }

    // region Helper Methods
    public static TeleMedApplication getInstance() {
        return currentApplication;
    }

    public static File getCacheDirectory()  {
        return currentApplication.getCacheDir();
    }

    // @use DI
    public WebService getRetrofitInstance() {
        return ServiceGenerator.createService(WebService.class);

    }
   // @use DI
    public  SharedPrefHelper getSharedPrefInstance() {
        return new SharedPrefHelper(getApplicationContext());

    }

    // @use DI
//    public  TeleMedDatabase getDatabaseInstance() {
//        return TeleMedDatabase.getDatabaseInstance(getApplicationContext());
//
//    }

    public boolean isNetAvail() {
        return Common.isNetworkAvail(getApplicationContext());
    }

          /*
             no need right now !!

           */

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    public void appInResumeState() {
//        Toast.makeText(getApplicationContext(),"In Foreground",Toast.LENGTH_LONG).show();
//
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    public void appInPauseState() {
//        Toast.makeText(getApplicationContext(),"In Background",Toast.LENGTH_LONG).show();
//    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    fun onAppStart() {
//        Log.e("lifecycle event", "ON_START")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    fun onAppResume() {
//        Log.e("lifecycle event", "ON_RESUME")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    fun onAppPause() {
//        Log.e("lifecycle event", "ON_PAUSE")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    fun onAppStop() {
//        Log.e("lifecycle event", "ON_STOP")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    fun onAppDestroy() {
//        Log.e("lifecycle event", "ON_DESTROY")
//    }


}
