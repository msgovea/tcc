package br.edu.puccamp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.puccamp.app.async.profile.AsyncEditProfile;
import br.edu.puccamp.app.posts.ExampleActivity;
import br.edu.puccamp.app.profile.ProfileEditActivity;
import br.edu.puccamp.app.util.API;

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
                        startActivity(new Intent(SplashScreen.this, br.edu.puccamp.app.principal.MainActivity.class));
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