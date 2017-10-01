package br.edu.puccamp.app.async.gosto_musical;

import android.os.AsyncTask;
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

import br.edu.puccamp.app.entity.ResponseGosto;
import br.edu.puccamp.app.gosto_musical.Gosto;
import br.edu.puccamp.app.util.API;


public class AsyncGostoMusical extends AsyncTask<String, String, String> {

    public interface Listener {
        void onLoaded(ArrayList<Gosto> lista);
        void onLoadedError(String s);
    }

    private Listener mListener;

    public AsyncGostoMusical(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(String... n) {

//        String id = n[0];
        HttpURLConnection urlConnection;

        try {

            URL url = new URL(API.URL + API.GOSTOS_MUSICAIS);
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
            Log.e("teste", result);
            ResponseGosto response = publicacoesGson.fromJson(result, ResponseGosto.class);

            ArrayList<Gosto> listaGostos = response.getGostos();

           if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

                if (mListener != null) {
                    mListener.onLoaded(listaGostos);
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
