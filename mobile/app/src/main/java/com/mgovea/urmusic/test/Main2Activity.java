package com.mgovea.urmusic.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mgovea.urmusic.R;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class Main2Activity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main2);

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            //to use RecycleView, you need a layout manager. default is LinearLayoutManager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            //recyclerView.setLayoutManager(linearLayoutManager);
            RecyclerAdapter adapter = new RecyclerAdapter(this);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}