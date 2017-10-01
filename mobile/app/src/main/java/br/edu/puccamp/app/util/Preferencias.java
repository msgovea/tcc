package br.edu.puccamp.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashMap;

import br.edu.puccamp.app.entity.Usuario;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Gson gson = new Gson();

    public Preferencias(Context contextoParametro){
        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(API.USUARIO, Context.MODE_PRIVATE );
    }

    public void salvarUsuarioPreferencias(Usuario usuario){
        editor = preferences.edit();
        editor.putString(API.USUARIO, gson.toJson(usuario));
        editor.commit();
    }

    public Usuario getDadosUsuario(){
        return gson.fromJson(preferences.getString(API.USUARIO, null), Usuario.class);
    }

}
