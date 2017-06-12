package br.edu.puccamp.app.principal;

import android.os.Bundle;
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

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.util.AbstractAsyncActivity;
import br.edu.puccamp.app.util.MyLayout;

public class MainActivity extends AbstractAsyncActivity {
    private static final String SELECTED_ITEM = "arg_selected_item";

    private BottomNavigationView mBottomNav;

    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_x);



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
        }
    }

    private void selectFragment(MenuItem item, String info) {
        Fragment frag = null;
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

                //                MyLayout layout = (MyLayout) findViewById(R.id.fragment_make_publication);
                //                layout.setOnSoftKeyboardListener(new MyLayout.OnSoftKeyboardListener() {
                //                    @Override
                //                    public void onShown() {
                //                        Log.e("FUU","DEU");
                //                        findViewById(R.id.navigation).setVisibility(View.GONE);
                //                    }
                //                    @Override
                //                    public void onHidden() {
                //                        Log.e("FUU","DEU naoo");
                //                        findViewById(R.id.navigation).setVisibility(View.VISIBLE);
                //                    }
                //                });

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

    protected void teste(){
        mBottomNav.setVisibility(View.GONE);
    }

    protected void teste2(){
        mBottomNav.setVisibility(View.VISIBLE);
    }
}
