package com.melborp.yarfmm.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.melborp.yarfmm.R;
import com.melborp.yarfmm.services.MainActivityService;

import com.scwang.wave.MultiWaveHeader;

public class Waveview extends AppCompatActivity {
    MultiWaveHeader waveHeader;

    ImageView playMack, stopMack;

    final String YAR_FM_STREAM = "http://196.46.210.116:8000/stream";
    //final String YAR_FM_STREAM = "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio1_mf_p";
    //final String YAR_FM_STREAM = "http://s2.voscast.com:9304/";
    //final String YAR_FM_STREAM = "http://stream-africa.com/PowerFMKabwe";

    private boolean isOnline;
    public Intent serviceIntent;

    boolean mBufferBroadcastIsRegistered;
   // private static final int REQUEST_PHONE_CALL = 1;

    public static ProgressBar playProgressCircle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      waveHeader = findViewById(R.id.waveHeader);
      playMack = findViewById(R.id.playMack);
      stopMack = findViewById(R.id.stopMack);

        //playProgressCircle
        playProgressCircle = findViewById(R.id.playProgressBar);
        /*playProgressCircle.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.menu_white), PorterDuff.Mode.SRC_IN);*/

        try {
            //serviceIntent = new Intent(this, MainActivityService.class);
            serviceIntent = new Intent(this, MainActivityService.class);


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    e.getClass().getName() + " " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


     playMack.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             playAudio();
            /* playMack.setVisibility(View.GONE);
             stopMack.setVisibility(View.VISIBLE);*/

         }
     });

     stopMack.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             stopMyPlayService();
             stopMack.setVisibility(View.GONE);
             playMack.setVisibility(View.VISIBLE);
             waveHeader.stop();
         }
     });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.aboutUs) {

                Intent i = new Intent(Waveview.this, Splash_about.class);
                startActivity(i);
        }
        else if (item.getItemId() == R.id.exit )
        {
            stopMyPlayService();
            finish();
        }

        return true;
    }


    //Start wave view
    private void startWave()
    {
        waveHeader.start();
       /* waveHeader.setStartColor(R.color.colorPrimaryDark);
        waveHeader.setCloseColor(R.color.colorPrimary);*/
    }


    public void playAudio() {

        checkConnectivity();
        if (isOnline) {
            stopMyPlayService();

            serviceIntent.putExtra("yar", YAR_FM_STREAM);

            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    startForegroundService(serviceIntent);
                }
                else
                {
                    startService(serviceIntent);
                }


            } catch (Exception e) {

                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getClass().getName() + " " + e.getMessage(), Toast.LENGTH_LONG).show();
            }


        } else {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("No Internet Connection...");
            //alertDialog.setMessage("Please connect to a network.....");
            alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.setCancelable(true);
                    finish();
                }


            });
            alertDialog.setIcon(R.mipmap.ic_launcher);
            alertDialog.create().getWindow().getAttributes().windowAnimations = R.style.dialogAnimationFade;
            alertDialog.show();
            //FragOnAir.speaker.clearAnimation();
        }
    }


    public void stopMyPlayService() {

        try {
            stopService(serviceIntent);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    e.getClass().getName() + " " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


    }

    private void checkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        isOnline = true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        isOnline = true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        isOnline = true;
                    }
                }
            }

            else {

                try {
                    NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        isOnline = true;
                    }
                    else {
                        isOnline = false;
                    }
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            }
        }
        Log.i("update_statut","Network is available : FALSE ");
        //isOnline = false;
    }

/*
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            isOnline = true;
        } else {
            isOnline = false;
        }*/


    @Override
    protected void onPause() {
        // Unregister broadcast receiver
        if (mBufferBroadcastIsRegistered) {
            unregisterReceiver(broadcastBufferReceiver);
            mBufferBroadcastIsRegistered = false;
        }
        super.onPause();
    }


    // -- onResume register broadcast receiver. To improve, retrieve saved screen data ---
    @Override
    protected void onResume() {
        // Register broadcast receiver
        if (!mBufferBroadcastIsRegistered) {
            registerReceiver(broadcastBufferReceiver, new IntentFilter(
                    MainActivityService.BROADCAST_BUFFER));
            mBufferBroadcastIsRegistered = true;
        }
        super.onResume();
    }



    // Set up broadcast receiver
    private BroadcastReceiver broadcastBufferReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent bufferIntent) {
            showPD(bufferIntent);
        }
    };


    // Progress dialogue...
    private void BufferDialogue() {

       /* pdBuff = new ProgressDialog(MainActivity.this);
        // pdBuff.setTitle("Buffering");
        //pdBuff.setMessage("Connecting to YAR FM server..");
        pdBuff.setTitle("Connecting to YAR FM server..");
        pdBuff.setIndeterminate(true);
        pdBuff.setCancelable(false);
        pdBuff.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                stopMyPlayService();
            }
        });
        pdBuff.show();

        playStream.setVisibility(View.GONE);
        stopStream.setVisibility(View.VISIBLE);*/
        playProgressCircle.setVisibility(View.VISIBLE);

    }


    // Handle progress dialogue for buffering...
    private void showPD(Intent bufferIntent) {
        String bufferValue = bufferIntent.getStringExtra("buffering");
        int bufferIntValue = Integer.parseInt(bufferValue);

        // When the broadcasted "buffering" value is 1, show "Buffering"
        // progress dialogue.
        // When the broadcasted "buffering" value is 0, dismiss the progress
        // dialogue.

        switch (bufferIntValue) {
            case 0:
               /* if (pdBuff != null) {
                    pdBuff.dismiss();
                }*/
              /*  playProgressCircle.setVisibility(View.GONE);
                startEqualizer();*/
                startWave();
                playProgressCircle.setVisibility(View.GONE);
                playMack.setVisibility(View.GONE);
                stopMack.setVisibility(View.VISIBLE);

                break;

            case 1:
                BufferDialogue();
                //
                break;


            case 2:

                break;

        }
    }

}
