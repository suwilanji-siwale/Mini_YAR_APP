/*
package com.melborp.yarfmm.services;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.melborp.yarfmm.R;
import com.melborp.yarfmm.activities.Waveview;

import static android.app.Service.START_STICKY;

public class UrlService extends Service {

    private static final String TAG = "MyService";
    String url;
    MediaPlayer mp;


    private static final String NOTIFICATION_CHANNEL_ID = "My ID";
    private static final int NOTIFICATION_ID = 17462091;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "My Service Created", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate");

        mp = new MediaPlayer();
        //mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");

        mp.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {

        startInForeground();
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();


        url = intent.getExtras().getString("yar");
        try {
            mp.setDataSource(url);
            mp.prepare();
            mp.start();
            Log.d(TAG, "we are working");
        } catch(Exception e){}
        Log.d(TAG, "onStart");
        return START_STICKY;


    }


    private void startInForeground() {
        Intent notificationIntent = new Intent(this, Waveview.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("YAR FM")
                    .setContentTitle("#kopalasno1hitstation")
                    .setContentIntent(pendingIntent);
            Notification notification1 = builder1.build();
            startForeground(123, notification1);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("YAR FM")
                .setContentTitle("#kopalasno1hitstation")
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }

}*/
