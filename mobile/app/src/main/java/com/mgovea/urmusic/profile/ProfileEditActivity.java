package com.mgovea.urmusic.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;
import com.mgovea.urmusic.async.profile.AsyncEditProfile;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.Validation;

import java.util.Date;

import com.mgovea.urmusic.R;;

public class ProfileEditActivity extends AbstractAsyncActivity implements AsyncEditProfile.Listener{

    private EditText etName;
    private EditText etUsername;
    private EditText etCountry;
    private EditText etState;
    private EditText etCity;

    private Button btnEditProfile;

    private SharedPreferences prefs;
    private Usuario usuario;
    private Usuario usuarioAtt;



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


        btnEditProfile = (Button) findViewById(R.id.btn_edit_profile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }

    private void updateProfile() {
        //usuarioAtt = new Usuario();
        //usuarioAtt.setCodigoUsuario(usuario.getCodigoUsuario());
        usuario.setEmail(usuario.getEmail());
        usuario.setNome(etName.getText().toString());
        usuario.setApelido(etUsername.getText().toString());
        usuario.setPais(etCountry.getText().toString());
        usuario.setEstado(etState.getText().toString());
        usuario.setCidade(etCity.getText().toString());

        showLoadingProgressDialog();

        AsyncEditProfile sinc = new AsyncEditProfile(this);
        sinc.execute(usuario);
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

    }


}
