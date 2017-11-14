package com.mgovea.urmusic.async.localizacao;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mgovea.urmusic.entity.Pais;
import com.mgovea.urmusic.entity.ResponseUsuarios;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.util.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class AsyncPaises extends AsyncTask<Void, String, String> {

    public interface Listener {
        void onLoaded(ArrayList<Pais> paises);
        void onLoadedError(String s);
    }

    private Listener mListener;

    public AsyncPaises(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(Void... n) {

        //String nomeUsuario = n[0];
        HttpURLConnection urlConnection;

        try {

            URL url = new URL(API.API_PAISES);
            urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setDoOutput(true);
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


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        try {

            Log.d("PAISES", result);

            JSONArray api_result = new JSONArray(result);

            ArrayList<Pais> paises = new ArrayList<>();

            for (int i = 0; i < api_result.length(); ++i) {
                JSONObject dados_result = api_result.getJSONObject(i);
                Pais pais = new Pais(dados_result.getString("CodigoArea"),
                        dados_result.getString("Pais"),
                        dados_result.getString("Sigla"));
                paises.add(pais);
            }

                if (mListener != null) {
                    mListener.onLoaded(paises);
                }

        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onLoadedError(e.getMessage());
            }
        }


    }

}
