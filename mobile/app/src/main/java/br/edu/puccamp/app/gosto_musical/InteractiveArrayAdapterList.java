package br.edu.puccamp.app.gosto_musical;

/**
 * Created by mgovea on 04/06/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import br.edu.puccamp.app.R;

public class InteractiveArrayAdapterList extends ArrayAdapter<Gosto> {

    private final List<Gosto> list;
    private final Activity context;



    public InteractiveArrayAdapterList(Activity context, List<Gosto> list) {
        super(context, R.layout.gosto_musical_list_exibicao, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.gosto_musical_list_exibicao, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.text_name_gosto);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getDescricao());
        return view;
    }
}