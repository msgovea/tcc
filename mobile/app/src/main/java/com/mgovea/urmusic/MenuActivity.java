package com.mgovea.urmusic;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.mgovea.urmusic.async.publication.AsyncMakePublication;
import com.mgovea.urmusic.async.search.AsyncSearch;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.principal.MenuFragment;
import com.mgovea.urmusic.principal.MenuMakePublicationFragment;
import com.mgovea.urmusic.principal.MenuOthersFragment;
import com.mgovea.urmusic.principal.MenuPublicationFragment;
import com.mgovea.urmusic.search.SearchActivity;
import com.mgovea.urmusic.search.SearchActivityFragment;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.Preferencias;

import java.io.ByteArrayOutputStream;

public class MenuActivity extends AbstractAsyncActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;

    private int mSelectedItem;

    private Fragment frag = null;


    private AppCompatImageView mIcon;
    private AppCompatImageView mIconSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menu();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //MAINACTIVITY
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_x);

        //logUser();

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

    public void menu() {
//        mIcon = (AppCompatImageView) findViewById(R.id.iconAlarm);
//        mIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NotificationCompat.Builder mBuilder =
//                        new NotificationCompat.Builder(getApplicationContext())
//                                .setSmallIcon(R.drawable.logo)
//                                .setContentTitle("My notification")
//                                .setContentText("Hello World!");
//                // Creates an explicit intent for an Activity in your app
//                Intent resultIntent = new Intent(getApplicationContext(), SearchActivity.class);
//
//                // The stack builder object will contain an artificial back stack for the
//                // started Activity.
//                // This ensures that navigating backward from the Activity leads out of
//                // your application to the Home screen.
//                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
//                // Adds the back stack for the Intent (but not the Intent itself)
//                stackBuilder.addParentStack(SearchActivity.class);
//                // Adds the Intent that starts the Activity to the top of the stack
//                stackBuilder.addNextIntent(resultIntent);
//                PendingIntent resultPendingIntent =
//                        stackBuilder.getPendingIntent(
//                                0,
//                                PendingIntent.FLAG_UPDATE_CURRENT
//                        );
//                mBuilder.setContentIntent(resultPendingIntent);
//                NotificationManager mNotificationManager =
//                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                // mId allows you to update the notification later on.
//                mNotificationManager.notify(123, mBuilder.build());
//            }
//        });

        mIconSearch = (AppCompatImageView) findViewById(R.id.iconSearch);
        mIconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_search, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//            queryTextListener = new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    Log.i("onQueryTextChange", newText);
//
//                    return true;
//                }
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    Log.i("onQueryTextSubmit", query);
//                    AsyncSearch sinc = new AsyncSearch(SearchActivityFragment.this);
//                    sinc.execute(query);
//                    showProgress(true);
//                    return true;
//                }
//            };
//            searchView.setOnQueryTextListener(queryTextListener);
//        }
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //ACTIVITY

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
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
        for (int i = 0; i < mBottomNav.getMenu().size(); i++) {
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

    public void openPublication(int id) {
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

                imagem = Bitmap.createScaledBitmap(imagem, 300, 300, false);

                //comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                //Cria um array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                Log.e("UPLOAD_IMAGEM", byteArray.length + "");

                Preferencias pref = new Preferencias(this);
                Usuario usuario = pref.getDadosUsuario();
                Publicacao publicacao = new Publicacao(usuario, ((MenuMakePublicationFragment) frag).mTextPublication.getText().toString(), byteArray);

                showLoadingProgressDialog();

                AsyncMakePublication sinc = new AsyncMakePublication(((MenuMakePublicationFragment) frag));
                sinc.execute(publicacao);


                //TODO EXIBIR BYTE ARRAY BYTE
//                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                imageView.setImageBitmap(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void teste() {
        mBottomNav.setVisibility(View.GONE);
    }

    public void teste2() {
        mBottomNav.setVisibility(View.VISIBLE);
    }
}
