package br.edu.puccamp.app.gosto_musical;

/**
 * Created by mgovea on 04/06/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import br.edu.puccamp.app.R;

public class InteractiveArrayAdapterFavorito extends ArrayAdapter<Gosto> {

    private final List<Gosto> list;
    private final Activity context;
    private RadioButton favorito;



    public InteractiveArrayAdapterFavorito(Activity context, List<Gosto> list) {
        super(context, R.layout.gosto_favorito_list, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected RadioButton radioButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.gosto_favorito_list, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.text_name_gosto_favorito);
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.radioButton);
            viewHolder.radioButton
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            Gosto element = (Gosto) viewHolder.radioButton.getTag();
                            element.setFavorito(buttonView.isChecked());
                            if (favorito != null) {
                                favorito.setChecked(false);
                                Gosto old_favorito = (Gosto) favorito.getTag();
                                old_favorito.setFavorito(false);
                            }

                            favorito = viewHolder.radioButton;
                        }
                    });
            viewHolder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.radioButton.setChecked(true);
                    //favorito = viewHolder.radioButton;
                }
            });
            view.setTag(viewHolder);
            viewHolder.radioButton.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).radioButton.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getDescricao());
        holder.radioButton.setChecked(list.get(position).isFavorito());
        return view;
    }
}