package com.mgovea.urmusic;

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

import com.mgovea.urmusic.principal.MenuFragment;
import com.mgovea.urmusic.principal.MenuPublicationFragment;
import com.mgovea.urmusic.principal.MenuPublicationHighFragment;

public class RegisterActivityNew extends AppCompatActivity {

    private int mSelectedItem;
    private Fragment frag = null;
    int cont = 0;

    private FrameLayout mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mViewPager = (FrameLayout) findViewById(R.id.container);

        final ProgressBar p = (ProgressBar) findViewById(R.id.progressBar2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                cont++;
                selectFragment(cont);
                p.setProgress(p.getProgress()+10);

            }
        });
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_register_activity_new, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }



    private void selectFragment(int item) {

        // init corresponding fragment
        Log.e("RECEBIDO_FRAGMENT", item + "");
        switch (item) {
            case 1:
                frag = PlaceholderFragment.newInstance(0);
                break;

            case 2:
                frag = PlaceholderFragment.newInstance(1);
                break;

            default:
                frag = PlaceholderFragment.newInstance(2);
                break;
        }

        //updateToolbarText(item.getTitle());

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, frag, frag.getTag());
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }
    }
}
