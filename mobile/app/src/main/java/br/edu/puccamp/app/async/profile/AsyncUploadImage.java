package br.edu.puccamp.app.async.profile;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

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

import br.edu.puccamp.app.entity.ImagemUsuario;
import br.edu.puccamp.app.entity.Response;
import br.edu.puccamp.app.entity.ResponseUsuario;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.util.API;


public class AsyncUploadImage extends AsyncTask<ImagemUsuario, String, String> {

    public interface Listener {
        void onLoaded(Object o);
    }

    private Listener mListener;

    public AsyncUploadImage(Listener mListener) {
        this.mListener = mListener;
    }
    @Override
    protected String doInBackground(ImagemUsuario... n) {

        ImagemUsuario imagem = n[0];
        HttpURLConnection urlConnection;

        try {
            URL url = new URL(API.URL + API.UPLOAD_IMAGEM);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept-Encoding", "application/json");

            Gson gson = new Gson();
            String json = gson.toJson(imagem);

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

            Log.e("MATEUSGOVEA", result);

            Object u = response.getObject();

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
