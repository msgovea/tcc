package br.edu.puccamp.app;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.edu.puccamp.app.async.AsyncRegister;
import br.edu.puccamp.app.util.AbstractAsyncActivity;
import br.edu.puccamp.app.util.Validation;

public class RegisterActivity extends AbstractAsyncActivity implements AsyncRegister.Listener{


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private EditText mNameView;
    private EditText mBirthdayView;
    private EditText mCountryView;
    private EditText mStateView;
    private EditText mCityView;
    private Button mEmailSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupActionBar();

        // Set up the register form.
        mEmailView           = (EditText) findViewById(R.id.email);
        mPasswordView        = (EditText) findViewById(R.id.password);
        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
        mNameView            = (EditText) findViewById(R.id.name);
        mBirthdayView        = (EditText) findViewById(R.id.birthday);
        mCountryView         = (EditText) findViewById(R.id.country);
        mStateView           = (EditText) findViewById(R.id.state);
        mCityView            = (EditText) findViewById(R.id.city);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);


        mCityView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        Validation validation = new Validation();
        validation.context = getApplicationContext();

        mNameView  = validation.isFieldValid(mNameView);
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

        mConfirmPasswordView = validation.isFieldValid(mConfirmPasswordView);

        if (!password.equals(confirmPassword)){
            mConfirmPasswordView.setError(getString(R.string.error_confirm_password));
            validation.focusView = (validation.focusView == null) ? mConfirmPasswordView : validation.focusView;
            validation.error = true;
        }

        mBirthdayView = validation.isFieldValid(mBirthdayView);
        mCountryView  = validation.isFieldValid(mCountryView);
        mStateView    = validation.isFieldValid(mStateView);
        mCityView     = validation.isFieldValid(mCityView);

        if (validation.error) {
            validation.focusView.requestFocus();
        } else {
            showLoadingProgressDialog();

            AsyncRegister sinc = new AsyncRegister(this);
            sinc.execute(mNameView.getText().toString(), mEmailView.getText().toString(), mPasswordView.getText().toString(), "A");

        }
    }


    @Override
    public void onLoaded(String string) {
        if (string == "true") {
            dismissProgressDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Conta registrada");
            builder.setMessage("Cadastro realizado, confirme o acesso via e-mail");
            builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        } else {
            dismissProgressDialog();

        }
    }


}

