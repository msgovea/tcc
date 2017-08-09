package br.edu.puccamp.app.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.AsyncProfile;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.profile.ProfileEditActivity;
import br.edu.puccamp.app.util.API;
import br.edu.puccamp.app.util.AbstractAsyncActivity;

public class ProfileTabbedActivity extends AbstractAsyncActivity implements AsyncProfile.Listener {

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

    private Button mButtonFollow;

    private Usuario usuario;
    private boolean myProfile;
    private Long idUsuario;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTextUserProfileName = (TextView) findViewById(R.id.user_profile_name);
        mTextUserBio = (TextView) findViewById(R.id.user_profile_short_bio);

        // se obtem idUsuario, bate na API e traz as informações do usuário
        idUsuario = getIntent().getLongExtra("idUsuario", 0);

        mButtonFollow = (Button) findViewById(R.id.btn_follow);

        if (idUsuario != 0) {
            myProfile = false;
            mButtonFollow.setVisibility(View.VISIBLE);
            showLoadingProgressDialog();
            AsyncProfile sinc = new AsyncProfile(this);
            sinc.execute(idUsuario);
        } else {
            myProfile = true;
            SharedPreferences prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
            Gson gson = new Gson();
            usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);
            idUsuario = usuario.getCodigoUsuario().longValue();
            mButtonFollow.setVisibility(View.INVISIBLE);

            populaPerfil(usuario);
        }


    }

    private void populaPerfil(Usuario usuario) {
        getSupportActionBar().setTitle(usuario.getNome());
        mTextUserProfileName.setText(usuario.getNome());
        mTextUserBio.setText(usuario.getCidade() + " - " + usuario.getEstado());
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        if (getIntent().getLongExtra("idUsuario",0) == 0) {
            SharedPreferences prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
            Gson gson = new Gson();
            usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);
            //edit.setText(usuario.getNome());
            getSupportActionBar().setTitle(usuario.getNome());
            mTextUserProfileName.setText(usuario.getNome());
            mTextUserBio.setText(usuario.getCidade() + " - " + usuario.getEstado());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (o.getClass() == Usuario.class) {


            usuario = (Usuario)o;
            populaPerfil(usuario);

        } else {
            builder.setTitle(getString(R.string.error));
            builder.setMessage(getString((o.equals("invalid")) ? R.string.error_edit_profile : R.string.error));
            builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
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
                case 1:
                    return GostoMusicalProfileFragment.newInstance(idUsuario);
                case 2:
                    return null;
                default:
                    return null;
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
