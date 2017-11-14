package com.mgovea.urmusic;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.mgovea.urmusic.async.profile.AsyncProfile;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.profile.ProfileTabbedActivity;
import com.mgovea.urmusic.register.RegisterActivityNew;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.Preferencias;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends Activity implements AsyncProfile.Listener {

    Uri deepLink = null;
    private String id = null;

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


        try {
            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(getIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                        @Override
                        public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                            if (pendingDynamicLinkData != null) {
                                deepLink = pendingDynamicLinkData.getLink();

                                if (deepLink.toString().contains("idLista")) {
                                    try {
                                        String[] divisao = deepLink.toString().split("/");
                                        id = divisao[divisao.length - 1];

                                        //Toasty.error(getBaseContext(), id, Toast.LENGTH_LONG, true).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {

                                }
                            }
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Dynamic Link", "getDynamicLink:onFailure", e);
                        }
                    });
        } catch (Exception e) {

        }


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
                                .addNextIntentWithParentStack(new Intent(SplashScreen.this, TesteLogin/*RegisterActivityNew*/.class))
                                .addNextIntent(new Intent(SplashScreen.this, IntroActivity.class))
                                .startActivities();
                    } else {
                        pref.atualizaUsuario();

                        if (deepLink == null || id == null) {
                            startActivity(new Intent(SplashScreen.this, MenuActivity.class));
                            finish();
                        } else {
                            AsyncProfile sinc = new AsyncProfile(SplashScreen.this);
                            sinc.execute(Long.valueOf(id));
                        }
                    }
                }
            }
        };
        Fabric.with(this, new Crashlytics());
        timerThread.start();

    }

    @Override
    public void onLoaded(Object o) {
        if (o.getClass() == Usuario.class) {
            Usuario usuarioLoad = (Usuario) o;
            Intent intent = new Intent(SplashScreen.this, MailActivity.class);
            intent.putExtra(API.USUARIO, usuarioLoad.getEmail());
            intent.putExtra(API.BUSCAR_USUARIO_NOME, usuarioLoad.getNome());
            startActivity(intent);
        } else {
            Log.e("urMusic", o.toString());
        }
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }
}