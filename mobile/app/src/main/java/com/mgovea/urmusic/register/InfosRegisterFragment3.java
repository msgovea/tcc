package com.mgovea.urmusic.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgovea.urmusic.R;

/**
 * Created by Mateus on 28/10/2017.
 */

public class InfosRegisterFragment3 extends Fragment {

    View mContent;

    public InfosRegisterFragment3() {
    }

    public static Fragment newInstance() {
        Fragment fragment = new InfosRegisterFragment3();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_activity_3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // initialize views
        mContent = view.findViewById(R.id.register_step_3);


    }
}