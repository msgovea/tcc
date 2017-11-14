package com.mgovea.urmusic.register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgovea.urmusic.R;

/**
 * Created by Mateus on 28/10/2017.
 */

public class InfosRegisterFragment1 extends Fragment {

    public InfosRegisterFragment1() {
    }

    public static Fragment newInstance() {
        Fragment fragment = new InfosRegisterFragment1();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_activity_1, container, false);
    }
}