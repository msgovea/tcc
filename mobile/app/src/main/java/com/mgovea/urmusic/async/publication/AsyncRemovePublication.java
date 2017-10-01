package com.mgovea.urmusic.async.publication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mgovea.urmusic.entity.Response;
import com.mgovea.urmusic.util.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AsyncRemovePublication extends AsyncTask<Long, String, String> {


    public interface Listener {
        void onLoaded(Boolean bool);
        void onLoadedError(String s);
    }

    private Listener mListener;

    public AsyncRemovePublication(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(Long... n) {

        HttpURLConnection urlConnection;

        try {

            URL url = new URL(API.URL + API.REMOVE_PUBLICATION + n[0]);
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
            Gson publicacaoGson = new Gson();
            Response<Object> response = publicacaoGson.fromJson(result, Response.class);

            if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

                if (mListener != null) {
                    mListener.onLoaded(true);
                }

            } else {
                if (mListener != null) {
                    mListener.onLoadedError("Erro ao carregar");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) {
                mListener.onLoadedError(e.toString());
            }
        }

    }

}
