package br.edu.puccamp.app.profile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.publication.AsyncPublication;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.QuestionsAdapter;


/**
 * Fragment class for each nav menu item
 */
public class PublicationProfileFragment extends Fragment implements AsyncPublication.Listener {

    private String mText;
    private int mColor;

    public static RecyclerView mRecyclerView;
    public  View mProgressView;
    private QuestionsAdapter mAdapter;
    private Long idUsuario;
    private ListView listView;

    private AppCompatImageView mIcon;
    private AppCompatImageView mIconSearch;

    public static Fragment newInstance(Long idUsuario) {
        Fragment frag = new PublicationProfileFragment();
        Bundle args = new Bundle();
        args.putLong("ID", idUsuario);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_tabbed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            try {
                Bundle args = getArguments();
                idUsuario = args.getLong("ID");
            } catch (Exception e) {
                e.printStackTrace();
                //TODO MSG ERRO GERAL
            }
        } else {
            //TODO
        }


        // iniciando recycleview - exibicao das publicacoes
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listPosts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mProgressView = (View) view.findViewById(R.id.publication_progress_profile);

        loadPublication();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            idUsuario = getArguments().getLong("ID");
        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG CRASH
        }
        loadPublication();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("ID", idUsuario);
        super.onSaveInstanceState(outState);
    }

    private void loadPublication() {
        try {
            showProgress(true);
            AsyncPublication sinc = new AsyncPublication(this);
            sinc.execute(idUsuario.toString());
        } catch (Exception e) {
            e.printStackTrace();
            //TODO ERRO CRASH APP
        }
    }

    private String trataData(String data) {
        try {
            String dataFinal;

            String[] partes = data.split("-");

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

    @Override
    public void onLoaded(ArrayList<Publicacao> lista) {
            mRecyclerView.setAdapter(mAdapter = new QuestionsAdapter(getContext(), lista));
            showProgress(false);
    }

    @Override
    public void onLoadedError(String s) {
        showProgress(false);
        //dismissProgressDialog();

         try {
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
         } catch (Exception e) {
             e.printStackTrace();
             //TODO MSG ERRO APP QUEBRADO
         }
    }

}
