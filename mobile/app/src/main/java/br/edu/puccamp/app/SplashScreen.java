package br.edu.puccamp.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import br.edu.puccamp.app.posts.ExampleActivity;
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

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //TODO MGOVEA2

        ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);

        //END TODO

//        Thread timerThread = new Thread() {
//            public void run() {
//                try {
//                    sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    SharedPreferences prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
//                    if (prefs.getString(API.USUARIO, null) == null) {
//                        TaskStackBuilder.create(SplashScreen.this)
//                                .addNextIntentWithParentStack(new Intent(SplashScreen.this, TesteLogin.class))
//                                .addNextIntent(new Intent(SplashScreen.this, IntroActivity.class))
//                                .startActivities();
//                    } else {
//                        startActivity(new Intent(SplashScreen.this, br.edu.puccamp.app.principal.MainActivity.class));
//                    }
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 1);
////                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
////                    startActivity(intent);
//                }
//            }
//        };
        //timerThread.start();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("img", "Imagem selecionada");
        //Testar processo de retorno dos dados
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            //recuperar local do recurso
            Uri localImagemSelecionada = data.getData();

            //recupera a imagem do local que foi selecionada
            try {
                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);

                //comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.WEBP, 0, stream);

                //Cria um array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                Log.d("img", byteArray.toString());
                Log.d("img", byteArray.length + "");

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                ImageView imageView = (ImageView) findViewById(R.id.imageView1);
                imageView.setImageBitmap(bitmap);

                Gson gson = new Gson();
                String json = gson.toJson(byteArray);
                Log.d("img", json);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onPause(){
        super.onPause();
        //finish();
    }
}