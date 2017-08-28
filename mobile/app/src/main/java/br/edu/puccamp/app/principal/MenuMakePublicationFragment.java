package br.edu.puccamp.app.principal;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.async.publication.AsyncMakePublication;
import br.edu.puccamp.app.entity.Publicacao;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.posts.QuestionsAdapter;
import br.edu.puccamp.app.util.API;
import br.edu.puccamp.app.util.MyLayout;

import static android.content.Context.MODE_PRIVATE;


/**
 * Fragment class for each nav menu item
 */
public class MenuMakePublicationFragment extends Fragment implements AsyncMakePublication.Listener {
    private static final String ARG_TEXT = "arg_text";
    private static final String ARG_COLOR = "arg_color";

    private String mText;
    private int mColor;

    private View mContent;
    public  View mProgressView;
    private QuestionsAdapter mAdapter;
    private SharedPreferences prefs;
    private ListView listView;
    private EditText mTextPublication;
    private TextView mButtonPublication;
    private LinearLayout mMakePublication;

    private android.support.design.widget.BottomNavigationView bottomNavigationView;


    public static Fragment newInstance(String text, int color) {
        Fragment frag = new MenuMakePublicationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putInt(ARG_COLOR, color);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_publication, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.navigation_menu);

        ((MyLayout) view.findViewById(R.id.fragment_make_publication)).setOnSoftKeyboardListener(new MyLayout.OnSoftKeyboardListener() {
            @Override
            public void onShown() {
                Log.e("FUU","DEU");
                MainActivity i = (MainActivity) getActivity();
                //TODO
                i.teste();
                listView.setVisibility(View.GONE);
                //bottomNavigationView.setVisibility(View.GONE);
                //findViewById(R.id.image).setVisibility(View.GONE);
            }
            @Override
            public void onHidden() {
                Log.e("FUU","DEU naoo");
                MainActivity i = (MainActivity) getActivity();
                //TODO
                i.teste2();
                listView.setVisibility(View.VISIBLE);
                //bottomNavigationView.setVisibility(View.GONE);

                //findViewById(R.id.image).setVisibility(View.VISIBLE);
            }
        });

        // retrieve text and color from bundle or savedInstanceState
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            mText = args.getString(ARG_TEXT);
            mColor = args.getInt(ARG_COLOR);
        } else {
            mText = savedInstanceState.getString(ARG_TEXT);
            mColor = savedInstanceState.getInt(ARG_COLOR);
        }

        // initialize views
        mContent = view.findViewById(R.id.fragment_content);

        mProgressView = (View) view.findViewById(R.id.publication_progress);




//        mIcon = (AppCompatImageView) findViewById(R.id.iconAlarm);
//        mIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                prefs = getSharedPreferences(API.USUARIO, MODE_PRIVATE);
//                prefs.edit().clear().apply();
//                startActivity(new Intent(DefaultActivity.this, br.edu.puccamp.app.MainActivity.class));
//                finish();
//            }
//        });

        listView = (ListView) view.findViewById(R.id.testemgovea);

        mButtonPublication = (TextView) view.findViewById(R.id.text_make_post);
        mTextPublication = (EditText) view.findViewById(R.id.et_publication);

        mMakePublication = (LinearLayout) view.findViewById(R.id.make_publication);

        //TODO

//        mMakePublication.setOnSoftKeyboardListener(new MyLayout.OnSoftKeyboardListener() {
//            @Override
//            public void onShown() {
//                listView.setVisibility(View.GONE);
//                //Log.e("FUU","DEU");
//            }
//
//            @Override
//            public void onHidden() {
//                listView.setVisibility(View.VISIBLE);
//                //Log.e("FUU","DEU, MENTIRA DEU BOM");
//            }
//        });

        //END TODO

        mButtonPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mTextPublication.getText().toString().trim().equals("")) {

                    Gson gson = new Gson();

                    prefs = getContext().getSharedPreferences(API.USUARIO, MODE_PRIVATE);
                    Usuario usuario = gson.fromJson(prefs.getString(API.USUARIO, null), Usuario.class);
                    Publicacao publicacao = new Publicacao(usuario, mTextPublication.getText().toString());

                    showProgress(true);
                    AsyncMakePublication sinc = new AsyncMakePublication(MenuMakePublicationFragment.this);
                    sinc.execute(publicacao);
                }
            }
        });


//        ImagePipelineConfig config = ImagePipelineConfig
//                .newBuilder(getContext())
//                .setDownsampleEnabled(true)
//                .build();
//        Fresco.initialize(getContext(), config);

        // from the link above

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TEXT, mText);
        outState.putInt(ARG_COLOR, mColor);
        super.onSaveInstanceState(outState);
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mMakePublication.setVisibility(show ? View.GONE : View.VISIBLE);
            mMakePublication.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMakePublication.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mMakePublication.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    // ***************************************
    // Metodos de retorno Async
    // ***************************************

    @Override
    public void onLoadedError(String s) {
        showProgress(false);
        //dismissProgressDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.error));
        builder.setMessage(getString(R.string.error));
        builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //b.finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onLoadedPublication(Boolean bool) {
        showProgress(false);
        //dismissProgressDialog();
        MainActivity i = (MainActivity) getActivity();
        //TODO
        i.openPublication(R.id.menu_publication);

    }


}
