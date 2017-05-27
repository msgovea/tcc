package br.edu.puccamp.app.gosto_musical;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.posts.Question;

public class GostosAdapter extends RecyclerView.Adapter<GostosAdapter.ViewHolder> {

    private List<Gosto> mQuestions;
    private Context mContext;

    public GostosAdapter(Context context, List<Gosto> questions) {
        mContext = context;
        mQuestions = questions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gosto_musical_list, null, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gosto gosto = mQuestions.get(position);

        holder.textAuthorName.setText(gosto.getGosto());
        //holder.textQuestion.setText(gosto.getText());


        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(1000);
        //holder.firstFilter.setBackgroundDrawable(drawable);
        GradientDrawable drawable1 = new GradientDrawable();
        drawable1.setCornerRadius(1000);
        //holder.secondFilter.setBackgroundDrawable(drawable1);
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textAuthorName;
        TextView textJobTitle;
        TextView textDate;
        TextView textQuestion;
        TextView firstFilter;
        TextView secondFilter;
        SimpleDraweeView avatar;

        public ViewHolder(View itemView) {
            super(itemView);

            textAuthorName = (TextView) itemView.findViewById(R.id.text_name_gosto);
            //textJobTitle = (TextView) itemView.findViewById(R.id.text_job_title);
            //textDate = (TextView) itemView.findViewById(R.id.text_date);
            //textQuestion = (TextView) itemView.findViewById(R.id.text_question);
            //firstFilter = (TextView) itemView.findViewById(R.id.filter_first);
            //secondFilter = (TextView) itemView.findViewById(R.id.filter_second);
            //avatar = (SimpleDraweeView) itemView.findViewById(R.id.avatar);
        }
    }
}
