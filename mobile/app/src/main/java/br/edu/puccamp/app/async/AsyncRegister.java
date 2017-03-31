package br.edu.puccamp.app.async;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.sql.Date;

import br.edu.puccamp.app.RegisterActivity;
import br.edu.puccamp.app.entity.NivelUsuario;
import br.edu.puccamp.app.entity.SituacaoConta;
import br.edu.puccamp.app.entity.TipoConexao;
import br.edu.puccamp.app.entity.Usuario;


public class AsyncRegister extends AsyncTask<String, String, String> {

    public interface Listener {

        public void onLoaded(String string);
    }

    private String id_tipo_status;
    private String senha;
    private String nome;
    private String email;
    public String status = "nao";
    private Listener mListener;


    public AsyncRegister(Listener mListener) {

        this.mListener = mListener;

    }
    @Override
    protected String doInBackground(String... n) {
        String api_url = "http://192.198.90.26:82/musicsocial/usuario/cadastro";
        nome = n[0];
        email = n[1];
        senha = n[2];
        id_tipo_status = n[3];

        HttpURLConnection urlConnection;
        String requestBody;

        try {
            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept-Encoding", "application/json");

            Usuario usuario = new Usuario();


            usuario.setEmail(email);
            usuario.setSenha("bla");
            usuario.setNome("bla");
            usuario.setApelido("bla");
            usuario.setDataNascimento("2001-01-01");
            usuario.setCidade("campinas");
            usuario.setEstado("estado");
            usuario.setPais("pais");

            Gson gson = new Gson();


            String json = gson.toJson(usuario);

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
                Log.i("teste_api", response);
                JSONObject resp = new JSONObject(response);
                JSONObject object = new JSONObject(resp.getString("object"));
                Log.i("nome", object.getString("nome"));
                Log.i("email", object.getString("email"));
                Log.i("senha", object.getString("senha"));
            }
            return response;


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        status = "sim";
        try {
            JSONObject api_result = new JSONObject(result);
            String message = api_result.getString("message");
            Log.i("teste",message);
           if (message.equalsIgnoreCase("Sucesso!")) {
                String dados = api_result.getString("object");
                JSONObject dados_result = new JSONObject(dados);
                String nome = dados_result.getString("nome");
                String email = dados_result.getString("email");
                String senha = dados_result.getString("senha");

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
