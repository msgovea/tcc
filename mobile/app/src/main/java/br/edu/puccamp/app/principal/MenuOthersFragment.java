package br.edu.puccamp.app.principal;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.puccamp.app.*;
import br.edu.puccamp.app.listview.AdapterListView;
import br.edu.puccamp.app.listview.ItemListView;
import br.edu.puccamp.app.util.Strings;

import static android.content.Context.MODE_PRIVATE;


/**
 * Fragment class for each nav menu item
 */
public class MenuOthersFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private String mText;
    private int mColor;

    private View mContent;
    private SharedPreferences prefs;
    private ListView listView;
    private ArrayList<ItemListView> itens;
    private AdapterListView adapterListView;

//    private BottomNavigationView bottomNavigationView;


    public static Fragment newInstance(String text, int color) {
        Fragment frag = new MenuOthersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putInt(ARG_COLOR, color);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_others_publication, container, false);
    }

    private void createListView()
    {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemListView>();
        ItemListView item1 = new ItemListView("Guilherme Biff", R.drawable.ic_alarm,1);
        ItemListView item2 = new ItemListView("Lucas Volgarini", R.drawable.ic_alarm,2);
        ItemListView item3 = new ItemListView("Eduardo Ricoldi", R.drawable.ic_alarm,3);
        ItemListView item4 = new ItemListView("Felipe Panngo", R.drawable.ic_alarm,4);

        itens.add(item1);
        itens.add(item2);
        itens.add(item3);
        itens.add(item4);

        //Cria o adapter
        adapterListView = new AdapterListView(getContext(), itens);

        //Define o Adapter
        listView.setAdapter(adapterListView);
        //Cor quando a lista é selecionada para ralagem.
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        //Pega o item que foi selecionado.
        ItemListView item = adapterListView.getItem(arg2);
        //Demostração
        if (item.getTexto().equals("Felipe Panngo")){
            SharedPreferences prefs = getContext().getSharedPreferences(Strings.USUARIO, MODE_PRIVATE);
            prefs.edit().clear().apply();
            startActivity(new Intent(getActivity(), br.edu.puccamp.app.MainActivity.class));
            getActivity().finish();
        }
        Toast.makeText(getContext(), "Você Clicou em: " + item.getTexto(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.navigation_menu);


        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            mText = args.getString(ARG_TEXT);
            mColor = args.getInt(ARG_COLOR);
        } else {
            mText = savedInstanceState.getString(ARG_TEXT);
            mColor = savedInstanceState.getInt(ARG_COLOR);
        }

        // initialize views
        mContent = view.findViewById(R.id.fragment_content);


//        mIcon = (AppCompatImageView) findViewById(R.id.iconAlarm);
//        mIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                prefs = getSharedPreferences(Strings.USUARIO, MODE_PRIVATE);
//                prefs.edit().clear().apply();
//                startActivity(new Intent(DefaultActivity.this, br.edu.puccamp.app.MainActivity.class));
//                finish();
//            }
//        });

        String[] lista = {"teste", "teste2"};

        listView = (ListView) view.findViewById(R.id.settings);
        createListView();
//        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
//                getContext(),
//                android.R.layout.simple_list_item_1,
//                lista);

        listView.setOnItemClickListener(this);

        //listView.setAdapter( adaptador );

        //TODO

//        mMakePublication.setOnSoftKeyboardListener(new MyLayout.OnSoftKeyboardListener() {
//            @Override
//            public void onShown() {
//                listView.setVisibility(View.GONE);
//                //Log.e("FUU","DEU");
//            }
//
//            @Override
//            public void onHidden() {
//                listView.setVisibility(View.VISIBLE);
//                //Log.e("FUU","DEU, MENTIRA DEU BOM");
//            }
//        });

        //END TODO


//        ImagePipelineConfig config = ImagePipelineConfig
//                .newBuilder(getContext())
//                .setDownsampleEnabled(true)
//                .build();
//        Fresco.initialize(getContext(), config);

        // from the link above

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

}
