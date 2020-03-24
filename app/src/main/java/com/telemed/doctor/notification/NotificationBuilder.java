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

import androidx.core.app.NotificationCompat;

import com.telemed.doctor.R;
import com.telemed.doctor.home.HomeActivity;
/*
  written by Parvinder Maan
 */
public class NotificationBuilder {
    private Context context;
    private String title="",bodyMessage="",targetFragment=""; //default

    public static NotificationBuilder build(Context context) {
        return new NotificationBuilder(context);
    }



    private NotificationBuilder(Context context) {
        this.context = context;
    }

    public NotificationBuilder setTitle(String title){
        this.title = title;
        return this;
    }

    public NotificationBuilder setBody(String body){
        this.bodyMessage = body;
        return this;
    }

    public NotificationBuilder setTargetFragment(String target){
        this.targetFragment = target;
        return this;
    }


    public void show() {

    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    int id= (int) System.currentTimeMillis();
    String NOTIFICATION_CHANNEL_ID = "default_channel";
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);



    Intent intent = new Intent(context, HomeActivity.class);
    intent.putExtra("KEY_WHICH_FRAGMENT",targetFragment);

//  PendingIntent pendingIntent = PendingIntent.getActivity(this, requestId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, id , intent, 0);



    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", android.app.NotificationManager.IMPORTANCE_HIGH);
        // Configure the notification channel.
        notificationChannel.setDescription(""+ bodyMessage);
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
            .setTicker(""+context.getResources().getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(""+title)
            .setContentText(""+ bodyMessage)
            .setColor(context.getResources().getColor(R.color.colorWhite))
            .setContentInfo("")
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);

    if (notificationManager != null) {
        notificationManager.notify(id, notificationBuilder.build());
    }
}
}
