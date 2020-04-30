package com.telemed.doctor.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.telemed.doctor.R;
import com.telemed.doctor.home.view.HomeActivity;
import com.telemed.doctor.schedule.model.TimeSlotModel;

import org.json.JSONObject;

import java.util.Map;
import java.util.TimeZone;

/*
  written by Parvinder Maan
 */
public class NotificationBuilder {
    private Intent intent;
    private Context context;
    private String title = "", bodyMessage = "", targetFragment = "";
    private Map<String, String> actionInfo; //default

    public static NotificationBuilder build(Context context) {

        return new NotificationBuilder(context);
    }


    private NotificationBuilder(Context context) {
        intent = new Intent(context, HomeActivity.class);
        this.context = context;
    }

    public NotificationBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public NotificationBuilder setBody(String body) {
        this.bodyMessage = body;
        return this;
    }

    public NotificationBuilder setTargetFragment(String target) {
        this.targetFragment = target;
        intent.putExtra("KEY_WHICH_FRAGMENT", targetFragment);
        return this;
    }

    public NotificationBuilder setActionInfo(String  actionInfo) {
         intent.putExtra("KEY_",getActionData(actionInfo));
//        Bundle b=new Bundle();
//        b.put("KEY__",getActionData(actionInfo));
//        intent.putExtra("KEY_",b);
        return this;
    }


    public void show() {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = (int) System.currentTimeMillis();
        String NOTIFICATION_CHANNEL_ID = "default_channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);




//        in.putExtra("KEY_",);
//        in.putExtra("KEY_WHICH_FRAGMENT", targetFragment);
//        in.putExtra("KEY_", actionInfo.get("AppointmentId"));
//        in.putExtra("KEY_", actionInfo.get("AppointmentStatus"));
//        in.putExtra("KEY_", );
//        in.putExtra("KEY_", );
//        in.putExtra("KEY_", );

       //  PendingIntent pendingIntent = PendingIntent.getActivity(this, requestId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, 0);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", android.app.NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setDescription("" + bodyMessage);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
//          notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(android.app.Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_notification)
                .setTicker("" + context.getResources().getString(R.string.app_name))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle("" + title)
                .setContentText("" + bodyMessage)
                .setColor(context.getResources().getColor(R.color.colorWhite))
                .setContentInfo("")
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify(id, notificationBuilder.build());
        }
    }


    public static TimeSlotModel getActionData(String jsonString){
        JsonParser jsonParser=new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        TimeSlotModel into=new TimeSlotModel();
        into.setPatientId(jsonObject.get("PatientId").getAsString());
        into.setDoctorId(jsonObject.get("DoctorId").getAsString());
        into.setSlotFrom(jsonObject.get("SlotFrom").getAsString());
        into.setSlotTo(jsonObject.get("SlotTo").getAsString());
        into.setAppointmentId(jsonObject.get("AppointmentId").getAsInt());
        into.setAppointmentStatus(jsonObject.get("AppointmentStatus").getAsInt());
        return into;
    }
}
