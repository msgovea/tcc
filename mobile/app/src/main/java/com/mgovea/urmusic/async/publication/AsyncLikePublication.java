package com.mgovea.urmusic.async.publication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mgovea.urmusic.entity.Curtida;
import com.mgovea.urmusic.entity.Response;
import com.mgovea.urmusic.util.API;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class AsyncLikePublication extends AsyncTask<Curtida, String, String> {

    public interface Listener {
        void onLoadedError(String s);
        void onLoaded(Double l);

    }

    private Listener mListener;

    public AsyncLikePublication(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(Curtida... n) {

        Curtida curtida = n[0];
        HttpURLConnection urlConnection;

        try {
            URL url = new URL(API.URL + API.LIKE_PUBLICATION);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept-Encoding", "application/json");

            Gson gson = new Gson();
            String json = gson.toJson(curtida);

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(json);
            Log.e("LIKE", json);
            writer.flush();
            writer.close();
            outputStream.close();

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
            Gson followGson = new Gson();
            Response<Double> response = followGson.fromJson(result, Response.class);

           if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

               if (mListener != null) {
                   mListener.onLoaded(response.getObject());
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
