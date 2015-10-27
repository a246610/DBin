package com.example.administrator.dbin.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.dbin.AlarmActivity.AlarmHttp;
import com.example.administrator.dbin.Fragment.AlertFragmentDataOff;
import com.example.administrator.dbin.Fragment.AlertInternetFragment;
import com.example.administrator.dbin.ClassLibrary.Http;
import com.example.administrator.dbin.R;


public class MainActivity extends AppCompatActivity {

    public static final String myPreferences = "config";
    private Button btnStart,btnStop,btnConfig, btnShowData;
    private  SharedPreferences pre;
    private PendingIntent pendingIntent;

    private int intervalTime;
    private Intent alramHttpIntent;

    public static final int codeStartTimeAlarm = 0;
    public static final int codeStopTimeAlarm = 1;
    public static final int codeHttpAlarm = 2;

    public static int codePendingIntentStartTime = 3;
    public static int codePendingIntentStopTime = 4;
    public static int codePendingIntentHttlpAlram = 5;

    public static int codeNotificationStartTime = 6;
    public static int codeNotificationStopTime = 7;
    public static int codeNotificationHttlpAlram = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnConfig = (Button) findViewById(R.id.btnConfig);
        btnShowData = (Button) findViewById(R.id.btnShowData);

        //check whether SharePreference init or not
        pre = getSharedPreferences(myPreferences,MODE_PRIVATE);
        int flag = pre.getInt("flag", 0);
        if (flag == 0 ){
            Intent i = new Intent(MainActivity.this,ConfigActivity.class);
            startActivity(i);
            finish();
        }

        //init alarm update
        intervalTime = pre.getInt("interval_time", 0);
        intervalTime *= 1000 * 60;
        alramHttpIntent = new Intent(getBaseContext(), AlarmHttp.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), MainActivity.codeHttpAlarm, alramHttpIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check internet
                Http http = new Http(getBaseContext());
                if (http.isNetworkAvailable()){
                    Toast.makeText(getBaseContext(), R.string.str_toast_start_time,Toast.LENGTH_SHORT).show();

                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis() + intervalTime, intervalTime , pendingIntent);

                    SharedPreferences.Editor editor = pre.edit();
                    editor.putBoolean("flag_alarm_working",true);
                    editor.commit();

                }else{
                    AlertInternetFragment alertInternetFragment = new AlertInternetFragment();
                    alertInternetFragment.show(getSupportFragmentManager(),"");
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pre.getBoolean("flag_alarm_working",false)){
                    Toast.makeText(getBaseContext(), R.string.str_toast_end_time,Toast.LENGTH_SHORT).show();
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);

                    SharedPreferences.Editor editor = pre.edit();
                    editor.putBoolean("flag_alarm_working",false);
                    editor.commit();
                }else {
                    Toast.makeText(getBaseContext(),R.string.str_unactive,Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(i);
            }
        });

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean alarmUp = pre.getBoolean("flag_alarm_working",false);

                if (alarmUp){
                    Intent i = new Intent(MainActivity.this, ShowDataActivity.class);
                    startActivity(i);
                }else{
                    AlertFragmentDataOff alertFragmentDataOff = new AlertFragmentDataOff();
                    alertFragmentDataOff.show(getSupportFragmentManager(),"");
                }

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
    }
}
