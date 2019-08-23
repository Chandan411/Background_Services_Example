package com.example.alarmmanager_demo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start, stop;
    MediaPlayer mp;
    Date first_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent recurringintent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // alarms.setRepeating(AlarmManager.RTC_WAKEUP, first_log.getTime(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, recurringintent); // Log repetition
        alarms.setRepeating(AlarmManager.RTC_WAKEUP, Long.parseLong("1000"), Long.parseLong("10000"), recurringintent);
    }

    @Override
    public void onClick(View view) {

        if (view == start) {
            startService(new Intent(this, MyService.class));
            Log.d("Testing", "Servicing - service started");
        } else if (view == stop) {
            stopService(new Intent(this, MyService.class));
            Log.d("Testing", "Servicing - service stopped");


        }

    }


}
