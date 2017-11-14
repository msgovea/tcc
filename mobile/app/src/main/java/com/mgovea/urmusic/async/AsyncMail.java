package com.mgovea.urmusic.async;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mgovea.urmusic.entity.Email;
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


public class AsyncMail extends AsyncTask<Email, String, String> {

    public interface Listener {
        void onLoaded(String string);
    }

    private Listener mListener;

    public AsyncMail(Listener mListener) {

        this.mListener = mListener;

    }

    @Override
    protected String doInBackground(Email... n) {

        Email mail = n[0];
        HttpURLConnection urlConnection;

        try {
            URL url = new URL(API.URL + API.ENVIAR_EMAIL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept-Encoding", "application/json");

            Gson gson = new Gson();
            String json = gson.toJson(mail);

            OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
            writer.write(json);
            Log.i("EMAIL", json);
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
            Log.d("MSG_RETORNO_MAIL", result);

            Gson retorno = new Gson();
            Response response = retorno.fromJson(result, Response.class);

            if (response.getMessage().equalsIgnoreCase("Sucesso!")) {

                if (mListener != null) {
                    mListener.onLoaded("true");
                }

            } else {
                if (mListener != null) {
                    mListener.onLoaded("Erro ao carregar");
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
