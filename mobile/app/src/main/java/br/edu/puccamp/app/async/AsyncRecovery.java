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

import br.edu.puccamp.app.entity.ResponseUsuario;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.util.Strings;


public class AsyncRecovery extends AsyncTask<String, String, String> {

    public interface Listener {
        void onLoaded(String string);
    }

    private Listener mListener;

    public AsyncRecovery(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(String... n) {

        String email = n[0];
        email = Base64.encodeToString(email.getBytes(), Base64.DEFAULT);
        HttpURLConnection urlConnection;

        try {

            URL url = new URL(Strings.URL + Strings.RECOVERY + "/" + email);
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

            Usuario u = response.getObject();

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
