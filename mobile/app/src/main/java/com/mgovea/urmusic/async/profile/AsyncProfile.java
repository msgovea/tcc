package com.mgovea.urmusic.async.profile;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mgovea.urmusic.entity.ResponseUsuario;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.util.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AsyncProfile extends AsyncTask<Long, String, String> {

    public interface Listener {
        void onLoaded(Object o);
    }

    private Listener mListener;

    public AsyncProfile(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(Long... n) {

        Long id = n[0];
        HttpURLConnection urlConnection;

        try {

            URL url = new URL(API.URL + API.BUSCAR_USUARIO + "/" + id);
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
            Gson usuarioGson = new Gson();
            ResponseUsuario response = usuarioGson.fromJson(result, ResponseUsuario.class);

            Log.e("MATEUSGOVEA", result);

            Usuario u = response.getObject();

           if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

                if (mListener != null) {
                    mListener.onLoaded(u);
                }

            } else {
                if (mListener != null) {
                    mListener.onLoaded("invalid");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onLoaded(e.toString());
            }
        }


    }

}
