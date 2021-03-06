package com.mgovea.urmusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mgovea.urmusic.async.gosto_musical.AsyncGostoMusical;
import com.mgovea.urmusic.gosto_musical.Gosto;
import com.mgovea.urmusic.gosto_musical.InteractiveArrayAdapter;
import com.mgovea.urmusic.util.AbstractAsyncActivity;

import java.util.ArrayList;

//import com.mgovea.urmusic.gosto_musical.GostosAdapter;


public class GostoMusicalActivity extends AbstractAsyncActivity implements AsyncGostoMusical.Listener {

    private ListView listView;
    protected static ArrayList<Gosto> gostos;
//    private GostosAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gosto_musical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean selecionado = false;
                for (Gosto gosto : gostos) {
                    if (gosto.isSelecionado()) selecionado = true;
                }

                if (selecionado) startActivity(new Intent(getApplicationContext(), GostoFavoritoActivity.class));

                else Snackbar.make(view, R.string.error_gosto_musical, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        listView = (ListView) findViewById(R.id.listGostos);


//        ArrayAdapter<Gosto> adapter = new InteractiveArrayAdapter(this, getGostos(lista));
//        listView.setAdapter(adapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //mRecyclerView.setAdapter(mAdapter = new GostosAdapter(this, listaOficial));

        obtemGostos();
    }

    private void obtemGostos() {
        showLoadingProgressDialog();
        AsyncGostoMusical sinc = new AsyncGostoMusical(this);
        sinc.execute();
    }

    public void skip(View v){
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        finish();
    }

    // ***************************************
    // Metodos de retorno Async
    // ***************************************
    @Override
    public void onLoaded(ArrayList<Gosto> lista) {
        gostos = lista;
        ArrayAdapter<Gosto> adapter = new InteractiveArrayAdapter(this, lista);
        listView.setAdapter(adapter);
        dismissProgressDialog();
    }

    @Override
    public void onLoadedError(String s) {
        showErrorMessage();
    }
}
