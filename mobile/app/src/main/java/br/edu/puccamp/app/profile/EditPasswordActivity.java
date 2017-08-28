package br.edu.puccamp.app.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import br.edu.puccamp.app.util.Hash;
import br.edu.puccamp.app.util.Validation;

public class EditPasswordActivity extends AbstractAsyncActivity implements AsyncEditProfile.Listener{

    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etConfirmNewPassword;

    private Button btnEditPassword;

    private SharedPreferences prefs;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        etOldPassword = (EditText) findViewById(R.id.et_old_password);
        etNewPassword = (EditText) findViewById(R.id.et_new_password);
        etConfirmNewPassword  = (EditText) findViewById(R.id.et_confirm_new_password);

        btnEditPassword = (Button) findViewById(R.id.btn_update_password);
        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaSenha();
                //TODO
            }


        });

        obtemUsuarioAtualizado();

    }

    private void obtemUsuarioAtualizado() {
        // Obtendo as informações do usuário logado

        Gson gson = new Gson();
        prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);
    }

    private void atualizaSenha() {
        // Reset errors.
        etOldPassword.setError(null);
        etNewPassword.setError(null);
        etConfirmNewPassword.setError(null);


        // Store values at the time of the login attempt.
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String confirmNewPassword = etConfirmNewPassword.getText().toString();

        Validation validation = new Validation();
        validation.context = getApplicationContext();

        etOldPassword = validation.isFieldValid(etOldPassword, false);
        //etNewPassword = validation.isFieldValid(etNewPassword, false);
        //etConfirmNewPassword = validation.isFieldValid(etConfirmNewPassword, false);

        // Check for a valid password
        if (!Validation.isPasswordValid(newPassword)) {
            etNewPassword.setError(getString(R.string.error_invalid_password));
            validation.focusView = (validation.focusView == null) ? etNewPassword : validation.focusView;
            validation.error = true;
        }

        etConfirmNewPassword = validation.isFieldValid(etConfirmNewPassword, false);


        if (!newPassword.equals(confirmNewPassword)) {
            etConfirmNewPassword.setError(getString(R.string.error_confirm_password));
            validation.focusView = (validation.focusView == null) ? etConfirmNewPassword : validation.focusView;
            validation.error = true;
        }

        if (validation.error) {
            validation.focusView.requestFocus();
        } else {
            if (Hash.MD5(oldPassword).equals(usuario.getSenha())) {
                showLoadingProgressDialog();

                usuario.setSenha(Hash.MD5(etNewPassword.getText().toString()));

                AsyncEditProfile sinc = new AsyncEditProfile(this);
                sinc.execute(usuario);
            } else {
                etOldPassword.setError(getString(R.string.error_old_password_invalid));
                validation.focusView = (validation.focusView == null) ? etOldPassword : validation.focusView;
                validation.error = true;
            }

        }
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
    }
}
