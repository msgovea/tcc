package br.edu.puccamp.app;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.gosto_musical.Gosto;
//import br.edu.puccamp.app.gosto_musical.GostosAdapter;
import br.edu.puccamp.app.gosto_musical.InteractiveArrayAdapter;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.QuestionsAdapter;

public class GostoMusicalActivity extends ListActivity {

    private ListView mRecyclerView;
//    private GostosAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gosto_musical);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Salvando as informações, aguarde.", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        ArrayList<Gosto> lista = new ArrayList<>();
        lista.add(new Gosto("teste", null, null));
        lista.add(new Gosto("teste2", null, null));
        lista.add(new Gosto("teste3", null, null));
        lista.add(new Gosto("teste4", null, null));
        lista.add(new Gosto("teste5", null, null));
        lista.add(new Gosto("teste6", null, null));
        lista.add(new Gosto("teste7", null, null));
        lista.add(new Gosto("teste8", null, null));
        lista.add(new Gosto("teste9", null, null));
        lista.add(new Gosto("teste10", null, null));
        lista.add(new Gosto("teste31", null, null));
        lista.add(new Gosto("teste32", null, null));
        lista.add(new Gosto("teste33", null, null));
        lista.add(new Gosto("teste34", null, null));
        lista.add(new Gosto("teste35", null, null));
        lista.add(new Gosto("teste36", null, null));
        lista.add(new Gosto("teste37", null, null));
        lista.add(new Gosto("teste38", null, null));
        lista.add(new Gosto("teste39", null, null));

        mRecyclerView = (ListView) findViewById(R.id.listPosts);

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<Gosto> listaOficial = getGostos(lista);
        //mRecyclerView.setAdapter(mAdapter = new GostosAdapter(this, listaOficial));


        ArrayAdapter<Gosto> adapter = new InteractiveArrayAdapter(this, getGostos(lista));
        setListAdapter(adapter);
    }

    private List<Gosto> getGostos(final ArrayList<Gosto> lista) {
        return new ArrayList<Gosto>() {{
            for (Gosto item : lista) {
                add(item);
            }
        }};
    }

}
