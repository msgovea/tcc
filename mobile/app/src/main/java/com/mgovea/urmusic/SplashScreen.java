package com.mgovea.urmusic;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.crashlytics.android.Crashlytics;
import com.mgovea.urmusic.util.Preferencias;

import io.fabric.sdk.android.Fabric;

public class SplashScreen extends Activity {


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

                    Preferencias pref = new Preferencias(getApplicationContext());
                    if (pref.getDadosUsuario() == null) {
                        TaskStackBuilder.create(SplashScreen.this)
                                .addNextIntentWithParentStack(new Intent(SplashScreen.this, TesteLogin.class))
                                .addNextIntent(new Intent(SplashScreen.this, IntroActivity.class))
                                .startActivities();
                    } else {
                        pref.atualizaUsuario();
                        //startActivity(new Intent(SplashScreen.this, ProfileEditActivity.class));
                        //    throw new RuntimeException("This is a crash");
                        startActivity(new Intent(SplashScreen.this, MenuActivity.class));
                        finish();
                    }
//                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                    startActivity(intent);
                }
            }
        };
        Fabric.with(this, new Crashlytics());
        //startActivity(new Intent(SplashScreen.this, com.mgovea.urmusic.test.Main2Activity.class));
        timerThread.start();

    }


    @Override
    protected void onPause(){
        super.onPause();
        //finish();
    }
}