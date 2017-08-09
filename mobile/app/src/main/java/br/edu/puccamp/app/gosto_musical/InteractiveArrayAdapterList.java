package br.edu.puccamp.app.gosto_musical;

/**
 * Created by mgovea on 04/06/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Random;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.entity.GostosMusicai;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.QuestionsAdapter;

public class InteractiveArrayAdapterList extends RecyclerView.Adapter<InteractiveArrayAdapterList.ViewHolder>  {

    private List<GostosMusicai> mGostos;
    private Context mContext;

    public InteractiveArrayAdapterList(Context context, List<GostosMusicai> gostos) {
        mContext = context;
        mGostos = gostos;
    }

    @Override
    public InteractiveArrayAdapterList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InteractiveArrayAdapterList.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gosto_musical_list_exibicao, null, false));
    }

    @Override
    public void onBindViewHolder(InteractiveArrayAdapterList.ViewHolder holder, int position) {
        GostosMusicai gosto = mGostos.get(position);

        holder.text.setText(gosto.getPk().getGostoMusical().getDescricao());
    }

    @Override
    public int getItemCount() {
        return mGostos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.text_name_gosto_exibicao);
        }
    }
}