package br.edu.puccamp.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import br.edu.puccamp.app.async.AsyncLogin;
import br.edu.puccamp.app.async.AsyncPublication;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.util.AbstractAsyncActivity;

public class TestePublication extends AbstractAsyncActivity implements AsyncPublication.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_publication);

        AsyncPublication sinc = new AsyncPublication(this);
        sinc.execute();

    }


    @Override
    public void onLoaded(ArrayList<Publicacao> lista) {
        Log.e(null, lista.toString());
    }

    @Override
    public void onLoaded(String s) {
        Log.e(null, s);
    }
}
