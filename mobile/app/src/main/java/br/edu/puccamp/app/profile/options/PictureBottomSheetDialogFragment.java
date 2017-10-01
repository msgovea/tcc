package br.edu.puccamp.app.profile.options;

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

import java.util.ArrayList;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.util.API;
import br.edu.puccamp.app.util.Menu;
import br.edu.puccamp.app.util.Preferencias;

/**
 * Created by mgovea on 9/90/2017.
 */
public class PictureBottomSheetDialogFragment extends BottomSheetDialogFragment {

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
    private PictureAdapter mAdapter;

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

        Long idUsuario = getArguments().getLong(API.USUARIO);

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerview_options);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        /*
        * TODO PALOMA
        * TEXTOS COM DESCRIÇÃO E SUBDESCRIÇÃO DOS MENUS
        * QUE APARECEM QUANDO SE CLICA NA FOTO DO PROPRIO PERFIL
        */

        ArrayList<Menu> menu = new ArrayList<>();
        menu.add(new Menu(1, "Visualizar", "Visualize sua imagem de perfil"));
        menu.add(new Menu(2, "Alterar", "Altere sua imagem de perfil"));

        mAdapter = new PictureAdapter(getContext(), menu, idUsuario);
        mRecyclerView.setAdapter(mAdapter);

    }

}
