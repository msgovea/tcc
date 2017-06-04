package br.edu.puccamp.app.gosto_musical;

/**
 * Created by mgovea on 04/06/2017.
 */
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import br.edu.puccamp.app.R;

public class InteractiveArrayAdapter extends ArrayAdapter<Gosto> {

    private final List<Gosto> list;
    private final Activity context;



    public InteractiveArrayAdapter(Activity context, List<Gosto> list) {
        super(context, R.layout.gosto_musical_list, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.gosto_musical_list, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.text_name_gosto);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.checkbox_gosto);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            Gosto element = (Gosto) viewHolder.checkbox
                                    .getTag();
                            element.setSelecionado(buttonView.isChecked());

                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getGosto());
        holder.checkbox.setChecked(list.get(position).getSelecionado());
        return view;
    }
}