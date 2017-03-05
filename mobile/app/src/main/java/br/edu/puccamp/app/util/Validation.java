package br.edu.puccamp.app.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import br.edu.puccamp.app.R;

/**
 * Created by Mateus on 3/5/2017.
 */

public final class Validation {

    public Boolean error = false;
    public View focusView = null;
    public Context context = null;

    public static boolean isEmailValid(String email) {
        if (email.contains("@")) {
            String[] validate = email.split("@");
            return (validate.length > 1);
        }
        return false;
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 5 && password.length() < 13;
    }


    public EditText isFieldValid(EditText text) {
        if (TextUtils.isEmpty(text.getText().toString())) {
            text.setError(context.getString(R.string.error_field_required));
            error = true;
            if (focusView == null) {
                focusView = text;
            }
        }
        return text;
    }

    public static EditText isFieldValid(EditText text, Context contexto, Boolean valid, View focusView) {
        if (TextUtils.isEmpty(text.getText().toString())) {
            text.setError(contexto.getString(R.string.error_field_required));
        }
        return text;
    }

}
