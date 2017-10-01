package com.mgovea.urmusic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.util.API;

public class ProfileActivity extends AppCompatActivity {

    private Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView edit = (TextView) findViewById(R.id.user_profile_name);

        if (getIntent().getLongExtra("idUsuario",0) != 0) {
            //asyntask para obter informações
            edit.setText("bla");

        } else {
            SharedPreferences prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
            Gson gson = new Gson();
            usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);
            edit.setText(usuario.getNome());

        }

        //toolbar.setTitle(usuario.getNome());


    }
}
