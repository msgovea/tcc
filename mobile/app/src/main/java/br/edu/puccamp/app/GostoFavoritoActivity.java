package br.edu.puccamp.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.edu.puccamp.app.async.AsyncGostoMusical;
import br.edu.puccamp.app.gosto_musical.Gosto;
import br.edu.puccamp.app.gosto_musical.InteractiveArrayAdapter;
import br.edu.puccamp.app.gosto_musical.InteractiveArrayAdapterFavorito;
import br.edu.puccamp.app.util.AbstractAsyncActivity;

//import br.edu.puccamp.app.gosto_musical.GostosAdapter;

public class GostoFavoritoActivity extends AbstractAsyncActivity implements AsyncGostoMusical.Listener {

    private ListView listView;
    //private ArrayList<Gosto> gostos;
//    private GostosAdapter mAdapter;
    ArrayList<Gosto> gostoSelecionado = new ArrayList<Gosto>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recupera os parâmetros passados pelo atributo estatico
        //gostos = (ArrayList<Gosto>) getIntent().getSerializableExtra("dados");

        for (Gosto gosto :
                GostoMusicalActivity.gostos) {
            Log.e(null, gosto.getDescricao() + gosto.getSelecionado());
        }

        setContentView(R.layout.activity_gosto_favorito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean selecionado = false;

                for (Gosto gosto : gostoSelecionado) {
                    if (gosto.getFavorito()) selecionado = true;

                }

                if (selecionado) Snackbar.make(view, "Gosto musical selecionado com sucesso!" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                else Snackbar.make(view, R.string.error_gosto_musical , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

//                Snackbar.make(view, "Salvando as informações, teste." + , Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        listView = (ListView) findViewById(R.id.listGostosFavoritos);


//        ArrayAdapter<Gosto> adapter = new InteractiveArrayAdapter(this, getGostos(lista));
 //       listView.setAdapter(adapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //mRecyclerView.setAdapter(mAdapter = new GostosAdapter(this, listaOficial));

        obtemGostos();
    }

    private void obtemGostos() {

        for (Gosto gosto : GostoMusicalActivity.gostos) {
            if (gosto.getSelecionado()){
                gostoSelecionado.add(gosto);
            }
        }
        ArrayAdapter<Gosto> adapter = new InteractiveArrayAdapterFavorito(this, gostoSelecionado);
        listView.setAdapter(adapter);
//        showLoadingProgressDialog();
//        AsyncGostoMusical sinc = new AsyncGostoMusical(this);
//        sinc.execute();
    }


    // ***************************************
    // Metodos de retorno Async
    // ***************************************
    @Override
    public void onLoaded(ArrayList<Gosto> lista) {
//        GostoMusicalActivity.gostos = lista;
//        ArrayAdapter<Gosto> adapter = new InteractiveArrayAdapter(this, lista);
//        listView.setAdapter(adapter);
//        dismissProgressDialog();
    }

    @Override
    public void onLoadedError(String s) {
        showErrorMessage();
    }
}
