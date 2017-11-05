package com.mgovea.urmusic.posts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.mgovea.urmusic.async.publication.AsyncLikePublication;
import com.mgovea.urmusic.entity.Curtida;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.posts.comments.CommentsActivity;
import com.mgovea.urmusic.posts.options.CustomBottomSheetDialogFragment;
import com.mgovea.urmusic.profile.ProfileTabbedActivity;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.Preferencias;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import com.mgovea.urmusic.R;;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Publicacao> mQuestions;
    private Context mContext;
    private Usuario usuario;

    private Preferencias pref;

    @Nullable
    private OnItemClickListener listener;
    public static BottomSheetDialogFragment bottomSheetDialogFragment;


    public interface OnItemClickListener {
        void onClick(Question question, int position);
    }

    public QuestionsAdapter(Context context, List<Publicacao> questions) {
        mContext = context;
        mQuestions = questions;

        pref = new Preferencias(context);
        usuario = pref.getDadosUsuario();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false));
    }

    public List<Publicacao> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Publicacao> questions) {
        this.mQuestions = questions;
    }

    private String trataData(String data) {
        try {
            String dataFinal;

            Date date = new Date(Long.valueOf(data).longValue());
            SimpleDateFormat dateFormat;

            if (Locale.getDefault().getLanguage().equals("pt")) {
                dateFormat = new SimpleDateFormat("dd MMM, h:mm a", Locale.getDefault());
            } else {
                dateFormat = new SimpleDateFormat("MMM dd, h:mm a", Locale.getDefault());
            }

            dateFormat.setTimeZone(TimeZone.getTimeZone("Brazil/East"));
            dataFinal = dateFormat.format(date);
            //dataFinal = date.toString();

            return dataFinal;
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Publicacao question = mQuestions.get(position);

        try {
            holder.avatar.setImageURI(API.URL_IMGS + API.IMG_PERFIL + question.getUsuario().getCodigoUsuario() + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
            //TODO ERRO CRASH APP
        }

        holder.textAuthorName.setText(question.getUsuario().getNome());
        holder.textJobTitle.setText(question.getUsuario().getCidade() + " - " + question.getUsuario().getEstado());
        holder.textDate.setText(trataData(question.getDataPublicacao()));
        if (question.getConteudo() == null) {
            holder.textQuestion.setVisibility(View.GONE);
        } else {
            holder.textQuestion.setVisibility(View.VISIBLE);
            holder.textQuestion.setText(question.getConteudo());
        }
        holder.textLikesCount.setText(question.getLikes().size() + "");
        holder.textChatCount.setText(question.getQtdComentarios() + " " + mContext.getResources().getString(R.string.response));

        holder.imgFollow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_vazio));
        for (Usuario u : question.getLikes()) {
            if (u.getCodigoUsuario().equals(usuario.getCodigoUsuario())) {
                holder.imgFollow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_like_cheio));
            }
        }
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(1000);
        //holder.firstFilter.setBackgroundDrawable(drawable);
        GradientDrawable drawable1 = new GradientDrawable();
        drawable1.setCornerRadius(1000);
        //holder.secondFilter.setBackgroundDrawable(drawable1);

        if (question.isTemImagem()) {
            //holder.imgPublication.setImageURI("https://scontent.fcpq3-1.fna.fbcdn.net/v/t31.0-8/19944619_1569822496381461_679355768551827599_o.jpg?oh=c90476dc9c76683b366221e5a1746d31&oe=5A3C124F");
            holder.imgPublication.setImageURI(API.URL_IMGS + API.IMG_PUBLICACAO + question.getCodigo() + ".jpg");
            holder.imgPublication.setVisibility(View.VISIBLE);
            holder.cardView.setVisibility(View.GONE);
        } else {
            holder.imgPublication.setVisibility(View.GONE);
            if (question.getVideo() == null) {
                holder.cardView.setVisibility(View.GONE);
            } else {
                try {
                    // YOUTUBE

                    final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                        }

                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            youTubeThumbnailView.setVisibility(View.VISIBLE);
                            holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                        }
                    };

                    holder.youTubeThumbnailView.initialize("AIzaSyAcY1bGc9apDHV5hprJ0HA1-2ttIHPNOrs", new YouTubeThumbnailView.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                            try {
                                youTubeThumbnailLoader.setVideo(question.getVideo());
                            } catch (Exception e) {
                                youTubeThumbnailLoader.setVideo("ad65dIWfdwI");
                            }
                            youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                        }

                        @Override
                        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                            //write something for failure
                        }
                    });
                // YOUTUBE

                    holder.cardView.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Nullable
    public OnItemClickListener getListener() {

        return listener;
    }

    public Publicacao getItem(int position) {
        return mQuestions.get(position);
    }

    public void curtir(int position) {
        mQuestions.get(position).addLike(usuario);
        notifyDataSetChanged();
    }

    public void descurtir(int position) {
        mQuestions.get(position).removeLike(usuario);
        notifyDataSetChanged();
    }

    public void removePublicacaoPorID(Long idPublicacao) {
        for (Publicacao p : mQuestions) {
            if (p.getCodigo().equals(idPublicacao)) {
                mQuestions.remove(p);
                notifyDataSetChanged();

                bottomSheetDialogFragment.dismiss();

                //TODO MSG PUBLICACAO REMOVIDA
                Toast.makeText(mContext,
                        "Publicação removida com sucesso!",
                        Toast.LENGTH_SHORT)
                        .show();

                return;
            }
        }
        Log.e("ERROR EXCLUSÃO", "ERRO");
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AsyncLikePublication.Listener {

        TextView textAuthorName;
        TextView textJobTitle;
        TextView textDate;
        TextView textQuestion;
        TextView textLikesCount;
        TextView textChatCount;
        SimpleDraweeView avatar;
        SimpleDraweeView imgPublication;
        private final AppCompatImageView appCompatImageView;
        AppCompatImageView imgFollow;

        /*YOUTUBE*/
        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;

        protected CardView cardView;
        /*YOUTUBE*/

        public ViewHolder(View itemView) {
            super(itemView);

            textAuthorName = (TextView) itemView.findViewById(R.id.user_name_publication);
            textJobTitle = (TextView) itemView.findViewById(R.id.text_job_title);
            textDate = (TextView) itemView.findViewById(R.id.text_date);
            textQuestion = (TextView) itemView.findViewById(R.id.text_publication);
            textLikesCount = (TextView) itemView.findViewById(R.id.text_likes_count);
            textChatCount = (TextView) itemView.findViewById(R.id.text_chat_count);
            avatar = (SimpleDraweeView) itemView.findViewById(R.id.avatar_publication);
            imgPublication = (SimpleDraweeView) itemView.findViewById(R.id.img_publication);

            appCompatImageView = (AppCompatImageView) itemView.findViewById(R.id.view_settings);
            imgFollow = (AppCompatImageView) itemView.findViewById(R.id.view_likes);

            avatar.setOnClickListener(this);
            textAuthorName.setOnClickListener(this);
            appCompatImageView.setOnClickListener(this);
            textChatCount.setOnClickListener(this);
            imgFollow.setOnClickListener(this);


            /*YOUTUBE*/
            playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);

            cardView = (CardView) itemView.findViewById(R.id.carview);
            /*YOUTUBE*/
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Log.e("1", view.getId() + "");

            Intent intent;

            switch (view.getId()) {
                case R.id.view_likes:
                    if (!usuario.getCodigoUsuario().equals(getItem(position).getUsuario().getCodigoUsuario())) {
                        Curtida curtidaPublicacao = new Curtida(
                                usuario, //usuario
                                getItem(position).getCodigo()); //codigoPublicacao

                        AsyncLikePublication sinc = new AsyncLikePublication(this);
                        sinc.execute(curtidaPublicacao);
                    }
                    break;
                case R.id.avatar_publication:
                    //SE JÁ ESTA NO PERFIL, NÃO ABRE DE NOVO
                    if (view.getContext() instanceof ProfileTabbedActivity) {
                        break;
                    }
                    intent = new Intent(view.getContext(), ProfileTabbedActivity.class);
                    intent.putExtra("idUsuario", Long.valueOf(getItem(position).getUsuario().getCodigoUsuario()));
                    view.getContext().startActivity(intent);
                    break;
                case R.id.view_settings:
                    Bundle args = new Bundle();
                    args.putLong(API.PUBLICACAO, getItem(position).getCodigo());
                    args.putLong(API.USUARIO, getItem(position).getUsuario().getCodigoUsuario());
                    bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
                    bottomSheetDialogFragment.setArguments(args);
                    bottomSheetDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                    break;
                case R.id.user_name_publication:
                    //SE JÁ ESTA NO PERFIL, NÃO ABRE DE NOVO
                    if (view.getContext() instanceof ProfileTabbedActivity) {
                        break;
                    }
                    intent = new Intent(view.getContext(), ProfileTabbedActivity.class);
                    intent.putExtra("idUsuario", Long.valueOf(getItem(position).getUsuario().getCodigoUsuario()));
                    view.getContext().startActivity(intent);
                    break;
                case R.id.text_chat_count:
                    intent = new Intent(view.getContext(), CommentsActivity.class);
                    intent.putExtra("idPublicacao", Long.valueOf(getItem(position).getCodigo()));
                    view.getContext().startActivity(intent);
                    break;
                case R.id.btnYoutube_player:
                    intent = YouTubeStandalonePlayer.createVideoIntent((Activity) mContext, "AIzaSyAcY1bGc9apDHV5hprJ0HA1-2ttIHPNOrs", getItem(position).getVideo());
                    mContext.startActivity(intent);
                    break;
                default:
                    Log.e("mgoveaaa", view.getId() + "");
            }
        }

        @Override
        public void onLoadedError(String s) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setTitle(mContext.getString(R.string.error));
//            builder.setMessage(mContext.getString(R.string.error));
//            builder.setPositiveButton(mContext.getString(R.string.close), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//            builder.setCancelable(false);
//            builder.show();
            //TODO MGOVEA MSG ERRO
            Log.e("ERRO GERAL", s);
        }

        @Override
        public void onLoaded(Double l) {
            switch (l.intValue()) {
                case 1:
                    curtir(getAdapterPosition());
                    Log.e("STATUS_CURTIDA", "CURTIU");
                    break;
                case 2:
                    Log.e("STATUS_CURTIDA", "DESCURTIU");
                    descurtir(getAdapterPosition());
                    break;
                default:
                    //ERRO
                    break;
            }
        }
    }

}
