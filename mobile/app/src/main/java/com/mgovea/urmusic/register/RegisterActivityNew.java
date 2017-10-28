package com.mgovea.urmusic.register;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mgovea.urmusic.R;
import com.mgovea.urmusic.TesteLogin;
import com.mgovea.urmusic.principal.MenuFragment;
import com.mgovea.urmusic.principal.MenuPublicationFragment;
import com.mgovea.urmusic.principal.MenuPublicationHighFragment;

public class RegisterActivityNew extends AppCompatActivity {

    private Fragment frag = null;

    private Fragment frag1 = null;
    private Fragment frag2 = null;
    private Fragment frag3 = null;

    int cont = 1;

    private FrameLayout mViewPager;
    private TextView stepRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mViewPager = (FrameLayout) findViewById(R.id.container);

        final ProgressBar p = (ProgressBar) findViewById(R.id.progressBar2);
        p.setProgress(p.getProgress()+(110/3));

        stepRegister = (TextView) findViewById(R.id.step_register);
        stepRegister.setText(cont + "");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p.getProgress() >= 100) {
                    startActivity(new Intent(getBaseContext(), TesteLogin.class));
                } else {
                    cont++;
                    stepRegister.setText(cont + "");
                    selectFragment(cont);
                    p.setProgress(p.getProgress() + (100 / 3));
                }
            }
        });

        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p.getProgress() >= 40) {
                    cont--;
                    stepRegister.setText(cont + "");
                    selectFragment(cont);
                    p.setProgress(p.getProgress() - (100 / 3));
                }
            }
        });

        getSupportActionBar().setTitle(getString(R.string.title_activity_register));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectFragment(cont);
    }

//    private void selectFragment2(int item) {
//
//        Log.e("RECEBIDO_FRAGMENT", item + "");
//        switch (item) {
//            case 1:
//                frag = frag1;
//                break;
//
//            case 2:
//                frag = frag2;
//                break;
//
//            case 3:
//                frag = frag3;
//                break;
//            default:
//                frag = new Fragment(); //InfosRegisterFragment1.newInstance(3);
//                break;
//        }
//
//        //updateToolbarText(item.getTitle());
//
//        if (frag != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            //ft.add(R.id.container, frag, frag.getTag());
//            ft.replace(R.id.container, frag, frag.getTag());
//            ft.commit();
//        }
//    }

    private void selectFragment(int item) {

        Log.e("RECEBIDO_FRAGMENT", item + "");
        switch (item) {
            case 1:
                if (frag1 == null) frag1 = InfosRegisterFragment1.newInstance();
                frag = frag1;
                break;

            case 2:
                if (frag2 == null) frag2 = InfosRegisterFragment2.newInstance();
                frag = frag2;
                break;

            case 3:
                if (frag3 == null) frag3 = InfosRegisterFragment3.newInstance();
                frag = frag3;
                break;
            default:
                frag = new Fragment(); //InfosRegisterFragment1.newInstance(3);
                break;
        }

        //updateToolbarText(item.getTitle());

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.add(R.id.container, frag, frag.getTag());
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }
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
}
