package com.mgovea.urmusic.posts.comments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mgovea.urmusic.async.comments.AsyncRemoveComments;
import com.mgovea.urmusic.entity.Comentario;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.posts.Question;
import com.mgovea.urmusic.posts.QuestionsAdapter;
import com.mgovea.urmusic.posts.QuestionsAdapterHigh;
import com.mgovea.urmusic.posts.QuestionsAdapterPerfil;
import com.mgovea.urmusic.profile.ProfileTabbedActivity;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.Preferencias;

import java.util.List;

import com.mgovea.urmusic.R;;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<Comentario> mComments;
    private Context mContext;
    private ProgressDialog progressDialog;


    @Nullable
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onClick(Question question, int position);
    }

    public CommentsAdapter(Context context, List<Comentario> questions) {
        mContext = context;
        mComments = questions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_list, null, false));
    }

    public List<Comentario> getQuestions() {
        return mComments;
    }

    public void setQuestions(List<Comentario> questions) {
        this.mComments = questions;
    }

    public void addComment(Comentario comentario) {
        this.mComments.add(mComments.size(), comentario);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Comentario question = mComments.get(position);

        boolean myComment = false;
        try {
            Preferencias pref = new Preferencias(mContext);
            Long idComentario = Long.valueOf(question.getUsuario().getCodigoUsuario());
            Long idUsuario = Long.valueOf(pref.getDadosUsuario().getCodigoUsuario());
            myComment = idComentario.equals(idUsuario);
        } catch (Exception e){
            e.printStackTrace();
            myComment = false;
            // TODO ERRO NAO TRATADO
        } finally {
            holder.removeComment.setVisibility(myComment ? View.VISIBLE : View.INVISIBLE);
        }

        try {
            holder.avatar.setImageURI(API.URL_IMGS + API.IMG_PERFIL + question.getUsuario().getCodigoUsuario() + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
            //TODO ERRO CRASH APP
        }
        holder.mName.setText(question.getUsuario().getNome());
        holder.mText.setText(question.getComentario());
    }

    @Nullable
    public OnItemClickListener getListener() {

        return listener;
    }

    public Comentario getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void removeComentarioByID(Long idComentario) {
        for (Comentario p : mComments) {
            if (p.getCodigo().equals(idComentario)) {
                mComments.remove(p);
                notifyDataSetChanged();

                //TODO MSG PUBLICACAO REMOVIDA
                Toast.makeText(mContext,
                        "Comentário removido com sucesso!",
                        Toast.LENGTH_SHORT)
                        .show();

                return;
            }
        }
        Log.e("ERROR EXCLUSÃO", "ERRO");
    }

    public void loading(boolean load) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setIndeterminate(true);
            }

            if (load) {
                progressDialog.setMessage(mContext.getString(R.string.loading));
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO APP QUEBRADO
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AsyncRemoveComments.Listener {

        TextView mName;
        TextView mText;
        SimpleDraweeView avatar;
        AppCompatImageView removeComment;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.user_name_comment);
            mText = (TextView) itemView.findViewById(R.id.text_comment);
            avatar = (SimpleDraweeView) itemView.findViewById(R.id.avatar_comment);
            removeComment = (AppCompatImageView) itemView.findViewById(R.id.remove_comment);

            AppCompatImageView appCompatImageView = (AppCompatImageView) itemView.findViewById(R.id.view_settings);

            removeComment.setOnClickListener(this);
            avatar.setOnClickListener(this);
            mName.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            position = getAdapterPosition();

            Log.e("POSICAO", position + "");

            Intent intent;

            switch (view.getId()) {

                case R.id.remove_comment:
                    loading(true);
                    AsyncRemoveComments sinc = new AsyncRemoveComments(this);
                    sinc.execute(getItem(position).getCodigo());
                    break;
                case R.id.avatar_comment:
                    intent = new Intent(view.getContext(), ProfileTabbedActivity.class);
                    intent.putExtra("idUsuario", Long.valueOf(getItem(position).getUsuario().getCodigoUsuario()));
                    view.getContext().startActivity(intent);
                    break;
                case R.id.user_name_comment:
                    intent = new Intent(view.getContext(), ProfileTabbedActivity.class);
                    intent.putExtra("idUsuario", Long.valueOf(getItem(position).getUsuario().getCodigoUsuario()));
                    view.getContext().startActivity(intent);
                    break;
                default:
                    Log.e("mgoveaaa error", view.getId() + "");
            }
        }

        @Override
        public void onLoaded(Boolean bool) {
            Comentario comentario = mComments.get(position);
            mComments.remove(position);
            notifyDataSetChanged();

            Toast.makeText(mContext,
                    mContext.getString(R.string.remove_comment_success),
                    Toast.LENGTH_LONG)
                    .show();

            try {
                for (Publicacao p: QuestionsAdapter.mQuestions) {
                    if (p.getCodigo().equals(comentario.getCodigoPublicacao())){
                        p.setQtdComentarios(p.getQtdComentarios() - 1);
                    }
                }
            } catch (Exception e){}

            try {
                for (Publicacao p: QuestionsAdapterHigh.mQuestions) {
                    if (p.getCodigo().equals(comentario.getCodigoPublicacao())){
                        p.setQtdComentarios(p.getQtdComentarios() - 1);
                    }
                }
            } catch (Exception e){}

            try {
                for (Publicacao p: QuestionsAdapterPerfil.mQuestions) {
                    if (p.getCodigo().equals(comentario.getCodigoPublicacao())){
                        p.setQtdComentarios(p.getQtdComentarios() - 1);
                    }
                }
            } catch (Exception e){}
            loading(false);
        }

        @Override
        public void onLoadedError(String s) {
            //TODO PALOMA TEXTO
            Toast.makeText(mContext,
                     mContext.getString(R.string.remove_comment_error) + "\n" + s,
                    Toast.LENGTH_SHORT)
                    .show();
            //FIM TODO PALOMA TEXTO
            loading(false);
        }
    }


}
