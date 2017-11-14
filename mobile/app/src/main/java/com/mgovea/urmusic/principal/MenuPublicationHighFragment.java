package com.mgovea.urmusic.principal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mgovea.urmusic.R;
import com.mgovea.urmusic.async.publication.AsyncFriendsPublication;
import com.mgovea.urmusic.async.publication.AsyncHighPublication;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.posts.QuestionsAdapter;
import com.mgovea.urmusic.posts.QuestionsAdapterHigh;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.Preferencias;
import com.mgovea.urmusic.util.RecyclerItemClickListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Fragment class for each nav menu item
 */
public class MenuPublicationHighFragment extends Fragment implements AsyncHighPublication.Listener {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private String mText;
    private int mColor;

    private View mContent;
    public static RecyclerView mRecyclerView2;
    public View mProgressView;
    private QuestionsAdapterHigh mAdapter1;
    private Preferencias prefs;
    private ListView listView;
    public static LinearLayout vazio;

    public static Fragment newInstance(String text, int color) {
        Fragment frag = new MenuPublicationHighFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putInt(ARG_COLOR, color);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            mAdapter1.atualiza();
        } catch (Exception e) {}
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

        vazio = (LinearLayout) view.findViewById(R.id.vazio);


        // initialize views
        mContent = view.findViewById(R.id.content);

        // iniciando recycleview - exibicao das publicacoes
        mRecyclerView2 = (RecyclerView) view.findViewById(R.id.listPosts2);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mProgressView = (View) view.findViewById(R.id.publication_progress);

        AppBarLayout teste_mgovea = (AppBarLayout) view.findViewById(R.id.appbar_pub);
        teste_mgovea.setVisibility(View.GONE);


        loadPublication();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

    private void loadPublication() {
        showProgress(true);

        prefs = new Preferencias(getContext());

        AsyncHighPublication sinc = new AsyncHighPublication(this);
        sinc.execute(prefs.getDadosUsuario().getCodigoUsuario());
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                mRecyclerView2.setVisibility(show ? View.GONE : View.VISIBLE);
                mRecyclerView2.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRecyclerView2.setVisibility(show ? View.GONE : View.VISIBLE);
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
                mRecyclerView2.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    // ***************************************
    // Metodos de retorno Async
    // ***************************************

    @Override
    public void onLoaded(ArrayList<Publicacao> lista) {
        mAdapter1 = new QuestionsAdapterHigh(getContext(), lista);
        mRecyclerView2.setAdapter(mAdapter1);

        mRecyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        }));

        if (mAdapter1.getItemCount() == 0) {
            mRecyclerView2.setVisibility(View.GONE);
            vazio.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView2.setVisibility(View.VISIBLE);
            vazio.setVisibility(View.GONE);
        }

        showProgress(false);
//        }
        //dismissProgressDialog();
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
            //throw new RuntimeException("This is a crash");
            //TODO MSG ERRO APP QUEBRADO
        }

    }

}
