package br.edu.puccamp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.util.Strings;

public class SplashScreen extends Activity {

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences prefs = getSharedPreferences(Strings.USUARIO, MODE_PRIVATE);
//        if (!prefs.getString("usuario", null).isEmpty()) {
//            startActivity(new Intent(this, DefaultActivity.class));
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SharedPreferences prefs = getSharedPreferences(Strings.USUARIO, MODE_PRIVATE);
                    if (prefs.getString(Strings.USUARIO, null) == null) {
                        TaskStackBuilder.create(SplashScreen.this)
                                .addNextIntentWithParentStack(new Intent(SplashScreen.this, MainActivity.class))
                                .addNextIntent(new Intent(SplashScreen.this, IntroActivity.class))
                                .startActivities();
                    } else {
                        startActivity(new Intent(SplashScreen.this, DefaultActivity.class));
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
        finish();
    }
}