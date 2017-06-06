package br.edu.puccamp.app;

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

import java.util.ArrayList;

import br.edu.puccamp.app.async.AsyncMakeGostoMusical;
import br.edu.puccamp.app.entity.GostoUsuario;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.gosto_musical.Gosto;
import br.edu.puccamp.app.gosto_musical.InteractiveArrayAdapterFavorito;
import br.edu.puccamp.app.util.AbstractAsyncActivity;
import br.edu.puccamp.app.util.Strings;

//import br.edu.puccamp.app.gosto_musical.GostosAdapter;

public class GostoFavoritoActivity extends AbstractAsyncActivity implements AsyncMakeGostoMusical.Listener {

    private ListView listView;

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

        for (Gosto gosto :
                GostoMusicalActivity.gostos) {
            Log.e(null, gosto.getDescricao() + gosto.isSelecionado());
        }

        setContentView(R.layout.activity_gosto_favorito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GostoUsuario gostoUsuario = verificaGosto();

                if (gostoUsuario != null) {
                    showLoadingProgressDialog();
                    AsyncMakeGostoMusical sinc = new AsyncMakeGostoMusical(GostoFavoritoActivity.this);
                    sinc.execute(gostoUsuario);
                }
                else {
                    Snackbar.make(view, R.string.error_gosto_musical , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        listView = (ListView) findViewById(R.id.listGostosFavoritos);

        obtemGostos();
    }

    private GostoUsuario verificaGosto() {
        Gson gson = new Gson();
        SharedPreferences prefs = getSharedPreferences(Strings.USUARIO, MODE_PRIVATE);
        GostoUsuario gostoUsuario = new GostoUsuario();
        ArrayList<Integer> gostos = new ArrayList<>();

        Integer favorito = null;

        for (Gosto gosto : gostoSelecionado) {
            gostos.add(gosto.getCodigo());
            if (gosto.isFavorito()) favorito = gosto.getCodigo();
        }

        if (favorito != null) {
            gostoUsuario.setCodigosGostosMusicais(gostos);
            gostoUsuario.setCodigoUsuario((gson.fromJson(prefs.getString(Strings.USUARIO, null), Usuario.class)).getCodigoUsuario());
            gostoUsuario.setFavorito(favorito);

            return gostoUsuario;
        }

        return null;
    }

    private void obtemGostos() {

        for (Gosto gosto : GostoMusicalActivity.gostos) {
            if (gosto.isSelecionado()){
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
    public void onLoadedError(String s) {
        showErrorMessage();
    }

    @Override
    public void onLoadedPublication(Boolean bool) {
        if (bool) {
            dismissProgressDialog();
            startActivity(new Intent(getApplicationContext(), DefaultActivity.class));
            finish();
        }
        else showErrorMessage();

    }
}
