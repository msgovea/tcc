package br.edu.puccamp.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import listbuy.me.listbuy.lista.DbConn;

public class LoginActivity extends AppCompatActivity implements SincronizaLogin.Listener{

    private EditText mEmailView, mPasswordView;
    private Button sign_in_register;
    private RequestQueue requestQueue;
    private static final String URL = "http://www.listbuy.me/api/user_control.php";
    private StringRequest request;
    public static View mProgressView;
    private View mLoginFormView;
    private DbConn dbconn;

    public static Context context;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                /*startActivity(new Intent(this, SuaActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem*/
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, WelcomeScreen.class));
        finishActivity(0);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Login ListBuy");


        mEmailView = (EditText) findViewById(R.id.emailLogin);
        mPasswordView = (EditText) findViewById(R.id.passwordLogin);
        sign_in_register = (Button) findViewById(R.id.sign_in_register);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.emailLogin|| id == EditorInfo.IME_NULL) {
                    attemptLogin();
                }
                return true;
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

  /*  private void request() {
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success")) {
                        Toast.makeText(getApplicationContext(), "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = getSharedPreferences("INFORMACOES_LOGIN_AUTOMATICO", MODE_PRIVATE).edit();

                        editor.putString("nome", mEmailView.getText().toString());
                        editor.putString("login", mEmailView.getText().toString());
                        editor.putString("senha", mPasswordView.getText().toString());
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(), Welcome.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Error" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("email", mEmailView.getText().toString());
                hashMap.put("password", mPasswordView.getText().toString());
                hashMap.put("type", "login");

                return hashMap;
            }
        };

        requestQueue.add(request);
    }*/

    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("É Necessário preencher este campo");
            requestFocus(mEmailView);
            return;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            requestFocus(mEmailView);
            return;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            requestFocus(mPasswordView);
            return;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            requestFocus(mPasswordView);
            return;
        }

        showProgress(true);
        SincronizaLogin sinc = new SincronizaLogin(this);
        sinc.execute(mEmailView.getText().toString(),mPasswordView.getText().toString());
        //request();
    }

/////////////////////////////////////


    private void requestFocus(View focusView) { focusView.requestFocus(); }

    private boolean isPasswordValid(String password) { return password.length() > 4; }

    private boolean isEmailValid(String email) {
        if (email.contains("@")) {
            String[] validacao = email.split("@");
            return (validacao.length > 1);
        }
        return false;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onLoaded(String string) {
        if (string == "true") {
            showProgress(false);
            startActivity(new Intent(this, MenuLateral.class));
            SharedPreferences.Editor editor = getSharedPreferences("INFORMACOES_LOGIN_AUTOMATICO", MODE_PRIVATE).edit();

            editor.putString("nome", mEmailView.getText().toString());
            editor.putString("login", mEmailView.getText().toString());
            editor.putString("senha", mPasswordView.getText().toString());
            editor.commit();
            finishActivity(1);
        } else {
            showProgress(false);
        }
    }
}


