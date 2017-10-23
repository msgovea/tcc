package com.mgovea.urmusic.posts.options;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mgovea.urmusic.util.API;
import com.mgovea.urmusic.util.Menu;
import com.mgovea.urmusic.util.Preferencias;

import java.util.ArrayList;

import com.mgovea.urmusic.R;;

/**
 * Created by mgovea on 9/90/2017.
 */
public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            //setStateText(newState);
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            //setOffsetText(slideOffset);
        }
    };
    private RecyclerView mRecyclerView;
    private OptionsAdapter mAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View contentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(contentView, savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_dialog_teste, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        Long idPublicacao = getArguments().getLong(API.PUBLICACAO);
        Long idUsuario = getArguments().getLong(API.USUARIO);

        Log.i("IDUSUARIO", idUsuario + "");
        Preferencias pref = new Preferencias(getContext());
        Log.i("IDMEUUSUARIO", pref.getDadosUsuario().getCodigoUsuario() + "");

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerview_options);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        /*
        * TODO PALOMA
        * TEXTOS COM DESCRIÇÃO E SUBDESCRIÇÃO DOS MENUS
            QUE APARECEM QUANDO SE CLICA NOS 3 PONTINHOS DA PUBLICAÇÃO
            */

        ArrayList<Menu> menu = new ArrayList<>();

        //Preferencias pref = new Preferencias(getContext());

        //TODO MSG PALOMA
        if (pref.getDadosUsuario().getCodigoUsuario().equals(idUsuario)) {
            menu.add(new Menu(1, "Excluir", "Sua publicação será definitivamente removida."));
            menu.add(new Menu(2, getString(R.string.impulsionar), "Seu conteúdo para um maior número de usuários!"));
        } else {
            menu.add(new Menu(3, "Denunciar", "Caso o conteúdo seja ofensivo ou impróprio."));
        }

        mAdapter = new OptionsAdapter(getContext(), menu, idPublicacao, idUsuario);
        mRecyclerView.setAdapter(mAdapter);

    }

}
