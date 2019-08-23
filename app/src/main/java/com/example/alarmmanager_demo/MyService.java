package com.example.alarmmanager_demo;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.graphics.Color.argb;

public class MyService extends Service {
    private Ringtone ringtone;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Testing", "Servicing - service is Running");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(this, uri);
        ringtone.play();
        create_notification();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
        Log.d("Testing", "Servicing - service is destroyed");
    }


    public void create_notification() {
        String channelId = "Test Notification";
        final Intent emptyIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("This is a text notification")
                        .setAutoCancel(true)
                        .setColor(argb(255, 255, 215, 0))
                        .setContentIntent(pendingIntent);

        final NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "location off",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(7 /* ID of notification */, notificationBuilder.build());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationManager.cancel(7);
            }
        }, 2000);

    }


    //Code to listen screen is on/off
    /*private void registerBroadcastReceiver() {

        final IntentFilter theFilter = new IntentFilter();
        */

    /**
     * System Defined Broadcast
     *//*
        theFilter.addAction(Intent.ACTION_SCREEN_ON);
        theFilter.addAction(Intent.ACTION_SCREEN_OFF);
        theFilter.addAction(Intent.ACTION_USER_PRESENT);

        BroadcastReceiver screenOnOffReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String strAction = intent.getAction();

                KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                if(strAction.equals(Intent.ACTION_USER_PRESENT) || strAction.equals(Intent.ACTION_SCREEN_OFF) || strAction.equals(Intent.ACTION_SCREEN_ON)  )
                    if( myKM.inKeyguardRestrictedInputMode())
                    {
                        System.out.println("Screen off " + "LOCKED");
                        Log.d("Testing","Servicing - Screen Off Locked");
                    } else
                    {
                        System.out.println("Screen on " + "UNLOCKED");
                        Log.d("Testing","Servicing - Screen On Locked");
                    }
                      }
        };
    }*/

}
