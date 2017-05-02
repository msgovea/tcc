package br.edu.puccamp.app.async;

import android.os.AsyncTask;
import android.util.Base64;

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
import br.edu.puccamp.app.entity.Response;
import br.edu.puccamp.app.entity.ResponsePublicacoes;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.util.Strings;


public class AsyncPublication extends AsyncTask<String, String, String> {

    public interface Listener {
        void onLoaded(ArrayList<Publicacao> lista);
    }

    private Listener mListener;

    public AsyncPublication(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(String... n) {

        String email = n[0];
        email = Base64.encodeToString(email.getBytes(), Base64.DEFAULT);
        HttpURLConnection urlConnection;

        try {

            URL url = new URL(Strings.URL + Strings.PUBLICATION + "/" + "MQ==");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");

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
            ResponsePublicacoes response = publicacoesGson.fromJson(result, ResponsePublicacoes.class);

            ArrayList<Publicacao> listaPublicacoes = response.getPublicacoes();

           if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

                if (mListener != null) {
                    mListener.onLoaded(listaPublicacoes);
                }

            } else {
                if (mListener != null) {
                    mListener.onLoaded(null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onLoaded(null);
            }
        }


    }

}
