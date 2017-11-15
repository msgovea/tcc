package com.mgovea.urmusic.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import com.mgovea.urmusic.R;;

/**
 * Created by Mateus on 3/8/2017.
 */

public abstract class AbstractAsyncActivity extends AppCompatActivity {
    protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    private boolean destroyed = false;

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    // ***************************************
    // Public methods
    // ***************************************
    public void showLoadingProgressDialog() {
        this.showProgressDialog(getString(R.string.loading));
    }

    public void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }

    public void showErrorMessage(){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getString(R.string.error));
            builder.setMessage(getString(R.string.error));
            builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e ){
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }
    }

    public void showErrorMessage(String teste){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getString(R.string.error_title));
            builder.setMessage(teste);
            builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }
    }

    public void showErrorMessageLink(String title, String message){
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }
    }
}
