package com.mgovea.urmusic.principal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.mgovea.urmusic.async.profile.AsyncUploadImage;
import com.mgovea.urmusic.async.publication.AsyncMakePublication;
import com.mgovea.urmusic.entity.ImagemUsuario;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;

import com.mgovea.urmusic.R;
import com.mgovea.urmusic.util.Preferencias;;import java.io.ByteArrayOutputStream;

import io.fabric.sdk.android.services.common.Crash;

public class MainActivity extends AbstractAsyncActivity {
    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;

    private int mSelectedItem;

    private Fragment frag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_x);

        logUser();

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation_menu);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 2);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(2);
        }
        selectFragment(selectedItem);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(2);
        Log.e("menu", homeItem.toString());
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else {
            super.onBackPressed();
            //finish();
        }
    }

    private void selectFragment(MenuItem item, String info) {

        // init corresponding fragment
        Log.e("RECEBIDO", item.getItemId() + " - " + item.toString());
        switch (item.getItemId()) {
            case R.id.menu_home:
                frag = MenuOthersFragment.newInstance(getString(R.string.account_banned_text),
                        getColorFromRes(R.color.primary_light));
                break;
            case R.id.menu_publication:
                frag = MenuPublicationFragment.newInstance(info,
                        getColorFromRes(R.color.primary_light));
                break;
            case R.id.menu_post:
                frag = MenuMakePublicationFragment.newInstance(null,
                        getColorFromRes(R.color.primary_light));
                break;

            default:
                frag = MenuFragment.newInstance(getString(R.string.account_banned_text),
                        getColorFromRes(R.color.accent));
                break;
        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i< mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        updateToolbarText(item.getTitle());

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.add(R.id.container, frag, frag.getTag());
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }
    }

    private void logUser() {
        Preferencias pref = new Preferencias(this);
        Crashlytics.setUserIdentifier(pref.getDadosUsuario().getCodigoUsuario().toString());
        Crashlytics.setUserEmail(pref.getDadosUsuario().getEmail());
        Crashlytics.setUserName(pref.getDadosUsuario().getNome());
    }


    private void selectFragment(MenuItem item) {
        selectFragment(item, null);
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    private int getColorFromRes(@ColorRes int resId) {
        return ContextCompat.getColor(this, resId);
    }

    protected void openPublication (int id){
        //mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
        MenuItem selectedItem = mBottomNav.getMenu().findItem(id);
        selectFragment(selectedItem, "done");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("img", "Imagem selecionada");
        //Testar processo de retorno dos dados
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            //recuperar local do recurso
            Uri localImagemSelecionada = data.getData();

            //recupera a imagem do local que foi selecionada
            try {
                showLoadingProgressDialog();
                //bottomSheetDialogFragment.dismiss();

                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);

                //comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                //Cria um array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                Log.e("UPLOAD_IMAGEM", byteArray.length + "");



                //TODO
                if (!((MenuMakePublicationFragment)frag).mTextPublication.getText().toString().trim().equals("")) {

                    Preferencias pref = new Preferencias(this);
                    Usuario usuario = pref.getDadosUsuario();
                    Publicacao publicacao = new Publicacao(usuario, ((MenuMakePublicationFragment)frag).mTextPublication.getText().toString(), byteArray);

                    showLoadingProgressDialog();

                    AsyncMakePublication sinc = new AsyncMakePublication(((MenuMakePublicationFragment)frag));
                    sinc.execute(publicacao);
                }


                //TODO EXIBIR BYTE ARRAY BYTE
//                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                imageView.setImageBitmap(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    protected void teste(){
        mBottomNav.setVisibility(View.GONE);
    }

    protected void teste2(){
        mBottomNav.setVisibility(View.VISIBLE);
    }
}
