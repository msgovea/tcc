package br.edu.puccamp.app;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.puccamp.app.async.AsyncGostoMusical;
import br.edu.puccamp.app.async.AsyncPublication;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.gosto_musical.Gosto;
//import br.edu.puccamp.app.gosto_musical.GostosAdapter;
import br.edu.puccamp.app.gosto_musical.InteractiveArrayAdapter;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.QuestionsAdapter;
import br.edu.puccamp.app.util.AbstractAsyncActivity;

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
                    if (gosto.getSelecionado()) selecionado = true;
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
        startActivity(new Intent(getApplicationContext(), DefaultActivity.class));
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
