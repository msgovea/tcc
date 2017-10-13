package com.mgovea.urmusic.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mgovea.urmusic.async.profile.AsyncProfile;
import com.mgovea.urmusic.entity.Usuario;

public class Preferencias implements AsyncProfile.Listener {

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

    public void removerUsuario(){
        editor = preferences.edit();
        editor.clear().apply();
    }

    public void atualizaUsuario(){
        AsyncProfile sinc = new AsyncProfile(this);
        sinc.execute(getDadosUsuario().getCodigoUsuario());
    }


    public Usuario getDadosUsuario(){
        return gson.fromJson(preferences.getString(API.USUARIO, null), Usuario.class);
    }

    @Override
    public void onLoaded(Object o) {
        if (o.getClass() == Usuario.class) {
            salvarUsuarioPreferencias((Usuario)o);
        }
    }
}
