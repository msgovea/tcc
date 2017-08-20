package br.edu.puccamp.app.posts;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

import br.edu.puccamp.app.R;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Question> mQuestions;
    private Context mContext;

    @Nullable
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        if (null != listener) {
            this.listener = listener;
        }
    }

    public interface OnItemClickListener {
        void onClick(Question question, int position);
    }

    public QuestionsAdapter(Context context, List<Question> questions) {
        mContext = context;
        mQuestions = questions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false));
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Question> questions) {
        this.mQuestions = questions;
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Question question = mQuestions.get(position);

        Random random = new Random();


        holder.avatar.setImageURI(question.getAuthorAvatar());
        holder.textAuthorName.setText(question.getAuthorName());
        holder.textJobTitle.setText(question.getAuthorJobTitle());
        holder.textDate.setText(question.getDate());
        holder.textQuestion.setText(question.getText());
        holder.textLikesCount.setText((String.valueOf(random.nextInt(99))));
        holder.textChatCount.setText((String.valueOf(random.nextInt(99))) + " " + mContext.getResources().getString(R.string.response));

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(question, position);
                    Log.e("MATEUS", question + "" + position);
                }
            }
        });


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

    public Question getItem(int position) {
        return mQuestions.get(position);
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

        public ViewHolder(View itemView) {
            super(itemView);

            textAuthorName = (TextView) itemView.findViewById(R.id.text_name);
            textJobTitle = (TextView) itemView.findViewById(R.id.text_job_title);
            textDate = (TextView) itemView.findViewById(R.id.text_date);
            textQuestion = (TextView) itemView.findViewById(R.id.text_question);
            textLikesCount = (TextView) itemView.findViewById(R.id.text_likes_count);
            textChatCount = (TextView) itemView.findViewById(R.id.text_chat_count);
            avatar = (SimpleDraweeView) itemView.findViewById(R.id.avatar);

            avatar.setOnClickListener(this);
            textAuthorName.setOnClickListener(this);
            AppCompatImageView appCompatImageView = (AppCompatImageView) itemView.findViewById(R.id.view_settings);
            appCompatImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position  =   getAdapterPosition();

            Log.e("1", view.getId()+"");

            switch (view.getId()){
                case R.id.avatar:
                    Log.e("MGOVEAA", "Selected"+position);

                    break;
                case R.id.view_settings:
                    Log.e("MGOVEAA", "SELECTED "+position);
                default:
                    Log.e("mgoveaaa", view.getId()+"");
            }
        }
    }
}
