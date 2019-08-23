package com.example.alarmmanager_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "Alarm_Demo";
    static Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v(TAG, "Alarm for LifeLog...");
        Intent service_intent = new Intent(context, MyService.class);
        context.startService(service_intent);
    }
}
