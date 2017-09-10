package br.edu.puccamp.app.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.follow.AsyncFollowUser;
import br.edu.puccamp.app.async.profile.AsyncProfile;
import br.edu.puccamp.app.entity.Amigo;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.util.API;
import br.edu.puccamp.app.util.AbstractAsyncActivity;

public class ProfileTabbedActivity extends AbstractAsyncActivity implements AsyncProfile.Listener, AsyncFollowUser.Listener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TextView mTextUserProfileName;
    private TextView mTextUserBio;
    private ImageView mImageView;

    private Button mButtonFollow;

    private Usuario usuario;
    private boolean myProfile;
    private Long idUsuario;
    private Usuario usuarioLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tabbed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mImageView = (ImageView) findViewById(R.id.user_profile_photo);

        mTextUserProfileName = (TextView) findViewById(R.id.user_profile_name);
        mTextUserBio = (TextView) findViewById(R.id.user_profile_short_bio);

        // se obtem idUsuario, bate na API e traz as informações do usuário
        idUsuario = getIntent().getLongExtra("idUsuario", 0);

        mButtonFollow = (Button) findViewById(R.id.btn_follow);

        SharedPreferences prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
        Gson gson = new Gson();
        usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);

        if (idUsuario != usuario.getCodigoUsuario().longValue() && idUsuario != 0) {
            myProfile = false;
            mButtonFollow.setVisibility(View.VISIBLE);
            showLoadingProgressDialog();
            AsyncProfile sinc = new AsyncProfile(this);
            sinc.execute(idUsuario);
        } else {
            myProfile = true;
            idUsuario = usuario.getCodigoUsuario().longValue();
            mButtonFollow.setVisibility(View.INVISIBLE);

            populaPerfil(usuario);
        }

        mButtonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amigo amigo = new Amigo();
                amigo.setSegue(usuario);
                amigo.setSeguido(usuarioLoad);
                AsyncFollowUser sinc = new AsyncFollowUser(ProfileTabbedActivity.this);
                sinc.execute(amigo);
            }
        });

    }

    private void populaPerfil(Usuario usuario) {
        getSupportActionBar().setTitle(usuario.getApelido());
        mTextUserProfileName.setText(usuario.getNome());

        byte[] byteArray ;

        Bitmap bitmap = null;

        try {
            byteArray = Base64.decode(usuario.getImagemPerfil().getBytes(), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            mImageView.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            mImageView.setImageDrawable(getDrawable(R.drawable.ic_account_box_black_24dp));
        }
        mTextUserBio.setText(usuario.getCidade() + " - " + usuario.getEstado());

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (idUsuario == usuario.getCodigoUsuario().longValue() || idUsuario == 0) {
            populaPerfil(usuario);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_tabbed, menu);
        MenuItem edit = menu.findItem(R.id.action_settings);
        MenuItem pass = menu.findItem(R.id.action_edit_password);

        edit.setVisible(myProfile);
        pass.setVisible(myProfile);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_edit_password:
                startActivity(new Intent(this, EditPasswordActivity.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(this, ProfileEditActivity.class));
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoaded(Object o) {
        dismissProgressDialog();

        if (o.getClass() == Usuario.class) {
            usuarioLoad = (Usuario)o;
            populaPerfil(usuarioLoad);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoadedError(String s) {
        showErrorMessage(s);
    }

    @Override
    public void onLoaded(String s) {
        mButtonFollow.setText(s.equals("INSERIDO") ? "UNFOLLOW" : "FOLLOW");
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return PublicationProfileFragment.newInstance(idUsuario);
                case 2:
                    return GostoMusicalProfileFragment.newInstance(idUsuario);
                default:
                    return PublicationProfileFragment.newInstance(idUsuario);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PUBLICAÇÕES";
                case 1:
                    return "MUSICAS";
                case 2:
                    return "GOSTOS";
            }
            return null;
        }
    }
}
