package br.edu.puccamp.app;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import br.edu.puccamp.app.async.AsyncRegister;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.util.AbstractAsyncActivity;
import br.edu.puccamp.app.util.Convert;
import br.edu.puccamp.app.util.Hash;
import br.edu.puccamp.app.util.Validation;

public class RegisterActivity extends AbstractAsyncActivity implements AsyncRegister.Listener {


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private EditText mNameView;
    private EditText mNickNameView;
    //private EditText mBirthdayView;
    private EditText mCountryView;
    private EditText mStateView;
    private EditText mCityView;
    private Button mEmailSignInButton;
    private DatePicker mBirthdayDataPickerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupActionBar();

        // Set up the register form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
        mNameView = (EditText) findViewById(R.id.name);
        mNickNameView = (EditText) findViewById(R.id.nickName);
        //mBirthdayView = (EditText) findViewById(R.id.birthday);
        mCountryView = (EditText) findViewById(R.id.country);
        mStateView = (EditText) findViewById(R.id.state);
        mCityView = (EditText) findViewById(R.id.city);

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

        mBirthdayDataPickerView = (DatePicker) findViewById(R.id.datePicker);
        mBirthdayDataPickerView.setMaxDate(new Date().getTime());
        //Toast.makeText(this, dp.getYear(), Toast.LENGTH_SHORT);

        ///////////////////////////

        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.country);
        // Get the string array
        String[] countries = getResources().getStringArray(R.array.countries_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the Strings is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void attemptLogin() {
        Log.i(null, mBirthdayDataPickerView.getYear() + "-" + mBirthdayDataPickerView.getMonth() + "-" + mBirthdayDataPickerView.getDayOfMonth());
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        Validation validation = new Validation();
        validation.context = getApplicationContext();

        mNameView = validation.isFieldValid(mNameView, true);
        mNickNameView = validation.isFieldValid(mNickNameView, false);
        mEmailView = validation.isFieldValid(mEmailView, false);

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

        if (!password.equals(confirmPassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_confirm_password));
            validation.focusView = (validation.focusView == null) ? mConfirmPasswordView : validation.focusView;
            validation.error = true;
        }

        //mBirthdayView = validation.isFieldValid(mBirthdayView, false);
        mCountryView = validation.isFieldValid(mCountryView, true);
        mStateView = validation.isFieldValid(mStateView, true);
        mCityView = validation.isFieldValid(mCityView, true);

        if (validation.error) {
            validation.focusView.requestFocus();
        } else {
            showLoadingProgressDialog();

            Usuario usuario = new Usuario();
            usuario.setNome(mNameView.getText().toString());
            usuario.setPais(mCountryView.getText().toString());
            usuario.setEstado(mStateView.getText().toString());
            usuario.setCidade(mCityView.getText().toString());
            usuario.setApelido(mNickNameView.getText().toString());
            usuario.setEmail(mEmailView.getText().toString());
            usuario.setSenha(Hash.MD5(mPasswordView.getText().toString()));
            //usuario.setDataNascimento(Convert.dateEnglish(mBirthdayView.getText().toString()));
            usuario.setDataNascimento(mBirthdayDataPickerView.getYear() + "-" +
                    mBirthdayDataPickerView.getMonth() + "-" +
                    mBirthdayDataPickerView.getDayOfMonth());

            AsyncRegister sinc = new AsyncRegister(this);
            sinc.execute(usuario);

        }
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
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(this, WelcomeScreen.class));
        //finishActivity(0);
        finish();
    }

    @Override
    public void onLoaded(String string) {
        dismissProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (string == "true") {
            builder.setTitle(getString(R.string.success_register_account));
            builder.setMessage(getString(R.string.register_confirm_account));
            builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);

        } else {
            builder.setTitle(getString(R.string.error_register_account));
            builder.setMessage(getString(R.string.error_invalid_account));
            builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
        }
        builder.show();
    }


}

