package br.edu.puccamp.app.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.AsyncEditProfile;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.util.API;
import br.edu.puccamp.app.util.AbstractAsyncActivity;

public class ProfileEditActivity extends AbstractAsyncActivity implements AsyncEditProfile.Listener{

    private EditText etName;
    private EditText etEmail;
    private EditText etCountry;
    private EditText etState;
    private EditText etCity;
    private EditText etBirthday;

    private Button btnEditProfile;

    private SharedPreferences prefs;
    private Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                atualizarSenha();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Instanciando os EditTexts

        etName     = (EditText) findViewById(R.id.etName);
        etEmail    = (EditText) findViewById(R.id.etEmail);
        etCountry  = (EditText) findViewById(R.id.etCountry);
        etState    = (EditText) findViewById(R.id.etState);
        etCity     = (EditText) findViewById(R.id.etCity);
        etBirthday = (EditText) findViewById(R.id.etBirthday);

        btnEditProfile = (Button) findViewById(R.id.btn_edit_profile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }


        });

        obtemUsuarioAtualizado();


    }

    private void atualizarSenha() {
        startActivity(new Intent(this, EditPasswordActivity.class));

    }

    private void obtemUsuarioAtualizado() {
        // Obtendo as informações do usuário logado

        Gson gson = new Gson();
        prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);

        // Populando as informações nos EditTexts

        etName.setText(usuario.getNome());
        etEmail.setText(usuario.getEmail());
        etCountry.setText(usuario.getPais());
        etState.setText(usuario.getEstado());
        etCity.setText(usuario.getCidade());
        etBirthday.setText(usuario.getDataNascimento());
    }

    private void updateProfile() {
        usuario.setNome(etName.getText().toString());
        usuario.setEmail(etEmail.getText().toString());
        usuario.setPais(etCountry.getText().toString());
        usuario.setEstado(etState.getText().toString());
        usuario.setCidade(etCity.getText().toString());
        usuario.setDataNascimento(etBirthday.getText().toString());

        showLoadingProgressDialog();

        AsyncEditProfile sinc = new AsyncEditProfile(this);
        sinc.execute(usuario);
    }

    @Override
    public void onLoaded(Object o) {
        dismissProgressDialog();

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

            atualizaUsuario((Usuario)o);



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

    }

    private void atualizaUsuario(Usuario usuarioAtualizado){

        prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        Gson json = new Gson();
        prefs.edit().putString(API.USUARIO, json.toJson(usuarioAtualizado)).apply();
        //Log.e("PQP", ((Usuario) o).getGostosMusicais().toString());

        etName.setText(usuarioAtualizado.getNome());
        etEmail.setText(usuarioAtualizado.getEmail());
        etCountry.setText(usuarioAtualizado.getPais());
        etState.setText(usuarioAtualizado.getEstado());
        etCity.setText(usuarioAtualizado.getCidade());
        etBirthday.setText(usuarioAtualizado.getDataNascimento());

    }


}
