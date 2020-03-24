package com.telemed.doctor.notification;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import org.jetbrains.annotations.NotNull;

import java.util.Map;
/*
  written by Parvinder Maan
 */

/**
 * Firebase service to generate token and get notifications
 */
public class TeleMedFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = TeleMedFirebaseMessagingService.class.getSimpleName();

    // detect whether app is alive or dead
    // if alive,which activity is visible at the top
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)  {
        String nTitle="",nPayload="";String nType="";
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Data: " + remoteMessage.getData());
        Map<String, String> data = remoteMessage.getData();

            nTitle = data.get("title");
            nType = data.get("type");
            nPayload = data.get("message");
            Log.e(TAG, ""+nTitle+" "+nType+" "+nPayload);
            sendNotification(nType,nPayload,nTitle);

    }

    @Override
    public void onNewToken(@NotNull String token) {
        super.onNewToken(token);
        Log.e(TAG,"token:   "+ token);
    //  sendRegistrationToServer(token);
    //  SharedPrefHelper sharedPref = ((TeleMedApplication) getApplication()).getSharedPrefInstance();
    //  sharedPref.write(SharedPrefHelper.KEY_ACCESS_TOKEN,token);

    }




    private void sendNotification(String type,String message, String title) {
        String targetFragment="";
        NotificationBuilder.build(this)
                .setTitle(title)
                .setBody(message)
                .setTargetFragment(targetFragment)
                .show();

        return;
//        String targetFragment="";
//        switch (type){
//
//            case NotificationType.ADMIN_NOTE:
//                break;
//
//            case NotificationType.NEW_APPOINTMENT_REQUEST:
//                targetFragment="AppointmentConfirmIFragment";
//                NotificationBuilder.init(this).setTitle(title).setBody(message).setTargetFragment(targetFragment)
//                        .build();
//                break;
//
//            case NotificationType.CANCELLED_APPOINTMENT:
//
//                break;
//
//            case NotificationType.CHAT_MESSAGE_REVIEVED:
//
//                break;
//
//
//            case NotificationType.PATIENT_WAITING:
//
//                break;
//
//
//            case NotificationType.CALL_REMIND_ALERT:
//
//                break;
//
//            case  NotificationType.CALL_REMAINING_TIME_ALERT:
//
//                break;
//
//        }

    }



}
