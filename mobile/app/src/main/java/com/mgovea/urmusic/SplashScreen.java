package com.mgovea.urmusic;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.mgovea.urmusic.principal.MainActivity;
import com.mgovea.urmusic.util.API;

public class SplashScreen extends Activity {

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
//        if (!prefs.getString("usuario", null).isEmpty()) {
//            startActivity(new Intent(this, DefaultActivity.class));
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //VERSION

        try {
            TextView versionTV = (TextView) findViewById(R.id.version);
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;

            versionTV.setText("urMusic v" + version + " - Release: " + verCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //END VERSION

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //TODO MGOVEA2

        ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);

        //END TODO

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SharedPreferences prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
                    if (prefs.getString(API.USUARIO, null) == null) {
                        TaskStackBuilder.create(SplashScreen.this)
                                .addNextIntentWithParentStack(new Intent(SplashScreen.this, TesteLogin.class))
                                .addNextIntent(new Intent(SplashScreen.this, IntroActivity.class))
                                .startActivities();
                    } else {
                        //startActivity(new Intent(SplashScreen.this, ProfileEditActivity.class));
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    }
//                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }


    @Override
    protected void onPause(){
        super.onPause();
        //finish();
    }
}