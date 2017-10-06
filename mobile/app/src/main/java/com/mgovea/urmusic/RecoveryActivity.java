package com.mgovea.urmusic;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mgovea.urmusic.async.AsyncRecovery;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.Validation;

public class RecoveryActivity extends AbstractAsyncActivity implements AsyncRecovery.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        setupActionBar();

        Button mRecoveryPassword = (Button) findViewById(R.id.recovery_account_button);
        mRecoveryPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void attemptLogin() {

        // Reset errors.
        EditText mEmailView = (EditText) findViewById(R.id.email);

        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();

        Validation validation = new Validation();
        validation.context = getApplicationContext();

        mEmailView = validation.isFieldValid(mEmailView, false);

        if (!Validation.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            validation.focusView = (validation.focusView == null) ? mEmailView : validation.focusView;
            validation.error = true;
        }

        if (validation.error) {
            validation.focusView.requestFocus();
        } else {
            showLoadingProgressDialog();

            AsyncRecovery sinc = new AsyncRecovery(this);
            sinc.execute(mEmailView.getText().toString());

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

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (string == "true") {
                builder.setTitle(getString(R.string.success_recovery_account));
                builder.setMessage(getString(R.string.success_recovery_account_text));
                builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setCancelable(false);

            } else {
                builder.setTitle(getString(R.string.error_recovery_account));

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

        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }
    }
}
