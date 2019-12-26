package com.telemed.doctor.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.telemed.doctor.TeleMedApplication;

public abstract class InternetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction()!=null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            onConnectionChanged();
        }
    }

    public abstract void onConnectionChanged();
}
