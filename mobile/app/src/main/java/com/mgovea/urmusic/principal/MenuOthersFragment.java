package com.mgovea.urmusic.principal;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mgovea.urmusic.R;
import com.mgovea.urmusic.TesteLogin;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.listview.AdapterListView;
import com.mgovea.urmusic.listview.ItemListView;
import com.mgovea.urmusic.profile.ProfileTabbedActivity;
import com.mgovea.urmusic.util.API;

import java.util.ArrayList;

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
    private Usuario usuario;


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

    private void createListView() {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemListView>();

        Gson gson = new Gson();
        prefs = getContext().getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);

        ItemListView item1 = new ItemListView(usuario.getNome(), R.drawable.ic_person_black_24dp, 1);
        ItemListView item2 = new ItemListView(getString(R.string.language), R.drawable.ic_language_black_24dp, 2);
        ItemListView item3 = new ItemListView(getString(R.string.politics), R.drawable.ic_verified_user_black_24dp, 3);
        ItemListView item4 = new ItemListView(getString(R.string.logoff), R.drawable.ic_exit_to_app_black_24dp, 4);

        itens.add(item1);
        itens.add(item2);
        itens.add(item3);
        itens.add(item4);

        //Cria o adapter
        adapterListView = new AdapterListView(getContext(), itens);

        //Define o Adapter
        listView.setAdapter(adapterListView);

        //Cor quando a lista é selecionada para rolagem.
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        //Pega o item que foi selecionado.
        ItemListView item = adapterListView.getItem(arg2);
        //Demostração
        Log.e("TODO", (String.valueOf(item.getIconeRid())));
        if (item.getTexto().equals(getString(R.string.logoff))) {
            SharedPreferences prefs = getContext().getSharedPreferences(API.USUARIO, MODE_PRIVATE);
            prefs.edit().clear().apply();
            startActivity(new Intent(getActivity(), TesteLogin.class));
            getActivity().finish();
        } else if (item.getTexto().equals(getString(R.string.language))) {

        } else if (item.getTexto().equals(usuario.getNome())) {
            Intent intent = new Intent(getActivity(), ProfileTabbedActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Você Clicou em: " + item.getTexto(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resultado : grantResults) {
            if (resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            //TODO TEXTO
            builder.setTitle("Permissões negadas");
            builder.setMessage("Para usar o app, aceite as permissões");
            builder.setPositiveButton(getString(R.string.close), null);
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO MSG ERRO
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

        listView = (ListView) view.findViewById(R.id.settings);
        createListView();

        listView.setOnItemClickListener(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

}
