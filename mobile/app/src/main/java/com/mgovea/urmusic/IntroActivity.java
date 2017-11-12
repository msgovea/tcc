package com.mgovea.urmusic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);
        //setSkipButtonVisible();
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.primary_dark)
                        .buttonsColor(R.color.accent)
                        .image(R.drawable.logo_m)
                        .title("\n" + getString(R.string.intro_title))
                        .description("\n\n" +getString(R.string.intro_message))
                        .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.primary_dark)
                .buttonsColor(R.color.accent)
                .image(R.drawable.mock_png)
                .title("\n" + "Amantes da Música")
                .description("\n\n" +"Dedicado ao público envolvido com a música: músicos profissionais, amadores, donos de casa de evento ou somente quem gosta de ouvi-la.")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.primary_dark)
                .buttonsColor(R.color.accent)
                .image(R.drawable.publi_1)
                .title("\n" + "Interesses")
                .description("\n\n" + "Baseado no gosto musical de cada usuário, facilitando assim, atingir o público que realmente tem o mesmo interesse por música que você")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.primary_dark)
                .buttonsColor(R.color.accent)
                .image(R.drawable.publi_2)
                .title("\n" + "Individualidade")
                .description("\n\n" +"Facilidade de conhecer, promover e compartilhar músicas e eventos para um público focado")
                .build());


        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.primary_dark)
                        .buttonsColor(R.color.accent)
                        .image(R.drawable.perfil)
                        .title("\n" + getString(R.string.intro_title3))
                        .description("\n\n" + getString(R.string.intro_message3))
                        .build());
    }


    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}