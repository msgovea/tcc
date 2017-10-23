package com.mgovea.urmusic.async;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mgovea.urmusic.entity.Denuncia;
import com.mgovea.urmusic.entity.Response;
import com.mgovea.urmusic.entity.ResponseUsuario;
import com.mgovea.urmusic.entity.Usuario;
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


public class AsyncDenunciar extends AsyncTask<Denuncia, String, String> {

    public interface Listener {
        void onLoaded(Object o);
        void onLoadedError(String o);
    }

    private Listener mListener;

    public AsyncDenunciar(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(Denuncia... n) {

        Denuncia denuncia = n[0];
        HttpURLConnection urlConnection;

        try {
            URL url = new URL(API.URL + API.DENUNCIAR);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept-Encoding", "application/json");

            Gson gson = new Gson();
            String json = gson.toJson(denuncia);

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(json);
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
            Gson usuarioGson = new Gson();
            Response<Object> response = usuarioGson.fromJson(result, Response.class);

            Log.e("DENUNCIA", result);
           if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

                if (mListener != null) {
                    mListener.onLoaded(response.getObject());
                }

            } else {
                if (mListener != null) {
                    mListener.onLoadedError("invalid");
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
