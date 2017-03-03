package br.edu.puccamp.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        // The connection URL
//        String url = "https://jsonplaceholder.typicode.com/users/1";
//
//        // Create a new RestTemplate instance
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Add the String message converter
//        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//
//        // Make the HTTP GET request, marshaling the response to a String
//        /*String result = */Log.e(null,restTemplate.getForObject(url, String.class, "Android"));

        //Log.e(null, result);


    }

    public void palomaChata(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /** Called when the user clicks the Send button *
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void createAccount(String email, String password) {

    }



}
