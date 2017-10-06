package com.mgovea.urmusic.gosto_musical;

/**
 * Created by mgovea on 04/06/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgovea.urmusic.entity.GostosMusicai;

import java.util.ArrayList;
import java.util.List;

import com.mgovea.urmusic.R;;

public class InteractiveArrayAdapterList extends RecyclerView.Adapter<InteractiveArrayAdapterList.ViewHolder>  {

    private List<GostosMusicai> mGostos;
    private Context mContext;

    public InteractiveArrayAdapterList(Context context, List<GostosMusicai> gostos) {
        mContext = context;

        ArrayList<GostosMusicai> gostosNaoFavoritos = new ArrayList<>();

        for (GostosMusicai gosto: gostos
             ) {
            if (!gosto.getFavorito()){
                gostosNaoFavoritos.add(gosto);
            }
        }

        mGostos = gostosNaoFavoritos;
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