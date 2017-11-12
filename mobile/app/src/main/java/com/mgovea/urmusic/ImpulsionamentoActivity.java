package com.mgovea.urmusic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mgovea.urmusic.async.gosto_musical.AsyncGostoMusical;
import com.mgovea.urmusic.async.gosto_musical.AsyncMakeGostoMusical;
import com.mgovea.urmusic.entity.GostoUsuario;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.gosto_musical.Gosto;
import com.mgovea.urmusic.gosto_musical.InteractiveArrayAdapter;
import com.mgovea.urmusic.gosto_musical.InteractiveArrayAdapterFavorito;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;

import java.util.ArrayList;

//import com.mgovea.urmusic.gosto_musical.GostosAdapter;

public class ImpulsionamentoActivity extends AbstractAsyncActivity implements AsyncGostoMusical.Listener {

    private ListView listView;
    private Long mIdPublicacao;


    ArrayList<Gosto> gostoSelecionado = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                //finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem*/
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIdPublicacao = getIntent().getLongExtra(API.PUBLICACAO, 0);

        setContentView(R.layout.activity_impulsionamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                impulsionar();
            }
        });

        listView = (ListView) findViewById(R.id.listGostosFavoritos);

        obtemGostos();
    }

    private void impulsionar(){
        GostoUsuario gostoUsuario = verificaGosto();

        Intent intent = new Intent(this, InAppBillingActivity.class);
        intent.putExtra(API.PUBLICACAO, mIdPublicacao);
        intent.putExtra(API.GOSTO, gostoUsuario.getFavorito());

        startActivity(intent);
    }

    private GostoUsuario verificaGosto() {
        Gson gson = new Gson();
        SharedPreferences prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        GostoUsuario gostoUsuario = new GostoUsuario();
        ArrayList<Integer> gostos = new ArrayList<>();

        Integer favorito = null;

        for (Gosto gosto : gostoSelecionado) {
            gostos.add(gosto.getCodigo());
            if (gosto.isFavorito()) favorito = gosto.getCodigo();
        }

        if (favorito != null) {
            gostoUsuario.setCodigosGostosMusicais(gostos);
            gostoUsuario.setCodigoUsuario((gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class)).getCodigoUsuario());
            gostoUsuario.setFavorito(favorito);
        } else {
            gostoUsuario.setFavorito(0);
        }
        return gostoUsuario;
    }

    // ***************************************
    // Metodos de retorno Async
    // ***************************************

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
        gostoSelecionado = lista;
        ArrayAdapter<Gosto> adapter = new InteractiveArrayAdapterFavorito(this, lista);
        listView.setAdapter(adapter);
        dismissProgressDialog();
    }

    @Override
    public void onLoadedError(String s) {
        showErrorMessage();
    }
}
