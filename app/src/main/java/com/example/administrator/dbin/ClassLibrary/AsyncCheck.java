package com.example.administrator.dbin.ClassLibrary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.administrator.dbin.Activity.MainActivity;
import com.example.administrator.dbin.Activity.ShowDataActivity;
import com.example.administrator.dbin.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncCheck extends AsyncTask<Void, Void, Integer> {

    //private String httpURL = "http://192.168.78.1:8080/checkRoute";
    private String httpURL = "http://192.168.0.123:8080/checkRoute";
    private Context context;
    private String first_floor;
    private String end_floor;

    public AsyncCheck(Context context_){
        this.context = context_;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.myPreferences,  Context.MODE_PRIVATE);
        first_floor = String.valueOf( sharedPreferences.getInt("floor_first", -2));
        end_floor = String.valueOf(sharedPreferences.getInt("floor_end", -2));
        httpURL += "/" + first_floor + "/" + end_floor;
    }
    //tum lai la cai may ao cua android studio k chay dc ha
    //google nexus anh cai con lai ok cung 1 code luon ah
    @Override
    protected Integer doInBackground(Void... params) {

        String inputLine;
        int kq = 0;
        try {
            URL url = new URL(httpURL);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((inputLine = bufferedReader.readLine())!=null){
                kq = Integer.valueOf(inputLine);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return kq;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if (integer == 1) {
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Intent i = new Intent(context, ShowDataActivity.class);
            //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pIntent = PendingIntent.getActivity(context, MainActivity.codePendingIntentHttlpAlram, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(context.getResources().getString(R.string.str_notification_check_tile))
                    .setContentText(context.getResources().getString(R.string.str_notification_check_message))
                    .setTicker(context.getResources().getString(R.string.str_notification_check_ticker))
                    .setSound(soundUri)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);

            // Create Notification Manager
            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // Build Notification with Notification Manager
            notificationmanager.notify(MainActivity.codeNotificationHttlpAlram, builder.build());
        }
    }
}
