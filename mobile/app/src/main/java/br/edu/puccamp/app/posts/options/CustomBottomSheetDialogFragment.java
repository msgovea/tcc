package br.edu.puccamp.app.posts.options;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.puccamp.app.R;
import br.edu.puccamp.app.entity.Comentario;
import br.edu.puccamp.app.entity.Usuario;
import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.util.API;
import br.edu.puccamp.app.util.Menu;

/**
 * Created by Nikola D. on 2/25/2016.
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

        //
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerview_options);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        /*
        * TODO PALOMA
        * TEXTOS COM DESCRIÇÃO E SUBDESCRIÇÃO DOS MENUS
            QUE APARECEM QUANDO SE CLICA NOS 3 PONTINHOS DA PUBLICAÇÃO
            */
        ArrayList<Menu> menu = new ArrayList<>();

        menu.add(new Menu("Excluir", "Esta ação removerá completamente sua publicação"));
        menu.add(new Menu("Impulsionar publicação", "Leve seu conteúdo para mais gente"));
        menu.add(new Menu("Denunciar publicação", "Envie esta publicação para uma análise"));

        mAdapter = new OptionsAdapter(getContext(), menu, idPublicacao);
        mRecyclerView.setAdapter(mAdapter);

    }

}
