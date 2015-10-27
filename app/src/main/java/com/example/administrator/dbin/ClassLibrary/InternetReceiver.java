package com.example.administrator.dbin.ClassLibrary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.administrator.dbin.Activity.MainActivity;
import com.example.administrator.dbin.AlarmActivity.AlarmHttp;
import com.example.administrator.dbin.R;

public class InternetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Http http = new Http(context);
        if (!http.isNetworkAvailable()){
            Toast.makeText(context,context.getResources().getText(R.string.str_toast_end_time), Toast.LENGTH_SHORT).show();

            Intent alramHttpIntent = new Intent(context, AlarmHttp.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, MainActivity.codeHttpAlarm, alramHttpIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }
}
