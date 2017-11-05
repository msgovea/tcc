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
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mgovea.urmusic.MenuActivity;
import com.mgovea.urmusic.R;
import com.mgovea.urmusic.async.AsyncDenunciar;
import com.mgovea.urmusic.async.publication.AsyncMakePublication;
import com.mgovea.urmusic.entity.Denuncia;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.posts.QuestionsAdapter;
import com.mgovea.urmusic.posts.options.OptionsAdapter;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.MyLayout;
import com.mgovea.urmusic.util.Preferencias;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

public class PostActivity extends AbstractAsyncActivity implements AsyncMakePublication.Listener {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private View mContent;

    public View mProgressView;
    private QuestionsAdapter mAdapter;
    private SharedPreferences prefs;
    private ListView listView;
    public EditText mTextPublication;
    private TextView mButtonPublication;
    private LinearLayout mMakePublication;
    Publicacao mPublicacao;

    private SimpleDraweeView imagePublication;

    private Button btnImagem;
    private Button btnYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new);

        Preferencias pref = new Preferencias(this);
        Usuario usuario = pref.getDadosUsuario();
        mPublicacao = new Publicacao(usuario, null);

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
        mContent = findViewById(R.id.make_publication);

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

        btnImagem = (Button) findViewById(R.id.button_img_pub);
        btnImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        btnYoutube = (Button) findViewById(R.id.button_yt);
        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText taskEditText = new EditText(PostActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(PostActivity.this)
                        .setTitle(getString(R.string.denuncia))
                        .setMessage(getString(R.string.denuncia_text))
                        .setView(taskEditText)
                        .setPositiveButton(getString(R.string.send), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                if (getYouTubeId(task) == "error") {
                                    showErrorMessageLink("Link inválido!","O link enviado não é válido! Tente novamente");

                                } else {
                                    imagePublication.setImageURI("http://www.freepngimg.com/download/youtube/8-2-youtube-transparent.png");
                                    imagePublication.setVisibility(View.VISIBLE);

                                    mPublicacao.setVideo(task);
                                    mPublicacao.setImagem(null);
                                    //code
                                    Log.e("VERIFICA", task);
                                    mPublicacao.setVideo(getYouTubeId(task));
                                    Log.e("VERIFICA", getYouTubeId(task));
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), null)
                        .create();
                dialog.show();
            }
        });

        mButtonPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingProgressDialog();
                mPublicacao.setConteudo(mTextPublication.getText().toString());
                AsyncMakePublication sinc = new AsyncMakePublication(PostActivity.this);
                sinc.execute(mPublicacao);

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagePublication = (SimpleDraweeView) findViewById(R.id.img_publication);

    }

    private String getYouTubeId(String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "error";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
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
        if (resultCode == RESULT_OK && data != null) {

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

                imagePublication.setImageBitmap(imagem);

                mPublicacao.setImagem(byteArray);
                mPublicacao.setVideo(null);

                imagePublication.setVisibility(View.VISIBLE);

                dismissProgressDialog();

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
