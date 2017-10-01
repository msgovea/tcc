package br.edu.puccamp.app.async.search;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.entity.ResponsePublicacoes;
import br.edu.puccamp.app.entity.ResponseUsuario;
import br.edu.puccamp.app.entity.ResponseUsuarios;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.util.API;


public class AsyncSearch extends AsyncTask<String, String, String> {

    public interface Listener {
        void onLoaded(ArrayList<Usuario> listaUsuarios);
        void onLoadedError(String s);
    }

    private Listener mListener;

    public AsyncSearch(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(String... n) {

        String nomeUsuario = n[0];
        nomeUsuario = Base64.encodeToString(nomeUsuario.getBytes(), Base64.DEFAULT);
        HttpURLConnection urlConnection;

        try {

            URL url = new URL(API.URL + API.BUSCAR_USUARIO_NOME + nomeUsuario);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");

            Log.e("mgovealindo", url.toString());
            urlConnection.connect();

            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }
            return response;


        } catch (IOException | JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            Gson publicacoesGson = new Gson();
            Log.e("busca", result);
            ResponseUsuarios response = publicacoesGson.fromJson(result, ResponseUsuarios.class);

            ArrayList<Usuario> listaUsuario = response.getObject();

           if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

                if (mListener != null) {
                    mListener.onLoaded(listaUsuario);
                }

            } else {
                if (mListener != null) {
                    mListener.onLoadedError("erro");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onLoadedError(e.getMessage());
            }
        }


    }

}
