package br.edu.puccamp.app.principal;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.edu.puccamp.app.DefaultActivity;
import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.AsyncPublication;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.QuestionsAdapter;
import br.edu.puccamp.app.util.AbstractAsyncFragment;
import br.edu.puccamp.app.util.Strings;

import static android.content.Context.MODE_PRIVATE;


/**
 * Fragment class for each nav menu item
 */
public class MenuPublicationFragment extends Fragment implements AsyncPublication.Listener {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private String mText;
    private int mColor;

    private View mContent;
    private RecyclerView mRecyclerView;
    private QuestionsAdapter mAdapter;
    private SharedPreferences prefs;
    private ListView listView;

    public static Fragment newInstance(String text, int color) {
        Fragment frag = new MenuPublicationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putInt(ARG_COLOR, color);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publication, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            mText = args.getString(ARG_TEXT);
            mColor = args.getInt(ARG_COLOR);
        } else {
            mText = savedInstanceState.getString(ARG_TEXT);
            mColor = savedInstanceState.getInt(ARG_COLOR);
        }

        // initialize views
        mContent = view.findViewById(R.id.fragment_content);



        // iniciando recycleview - exibicao das publicacoes
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listPosts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        loadPublication();

        ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(getContext())
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(getContext(), config);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

    private void loadPublication() {
        //getContext().showLoadingProgressDialog();
        Gson gson = new Gson();
        prefs = getContext().getSharedPreferences(Strings.USUARIO, MODE_PRIVATE);
        Usuario usuario = gson.fromJson(prefs.getString(Strings.USUARIO, null), Usuario.class);
        AsyncPublication sinc = new AsyncPublication(this);
        sinc.execute(usuario.getCodigoUsuario().toString());
    }

    private List<Question> getQuestions(final ArrayList<Publicacao> lista) {
        return new ArrayList<Question>() {{
            for (Publicacao item : lista) {
                add(new Question(item.getUsuario().getNome(),
                        item.getUsuario().getCidade() + " - " + item.getUsuario().getEstado(),
                        "https://scontent.fcpq3-1.fna.fbcdn.net/v/t1.0-9/11918928_1012801065406820_5528279907234667073_n.jpg?oh=1afd1268531b58274fd34090bc90d46c&oe=598B0484",
                        trataData(item.getDataPublicacao()),
                        item.getConteudo()));
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

    private String trataData(String data) {
        try {
            String dataFinal;

            String[] partes = data.split("-");

//            Log.e("data1", partes[0]);
//            Log.e("data2", partes[1]);
//            Log.e("data3", partes[2]);

            dataFinal = partes[2] + " " + theMonth(Integer.parseInt(partes[1])) + " " +  partes[0];

            return dataFinal;
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    public String theMonth(int month){
        String[] monthNames = getResources().getStringArray(R.array.month); //{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month+1];
    }

    @Override
    public void onLoaded(ArrayList<Publicacao> lista) {
        mRecyclerView.setAdapter(mAdapter = new QuestionsAdapter(getContext(), getQuestions(lista)));
        //dismissProgressDialog();
    }

    @Override
    public void onLoadedError(String s) {
        //dismissProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.error));
        builder.setMessage(getString(R.string.error));
        builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //b.finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    // ***************************************
    // Metodos de retorno Async
    // ***************************************

}
