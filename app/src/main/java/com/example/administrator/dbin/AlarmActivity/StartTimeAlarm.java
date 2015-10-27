package com.example.administrator.dbin.AlarmActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.dbin.Activity.MainActivity;
import com.example.administrator.dbin.R;

public class StartTimeAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent i = new Intent(context,MainActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(context, MainActivity.codePendingIntentStartTime, i,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getResources().getString(R.string.str_notification_start_time_title))
                .setContentText(context.getResources().getString(R.string.str_notification_start_time_message))
                .setTicker(context.getResources().getString(R.string.str_notification_start_time_ticker))
                .setSound(soundUri)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pIntent)
                .setAutoCancel(true);


        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(MainActivity.codeNotificationStartTime, builder.build());

    }
}