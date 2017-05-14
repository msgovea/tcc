package br.edu.puccamp.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.gosto_musical.Gosto;
import br.edu.puccamp.app.gosto_musical.GostosAdapter;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.QuestionsAdapter;

public class GostoMusicalActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GostosAdapter mAdapter;


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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<Gosto> lista = new ArrayList<>();
        lista.add(new Gosto("teste", null, null));
        lista.add(new Gosto("teste2", null, null));
        lista.add(new Gosto("teste3", null, null));

        mRecyclerView = (RecyclerView) findViewById(R.id.listPosts);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new GostosAdapter(this, getGostos(lista)));

    }

    private List<Gosto> getGostos(final ArrayList<Gosto> lista) {
        return new ArrayList<Gosto>() {{
            for (Gosto item : lista) {
                add(item);
            }
//            add(new Question("Paloma Silva", "Tester",
//                    "http://kingofwallpapers.com/girl/girl-011.jpg", "Nov 20, 6:12 PM",
//                    "What is the first step to transform an idea into an actual project?"));
//            add(new Question("Paloma Silva", "Scrum Master",
//                    "http://weknowyourdreams.com/images/girl/girl-03.jpg", "Nov 20, 3:48 AM",
//                    "What is your biggest frustration with taking your business/career (in a corporate) to the next level?"));
//            add(new Question("Janaine Cristina", "Web Developer",
//                    "http://www.viraldoza.com/wp-content/uploads/2014/03/8876509-lily-pretty-girl.jpg", "Nov 20, 6:12 PM",
//                    "What is the first step to transform an idea into an actual project?"));
//            add(new Question("Wagner", "API Developer",
//                    "http://kingofwallpapers.com/girl/girl-Web Developer019.jpg", "Nov 20, 6:12 PM",
//                    "What is the first step to transform an idea into an actual project?"));
//            add(new Question("Mateus Govêa", "Android Developer",
//                    "https://scontent.fcpq3-1.fna.fbcdn.net/v/t1.0-9/11918928_1012801065406820_5528279907234667073_n.jpg?oh=1afd1268531b58274fd34090bc90d46c&oe=598B0484", "Nov 20, 6:12 PM",
//                    "What is the first step to transform an idea into an actual project?"));
        }};
    }

}
