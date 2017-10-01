package com.mgovea.urmusic.async.comments;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mgovea.urmusic.entity.Comentario;
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


public class AsyncMakeComment extends AsyncTask<Comentario, String, String> {

    public interface Listener {
        void onLoadedError(String s);
        void onLoadedComment(Long idComentario);

    }

    private Listener mListener;

    public AsyncMakeComment(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(Comentario... n) {

        Comentario comentario = n[0];
        HttpURLConnection urlConnection;

        try {
            URL url = new URL(API.URL + API.COMMENTS_REGISTER);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept-Encoding", "application/json");

            Gson gson = new Gson();
            String json = gson.toJson(comentario);

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(json);
            Log.e("teste", json);
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
            Gson publicacaoGson = new Gson();
            Response response = publicacaoGson.fromJson(result, Response.class);

           if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

               if (mListener != null) {
                   //por algum motivo divino o json vem como double, por isso a conversao para long
                   mListener.onLoadedComment(((Double)response.getObject()).longValue());
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
