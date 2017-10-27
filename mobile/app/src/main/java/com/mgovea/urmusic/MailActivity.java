package com.mgovea.urmusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mgovea.urmusic.async.AsyncMail;
import com.mgovea.urmusic.entity.Email;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.Preferencias;

public class MailActivity extends AbstractAsyncActivity implements AsyncMail.Listener {

    private EditText etConteudo;
    private EditText etEndEmail;
    private Button btEnviarEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etConteudo = (EditText) findViewById(R.id.et_conteudo);
        etEndEmail = (EditText) findViewById(R.id.et_end_email);
        btEnviarEmail = (Button) findViewById(R.id.bt_enviar_email);

        btEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingProgressDialog();
                enviaEmail();
            }
        });
    }

    private void enviaEmail() {
        String endEmail = etEndEmail.getText().toString();
        String conteudo = etConteudo.getText().toString();

        //TODO REMOVER
        Preferencias pref = new Preferencias(this);
        //TODO FIM

        Email mail = new Email("Nova mensagem de: " + pref.getDadosUsuario().getApelido(), conteudo, endEmail);

        AsyncMail sinc = new AsyncMail(this);
        sinc.execute(mail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }

    //Métodos de retorno asincrono

    @Override
    public void onLoaded(String string) {
        if (string.equalsIgnoreCase("true")) {
            dismissProgressDialog();
            //TODO TEXT
            Toast.makeText(this, "E-mail enviado com sucesso", Toast.LENGTH_LONG).show();
        } else {
            showErrorMessage();
            //TODO MUDAR MENSAGEM DE ERRO PARA ESPECIFICA
        }
    }
}
