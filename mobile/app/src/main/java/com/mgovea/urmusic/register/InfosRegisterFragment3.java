package com.mgovea.urmusic.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.mgovea.urmusic.R;
import com.mgovea.urmusic.async.localizacao.AsyncPaises;
import com.mgovea.urmusic.entity.Pais;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateus on 28/10/2017.
 */

public class InfosRegisterFragment3 extends Fragment implements AsyncPaises.Listener {

    AutoCompleteTextView tvCountry;

    public InfosRegisterFragment3() {
    }

    public static Fragment newInstance() {
        Fragment fragment = new InfosRegisterFragment3();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_activity_3, container, false);

        tvCountry = view.findViewById(R.id.country);

        if (tvCountry.getAdapter() == null) {
            AsyncPaises sinc = new AsyncPaises(this);
            sinc.execute();
        }

        return view;
    }

    @Override
    public void onLoaded(ArrayList<Pais> paises) {

        List<String> paisesString = new ArrayList<>();

        for (Pais pais : paises) {
            paisesString.add(pais.getPais());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, paisesString);

        tvCountry.setAdapter(adapter);

    }

    @Override
    public void onLoadedError(String s) {

    }
}