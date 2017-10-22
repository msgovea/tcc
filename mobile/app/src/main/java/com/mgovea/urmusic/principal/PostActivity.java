package com.mgovea.urmusic.principal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.mgovea.urmusic.MenuActivity;
import com.mgovea.urmusic.R;
import com.mgovea.urmusic.async.publication.AsyncMakePublication;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.posts.QuestionsAdapter;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.MyLayout;
import com.mgovea.urmusic.util.Preferencias;

import java.io.ByteArrayOutputStream;

;

public class PostActivity extends AbstractAsyncActivity implements AsyncMakePublication.Listener {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private View mContent;

    public  View mProgressView;
    private QuestionsAdapter mAdapter;
    private SharedPreferences prefs;
    private ListView listView;
    public EditText mTextPublication;
    private TextView mButtonPublication;
    private LinearLayout mMakePublication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        //TODO MGOVEA - REMOVER LISTA DE ITENS
        /*((MyLayout) findViewById(R.id.fragment_make_publication)).setOnSoftKeyboardListener(new MyLayout.OnSoftKeyboardListener() {
            @Override
            public void onShown() {
                Log.e("FUU","DEU");
                //TODO
                listView.setVisibility(View.GONE);
            }
            @Override
            public void onHidden() {
                Log.e("FUU","DEU naoo");
                //TODO
                listView.setVisibility(View.VISIBLE);
            }
        });*/


        // initialize views
        mContent = findViewById(R.id.fragment_content);

        mProgressView = (View) findViewById(R.id.publication_progress);


//        mIcon = (AppCompatImageView) findViewById(R.id.iconAlarm);
//        mIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
//                prefs.edit().clear().apply();
//                startActivity(new Intent(DefaultActivity.this, com.mgovea.urmusic.MainActivity.class));
//                finish();
//            }
//        });

        listView = (ListView) findViewById(R.id.testemgovea);

        mButtonPublication = (TextView) findViewById(R.id.text_make_post);
        mTextPublication = (EditText) findViewById(R.id.et_publication);

        mMakePublication = (LinearLayout) findViewById(R.id.make_publication);

        //TODO

//        mMakePublication.setOnSoftKeyboardListener(new MyLayout.OnSoftKeyboardListener() {
//            @Override
//            public void onShown() {
//                listView.setVisibility(View.GONE);
//                //Log.e("FUU","DEU");
//            }
//
//            @Override
//            public void onHidden() {
//                listView.setVisibility(View.VISIBLE);
//                //Log.e("FUU","DEU, MENTIRA DEU BOM");
//            }
//        });

        //END TODO

        mButtonPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //TODO MGOVEA
        super.onSaveInstanceState(outState);
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
                showLoadingProgressDialog();
                //bottomSheetDialogFragment.dismiss();

                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);

                //REDIMENSIONAMENTO IMAGEM
                //imagem = Bitmap.createScaledBitmap(imagem, 300, 300, false);

                //comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                //Cria um array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                Log.e("UPLOAD_IMAGEM", byteArray.length + "");

                    Preferencias pref = new Preferencias(this);
                    Usuario usuario = pref.getDadosUsuario();
                    Publicacao publicacao = new Publicacao(usuario, mTextPublication.getText().toString(), byteArray);

                    showLoadingProgressDialog();

                    AsyncMakePublication sinc = new AsyncMakePublication(this);
                    sinc.execute(publicacao);


                //TODO EXIBIR BYTE ARRAY BYTE
//                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                imageView.setImageBitmap(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ***************************************
    // Metodos de retorno Async
    // ***************************************

    @Override
    public void onLoadedError(String s) {
        dismissProgressDialog();
        //dismissProgressDialog();
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.error));
            builder.setMessage(getString(R.string.error));
            builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //b.finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }
    }

    @Override
    public void onLoadedPublication(Boolean bool) {
        dismissProgressDialog();

        Intent i = new Intent(this, MenuActivity.class);
        i.putExtra(API.PUBLICADO, true);
        startActivity(i);
        finish();
    }
}
