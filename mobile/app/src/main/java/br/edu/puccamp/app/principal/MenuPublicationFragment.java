package br.edu.puccamp.app.principal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.publication.AsyncPublication;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.posts.QuestionsAdapter;
import br.edu.puccamp.app.util.API;
import br.edu.puccamp.app.util.RecyclerItemClickListener;

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
    public static RecyclerView mRecyclerView;
    public  View mProgressView;
    private QuestionsAdapter mAdapter;
    private SharedPreferences prefs;
    private ListView listView;

    private AppCompatImageView mIcon;
    private AppCompatImageView mIconSearch;

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

        mProgressView = (View) view.findViewById(R.id.publication_progress);

        loadPublication();

        if(mText != null) Snackbar.make(view, R.string.publication_success , Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        //////// TODO MGOVEA1

//        mIcon = (AppCompatImageView) view.findViewById(R.id.iconAlarm);
//        mIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                prefs = getContext().getSharedPreferences(API.USUARIO, MODE_PRIVATE);
//                prefs.edit().clear().apply();
//                startActivity(new Intent(getContext(), TesteLogin.class));
//                getActivity().finish();
//            }
//        });
//
//        mIconSearch = (AppCompatImageView) view.findViewById(R.id.iconSearch);
//        mIconSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), TesteLogin.class));
//            }
//        });

        //////// END TODO
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

    private void loadPublication() {
        showProgress(true);
        //getContext().showLoadingProgressDialog();
        Gson gson = new Gson();
        prefs = getContext().getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        Usuario usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);
        AsyncPublication sinc = new AsyncPublication(this);
        sinc.execute(usuario.getCodigoUsuario().toString());
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
        mAdapter = new QuestionsAdapter(getContext(), lista);
            mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //int pos = listView.getPositionForView(view);
//                    Toast.makeText(getContext(),view.getId()+"",Toast.LENGTH_SHORT).show();
//
//                    switch(view.getId())
//                    {
//                        case R.id.avatar :
//                            Toast.makeText(getContext(),"AVATAR",Toast.LENGTH_SHORT).show();
//                            break;
//                        case R.id.view_settings :
//                            Toast.makeText(getContext(),"AVATAR",Toast.LENGTH_SHORT).show();
//                            break;
//                        default:
//                            onItemClicado(position);
//                            break;
//                    }
                }
            }));

            showProgress(false);
//        }
        //dismissProgressDialog();
    }

    private void onItemClicado(int position){
        Toast.makeText(getContext(),"Cliquei no item "+mAdapter.getItem(position).getConteudo(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadedError(String s) {
        showProgress(false);
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

}
