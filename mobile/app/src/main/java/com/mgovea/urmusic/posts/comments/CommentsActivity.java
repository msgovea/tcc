package com.mgovea.urmusic.posts.comments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mgovea.urmusic.async.comments.AsyncComments;
import com.mgovea.urmusic.async.comments.AsyncMakeComment;
import com.mgovea.urmusic.entity.Comentario;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.posts.QuestionsAdapter;
import com.mgovea.urmusic.posts.QuestionsAdapterHigh;
import com.mgovea.urmusic.posts.QuestionsAdapterPerfil;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;

import java.util.ArrayList;

import com.mgovea.urmusic.R;;

public class CommentsActivity extends AbstractAsyncActivity implements AsyncComments.Listener, AsyncMakeComment.Listener {

    private RecyclerView mRecyclerView;
    public View mProgressView;
    private CommentsAdapter mAdapter;

    private Long idPublicacao;

    private ImageButton mEnviarComentario;
    private EditText mComentario;
    private Comentario comentario;

    private ArrayList<Comentario> listaComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idPublicacao = getIntent().getLongExtra("idPublicacao", 0);

        // iniciando recycleview - exibicao das publicacoes
        mRecyclerView = (RecyclerView) findViewById(R.id.list_comments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mProgressView = (View) findViewById(R.id.comments_progress);

        enviarComentario();

        //
//        ArrayList<Comentario> lista = new ArrayList<>();
//        lista.add(new Comentario(Long.valueOf("1"), Long.valueOf("1"), new Usuario("Usuário", Integer.valueOf("1")), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."));
//        lista.add(new Comentario(Long.valueOf("1"), Long.valueOf("1"), new Usuario("Usuário", Integer.valueOf("1")), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."));
//        lista.add(new Comentario(Long.valueOf("1"), Long.valueOf("1"), new Usuario("Usuário", Integer.valueOf("1")), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."));
//        lista.add(new Comentario(Long.valueOf("1"), Long.valueOf("1"), new Usuario("Usuário", Integer.valueOf("1")), "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."));
//
//
//        mAdapter = new CommentsAdapter(this, lista);
//        mRecyclerView.setAdapter(mAdapter);

        //

        loadComments();
    }

    private void enviarComentario() {
        mEnviarComentario = (ImageButton) findViewById(R.id.button_send);
        mComentario = (EditText) findViewById(R.id.edittext_input_comment);

        mEnviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mComentario.getText().toString().isEmpty()) {

                    try {
                        SharedPreferences prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
                        Gson gson = new Gson();
                        Usuario usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);

                        comentario = new Comentario(null, idPublicacao, usuario, mComentario.getText().toString());

                        mEnviarComentario.setEnabled(false);
                        mComentario.setEnabled(false);

                        AsyncMakeComment sinc = new AsyncMakeComment(CommentsActivity.this);
                        sinc.execute(comentario);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void loadComments() {
        showProgress(true);

        AsyncComments sinc = new AsyncComments(this);
        sinc.execute(idPublicacao.toString());
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
        } catch (Exception e) {
            e.getCause();
        }
    }

    // ***************************************
    // Metodos de retorno Async
    // ***************************************

    @Override
    public void onLoaded(ArrayList<Comentario> lista) {
        listaComentarios = lista;
        mAdapter = new CommentsAdapter(this, lista);
        mRecyclerView.setAdapter(mAdapter);

        showProgress(false);
    }

    @Override
    public void onLoadedError(String s) {
        showProgress(false);
        //dismissProgressDialog();
        try {
            showErrorMessage();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage(e.getMessage());
        }
    }

    @Override
    public void onLoadedComment(Long idComentario) {
        try {
            comentario.setCodigo(idComentario);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage(e.getMessage());
        }
        mAdapter.addComment(comentario);
        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);

        mEnviarComentario.setEnabled(true);
        mComentario.setEnabled(true);
        mComentario.setText(null);

        try {
            for (Publicacao p: QuestionsAdapter.mQuestions) {
                if (p.getCodigo().equals(comentario.getCodigoPublicacao())){
                    p.setQtdComentarios(p.getQtdComentarios() + 1);
                }
            }
        } catch (Exception e){}

        try {
            for (Publicacao p: QuestionsAdapterHigh.mQuestions) {
                if (p.getCodigo().equals(comentario.getCodigoPublicacao())){
                    p.setQtdComentarios(p.getQtdComentarios() + 1);
                }
            }
        } catch (Exception e){}

        try {
            for (Publicacao p: QuestionsAdapterPerfil.mQuestions) {
                if (p.getCodigo().equals(comentario.getCodigoPublicacao())){
                    p.setQtdComentarios(p.getQtdComentarios() + 1);
                }
            }
        } catch (Exception e){}


        Toast.makeText(getApplicationContext(), getString(R.string.comment_success), Toast.LENGTH_SHORT).show();
    }


}
