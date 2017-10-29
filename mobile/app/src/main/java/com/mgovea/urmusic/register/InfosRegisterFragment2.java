package com.mgovea.urmusic.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.mgovea.urmusic.R;
import com.mgovea.urmusic.async.localizacao.AsyncPaises;
import com.mgovea.urmusic.entity.Pais;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateus on 28/10/2017.
 */

public class InfosRegisterFragment2 extends Fragment {


    public InfosRegisterFragment2() {
    }

    public static Fragment newInstance() {
        Fragment fragment = new InfosRegisterFragment2();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_activity_2, container, false);

    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

//    }


}