package com.example.administrator.dbin.AlarmActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.dbin.ClassLibrary.AsyncCheck;
import com.example.administrator.dbin.ClassLibrary.Http;

public class AlarmHttp extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Http http = new Http(context);
        if (http.isNetworkAvailable()){
            AsyncCheck asyncCheck = new AsyncCheck(context);
            asyncCheck.execute();
        }
    }
}
