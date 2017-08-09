package br.edu.puccamp.app.profile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.AsyncProfile;
import br.edu.puccamp.app.async.AsyncPublication;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.gosto_musical.Gosto;
import br.edu.puccamp.app.gosto_musical.InteractiveArrayAdapter;
import br.edu.puccamp.app.gosto_musical.InteractiveArrayAdapterList;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.QuestionsAdapter;


/**
 * Fragment class for each nav menu item
 */
public class GostoMusicalProfileFragment extends Fragment implements AsyncProfile.Listener {

    private String mText;
    private int mColor;

    private RecyclerView mRecyclerView;
    public  View mProgressView;
    private QuestionsAdapter mAdapter;
    private Long idUsuario;
    private ListView listView;

    private AppCompatImageView mIcon;
    private AppCompatImageView mIconSearch;

    public static Fragment newInstance(Long idUsuario) {
        Fragment frag = new GostoMusicalProfileFragment();
        Bundle args = new Bundle();
        args.putLong("ID", idUsuario);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gostomusical_tabbed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            idUsuario = args.getLong("ID");
        } else {
            //TODO
        }


        // iniciando recycleview - exibicao das publicacoes
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listPosts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mProgressView = (View) view.findViewById(R.id.publication_progress);

        loadPublication();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putString(ARG_TEXT, mText);
        //outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

    private void loadPublication() {
        showProgress(true);
        //getContext().showLoadingProgressDialog();
        Gson gson = new Gson();
//        AsyncPublication sinc = new AsyncPublication(this);
//        sinc.execute(idUsuario.toString());
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
        return monthNames[month-1];
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                mRecyclerView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mProgressView.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply show
                // and hide the relevant UI components.
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }
        catch (Exception e) {
            e.getCause();
        }
    }

    // ***************************************
    // Metodos de retorno Async
    // ***************************************

//    @Override
//    public void onLoaded(ArrayList<Publicacao> lista) {
//        List<Question> l = getQuestions(lista);
////        if (l.size() == 0) {
////            MainActivity i = (MainActivity) getActivity();
////            //TODO
////            i.openPublication(R.id.menu_post);
////        } else {
//            mRecyclerView.setAdapter(mAdapter = new QuestionsAdapter(getContext(), getQuestions(lista)));
//            showProgress(false);
////        }
//        //dismissProgressDialog();
//    }

//    @Override
//    public void onLoadedError(String s) {
//        showProgress(false);
//        //dismissProgressDialog();
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle(getString(R.string.error));
//        builder.setMessage(getString(R.string.error));
//        builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //b.finish();
//            }
//        });
//        builder.setCancelable(false);
//        builder.show();
//    }


    @Override
    public void onLoaded(Object o) {
        List<Gosto> lista = null;
        ((Usuario)o).getGostosMusicais();
        //gostos = lista;
        ArrayAdapter<Gosto> adapter = new InteractiveArrayAdapterList(getActivity(), lista);
        listView.setAdapter(adapter);

    }
}
