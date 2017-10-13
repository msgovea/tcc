package com.mgovea.urmusic.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.mgovea.urmusic.async.follow.AsyncFollowUser;
import com.mgovea.urmusic.async.profile.AsyncProfile;
import com.mgovea.urmusic.async.profile.AsyncUploadImage;
import com.mgovea.urmusic.entity.Amigo;
import com.mgovea.urmusic.entity.ImagemUsuario;
import com.mgovea.urmusic.entity.Usuario;
import com.mgovea.urmusic.profile.options.PictureBottomSheetDialogFragment;
import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.AbstractAsyncActivity;
import com.mgovea.urmusic.util.Preferencias;

import java.io.ByteArrayOutputStream;

import com.mgovea.urmusic.R;

public class ProfileTabbedActivity extends AbstractAsyncActivity implements AsyncProfile.Listener, AsyncFollowUser.Listener, AsyncUploadImage.Listener {

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

    private CoordinatorLayout mainContent;

    private ViewPager mViewPager;
    private TextView mTextUserProfileName;
    private TextView mTextUserBio;
    private SimpleDraweeView mImageView;
    private TextView mQtdSeguidores;
    private TextView mQtdSeguidos;

    private Button mButtonFollow;

    private BottomSheetDialogFragment bottomSheetDialogFragment;

    private Usuario usuario;
    private boolean myProfile;
    private Long idUsuario;
    private Usuario usuarioLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tabbed);

        mainContent = (CoordinatorLayout) findViewById(R.id.main_content);
        mainContent.setVisibility(View.INVISIBLE);

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

        mImageView = (SimpleDraweeView) findViewById(R.id.user_profile_photo);
        mQtdSeguidores = (TextView) findViewById(R.id.qtd_followers);
        mQtdSeguidos = (TextView) findViewById(R.id.qtd_follows);

        mTextUserProfileName = (TextView) findViewById(R.id.user_profile_name);
        mTextUserBio = (TextView) findViewById(R.id.user_profile_short_bio);

        // se obtem idUsuario, bate na API e traz as informações do usuário
        idUsuario = getIntent().getLongExtra("idUsuario", 0);

        mButtonFollow = (Button) findViewById(R.id.btn_follow);

        Preferencias pref = new Preferencias(this);
        usuario = pref.getDadosUsuario();

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

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myProfile) {
                    Bundle args = new Bundle();
                    args.putLong(API.USUARIO, idUsuario);
                    bottomSheetDialogFragment = new PictureBottomSheetDialogFragment();
                    bottomSheetDialogFragment.setArguments(args);
                    bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                } else {
                    //TODO VIEW IMAGE
                }
            }
        });

    }

    private void populaPerfil(Usuario usuarioPopulaPerfil) {
        getSupportActionBar().setTitle(usuarioPopulaPerfil.getApelido());
        mTextUserProfileName.setText(usuarioPopulaPerfil.getNome());

        mQtdSeguidores.setText(usuarioPopulaPerfil.getSeguidores().size() + "");
        mQtdSeguidos.setText(usuarioPopulaPerfil.getQtdSeguidos().toString());

        if (!myProfile) {
            for (Long u : usuarioPopulaPerfil.getSeguidores()) {
                if (u.equals(usuario.getCodigoUsuario())) {
                    mButtonFollow.setText(getString(R.string.unfollow));
                }
            }
        }
        try {
            mImageView.setImageURI(API.URL_IMGS + API.IMG_PERFIL + usuarioPopulaPerfil.getCodigoUsuario() + ".jpg");
        } catch (Exception e) {
            try {
                mImageView.setImageURI("https://scontent.fcpq3-1.fna.fbcdn.net/v/t1.0-9/11918928_1012801065406820_5528279907234667073_n.jpg?oh=d3b42bf86a3fc19181b84efd9a7a2110&oe=5A293884");
            } catch (Exception e2) {
                e2.printStackTrace();
                //TODO ERRO CRASH APP
            }
        }

        mTextUserBio.setText(usuarioPopulaPerfil.getCidade() + " - " + usuarioPopulaPerfil.getEstado());

        mainContent.setVisibility(View.VISIBLE);
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
                bottomSheetDialogFragment.dismiss();

                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);

                //resize
                imagem = Bitmap.createScaledBitmap(imagem, 300, 300, false);

                //comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                //Cria um array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                Log.e("UPLOAD_IMAGEM", byteArray.length + "");

                ImagemUsuario imagemUsuario = new ImagemUsuario(idUsuario, byteArray);
                AsyncUploadImage sinc = new AsyncUploadImage(this);
                sinc.execute(imagemUsuario);


                //TODO EXIBIR BYTE ARRAY BYTE
//                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                imageView.setImageBitmap(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaded(Object o) {
        dismissProgressDialog();

        if (o.getClass() == Usuario.class) {
            usuarioLoad = (Usuario) o;
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
    public void onLoadedImage(boolean bool) {

        try {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.clearCaches();

            mImageView.setImageURI(API.URL_IMGS + API.IMG_PERFIL + usuario.getCodigoUsuario() + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
            //TODO ERRO CRASH APP
        }

        dismissProgressDialog();

        Toast.makeText(this, getString(R.string.success_update_photo), Toast.LENGTH_LONG).show();
        Log.i(this.toString(), "SUCESSO IMAGEM PUBLICADA");
    }

    @Override
    public void onLoaded(String s) {
        boolean seguiu = s.equals("INSERIDO");
        mButtonFollow.setText(getString(seguiu ? R.string.unfollow : R.string.follow));

        //TODO REMOVER TEXTO FIXO
        try {
            if (seguiu)
                mQtdSeguidores.setText((Long.valueOf(mQtdSeguidores.getText().toString()) + 1) + "");
            else
                mQtdSeguidores.setText((Long.valueOf(mQtdSeguidores.getText().toString()) - 1) + "");
        } catch (Exception e) {
            e.printStackTrace();
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
            Log.e("position", position + " ");
            switch (position) {
                case 0:
                    return PublicationProfileFragment.newInstance(idUsuario);
//                case 1:
//                    return GostoMusicalProfileFragment.newInstance(idUsuario);
                case 2:
                    return GostoMusicalProfileFragment.newInstance(idUsuario);
            }
            return new Fragment();
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
