package br.edu.puccamp.app;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.gson.Gson;

import br.edu.puccamp.app.async.AsyncLogin;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.util.AbstractAsyncActivity;
import br.edu.puccamp.app.util.Hash;
import br.edu.puccamp.app.util.Strings;
import br.edu.puccamp.app.util.Validation;


public class LoginActivity extends AbstractAsyncActivity implements AsyncLogin.Listener {



    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    Usuario usuario = new Usuario();



    /**
     * Set up the {@link android.app.ActionBar}, if the Strings is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        Validation validation = new Validation();
        validation.context = getApplicationContext();

        mEmailView = validation.isFieldValid(mEmailView);

        if (!Validation.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            validation.focusView = (validation.focusView == null) ? mEmailView : validation.focusView;
            validation.error = true;
        }

        // Check for a valid password
        if (!Validation.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            validation.focusView = (validation.focusView == null) ? mPasswordView : validation.focusView;
            validation.error = true;
        }

        if (validation.error) {
            validation.focusView.requestFocus();
        } else {
            showLoadingProgressDialog();

            usuario.setEmail(mEmailView.getText().toString());
            usuario.setSenha(Hash.MD5(mPasswordView.getText().toString()));

            AsyncLogin sinc = new AsyncLogin(this);
            sinc.execute(usuario);

        }
    }

    @Override
    public void onLoaded(Object o) {
        dismissProgressDialog();

        if (o.getClass() == Usuario.class) {
            SharedPreferences prefs = getSharedPreferences(Strings.USUARIO, MODE_PRIVATE);
            Gson json = new Gson();
            prefs.edit().putString(Strings.USUARIO, json.toJson(o)).apply();

            startActivity(new Intent(this, DefaultActivity.class));
        } else {
            if (!o.equals("invalid")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.error_invalid_login));
                builder.setMessage(getString(R.string.error_invalid_account));
                builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            } else {
                mEmailView.setError(getString(R.string.error_invalid_login));
                mPasswordView.setError(getString(R.string.error_invalid_login));
                mPasswordView.requestFocus();
            }
        }

    }

}

