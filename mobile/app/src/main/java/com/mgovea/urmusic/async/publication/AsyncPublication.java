package com.mgovea.urmusic.async.publication;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.ResponsePublicacoes;
import com.mgovea.urmusic.util.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class AsyncPublication extends AsyncTask<String, String, String> {

    public interface Listener {
        void onLoaded(ArrayList<Publicacao> lista);
        void onLoadedError(String s);
    }

    private Listener mListener;

    public AsyncPublication(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(String... n) {

        String id = n[0];
        id = Base64.encodeToString(id.getBytes(), Base64.DEFAULT);
        HttpURLConnection urlConnection;

        try {

            URL url = new URL(API.URL + API.PUBLICATION + "/" + id);
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
            Log.e("teste", result);
            ResponsePublicacoes response = publicacoesGson.fromJson(result, ResponsePublicacoes.class);

            ArrayList<Publicacao> listaPublicacoes = response.getPublicacoes();

           if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

                if (mListener != null) {
                    mListener.onLoaded(listaPublicacoes);
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
