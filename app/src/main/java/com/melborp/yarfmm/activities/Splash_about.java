package com.melborp.yarfmm.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.melborp.yarfmm.R;

public class Splash_about extends AppCompatActivity implements View.OnClickListener {

    ImageView fb, tw, in, wh;

    public static final String FB_URL = "http://www.facebook.com/yar89dot7fm/";
    public static final String FB_ID = "148314907185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hideStatusBar();
        setContentView(R.layout.about_splash);

        fb = findViewById(R.id.fbButton);
        tw = findViewById(R.id.twButton);
        in = findViewById(R.id.inButton);
        wh = findViewById(R.id.whButton);


        fb.setOnClickListener(this);
        tw.setOnClickListener(this);
        in.setOnClickListener(this);
        wh.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fbButton:

               /* Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(getApplicationContext());
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);*/
               Intent i = newFacebookIntent(getPackageManager(), "https://web.facebook.com/yar89dot7fm/");
                startActivity(i);

                break;

            case R.id.twButton:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+"YAR897FM"));
                startActivity(intent);

                break;

            case R.id.inButton:

                callInstagram();

                break;

            case R.id.whButton:
                launchWhatsapp();

                break;

        }
    }

    //check for facebook app if installed
    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }
   /* public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + FB_URL;
            } else {
                return "fb://page/" + FB_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FB_URL;
        }
    }*/

    //launch whatsapp method (static method)
    public void launchWhatsapp() {
        //very important code
        Intent localIntent = new Intent("android.intent.action.MAIN");
        localIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        localIntent.setAction("android.intent.action.SEND");
        localIntent.setType("text/plain");
        localIntent.putExtra("android.intent.extra.TEXT", "This is my text to send.");
        localIntent.putExtra("jid", "260761541414" + "@s.whatsapp.net");
        //localIntent.putExtra("jid", "260979062493" + "@s.whatsapp.net");
        localIntent.setPackage("com.whatsapp");
        if (isAppInstalledAndEnabled("com.whatsapp")) {
            startActivity(localIntent);
            return;
        }
        Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
    }

    private boolean isAppInstalledAndEnabled(String paramString) {
        try {
            boolean bool = this.getPackageManager().getApplicationInfo(paramString, 0).enabled;
            return bool;
        } catch (Exception localException) {
        }
        return false;
    }

    // Launch Instagram App
    private void callInstagram() {
        String apppackage = "com.instagram.android";
        Context cx=this;
        try {
            Intent i = cx.getPackageManager().getLaunchIntentForPackage(apppackage);
            cx.startActivity(i);
        } catch (Exception  e) {
            Toast.makeText(this, "Sorry, Instagram Not Found", Toast.LENGTH_LONG).show();
        }

    }



}
