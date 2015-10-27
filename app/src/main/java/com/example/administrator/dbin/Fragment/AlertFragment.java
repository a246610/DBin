package com.example.administrator.dbin.Fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.example.administrator.dbin.Activity.MainActivity;
import com.example.administrator.dbin.AlarmActivity.AlarmHttp;
import com.example.administrator.dbin.AlarmActivity.StartTimeAlarm;
import com.example.administrator.dbin.AlarmActivity.StopTimeAlarm;
import com.example.administrator.dbin.R;

import java.util.Calendar;
import java.util.List;

public class AlertFragment extends DialogFragment{

    private Activity context;
    private List<String> list;

    public AlertFragment(Activity activity, List<String> list_){
        this.context = activity;
        this.list = list_;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.str_confirm_title)
                .setMessage(R.string.str_confirm_message)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveConfig();
                        dismiss();
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).create();

    }

    private void saveConfig(){
        SharedPreferences pre = context.getSharedPreferences(MainActivity.myPreferences, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();

        editor.putInt("flag", 1);

        editor.putString("start_time", list.get(0));
        editor.putString("end_time", list.get(1));

        editor.putInt("floor_first", Integer.parseInt(list.get(2)));
        editor.putInt("floor_end", Integer.parseInt(list.get(3)));
        editor.putInt("interval_time", Integer.parseInt(list.get(4)));
        editor.putInt("max_weight", Integer.parseInt(list.get(5)));

        editor.commit();

        //Set current time
        //Set time start to notice
        int hour_start =  Integer.parseInt(list.get(0).substring(0, 2));
        int minute_start =  Integer.parseInt(list.get(0).substring(5));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour_start);
        calendar.set(Calendar.MINUTE, minute_start);

        long tmp_  = calendar.getTimeInMillis();
        if (tmp_ < System.currentTimeMillis())
            tmp_ += AlarmManager.INTERVAL_DAY;

        Intent alarmIntent = new Intent(context.getBaseContext(), StartTimeAlarm.class);
        //alarmIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getBaseContext(), MainActivity.codeStartTimeAlarm, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tmp_, AlarmManager.INTERVAL_DAY, pendingIntent);

        //Set time stop to notice
        hour_start =  Integer.parseInt(list.get(1).substring(0, 2));
        minute_start =  Integer.parseInt(list.get(1).substring(5));

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour_start);
        calendar.set(Calendar.MINUTE, minute_start);

        tmp_  = calendar.getTimeInMillis();
        if (tmp_ < System.currentTimeMillis())
            tmp_ += AlarmManager.INTERVAL_DAY;

        Intent alarmIntent2 = new Intent(context.getBaseContext(), StopTimeAlarm.class);
        //alarmIntent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context.getBaseContext(), MainActivity.codeStopTimeAlarm, alarmIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, tmp_, AlarmManager.INTERVAL_DAY, pendingIntent2);

        Toast.makeText(context,R.string.str_toast_off,Toast.LENGTH_LONG).show();

    }
}

