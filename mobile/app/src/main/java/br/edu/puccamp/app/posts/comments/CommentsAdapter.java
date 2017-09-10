package br.edu.puccamp.app.posts.comments;

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

import java.util.List;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.comments.AsyncRemoveComments;
import br.edu.puccamp.app.async.publication.AsyncLikePublication;
import br.edu.puccamp.app.entity.Comentario;
import br.edu.puccamp.app.entity.Curtida;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.profile.ProfileTabbedActivity;
import br.edu.puccamp.app.util.API;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<Comentario> mComments;
    private Context mContext;

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
        this.mComments.add(comentario);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Comentario question = mComments.get(position);

        holder.avatar.setImageURI("https://scontent.fcpq3-1.fna.fbcdn.net/v/t1.0-9/11918928_1012801065406820_5528279907234667073_n.jpg?oh=d3b42bf86a3fc19181b84efd9a7a2110&oe=5A293884");
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AsyncRemoveComments.Listener {

        TextView mName;
        TextView mText;
        SimpleDraweeView avatar;
        AppCompatImageView removeComment;

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
            int position = getAdapterPosition();

            Intent intent;

            switch (view.getId()){

                case R.id.remove_comment:
                    //TODO MGOVEA - MUDAR ORDEM QUANDO API OK
                    mComments.remove(position);
                    notifyDataSetChanged();
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
                    Log.e("mgoveaaa error", view.getId()+"");
            }
        }

        @Override
        public void onLoaded(Boolean bool) {
            notifyDataSetChanged();
        }

        @Override
        public void onLoadedError(String s) {

        }
    }


}
