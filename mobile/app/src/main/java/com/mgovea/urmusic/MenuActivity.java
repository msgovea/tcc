package com.mgovea.urmusic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mgovea.urmusic.async.gosto_musical.AsyncMakeGostoMusical;
import com.mgovea.urmusic.async.publication.AsyncMakePublication;
import com.mgovea.urmusic.async.search.AsyncSearch;
import com.mgovea.urmusic.entity.GostoUsuario;
import com.mgovea.urmusic.entity.Publicacao;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.principal.MenuFragment;
import com.mgovea.urmusic.principal.MenuMakePublicationFragment;
import com.mgovea.urmusic.principal.MenuOthersFragment;
import com.mgovea.urmusic.principal.MenuPublicationFragment;
import com.mgovea.urmusic.principal.MenuPublicationHighFragment;
import com.mgovea.urmusic.principal.PostActivity;
import com.mgovea.urmusic.profile.ProfileTabbedActivity;
import com.mgovea.urmusic.search.SearchActivity;
import com.mgovea.urmusic.search.SearchActivityFragment;
import com.mgovea.urmusic.search.SearchAdapter;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.Preferencias;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MenuActivity extends AbstractAsyncActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncSearch.Listener {

    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;

    private int mSelectedItem;

    private Fragment frag = null;

    /**/
    private LinearLayout searchScreen;
    private FrameLayout defaultScreen;
    /**/
    private RecyclerView mRecyclerView;
    public View mProgressView;
    private SearchAdapter mAdapter;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        menu();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);


        /* OBTEM VERSÃO DA APLICAÇÃO */
        try {
            TextView versionTV = (TextView) findViewById(R.id.version);
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;

            navigationView.getMenu().findItem(R.id.text_menu_outros).setTitle("urMusic v" + version + " - Release: " + verCode);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO ERROR
        }

        //CARREGA DRAWER
        drawer();
        //MAINACTIVITY

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
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 1);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
            selectFragment(selectedItem);
        } else {
            //VERIFICA ARGS
            //SE A TELA FOI ABERTA PELO ATO DE IMPULSIONAMENTO, ABRE A TELA DE PUBLICAÇÕES EM ALTA
            if (getIntent().getBooleanExtra(API.IMPULSIONAMENTO, false)) {
                openAlta();
            } else {
                selectedItem = mBottomNav.getMenu().getItem(1);
                selectFragment(selectedItem);
            }
        }
        //selectFragment(selectedItem);

        //  SEARCH

        mRecyclerView = (RecyclerView) findViewById(R.id.list_users);
        mProgressView = findViewById(R.id.user_progress);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (getIntent().getBooleanExtra(API.PUBLICADO, false)) {
            openPublication(R.id.menu_publication);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, PostActivity.class));
            }
        });
    }

    public void menu() {

        searchScreen = (LinearLayout) findViewById(R.id.search_screen);
        defaultScreen = (FrameLayout) findViewById(R.id.container);

//        mIconSearch = (AppCompatImageView) findViewById(R.id.iconSearch);
//        mIconSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (searchScreen.getVisibility() == View.VISIBLE) {
                searchScreen.setVisibility(View.GONE);
                defaultScreen.setVisibility(View.VISIBLE);

                searchView.onActionViewCollapsed();

            } else {
                //todo search
                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    searchScreen.setVisibility(View.VISIBLE);
                    defaultScreen.setVisibility(View.GONE);

                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                    AsyncSearch sinc = new AsyncSearch(MenuActivity.this);
                    sinc.execute(query);
                    showProgress(true);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    searchScreen.setVisibility(View.GONE);
                    defaultScreen.setVisibility(View.VISIBLE);

                    searchView.onActionViewCollapsed();
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu);
        return true;
    }

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

    private void drawer() {
        SimpleDraweeView imagem = (SimpleDraweeView) navigationView.getHeaderView(0).findViewById(R.id.profile_photo_user);
        TextView tvUser = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_name_user);
        TextView tvMail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_mail_user);

        Preferencias pref = new Preferencias(this);

        imagem.setImageURI(API.URL_IMGS + API.IMG_PERFIL + pref.getDadosUsuario().getCodigoUsuario() + ".jpg");
        tvUser.setText(pref.getDadosUsuario().getNome());
        tvMail.setText(pref.getDadosUsuario().getEmail());

        LinearLayout linearLayout = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.teste);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ProfileTabbedActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Log.e("LOG", item.toString());
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            selectFragment(item);
        } else if (id == R.id.nav_gallery) {
            selectFragment(item);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Preferencias pref = new Preferencias(this);
            pref.removerUsuario();

            startActivity(new Intent(this, TesteLogin.class));
            finish();
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
//            case R.id.menu_home:
//                frag = MenuOthersFragment.newInstance(getString(R.string.account_banned_text),
//                        getColorFromRes(R.color.primary_light));
//                break;
            case R.id.menu_publication:
                frag = MenuPublicationFragment.newInstance(info,
                        getColorFromRes(R.color.primary_light));
                break;

            case R.id.nav_camera:
                frag = MenuPublicationFragment.newInstance(info,
                        getColorFromRes(R.color.primary_light));
                break;
            case R.id.nav_gallery:
                frag = MenuPublicationHighFragment.newInstance(info,
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

    public void openAlta() {
        frag = MenuPublicationHighFragment.newInstance(null,
                getColorFromRes(R.color.primary_light));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.add(R.id.container, frag, frag.getTag());
        ft.replace(R.id.container, frag, frag.getTag());
        ft.commit();

        updateToolbarText(getString(R.string.feed_high));
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


    public void teste() {
        mBottomNav.setVisibility(View.GONE);
    }

    public void teste2() {
        mBottomNav.setVisibility(View.VISIBLE);
    }

    ///SEARCH

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

                mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                mRecyclerView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mProgressView.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply show
                // and hide the relevant UI components.
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    // ***************************************
    // Metodos de retorno Async
    // ***************************************

    @Override
    public void onLoaded(ArrayList<Usuario> listaUsuarios) {
        mAdapter = new SearchAdapter(this, listaUsuarios);
        mRecyclerView.setAdapter(mAdapter);

        showProgress(false);
    }

    @Override
    public void onLoadedError(String s) {
        searchScreen.setVisibility(View.GONE);
        defaultScreen.setVisibility(View.VISIBLE);

        Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        //TODO MSG PALOMA
        Log.e("ERRO GERAL", s);
    }
}
