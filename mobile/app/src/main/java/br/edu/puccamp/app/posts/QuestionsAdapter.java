package br.edu.puccamp.app.posts;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
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
import java.util.Random;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.posts.comments.CommentsActivity;
import br.edu.puccamp.app.posts.options.CustomBottomSheetDialogFragment;
import br.edu.puccamp.app.profile.ProfileTabbedActivity;
import br.edu.puccamp.app.util.API;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Publicacao> mQuestions;
    private Context mContext;

    @Nullable
    private OnItemClickListener listener;
    private BottomSheetDialogFragment bottomSheetDialogFragment;


    public interface OnItemClickListener {
        void onClick(Question question, int position);
    }

    public QuestionsAdapter(Context context, List<Publicacao> questions) {
        mContext = context;
        mQuestions = questions;
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

            String[] partes = data.split("-");

            dataFinal = partes[2] + " " + theMonth(Integer.parseInt(partes[1])) + " " +  partes[0];

            return dataFinal;
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    public String theMonth(int month){
        String[] monthNames = mContext.getResources().getStringArray(R.array.month); //{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month-1];
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Publicacao question = mQuestions.get(position);

        Random random = new Random();


        holder.avatar.setImageURI("https://scontent.fcpq3-1.fna.fbcdn.net/v/t1.0-9/11918928_1012801065406820_5528279907234667073_n.jpg?oh=d3b42bf86a3fc19181b84efd9a7a2110&oe=5A293884");
        holder.textAuthorName.setText(question.getUsuario().getNome());
        holder.textJobTitle.setText(question.getUsuario().getCidade() +  " - " + question.getUsuario().getEstado());
        holder.textDate.setText(trataData(question.getDataPublicacao()));
        holder.textQuestion.setText(question.getConteudo());
        holder.textLikesCount.setText((String.valueOf(random.nextInt(99))));
        holder.textChatCount.setText((String.valueOf(random.nextInt(99))) + " " + mContext.getResources().getString(R.string.response));



        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(1000);
        //holder.firstFilter.setBackgroundDrawable(drawable);
        GradientDrawable drawable1 = new GradientDrawable();
        drawable1.setCornerRadius(1000);
        //holder.secondFilter.setBackgroundDrawable(drawable1);
    }

    @Nullable
    public OnItemClickListener getListener() {

        return listener;
    }

    public Publicacao getItem(int position) {
        return mQuestions.get(position);
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textAuthorName;
        TextView textJobTitle;
        TextView textDate;
        TextView textQuestion;
        TextView textLikesCount;
        TextView textChatCount;
        SimpleDraweeView avatar;
        private final AppCompatImageView appCompatImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textAuthorName  = (TextView) itemView.findViewById(R.id.user_name_publication);
            textJobTitle    = (TextView) itemView.findViewById(R.id.text_job_title);
            textDate        = (TextView) itemView.findViewById(R.id.text_date);
            textQuestion    = (TextView) itemView.findViewById(R.id.text_publication);
            textLikesCount  = (TextView) itemView.findViewById(R.id.text_likes_count);
            textChatCount   = (TextView) itemView.findViewById(R.id.text_chat_count);
            avatar  = (SimpleDraweeView) itemView.findViewById(R.id.avatar_publication);

            appCompatImageView = (AppCompatImageView) itemView.findViewById(R.id.view_settings);

            avatar.setOnClickListener(this);
            textAuthorName.setOnClickListener(this);
            appCompatImageView.setOnClickListener(this);
            textChatCount.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position  =   getAdapterPosition();

            Log.e("1", view.getId()+"");

            Intent intent;

            switch (view.getId()){
                case R.id.avatar_publication:
                    intent = new Intent(view.getContext(), ProfileTabbedActivity.class);
                    intent.putExtra("idUsuario", Long.valueOf(getItem(position).getUsuario().getCodigoUsuario()));
                    view.getContext().startActivity(intent);
                    break;
                case R.id.view_settings:
                    Log.e("MGOVEAA", "SELECTED "+position);
                    // TODO 27/08

                    Bundle args = new Bundle();
                    args.putLong(API.PUBLICACAO, getItem(position).getCodigo());
                    bottomSheetDialogFragment = new CustomBottomSheetDialogFragment();
                    bottomSheetDialogFragment.setArguments(args);
                    bottomSheetDialogFragment.show(((FragmentActivity)mContext).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

                    Log.e("APÓS - MGOVEAA", "SELECTED "+position);

                    //
                    break;
                case R.id.user_name_publication:
                    intent = new Intent(view.getContext(), ProfileTabbedActivity.class);
                    intent.putExtra("idUsuario", Long.valueOf(getItem(position).getUsuario().getCodigoUsuario()));
                    view.getContext().startActivity(intent);
                    break;
                case R.id.text_chat_count:
                    intent = new Intent(view.getContext(), CommentsActivity.class);
                    intent.putExtra("idPublicacao", Long.valueOf(getItem(position).getCodigo()));
                    view.getContext().startActivity(intent);
                default:
                    Log.e("mgoveaaa", view.getId()+"");
            }
        }
    }


}
