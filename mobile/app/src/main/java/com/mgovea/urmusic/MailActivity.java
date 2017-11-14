package com.mgovea.urmusic;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.mgovea.urmusic.async.AsyncMail;
import com.mgovea.urmusic.entity.Email;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.Preferencias;

import es.dmoral.toasty.Toasty;

public class MailActivity extends AbstractAsyncActivity implements AsyncMail.Listener {

    private EditText etConteudo;
    private Button btEnviarEmail;
    private TextView mNameEmail;
    private Email email;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = new Email();

        try {
            email.setDestinatario(getIntent().getStringExtra(API.USUARIO));
        } catch (Exception e) {
            showErrorMessage();
        }

        mNameEmail = (TextView) findViewById(R.id.name_email);
        try {
            mNameEmail.setText(getIntent().getStringExtra(API.BUSCAR_USUARIO_NOME));
        } catch (Exception e) {
            mNameEmail.setText("Usuário");
        }
        etConteudo = (EditText) findViewById(R.id.et_conteudo);
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
        String conteudo = etConteudo.getText().toString().replace("\n", "<br>");

        //TODO REMOVER
        Preferencias pref = new Preferencias(this);
        //TODO FIM

        email.setTexto(conteudo);
        email.setCodRemetente(pref.getDadosUsuario().getCodigoUsuario());

        AsyncMail sinc = new AsyncMail(this);
        sinc.execute(email);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    //Métodos de retorno asincrono

    @Override
    public void onLoaded(String string) {
        if (string.equalsIgnoreCase("true")) {
            dismissProgressDialog();
            Toasty.success(this, getString(R.string.mail_ok), Toast.LENGTH_LONG, true).show();

            onBackPressed();
        } else {
            showErrorMessage();
        }
    }
}
