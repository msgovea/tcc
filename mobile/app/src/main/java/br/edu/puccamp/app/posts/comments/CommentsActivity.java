package br.edu.puccamp.app.posts.comments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.AsyncComments;
import br.edu.puccamp.app.entity.Comentario;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.posts.QuestionsAdapter;
import br.edu.puccamp.app.util.RecyclerItemClickListener;

public class CommentsActivity extends AppCompatActivity implements AsyncComments.Listener {

    private RecyclerView mRecyclerView;
    public  View mProgressView;
    private CommentsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // iniciando recycleview - exibicao das publicacoes
        mRecyclerView = (RecyclerView) findViewById(R.id.list_comments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mProgressView = (View) findViewById(R.id.comments_progress);

        //
        ArrayList<Comentario> lista = new ArrayList<>();
        lista.add(new Comentario(Long.valueOf("1"), Long.valueOf("1"), new Usuario("Mateus", Integer.valueOf("1")), "Teste"));
        mAdapter = new CommentsAdapter(this, lista);
        mRecyclerView.setAdapter(mAdapter);

        //

//        loadComments();
    }

    private void loadComments() {
        showProgress(true);

        AsyncComments sinc = new AsyncComments(this);
        //sinc.execute(publicacao);
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
    public void onLoaded(ArrayList<Comentario> lista) {
        mAdapter = new CommentsAdapter(this, lista);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
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
    }

    @Override
    public void onLoadedError(String s) {
        showProgress(false);
        //dismissProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
