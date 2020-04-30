package com.telemed.doctor.notification;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.telemed.doctor.annotation.NotificationType;
import com.telemed.doctor.schedule.model.TimeSlotModel;


import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

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
    //    Log.e(TAG, "From: " + remoteMessage.getFrom());
    //    Log.e(TAG, "Data: " + remoteMessage.getData());
          sendNotification(remoteMessage.getData());

    }

    @Override
    public void onNewToken(@NotNull String token) {
        super.onNewToken(token);
        Log.e(TAG,"token:   "+ token);
    //  sendRegistrationToServer(token);
    //  SharedPrefHelper sharedPref = ((TeleMedApplication) getApplication()).getSharedPrefInstance();
    //  sharedPref.write(SharedPrefHelper.KEY_ACCESS_TOKEN,token);

    }




    private void sendNotification(Map<String, String> data) {
        String title = data.get("title");
        String type = data.get("type");
        String message = data.get("message");
        String targetFragment;

     //   JSONObject json=new JSONObject(data);           // returns {"hei":"sann"}

//        TimeSlotModel into=new TimeSlotModel();
//        into.setPatientId(data.get("PatientId"));
//        into.setDoctorId(data.get("DoctorId"));
//        into.setSlotFrom(data.get("SlotFrom"));
//        into.setSlotTo(data.get("SlotTo"));
//        into.setAppointmentId(Integer.valueOf(data.get("AppointmentId")));
//        into.setAppointmentStatus(Integer.valueOf(data.get("AppointmentStatus")));
      //     Log.e(TAG, data.get("appointmentDetails").toString());



        switch (type){
            case NotificationType.ADMIN_NOTE:
                targetFragment="";
                NotificationBuilder.build(this)
                        .setTitle(title)
                        .setBody(message)
//                      .setActionInfo(data)
                        .setTargetFragment(targetFragment)
                        .show();
                break;

            case NotificationType.NEW_APPOINTMENT_REQUEST:
                targetFragment="AppointmentConfirmIFragment";
                NotificationBuilder.build(this)
                        .setTitle(title)
                        .setBody(message)
                        .setActionInfo(data.get("appointmentDetails"))
                        .setTargetFragment(targetFragment)
                        .show();

                break;

            case NotificationType.CANCELLED_APPOINTMENT:
                targetFragment="";
                NotificationBuilder.build(this)
                        .setTitle(title)
                        .setBody(message)
                      //  .setActionInfo(data)
                        .setTargetFragment(targetFragment)
                        .show();
                break;

            case NotificationType.CHAT_MESSAGE_REVIEVED:

                break;


            case NotificationType.PATIENT_WAITING:

                break;


            case NotificationType.CALL_REMIND_ALERT:

                break;

            case  NotificationType.CALL_REMAINING_TIME_ALERT:

                break;

        }

    }



}
