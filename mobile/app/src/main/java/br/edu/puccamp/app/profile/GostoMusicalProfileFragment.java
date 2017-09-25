package br.edu.puccamp.app.profile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.profile.AsyncProfile;
import br.edu.puccamp.app.entity.GostosMusicai;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.gosto_musical.InteractiveArrayAdapterList;


/**
 * Fragment class for each nav menu item
 */
public class GostoMusicalProfileFragment extends Fragment implements AsyncProfile.Listener {

    private String mText;
    private int mColor;

    public  View mProgressView;
    public LinearLayout mLayout;

    private InteractiveArrayAdapterList mAdapter;
    private Long idUsuario;
    private RecyclerView listView;
    private TextView mGostoFavorito;

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
        listView = (RecyclerView) view.findViewById(R.id.listPosts2);
        listView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mProgressView = (View) view.findViewById(R.id.progress_gosto_musical);
        mLayout = (LinearLayout) view.findViewById(R.id.gostos_musicais);

        mGostoFavorito = (TextView) view.findViewById(R.id.text_gosto_favorito);

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
        AsyncProfile sinc = new AsyncProfile(this);
        sinc.execute(idUsuario);
    }


    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                mLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                mLayout.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        listView.setVisibility(show ? View.GONE : View.VISIBLE);
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
                mLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }
        catch (Exception e) {
            e.getCause();
        }
    }



    @Override
    public void onLoaded(Object o) {
        try {

            List<GostosMusicai> lista = ((Usuario) o).getGostosMusicais();
            Log.e("RETORNO GOSTO MUSICAL", lista.toString());
            Log.e("IGUAL", lista.get(0).toString());
            //gostos = lista;
//        ArrayAdapter<GostosMusicai> adapter = new InteractiveArrayAdapterList(getActivity(), lista);
//        listView.setAdapter(adapter);

            for (GostosMusicai gosto : lista
                    ) {
                if (gosto.getFavorito()) {
                    mGostoFavorito.setText(gosto.getPk().getGostoMusical().getDescricao());
                }
            }

            listView.setAdapter(mAdapter = new InteractiveArrayAdapterList(getActivity(), lista));
            showProgress(false);
        }catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }

    }
}
