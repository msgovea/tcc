package br.edu.puccamp.app.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.profile.AsyncEditProfile;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.entity.UsuarioByte;
import br.edu.puccamp.app.util.API;
import br.edu.puccamp.app.util.AbstractAsyncActivity;
import br.edu.puccamp.app.util.Validation;

public class ProfileEditActivity extends AbstractAsyncActivity implements AsyncEditProfile.Listener{

    private EditText etName;
    private EditText etUsername;
    private EditText etCountry;
    private EditText etState;
    private EditText etCity;
    private DatePicker dpBirthday;

    private Button btnEditProfile;

    private SharedPreferences prefs;
    private Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Instanciando os EditTexts

        etName     = (EditText) findViewById(R.id.et_name);
        etUsername = (EditText) findViewById(R.id.et_user_name);
        etCountry  = (EditText) findViewById(R.id.et_country);
        etState    = (EditText) findViewById(R.id.et_state);
        etCity     = (EditText) findViewById(R.id.et_city);
        dpBirthday = (DatePicker) findViewById(R.id.dp_birthday);
        dpBirthday.setMaxDate(new Date().getTime());


        btnEditProfile = (Button) findViewById(R.id.btn_edit_profile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                attempUpdate();
            }


        });

        obtemUsuarioAtualizado();
    }

    private void attempUpdate() {

        Validation validation = new Validation();
        validation.context = getApplicationContext();

        etName = validation.isFieldValid(etName, true);
        etUsername = validation.isFieldValid(etUsername, false);
        etCountry = validation.isFieldValid(etCountry, true);
        etState = validation.isFieldValid(etState, true);
        etCity = validation.isFieldValid(etCity, true);

        if (validation.error) {
            validation.focusView.requestFocus();
        } else {
            updateProfile();
        }
    }

    private void obtemUsuarioAtualizado() {
        // Obtendo as informações do usuário logado

        Gson gson = new Gson();
        prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);

        // Populando as informações nos EditTexts

        etName.setText(usuario.getNome());
        etUsername.setText(usuario.getApelido());
        etCountry.setText(usuario.getPais());
        etState.setText(usuario.getEstado());
        etCity.setText(usuario.getCidade());
        String[] partes = usuario.getDataNascimento().split("-");
        Log.e("MATEUS", partes[0]);
        Log.e("MATEUS", partes[1]);
        Log.e("MATEUS", partes[2]);

        dpBirthday.updateDate(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
    }

    private void updateProfile() {
        usuario.setNome(etName.getText().toString());
        usuario.setApelido(etUsername.getText().toString());
        usuario.setPais(etCountry.getText().toString());
        usuario.setEstado(etState.getText().toString());
        usuario.setCidade(etCity.getText().toString());
        usuario.setDataNascimento(dpBirthday.getYear() + "-" +
                (dpBirthday.getMonth() + 1) + "-" +
                dpBirthday.getDayOfMonth());


        //TODO ATT IMAGEM
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, 1);
        showLoadingProgressDialog();

        AsyncEditProfile sinc = new AsyncEditProfile(this);
        sinc.execute(usuario);
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

                AsyncEditProfile sinc = new AsyncEditProfile(this);
                sinc.execute(usuario);

//                Log.d("img", byteArray.toString());
//                Log.d("img", byteArray.length + "");
//
//                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//
//                ImageView imageView = (ImageView) findViewById(R.id.imageView1);
//                imageView.setImageBitmap(bitmap);
//
//                Gson gson = new Gson();
//                String json = gson.toJson(byteArray);
//                Log.d("img", json);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaded(Object o) {
        dismissProgressDialog();

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            if (o.getClass() == Usuario.class) {

                builder.setTitle(getString(R.string.success));
                builder.setMessage(getString(R.string.update_profile_success));
                builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.setCancelable(false);
                builder.show();

                atualizaUsuario((Usuario) o);


            } else {
                builder.setTitle(getString(R.string.error));
                builder.setMessage(getString((o.equals("invalid")) ? R.string.error_edit_profile : R.string.error));
                builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }

    }

    private void atualizaUsuario(Usuario usuarioAtualizado){

        prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        Gson json = new Gson();
        prefs.edit().putString(API.USUARIO, json.toJson(usuarioAtualizado)).apply();
        //Log.e("PQP", ((Usuario) o).getGostosMusicais().toString());

        etName.setText(usuarioAtualizado.getNome());
        etUsername.setText(usuarioAtualizado.getApelido());
        etCountry.setText(usuarioAtualizado.getPais());
        etState.setText(usuarioAtualizado.getEstado());
        etCity.setText(usuarioAtualizado.getCidade());
        String[] partes = usuarioAtualizado.getDataNascimento().split("-");
        dpBirthday.updateDate(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));

    }


}
