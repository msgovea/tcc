package br.edu.puccamp.app.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by Mateus on 4/22/2017.
 */

public class Bla implements TextWatcher {

    private final Pattern sPattern = Pattern.compile("/^[a-zA-Zà-ú ]+$/");

    private CharSequence mText;
    private EditText mView;

    public Bla(EditText mView) {
        this.mView = mView;
    }

    private boolean isValid(CharSequence s) {
        return sPattern.matcher(s).matches();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        mText = isValid(s) ? s : "";
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isValid(s)) {
            mView.setText(mText);
        }
        mText = null;
    }
}