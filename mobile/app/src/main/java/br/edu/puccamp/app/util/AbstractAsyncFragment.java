package br.edu.puccamp.app.util;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import br.edu.puccamp.app.R;

/**
 * Created by Mateus on 3/8/2017.
 */

public abstract class AbstractAsyncFragment extends Fragment {
    private ProgressDialog progressDialog;

    private boolean destroyed = false;


    // ***************************************
    // Public methods
    // ***************************************
    public void showLoadingProgressDialog() {
        this.showProgressDialog(getString(R.string.loading));
    }

    public void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }

//    public void showErrorMessage(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.error));
//        builder.setMessage(getString(R.string.error));
//        builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        builder.setCancelable(false);
//        builder.show();
//    }
//
//    public void showErrorMessage(String teste){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.error));
//        builder.setMessage(teste);
//        builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        builder.setCancelable(false);
//        builder.show();
//    }
}
